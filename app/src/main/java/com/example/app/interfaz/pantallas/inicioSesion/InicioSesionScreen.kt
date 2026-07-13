package com.example.app.interfaz.pantallas.inicioSesion

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app.datos.RepositorioAutenticacionFalso
import com.example.app.navegacion.Rutas

@Composable
fun InicioSesionScreen(
    navController: NavHostController
) {

    val context = LocalContext.current

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🌱 SaveBite",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicia sesión para continuar",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
            },
            label = {
                Text("Correo electrónico")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
            },
            label = {
                Text("Contraseña")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                if (correo.isBlank() || contrasena.isBlank()) {

                    Toast.makeText(
                        context,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                val acceso = RepositorioAutenticacionFalso.iniciarSesion(
                    correo,
                    contrasena
                )

                if (acceso) {

                    Toast.makeText(
                        context,
                        "Bienvenido a SaveBite",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate(Rutas.INICIO)

                } else {

                    Toast.makeText(
                        context,
                        "Correo o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

        ) {

            Text("Ingresar")

        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {

                navController.navigate(Rutas.REGISTRO)

            }
        ) {

            Text("¿No tienes cuenta? Regístrate")

        }

    }

}