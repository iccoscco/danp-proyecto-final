package com.example.app.navegacion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.app.interfaz.pantallas.carrito.CarritoScreen
import com.example.app.interfaz.pantallas.inicio.InicioScreen
import com.example.app.interfaz.pantallas.misPedidos.MisPedidosScreen
import com.example.app.interfaz.pantallas.ofertas.OfertasScreen
import com.example.app.interfaz.pantallas.perfil.PerfilScreen
import com.example.app.interfaz.pantallas.productos.ProductosScreen

fun NavGraphBuilder.grafoPrincipal(
    navController: NavHostController
) {

    composable(Rutas.INICIO) {
        InicioScreen(navController)
    }

    composable(Rutas.PRODUCTOS) {
        ProductosScreen(navController)
    }

    composable(Rutas.OFERTAS) {
        OfertasScreen(navController)
    }

    composable(Rutas.CARRITO) {
        CarritoScreen(navController)
    }

    composable(Rutas.PEDIDOS) {
        MisPedidosScreen(navController)
    }

    composable(Rutas.PERFIL) {
        PerfilScreen(navController)
    }

}