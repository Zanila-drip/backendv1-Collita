package com.desarrollomovil.demo12.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Document(collection = "usuarios")
data class UsuarioEntity(
    @Id
    val id: String? = null,

    @field:NotBlank(message = "El nombre de usuario es obligatorio")
    @field:Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    val nombreUsuario: String,

    @field:NotBlank(message = "El CURP es obligatorio")
    @field:Pattern(regexp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}$", message = "El formato del CURP no es válido")
    @Indexed(unique = true)
    val curpUsuario: String,

    @field:NotBlank(message = "El apellido materno es obligatorio")
    val apellidoMaternoUsuario: String,

    @field:NotBlank(message = "El apellido paterno es obligatorio")
    val apellidoPaternoUsuario: String,

    @field:NotBlank(message = "El correo es obligatorio")
    @field:Email(message = "El formato del correo no es válido")
    @Indexed(unique = true)
    val correo: String,

    @field:Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
    val telefono: String,

    @field:NotBlank(message = "La contraseña es obligatoria")
    @field:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    val contraseña: String,

    val fechaCreacion: LocalDateTime = LocalDateTime.now(),
    val fechaActualizacion: LocalDateTime = LocalDateTime.now(),
    val activo: Boolean = true
) 