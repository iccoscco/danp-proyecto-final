from typing import Any

from supabase import Client

# Trae, vía JOIN, los campos del producto necesarios para que el frontend
# muestre el precio original y calcule el precio en oferta.
SELECT_OFERTA = (
    "id,nombre,descuento,fecha_inicio,fecha_fin,estado,producto_id,imagen_url,"
    "productos(id,nombre,precio,stock,imagen_url,fecha_vencimiento)"
)


class SupabaseOfertaRepository:
    def __init__(self, client: Client) -> None:
        self.client = client

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        response = (
            self.client.table("ofertas")
            .select(SELECT_OFERTA)
            .order("id", desc=True)
            .execute()
        )
        ofertas = [self._serialize(o) for o in response.data]

        if not search:
            return ofertas

        term = search.lower().strip()
        return [
            oferta
            for oferta in ofertas
            if term in str(oferta.get("nombre", "")).lower()
            or term in str(oferta.get("nombre_producto", "")).lower()
        ]

    def get_by_id(self, oferta_id: int) -> dict[str, Any] | None:
        response = (
            self.client.table("ofertas")
            .select(SELECT_OFERTA)
            .eq("id", oferta_id)
            .limit(1)
            .execute()
        )
        if not response.data:
            return None
        return self._serialize(response.data[0])

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("ofertas").insert(data).execute()
        return response.data[0]

    def update(self, oferta_id: int, data: dict[str, Any]) -> dict[str, Any]:
        response = (
            self.client.table("ofertas")
            .update(data)
            .eq("id", oferta_id)
            .execute()
        )
        return response.data[0]

    def delete(self, oferta_id: int) -> None:
        self.client.table("ofertas").delete().eq("id", oferta_id).execute()

    @staticmethod
    def _serialize(oferta: dict[str, Any]) -> dict[str, Any]:
        """Aplana el JOIN de productos: producto -> {...} => nombre_producto, etc."""
        producto = oferta.pop("productos", None) or {}
        oferta["nombre_producto"] = producto.get("nombre")
        oferta["precio_original"] = producto.get("precio")
        oferta["stock_producto"] = producto.get("stock")
        oferta["fecha_vencimiento"] = producto.get("fecha_vencimiento")
        # imagen_url de la oferta puede estar vacía; si lo está, hereda la del producto.
        if not oferta.get("imagen_url"):
            oferta["imagen_url"] = producto.get("imagen_url")
        return oferta
