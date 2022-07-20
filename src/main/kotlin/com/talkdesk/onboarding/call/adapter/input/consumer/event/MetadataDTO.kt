package com.talkdesk.onboarding.call.adapter.input.consumer.event

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class MetadataDTO (
    @JsonProperty("event")
    val event: String,

    @JsonProperty("timestamp")
    val timestamp: Instant?,

    @JsonProperty("event_id")
    val eventId: String?,

    @JsonProperty("platform_tid")
    val platformTid: String?,

    @JsonProperty("account_id")
    val accountId: String?
)