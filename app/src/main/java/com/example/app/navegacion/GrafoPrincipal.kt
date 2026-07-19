package com.example.app.navegacion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.example.app.interfaz.pantallas.inicio.InicioScreen
import com.example.app.interfaz.pantallas.productos.ProductosScreen
import com.example.app.interfaz.pantallas.ofertas.OfertasScreen

fun NavGraphBuilder.grafoPrincipal(
    navController: NavHostController
) {

    composable(Rutas.INICIO) {
        InicioScreen(navController)
    }

    composable(Rutas.PRODUCTOS) {
        ProductosScreen()
    }

    composable(Rutas.OFERTAS) {
        OfertasScreen()
    }
}