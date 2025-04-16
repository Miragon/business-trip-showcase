package io.miragon.example.formcentric.adapter.out.mail

import io.miragon.example.formcentric.application.port.out.SendMailPort
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MailAdapter() : SendMailPort {

    private val log = KotlinLogging.logger {}

    override fun sendMail(email: String, link: String) {
        // TODO: Implement SendGrid
        log.info { "Send mail to $email with link $link" }
    }
}