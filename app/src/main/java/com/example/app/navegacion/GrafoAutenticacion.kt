package com.example.app.navegacion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.app.interfaz.pantallas.inicioSesion.InicioSesionScreen
import com.example.app.interfaz.pantallas.registro.RegistroScreen
import com.example.app.interfaz.pantallas.splash.SplashScreen

fun NavGraphBuilder.grafoAutenticacion(
    navController: NavHostController
) {

    composable(Rutas.SPLASH) {
        SplashScreen(navController)
    }

    composable(Rutas.LOGIN) {
        InicioSesionScreen(navController)
    }

    composable(Rutas.REGISTRO) {
        RegistroScreen(navController)
    }

}