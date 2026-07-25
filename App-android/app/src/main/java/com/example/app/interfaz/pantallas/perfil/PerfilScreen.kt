package com.example.app.interfaz.pantallas.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PerfilScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "👤 Mi Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Nombre",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Cliente SaveBite")

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Correo",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("cliente@savebite.com")

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Miembro desde",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("2026")

            }

        }

    }

}