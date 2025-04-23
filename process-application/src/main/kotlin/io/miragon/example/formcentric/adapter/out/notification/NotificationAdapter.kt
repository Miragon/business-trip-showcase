package io.miragon.example.formcentric.adapter.out.notification

import io.miragon.example.formcentric.adapter.out.notification.novu.NovuClient
import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class NotificationAdapter(
    private val novuClient: NovuClient
) : SendNotificationPort {

    private val log = KotlinLogging.logger {}

    override fun send(email: String, link: String): String {
        log.info("[NOTIFICATION] Sending notification with link $link")
        var transactionId = ""
        runBlocking {
            transactionId = novuClient.trigger(email, link) ?: ""
        }
        return transactionId
    }
}