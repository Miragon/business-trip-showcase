package io.miragon.example.formcentric.adapter.out.notification.novu

import co.novu.Novu
import co.novu.dto.ChannelCredentials
import co.novu.dto.Credential
import co.novu.dto.request.IntegrationRequest
import co.novu.dto.request.SubscriberRequest
import co.novu.dto.request.TriggerEventRequest
import co.novu.dto.request.UpdateSubscriberCredentialsRequest
import co.novu.extensions.*
import kotlinx.coroutines.runBlocking

class NovuClient(
    private val novuProperties: NovuProperties,
    private val novuApiV1: Novu,
    private val novuApiV2: Novu,
) {

    private val subscriberId = "formcentric-subscriber"

    private var msTeamsIntegrationId: String? = null

    fun setUp() = runBlocking {
        // create subscriber
        createSubscriber()
        // create ms-teams integration
        if (!checkIfIntegrationExists("msteams"))
            createMsTeamsIntegration()
        // update subscriber for ms-teams
        updateSubscriberForMsTeams()
    }

    fun cleanUp() = runBlocking {
        // delete subscriber
        deleteSubscriber()
        // delete ms-teams integration
        deleteMsTeamsIntegration()
        // delete workflow
    }

    suspend fun trigger(email: String, link: String): String? {
        val request = TriggerEventRequest(
            name = "formcentric-showcase", // the name of the workflow
            to = SubscriberRequest(
                subscriberId
            ),
            payload = mapOf("link" to link),
            overrides = mapOf("email" to mapOf("to" to listOf(email)))
        )

        val response = novuApiV1.trigger(request)?.data

        if (response?.error?.isNotEmpty() == true) throw Exception()

        return response?.transactionId
    }

    private suspend fun createSubscriber() {
        novuApiV2.createSubscriber(
            SubscriberRequest(
                subscriberId,
                email = novuProperties.subscriber.email,
            )
        )
    }

    private suspend fun deleteSubscriber() {
        novuApiV2.deleteSubscriber(subscriberId)
    }

    private suspend fun checkIfIntegrationExists(providerId: String): Boolean {
        val integrations = novuApiV1.activeIntegrations()?.data
        return integrations?.any { it.providerId == providerId } ?: false
    }

    private suspend fun createMsTeamsIntegration() {
        val request = IntegrationRequest(
            providerId = "msteams",
            channel = "chat",
            credentials = Credential(),
            active = true,
            check = true,
        )

        val integration = novuApiV1.createIntegration(request)?.data

        msTeamsIntegrationId = integration?.id
    }

    private suspend fun deleteMsTeamsIntegration() {
        val integrationId = msTeamsIntegrationId
        if (integrationId != null) {
            novuApiV1.deleteIntegration(integrationId)
        }
    }

    private suspend fun updateSubscriberForMsTeams() {
        novuApiV1.updateSubscriberCredentials(
            subscriberId,
            UpdateSubscriberCredentialsRequest(
                "msteams",
                ChannelCredentials(
                    webhookUrl = novuProperties.subscriber.credentials.msTeams.webhook
                )
            )
        )
    }
}