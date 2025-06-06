package com.desarrollomovil.demo12.controller

import com.desarrollomovil.demo12.dto.CanaDto
import com.desarrollomovil.demo12.service.CanaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@RestController
@RequestMapping("/cana")
class CanaController(private val canaService: CanaService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<CanaDto>> = ResponseEntity.ok(canaService.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<CanaDto> =
        canaService.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/fecha/{fecha}")
    fun getCanaByFecha(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<List<CanaDto>> {
        return ResponseEntity.ok(canaService.getCanaByFecha(fecha))
    }

    @GetMapping("/usuario/{idUsuario}")
    fun getCanaByUsuario(
        @PathVariable idUsuario: String,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<List<CanaDto>> {
        return ResponseEntity.ok(canaService.getCanaByUsuario(idUsuario, fecha))
    }

    @PostMapping
    fun create(@Valid @RequestBody dto: CanaDto): ResponseEntity<CanaDto> =
        ResponseEntity.status(HttpStatus.CREATED).body(canaService.create(dto))

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody dto: CanaDto): ResponseEntity<CanaDto> =
        canaService.update(id, dto)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> =
        if (canaService.delete(id)) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
} 