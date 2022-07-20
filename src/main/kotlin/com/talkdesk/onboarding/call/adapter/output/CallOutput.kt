package com.talkdesk.onboarding.call.adapter.output

import com.talkdesk.onboarding.call.adapter.output.mapper.toCall
import com.talkdesk.onboarding.call.adapter.output.mapper.toCallEntity
import com.talkdesk.onboarding.call.adapter.output.mapper.toCallEntityUpdate
import com.talkdesk.onboarding.call.adapter.output.persistence.ICallJPARepository
import com.talkdesk.onboarding.call.application.domain.Call
import com.talkdesk.onboarding.call.application.port.output.ICallOutput
import com.talkdesk.onboarding.call.config.validation.SortFields
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CallOutput(private val repository: ICallJPARepository) : ICallOutput {
    override fun saveCall(call: Call) {
        val currentCallEntity = repository.findById(call.callId)

        if (currentCallEntity.isPresent)
            repository.save(call.toCallEntityUpdate(currentCallEntity.get()))
        else
            repository.save(call.toCallEntity())
    }

    override fun getCallHistory(page: String, pageSize: String, orderBy: String, direction: String): Page<Call> {

        val pageRequest = PageRequest.of(page.toInt(), pageSize.toInt(), Sort.Direction.valueOf(direction), SortFields.toEnum(orderBy)?.entityField)

        return repository.findAll(pageRequest).map { it.toCall() }
    }
}