package io.miragon.example.formcentric.adapter.`in`.rest.model

import java.time.LocalDate

data class BusinessTripRequestDetailedDto(
    val salutation: String,
    val title: String?,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val iban: String,
    val tripType: String,
    val comment: String?,
    val startPoint: String,
    val startDate: LocalDate,
    val startTime: String?,
    val address: Address?,
    val destinations: List<Destination>,
    val finalDestination: Destination,
    val approval: Boolean = false,
    val approvalComment: String = "",
) {

    data class Address(
        val street: String?,
        val city: String?,
        val zipCode: String?,
    )

    data class Destination(
        val city: String,
        val date: LocalDate,
        val time: String?,
    )

}
