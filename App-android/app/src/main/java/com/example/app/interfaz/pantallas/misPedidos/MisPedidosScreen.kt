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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "📦 Mis Pedidos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(pedidos) { pedido ->

                Card {

                    Text(
                        text = pedido,
                        modifier = Modifier.padding(16.dp)
                    )

                }

            }

        }

    }

}