package io.miragon.example.formcentric.application.port.`in`

interface MailUseCase {
    fun sendMail(email: String, taskId: String)
}