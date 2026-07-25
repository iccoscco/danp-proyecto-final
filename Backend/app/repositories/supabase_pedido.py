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
        pedido_creado = resp_pedido.data[0]

        # 2. Crear los detalles
        for d in detalles:
            d["pedido_id"] = pedido_creado["id"]

        self.client.table("pedido_detalles").insert(detalles).execute()

        return self.get_by_id(pedido_creado["id"]) or pedido_creado

    def update(self, pedido_id: int, data: dict[str, Any]) -> dict[str, Any]:
        response = (
            self.client.table("pedidos")
            .update(data)
            .eq("id", pedido_id)
            .execute()
        )
        return response.data[0]

    def delete(self, pedido_id: int) -> None:
        self.client.table("pedidos").delete().eq("id", pedido_id).execute()

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
