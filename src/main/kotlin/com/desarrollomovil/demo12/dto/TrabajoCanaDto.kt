package com.desarrollomovil.demo12.dto

import java.time.LocalDate

data class TrabajoCanaDto(
    val id: String? = null,
    val idUsuario: String,
    val nombreUsuario: String? = null,
    val fechaTrabajoCaña: LocalDate,
    val accionesCana: List<String>? = null,
    val totalCañaTrabajoCaña: Double = 0.0,
    val detallesAcciones: List<Map<String, Any>> = emptyList()
) 