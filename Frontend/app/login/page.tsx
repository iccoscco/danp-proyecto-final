"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import styles from "./Login.module.css";

const API_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8000";

export default function LoginPage() {
  const router = useRouter();
  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");
  const [cargando, setCargando] = useState(false);

  async function manejarEnvio(evento: FormEvent<HTMLFormElement>) {
    evento.preventDefault();
    setCargando(true);
    setError("");

    try {
      const respuesta = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ correo, contrasena }),
      });
      const cuerpo = await respuesta.json().catch(() => null);
      if (!respuesta.ok) {
        throw new Error(cuerpo?.detail ?? "No se pudo iniciar sesión.");
      }

      window.localStorage.setItem("access_token", cuerpo.access_token);
      router.push("/dashboard");
    } catch (causa) {
      setError(causa instanceof Error ? causa.message : "No se pudo iniciar sesión.");
    } finally {
      setCargando(false);
    }
  }

  return (
    <main className={styles.pagina}>
      <form className={styles.formulario} onSubmit={manejarEnvio}>
        <div>
          <p className={styles.etiqueta}>Panel administrativo</p>
          <h1>Iniciar sesión</h1>
        </div>

        <div className={styles.campo}>
          <label htmlFor="correo">Correo</label>
          <input
            id="correo"
            name="correo"
            onChange={(evento) => setCorreo(evento.target.value)}
            required
            type="email"
            value={correo}
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

        <button className={styles.boton} disabled={cargando} type="submit">
          {cargando ? "Ingresando..." : "Ingresar"}
        </button>
      </form>
    </main>
  );
}
