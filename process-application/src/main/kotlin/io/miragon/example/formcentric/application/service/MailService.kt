package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.MailUseCase
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MailService(
    private val sendNotificationPort: SendNotificationPort
) : MailUseCase {

    @Value("\${formcentric-showcase.base-url}")
    lateinit var baseUrl: String

    override fun sendMail(email: String, taskId: String) {
        val link = "$baseUrl/$taskId"
        sendNotificationPort.send(email, link)
    }
}