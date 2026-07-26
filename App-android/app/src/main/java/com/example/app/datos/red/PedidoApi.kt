package com.example.app.datos.red

import com.example.app.modelos.PedidoResponse
import com.example.app.modelos.PedidoCreate
import retrofit2.Response
import retrofit2.http.*

interface PedidoApi {
    @GET("pedidos/")
    suspend fun obtenerPedidos(
        @Header("Authorization") token: String
    ): Response<List<PedidoResponse>>

    @POST("pedidos/")
    suspend fun crearPedido(
        @Header("Authorization") token: String,
        @Body pedido: PedidoCreate
    ): Response<PedidoResponse>

    @PUT("pedidos/{id}")
    suspend fun actualizarPedido(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body updates: Any // Usamos Any para soportar Map o PedidoUpdate
    ): Response<PedidoResponse>
}
