package io.miragon.example.formcentric.application.port.`in`

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface GetTaskDataUseCase {
    fun getTaskData(taskId: String): BusinessTripRequest
}