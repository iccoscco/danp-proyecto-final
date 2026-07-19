package com.example.app.interfaz.pantallas.productos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.datos.RepositorioProductosFalso
import com.example.app.interfaz.componentes.TarjetaProducto

@Composable
fun ProductosScreen() {

    var busqueda by remember {
        mutableStateOf("")
    }

    var categoriaSeleccionada by remember {
        mutableStateOf("Todos")
    }

    val productos = RepositorioProductosFalso.obtenerProductos()

    val categorias = listOf(
        "Todos",
        "Panadería",
        "Bebidas",
        "Frutas",
        "Comidas"
    )

    val productosFiltrados = productos.filter { producto ->

        val coincideBusqueda =
            producto.nombre.contains(busqueda, ignoreCase = true)

        val coincideCategoria =
            categoriaSeleccionada == "Todos" ||
                    producto.categoria == categoriaSeleccionada

        coincideBusqueda && coincideCategoria

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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = {
                busqueda = it
            },
            label = {
                Text("Buscar producto")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            categorias.forEach { categoria ->

                AssistChip(
                    onClick = {
                        categoriaSeleccionada = categoria
                    },
                    label = {
                        Text(categoria)
                    }
                )

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(productosFiltrados) { producto ->

                TarjetaProducto(producto)

            }

        }

    }

}