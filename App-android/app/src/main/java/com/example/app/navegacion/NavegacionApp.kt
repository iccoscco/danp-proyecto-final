package com.example.app.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavegacionApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.SPLASH
    ) {

        grafoAutenticacion(navController)

        grafoPrincipal(navController)

    }

}