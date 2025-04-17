package io.miragon.example.formcentric.application.port.out

interface TaskDataPort {
    fun getData(taskId: String): Map<String, Any>
}