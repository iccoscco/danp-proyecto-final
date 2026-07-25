package com.example.app.datos.repositorios

import androidx.compose.runtime.mutableStateListOf
import com.example.app.modelos.Producto

object RepositorioCarrito {
    private val _productos = mutableStateListOf<Producto>()
    val productos: List<Producto> get() = _productos

    fun agregar(producto: Producto) {
        val existente = _productos.find { it.id == producto.id }
        if (existente != null) {
            existente.cantidad++
        } else {
            // Aseguramos que la cantidad inicial sea 1 al agregar
            producto.cantidad = 1
            _productos.add(producto)
        }
    }

    fun eliminar(producto: Producto) {
        _productos.remove(producto)
    }

    fun vaciar() {
        _productos.clear()
    }

    fun obtenerTotal(): Double {
        return _productos.sumOf { it.precio * it.cantidad }
    }
}
