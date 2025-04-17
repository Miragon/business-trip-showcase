package io.miragon.example.formcentric.adapter.out.process

import dev.bpmcrafters.processengineapi.task.support.UserTaskSupport
import io.miragon.example.formcentric.application.port.out.TaskDataPort
import org.springframework.stereotype.Component

@Component
class UserTaskSupportAdapter(
    private val userTaskSupport: UserTaskSupport,
) : TaskDataPort {
    override fun getData(taskId: String): Map<String, Any>? {
        val data = userTaskSupport.getPayload(taskId) as Map<String, Map<String, Any>>
        return data["request"]
    }
}