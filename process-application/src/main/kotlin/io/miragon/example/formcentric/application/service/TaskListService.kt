package io.miragon.example.formcentric.application.service

import io.miragon.example.formcentric.application.port.`in`.TaskListUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.stereotype.Service

@Service
class TaskListService() : TaskListUseCase {

    private val tasks: MutableMap<String, BusinessTripRequest> = mutableMapOf()

    override fun addTask(taskId: String, variables: BusinessTripRequest) {
        tasks[taskId] = variables
    }

    override fun completeTask(taskId: String) {
        tasks.remove(taskId)
    }

    override fun getVariables(taskId: String): BusinessTripRequest {
        val data = tasks[taskId]
        if (data == null) {
            throw IllegalArgumentException("Task with id $taskId does not exist")
        }
        return data
    }
}