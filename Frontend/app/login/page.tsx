"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";

export default function LoginPage() {
  const router = useRouter();
  const [usuario, setUsuario] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");

  function manejarEnvio(evento: FormEvent<HTMLFormElement>) {
    evento.preventDefault();

    if (usuario === "admin" && contrasena === "1234") {
      setError("");
      router.push("/dashboard");
      return;
    }

    setError("Usuario o contraseña incorrectos.");
  }

  return (
    <main className="pagina-login">
      <form className="formulario-login" onSubmit={manejarEnvio}>
        <div>
          <p className="etiqueta-seccion">Panel administrativo</p>
          <h1>Iniciar sesión</h1>
        </div>

        <div className="campo-login">
          <label htmlFor="usuario">Usuario</label>
          <input
            id="usuario"
            name="usuario"
            onChange={(evento) => setUsuario(evento.target.value)}
            required
            value={usuario}
          />
        </div>

        <div className="campo-login">
          <label htmlFor="contrasena">Contraseña</label>
          <input
            id="contrasena"
            name="contrasena"
            onChange={(evento) => setContrasena(evento.target.value)}
            required
            type="password"
            value={contrasena}
          />
        </div>

        {error && <p className="error-login" role="alert">{error}</p>}

        <button className="boton-login" type="submit">
          Ingresar
        </button>
      </form>
    </main>
  );
}
