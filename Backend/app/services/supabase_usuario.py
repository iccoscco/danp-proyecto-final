from typing import Any

from app.core.security import get_password_hash
from app.repositories.supabase_usuario import SupabaseUsuarioRepository


class SupabaseUsuarioService:
    def __init__(self, repository: SupabaseUsuarioRepository) -> None:
        self.repository = repository

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        return self.repository.get_all(search)

    def get_by_id(self, usuario_id: str) -> dict[str, Any] | None:
        return self.repository.get_by_id(usuario_id)

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        return self.repository.create(self._prepare(data))

    def update(self, usuario_id: str, data: dict[str, Any]) -> dict[str, Any]:
        return self.repository.update(usuario_id, self._prepare(data))

    def delete(self, usuario_id: str) -> None:
        self.repository.delete(usuario_id)

    @staticmethod
    def _prepare(data: dict[str, Any]) -> dict[str, Any]:
        prepared = dict(data)

        # La contraseña viaja como "contrasena" desde el esquema y se persiste hasheada.
        contrasena = prepared.pop("contrasena", None)
        if contrasena:
            prepared["password_hash"] = get_password_hash(str(contrasena))

        # Mantenemos es_administrador sincronizado con el rol.
        rol = prepared.get("rol")
        if rol is not None:
            prepared["es_administrador"] = rol == "Administrador"

        # El estado por defecto es "Activo" si no viene especificado.
        prepared.setdefault("estado", "Activo")

        return prepared
