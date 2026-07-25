package com.example.app.interfaz.pantallas.ofertas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.datos.SesionManager
import com.example.app.datos.red.RetrofitClient
import com.example.app.interfaz.componentes.TarjetaProducto
import com.example.app.modelos.Producto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun OfertasScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sesionManager = remember { SesionManager(context) }

    var ofertas by remember { mutableStateOf<List<Producto>>(emptyList()) }
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

                val response = RetrofitClient.productoApi.obtenerProductos("Bearer $token")
                if (response.isSuccessful) {
                    // Por ahora mostramos todos como "ofertas" o podemos filtrar por stock bajo
                    // o productos que vencen pronto si tuviéramos esa lógica en el modelo real.
                    // En el modelo real no tenemos campo 'descuento' como en el falso.
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🔥 Ofertas Especiales",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Aprovecha los alimentos con descuento antes de que se agoten.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (ofertas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay ofertas disponibles actualmente")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ofertas) { producto ->
                        TarjetaProducto(producto)
                    }
                }
            }
        }
    }
}
