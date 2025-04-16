package io.miragon.example.formcentric.adapter.`in`.process

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.bpmcrafters.processengineapi.CommonRestrictions
import dev.bpmcrafters.processengineapi.task.SubscribeForTaskCmd
import dev.bpmcrafters.processengineapi.task.TaskSubscriptionApi
import dev.bpmcrafters.processengineapi.task.TaskType
import io.miragon.example.formcentric.application.port.`in`.SendMailUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

val mapper = jacksonObjectMapper().apply {
    registerModule(SimpleModule().apply {
        addDeserializer(LocalDate::class.java, object : JsonDeserializer<LocalDate>() {
            override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): LocalDate {
                return LocalDate.parse(jp.text)
            }
        })
    })
}

@Component
class UserTaskHandler() {
    lateinit var sendMailUseCase: SendMailUseCase

    lateinit var taskSubscriptionApi: TaskSubscriptionApi;

    private val handledUserTasks: MutableList<String> = mutableListOf()

    private val log = KotlinLogging.logger {}

    @Autowired
    constructor(
        sendMailUseCase: SendMailUseCase,
        taskSubscriptionApi: TaskSubscriptionApi,
    ) : this() {
        this.sendMailUseCase = sendMailUseCase
        this.taskSubscriptionApi = taskSubscriptionApi
        this.subscribe()
    }

    private fun subscribe() {
        taskSubscriptionApi.subscribeForTask(
            SubscribeForTaskCmd(
                CommonRestrictions.builder().build(),
                TaskType.USER,
                null,
                null,
                action = fun(taskInfo, variables) {
                    if (taskInfo.meta["processDefinitionKey"] != "Dienstreisenantrag"
                        && handledUserTasks.contains(taskInfo.taskId)
                    ) {
                        return;
                    }
                    log.info { "TaskHandler received task ${taskInfo.taskId} with meta ${taskInfo.meta}." }
                    try {
                        val request = mapper.convertValue(variables["request"], BusinessTripRequest::class.java)
                        sendMailUseCase.sendMail(request.email, taskInfo.taskId)
                        handledUserTasks.add(taskInfo.taskId);
                    } catch (e: IllegalArgumentException) {
                        log.error(e) { "Failed to parse request!" }
                    } catch (e: Exception) {
                        log.error(e) { "Failed to send mail!" }
                    }
                },
                termination = fun(_) {}
            )
        ).get()
    }

}