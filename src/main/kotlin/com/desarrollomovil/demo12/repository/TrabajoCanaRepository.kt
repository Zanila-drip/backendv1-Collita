package com.desarrollomovil.demo12.repository

import com.desarrollomovil.demo12.entity.TrabajoCanaEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TrabajoCanaRepository : MongoRepository<TrabajoCanaEntity, String> {
    fun findByFechaTrabajoCaña(fecha: LocalDate): List<TrabajoCanaEntity>
    fun findByIdUsuario(idUsuario: String): List<TrabajoCanaEntity>
    fun findByIdUsuarioAndFechaTrabajoCaña(idUsuario: String, fecha: LocalDate): List<TrabajoCanaEntity>
    fun findByIdUsuarioAndFechaTrabajoCañaBetween(
        idUsuario: String,
        fechaInicio: LocalDate,
        fechaFin: LocalDate
    ): List<TrabajoCanaEntity>
} 