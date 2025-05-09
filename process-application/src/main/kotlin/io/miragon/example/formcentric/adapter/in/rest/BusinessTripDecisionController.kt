package io.miragon.example.formcentric.adapter.`in`.rest

import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDetailedDto
import io.miragon.example.formcentric.adapter.`in`.rest.model.FormDataDto
import io.miragon.example.formcentric.application.port.`in`.BusinessTripDecisionUseCase
import io.miragon.example.formcentric.application.port.`in`.GetFormDataUseCase
import io.miragon.example.formcentric.application.port.`in`.TaskListUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest/task/{taskId}")
class BusinessTripDecisionController(
    private val getFormDataUseCase: GetFormDataUseCase,
    private val taskListUseCase: TaskListUseCase,
    private val businessTripDecisionUseCase: BusinessTripDecisionUseCase,
) {

    @GetMapping("/load")
    fun load(@PathVariable taskId: String): ResponseEntity<FormDataDto> {
        return try {
            val form = this.getFormDataUseCase.getFormData()
            val data = this.taskListUseCase.getVariables(taskId)
            ResponseEntity.ok(
                FormDataDto(
                    schema = form.schema,
                    uiSchema = form.uiSchema,
                    data = BusinessTripRequestDetailedDto(
                        salutation = data.salutation ?: "",
                        title = data.title ?: "",
                        firstName = data.firstname,
                        lastName = data.lastname,
                        mail = data.mail,
                        iban = data.iban ?: "",
                        tripType = data.tripType ?: "",
                        comment = data.comment ?: "",
                        startPoint = data.startPoint ?: "",
                        startDate = data.startDate,
                        startTime = null,
                        address = null,
                        destinations = data.destinations?.map { destination ->
                            BusinessTripRequestDetailedDto.Destination(
                                city = destination.city,
                                date = destination.date,
                                time = null
                            )
                        } ?: emptyList(),
                        finalDestination = BusinessTripRequestDetailedDto.Destination(
                            city = data.finalDestination.city,
                            date = data.finalDestination.date,
                            time = null
                        )
                    )
                )
            )
        } catch (_: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/complete")
    fun complete(
        @PathVariable taskId: String,
        @RequestBody request: BusinessTripRequestDetailedDto
    ): ResponseEntity<Boolean> {
        return try {
            val result = businessTripDecisionUseCase.decide(
                taskId,
                approval = request.approval,
            )
            taskListUseCase.completeTask(taskId)
            ResponseEntity.ok(result)
        } catch (_: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

}