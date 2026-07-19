package com.example.app.datos

import com.example.app.modelos.Producto

object RepositorioCarritoFalso {

    private val carrito = mutableListOf<Producto>()

    fun agregar(producto: Producto) {
        carrito.add(producto)
    }

    fun obtenerProductos(): List<Producto> {
        return carrito
    }

    fun eliminar(producto: Producto) {
        carrito.remove(producto)
    }

    fun total(): Double {
        return carrito.sumOf { it.precioOferta }
    }

    fun vaciar() {
        carrito.clear()
    }

}