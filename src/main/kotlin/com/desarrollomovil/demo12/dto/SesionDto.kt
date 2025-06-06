package com.desarrollomovil.demo12.dto

import java.time.LocalDate

data class SesionDto(
    val id: String?,
    val fecha: LocalDate,
    val trabajosCana: List<String>,
    val totalEmpleados: Int,
    val totalCaña: Double,
    val activa: Boolean,
    val empleados: List<EmpleadoSesionDto>
)

data class EmpleadoSesionDto(
    val idUsuario: String,
    val nombreUsuario: String,
    val curpUsuario: String,
    val apellidoPaternoUsuario: String,
    val apellidoMaternoUsuario: String,
    val totalCaña: Double,
    val trabajosCana: List<String>
) 