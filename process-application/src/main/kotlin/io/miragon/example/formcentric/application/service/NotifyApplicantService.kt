package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.NotifyApplicantUseCase
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import io.miragon.example.formcentric.domain.Email
import org.springframework.stereotype.Service

@Service
class NotifyApplicantService(
    private val notificationPort: SendNotificationPort
) : NotifyApplicantUseCase {
    override fun sendNotification(request: BusinessTripRequest, approval: Boolean) {
        val greeting = "Dear ${request.firstname} ${request.lastname}\n\n"
        val message = if (approval) {
            "Your business trip from ${request.startDate} to ${request.finalDestination.date} has been granted."
        } else {
            "We have to inform you that your business trip from ${request.startDate} has been" +
                    " denied, because of the following reasons: ${request.comment}"
        }

        val email = Email(
            address = request.mail,
            subject = "Regarding your business trip",
            body = greeting + message,
        )

        notificationPort.sendToApplicant(email)
    }
}