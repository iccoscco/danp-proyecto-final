package com.example.app.datos

import com.example.app.modelos.Usuario

object RepositorioAutenticacionFalso {

    private val usuarios = mutableListOf(

        Usuario(
            nombre = "Administrador",
            correo = "admin@savebite.com",
            contrasena = "123456"
        ),

        Usuario(
            nombre = "Cliente",
            correo = "cliente@savebite.com",
            contrasena = "123456"
        )

    )

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ): Boolean {

        return usuarios.any {

            it.correo == correo &&
                    it.contrasena == contrasena

        }

    }

    fun registrar(usuario: Usuario): Boolean {

        if (usuarios.any { it.correo == usuario.correo }) {
            return false
        }

        usuarios.add(usuario)

        return true

    }

}