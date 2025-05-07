package io.miragon.example.formcentric.adapter.`in`.rest

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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

    private var requestHash = ""

    @PostMapping("/start", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun startProcess(@RequestBody request: BusinessTripRequestDto): ResponseEntity<Unit> {
        log.info { "[${this::class.simpleName}] Request received" }
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
        } catch (e: Exception) {
            log.error(e) { "[${this::class.simpleName}] Request failed" }
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/start", consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun startProcess(@RequestBody body: String): ResponseEntity<Unit> {
        log.info { "[${this::class.simpleName}] Request received" }

        val rh = body.hashCode().toString()
        if (this.requestHash == rh) {
            log.info { "[${this::class.simpleName}] Duplicate request received" }
            return ResponseEntity.ok().build()
        }

        this.requestHash = rh

        return try {
            val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
            val dto = mapper.readValue(body, BusinessTripRequestDto::class.java)
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
        } catch (e: Exception) {
            log.error(e) { "[${this::class.simpleName}] Request failed" }
            ResponseEntity.badRequest().build()
        }
    }
}