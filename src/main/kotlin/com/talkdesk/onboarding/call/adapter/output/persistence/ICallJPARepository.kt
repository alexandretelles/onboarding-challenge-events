package com.talkdesk.onboarding.call.adapter.output.persistence

import com.talkdesk.onboarding.call.adapter.output.persistence.entity.CallEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ICallJPARepository : JpaRepository<CallEntity, String> {

}