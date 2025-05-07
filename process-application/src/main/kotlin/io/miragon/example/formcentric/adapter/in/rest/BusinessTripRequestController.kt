package io.miragon.example.formcentric.adapter.`in`.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDto
import io.miragon.example.formcentric.application.port.`in`.BusinessTripRequestUseCase
import io.miragon.example.formcentric.domain.BusinessTripRequest
import mu.KotlinLogging
import org.springframework.http.MediaType
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

    private val log = KotlinLogging.logger {}

    @PostMapping("/start", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun startProcess(@RequestBody request: BusinessTripRequestDto): ResponseEntity<Unit> {
        return try {
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
            ResponseEntity.ok().build()
        } catch (_: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/start", consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun startProcess(@RequestBody body: String): ResponseEntity<Unit> {
        return try {
            val dto = jacksonObjectMapper().readValue(body, BusinessTripRequestDto::class.java)
            businessTripRequestUseCase.request(
                BusinessTripRequest(
                    name = dto.name,
                    email = dto.email,
                    dateFrom = dto.dateFrom,
                    dateTo = dto.dateTo,
                    cost = dto.cost,
                    destination = dto.destination,
                    approval = false
                )
            )
            ResponseEntity.ok().build()
        } catch (_: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}