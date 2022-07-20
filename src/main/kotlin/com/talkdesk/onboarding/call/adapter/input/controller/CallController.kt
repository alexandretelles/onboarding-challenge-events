package com.talkdesk.onboarding.call.adapter.input.controller

import com.talkdesk.onboarding.call.adapter.input.controller.dto.CallResponseDTO
import com.talkdesk.onboarding.call.adapter.input.mapper.toCallResponseDTO
import com.talkdesk.onboarding.call.application.domain.Call
import com.talkdesk.onboarding.call.application.port.input.ICallInput
import com.talkdesk.onboarding.call.config.validation.SortValues
import org.hibernate.validator.constraints.Range
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Digits
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/call")
@Validated
class CallController @Autowired constructor(private val callService: ICallInput) {

    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun callHistory(
        @RequestParam(value="page", defaultValue="0")
            @Pattern(regexp = "^\\d+", message = "page number must be a number")  page: String,
        @RequestParam(value="pageSize", defaultValue="10")
            @Pattern(regexp = "^[1-9][0-9]?\$|^100\$", message = "page size must be between 1 and 100") pageSize: String,
        @RequestParam(value="orderBy", defaultValue="start_date")
            @SortValues("Invalid field(orderBy)") orderBy: String ,
        @RequestParam(value="direction", defaultValue="ASC")
            @Pattern(regexp="^(ASC|DESC)$",message="invalid direction value") direction: String
    ) : ResponseEntity<Page<CallResponseDTO>> {

        val calls: Page<Call> = callService.getlCallHistory(page, pageSize, orderBy, direction)

        return ResponseEntity<Page<CallResponseDTO>>(calls.map { it.toCallResponseDTO() }, HttpStatus.OK)
    }
}