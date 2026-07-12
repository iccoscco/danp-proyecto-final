import LayoutDashboard from "../../componentes/LayoutDashboard";

export default function DashboardPage() {
  return (
    <LayoutDashboard>
      <section className="encabezado-dashboard">
        <div>
          <p className="etiqueta-seccion">Vista general</p>
          <h1>Dashboard</h1>
        </div>
      </section>

      <section className="tarjetas-vacias" aria-label="Contenido del dashboard">
        <div className="tarjeta-vacia" />
        <div className="tarjeta-vacia" />
      </section>
    </LayoutDashboard>
  );
}
