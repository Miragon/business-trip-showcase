package io.miragon.example.formcentric.application.port.`in`

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface StartProcessUseCase {
    fun startProcess(request: BusinessTripRequest)
}