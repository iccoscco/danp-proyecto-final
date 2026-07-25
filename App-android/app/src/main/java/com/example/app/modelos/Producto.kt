package com.example.app.modelos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

data class Producto(

    val id: Int,

    val nombre: String,

    val categoria: String,

    val precioOriginal: Double,

    val precioOferta: Double,

    val descuento: String,

    val imagen: Int

) {

    var cantidad by mutableIntStateOf(1)

}