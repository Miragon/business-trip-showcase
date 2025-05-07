package io.miragon.example.formcentric.adapter.`in`.process

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.bpmcrafters.processengineapi.CommonRestrictions
import dev.bpmcrafters.processengineapi.task.SubscribeForTaskCmd
import dev.bpmcrafters.processengineapi.task.TaskSubscriptionApi
import dev.bpmcrafters.processengineapi.task.TaskTerminationHandler
import dev.bpmcrafters.processengineapi.task.TaskType
import io.miragon.example.formcentric.application.port.`in`.NotifyReviewerUseCase
import io.miragon.example.formcentric.application.port.`in`.TaskListUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

//private val mapper = jacksonObjectMapper().apply {
//    registerModule(SimpleModule().apply {
//        addDeserializer(LocalDate::class.java, object : JsonDeserializer<LocalDate>() {
//            override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): LocalDate {
//                return LocalDate.parse(jp.text)
//            }
//        })
//    })
//}
//
@Component
@Suppress("unused")
class UserTaskHandler() {
    lateinit var taskListUseCase: TaskListUseCase

    lateinit var notifyReviewerUseCase: NotifyReviewerUseCase

    lateinit var taskSubscriptionApi: TaskSubscriptionApi

    private val log = KotlinLogging.logger {}

    @Autowired
    constructor(
        taskListUseCase: TaskListUseCase,
        notifyReviewerUseCase: NotifyReviewerUseCase,
        taskSubscriptionApi: TaskSubscriptionApi,
    ) : this() {
        this.taskListUseCase = taskListUseCase
        this.notifyReviewerUseCase = notifyReviewerUseCase
        this.taskSubscriptionApi = taskSubscriptionApi
        this.subscribe()
    }

    private fun subscribe() {
        taskSubscriptionApi.subscribeForTask(
            SubscribeForTaskCmd(
                CommonRestrictions
                    .builder()
                    .withProcessDefinitionKey("Dienstreisenantrag")
                    .build(),
                TaskType.USER,
                null,
                null,
                { taskInfo, variables ->
                    log.info { "[TaskHandler] received task ${taskInfo.taskId}." }
                    try {
                        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
                        val request = mapper.convertValue(variables["request"], BusinessTripRequest::class.java)
                        taskListUseCase.addTask(taskInfo.taskId, request)
                        notifyReviewerUseCase.sendNotification(taskInfo.taskId)
                    } catch (e: IllegalArgumentException) {
                        log.error(e) { "Failed to parse request!" }
                    } catch (e: Exception) {
                        log.error(e) { "Failed to send mail!" }
                    }
                },
                TaskTerminationHandler { taskInfo ->
                    log.info { "[TaskHandler] task $taskInfo terminated." }
                    // Remove the task if it gets terminated.
                    taskListUseCase.completeTask(taskInfo.taskId)
                }
            )
        ).get()
    }

}
