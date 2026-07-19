package com.example.app.datos

import com.example.app.R
import com.example.app.modelos.Producto

object RepositorioProductosFalso {

    fun obtenerProductos() = listOf(

        Producto(
            id = 1,
            nombre = "Pan Integral",
            categoria = "Panadería",
            precioOriginal = 8.50,
            precioOferta = 5.50,
            descuento = "35% OFF",
            imagen = R.drawable.pan
        ),

        Producto(
            id = 2,
            nombre = "Ensalada César",
            categoria = "Comidas",
            precioOriginal = 15.00,
            precioOferta = 8.50,
            descuento = "Último día",
            imagen = R.drawable.ensalada
        ),

        Producto(
            id = 3,
            nombre = "Café Americano",
            categoria = "Bebidas",
            precioOriginal = 10.00,
            precioOferta = 6.00,
            descuento = "40% OFF",
            imagen = R.drawable.cafe
        ),

        Producto(
            id = 4,
            nombre = "Pizza Familiar",
            categoria = "Comidas",
            precioOriginal = 35.00,
            precioOferta = 20.00,
            descuento = "43% OFF",
            imagen = R.drawable.pizza
        ),

        Producto(
            id = 5,
            nombre = "Manzanas",
            categoria = "Frutas",
            precioOriginal = 9.00,
            precioOferta = 5.00,
            descuento = "Próximo a vencer",
            imagen = R.drawable.manzana
        )

    )

}