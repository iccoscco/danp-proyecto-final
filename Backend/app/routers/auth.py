from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, status
from app.core.supabase import get_supabase_client
from app.repositories.supabase_usuario import SupabaseUsuarioRepository
from app.schemas.auth import LoginRequest, TokenResponse
from app.services.auth import AuthService

router = APIRouter(prefix="/auth", tags=["autenticación"])


def get_service() -> AuthService:
    return AuthService(SupabaseUsuarioRepository(get_supabase_client()))


Service = Annotated[AuthService, Depends(get_service)]


@router.post("/login", response_model=TokenResponse)
def login(data: LoginRequest, service: Service) -> TokenResponse:
    usuario = service.authenticate(data.correo, data.contrasena)
    if usuario is None:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Credenciales inválidas",
        )
    return TokenResponse(access_token=service.create_token(usuario))
