package com.desarrollomovil.demo12.dto

data class UsuarioDto(
    val id: String? = null,
    val nombreUsuario: String,
    val curpUsuario: String,
    val apellidoMaternoUsuario: String,
    val apellidoPaternoUsuario: String,
    val correo: String,
    val telefono: String
    // No incluimos la contraseña en el DTO por seguridad
) 