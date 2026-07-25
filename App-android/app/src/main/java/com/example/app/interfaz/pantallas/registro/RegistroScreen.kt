package com.example.app.interfaz.pantallas.registro

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app.datos.repositorios.RepositorioAutenticacion
import kotlinx.coroutines.launch

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
    val contrasenaValida = contrasena.length >= 8 && 
                          contrasena.any { it.isLetter() } && 
                          contrasena.any { it.isDigit() }
    
    val formularioValido = nombre.isNotEmpty() && correoValido && contrasenaValida

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Regístrate para empezar a salvar comida",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = correo.isNotEmpty() && !correoValido
        )
        if (correo.isNotEmpty() && !correoValido) {
            Text("Correo inválido", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = contrasena.isNotEmpty() && !contrasenaValida
        )
        if (contrasena.isNotEmpty() && !contrasenaValida) {
            Text("Mínimo 8 caracteres, letras y números", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            enabled = formularioValido && !cargando,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                cargando = true
                scope.launch {
                    val resultado = repositorio.registrar(nombre, correo, contrasena)
                    cargando = false
                    resultado.onSuccess {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }.onFailure {
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        ) {
            if (cargando) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Registrarse")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
