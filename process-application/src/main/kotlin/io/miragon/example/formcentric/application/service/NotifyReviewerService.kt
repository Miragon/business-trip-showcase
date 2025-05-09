package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.adapter.out.notification.novu.NovuProperties
import io.miragon.example.formcentric.application.port.`in`.NotifyReviewerUseCase
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import io.miragon.example.formcentric.domain.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NotifyReviewerService(
    private val notificationPort: SendNotificationPort,
    private val novuProperties: NovuProperties
) : NotifyReviewerUseCase {

    @Value("\${formcentric-showcase.base-url}")
    lateinit var baseUrl: String

    override fun sendNotification(taskId: String) {
        val link = "$baseUrl/$taskId"
        val message =
            "A new request was created. Please review it under $link"

        val email = Email(
            address = novuProperties.subscriber.email,
            subject = "New business trip request",
            body = message,
        )

        notificationPort.sendToReviewer(link, email)
    }
}