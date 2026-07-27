package com.example.app.interfaz.pantallas.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app.navegacion.Rutas
import com.example.app.ui.theme.VerdePrincipal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SaveBite",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = VerdePrincipal
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Rutas.PERFIL)
                    }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil", tint = VerdePrincipal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Fondo decorativo superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(VerdePrincipal.copy(alpha = 0.05f), Color.Transparent)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp)) // Espacio adicional solicitado

                Text(
                    text = "¡Hola de nuevo!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                )

                Text(
                    text = "¿Qué quieres hacer hoy?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF64748B)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Grid de opciones
                OpcionMenu(
                    titulo = "Explorar Tienda",
                    descripcion = "Ver todos los productos",
                    icono = Icons.Default.ShoppingCart,
                    onClick = { navController.navigate(Rutas.PRODUCTOS) }
                )

                OpcionMenu(
                    titulo = "Ofertas Flash",
                    descripcion = "Descuentos increíbles",
                    icono = Icons.Default.Star,
                    colorIcono = Color(0xFFF59E0B),
                    onClick = { navController.navigate(Rutas.OFERTAS) }
                )

                OpcionMenu(
                    titulo = "Mi Carrito",
                    descripcion = "Finaliza tu compra",
                    icono = Icons.Default.List,
                    onClick = { navController.navigate(Rutas.CARRITO) }
                )

                OpcionMenu(
                    titulo = "Mis Pedidos",
                    descripcion = "Historial y estados",
                    icono = Icons.Default.DateRange,
                    onClick = { navController.navigate(Rutas.PEDIDOS) }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun OpcionMenu(
    titulo: String,
    descripcion: String,
    icono: ImageVector,
    colorIcono: Color = VerdePrincipal,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = colorIcono.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = colorIcono,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    titulo,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                )
                Text(
                    descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B)
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFFCBD5E1)
            )
        }
    }
}
