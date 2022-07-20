package com.talkdesk.onboarding.call.application.service

import com.talkdesk.onboarding.call.application.domain.Call
import com.talkdesk.onboarding.call.application.port.input.ICallInput
import com.talkdesk.onboarding.call.application.port.output.ICallOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class CallService(private val callOutput: ICallOutput) : ICallInput {
    override fun receiveCall(call: Call) {
        callOutput.saveCall(call)
    }

    override fun getlCallHistory(page: String, pageSize: String, orderBy: String, direction: String): Page<Call> {
        return callOutput.getCallHistory(page, pageSize, orderBy, direction)
    }
}