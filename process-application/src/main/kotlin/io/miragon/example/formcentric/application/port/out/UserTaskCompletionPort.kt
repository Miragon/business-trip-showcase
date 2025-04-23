package io.miragon.example.formcentric.application.port.out

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface UserTaskCompletionPort {
    fun complete(taskId: String, request: BusinessTripRequest): Boolean
}