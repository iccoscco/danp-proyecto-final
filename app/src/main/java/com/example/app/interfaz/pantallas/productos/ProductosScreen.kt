package com.example.app.interfaz.pantallas.productos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.datos.RepositorioProductosFalso
import com.example.app.interfaz.componentes.TarjetaProducto

@Composable
fun ProductosScreen() {

    var busqueda by remember { mutableStateOf("") }

    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    val categorias = listOf(
        "Todos",
        "Panadería",
        "Bebidas",
        "Frutas",
        "Comidas",
        "Lácteos",
        "Postres",
        "Snacks"
    )

    val productos = RepositorioProductosFalso.obtenerProductos()

    val productosFiltrados = productos.filter {

        (categoriaSeleccionada == "Todos" ||
                it.categoria == categoriaSeleccionada)

                &&

                it.nombre.contains(
                    busqueda,
                    ignoreCase = true
                )

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🛍 Productos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = {
                busqueda = it
            },
            label = {
                Text("Buscar productos")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.horizontalScroll(
                rememberScrollState()
            )
        ) {

            categorias.forEach { categoria ->

                FilterChip(

                    selected = categoria == categoriaSeleccionada,

                    onClick = {

                        categoriaSeleccionada = categoria

                    },

                    label = {

                        Text(categoria)

                    }

                )

                Spacer(modifier = Modifier.width(8.dp))

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "⭐ Recomendados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(productos.take(3)) { producto ->

                Card(
                    modifier = Modifier.width(180.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "S/. ${producto.precioOferta}"
                        )

                    }

                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Todos los productos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(productosFiltrados) { producto ->

                TarjetaProducto(producto)

            }

        }

    }

}