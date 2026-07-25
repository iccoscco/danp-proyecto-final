package com.example.app.modelos

import com.google.gson.annotations.SerializedName

data class Oferta(
    val id: Int,
    val nombre: String,
    val descuento: Double,
    @SerializedName("fecha_inicio")
    val fechaInicio: String,
    @SerializedName("fecha_fin")
    val fechaFin: String,
    val estado: String,
    @SerializedName("producto_id")
    val productoId: Int,
    @SerializedName("imagen_url")
    val imagenUrl: String?,
    @SerializedName("nombre_producto")
    val nombreProducto: String?,
    @SerializedName("precio_original")
    val precioOriginal: Double?,
    @SerializedName("stock_producto")
    val stockProducto: Int?,
    // Este campo lo obtendremos del producto asociado si es necesario, 
    // pero el backend OfertaResponse ya trae los datos básicos.
    // Para la fecha de vencimiento, necesitaremos que el backend la incluya o hacer un join extra.
    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String? = null
)
