import LayoutDashboard from "../../componentes/LayoutDashboard";

const pedidos = [
  { id: "Pedido 014", cliente: "Valeria Soto", fecha: "12 Jul 2026", total: "S/ 74.00", estado: "Pendiente", claseEstado: "estado-pendiente" },
  { id: "Pedido 015", cliente: "Miguel Castro", fecha: "12 Jul 2026", total: "S/ 130.50", estado: "Completado", claseEstado: "estado-completado" },
  { id: "Pedido 016", cliente: "Sofía Paredes", fecha: "11 Jul 2026", total: "S/ 46.90", estado: "Cancelado", claseEstado: "estado-cancelado" },
  { id: "Pedido 017", cliente: "Jorge Núñez", fecha: "11 Jul 2026", total: "S/ 95.00", estado: "Completado", claseEstado: "estado-completado" },
];

export default function PedidosPage() {
  return (
    <LayoutDashboard enlaceActivo="/pedidos">
      <section className="encabezado-modulo">
        <div>
          <p className="etiqueta-seccion">Operaciones</p>
          <h1>Pedidos</h1>
        </div>
        <button className="boton-primario" type="button">Agregar pedido</button>
      </section>

      <section className="panel-listado" aria-label="Listado de pedidos">
        <input aria-label="Buscar pedidos" className="buscador-listado" placeholder="Buscar pedidos" type="search" />
        <div className="contenedor-tabla">
          <table>
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
                  <td><span className={`estado-pedido ${pedido.claseEstado}`}>{pedido.estado}</span></td>
                  <td>
                    <div className="acciones-listado">
                      <button className="boton-secundario" type="button">Editar</button>
                      <button className="boton-eliminar" type="button">Eliminar</button>
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
