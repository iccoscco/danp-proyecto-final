package com.example.app.datos.repositorios

import com.example.app.datos.red.ClienteLoginRequest
import com.example.app.datos.red.ClienteRegistroRequest
import com.example.app.datos.red.RetrofitClient

class RepositorioAutenticacion {

    suspend fun iniciarSesion(correo: String, contrasena: String): Result<String> {
        return try {
            val response = RetrofitClient.api.login(ClienteLoginRequest(correo, contrasena))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.access_token)
            } else {
                val error = response.errorBody()?.string() ?: "Credenciales inválidas"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registrar(nombre: String, correo: String, contrasena: String): Result<Boolean> {
        return try {
            val response = RetrofitClient.api.registrar(ClienteRegistroRequest(nombre, correo, contrasena))
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                val error = response.errorBody()?.string() ?: "Error al registrarse"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
