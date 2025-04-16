package io.miragon.example.formcentric.adapter.`in`.rest.model

import java.time.LocalDate

data class BusinessTripRequestDto(
    val name: String,
    val email: String,
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val cost: Double,
    val destination: String,
    val approval: Boolean = false,
    val comment: String?,
)