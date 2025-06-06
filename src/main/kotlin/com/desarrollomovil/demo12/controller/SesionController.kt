package com.desarrollomovil.demo12.controller

import com.desarrollomovil.demo12.dto.SesionDto
import com.desarrollomovil.demo12.service.SesionService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/sesiones")
class SesionController(private val sesionService: SesionService) {

    @GetMapping("/actual")
    fun getSesionActual(): ResponseEntity<SesionDto> {
        return sesionService.getSesionActual()?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/fecha/{fecha}")
    fun getSesionByFecha(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<SesionDto> {
        return sesionService.getSesionByFecha(fecha)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping("/crear/{fecha}")
    fun crearSesion(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<SesionDto> {
        return ResponseEntity.ok(sesionService.crearSesion(fecha))
    }
} 