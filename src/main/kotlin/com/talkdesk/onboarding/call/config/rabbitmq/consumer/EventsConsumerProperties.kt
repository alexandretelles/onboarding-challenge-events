package com.talkdesk.onboarding.call.config.rabbitmq.consumer

import com.talkdesk.onboarding.call.config.rabbitmq.RabbitMQConnectionConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty
import java.net.URI

@ConstructorBinding
@ConfigurationProperties(prefix = "rabbitmq.talkdesk.consumer")
class EventsConsumerProperties(
    val url: String? = null,
    @NestedConfigurationProperty
    val connection: RabbitMQConnectionConfig? = null,
    val routingKeys: Array<String> = arrayOf("calls.inbound"),
    val queueName: String = "inbound_calls",
    val exchange: String = "inbound_calls_exc",
    val threads: Int = 10,
    val prefetch: Int = 255,
    val heartbeat: Int = 60,
    val retries: Int = 3
) {
    val uri = connection?.uri ?: URI(url)
}
