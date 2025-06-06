package com.desarrollomovil.demo12.model

import java.time.LocalDateTime

data class UsuarioModel(
    val id: String? = null,
    val nombreUsuario: String,
    val curpUsuario: String,
    val apellidoMaternoUsuario: String,
    val apellidoPaternoUsuario: String,
    val correo: String,
    val telefono: String,
    val contraseña: String,
    val fechaCreacion: LocalDateTime = LocalDateTime.now(),
    val fechaActualizacion: LocalDateTime = LocalDateTime.now(),
    val activo: Boolean = true
) {
    fun nombreCompleto(): String {
        return "$nombreUsuario $apellidoPaternoUsuario $apellidoMaternoUsuario"
    }

    fun esValido(): Boolean {
        return nombreUsuario.isNotBlank() && 
               curpUsuario.matches(Regex("^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}$")) &&
               correo.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$")) &&
               telefono.matches(Regex("^[0-9]{10}$"))
    }

    fun actualizarFechaModificacion() {
        // En Kotlin, las data classes son inmutables, así que necesitaríamos crear una nueva instancia
        // Esta función es más para demostración de la lógica de negocio
    }
} 