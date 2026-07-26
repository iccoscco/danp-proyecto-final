package com.example.app.datos.repositorios

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.example.app.datos.red.RetrofitClient
import com.example.app.modelos.PedidoCreate
import com.example.app.modelos.PedidoDetalleCreate
import com.example.app.modelos.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object RepositorioCarrito {
    private val _productos = mutableStateListOf<Producto>()
    private val _cantidades = mutableStateMapOf<Int, Int>()
    
    // Almacenamos el ID del pedido "Pendiente" en DB
    private var pedidoIdRemoto: Int? = null
    
    val productos: List<Producto> get() = _productos

    fun obtenerCantidad(productoId: Int): Int {
        return _cantidades[productoId] ?: 1
    }

    fun agregar(producto: Producto, token: String?) {
        val existente = _productos.find { it.id == producto.id }
        if (existente != null) {
            val nuevaCantidad = (_cantidades[producto.id] ?: 1) + 1
            _cantidades[producto.id] = nuevaCantidad
        } else {
            _productos.add(producto)
            _cantidades[producto.id] = 1
        }
        
        sincronizarConBackend(token)
    }

    fun incrementar(productoId: Int, token: String?) {
        val actual = _cantidades[productoId] ?: 1
        _cantidades[productoId] = actual + 1
        sincronizarConBackend(token)
    }

    fun decrementar(productoId: Int, token: String?) {
        val actual = _cantidades[productoId] ?: 1
        if (actual > 1) {
            _cantidades[productoId] = actual - 1
            sincronizarConBackend(token)
        }
    }

    fun eliminar(producto: Producto, token: String?) {
        _productos.remove(producto)
        _cantidades.remove(producto.id)
        sincronizarConBackend(token)
    }

    fun vaciarLocalmente() {
        _productos.clear()
        _cantidades.clear()
        pedidoIdRemoto = null
    }

    fun obtenerTotal(): Double {
        return _productos.sumOf { it.precio * (obtenerCantidad(it.id)) }
    }

    fun obtenerPedidoIdRemoto() = pedidoIdRemoto

    private fun sincronizarConBackend(token: String?) {
        if (token == null) return
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (_productos.isEmpty()) {
                    // Si vaciamos el carrito pero queda un pedido remoto pendiente,
                    // podríamos marcarlo como cancelado o borrarlo.
                    // Por simplicidad, si está vacío no sincronizamos o mandamos lista vacía.
                    return@launch
                }

                val detalles = _productos.map {
                    PedidoDetalleCreate(
                        productoId = it.id,
                        cantidad = obtenerCantidad(it.id),
                        precioUnitario = it.precio
                    )
                }

                if (pedidoIdRemoto == null) {
                    val request = PedidoCreate(detalles = detalles, estado = "Pendiente")
                    val response = RetrofitClient.pedidoApi.crearPedido("Bearer $token", request)
                    if (response.isSuccessful) {
                        pedidoIdRemoto = response.body()?.id
                    }
                } else {
                    // Actualizamos los detalles del pedido pendiente existente
                    val updateData = mapOf(
                        "detalles" to detalles,
                        "estado" to "Pendiente"
                    )
                    RetrofitClient.pedidoApi.actualizarPedido("Bearer $token", pedidoIdRemoto!!, updateData)
                }
            } catch (e: Exception) {
                android.util.Log.e("CARRITO", "Error sincronizando", e)
            }
        }
    }
}
