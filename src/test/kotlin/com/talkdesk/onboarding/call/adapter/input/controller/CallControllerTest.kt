package com.talkdesk.onboarding.call.adapter.input.controller

import com.talkdesk.onboarding.call.adapter.output.persistence.ICallJPARepository
import com.talkdesk.onboarding.call.adapter.output.persistence.entity.CallEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(properties = ["rabbit.auto-startup=false"])
@EnableAutoConfiguration(
    exclude = [
        JpaRepositoriesAutoConfiguration::class,
        DataSourceAutoConfiguration::class
    ]
)
@ActiveProfiles("test")
class CallControllerTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var repository: ICallJPARepository

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Nested
    @DisplayName("GET /call")
    inner class CallHistory {

        @Test
        fun `should return call history`() {
            val pageCall: Page<CallEntity> = PageImpl(
                listOf(
                    CallEntity(
                        "CA425a9196aa622a41b7c0d9708aa207e6",
                        "2022-07-20T07:57:01Z",
                        null,
                        "+351920118522",
                        "voicemail",
                        "ringing",
                        1,
                        1
                    )
                )
            )

            whenever(repository.findAll(any<PageRequest>())).thenReturn(pageCall)

            mockMvc.perform(get("/call"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value("CA425a9196aa622a41b7c0d9708aa207e6"))
                .andExpect(jsonPath("$.content[0].start_date").value("2022-07-20T07:57:01Z"))
                .andExpect(jsonPath("$.content[0].end_date").value(null))
                .andExpect(jsonPath("$.content[0].customer_phone_number").value("+351920118522"))
                .andExpect(jsonPath("$.content[0].call_outcome").value("voicemail"))
                .andExpect(jsonPath("$.content[0].status").value("ringing"))
                .andExpect(jsonPath("$.content[0].duration").value(1))
                .andExpect(jsonPath("$.content[0].agent_id").value(1))
        }
    }
}