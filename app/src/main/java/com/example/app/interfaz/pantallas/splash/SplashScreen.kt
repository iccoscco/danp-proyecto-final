package com.example.app.interfaz.pantallas.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.app.navegacion.Rutas
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
) {

    LaunchedEffect(Unit) {

        delay(2000)

        navController.navigate(Rutas.LOGIN) {
            popUpTo(Rutas.SPLASH) {
                inclusive = true
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🌱",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = "SaveBite",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Rescatando alimentos,\ncuidando el planeta."
        )

    }

}