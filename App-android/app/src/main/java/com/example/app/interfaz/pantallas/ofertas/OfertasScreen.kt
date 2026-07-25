package com.example.app.interfaz.pantallas.ofertas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

    val ofertas = RepositorioProductosFalso
        .obtenerProductos()
        .filter {

            it.descuento.contains("OFF") ||
                    it.descuento.contains("Último") ||
                    it.descuento.contains("Próximo")

        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🔥 Ofertas Especiales",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Aprovecha los alimentos con descuento antes de que se agoten.",
            style = MaterialTheme.typography.bodyMedium
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(ofertas) { producto ->

                TarjetaProducto(producto)

            }

        }

    }

}