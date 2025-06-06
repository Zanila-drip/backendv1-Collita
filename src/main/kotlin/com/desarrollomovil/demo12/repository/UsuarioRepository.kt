package com.desarrollomovil.demo12.repository

import com.desarrollomovil.demo12.entity.UsuarioEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : MongoRepository<UsuarioEntity, String> {
    fun findByCorreo(correo: String): UsuarioEntity?
    fun findByCurpUsuario(curpUsuario: String): UsuarioEntity?
    fun existsByCorreo(correo: String): Boolean
    fun existsByCurpUsuario(curp: String): Boolean
} 