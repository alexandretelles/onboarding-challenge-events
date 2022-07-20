package com.talkdesk.onboarding.call.adapter.output.persistence.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "call")
@Table(name = "call")
data class CallEntity(
    @Id
    @Column(name = "id")
    var id: String,

    @Column(name = "start_date")
    @JsonProperty("start_date")
    val startDate: String? = null,

    @Column(name = "end_date")
    val endDate: String? = null,

    @Column(name = "customer_phone_number")
    val customerPhoneNumber: String,

    @Column(name = "call_outcome")
    val callOutcome: String? = null,

    @Column(name = "status")
    val status: String,

    @Column(name = "duration")
    val duration: Int? = null,

    @Column(name = "agent_id")
    val agentId: Long? = null,
)