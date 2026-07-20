package com.example.app.interfaz.pantallas.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PerfilScreen() {

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "👤 Mi Perfil",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(20.dp))

        Text("Nombre: Cliente")

        Text("Correo: cliente@savebite.com")

        Text("Miembro desde: 2026")

        Text("Pedidos realizados: 5")

    }

}