package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.StartProcessUseCase
import io.miragon.example.formcentric.application.port.out.ProcessStartPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Service

@Service
class ProcessStartService(
    private val processStartPort: ProcessStartPort
) : StartProcessUseCase {
    override fun startProcess(request: BusinessTripRequest) {
        this.processStartPort.startProcess(request)
    }
}