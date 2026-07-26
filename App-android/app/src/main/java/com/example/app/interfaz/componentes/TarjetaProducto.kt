package com.example.app.interfaz.componentes

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app.datos.repositorios.RepositorioCarrito
import com.example.app.modelos.Producto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun TarjetaProducto(producto: Producto) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val sesionManager = remember { com.example.app.datos.SesionManager(context) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = producto.imagenUrl,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Vence: ${producto.fechaVencimiento}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "S/. ${String.format("%.2f", producto.precio)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        scope.launch {
                            val token = sesionManager.token.first()
                            RepositorioCarrito.agregar(producto, token)
                        }
                        Toast.makeText(
                            context,
                            "🛒 ${producto.nombre} agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text("Agregar al carrito", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
