package io.miragon.example.formcentric.adapter.out.process

import dev.bpmcrafters.processengineapi.process.StartProcessApi
import dev.bpmcrafters.processengineapi.process.StartProcessByDefinitionCmd
import io.miragon.example.formcentric.application.port.out.ProcessStartPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Component

@Component
class ProcessStarterAdapter(
    private val startProcessApi: StartProcessApi
) : ProcessStartPort {

    override fun startProcess(request: BusinessTripRequest): String {
        val result = startProcessApi.startProcess(
            StartProcessByDefinitionCmd(
                definitionKey = "Dienstreisenantrag",
                payloadSupplier = { mapOf("request" to request) }
            )
        ).get()

        return result.instanceId
    }

}