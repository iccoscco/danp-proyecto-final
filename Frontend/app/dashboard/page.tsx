import LayoutDashboard from "../../componentes/LayoutDashboard";
import styles from "./Dashboard.module.css";

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
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Vista general</p>
          <h1>Dashboard</h1>
        </div>
      </section>

      <section className={styles.tarjetas} aria-label="Resumen general">
        {resumen.map((item) => (
          <article className={styles.tarjeta} key={item.etiqueta}>
            <p>{item.etiqueta}</p>
            <strong>{item.valor}</strong>
          </article>
        ))}
      </section>

      <section className={styles.pedidos} aria-labelledby="ultimos-pedidos">
        <h2 id="ultimos-pedidos">Últimos pedidos</h2>
        <div className={styles.contenedorTabla}>
          <table className={styles.tabla}>
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
                    <span className={styles.estado}>{pedido.estado}</span>
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
