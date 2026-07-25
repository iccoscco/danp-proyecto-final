package com.example.app.datos.red

import com.example.app.modelos.Oferta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OfertaApi {
    @GET("ofertas/")
    suspend fun obtenerOfertas(
        @Header("Authorization") token: String,
        @Query("buscar") buscar: String? = null
    ): Response<List<Oferta>>
}
