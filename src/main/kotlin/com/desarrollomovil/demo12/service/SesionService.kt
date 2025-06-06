package com.desarrollomovil.demo12.service

import com.desarrollomovil.demo12.dto.EmpleadoSesionDto
import com.desarrollomovil.demo12.dto.SesionDto
import com.desarrollomovil.demo12.entity.SesionEntity
import com.desarrollomovil.demo12.repository.SesionRepository
import com.desarrollomovil.demo12.repository.TrabajoCanaRepository
import com.desarrollomovil.demo12.repository.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class SesionService(
    private val sesionRepository: SesionRepository,
    private val trabajoCanaRepository: TrabajoCanaRepository,
    private val usuarioRepository: UsuarioRepository
) {
    
    fun getSesionByFecha(fecha: LocalDate): SesionDto? {
        val sesion = sesionRepository.findByFecha(fecha) ?: return null
        return mapToDto(sesion)
    }

    fun getSesionActual(): SesionDto? {
        val sesion = sesionRepository.findByActivaTrue() ?: return null
        return mapToDto(sesion)
    }

    @Transactional
    fun crearSesion(fecha: LocalDate): SesionDto {
        // Desactivar sesión anterior si existe
        sesionRepository.findByActivaTrue()?.let {
            sesionRepository.save(it.copy(activa = false))
        }

        // Obtener todos los trabajos de caña del día
        val trabajosCana = trabajoCanaRepository.findByFechaTrabajoCaña(fecha)
        val trabajosCanaIds = trabajosCana.map { it.id!! }

        // Obtener usuarios únicos
        val usuariosIds = trabajosCana.map { it.idUsuario }.distinct()
        val usuarios = usuarioRepository.findAllById(usuariosIds)

        // Crear la nueva sesión
        val sesionEntity = SesionEntity(
            fecha = fecha,
            trabajosCana = trabajosCanaIds,
            totalEmpleados = usuariosIds.size,
            totalCaña = trabajosCana.sumOf { it.totalCañaTrabajoCaña },
            activa = true
        )

        return mapToDto(sesionRepository.save(sesionEntity))
    }

    private fun mapToDto(sesion: SesionEntity): SesionDto {
        val trabajosCana = trabajoCanaRepository.findAllById(sesion.trabajosCana)
        val usuariosIds = trabajosCana.map { it.idUsuario }.distinct()
        val usuarios = usuarioRepository.findAllById(usuariosIds)

        val empleados = usuarios.map { usuario ->
            val trabajosUsuario = trabajosCana.filter { it.idUsuario == usuario.id }
            EmpleadoSesionDto(
                idUsuario = usuario.id!!,
                nombreUsuario = usuario.nombreUsuario,
                curpUsuario = usuario.curpUsuario,
                apellidoPaternoUsuario = usuario.apellidoPaternoUsuario,
                apellidoMaternoUsuario = usuario.apellidoMaternoUsuario,
                totalCaña = trabajosUsuario.sumOf { it.totalCañaTrabajoCaña },
                trabajosCana = trabajosUsuario.map { it.id!! }
            )
        }

        return SesionDto(
            id = sesion.id,
            fecha = sesion.fecha,
            trabajosCana = sesion.trabajosCana,
            totalEmpleados = sesion.totalEmpleados,
            totalCaña = sesion.totalCaña,
            activa = sesion.activa,
            empleados = empleados
        )
    }
} 