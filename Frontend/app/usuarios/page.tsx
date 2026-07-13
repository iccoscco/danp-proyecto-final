import LayoutDashboard from "../../componentes/LayoutDashboard";

const usuarios = [
  { id: 1, nombre: "Ana Gómez", correo: "ana.gomez@ejemplo.com", rol: "Administradora", estado: "Activo" },
  { id: 2, nombre: "Bruno Salas", correo: "bruno.salas@ejemplo.com", rol: "Operador", estado: "Activo" },
  { id: 3, nombre: "Carla Díaz", correo: "carla.diaz@ejemplo.com", rol: "Operadora", estado: "Activo" },
  { id: 4, nombre: "David León", correo: "david.leon@ejemplo.com", rol: "Operador", estado: "Inactivo" },
];

export default function UsuariosPage() {
  return (
    <LayoutDashboard enlaceActivo="/usuarios">
      <section className="encabezado-modulo">
        <div>
          <p className="etiqueta-seccion">Administración</p>
          <h1>Usuarios</h1>
        </div>
        <button className="boton-primario" type="button">Agregar usuario</button>
      </section>

      <section className="panel-listado" aria-label="Listado de usuarios">
        <input aria-label="Buscar usuarios" className="buscador-listado" placeholder="Buscar usuarios" type="search" />
        <div className="contenedor-tabla">
          <table>
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
