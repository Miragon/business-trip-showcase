package io.miragon.example.formcentric.adapter.`in`.rest

import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDto
import io.miragon.example.formcentric.application.port.`in`.BusinessTripRequestUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/process")
class BusinessTripRequestController(
    private val businessTripRequestUseCase: BusinessTripRequestUseCase
) {

    @PostMapping("/start")
    fun startProcess(@RequestBody request: BusinessTripRequestDto): ResponseEntity<Unit> {

        try {
            businessTripRequestUseCase.request(
                BusinessTripRequest(
                    name = request.name,
                    email = request.email,
                    dateFrom = request.dateFrom,
                    dateTo = request.dateTo,
                    cost = request.cost,
                    destination = request.destination,
                    approval = false
                )
            )
            return ResponseEntity.ok().build()
        } catch (_: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }
}