package com.desarrollomovil.demo12.controller

import com.desarrollomovil.demo12.dto.ResumenTrabajosDto
import com.desarrollomovil.demo12.dto.TrabajoCanaDto
import com.desarrollomovil.demo12.service.TrabajoCanaService
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/trabajos-cana")
class TrabajoCanaController(private val trabajoCanaService: TrabajoCanaService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<TrabajoCanaDto>> = 
        ResponseEntity.ok(trabajoCanaService.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<TrabajoCanaDto> =
        trabajoCanaService.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/usuario/{idUsuario}")
    fun getByIdUsuario(@PathVariable idUsuario: String): ResponseEntity<List<TrabajoCanaDto>> =
        ResponseEntity.ok(trabajoCanaService.findByIdUsuario(idUsuario))

    @GetMapping("/fecha/{fecha}")
    fun getByFecha(@PathVariable fecha: LocalDate): ResponseEntity<List<TrabajoCanaDto>> =
        ResponseEntity.ok(trabajoCanaService.findByFecha(fecha))

    @GetMapping("/usuario/{idUsuario}/fecha/{fecha}")
    fun getByIdUsuarioAndFecha(
        @PathVariable idUsuario: String,
        @PathVariable fecha: LocalDate
    ): ResponseEntity<List<TrabajoCanaDto>> =
        ResponseEntity.ok(trabajoCanaService.findByIdUsuarioAndFecha(idUsuario, fecha))

    @PostMapping
    fun create(@Valid @RequestBody dto: TrabajoCanaDto): ResponseEntity<TrabajoCanaDto> =
        ResponseEntity.status(HttpStatus.CREATED).body(trabajoCanaService.create(dto))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @Valid @RequestBody dto: TrabajoCanaDto
    ): ResponseEntity<TrabajoCanaDto> =
        trabajoCanaService.update(id, dto)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> =
        if (trabajoCanaService.delete(id)) ResponseEntity.noContent().build() 
        else ResponseEntity.notFound().build()
} 