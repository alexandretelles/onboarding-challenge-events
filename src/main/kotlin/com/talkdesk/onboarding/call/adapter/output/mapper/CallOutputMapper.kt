package com.talkdesk.onboarding.call.adapter.output.mapper

import com.talkdesk.onboarding.call.adapter.output.persistence.entity.CallEntity
import com.talkdesk.onboarding.call.application.domain.Call

fun Call.toCallEntity() =
    CallEntity(
        id = callId,
        startDate = startDate,
        endDate = endDate,
        customerPhoneNumber = customerPhoneNumber,
        callOutcome = callOutcome,
        status = status,
        duration = duration,
        agentId = agentId
    )

fun Call.toCallEntityUpdate(callEntity: CallEntity) =
    CallEntity(
        id = callEntity.id,
        startDate = startDate ?: callEntity.startDate,
        endDate = endDate ?: callEntity.endDate,
        customerPhoneNumber = callEntity.customerPhoneNumber,
        callOutcome = callOutcome ?: callEntity.callOutcome,
        status = status,
        duration = duration,
        agentId = agentId ?: callEntity.agentId
    )

fun CallEntity.toCall() =
    Call(
        callId = id,
        startDate = startDate,
        endDate = endDate,
        customerPhoneNumber = customerPhoneNumber,
        callOutcome = callOutcome,
        status = status,
        duration = duration,
        agentId = agentId
    )
