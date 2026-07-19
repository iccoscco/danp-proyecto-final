package com.example.app.interfaz.pantallas.inicio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app.navegacion.Rutas

@Composable
fun InicioScreen(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "🌱 SaveBite",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Selecciona una opción del menú."
        )

        Spacer(modifier = Modifier.height(24.dp))

        OpcionMenu(
            titulo = "🛍 Productos",
            descripcion = "Explora todos los alimentos disponibles."
        ) {
            navController.navigate(Rutas.PRODUCTOS)
        }

        OpcionMenu(
            titulo = "🔥 Ofertas",
            descripcion = "Productos con grandes descuentos."
        ) {
            navController.navigate(Rutas.OFERTAS)
        }

        OpcionMenu(
            titulo = "🛒 Mi Carrito",
            descripcion = "Revisa los productos agregados."
        ) {
            navController.navigate(Rutas.CARRITO)
        }

        OpcionMenu(
            titulo = "📦 Mis Pedidos",
            descripcion = "Consulta tu historial."
        ) {
            navController.navigate(Rutas.PEDIDOS)
        }

        OpcionMenu(
            titulo = "👤 Mi Perfil",
            descripcion = "Información de tu cuenta."
        ) {
            navController.navigate(Rutas.PERFIL)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            onClick = {

                navController.navigate(Rutas.LOGIN) {

                    popUpTo(Rutas.INICIO) {
                        inclusive = true
                    }

                }

            }

        ) {

            Text("Cerrar sesión")

        }

    }

}

@Composable
fun OpcionMenu(
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                titulo,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(descripcion)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick
            ) {
                Text("Abrir")
            }

        }

    }

}