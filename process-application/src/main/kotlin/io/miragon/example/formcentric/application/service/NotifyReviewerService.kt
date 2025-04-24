package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.NotifyReviewerUseCase
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NotifyReviewerService(
    private val sendNotificationPort: SendNotificationPort
) : NotifyReviewerUseCase {

    @Value("\${formcentric-showcase.base-url}")
    lateinit var baseUrl: String

    override fun sendNotification(taskId: String) {
        val link = "$baseUrl/$taskId"
        sendNotificationPort.sendToReviewer(link)
    }
}