"use client";

import { FormEvent, useState } from "react";
import LayoutDashboard from "../../componentes/LayoutDashboard";
import styles from "./Perfil.module.css";

const datosPerfil = [
  { etiqueta: "Nombre", valor: "Administrador" },
  { etiqueta: "Usuario", valor: "admin" },
  { etiqueta: "Correo", valor: "admin@savebite.com" },
  { etiqueta: "Rol", valor: "Administrador" },
];

export default function PerfilPage() {
  const [modalAbierto, setModalAbierto] = useState(false);

  function guardarPerfil(evento: FormEvent<HTMLFormElement>) {
    evento.preventDefault();
    setModalAbierto(false);
  }

  return (
    <LayoutDashboard enlaceActivo="/perfil">
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Cuenta</p>
          <h1>Perfil</h1>
        </div>
        <button className={styles.botonPrimario} onClick={() => setModalAbierto(true)} type="button">
          Editar Perfil
        </button>
      </section>

      <section className={styles.tarjeta} aria-label="Información de perfil">
        <div className={styles.avatar} aria-hidden="true">A</div>
        <div className={styles.datos}>
          {datosPerfil.map((dato) => (
            <div className={styles.fila} key={dato.etiqueta}>
              <span>{dato.etiqueta}</span>
              <strong>{dato.valor}</strong>
            </div>
          ))}
        </div>
      </section>

      {modalAbierto && (
        <div className={styles.fondoModal} role="presentation">
          <section aria-labelledby="titulo-modal-perfil" className={styles.modal} role="dialog">
            <div className={styles.cabeceraModal}>
              <h2 id="titulo-modal-perfil">Editar perfil</h2>
              <button
                aria-label="Cerrar modal"
                className={styles.cerrarModal}
                onClick={() => setModalAbierto(false)}
                type="button"
              >
                ×
              </button>
            </div>

            <form className={styles.formulario} onSubmit={guardarPerfil}>
              <label>
                Nombre
                <input defaultValue="Administrador" name="nombre" required />
              </label>
              <label>
                Usuario
                <input defaultValue="admin" name="usuario" required />
              </label>
              <label>
                Correo
                <input defaultValue="admin@savebite.com" name="correo" required type="email" />
              </label>
              <label>
                Nueva contraseña
                <input name="contrasena" type="password" />
              </label>
              <label>
                Rol
                <input defaultValue="Administrador" name="rol" readOnly />
              </label>
              <button className={styles.botonPrimario} type="submit">Guardar</button>
            </form>
          </section>
        </div>
      )}
    </LayoutDashboard>
  );
}
