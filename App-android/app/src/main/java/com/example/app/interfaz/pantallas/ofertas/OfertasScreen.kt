package com.example.app.interfaz.pantallas.ofertas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app.datos.SesionManager
import com.example.app.datos.red.RetrofitClient
import com.example.app.interfaz.componentes.TarjetaOferta
import com.example.app.modelos.Oferta
import com.example.app.ui.theme.VerdePrincipal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfertasScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sesionManager = remember { SesionManager(context) }

    var ofertas by remember { mutableStateOf<List<Oferta>>(emptyList()) }
    var cargando by remember { mutableStateOf(false) }

    fun cargarOfertas() {
        cargando = true
        scope.launch {
            try {
                val token = sesionManager.token.first()
                if (token == null) {
                    Toast.makeText(context, "Sesión no válida", Toast.LENGTH_SHORT).show()
                    cargando = false
                    return@launch
                }

                val response = RetrofitClient.ofertaApi.obtenerOfertas("Bearer $token")
                if (response.isSuccessful) {
                    ofertas = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Error al cargar ofertas", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                cargando = false
            }
        }
    }

    LaunchedEffect(Unit) {
        cargarOfertas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ofertas Especiales",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Aprovecha los alimentos con descuento antes de que se agoten.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = VerdePrincipal)
                }
            } else {
                if (ofertas.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No hay ofertas disponibles actualmente",
                            color = Color(0xFF64748B)
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(ofertas) { oferta ->
                            TarjetaOferta(oferta)
                        }
                    }
                }
            }
        }
    }
}
