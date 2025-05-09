package io.miragon.example.formcentric.application.port.`in`

interface BusinessTripDecisionUseCase {
    fun decide(taskId: String, approval: Boolean): Boolean
}