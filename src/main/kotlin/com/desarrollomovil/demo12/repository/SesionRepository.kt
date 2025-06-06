package com.desarrollomovil.demo12.repository

import com.desarrollomovil.demo12.entity.SesionEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SesionRepository : MongoRepository<SesionEntity, String> {
    fun findByFecha(fecha: LocalDate): SesionEntity?
    fun findByActivaTrue(): SesionEntity?
} 