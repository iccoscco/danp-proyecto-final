import LayoutDashboard from "../../componentes/LayoutDashboard";

const resumen = [
  { etiqueta: "Productos registrados", valor: "128" },
  { etiqueta: "Usuarios registrados", valor: "842" },
  { etiqueta: "Pedidos pendientes", valor: "24" },
  { etiqueta: "Ofertas activas", valor: "16" },
];

const ultimosPedidos = [
  { id: "Pedido 001", cliente: "María Torres", total: "S/ 86.50", estado: "Pendiente" },
  { id: "Pedido 002", cliente: "Carlos Ruiz", total: "S/ 124.00", estado: "En proceso" },
  { id: "Pedido 003", cliente: "Lucía Ramos", total: "S/ 59.90", estado: "Pendiente" },
  { id: "Pedido 004", cliente: "Diego Flores", total: "S/ 210.00", estado: "En proceso" },
];

export default function DashboardPage() {
  return (
    <LayoutDashboard>
      <section className="encabezado-dashboard">
        <div>
          <p className="etiqueta-seccion">Vista general</p>
          <h1>Dashboard</h1>
        </div>
      </section>

      <section className="tarjetas-resumen" aria-label="Resumen general">
        {resumen.map((item) => (
          <article className="tarjeta-resumen" key={item.etiqueta}>
            <p>{item.etiqueta}</p>
            <strong>{item.valor}</strong>
          </article>
        ))}
      </section>

      <section className="seccion-pedidos" aria-labelledby="ultimos-pedidos">
        <h2 id="ultimos-pedidos">Últimos pedidos</h2>
        <div className="contenedor-tabla">
          <table>
            <thead>
              <tr>
                <th>Pedido</th>
                <th>Cliente</th>
                <th>Total</th>
                <th>Estado</th>
              </tr>
            </thead>
            <tbody>
              {ultimosPedidos.map((pedido) => (
                <tr key={pedido.id}>
                  <td>{pedido.id}</td>
                  <td>{pedido.cliente}</td>
                  <td>{pedido.total}</td>
                  <td>
                    <span className="estado-pedido">{pedido.estado}</span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </LayoutDashboard>
  );
}
