package io.miragon.example.formcentric.domain

import java.time.LocalDate

data class BusinessTripRequest(
    val name: String,
    val email: String,
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
    val cost: Double,
    val destination: String,
    val approval: Boolean,
    val comment: String? = null
)