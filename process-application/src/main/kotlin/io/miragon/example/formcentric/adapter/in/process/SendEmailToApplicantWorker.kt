package io.miragon.example.formcentric.adapter.`in`.process

import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDto
import io.miragon.example.formcentric.application.port.`in`.NotifyApplicantUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Component

@Component
@Suppress("unused")
class SendEmailToApplicantWorker(
    private val notifyApplicantUseCase: NotifyApplicantUseCase
) {

    @JobWorker(type = "send-email")
    fun sendMail(@Variable request: BusinessTripRequestDto) {
        notifyApplicantUseCase.sendNotification(
            BusinessTripRequest(
                name = request.name,
                email = request.email,
                dateFrom = request.dateFrom,
                dateTo = request.dateTo,
                cost = request.cost,
                destination = request.destination,
                approval = request.approval,
                comment = request.comment,
            )
        )
    }

}