package com.desarrollomovil.demo12.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "trabajos_cana")
data class TrabajoCanaEntity(
    @Id
    val id: String? = null,
    val idUsuario: String,
    val fechaTrabajoCaña: LocalDate,
    val accionesCana: List<String>? = null, // Lista de IDs de las acciones de caña
    val totalCañaTrabajoCaña: Double = 0.0
) 