package io.miragon.example.formcentric.application.port.`in`

interface SendMailUseCase {
    fun sendMail(email: String, taskId: String)
}