package io.miragon.example.formcentric.domain

data class Email(
    val address: String,
    val subject: String,
    val body: String
)