import Link from "next/link";
import type { ReactNode } from "react";

type LayoutDashboardProps = {
  children: ReactNode;
};

const enlaces = [
  { etiqueta: "Dashboard", href: "/dashboard" },
  { etiqueta: "Productos", href: "/productos" },
  { etiqueta: "Usuarios", href: "/usuarios" },
  { etiqueta: "Pedidos", href: "/pedidos" },
  { etiqueta: "Ofertas", href: "/ofertas" },
  { etiqueta: "Perfil", href: "/perfil" },
];

export default function LayoutDashboard({ children }: LayoutDashboardProps) {
  return (
    <div className="panel-dashboard">
      <aside className="sidebar" aria-label="Navegación principal">
        <Link className="marca" href="/dashboard">
          Panel Admin
        </Link>

        <nav className="navegacion">
          {enlaces.map((enlace) => (
            <Link
              className={enlace.href === "/dashboard" ? "enlace activo" : "enlace"}
              href={enlace.href}
              key={enlace.href}
            >
              {enlace.etiqueta}
            </Link>
          ))}
        </nav>

        <Link className="cerrar-sesion" href="/login">
          Cerrar sesión
        </Link>
      </aside>

      <div className="contenido-panel">
        <header className="navbar">
          <p>Panel administrativo</p>
          <div className="avatar" aria-label="Perfil de usuario">
            A
          </div>
        </header>
        <main className="contenido-principal">{children}</main>
      </div>
    </div>
  );
}
