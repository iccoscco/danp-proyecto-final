from typing import Any
from app.core.security import get_password_hash
from app.repositories.supabase_cliente import SupabaseClienteRepository

class SupabaseClienteService:
    def __init__(self, repository: SupabaseClienteRepository) -> None:
        self.repository = repository

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        return self.repository.get_all(search)

    def get_by_id(self, cliente_id: str) -> dict[str, Any] | None:
        return self.repository.get_by_id(cliente_id)

    def get_by_correo(self, correo: str) -> dict[str, Any] | None:
        return self.repository.get_by_correo(correo)

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        if "contrasena" in data:
            data["password_hash"] = get_password_hash(data.pop("contrasena"))
        return self.repository.create(data)

    def update(self, cliente_id: str, data: dict[str, Any]) -> dict[str, Any]:
        if "contrasena" in data and data["contrasena"]:
            data["password_hash"] = get_password_hash(data.pop("contrasena"))
        elif "contrasena" in data:
            data.pop("contrasena")
        return self.repository.update(cliente_id, data)

    def delete(self, cliente_id: str) -> None:
        self.repository.delete(cliente_id)
