package io.miragon.example.formcentric.adapter.out.process

import dev.bpmcrafters.processengineapi.task.CompleteTaskCmd
import dev.bpmcrafters.processengineapi.task.UserTaskCompletionApi
import io.miragon.example.formcentric.application.port.out.UserTaskCompletionPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class UserTaskCompletionAdapter(
    private val taskCompletionApi: UserTaskCompletionApi
) : UserTaskCompletionPort {

    private val log = KotlinLogging.logger {}

    override fun complete(taskId: String, request: BusinessTripRequest): Boolean {
        try {
            taskCompletionApi.completeTask(
                CompleteTaskCmd(
                    taskId,
                    payload = mapOf<String, Any>("request" to request)
                )
            ).get()
            return true
        } catch (e: ClassCastException) {
            log.error(e.message, e)
            return true
        }
    }
}