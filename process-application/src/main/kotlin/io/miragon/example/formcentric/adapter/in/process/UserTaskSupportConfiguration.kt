package io.miragon.example.formcentric.adapter.`in`.process

import dev.bpmcrafters.processengineapi.CommonRestrictions
import dev.bpmcrafters.processengineapi.task.TaskSubscriptionApi
import dev.bpmcrafters.processengineapi.task.support.UserTaskSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserTaskSupportConfiguration {

    @Bean
    fun createAndRegisterUserTaskSupport(taskSubscriptionApi: TaskSubscriptionApi): UserTaskSupport {
        val support = UserTaskSupport()
        support.subscribe(
            taskSubscriptionApi,
            CommonRestrictions
                .builder()
                .withProcessDefinitionKey("Dienstreisenantrag")
                .build(),
            null,
            null,
        )
        return support
    }
}