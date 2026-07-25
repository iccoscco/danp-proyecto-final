from typing import Any
from supabase import Client

class SupabaseClienteRepository:
    def __init__(self, client: Client) -> None:
        self.client = client

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        query = self.client.table("clientes").select("*").order("nombre")
        response = query.execute()
        clientes = response.data

        if not search:
            return clientes

        term = search.lower().strip()
        return [
            cliente
            for cliente in clientes
            if term in str(cliente.get("nombre", "")).lower()
            or term in str(cliente.get("correo", "")).lower()
        ]

    def get_by_id(self, cliente_id: str) -> dict[str, Any] | None:
        response = (
            self.client.table("clientes")
            .select("*")
            .eq("id", cliente_id)
            .limit(1)
            .execute()
        )
        return response.data[0] if response.data else None

    def get_by_correo(self, correo: str) -> dict[str, Any] | None:
        response = (
            self.client.table("clientes")
            .select("*")
            .eq("correo", correo)
            .limit(1)
            .execute()
        )
        return response.data[0] if response.data else None

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("clientes").insert(data).execute()
        return response.data[0]

    def update(self, cliente_id: str, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("clientes").update(data).eq("id", cliente_id).execute()
        return response.data[0]

    def delete(self, cliente_id: str) -> None:
        self.client.table("clientes").delete().eq("id", cliente_id).execute()
