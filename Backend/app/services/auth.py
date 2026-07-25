from app.core.security import create_access_token, verify_password
from app.repositories.supabase_usuario import SupabaseUsuarioRepository


class AuthService:
    def __init__(self, usuario_repository: SupabaseUsuarioRepository) -> None:
        self.usuario_repository = usuario_repository

    def authenticate(self, correo: str, contrasena: str) -> dict[str, object] | None:
        usuario = self.usuario_repository.get_by_correo(correo)
        if usuario is None or not verify_password(contrasena, str(usuario["password_hash"])):
            return None
        return usuario

    def create_token(self, usuario: dict[str, object]) -> str:
        rol = str(usuario.get("rol") or ("Administrador" if usuario.get("es_administrador") else "Operador"))
        return create_access_token(str(usuario["id"]), rol)
