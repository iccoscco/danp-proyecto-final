from typing import Any

from supabase import Client


class SupabaseUsuarioRepository:
    def __init__(self, client: Client) -> None:
        self.client = client

    def get_by_id(self, usuario_id: int) -> dict[str, Any] | None:
        response = self.client.table("usuarios").select("*").eq("id", usuario_id).limit(1).execute()
        return response.data[0] if response.data else None

    def get_by_correo(self, correo: str) -> dict[str, Any] | None:
        response = self.client.table("usuarios").select("*").eq("correo", correo).limit(1).execute()
        return response.data[0] if response.data else None

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("usuarios").insert(data).execute()
        return response.data[0]
