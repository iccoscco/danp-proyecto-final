package com.example.app.interfaz.pantallas.productos

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
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
fun ProductosScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sesionManager = remember { SesionManager(context) }

    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var cargando by remember { mutableStateOf(false) }

    val categorias = listOf(
        "Todos",
        "Panadería",
        "Bebidas",
        "Frutas",
        "Comidas",
        "Lácteos",
        "Postres",
        "Snacks"
    )

    fun cargarProductos() {
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
                    productos = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                cargando = false
            }
        }
    }

    LaunchedEffect(Unit) {
        cargarProductos()
    }

    val productosFiltrados = productos.filter { producto ->
        (categoriaSeleccionada == "Todos" || producto.categoria == categoriaSeleccionada) &&
        producto.nombre.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🛍 Productos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar productos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            categorias.forEach { categoria ->
                FilterChip(
                    selected = categoria == categoriaSeleccionada,
                    onClick = { categoriaSeleccionada = categoria },
                    label = { Text(categoria) }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (productosFiltrados.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (categoriaSeleccionada == "Todos") 
                            "No hay productos disponibles" 
                            else "No hay productos en la categoría $categoriaSeleccionada",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(productosFiltrados) { producto ->
                        TarjetaProducto(producto)
                    }
                }
            }
        }
    }
}
