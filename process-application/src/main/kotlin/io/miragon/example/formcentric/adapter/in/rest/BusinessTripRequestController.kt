package io.miragon.example.formcentric.adapter.`in`.rest

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.miragon.example.formcentric.adapter.`in`.rest.model.BusinessTripRequestDetailedDto
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
        log.info { "[${this::class.simpleName}] Request received" }
        return try {
            businessTripRequestUseCase.request(
                BusinessTripRequest(
                    firstname = request.name.split(" ").first(),
                    lastname = request.name.split(" ").last(),
                    mail = request.email,
                    startDate = request.dateFrom,
                    finalDestination = BusinessTripRequest.Destination(
                        city = request.destination,
                        date = request.dateTo
                    ),
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
        return try {
            val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
            val dto = mapper.readValue(body, BusinessTripRequestDetailedDto::class.java)
            businessTripRequestUseCase.request(
                BusinessTripRequest(
                    salutation = dto.salutation,
                    title = dto.title,
                    firstname = dto.firstName,
                    lastname = dto.lastName,
                    mail = dto.mail,
                    iban = dto.iban,
                    tripType = dto.tripType,
                    comment = dto.comment,
                    startPoint = dto.startPoint,
                    startDate = dto.startDate,
                    startTime = dto.startTime,
                    address = BusinessTripRequest.Address(
                        street = dto.address?.street ?: "",
                        zipCode = dto.address?.zipCode ?: "",
                        city = dto.address?.city ?: "",
                    ),
                    destinations = dto.destinations.map { destination ->
                        BusinessTripRequest.Destination(
                            city = destination.city,
                            date = destination.date,
                            time = destination.time,
                        )
                    },
                    finalDestination = BusinessTripRequest.Destination(
                        city = dto.finalDestination.city,
                        date = dto.finalDestination.date,
                        time = dto.finalDestination.time,
                    )
                )
            )
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            log.error(e) { "[${this::class.simpleName}] Request failed" }
            ResponseEntity.badRequest().build()
        }
    }
}