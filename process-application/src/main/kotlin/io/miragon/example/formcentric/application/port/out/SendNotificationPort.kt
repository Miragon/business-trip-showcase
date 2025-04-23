package io.miragon.example.formcentric.application.port.out

interface SendNotificationPort {
    fun send(address: String, subject: String, body: String)
}