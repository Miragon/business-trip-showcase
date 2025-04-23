package io.miragon.example.formcentric.adapter.out.notification.novu

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "novu")
data class NovuProperties @ConstructorBinding constructor(
    val api: ApiConfig,
    val subscriber: SubscriberConfig
) {
    data class ApiConfig(
        val url: String,
        val key: String,
    )

    data class SubscriberConfig(
        val email: String,
        val credentials: CredentialsConfig,
    )

    data class CredentialsConfig(
        val msTeams: MsTeamsConfig
    )

    data class MsTeamsConfig(
        val webhook: String
    )
}
