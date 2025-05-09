package io.miragon.example.formcentric.domain

import java.time.LocalDate

data class BusinessTripRequest(
    val salutation: String? = null,
    val title: String? = null,
    val firstname: String,
    val lastname: String,
    val mail: String,
    val iban: String? = null,
    val tripType: String? = null,
    val comment: String? = null,
    val startPoint: String? = null,
    val startDate: LocalDate,
    val startTime: String? = null,
    val address: Address? = null,
    val destinations: List<Destination>? = null,
    val finalDestination: Destination,
) {

    data class Address(
        val street: String,
        val city: String,
        val zipCode: String,
    )

    data class Destination(
        val city: String,
        val date: LocalDate,
        val time: String? = null,
    )

}
