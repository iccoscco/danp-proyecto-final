package com.example.app.datos.red

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ClienteApi {
    @POST("auth/cliente/register")
    suspend fun registrar(@Body request: ClienteRegistroRequest): Response<ClienteResponse>

    @POST("auth/cliente/login")
    suspend fun login(@Body request: ClienteLoginRequest): Response<TokenResponse>
}
