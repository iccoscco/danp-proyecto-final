"use client";

import LayoutDashboard from "../../componentes/LayoutDashboard";
import GraficaBarras from "../../componentes/GraficaBarras";
import GraficaEstados from "../../componentes/GraficaEstados";
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

// Datos mock — reemplazar por fetch al backend cuando se conecte.
const ventasSemana = [
  { etiqueta: "Lun", valor: 32 },
  { etiqueta: "Mar", valor: 45 },
  { etiqueta: "Mié", valor: 28 },
  { etiqueta: "Jue", valor: 56 },
  { etiqueta: "Vie", valor: 61 },
  { etiqueta: "Sáb", valor: 38 },
  { etiqueta: "Dom", valor: 19 },
];

const pedidosEstado = [
  { etiqueta: "Pendiente", valor: 24, color: "#f59e0b" },
  { etiqueta: "En proceso", valor: 12, color: "#1769e0" },
  { etiqueta: "Completado", valor: 86, color: "#16a34a" },
];

export default function DashboardPage() {
  return (
    <LayoutDashboard enlaceActivo="/dashboard">
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Vista general</p>
          <h1>Dashboard</h1>
        </div>
      </section>

      {/* Gráficas */}
      <section className={styles.graficas} aria-label="Resumen gráfico">
        <GraficaBarras titulo="Ventas de la semana" datos={ventasSemana} color="#1769e0" />
        <GraficaEstados titulo="Pedidos por estado" datos={pedidosEstado} />
      </section>

      {/* Tarjetas */}
      <section className={styles.tarjetas} aria-label="Resumen general">
        {resumen.map((item) => (
          <article className={styles.tarjeta} key={item.etiqueta}>
            <p>{item.etiqueta}</p>
            <strong>{item.valor}</strong>
          </article>
        ))}
      </section>

      {/* Últimos pedidos */}
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
