from sqlalchemy import select

from app.models.usuario import Usuario
from app.repositories.base import BaseRepository


class UsuarioRepository(BaseRepository[Usuario]):
    model = Usuario

    def get_by_correo(self, correo: str) -> Usuario | None:
        return self.db.scalar(select(Usuario).where(Usuario.correo == correo))
