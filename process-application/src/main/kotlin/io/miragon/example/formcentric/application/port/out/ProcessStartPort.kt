package io.miragon.example.formcentric.application.port.out

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface ProcessStartPort {
    fun startProcess(request: BusinessTripRequest): String
}