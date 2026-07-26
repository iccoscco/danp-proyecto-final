package com.example.app.modelos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.google.gson.annotations.SerializedName

data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    @SerializedName("fecha_vencimiento")
    val fechaVencimiento: String,
    @SerializedName("imagen_url")
    val imagenUrl: String?
)
