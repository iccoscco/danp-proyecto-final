from app.models.usuario import Usuario
from app.repositories.usuario import UsuarioRepository
from app.services.base import BaseService


class UsuarioService(BaseService[Usuario]):
    def __init__(self, repository: UsuarioRepository) -> None:
        super().__init__(repository)
