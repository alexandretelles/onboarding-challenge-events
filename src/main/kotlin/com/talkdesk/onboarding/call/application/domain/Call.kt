package com.talkdesk.onboarding.call.application.domain

data class Call(
    val callId: String,

    val startDate: String? = null,

    val endDate: String? = null,

    val customerPhoneNumber: String,

    val callOutcome: String? = null,

    val status: String,

    val duration: Int? = null,

    val agentId: Long? = null,
)