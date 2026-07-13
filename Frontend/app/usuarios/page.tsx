import LayoutDashboard from "../../componentes/LayoutDashboard";
import styles from "./Usuarios.module.css";

const usuarios = [
  { id: 1, nombre: "Ana Gómez", correo: "ana.gomez@ejemplo.com", rol: "Administradora", estado: "Activo" },
  { id: 2, nombre: "Bruno Salas", correo: "bruno.salas@ejemplo.com", rol: "Operador", estado: "Activo" },
  { id: 3, nombre: "Carla Díaz", correo: "carla.diaz@ejemplo.com", rol: "Operadora", estado: "Activo" },
  { id: 4, nombre: "David León", correo: "david.leon@ejemplo.com", rol: "Operador", estado: "Inactivo" },
];

export default function UsuariosPage() {
  return (
    <LayoutDashboard enlaceActivo="/usuarios">
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Administración</p>
          <h1>Usuarios</h1>
        </div>
        <button className={styles.botonPrimario} type="button">Agregar usuario</button>
      </section>

      <section className={styles.panel} aria-label="Listado de usuarios">
        <input aria-label="Buscar usuarios" className={styles.buscador} placeholder="Buscar usuarios" type="search" />
        <div className={styles.contenedorTabla}>
          <table className={styles.tabla}>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Rol</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {usuarios.map((usuario) => (
                <tr key={usuario.id}>
                  <td>{usuario.nombre}</td>
                  <td>{usuario.correo}</td>
                  <td>{usuario.rol}</td>
                  <td>{usuario.estado}</td>
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
