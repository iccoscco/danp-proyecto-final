from typing import Any
from supabase import Client

class SupabasePedidoRepository:
    def __init__(self, client: Client) -> None:
        self.client = client

    def get_all(self) -> list[dict[str, Any]]:
        # Traemos pedidos con el nombre del cliente
        response = (
            self.client.table("pedidos")
            .select("*, clientes(nombre)")
            .order("id", desc=True)
            .execute()
        )
        return [self._serialize_pedido(p) for p in response.data]

    def get_by_cliente(self, cliente_id: str) -> list[dict[str, Any]]:
        response = (
            self.client.table("pedidos")
            .select("*, clientes(nombre)")
            .eq("cliente_id", cliente_id)
            .order("id", desc=True)
            .execute()
        )
        return [self._serialize_pedido(p) for p in response.data]

    def get_by_id(self, pedido_id: int) -> dict[str, Any] | None:
        response = (
            self.client.table("pedidos")
            .select("*, clientes(nombre)")
            .eq("id", pedido_id)
            .limit(1)
            .execute()
        )
        if not response.data:
            return None
        pedido = self._serialize_pedido(response.data[0])

        # Traer detalles
        detalles_resp = (
            self.client.table("pedido_detalles")
            .select("*, productos(nombre, imagen_url), ofertas(nombre, imagen_url)")
            .eq("pedido_id", pedido_id)
            .execute()
        )
        pedido["detalles"] = [self._serialize_detalle(d) for d in detalles_resp.data]
        return pedido

    def create(self, data: dict[str, Any], detalles: list[dict[str, Any]]) -> dict[str, Any]:
        # 1. Crear el pedido
        resp_pedido = self.client.table("pedidos").insert(data).execute()
        if not resp_pedido.data:
            raise Exception(f"Error al insertar pedido: {resp_pedido.error if hasattr(resp_pedido, 'error') else 'Sin datos'}")

        pedido_creado = resp_pedido.data[0]

        # 2. Crear los detalles
        for d in detalles:
            d["pedido_id"] = pedido_creado["id"]

        resp_detalles = self.client.table("pedido_detalles").insert(detalles).execute()
        if not resp_detalles.data:
            # Si fallan los detalles, intentamos borrar el pedido para no dejar basura
            self.delete(pedido_creado["id"])
            raise Exception(f"Error al insertar detalles: {resp_detalles.error if hasattr(resp_detalles, 'error') else 'Sin datos'}")

        return self.get_by_id(pedido_creado["id"]) or pedido_creado

    def update(self, pedido_id: int, data: dict[str, Any], detalles: list[dict[str, Any]] | None = None) -> dict[str, Any]:
        # 1. Actualizar el pedido
        response = (
            self.client.table("pedidos")
            .update(data)
            .eq("id", pedido_id)
            .execute()
        )

        # 2. Si vienen detalles, reemplazarlos
        if detalles is not None:
            # Borrar anteriores
            self.client.table("pedido_detalles").delete().eq("pedido_id", pedido_id).execute()
            # Insertar nuevos
            for d in detalles:
                d["pedido_id"] = pedido_id
            self.client.table("pedido_detalles").insert(detalles).execute()

        return self.get_by_id(pedido_id) or response.data[0]

    def get_stats(self) -> dict[str, Any]:
        from datetime import datetime, timedelta

        # 1. Ventas de la semana (últimos 7 días, solo Pagados o Completados)
        hoy = datetime.now()
        hace_7_dias = (hoy - timedelta(days=7)).isoformat()

        pedidos_recientes = (
            self.client.table("pedidos")
            .select("fecha, total, estado")
            .gte("fecha", hace_7_dias)
            .in_("estado", ["Pagado", "Completado"])
            .execute()
        )

        # Agrupar por día
        ventas_map = {}
        for i in range(7):
            fecha = (hoy - timedelta(days=i)).strftime("%Y-%m-%d")
            ventas_map[fecha] = 0.0

        for p in pedidos_recientes.data:
            fecha_p = p["fecha"].split("T")[0]
            if fecha_p in ventas_map:
                ventas_map[fecha_p] += float(p["total"])

        ventas_semana = [{"fecha": k, "total": v} for k, v in sorted(ventas_map.items())]

        # 2. Pedidos por estado (TODOS)
        todos_pedidos = self.client.table("pedidos").select("estado").execute()
        estados_count = {"Pendiente": 0, "Pagado": 0, "Completado": 0, "Cancelado": 0}
        for p in todos_pedidos.data:
            est = p["estado"]
            if est in estados_count:
                estados_count[est] += 1

        # 3. Conteos totales
        prod_count = self.client.table("productos").select("id", count="exact").execute().count
        user_count = self.client.table("usuarios").select("id", count="exact").execute().count
        ofer_count = self.client.table("ofertas").select("id", count="exact").execute().count

        # 4. Últimos 4 pedidos
        ultimos = (
            self.client.table("pedidos")
            .select("numero_pedido, total, estado, clientes(nombre)")
            .order("fecha", desc=True)
            .limit(4)
            .execute()
        )

        ultimos_formateados = []
        for u in ultimos.data:
            cliente_nombre = "Desconocido"
            if u.get("clientes"):
                cliente_nombre = u["clientes"].get("nombre", "Desconocido")

            ultimos_formateados.append({
                "numero_pedido": u["numero_pedido"],
                "cliente": cliente_nombre,
                "total": float(u["total"]),
                "estado": u["estado"]
            })

        return {
            "ventas_semana": ventas_semana,
            "pedidos_por_estado": estados_count,
            "total_productos": prod_count or 0,
            "total_usuarios": user_count or 0,
            "total_ofertas": ofer_count or 0,
            "ultimos_pedidos": ultimos_formateados
        }

    def _serialize_pedido(self, p: dict[str, Any]) -> dict[str, Any]:
        cliente = p.pop("clientes", None) or {}
        p["cliente_nombre"] = cliente.get("nombre")
        return p

    def _serialize_detalle(self, d: dict[str, Any]) -> dict[str, Any]:
        producto = d.pop("productos", None) or {}
        oferta = d.pop("ofertas", None) or {}

        # El nombre del item viene de productos o de ofertas
        d["nombre_item"] = producto.get("nombre") or oferta.get("nombre")
        d["imagen_url"] = producto.get("imagen_url") or oferta.get("imagen_url")
        return d
