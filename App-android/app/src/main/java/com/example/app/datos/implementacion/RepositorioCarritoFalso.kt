package com.example.app.datos

import com.example.app.modelos.Producto

object RepositorioCarritoFalso {

    private val carrito = mutableListOf<Producto>()

    fun agregar(producto: Producto) {

        val existente = carrito.find { it.id == producto.id }

        if (existente != null) {

            existente.cantidad++

        } else {

            producto.cantidad = 1
            carrito.add(producto)

        }

    }

    fun obtenerProductos(): MutableList<Producto> {
        return carrito
    }

    fun eliminar(producto: Producto) {
        carrito.remove(producto)
    }

    fun total(): Double {

        return carrito.sumOf {

            it.precioOferta * it.cantidad

        }

    }

    fun vaciar() {
        carrito.clear()
    }

}