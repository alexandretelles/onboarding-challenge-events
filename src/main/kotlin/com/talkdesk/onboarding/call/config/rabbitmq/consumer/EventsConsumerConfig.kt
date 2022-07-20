package com.talkdesk.onboarding.call.config.rabbitmq.consumer

import com.bugsnag.Bugsnag
import com.rabbitmq.client.ConnectionFactory
import com.talkdesk.events.common.QueueInitializer
import com.talkdesk.events.consumer.ConsumerExceptionHandler
import com.talkdesk.events.consumer.DefaultConsumerExceptionHandler
import com.talkdesk.events.consumer.RequeuingExceptionHandler
import com.talkdesk.events.consumer.RetryException
import com.talkdesk.events.consumer.handler.EventHandler
import com.talkdesk.events.consumer.handler.HandlerDispatcher
import com.talkdesk.events.consumer.rabbitmq.RabbitMQConsumer
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.util.Optional
import javax.annotation.PreDestroy

@Configuration
@EnableConfigurationProperties(EventsConsumerProperties::class)
class EventsConsumerConfig(
    private val properties: EventsConsumerProperties,
    private val bugsnag: Optional<Bugsnag>,
    handlers: List<EventHandler<*>>
) {
    private val logger = LoggerFactory.getLogger(EventsConsumerConfig::class.java)
    private val dispatcher = HandlerDispatcher(handlers)
    private lateinit var consumers: List<RabbitMQConsumer>

    @Bean
    fun runner() = CommandLineRunner {
        try {
            logger.info("initializing queue")
            QueueInitializer.initializeQueue(
                connectionFactory = connectionFactory,
                exchange = properties.exchange,
                queue = properties.queueName,
                routingKeys = properties.routingKeys.toList()
            )

            logger.debug("Launching {} consumer(s).", properties.threads)
            consumers = launchConsumers()
        } catch (exception: Exception) {
            bugsnag.ifPresent { it.notify(exception) }
            throw exception
        }
    }

    @PreDestroy
    fun closeConsumers() = consumers
        .also { logger.debug("Closing {} consumer(s).", consumers.size) }
        .forEach { consumer ->
            try {
                consumer.close()
            } catch (exception: IOException) {
                bugsnag.ifPresent { bugsnag -> bugsnag.notify(exception) }
                logger.debug("Failed to close consumer. For more information check bugsnag")
            }
        }
        .also { logger.debug("Consumer(s) closed.") }

    val connectionFactory = ConnectionFactory().apply {
        setUri(properties.uri)
        requestedHeartbeat = properties.heartbeat
    }

    private fun exceptionHandler(): ConsumerExceptionHandler = RequeuingExceptionHandler(
        connectionFactory = connectionFactory,
        retryOn = setOf(
            DefaultConsumerExceptionHandler::class.java,
            RetryException::class.java
        ),
        retriesPerMessage = properties.retries,
        bugsnag = bugsnag.orElse(null)
    )

    private fun launchConsumers() = (1..properties.threads).map { _ ->
        RabbitMQConsumer(
            connectionFactory = connectionFactory,
            prefetch = properties.prefetch,
            queueName = properties.queueName,
            dispatcher = dispatcher,
            bugsnag = bugsnag.orElse(null),
            exceptionHandler = exceptionHandler()
        ).also { it.start() }
    }
}
