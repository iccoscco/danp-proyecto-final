package com.example.app.modelos

import com.google.gson.annotations.SerializedName

data class PedidoCreate(
    val detalles: List<PedidoDetalleCreate>,
    val estado: String = "Pendiente"
)

data class PedidoDetalleCreate(
    @SerializedName("producto_id")
    val productoId: Int? = null,
    @SerializedName("oferta_id")
    val ofertaId: Int? = null,
    val cantidad: Int,
    @SerializedName("precio_unitario")
    val precioUnitario: Double
)

data class PedidoResponse(
    val id: Int,
    @SerializedName("numero_pedido")
    val numeroPedido: String,
    @SerializedName("cliente_id")
    val clienteId: String,
    @SerializedName("cliente_nombre")
    val clienteNombre: String?,
    val fecha: String,
    val total: Double,
    val estado: String,
    val detalles: List<PedidoDetalleResponse>
)

data class PedidoDetalleResponse(
    val id: Int,
    @SerializedName("producto_id")
    val productoId: Int?,
    @SerializedName("oferta_id")
    val ofertaId: Int?,
    val cantidad: Int,
    @SerializedName("precio_unitario")
    val precioUnitario: Double,
    @SerializedName("nombre_item")
    val nombreItem: String?,
    @SerializedName("imagen_url")
    val imagenUrl: String?
)
