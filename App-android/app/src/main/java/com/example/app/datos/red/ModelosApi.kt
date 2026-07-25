package com.example.app.datos.red

data class ClienteRegistroRequest(
    val nombre: String,
    val correo: String,
    val contrasena: String
)

data class ClienteLoginRequest(
    val correo: String,
    val contrasena: String
)

data class TokenResponse(
    val access_token: String
)

data class ClienteResponse(
    val id: String,
    val nombre: String,
    val correo: String,
    val estado: String
)
