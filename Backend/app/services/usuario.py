from app.models.usuario import Usuario
from app.core.security import get_password_hash
from app.repositories.usuario import UsuarioRepository
from app.services.base import BaseService


class UsuarioService(BaseService[Usuario]):
    def __init__(self, repository: UsuarioRepository) -> None:
        super().__init__(repository)

    def get_by_correo(self, correo: str) -> Usuario | None:
        return self.repository.get_by_correo(correo)

    def create(self, data: dict[str, object]) -> Usuario:
        contrasena = data.pop("contrasena")
        data["password_hash"] = get_password_hash(str(contrasena))
        return super().create(data)

    def update(self, entity: Usuario, data: dict[str, object]) -> Usuario:
        contrasena = data.pop("contrasena", None)
        if contrasena is not None:
            data["password_hash"] = get_password_hash(str(contrasena))
        return super().update(entity, data)
