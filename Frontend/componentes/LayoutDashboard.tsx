import Link from "next/link";
import type { ReactNode } from "react";
import styles from "./LayoutDashboard.module.css";

type LayoutDashboardProps = {
  children: ReactNode;
  enlaceActivo?: string;
};

const enlaces = [
  { etiqueta: "Dashboard", href: "/dashboard" },
  { etiqueta: "Productos", href: "/productos" },
  { etiqueta: "Usuarios", href: "/usuarios" },
  { etiqueta: "Pedidos", href: "/pedidos" },
  { etiqueta: "Ofertas", href: "/ofertas" },
  { etiqueta: "Perfil", href: "/perfil" },
];

export default function LayoutDashboard({
  children,
  enlaceActivo = "/dashboard",
}: LayoutDashboardProps) {
  return (
    <div className={styles.panel}>
      <aside className={styles.sidebar} aria-label="Navegación principal">
        <Link className={styles.marca} href="/dashboard">
          Panel Admin
        </Link>

        <nav className={styles.navegacion}>
          {enlaces.map((enlace) => (
            <Link
              className={enlace.href === enlaceActivo ? `${styles.enlace} ${styles.activo}` : styles.enlace}
              href={enlace.href}
              key={enlace.href}
            >
              {enlace.etiqueta}
            </Link>
          ))}
        </nav>

        <Link className={styles.cerrarSesion} href="/login">
          Cerrar sesión
        </Link>
      </aside>

      <div className={styles.contenido}>
        <header className={styles.navbar}>
          <p>Panel administrativo</p>
          <div className={styles.avatar} aria-label="Perfil de usuario">
            A
          </div>
        </header>
        <main className={styles.principal}>{children}</main>
      </div>
    </div>
  );
}
