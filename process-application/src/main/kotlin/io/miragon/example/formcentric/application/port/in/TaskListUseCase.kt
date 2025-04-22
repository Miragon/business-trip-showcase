package io.miragon.example.formcentric.application.port.`in`

import io.miragon.example.formcentric.domain.BusinessTripRequest

interface TaskListUseCase {
    fun addTask(taskId: String, variables: BusinessTripRequest)

    fun completeTask(taskId: String)

    fun getVariables(taskId: String): BusinessTripRequest
}