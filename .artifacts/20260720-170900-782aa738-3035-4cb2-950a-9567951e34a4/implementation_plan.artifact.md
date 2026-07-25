# Integración de Productos y Categorías (Backend, Frontend y App)

Este plan detalla los cambios necesarios para sincronizar las categorías en todo el sistema, permitir que el administrador las seleccione desde un desplegable en el Frontend, y que la App Android consuma los productos reales de la base de datos filtrados por categoría.

## Cambios Propuestos

### Backend

Sincronizar las categorías y permitir el acceso a clientes.

#### [categorias_seed.sql](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/Backend/supabase/categorias_seed.sql) [NUEVO]
- Script para insertar las 7 categorías requeridas en la base de datos.

#### [dependencies.py](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/Backend/app/api/dependencies.py)
- Modificar `get_current_user` para que reconozca tanto a Administradores (tabla `usuarios`) como a Clientes (tabla `clientes`) basándose en el rol del token JWT.

#### [productos.py](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/Backend/app/routers/productos.py)
- Asegurar que el endpoint `GET /` sea accesible para Clientes y Administradores.

---

### Frontend (Next.js)

Mejorar la gestión de categorías.

#### [page.tsx](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/Frontend/app/productos/page.tsx)
- Cambiar el `input` de categoría por un `<select>` con las opciones fijas: Panadería, Bebidas, Frutas, Comidas, Lácteos, Postres, Snacks.

---

### App Android

Conectar con la API real y actualizar la interfaz.

#### [build.gradle.kts](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/App-android/app/build.gradle.kts)
- Añadir dependencia de Coil para carga de imágenes: `implementation("io.coil-kt:coil-compose:2.7.0")`.

#### [Producto.kt](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/App-android/app/src/main/java/com/example/app/modelos/Producto.kt)
- Actualizar el modelo para incluir `fechaVencimiento`, `imagenUrl` y simplificar los campos de precio (quitar ofertas/descuentos).

#### [ProductoApi.kt](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/App-android/app/src/main/java/com/example/app/datos/red/ProductoApi.kt) [NUEVO]
- Definir la interfaz de Retrofit para obtener productos.

#### [RetrofitClient.kt](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/App-android/app/src/main/java/com/example/app/datos/red/RetrofitClient.kt)
- Añadir instancia de `ProductoApi`.

#### [TarjetaProducto.kt](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/App-android/app/src/main/java/com/example/app/interfaz/componentes/TarjetaProducto.kt)
- Reemplazar `Image` por `AsyncImage` (Coil).
- Mostrar precio real y fecha de vencimiento.
- Eliminar chips de descuento y estilos de oferta.

#### [ProductosScreen.kt](file:///C:/Users/User/Downloads/DANP-Final%20Teo/danp-proyecto-final/App-android/app/src/main/java/com/example/app/interfaz/pantallas/productos/ProductosScreen.kt)
- Implementar la llamada a la API real.
- Filtrar los productos por la categoría seleccionada en el chip superior.

## Plan de Verificación

### Pruebas Automatizadas
- No se dispone de suite de pruebas automatizadas actualmente, se procederá con verificación manual.

### Verificación Manual
1. **Base de Datos**: Verificar que las categorías aparezcan en Supabase tras ejecutar el SQL.
2. **Frontend**: Intentar crear un producto y verificar que el desplegable funcione y guarde la categoría correcta.
3. **Android App**:
    - Verificar que al abrir la pantalla de productos, estos carguen desde el backend.
    - Verificar que al cambiar de categoría, la lista se filtre correctamente.
    - Verificar que las imágenes carguen correctamente desde las URLs de Supabase.
    - Verificar que la tarjeta de producto no muestre ofertas falsas y sí muestre la fecha de vencimiento.
