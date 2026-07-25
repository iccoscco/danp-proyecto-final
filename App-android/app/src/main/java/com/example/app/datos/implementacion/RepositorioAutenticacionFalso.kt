package com.example.app.datos

import com.example.app.modelos.Usuario

object RepositorioAutenticacionFalso {

    private val usuarios = mutableListOf(

        Usuario(
            nombre = "Administrador",
            correo = "admin@savebite.com",
            contrasena = "Admin1234"
        ),

        Usuario(
            nombre = "Cliente",
            correo = "cliente@savebite.com",
            contrasena = "Save1234"
        )

    )

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ): Boolean {

        return usuarios.any {

            it.correo.equals(correo.trim(), ignoreCase = true) &&
                    it.contrasena == contrasena.trim()

        }

    }

    fun registrar(usuario: Usuario): Boolean {

        if (usuarios.any {

                it.correo.equals(usuario.correo, ignoreCase = true)

            }) {

            return false

        }

        usuarios.add(usuario)

        return true

    }

}