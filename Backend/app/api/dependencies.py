from typing import Annotated

from fastapi import Depends, HTTPException, status
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from app.core.security import decode_access_token
from app.core.supabase import get_supabase_client
from app.repositories.supabase_usuario import SupabaseUsuarioRepository

bearer_scheme = HTTPBearer(auto_error=False)


def get_current_user(
    credentials: Annotated[HTTPAuthorizationCredentials | None, Depends(bearer_scheme)],
) -> dict[str, object]:
    unauthorized = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="No autenticado",
        headers={"WWW-Authenticate": "Bearer"},
    )

    if credentials is None:
        raise unauthorized

    try:
        payload = decode_access_token(credentials.credentials)
        user_id = str(payload["sub"])
    except (KeyError, TypeError, ValueError):
        raise unauthorized

    user = SupabaseUsuarioRepository(get_supabase_client()).get_by_id(user_id)
    if user is None:
        raise unauthorized
    return user


def require_admin(
    current_user: Annotated[dict[str, object], Depends(get_current_user)],
) -> dict[str, object]:
    if not (current_user.get("es_administrador") or current_user.get("rol") == "Administrador"):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Se requiere rol de Administrador",
        )
    return current_user
