package com.talkdesk.onboarding.call.adapter.input.consumer

import com.talkdesk.events.consumer.handler.handlerFor
import com.talkdesk.onboarding.call.adapter.input.consumer.event.CallEventDTO
import com.talkdesk.onboarding.call.adapter.input.mapper.toCall
import com.talkdesk.onboarding.call.application.port.input.ICallInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    value=["rabbit.auto-startup"],
    havingValue = "true",
    matchIfMissing = true)
class CallEventConsumer (private val callInput: ICallInput) {
    @Bean
    fun inboundCall() = handlerFor<CallEventDTO> { event ->
        println("Handle event $event")

        callInput.receiveCall(event.toCall())
    }
}