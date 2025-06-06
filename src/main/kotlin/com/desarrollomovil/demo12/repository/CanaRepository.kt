package com.desarrollomovil.demo12.repository

import com.desarrollomovil.demo12.entity.CanaEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CanaRepository : MongoRepository<CanaEntity, String> {
    fun findByFecha(fecha: LocalDate): List<CanaEntity>
    fun findByFechaAndIdUsuario(fecha: LocalDate, idUsuario: String): List<CanaEntity>
} 