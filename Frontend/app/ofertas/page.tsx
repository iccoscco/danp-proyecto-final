import LayoutDashboard from "../../componentes/LayoutDashboard";
import styles from "./Ofertas.module.css";

const ofertas = [
  { id: 1, nombre: "Leche amarilla", descuento: "20%", vigencia: "15 - 31 Jul 2026", estado: "Activa" },
  { id: 2, nombre: "Yogurt con cereal UwU", descuento: "15%", vigencia: "12 - 20 Jul 2026", estado: "Activa" },
  { id: 3, nombre: "Ensalada de Frutas", descuento: "10%", vigencia: "01 - 10 Jul 2026", estado: "Finalizada" },
  { id: 4, nombre: "Galletas", descuento: "25%", vigencia: "22 - 29 Jul 2026", estado: "Programada" },
];

export default function OfertasPage() {
  return (
    <LayoutDashboard enlaceActivo="/ofertas">
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Promociones</p>
          <h1>Ofertas</h1>
        </div>
        <button className={styles.botonPrimario} type="button">Agregar oferta</button>
      </section>

      <section className={styles.panel} aria-label="Listado de ofertas">
        <input aria-label="Buscar ofertas" className={styles.buscador} placeholder="Buscar ofertas" type="search" />
        <div className={styles.contenedorTabla}>
          <table className={styles.tabla}>
            <thead>
              <tr>
                <th>Oferta</th>
                <th>Descuento</th>
                <th>Vigencia</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {ofertas.map((oferta) => (
                <tr key={oferta.id}>
                  <td>{oferta.nombre}</td>
                  <td>{oferta.descuento}</td>
                  <td>{oferta.vigencia}</td>
                  <td>{oferta.estado}</td>
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
