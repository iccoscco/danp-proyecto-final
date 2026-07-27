package com.example.app.interfaz.pantallas.inicioSesion

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app.R
import com.example.app.datos.SesionManager
import com.example.app.datos.repositorios.RepositorioAutenticacion
import com.example.app.navegacion.Rutas
import com.example.app.ui.theme.VerdePrincipal
import kotlinx.coroutines.launch

@Composable
fun InicioSesionScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repositorio = remember { RepositorioAutenticacion() }
    val sesionManager = remember { SesionManager(context) }

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var cargando by remember { mutableStateOf(false) }

    val correoValido = Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    val contrasenaValida = contrasena.length >= 6

    val formularioValido = correoValido && contrasenaValida

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fondo con gradiente sutil en la parte superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(VerdePrincipal.copy(alpha = 0.1f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "SaveBite Logo",
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvenido a SaveBite",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Tu comida favorita al mejor precio",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

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
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = VerdePrincipal) },
                visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrincipal,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedLabelColor = VerdePrincipal,
                    unfocusedLabelColor = Color(0xFF64748B)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Ingresar
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
                        val resultado = repositorio.iniciarSesion(correo, contrasena)
                        cargando = false
                        resultado.onSuccess { token ->
                            scope.launch {
                                sesionManager.guardarToken(token)
                                Toast.makeText(context, "¡Hola de nuevo!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Rutas.INICIO) {
                                    popUpTo(Rutas.LOGIN) { inclusive = true }
                                }
                            }
                        }.onFailure {
                            Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
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
                        "Iniciar Sesión",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Enlace a Registro
            TextButton(
                onClick = { navController.navigate(Rutas.REGISTRO) }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("¿Eres nuevo?", color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Crea una cuenta", color = VerdePrincipal, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
