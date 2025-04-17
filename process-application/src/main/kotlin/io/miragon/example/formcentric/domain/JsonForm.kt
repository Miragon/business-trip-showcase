package io.miragon.example.formcentric.domain

data class JsonForm(
    val schema: Map<String, Any>,
    val uiSchema: Map<String, Any>,
)