package io.miragon.example.formcentric.application.port.`in`

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface BusinessTripDecisionUseCase {
    fun decide(taskId: String, request: BusinessTripRequest): Boolean
}