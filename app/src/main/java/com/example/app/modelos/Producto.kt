package com.example.app.modelos

data class Producto(

    val id: Int,

    val nombre: String,

    val categoria: String,

    val precioOriginal: Double,

    val precioOferta: Double,

    val descuento: String,

    val imagen: Int

)