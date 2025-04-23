package io.miragon.example.formcentric.application.port.out

interface SendNotificationPort {
    fun sendMail(email: String, link: String)
}