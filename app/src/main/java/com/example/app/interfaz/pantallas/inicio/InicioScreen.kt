package com.example.app.interfaz.pantallas.inicio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.interfaz.componentes.TarjetaProducto

@Composable
fun InicioScreen() {

    var busqueda by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🌱 SaveBite",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Encuentra alimentos con descuento",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar productos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Ofertas especiales",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {

            item {
                TarjetaProducto("🥗 Ensalada César", "S/ 8.50")
            }

            item {
                TarjetaProducto("🍞 Pan Integral", "S/ 3.00")
            }

            item {
                TarjetaProducto("☕ Café Americano", "S/ 5.00")
            }

            item {
                TarjetaProducto("🍎 Manzanas", "S/ 6.00")
            }

        }

    }

}