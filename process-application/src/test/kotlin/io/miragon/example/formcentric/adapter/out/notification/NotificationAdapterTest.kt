package io.miragon.example.formcentric.adapter.out.notification

import co.novu.Novu
import co.novu.extensions.messages
import io.miragon.example.formcentric.adapter.out.notification.novu.NovuConfiguration
import io.miragon.example.formcentric.adapter.out.notification.novu.NovuProperties
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
    fun `should send notification with link`() {
        // Given
        val email = "peter.hnm@gmx.de"
        val testLink = "https://example.com/form/123456789"

        // When
        val transactionId = notificationAdapter.send(email, testLink)

        // Then
        runBlocking {
            val response = novuApiV1.messages(
                transactionId = transactionId,
            )?.data

            assertTrue { response?.isNotEmpty() ?: false }
        }
    }
}