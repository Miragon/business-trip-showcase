package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.BusinessTripDecisionUseCase
import io.miragon.example.formcentric.application.port.out.UserTaskCompletionPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Service

@Service
class BusinessTripDecisionService(
    private val userTaskCompletionPort: UserTaskCompletionPort
) : BusinessTripDecisionUseCase {
    override fun decide(taskId: String, request: BusinessTripRequest): Boolean {
        return this.userTaskCompletionPort.complete(taskId, request)
    }
}