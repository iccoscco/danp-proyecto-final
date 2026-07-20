package com.example.app.interfaz.pantallas.misPedidos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MisPedidosScreen() {

    val pedidos = listOf(
        "🍕 Pizza Familiar",
        "☕ Café Americano",
        "🍞 Pan Integral"
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "📦 Mis Pedidos",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(20.dp))

        LazyColumn {

            items(pedidos) {

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp)
                    )

                }

            }

        }

    }

}