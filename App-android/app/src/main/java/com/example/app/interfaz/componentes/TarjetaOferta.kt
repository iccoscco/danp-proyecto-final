package com.example.app.interfaz.componentes

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app.datos.repositorios.RepositorioCarrito
import com.example.app.modelos.Oferta

@Composable
fun TarjetaOferta(oferta: Oferta) {
    val context = LocalContext.current
    val precioOriginal = oferta.precioOriginal ?: 0.0
    val descuento = oferta.descuento
    val precioFinal = precioOriginal * (1 - descuento / 100)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Box {
                AsyncImage(
                    model = oferta.imagenUrl,
                    contentDescription = oferta.nombreProducto,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Badge de descuento
                Surface(
                    color = Color(0xFFE53935),
                    shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = "-${descuento.toInt()}%",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = oferta.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = oferta.nombreProducto ?: "Producto",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (oferta.fechaVencimiento != null) {
                    Text(
                        text = "Vence: ${oferta.fechaVencimiento}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFD32F2F)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "S/. ${String.format("%.2f", precioOriginal)}",
                        style = MaterialTheme.typography.bodyMedium,
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "S/. ${String.format("%.2f", precioFinal)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        // Convertir Oferta a Producto para el carrito (o manejar ambos en el repo)
                        // Para simplificar, agregamos como producto usando el ID de producto
                        val p = com.example.app.modelos.Producto(
                            id = oferta.productoId,
                            nombre = oferta.nombreProducto ?: oferta.nombre,
                            categoria = "Oferta",
                            precio = precioFinal,
                            stock = oferta.stockProducto ?: 1,
                            fechaVencimiento = oferta.fechaVencimiento ?: "",
                            imagenUrl = oferta.imagenUrl
                        )
                        RepositorioCarrito.agregar(p)
                        Toast.makeText(
                            context,
                            "🔥 Oferta agregada!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text("Aprovechar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
