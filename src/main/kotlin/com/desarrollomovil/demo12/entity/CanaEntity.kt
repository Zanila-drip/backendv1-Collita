package com.desarrollomovil.demo12.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalTime

@Document(collection = "cana")
data class CanaEntity(
    @Id
    val id: String? = null,
    val idUsuario: String,
    val horaInicioUsuario: LocalTime,
    val horaFinalUsuario: LocalTime,
    val cantidadCanaUsuario: Double,
    val fecha: LocalDate,
    val fechaUsuario: LocalDate,
    val pdfUsuario: String? = null
) 