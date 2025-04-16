package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.SendMailUseCase
import io.miragon.example.formcentric.application.port.out.SendMailPort
import org.springframework.stereotype.Service

@Service
class SendMailService(
    private val sendMailPort: SendMailPort
) : SendMailUseCase {
    override fun sendMail(email: String, taskId: String) {
        val link = "localhost:8080/$taskId"
        sendMailPort.sendMail(email, link)
    }
}