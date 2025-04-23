package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.BusinessTripRequestUseCase
import io.miragon.example.formcentric.application.port.out.ProcessStartPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Service

@Service
class BusinessTripRequestService(
    private val processStartPort: ProcessStartPort
) : BusinessTripRequestUseCase {
    override fun request(request: BusinessTripRequest) {
        this.processStartPort.startProcess(request)
    }
}