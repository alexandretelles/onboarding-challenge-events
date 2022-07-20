package com.talkdesk.onboarding.call.application.port.output

import com.talkdesk.onboarding.call.application.domain.Call
import org.springframework.data.domain.Page

interface ICallOutput {

    fun saveCall(call: Call)
    fun getCallHistory(page: String, pageSize: String, orderBy: String, direction: String): Page<Call>
}