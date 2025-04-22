package io.miragon.example.formcentric.application.port.`in`

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface CompleteUserTaskUseCase {
    fun complete(taskId: String, request: BusinessTripRequest): Boolean
}