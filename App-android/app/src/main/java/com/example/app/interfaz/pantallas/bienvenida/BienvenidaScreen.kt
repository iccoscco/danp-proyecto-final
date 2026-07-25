package com.example.app.interfaz.pantallas.bienvenida

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app.R
import com.example.app.navegacion.Rutas

@Composable
fun BienvenidaScreen(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo SaveBite"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "SaveBite",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Conectamos personas con alimentos de calidad\npara reducir el desperdicio.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Rutas.LOGIN)
            }
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Rutas.REGISTRO)
            }
        ) {
            Text("Crear cuenta")
        }

    }

}