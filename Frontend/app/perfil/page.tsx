"use client";

import { FormEvent, useState } from "react";
import LayoutDashboard from "../../componentes/LayoutDashboard";

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
      <section className="encabezado-modulo">
        <div>
          <p className="etiqueta-seccion">Cuenta</p>
          <h1>Perfil</h1>
        </div>
        <button className="boton-primario" onClick={() => setModalAbierto(true)} type="button">
          Editar Perfil
        </button>
      </section>

      <section className="tarjeta-perfil" aria-label="Información de perfil">
        <div className="avatar avatar-perfil" aria-hidden="true">A</div>
        <div className="datos-perfil">
          {datosPerfil.map((dato) => (
            <div className="fila-perfil" key={dato.etiqueta}>
              <span>{dato.etiqueta}</span>
              <strong>{dato.valor}</strong>
            </div>
          ))}
        </div>
      </section>

      {modalAbierto && (
        <div className="fondo-modal" role="presentation">
          <section aria-labelledby="titulo-modal-perfil" className="modal-producto" role="dialog">
            <div className="cabecera-modal">
              <h2 id="titulo-modal-perfil">Editar perfil</h2>
              <button
                aria-label="Cerrar modal"
                className="cerrar-modal"
                onClick={() => setModalAbierto(false)}
                type="button"
              >
                ×
              </button>
            </div>

            <form className="formulario-producto" onSubmit={guardarPerfil}>
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
              <button className="boton-primario" type="submit">Guardar</button>
            </form>
          </section>
        </div>
      )}
    </LayoutDashboard>
  );
}
