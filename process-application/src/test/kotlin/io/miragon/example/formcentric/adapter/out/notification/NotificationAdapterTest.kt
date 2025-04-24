package io.miragon.example.formcentric.adapter.out.notification

import co.novu.Novu
import co.novu.extensions.messages
import io.miragon.example.formcentric.adapter.out.notification.novu.NovuConfiguration
import io.miragon.example.formcentric.adapter.out.notification.novu.NovuProperties
import io.miragon.example.formcentric.domain.Email
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertTrue

@SpringBootTest(classes = [NotificationAdapter::class, NovuConfiguration::class])
@EnableConfigurationProperties(value = [NovuProperties::class])
@ActiveProfiles("test")
class NotificationAdapterTest {
    @Autowired
    private lateinit var notificationAdapter: NotificationAdapter

    @Autowired
    private lateinit var novuApiV1: Novu

    @Test
    fun `should send notification to reviewer`() {
        // Given
        val testLink = "https://example.com/form/123456789"

        runBlocking {
            // When
            val transactionId = notificationAdapter.sendToReviewer(testLink)

            // Then
            delay(2000)
            val response = novuApiV1.messages(
                transactionId = transactionId,
            )?.data

            assertTrue { response?.size == 2 }
        }
    }

    @Test
    fun `should send notification to applicant`() {
        // Given
        val email = Email(
            address = "peter.heinemann@miragon.io",
            subject = "Test",
            body = "test",
        )

        runBlocking {
            // When
            val transactionId = notificationAdapter.sendToApplicant(email)

            // Then
            delay(2000)
            val response = novuApiV1.messages(
                transactionId = transactionId,
            )?.data

            assertTrue { response?.size == 1 }
        }
    }
}