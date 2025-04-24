package io.miragon.example.formcentric.application.port.out

import io.miragon.example.formcentric.domain.Email

interface SendNotificationPort {
    fun sendToReviewer(link: String): String

    fun sendToApplicant(email: Email): String
}