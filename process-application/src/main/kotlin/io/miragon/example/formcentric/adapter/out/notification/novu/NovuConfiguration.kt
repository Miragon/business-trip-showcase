package io.miragon.example.formcentric.adapter.out.notification.novu

import co.novu.Novu
import co.novu.NovuConfig
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan
class NovuConfiguration(
    private val novuProperties: NovuProperties
) {

    @Bean(initMethod = "setUp", destroyMethod = "cleanUp")
    fun novuClient() = NovuClient(
        novuProperties,
        novuApiV1(),
        novuApiV2()
    )

    @Bean
    fun novuApiV1(): Novu {
        val config = NovuConfig(
            backendUrl = "${novuProperties.api.url}/v1/",
            apiKey = novuProperties.api.key
        )

        return Novu(config)
    }

    @Bean
    fun novuApiV2(): Novu {
        val config = NovuConfig(
            backendUrl = "${novuProperties.api.url}/v2/",
            apiKey = novuProperties.api.key
        )

        return Novu(config)
    }
}