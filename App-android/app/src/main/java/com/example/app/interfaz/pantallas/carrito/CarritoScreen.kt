package com.example.app.interfaz.pantallas.carrito

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.datos.RepositorioCarritoFalso
import com.example.app.modelos.Producto

@Composable
fun CarritoScreen() {

    val context = LocalContext.current

    val carrito = remember {
        mutableStateListOf<Producto>().apply {
            addAll(RepositorioCarritoFalso.obtenerProductos())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🛒 Mi Carrito",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (carrito.isEmpty()) {

            Text(
                text = "Tu carrito está vacío.",
                style = MaterialTheme.typography.bodyLarge
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(carrito) { producto ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Precio: S/. ${String.format("%.2f", producto.precio)}"
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Button(
                                    onClick = {
                                        if (producto.cantidad > 1) {
                                            producto.cantidad--
                                        }
                                    }
                                ) {
                                    Text("-")
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = producto.cantidad.toString(),
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Button(
                                    onClick = {
                                        producto.cantidad++
                                    }
                                ) {
                                    Text("+")
                                }

                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Subtotal: S/. ${String.format("%.2f", producto.precio * producto.cantidad)}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedButton(
                                onClick = {
                                    RepositorioCarritoFalso.eliminar(producto)
                                    carrito.remove(producto)
                                }
                            ) {
                                Text("🗑 Eliminar")
                            }
                        }
                    }
                }
            }

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Productos: ${carrito.sumOf { it.cantidad }}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "TOTAL: S/. ${String.format("%.2f", carrito.sumOf { it.precio * it.cantidad })}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    RepositorioCarritoFalso.vaciar()

                    carrito.clear()

                    Toast.makeText(
                        context,
                        "¡Pedido realizado correctamente!",
                        Toast.LENGTH_LONG
                    ).show()

                }
            ) {

                Text("🛍 Finalizar compra")

            }

        }

    }

}