from app.core.security import create_access_token, verify_password
from app.models.usuario import Usuario
from app.repositories.usuario import UsuarioRepository


class AuthService:
    def __init__(self, usuario_repository: UsuarioRepository) -> None:
        self.usuario_repository = usuario_repository

    def authenticate(self, correo: str, contrasena: str) -> Usuario | None:
        usuario = self.usuario_repository.get_by_correo(correo)
        if usuario is None or not verify_password(contrasena, usuario.password_hash):
            return None
        return usuario

    def create_token(self, usuario: Usuario) -> str:
        return create_access_token(str(usuario.id))
