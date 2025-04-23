package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.SendMailUseCase
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SendNotificationService(
    private val sendNotificationPort: SendNotificationPort
) : SendMailUseCase {

    @Value("\${miragon.base-url}")
    lateinit var baseUrl: String

    override fun sendMail(email: String, taskId: String) {
        val link = "$baseUrl/$taskId"
        sendNotificationPort.sendMail(email, link)
    }
}