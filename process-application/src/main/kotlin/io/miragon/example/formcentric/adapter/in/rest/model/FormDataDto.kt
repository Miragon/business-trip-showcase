package io.miragon.example.formcentric.adapter.`in`.rest.model

data class FormDataDto(
    val schema: Map<String, Any>,
    val uiSchema: Map<String, Any>,
    val data: BusinessTripRequestDetailedDto
)