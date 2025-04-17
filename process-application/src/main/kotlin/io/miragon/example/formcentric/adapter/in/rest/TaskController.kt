package io.miragon.example.formcentric.adapter.`in`.rest

import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDto
import io.miragon.example.formcentric.adapter.`in`.rest.model.FormDataDto
import io.miragon.example.formcentric.application.port.`in`.CompleteUserTaskUseCase
import io.miragon.example.formcentric.application.port.`in`.GetFormDataUseCase
import io.miragon.example.formcentric.application.port.`in`.GetTaskDataUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest/task/{taskId}")
class TaskController(
    private val getFormDataUseCase: GetFormDataUseCase,
    private val getTaskDataUseCase: GetTaskDataUseCase,
    private val completeUserTaskUseCase: CompleteUserTaskUseCase,
) {

    @GetMapping("/load")
    fun load(@PathVariable taskId: String): ResponseEntity<FormDataDto> {
        val form = this.getFormDataUseCase.getFormData()
        val data = this.getTaskDataUseCase.getTaskData(taskId)
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
    }

    @PostMapping("/complete")
    fun complete(@PathVariable taskId: String, @RequestBody request: BusinessTripRequestDto): ResponseEntity<Unit> {
        completeUserTaskUseCase.complete(
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

        return ResponseEntity.ok().build()
    }

}