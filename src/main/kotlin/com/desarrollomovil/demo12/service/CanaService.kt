package com.desarrollomovil.demo12.service

import com.desarrollomovil.demo12.dto.CanaDto
import com.desarrollomovil.demo12.entity.CanaEntity
import com.desarrollomovil.demo12.repository.CanaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.Optional

@Service
class CanaService(private val canaRepository: CanaRepository) {

    fun findAll(): List<CanaDto> = canaRepository.findAll().map { it.toDto() }

    fun findById(id: String): Optional<CanaDto> = canaRepository.findById(id).map { it.toDto() }

    fun getCanaByUsuario(idUsuario: String, fecha: LocalDate): List<CanaDto> {
        return canaRepository.findByFechaAndIdUsuario(fecha, idUsuario).map { it.toDto() }
    }

    fun getCanaByFecha(fecha: LocalDate): List<CanaDto> {
        return canaRepository.findByFecha(fecha).map { it.toDto() }
    }

    @Transactional
    fun create(dto: CanaDto): CanaDto {
        val entity = CanaEntity(
            idUsuario = dto.idUsuario,
            horaInicioUsuario = dto.horaInicioUsuario,
            horaFinalUsuario = dto.horaFinalUsuario,
            cantidadCanaUsuario = dto.cantidadCanaUsuario,
            fecha = dto.fecha,
            fechaUsuario = dto.fechaUsuario,
            pdfUsuario = dto.pdfUsuario
        )
        return canaRepository.save(entity).toDto()
    }

    @Transactional
    fun update(id: String, dto: CanaDto): Optional<CanaDto> {
        return canaRepository.findById(id).map { existing ->
            val updated = existing.copy(
                idUsuario = dto.idUsuario,
                horaInicioUsuario = dto.horaInicioUsuario,
                horaFinalUsuario = dto.horaFinalUsuario,
                cantidadCanaUsuario = dto.cantidadCanaUsuario,
                fecha = dto.fecha,
                fechaUsuario = dto.fechaUsuario,
                pdfUsuario = dto.pdfUsuario
            )
            canaRepository.save(updated).toDto()
        }
    }

    @Transactional
    fun delete(id: String): Boolean {
        return if (canaRepository.existsById(id)) {
            canaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    @Transactional
    fun createCana(canaDto: CanaDto): CanaDto {
        val canaEntity = CanaEntity(
            idUsuario = canaDto.idUsuario,
            horaInicioUsuario = canaDto.horaInicioUsuario,
            horaFinalUsuario = canaDto.horaFinalUsuario,
            cantidadCanaUsuario = canaDto.cantidadCanaUsuario,
            fecha = canaDto.fecha,
            fechaUsuario = canaDto.fechaUsuario
        )
        return canaRepository.save(canaEntity).toDto()
    }

    private fun CanaEntity.toDto() = CanaDto(
        id = id,
        idUsuario = idUsuario,
        horaInicioUsuario = horaInicioUsuario,
        horaFinalUsuario = horaFinalUsuario,
        cantidadCanaUsuario = cantidadCanaUsuario,
        fecha = fecha,
        fechaUsuario = fechaUsuario,
        pdfUsuario = pdfUsuario
    )
} 