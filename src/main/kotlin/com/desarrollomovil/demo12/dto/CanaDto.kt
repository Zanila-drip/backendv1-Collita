package com.desarrollomovil.demo12.dto

import java.time.LocalDate
import java.time.LocalTime

data class CanaDto(
    val id: String? = null,
    val idUsuario: String,
    val horaInicioUsuario: String,
    val horaFinalUsuario: String,
    val cantidadCanaUsuario: Double,
    val fecha: LocalDate,
    val fechaUsuario: String,
    val resumenCosecha: String? = null
) 