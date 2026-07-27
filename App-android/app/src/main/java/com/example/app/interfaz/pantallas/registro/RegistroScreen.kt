package com.example.app.interfaz.pantallas.registro

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app.datos.repositorios.RepositorioAutenticacion
import com.example.app.ui.theme.VerdePrincipal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repositorio = remember { RepositorioAutenticacion() }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    val correoValido = Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    val contrasenaValida = contrasena.length >= 6

    val formularioValido = nombre.trim().isNotEmpty() && correoValido && contrasenaValida

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Gradiente decorativo superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(VerdePrincipal.copy(alpha = 0.05f), Color.Transparent)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Crear Cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF0F172A),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Únete a SaveBite y empieza a ahorrar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Campo Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    placeholder = { Text("Tu nombre") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = VerdePrincipal) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VerdePrincipal,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedLabelColor = VerdePrincipal,
                        unfocusedLabelColor = Color(0xFF64748B)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Correo
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    placeholder = { Text("ejemplo@correo.com") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = VerdePrincipal) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = correo.isNotEmpty() && !correoValido,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VerdePrincipal,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedLabelColor = VerdePrincipal,
                        unfocusedLabelColor = Color(0xFF64748B)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    placeholder = { Text("Mínimo 6 caracteres") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = VerdePrincipal) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = contrasena.isNotEmpty() && !contrasenaValida,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VerdePrincipal,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedLabelColor = VerdePrincipal,
                        unfocusedLabelColor = Color(0xFF64748B)
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón Registrarse
                Button(
                    enabled = formularioValido && !cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdePrincipal,
                        disabledContainerColor = VerdePrincipal.copy(alpha = 0.5f)
                    ),
                    onClick = {
                        cargando = true
                        scope.launch {
                            val resultado = repositorio.registrar(nombre, correo, contrasena)
                            cargando = false
                            resultado.onSuccess {
                                Toast.makeText(context, "¡Bienvenido a SaveBite!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }.onFailure {
                                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                ) {
                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Crear Cuenta",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Enlace a Login
                TextButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("¿Ya tienes una cuenta?", color = Color(0xFF64748B))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Inicia sesión", color = VerdePrincipal, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
