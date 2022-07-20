package com.talkdesk.onboarding.call.application.port.input

import com.talkdesk.onboarding.call.application.domain.Call
import org.springframework.data.domain.Page

interface ICallInput {

    fun receiveCall(call: Call)
    fun getlCallHistory(page: String, pageSize: String, orderBy: String, direction: String): Page<Call>
}