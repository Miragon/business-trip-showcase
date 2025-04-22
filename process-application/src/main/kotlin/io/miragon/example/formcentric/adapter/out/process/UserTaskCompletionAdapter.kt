package io.miragon.example.formcentric.adapter.out.process

import dev.bpmcrafters.processengineapi.task.CompleteTaskCmd
import dev.bpmcrafters.processengineapi.task.UserTaskCompletionApi
import io.miragon.example.formcentric.application.port.out.UserTaskCompletionPort
import org.springframework.stereotype.Component

@Component
class UserTaskCompletionAdapter(
    private val taskCompletionApi: UserTaskCompletionApi
) : UserTaskCompletionPort {

    override fun complete(taskId: String, approval: Boolean): Boolean {
        taskCompletionApi.completeTask(
            CompleteTaskCmd(
                taskId = taskId,
                payloadSupplier = { mapOf("approval" to approval) }
            )
        ).get()
        return true
    }

}