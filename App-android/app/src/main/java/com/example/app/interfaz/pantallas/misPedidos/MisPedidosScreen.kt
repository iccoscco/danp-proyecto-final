package com.example.app.interfaz.pantallas.misPedidos

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app.datos.SesionManager
import com.example.app.datos.red.RetrofitClient
import com.example.app.modelos.PedidoResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MisPedidosScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sesionManager = remember { SesionManager(context) }
    
    var pedidos by remember { mutableStateOf<List<PedidoResponse>>(emptyList()) }
    var cargando by remember { mutableStateOf(false) }

    fun cargarPedidos() {
        cargando = true
        scope.launch {
            try {
                val token = sesionManager.token.first()
                if (token == null) {
                    cargando = false
                    return@launch
                }
                val response = RetrofitClient.pedidoApi.obtenerPedidos("Bearer $token")
                if (response.isSuccessful) {
                    pedidos = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                cargando = false
            }
        }
    }

    fun marcarRecibido(id: Int) {
        scope.launch {
            try {
                val token = sesionManager.token.first() ?: return@launch
                val response = RetrofitClient.pedidoApi.actualizarPedido(
                    "Bearer $token", 
                    id, 
                    mapOf("estado" to "Completado")
                )
                if (response.isSuccessful) {
                    Toast.makeText(context, "¡Pedido recibido!", Toast.LENGTH_SHORT).show()
                    cargarPedidos()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        cargarPedidos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "📦 Mis Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (pedidos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Aún no has realizado pedidos.")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(pedidos) { pedido ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = pedido.numeroPedido,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    color = when(pedido.estado) {
                                        "Pagado" -> Color(0xFFE3F2FD)
                                        "Completado" -> Color(0xFFE8F5E9)
                                        "Cancelado" -> Color(0xFFFFEBEE)
                                        else -> Color(0xFFFFF3E0)
                                    },
                                    shape = RoundedCornerShape(99.dp)
                                ) {
                                    Text(
                                        text = pedido.estado,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = when(pedido.estado) {
                                            "Pagado" -> Color(0xFF1976D2)
                                            "Completado" -> Color(0xFF2E7D32)
                                            "Cancelado" -> Color(0xFFC62828)
                                            else -> Color(0xFFE65100)
                                        }
                                    )
                                }
                            }

                            if (pedido.estado == "Pagado") {
                                Text(
                                    text = "🛵 Entrega en 20min aprox.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Mostrar items del pedido
                            pedido.detalles.forEach { detalle ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = detalle.imagenUrl,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp).clip(RoundedCornerShape(4.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "${detalle.cantidad}x ${detalle.nombreItem}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "S/. ${String.format("%.2f", detalle.precioUnitario * detalle.cantidad)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total: S/. ${String.format("%.2f", pedido.total)}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                
                                if (pedido.estado == "Pagado") {
                                    Button(
                                        onClick = { marcarRecibido(pedido.id) },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Pedido recibido")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
