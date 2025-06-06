package com.desarrollomovil.demo12.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "sesiones")
data class SesionEntity(
    @Id
    val id: String? = null,
    val fecha: LocalDate,
    val trabajosCana: List<String>, // IDs de los trabajos de caña
    val totalEmpleados: Int,
    val totalCaña: Double,
    val activa: Boolean = true
) 