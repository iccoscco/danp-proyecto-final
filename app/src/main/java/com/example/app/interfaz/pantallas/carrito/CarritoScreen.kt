package com.example.app.interfaz.pantallas.carrito

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                text = "No hay productos en el carrito."
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(carrito) { producto ->

                    Card {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "Precio: S/. ${producto.precioOferta}"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {

                                    RepositorioCarritoFalso.eliminar(producto)

                                    carrito.remove(producto)

                                }
                            ) {

                                Text("Eliminar")

                            }

                        }

                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "TOTAL: S/. ${
                    carrito.sumOf { it.precioOferta }
                }",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    RepositorioCarritoFalso.vaciar()

                    carrito.clear()

                    Toast.makeText(
                        context,
                        "¡Pedido realizado con éxito!",
                        Toast.LENGTH_LONG
                    ).show()

                }
            ) {

                Text("Finalizar pedido")

            }

        }

    }

}