from app.core.config import settings
from app.core.security import get_password_hash
from app.core.supabase import get_supabase_client
from app.repositories.supabase_usuario import SupabaseUsuarioRepository


def create_master_admin() -> None:
    repository = SupabaseUsuarioRepository(get_supabase_client())
    if repository.get_by_correo(settings.master_admin_email) is None:
        repository.create(
            {
                "nombre": "Mika",
                "correo": settings.master_admin_email,
                "password_hash": get_password_hash(settings.master_admin_password),
                "estado": "Activo",
                "es_administrador": True,
            }
        )
