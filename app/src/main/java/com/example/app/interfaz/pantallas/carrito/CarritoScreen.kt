package com.example.app.interfaz.pantallas.carrito

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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

    val total = carrito.sumOf { it.precioOferta }

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

            Text("Tu carrito está vacío.")

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(carrito) { producto ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                producto.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text("S/. ${producto.precioOferta}")

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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "TOTAL: S/. $total",
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
                        "Pedido realizado correctamente",
                        Toast.LENGTH_LONG
                    ).show()

                }
            ) {

                Text("Finalizar pedido")

            }

        }

    }

}