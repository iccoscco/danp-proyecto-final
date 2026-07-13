package com.example.app.navegacion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.app.interfaz.pantallas.inicio.InicioScreen

fun NavGraphBuilder.grafoPrincipal(
    navController: NavHostController
) {

    composable(Rutas.INICIO) {

        InicioScreen()

    }

}