package com.example.app.interfaz.componentes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.app.datos.RepositorioCarritoFalso
import com.example.app.modelos.Producto

@Composable
fun TarjetaProducto(producto: Producto) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(producto.imagen),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                AssistChip(
                    onClick = {},
                    label = {
                        Text(producto.descuento)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "S/. ${producto.precioOriginal}",
                    textDecoration = TextDecoration.LineThrough
                )

                Text(
                    text = "S/. ${producto.precioOferta}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                        RepositorioCarritoFalso.agregar(producto)

                        Toast.makeText(
                            context,
                            "${producto.nombre} agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                ) {
                    Text("Agregar")
                }

            }

        }

    }

}