package com.desarrollomovil.demo12.controller

import com.desarrollomovil.demo12.dto.LoginRequestDto
import com.desarrollomovil.demo12.dto.UsuarioDto
import com.desarrollomovil.demo12.service.UsuarioService
import com.desarrollomovil.demo12.service.UsuarioDuplicadoException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping
    fun getAll(): List<UsuarioDto> {
        val usuarios = usuarioService.findAll()
        println("Usuarios encontrados: ${usuarios.size}")
        usuarios.forEach { usuario ->
            println("Usuario: ${usuario.nombreUsuario}, Correo: ${usuario.correo}, CURP: ${usuario.curpUsuario}")
        }
        return usuarios
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<Any> {
        return usuarioService.findById(id)
            .map { ResponseEntity.ok(it) as ResponseEntity<Any> }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun create(@RequestBody usuarioDto: UsuarioDto): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuarioDto))
        } catch (e: UsuarioDuplicadoException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<Any> {
        return try {
            println("Intento de login con correo: ${loginRequest.correo} y CURP: ${loginRequest.curpUsuario}")
            val usuario = usuarioService.login(loginRequest.correo, loginRequest.curpUsuario)
            println("Login exitoso para usuario: ${usuario.nombreUsuario}")
            ResponseEntity.ok(usuario)
        } catch (e: Exception) {
            println("Error en login: ${e.message}")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Credenciales inválidas"))
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody usuarioDto: UsuarioDto): ResponseEntity<Any> {
        return try {
            usuarioService.update(id, usuarioDto)
                .map { ResponseEntity.ok(it) as ResponseEntity<Any> }
                .orElse(ResponseEntity.notFound().build())
        } catch (e: UsuarioDuplicadoException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> {
        return if (usuarioService.delete(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 