package io.miragon.example.formcentric.application.port.out

interface SendNotificationPort {
    fun send(email: String, link: String): String
}