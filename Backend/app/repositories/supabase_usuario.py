from typing import Any

from supabase import Client


class SupabaseUsuarioRepository:
    def __init__(self, client: Client) -> None:
        self.client = client

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        response = (
            self.client.table("usuarios")
            .select("id,nombre,correo,rol,genero,estado,es_administrador,foto_url,foto_path")
            .order("nombre")
            .execute()
        )
        usuarios = response.data

        if not search:
            return usuarios

        term = search.lower().strip()
        return [
            usuario
            for usuario in usuarios
            if term in str(usuario.get("nombre", "")).lower()
            or term in str(usuario.get("correo", "")).lower()
            or term in str(usuario.get("rol", "")).lower()
        ]

    def get_by_id(self, usuario_id: str) -> dict[str, Any] | None:
        response = (
            self.client.table("usuarios")
            .select("*")
            .eq("id", usuario_id)
            .limit(1)
            .execute()
        )
        return response.data[0] if response.data else None

    def get_by_correo(self, correo: str) -> dict[str, Any] | None:
        response = (
            self.client.table("usuarios")
            .select("*")
            .eq("correo", correo)
            .limit(1)
            .execute()
        )
        return response.data[0] if response.data else None

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("usuarios").insert(data).execute()
        return response.data[0]

    def update(self, usuario_id: str, data: dict[str, Any]) -> dict[str, Any]:
        response = self.client.table("usuarios").update(data).eq("id", usuario_id).execute()
        return response.data[0]

    def delete(self, usuario_id: str) -> None:
        self.client.table("usuarios").delete().eq("id", usuario_id).execute()
