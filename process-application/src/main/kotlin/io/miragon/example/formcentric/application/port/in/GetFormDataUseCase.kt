package io.miragon.example.formcentric.application.port.`in`

import io.miragon.example.formcentric.domain.JsonForm

interface GetFormDataUseCase {
    fun getFormData(): JsonForm
}