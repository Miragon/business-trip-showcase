package io.miragon.example.formcentric.application.port.out

interface UserTaskCompletionPort {
    fun complete(taskId: String, approval: Boolean): Boolean
}