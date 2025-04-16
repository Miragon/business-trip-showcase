package io.miragon.example.formcentric.application.port.out

interface SendMailPort {
    fun sendMail(email: String, link: String)
}