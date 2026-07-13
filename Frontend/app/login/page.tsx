"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import styles from "./Login.module.css";

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
    <main className={styles.pagina}>
      <form className={styles.formulario} onSubmit={manejarEnvio}>
        <div>
          <p className={styles.etiqueta}>Panel administrativo</p>
          <h1>Iniciar sesión</h1>
        </div>

        <div className={styles.campo}>
          <label htmlFor="usuario">Usuario</label>
          <input
            id="usuario"
            name="usuario"
            onChange={(evento) => setUsuario(evento.target.value)}
            required
            value={usuario}
          />
        </div>

        <div className={styles.campo}>
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

        {error && <p className={styles.error} role="alert">{error}</p>}

        <button className={styles.boton} type="submit">
          Ingresar
        </button>
      </form>
    </main>
  );
}
