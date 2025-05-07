package io.miragon.example.formcentric.adapter.`in`.process

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.bpmcrafters.processengineapi.CommonRestrictions
import dev.bpmcrafters.processengineapi.task.SubscribeForTaskCmd
import dev.bpmcrafters.processengineapi.task.TaskSubscriptionApi
import dev.bpmcrafters.processengineapi.task.TaskType
import io.miragon.example.formcentric.application.port.`in`.NotifyReviewerUseCase
import io.miragon.example.formcentric.application.port.`in`.TaskListUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

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
                    log.info { "[${this::class.simpleName}] received task ${taskInfo.taskId}." }
                    try {
                        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
                        val request = mapper.convertValue(variables["request"], BusinessTripRequest::class.java)
                        taskListUseCase.addTask(taskInfo.taskId, request)
                        notifyReviewerUseCase.sendNotification(taskInfo.taskId)
                    } catch (e: IllegalArgumentException) {
                        log.error(e) { "[${this::class.simpleName}] Failed to parse request!" }
                    } catch (e: Exception) {
                        log.error(e) { "[${this::class.simpleName}] Failed to send mail!" }
                    }
                },
                termination = { log.info { "[${this::class.simpleName}] terminated" } },
            )
        ).get()
    }

}
