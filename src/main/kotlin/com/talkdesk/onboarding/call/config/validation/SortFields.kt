package com.talkdesk.onboarding.call.config.validation

enum class SortFields(val sortField: String, val entityField: String) {
    ID("id", "id"),
    START_DATE("start_date", "startDate"),
    END_DATE("end_date", "endDate"),
    CUSTOMER_PHONE_NUMBER("customer_phone_number", "customerPhoneNumber"),
    CALL_OUTCOME("call_outcome", "callOutcome"),
    STATUS("status", "status"),
    DURATION("duration", "duration"),
    AGENT_ID("agent_id", "agentId");

    companion object {
        fun toEnum(value: String?) = SortFields.values().firstOrNull { it.sortField == value }
    }
}