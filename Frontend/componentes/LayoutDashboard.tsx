"use client";

import Link from "next/link";
import { useEffect, useState } from "react";
import type { ReactNode } from "react";
import { cerrarSesion, esAdministrador, obtenerUsuarioActual, type UsuarioActual } from "../utilidades/api";
import styles from "./LayoutDashboard.module.css";

type LayoutDashboardProps = {
  children: ReactNode;
  enlaceActivo?: string;
};

type Enlace = { etiqueta: string; href: string; soloAdmin?: boolean };

const enlaces: Enlace[] = [
  { etiqueta: "Dashboard", href: "/dashboard" },
  { etiqueta: "Productos", href: "/productos" },
  { etiqueta: "Usuarios", href: "/usuarios", soloAdmin: true },
  { etiqueta: "Clientes", href: "/clientes", soloAdmin: true },
  { etiqueta: "Pedidos", href: "/pedidos" },
  { etiqueta: "Ofertas", href: "/ofertas" },
  { etiqueta: "Perfil", href: "/perfil" },
];

export default function LayoutDashboard({ children, enlaceActivo = "/dashboard" }: LayoutDashboardProps) {
  const [usuario, setUsuario] = useState<UsuarioActual | null>(null);

  useEffect(() => {
    let activo = true;
    obtenerUsuarioActual().then((u) => {
      if (activo) setUsuario(u);
    });
    return () => {
      activo = false;
    };
  }, []);

  const enlacesVisibles = enlaces.filter((enlace) => !enlace.soloAdmin || esAdministrador(usuario));
  const inicial = usuario?.nombre?.trim()?.charAt(0)?.toUpperCase() ?? "A";

  return (
    <div className={styles.panel}>
      <aside className={styles.sidebar} aria-label="Navegación principal">
        <Link className={styles.marca} href="/dashboard">
          Panel Admin
        </Link>

        <nav className={styles.navegacion}>
          {enlacesVisibles.map((enlace) => (
            <Link
              className={enlace.href === enlaceActivo ? `${styles.enlace} ${styles.activo}` : styles.enlace}
              href={enlace.href}
              key={enlace.href}
            >
              {enlace.etiqueta}
            </Link>
          ))}
        </nav>

        <button className={styles.cerrarSesion} onClick={() => cerrarSesion()} type="button">
          Cerrar sesión
        </button>
      </aside>

      <div className={styles.contenido}>
        <header className={styles.navbar}>
          <p>Panel administrativo</p>
          <div className={styles.avatar} aria-label="Perfil de usuario" title={usuario?.nombre}>
            {inicial}
          </div>
        </header>
        <main className={styles.principal}>{children}</main>
      </div>
    </div>
  );
}
