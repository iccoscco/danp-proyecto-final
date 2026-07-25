from typing import Annotated
from fastapi import APIRouter, Depends, HTTPException, status
from app.core.security import verify_password, create_access_token
from app.core.supabase import get_supabase_client
from app.repositories.supabase_cliente import SupabaseClienteRepository
from app.schemas.auth import TokenResponse
from app.schemas.cliente import ClienteCreate, ClienteLoginRequest, ClienteResponse
from app.services.supabase_cliente import SupabaseClienteService

router = APIRouter(prefix="/auth/cliente", tags=["autenticación clientes"])

def get_service() -> SupabaseClienteService:
    return SupabaseClienteService(SupabaseClienteRepository(get_supabase_client()))

Service = Annotated[SupabaseClienteService, Depends(get_service)]

@router.post("/register", response_model=ClienteResponse)
def register(data: ClienteCreate, service: Service) -> ClienteResponse:
    existente = service.get_by_correo(data.correo)
    if existente:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="El correo ya está registrado",
        )
    nuevo = service.create(data.model_dump())
    return ClienteResponse.model_validate(nuevo)

@router.post("/login", response_model=TokenResponse)
def login(data: ClienteLoginRequest, service: Service) -> TokenResponse:
    cliente = service.get_by_correo(data.correo)
    if cliente is None or not verify_password(data.contrasena, str(cliente["password_hash"])):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Credenciales inválidas",
        )

    if cliente.get("estado") == "Inactivo":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Cuenta desactivada",
        )

    token = create_access_token(str(cliente["id"]), rol="Cliente")
    return TokenResponse(access_token=token)
