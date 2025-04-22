package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.CompleteUserTaskUseCase
import io.miragon.example.formcentric.application.port.out.UserTaskCompletionPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Service

@Service
class UserTaskCompleteService(
    private val userTaskCompletionPort: UserTaskCompletionPort
) : CompleteUserTaskUseCase {
    override fun complete(taskId: String, request: BusinessTripRequest): Boolean {
        return this.userTaskCompletionPort.complete(taskId, request.approval)
    }
}