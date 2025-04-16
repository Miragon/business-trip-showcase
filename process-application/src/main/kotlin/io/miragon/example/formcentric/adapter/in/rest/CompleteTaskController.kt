package io.miragon.example.formcentric.adapter.`in`.rest

import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDto
import io.miragon.example.formcentric.application.port.`in`.CompleteUserTaskUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest/task")
class CompleteTaskController(
    private val completeUserTaskUseCase: CompleteUserTaskUseCase
) {
    @PostMapping("/complete/{taskId}")
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