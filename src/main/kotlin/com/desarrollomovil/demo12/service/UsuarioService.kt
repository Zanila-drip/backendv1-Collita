package com.desarrollomovil.demo12.service

import com.desarrollomovil.demo12.dto.UsuarioDto
import com.desarrollomovil.demo12.entity.UsuarioEntity
import com.desarrollomovil.demo12.repository.UsuarioRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

class UsuarioDuplicadoException(message: String) : RuntimeException(message)

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun findAll(): List<UsuarioDto> {
        return usuarioRepository.findAll().map { it.toDto() }
    }

    fun findById(id: String): Optional<UsuarioDto> {
        return usuarioRepository.findById(id).map { it.toDto() }
    }

    fun login(correo: String, curpUsuario: String): UsuarioDto {
        println("Buscando usuario con correo: $correo")
        val usuario = usuarioRepository.findByCorreo(correo.lowercase().trim())
            ?: throw RuntimeException("Usuario no encontrado")
        
        println("Usuario encontrado: ${usuario.correo}")
        println("Comparando CURP: ${usuario.curpUsuario} con ${curpUsuario}")
        
        if (usuario.curpUsuario.uppercase().trim() != curpUsuario.uppercase().trim()) {
            throw RuntimeException("CURP incorrecto")
        }
        
        return usuario.toDto()
    }

    @Transactional
    fun create(usuarioDto: UsuarioDto): UsuarioDto {
        try {
            val entity = UsuarioEntity(
                nombreUsuario = usuarioDto.nombreUsuario,
                curpUsuario = usuarioDto.curpUsuario,
                apellidoMaternoUsuario = usuarioDto.apellidoMaternoUsuario,
                apellidoPaternoUsuario = usuarioDto.apellidoPaternoUsuario,
                correo = usuarioDto.correo,
                telefono = usuarioDto.telefono,
                contraseña = "TEMP_PASSWORD" // En producción, esto debería ser hasheado
            )
            return usuarioRepository.save(entity).toDto()
        } catch (e: DuplicateKeyException) {
            val errorMessage = when {
                e.message?.contains("curpUsuario") == true -> 
                    "Ya existe un usuario con el CURP ${usuarioDto.curpUsuario}"
                e.message?.contains("correo") == true -> 
                    "Ya existe un usuario con el correo ${usuarioDto.correo}"
                else -> "Ya existe un usuario con los mismos datos"
            }
            throw UsuarioDuplicadoException(errorMessage)
        }
    }

    @Transactional
    fun update(id: String, usuarioDto: UsuarioDto): Optional<UsuarioDto> {
        return usuarioRepository.findById(id).map { existingEntity ->
            try {
                val updatedEntity = existingEntity.copy(
                    nombreUsuario = usuarioDto.nombreUsuario,
                    curpUsuario = usuarioDto.curpUsuario,
                    apellidoMaternoUsuario = usuarioDto.apellidoMaternoUsuario,
                    apellidoPaternoUsuario = usuarioDto.apellidoPaternoUsuario,
                    correo = usuarioDto.correo,
                    telefono = usuarioDto.telefono
                )
                usuarioRepository.save(updatedEntity).toDto()
            } catch (e: DuplicateKeyException) {
                val errorMessage = when {
                    e.message?.contains("curpUsuario") == true -> 
                        "Ya existe un usuario con el CURP ${usuarioDto.curpUsuario}"
                    e.message?.contains("correo") == true -> 
                        "Ya existe un usuario con el correo ${usuarioDto.correo}"
                    else -> "Ya existe un usuario con los mismos datos"
                }
                throw UsuarioDuplicadoException(errorMessage)
            }
        }
    }

    @Transactional
    fun delete(id: String): Boolean {
        return if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    private fun UsuarioEntity.toDto() = UsuarioDto(
        id = id,
        nombreUsuario = nombreUsuario,
        curpUsuario = curpUsuario,
        apellidoMaternoUsuario = apellidoMaternoUsuario,
        apellidoPaternoUsuario = apellidoPaternoUsuario,
        correo = correo,
        telefono = telefono
    )
} 