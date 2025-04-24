package io.miragon.example.formcentric.adapter.out.notification

import io.miragon.example.formcentric.adapter.out.notification.novu.NovuClient
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import io.miragon.example.formcentric.domain.Email
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class NotificationAdapter(
    private val novuClient: NovuClient
) : SendNotificationPort {

    private val log = KotlinLogging.logger {}

    override fun sendToReviewer(link: String): String {
        log.info("[NOTIFICATION] Sending link $link to reviewer")
        var transactionId = ""
        runBlocking {
            transactionId = novuClient.trigger(
                link = link,
                chat = true,
            ) ?: ""
        }
        return transactionId
    }

    override fun sendToApplicant(email: Email): String {
        log.info("[NOTIFICATION] Sending notification to applicant $email")
        var transactionId = ""
        runBlocking {
            transactionId = novuClient.trigger(
                link = "",
                email = email,
            ) ?: ""
        }
        return transactionId
    }
}