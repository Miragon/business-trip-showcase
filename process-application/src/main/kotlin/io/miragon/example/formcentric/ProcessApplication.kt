package io.miragon.example.formcentric

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProcessApplication

fun main(args: Array<String>) {
    runApplication<ProcessApplication>(*args)
}