package io.miragon.example.formcentric.adapter.out.notification

import io.miragon.example.formcentric.application.port.out.SendNotificationPort
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class NotificationAdapter() : SendNotificationPort {

    private val log = KotlinLogging.logger {}

    override fun send(address: String, subject: String, body: String) {
        log.info { "Send mail to $address with message $body" }
    }
}