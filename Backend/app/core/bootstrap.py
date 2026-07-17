from sqlalchemy.orm import Session

from app.core.config import settings
from app.repositories.usuario import UsuarioRepository
from app.services.usuario import UsuarioService


def create_admin_user(db: Session) -> None:
    if not settings.admin_email or not settings.admin_password:
        return

    service = UsuarioService(UsuarioRepository(db))
    if service.get_by_correo(settings.admin_email) is None:
        service.create(
            {
                "nombre": "Administrador",
                "correo": settings.admin_email,
                "contrasena": settings.admin_password,
                "estado": "Activo",
                "es_administrador": True,
            }
        )
