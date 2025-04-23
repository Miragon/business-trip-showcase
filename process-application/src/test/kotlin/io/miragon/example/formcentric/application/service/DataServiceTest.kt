package io.miragon.example.formcentric.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.miragon.example.formcentric.domain.JsonForm
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DataServiceTest {

    private lateinit var dataService: DataService
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper()
        dataService = DataService(objectMapper)
    }

    @Test
    fun `getFormData should load and parse schema files correctly`() {
        // When
        val result: JsonForm = dataService.getFormData()

        // Then
        assertNotNull(result)
        assertNotNull(result.schema)
        assertNotNull(result.uiSchema)

        // Verify schema contains expected structure
        val schema = result.schema
        assertTrue(schema.containsKey("type"))
        assertTrue(schema.containsKey("properties"))

        // Verify uiSchema contains expected structure
        val uiSchema = result.uiSchema
        assertTrue(uiSchema.containsKey("type"))
        assertTrue(uiSchema.containsKey("elements"))
    }

}