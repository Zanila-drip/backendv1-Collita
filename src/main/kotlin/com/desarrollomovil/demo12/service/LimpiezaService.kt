package com.desarrollomovil.demo12.service

import com.desarrollomovil.demo12.repository.CanaRepository
import com.desarrollomovil.demo12.repository.SesionRepository
import com.desarrollomovil.demo12.repository.TrabajoCanaRepository
import com.desarrollomovil.demo12.repository.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LimpiezaService(
    private val usuarioRepository: UsuarioRepository,
    private val canaRepository: CanaRepository,
    private val trabajoCanaRepository: TrabajoCanaRepository,
    private val sesionRepository: SesionRepository
) {
    @Transactional
    fun limpiarBaseDeDatos() {
        // Primero eliminamos las sesiones
        sesionRepository.deleteAll()
        
        // Luego los trabajos de caña
        trabajoCanaRepository.deleteAll()
        
        // Después las acciones de caña
        canaRepository.deleteAll()
        
        // Finalmente los usuarios
        usuarioRepository.deleteAll()
    }

    @Transactional
    fun limpiarDatosDePrueba() {
        // Eliminar usuarios de prueba (aquellos que no tienen CURP válida)
        val usuarios = usuarioRepository.findAll()
        val usuariosDePrueba = usuarios.filter { 
            !it.curpUsuario.matches(Regex("[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}"))
        }
        usuarioRepository.deleteAll(usuariosDePrueba)

        // Eliminar acciones de caña sin usuario válido
        val accionesCana = canaRepository.findAll()
        val accionesInvalidas = accionesCana.filter { accion ->
            !usuarioRepository.existsById(accion.idUsuario)
        }
        canaRepository.deleteAll(accionesInvalidas)

        // Eliminar trabajos de caña sin usuario válido o sin acciones
        val trabajosCana = trabajoCanaRepository.findAll()
        val trabajosInvalidos = trabajosCana.filter { trabajo ->
            !usuarioRepository.existsById(trabajo.idUsuario) ||
            trabajo.accionesCana?.isEmpty() == true
        }
        trabajoCanaRepository.deleteAll(trabajosInvalidos)

        // Eliminar sesiones sin trabajos válidos
        val sesiones = sesionRepository.findAll()
        val sesionesInvalidas = sesiones.filter { sesion ->
            sesion.trabajosCana.isEmpty() ||
            !trabajoCanaRepository.existsById(sesion.trabajosCana.first())
        }
        sesionRepository.deleteAll(sesionesInvalidas)
    }
} 