package com.desarrollomovil.demo12.dto

import java.time.LocalDate

data class ResumenTrabajosDto(
    val idUsuario: String,
    val nombreUsuario: String,
    val curpUsuario: String,
    val totalDiasTrabajados: Int,
    val totalCaña: Double,
    val trabajosPorDia: List<TrabajoPorDiaDto>
)

data class TrabajoPorDiaDto(
    val fecha: LocalDate,
    val totalCaña: Double,
    val numeroTrabajos: Int,
    val trabajosCana: List<String>
) 