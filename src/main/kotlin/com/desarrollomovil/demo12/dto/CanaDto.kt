package com.desarrollomovil.demo12.dto

import java.time.LocalDate
import java.time.LocalTime

data class CanaDto(
    val id: String? = null,
    val idUsuario: String,
    val horaInicioUsuario: LocalTime,
    val horaFinalUsuario: LocalTime,
    val cantidadCanaUsuario: Double,
    val fecha: LocalDate,
    val fechaUsuario: LocalDate,
    val pdfUsuario: String? = null
) 