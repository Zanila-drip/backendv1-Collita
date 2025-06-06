package com.desarrollomovil.demo12.service

import com.desarrollomovil.demo12.dto.ResumenTrabajosDto
import com.desarrollomovil.demo12.dto.TrabajoCanaDto
import com.desarrollomovil.demo12.dto.TrabajoPorDiaDto
import com.desarrollomovil.demo12.entity.TrabajoCanaEntity
import com.desarrollomovil.demo12.repository.TrabajoCanaRepository
import com.desarrollomovil.demo12.repository.UsuarioRepository
import com.desarrollomovil.demo12.repository.CanaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.Optional
import java.util.NoSuchElementException

@Service
class TrabajoCanaService(
    private val trabajoCanaRepository: TrabajoCanaRepository,
    private val canaRepository: CanaRepository,
    private val usuarioRepository: UsuarioRepository
) {

    fun findAll(): List<TrabajoCanaDto> = trabajoCanaRepository.findAll().map { it.toDto() }

    fun findById(id: String): Optional<TrabajoCanaDto> = trabajoCanaRepository.findById(id).map { it.toDto() }

    fun findByIdUsuario(idUsuario: String): List<TrabajoCanaDto> = 
        trabajoCanaRepository.findByIdUsuario(idUsuario).map { it.toDto() }

    fun findByFecha(fecha: LocalDate): List<TrabajoCanaDto> {
        val trabajos = trabajoCanaRepository.findByFechaTrabajoCaña(fecha)
        return trabajos.map { trabajo ->
            val usuario = usuarioRepository.findById(trabajo.idUsuario).orElse(null)
            val accionesCana = trabajo.accionesCana?.mapNotNull { id ->
                canaRepository.findById(id).orElse(null)
            } ?: emptyList()
            
            TrabajoCanaDto(
                id = trabajo.id,
                idUsuario = trabajo.idUsuario,
                nombreUsuario = usuario?.nombreUsuario,
                fechaTrabajoCaña = trabajo.fechaTrabajoCaña,
                accionesCana = trabajo.accionesCana ?: emptyList(),
                totalCañaTrabajoCaña = trabajo.totalCañaTrabajoCaña,
                detallesAcciones = accionesCana.map { accion ->
                    mapOf(
                        "horaInicio" to accion.horaInicioUsuario.toString(),
                        "horaFinal" to accion.horaFinalUsuario.toString(),
                        "cantidad" to accion.cantidadCanaUsuario
                    )
                }
            )
        }
    }

    fun findByIdUsuarioAndFecha(idUsuario: String, fecha: LocalDate): List<TrabajoCanaDto> = 
        trabajoCanaRepository.findByIdUsuarioAndFechaTrabajoCaña(idUsuario, fecha).map { it.toDto() }

    @Transactional
    fun create(dto: TrabajoCanaDto): TrabajoCanaDto {
        // Calcular el total de caña sumando todas las acciones
        val totalCaña = dto.accionesCana?.mapNotNull { actionId ->
            canaRepository.findById(actionId).orElse(null)?.cantidadCanaUsuario
        }?.sum() ?: 0.0

        val entity = TrabajoCanaEntity(
            idUsuario = dto.idUsuario,
            fechaTrabajoCaña = dto.fechaTrabajoCaña,
            accionesCana = dto.accionesCana,
            totalCañaTrabajoCaña = totalCaña
        )
        return trabajoCanaRepository.save(entity).toDto()
    }

    @Transactional
    fun update(id: String, dto: TrabajoCanaDto): Optional<TrabajoCanaDto> {
        return trabajoCanaRepository.findById(id).map { existing ->
            // Calcular el total de caña sumando todas las acciones
            val totalCaña = dto.accionesCana?.mapNotNull { actionId ->
                canaRepository.findById(actionId).orElse(null)?.cantidadCanaUsuario
            }?.sum() ?: 0.0

            val updated = existing.copy(
                idUsuario = dto.idUsuario,
                fechaTrabajoCaña = dto.fechaTrabajoCaña,
                accionesCana = dto.accionesCana,
                totalCañaTrabajoCaña = totalCaña
            )
            trabajoCanaRepository.save(updated).toDto()
        }
    }

    @Transactional
    fun delete(id: String): Boolean {
        return if (trabajoCanaRepository.existsById(id)) {
            trabajoCanaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    private fun TrabajoCanaEntity.toDto() = TrabajoCanaDto(
        id = id,
        idUsuario = idUsuario,
        fechaTrabajoCaña = fechaTrabajoCaña,
        accionesCana = accionesCana ?: emptyList(),
        totalCañaTrabajoCaña = totalCañaTrabajoCaña
    )

    fun getTrabajosByFecha(fecha: LocalDate): List<TrabajoCanaDto> {
        val trabajos = trabajoCanaRepository.findByFechaTrabajoCaña(fecha)
        
        return trabajos.map { trabajo ->
            val usuario = usuarioRepository.findById(trabajo.idUsuario).orElse(null)
            val acciones = canaRepository.findAllById(trabajo.accionesCana ?: emptyList())
            
            val detallesAcciones = acciones.map { accion ->
                mapOf(
                    "horaInicio" to accion.horaInicioUsuario,
                    "horaFinal" to accion.horaFinalUsuario,
                    "cantidad" to accion.cantidadCanaUsuario
                )
            }
            
            val totalCaña = acciones.sumOf { it.cantidadCanaUsuario }
            
            TrabajoCanaDto(
                id = trabajo.id,
                idUsuario = trabajo.idUsuario,
                nombreUsuario = usuario?.nombreUsuario,
                fechaTrabajoCaña = trabajo.fechaTrabajoCaña,
                accionesCana = trabajo.accionesCana ?: emptyList(),
                totalCañaTrabajoCaña = totalCaña,
                detallesAcciones = detallesAcciones
            )
        }
    }

    fun getResumenTrabajosUsuario(idUsuario: String): ResumenTrabajosDto {
        val usuario = usuarioRepository.findById(idUsuario).orElseThrow { NoSuchElementException("Usuario no encontrado") }
        val trabajos = trabajoCanaRepository.findByIdUsuario(idUsuario)
        
        val trabajosPorDia = trabajos.groupBy { it.fechaTrabajoCaña }
            .map { (fecha, trabajosDia) ->
                TrabajoPorDiaDto(
                    fecha = fecha,
                    totalCaña = trabajosDia.sumOf { it.totalCañaTrabajoCaña },
                    numeroTrabajos = trabajosDia.size,
                    trabajosCana = trabajosDia.mapNotNull { it.id }
                )
            }
            .sortedByDescending { it.fecha }

        return ResumenTrabajosDto(
            idUsuario = usuario.id!!,
            nombreUsuario = usuario.nombreUsuario,
            curpUsuario = usuario.curpUsuario,
            totalDiasTrabajados = trabajosPorDia.size,
            totalCaña = trabajosPorDia.sumOf { it.totalCaña },
            trabajosPorDia = trabajosPorDia
        )
    }

    fun getResumenTrabajosUsuarioPorRango(
        idUsuario: String,
        fechaInicio: LocalDate,
        fechaFin: LocalDate
    ): ResumenTrabajosDto {
        val usuario = usuarioRepository.findById(idUsuario).orElseThrow { NoSuchElementException("Usuario no encontrado") }
        val trabajos = trabajoCanaRepository.findByIdUsuarioAndFechaTrabajoCañaBetween(idUsuario, fechaInicio, fechaFin)
        
        val trabajosPorDia = trabajos.groupBy { it.fechaTrabajoCaña }
            .map { (fecha, trabajosDia) ->
                TrabajoPorDiaDto(
                    fecha = fecha,
                    totalCaña = trabajosDia.sumOf { it.totalCañaTrabajoCaña },
                    numeroTrabajos = trabajosDia.size,
                    trabajosCana = trabajosDia.mapNotNull { it.id }
                )
            }
            .sortedByDescending { it.fecha }

        return ResumenTrabajosDto(
            idUsuario = usuario.id!!,
            nombreUsuario = usuario.nombreUsuario,
            curpUsuario = usuario.curpUsuario,
            totalDiasTrabajados = trabajosPorDia.size,
            totalCaña = trabajosPorDia.sumOf { it.totalCaña },
            trabajosPorDia = trabajosPorDia
        )
    }

    fun createTrabajoCana(trabajoCanaDto: TrabajoCanaDto): TrabajoCanaDto {
        val trabajoCanaEntity = TrabajoCanaEntity(
            idUsuario = trabajoCanaDto.idUsuario,
            fechaTrabajoCaña = trabajoCanaDto.fechaTrabajoCaña,
            accionesCana = trabajoCanaDto.accionesCana
        )
        
        val savedTrabajo = trabajoCanaRepository.save(trabajoCanaEntity)
        
        val usuario = usuarioRepository.findById(savedTrabajo.idUsuario).orElse(null)
        val acciones = canaRepository.findAllById(savedTrabajo.accionesCana ?: emptyList())
        
        val detallesAcciones = acciones.map { accion ->
            mapOf(
                "horaInicio" to accion.horaInicioUsuario,
                "horaFinal" to accion.horaFinalUsuario,
                "cantidad" to accion.cantidadCanaUsuario
            )
        }
        
        val totalCaña = acciones.sumOf { it.cantidadCanaUsuario }
        
        return TrabajoCanaDto(
            id = savedTrabajo.id,
            idUsuario = savedTrabajo.idUsuario,
            nombreUsuario = usuario?.nombreUsuario,
            fechaTrabajoCaña = savedTrabajo.fechaTrabajoCaña,
            accionesCana = savedTrabajo.accionesCana ?: emptyList(),
            totalCañaTrabajoCaña = totalCaña,
            detallesAcciones = detallesAcciones
        )
    }
} 