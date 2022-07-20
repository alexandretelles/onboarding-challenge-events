package com.talkdesk.onboarding.call.adapter.input.consumer.event

import com.fasterxml.jackson.annotation.JsonProperty
import com.talkdesk.events.consumer.handler.EventName

@EventName("inbound_call")
data class CallEventDTO(

    @JsonProperty("call_id")
    val callId: String,

    @JsonProperty("start_date")
    val startDate: String? = null,

    @JsonProperty("end_date")
    val endDate: String? = null,

    @JsonProperty("customer_phone_number")
    val customerPhoneNumber: String,

    @JsonProperty("call_outcome")
    val callOutcome: String? = null,

    @JsonProperty("status")
    val status: String,

    @JsonProperty("duration")
    val duration: Int? = null,

    @JsonProperty("agent_id")
    val agentId: Long? = null,

    @JsonProperty("metadata")
    val metadata: MetadataDTO
)