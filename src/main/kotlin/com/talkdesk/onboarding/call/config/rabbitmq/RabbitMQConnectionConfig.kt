package com.talkdesk.onboarding.call.config.rabbitmq

import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
data class RabbitMQConnectionConfig(
    val host: String,
    val username: String,
    val password: String,
    val vhost: String,
    val protocol: String,
    val port: Int
) {
    val uri = URI(protocol, "$username:$password", host, port, if (vhost.isBlank()) "" else "/$vhost", "", "")
}
