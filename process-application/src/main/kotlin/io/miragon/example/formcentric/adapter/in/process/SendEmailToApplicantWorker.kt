package io.miragon.example.formcentric.adapter.`in`.process

import dev.bpmcrafters.processengine.worker.ProcessEngineWorker
import dev.bpmcrafters.processengine.worker.Variable
import io.miragon.example.formcentric.application.port.`in`.NotifyApplicantUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Component

@Component
@Suppress("unused")
class SendEmailToApplicantWorker(
    private val notifyApplicantUseCase: NotifyApplicantUseCase
) {

    @ProcessEngineWorker(topic = "send-email")
    fun sendMail(@Variable("request") request: BusinessTripRequest, @Variable("approval") approval: Boolean) {
        notifyApplicantUseCase.sendNotification(request, approval)
    }

}