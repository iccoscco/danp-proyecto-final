from typing import Any

from supabase import Client


class ProductoRepository:
    def __init__(self, client: Client) -> None:
        self.client = client

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        response = (
            self.client.table("productos")
            .select("id,nombre,precio,stock,fecha_vencimiento,categoria_id,imagen_url,imagen_path,categorias(nombre)")
            .order("id", desc=True)
            .execute()
        )
        products = [self._serialize(product) for product in response.data]

        if not search:
            return products

        term = search.lower().strip()
        return [
            product
            for product in products
            if term in product["nombre"].lower() or term in product["categoria"].lower()
        ]

    def get_by_id(self, product_id: int) -> dict[str, Any] | None:
        response = (
            self.client.table("productos")
            .select("id,nombre,precio,stock,fecha_vencimiento,categoria_id,imagen_url,imagen_path,categorias(nombre)")
            .eq("id", product_id)
            .limit(1)
            .execute()
        )
        if not response.data:
            return None
        return self._serialize(response.data[0])

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("productos").insert(data).execute()
        return response.data[0]

    def update(self, product_id: int, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("productos").update(data).eq("id", product_id).execute()
        return response.data[0]

    def delete(self, product_id: int) -> None:
        self.client.table("productos").delete().eq("id", product_id).execute()

    def get_or_create_categoria_id(self, nombre: str) -> int:
        category_name = nombre.strip()
        response = (
            self.client.table("categorias")
            .select("id")
            .ilike("nombre", category_name)
            .limit(1)
            .execute()
        )
        if response.data:
            return response.data[0]["id"]

        response = self.client.table("categorias").insert({"nombre": category_name}).execute()
        return response.data[0]["id"]

    @staticmethod
    def _serialize(product: dict[str, Any]) -> dict[str, Any]:
        category = product.pop("categorias", None) or {}
        product["categoria"] = category.get("nombre", "Sin categoría")
        return product
