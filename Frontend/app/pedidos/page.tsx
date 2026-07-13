import LayoutDashboard from "../../componentes/LayoutDashboard";
import styles from "./Pedidos.module.css";

const pedidos = [
  { id: "Pedido 014", cliente: "Valeria Soto", fecha: "12 Jul 2026", total: "S/ 74.00", estado: "Pendiente", claseEstado: "estadoPendiente" },
  { id: "Pedido 015", cliente: "Miguel Castro", fecha: "12 Jul 2026", total: "S/ 130.50", estado: "Completado", claseEstado: "estadoCompletado" },
  { id: "Pedido 016", cliente: "Sofía Paredes", fecha: "11 Jul 2026", total: "S/ 46.90", estado: "Cancelado", claseEstado: "estadoCancelado" },
  { id: "Pedido 017", cliente: "Jorge Núñez", fecha: "11 Jul 2026", total: "S/ 95.00", estado: "Completado", claseEstado: "estadoCompletado" },
];

export default function PedidosPage() {
  return (
    <LayoutDashboard enlaceActivo="/pedidos">
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Operaciones</p>
          <h1>Pedidos</h1>
        </div>
        <button className={styles.botonPrimario} type="button">Agregar pedido</button>
      </section>

      <section className={styles.panel} aria-label="Listado de pedidos">
        <input aria-label="Buscar pedidos" className={styles.buscador} placeholder="Buscar pedidos" type="search" />
        <div className={styles.contenedorTabla}>
          <table className={styles.tabla}>
            <thead>
              <tr>
                <th>Pedido</th>
                <th>Cliente</th>
                <th>Fecha</th>
                <th>Total</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {pedidos.map((pedido) => (
                <tr key={pedido.id}>
                  <td>{pedido.id}</td>
                  <td>{pedido.cliente}</td>
                  <td>{pedido.fecha}</td>
                  <td>{pedido.total}</td>
                  <td><span className={`${styles.estado} ${styles[pedido.claseEstado]}`}>{pedido.estado}</span></td>
                  <td>
                    <div className={styles.acciones}>
                      <button className={styles.botonSecundario} type="button">Editar</button>
                      <button className={styles.botonEliminar} type="button">Eliminar</button>
                    </div>
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
