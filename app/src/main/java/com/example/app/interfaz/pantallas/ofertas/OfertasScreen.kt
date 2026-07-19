package com.example.app.interfaz.pantallas.ofertas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.datos.RepositorioProductosFalso
import com.example.app.interfaz.componentes.TarjetaProducto

@Composable
fun OfertasScreen() {

    val ofertas = RepositorioProductosFalso.obtenerProductos()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🔥 Ofertas Especiales",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Productos con descuento y próximos a vencer.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(ofertas) { producto ->
                TarjetaProducto(producto)
            }

        }

    }

}