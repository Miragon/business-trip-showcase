package io.miragon.example.formcentric.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.miragon.example.formcentric.application.port.`in`.GetFormDataUseCase
import io.miragon.example.formcentric.application.port.`in`.GetTaskDataUseCase
import io.miragon.example.formcentric.application.port.out.TaskDataPort
import io.miragon.example.formcentric.domain.BusinessTripRequest
import io.miragon.example.formcentric.domain.JsonForm
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class DataService(
    private val objectMapper: ObjectMapper = ObjectMapper(),
    private val taskDataPort: TaskDataPort,
) : GetFormDataUseCase, GetTaskDataUseCase {

    private val jsonSchema = "Dienstreisenantrag.schema.json"

    private val uiSchema = "Dienstreisenantrag.uischema.json"

    override fun getFormData(): JsonForm {
        val schemaResource = ClassPathResource(jsonSchema)
        val uiResource = ClassPathResource(uiSchema)

        val jsonSchemaContent = objectMapper.readValue(schemaResource.inputStream, Map::class.java) as? Map<String, Any>
            ?: throw IllegalStateException("Unable to parse schema")
        val uiSchemaContent = objectMapper.readValue(uiResource.inputStream, Map::class.java) as? Map<String, Any>
            ?: throw IllegalStateException("Unable to parse ui schema")

        return JsonForm(
            schema = jsonSchemaContent,
            uiSchema = uiSchemaContent
        )
    }

    override fun getTaskData(taskId: String): BusinessTripRequest {
        val data = this.taskDataPort.getData(taskId)
        return objectMapper.convertValue(data, BusinessTripRequest::class.java)
    }
}