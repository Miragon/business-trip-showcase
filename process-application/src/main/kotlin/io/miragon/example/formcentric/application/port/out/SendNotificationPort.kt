package io.miragon.example.formcentric.application.port.out

import io.miragon.example.formcentric.domain.Email

interface SendNotificationPort {
    fun sendToReviewer(link: String, email: Email): String

    fun sendToApplicant(email: Email): String
}