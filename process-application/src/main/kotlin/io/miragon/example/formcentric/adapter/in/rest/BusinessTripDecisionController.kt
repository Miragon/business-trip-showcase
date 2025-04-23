package io.miragon.example.formcentric.adapter.`in`.rest

import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDto
import io.miragon.example.formcentric.adapter.`in`.rest.model.FormDataDto
import io.miragon.example.formcentric.application.port.`in`.BusinessTripDecisionUseCase
import io.miragon.example.formcentric.application.port.`in`.GetFormDataUseCase
import io.miragon.example.formcentric.application.port.`in`.TaskListUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
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
        try {
            val form = this.getFormDataUseCase.getFormData()
            val data = this.taskListUseCase.getVariables(taskId)
            return ResponseEntity.ok(
                FormDataDto(
                    schema = form.schema,
                    uiSchema = form.uiSchema,
                    data = BusinessTripRequestDto(
                        name = data.name,
                        email = data.email,
                        dateFrom = data.dateFrom,
                        dateTo = data.dateTo,
                        cost = data.cost,
                        destination = data.destination,
                        approval = false,
                        comment = ""
                    )
                )
            )
        } catch (_: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/complete")
    fun complete(@PathVariable taskId: String, @RequestBody request: BusinessTripRequestDto): ResponseEntity<Boolean> {
        try {
            val result = businessTripDecisionUseCase.decide(
                taskId,
                BusinessTripRequest(
                    name = request.name,
                    email = request.email,
                    dateFrom = request.dateFrom,
                    dateTo = request.dateTo,
                    cost = request.cost,
                    destination = request.destination,
                    approval = request.approval,
                    comment = request.comment,
                )
            )
            taskListUseCase.completeTask(taskId)
            return ResponseEntity.ok(result)
        } catch (_: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

}