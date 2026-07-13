package com.example.app.datos

import com.example.app.R
import com.example.app.modelos.Producto

object RepositorioProductosFalso {

    fun obtenerProductos() = listOf(

        Producto(
            1,
            "Pan Integral",
            8.50,
            5.50,
            "35% OFF",
            R.drawable.pan
        ),

        Producto(
            2,
            "Ensalada César",
            15.00,
            8.50,
            "Último día",
            R.drawable.ensalada
        ),

        Producto(
            3,
            "Café Americano",
            10.00,
            6.00,
            "40% OFF",
            R.drawable.cafe
        ),

        Producto(
            4,
            "Pizza Familiar",
            35.00,
            20.00,
            "43% OFF",
            R.drawable.pizza
        ),

        Producto(
            5,
            "Manzanas",
            9.00,
            5.00,
            "Próximo a vencer",
            R.drawable.manzana
        )

    )

}