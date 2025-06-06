package com.desarrollomovil.demo12.controller

import com.desarrollomovil.demo12.repository.CanaRepository
import com.desarrollomovil.demo12.repository.SesionRepository
import com.desarrollomovil.demo12.repository.TrabajoCanaRepository
import com.desarrollomovil.demo12.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/limpieza")
class LimpiezaController(
    private val usuarioRepository: UsuarioRepository,
    private val canaRepository: CanaRepository,
    private val trabajoCanaRepository: TrabajoCanaRepository,
    private val sesionRepository: SesionRepository
) {

    @DeleteMapping
    fun limpiarBaseDeDatos(): ResponseEntity<String> {
        // Primero eliminamos las sesiones
        sesionRepository.deleteAll()
        
        // Luego los trabajos de caña
        trabajoCanaRepository.deleteAll()
        
        // Después las acciones de caña
        canaRepository.deleteAll()
        
        // Finalmente los usuarios
        usuarioRepository.deleteAll()
        
        return ResponseEntity.ok("Base de datos limpiada exitosamente")
    }
    @GetMapping("/prueba")
    fun prueba(): String = "ok"
}