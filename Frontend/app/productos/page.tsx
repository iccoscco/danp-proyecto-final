"use client";

import { FormEvent, useState } from "react";
import LayoutDashboard from "../../componentes/LayoutDashboard";
import styles from "./Productos.module.css";

const productos = [
  { id: 1, nombre: "Yogur natural", categoria: "Lácteos", precio: "S/ 8.50", stock: 42, vencimiento: "18 Jul 2026" },
  { id: 2, nombre: "Pan integral", categoria: "Panadería", precio: "S/ 6.00", stock: 28, vencimiento: "15 Jul 2026" },
  { id: 3, nombre: "Jugo de naranja", categoria: "Bebidas", precio: "S/ 9.90", stock: 35, vencimiento: "22 Jul 2026" },
  { id: 4, nombre: "Granola clásica", categoria: "Cereales", precio: "S/ 14.50", stock: 19, vencimiento: "30 Ago 2026" },
];

export default function ProductosPage() {
  const [modalAbierto, setModalAbierto] = useState(false);

  function guardarProducto(evento: FormEvent<HTMLFormElement>) {
    evento.preventDefault();
    setModalAbierto(false);
  }

  return (
    <LayoutDashboard enlaceActivo="/productos">
      <section className={styles.encabezado}>
        <div>
          <p className={styles.etiqueta}>Catálogo</p>
          <h1>Productos</h1>
        </div>
        <button className={styles.botonPrimario} onClick={() => setModalAbierto(true)} type="button">
          Nuevo Producto
        </button>
      </section>

      <section className={styles.panel} aria-label="Listado de productos">
        <input
          aria-label="Buscar productos"
          className={styles.buscador}
          placeholder="Buscar productos"
          type="search"
        />

        <div className={styles.contenedorTabla}>
          <table className={styles.tabla}>
            <thead>
              <tr>
                <th>Producto</th>
                <th>Categoría</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Vencimiento</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {productos.map((producto) => (
                <tr key={producto.id}>
                  <td>{producto.nombre}</td>
                  <td>{producto.categoria}</td>
                  <td>{producto.precio}</td>
                  <td>{producto.stock}</td>
                  <td>{producto.vencimiento}</td>
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

      {modalAbierto && (
        <div className={styles.fondoModal} role="presentation">
          <section aria-labelledby="titulo-modal-producto" className={styles.modal} role="dialog">
            <div className={styles.cabeceraModal}>
              <h2 id="titulo-modal-producto">Nuevo producto</h2>
              <button
                aria-label="Cerrar modal"
                className={styles.cerrarModal}
                onClick={() => setModalAbierto(false)}
                type="button"
              >
                ×
              </button>
            </div>

            <form className={styles.formulario} onSubmit={guardarProducto}>
              <label>
                Nombre
                <input name="nombre" required />
              </label>
              <label>
                Categoría
                <input name="categoria" required />
              </label>
              <label>
                Precio
                <input min="0" name="precio" required step="0.01" type="number" />
              </label>
              <label>
                Stock
                <input min="0" name="stock" required type="number" />
              </label>
              <label>
                Fecha de vencimiento
                <input name="vencimiento" required type="date" />
              </label>
              <button className={styles.botonPrimario} type="submit">Guardar</button>
            </form>
          </section>
        </div>
      )}
    </LayoutDashboard>
  );
}
