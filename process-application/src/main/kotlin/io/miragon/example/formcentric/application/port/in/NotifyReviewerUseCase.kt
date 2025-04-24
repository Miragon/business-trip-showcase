package io.miragon.example.formcentric.application.port.`in`

interface NotifyReviewerUseCase {
    fun sendNotification(taskId: String)
}