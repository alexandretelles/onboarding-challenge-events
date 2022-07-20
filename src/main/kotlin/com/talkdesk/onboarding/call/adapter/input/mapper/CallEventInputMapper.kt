package com.talkdesk.onboarding.call.adapter.input.mapper

import com.talkdesk.onboarding.call.adapter.input.consumer.event.CallEventDTO
import com.talkdesk.onboarding.call.adapter.input.controller.dto.CallResponseDTO
import com.talkdesk.onboarding.call.application.domain.Call

fun CallEventDTO.toCall() =
    Call(
        callId = callId,
        startDate = startDate,
        endDate = endDate,
        customerPhoneNumber = customerPhoneNumber,
        callOutcome = callOutcome,
        status = status,
        duration = duration,
        agentId = agentId
    )

fun Call.toCallResponseDTO() =
    CallResponseDTO(
        callId = callId,
        startDate = startDate,
        endDate = endDate,
        customerPhoneNumber = customerPhoneNumber,
        callOutcome = callOutcome,
        status = status,
        duration = duration,
        agentId = agentId
    )