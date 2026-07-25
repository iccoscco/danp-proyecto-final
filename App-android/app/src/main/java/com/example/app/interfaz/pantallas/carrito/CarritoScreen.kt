package com.example.app.interfaz.pantallas.carrito

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app.datos.repositorios.RepositorioCarrito
import com.example.app.datos.SesionManager
import com.example.app.datos.red.RetrofitClient
import com.example.app.modelos.PedidoCreate
import com.example.app.modelos.PedidoDetalleCreate
import com.example.app.modelos.Producto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun CarritoScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sesionManager = remember { SesionManager(context) }
    var cargandoPedido by remember { mutableStateOf(false) }

    val carritoItems = RepositorioCarrito.productos

    fun realizarPedido() {
        if (carritoItems.isEmpty()) return
        
        cargandoPedido = true
        scope.launch {
            try {
                val token = sesionManager.token.first()
                if (token == null) {
                    Toast.makeText(context, "Inicia sesión para comprar", Toast.LENGTH_SHORT).show()
                    cargandoPedido = false
                    return@launch
                }

                val detalles = carritoItems.map {
                    PedidoDetalleCreate(
                        productoId = it.id,
                        cantidad = it.cantidad,
                        precioUnitario = it.precio
                    )
                }

                val request = PedidoCreate(detalles = detalles, estado = "Pagado")
                val response = RetrofitClient.pedidoApi.crearPedido("Bearer $token", request)

                if (response.isSuccessful) {
                    RepositorioCarrito.vaciar()
                    Toast.makeText(context, "¡Pedido Pagado! Entrega en 20min", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error al procesar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                cargandoPedido = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🛒 Mi Carrito",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (carritoItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Tu carrito está vacío.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carritoItems) { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp)) {
                            AsyncImage(
                                model = producto.imagenUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text(text = "Precio: S/. ${String.format("%.2f", producto.precio)}", style = MaterialTheme.typography.bodyMedium)
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    IconButton(
                                        onClick = { if (producto.cantidad > 1) producto.cantidad-- },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Text("-", style = MaterialTheme.typography.headlineSmall)
                                    }
                                    
                                    Text(
                                        text = producto.cantidad.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(horizontal = 12.dp)
                                    )
                                    
                                    IconButton(
                                        onClick = { producto.cantidad++ },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Text("+", style = MaterialTheme.typography.headlineSmall)
                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = "S/. ${String.format("%.2f", producto.precio * producto.cantidad)}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                
                                TextButton(
                                    onClick = {
                                        RepositorioCarrito.eliminar(producto)
                                    },
                                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("🗑 Eliminar")
                                }
                            }
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Productos:", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "${carritoItems.sumOf { it.cantidad }}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "TOTAL:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        text = "S/. ${String.format("%.2f", RepositorioCarrito.obtenerTotal())}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !cargandoPedido,
                onClick = { realizarPedido() }
            ) {
                if (cargandoPedido) CircularProgressIndicator(size = 24.dp, color = MaterialTheme.colorScheme.onPrimary)
                else Text("💳 Pagar ahora", style = MaterialTheme.typography.titleMedium)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !cargandoPedido,
                onClick = {
                    RepositorioCarrito.vaciar()
                    Toast.makeText(context, "Carrito cancelado", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("❌ Cancelar carrito", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
}
