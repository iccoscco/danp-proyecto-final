package com.example.app.datos.red

import com.example.app.modelos.Producto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductoApi {
    @GET("productos/")
    suspend fun obtenerProductos(
        @Header("Authorization") token: String,
        @Query("buscar") buscar: String? = null
    ): Response<List<Producto>>
}
