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
    override fun sendNotification(request: BusinessTripRequest) {
        val greeting = "Dear ${request.name}\n\n"
        val message = if (request.approval) {
            "Your business trip to ${request.destination} from ${request.dateFrom} to ${request.dateTo} has been granted."
        } else {
            "We have to inform you that your business trip to ${request.destination} from ${request.dateFrom} has been" +
                    " denied, because of the following reasons: ${request.comment}"
        }

        val email = Email(
            address = request.email,
            subject = "Regarding you business trip to ${request.destination}",
            body = greeting + message,
        )

        notificationPort.sendToApplicant(email)
    }
}