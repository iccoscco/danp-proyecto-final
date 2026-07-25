from typing import Annotated
from fastapi import APIRouter, Depends, HTTPException, status
from app.api.dependencies import require_admin
from app.core.supabase import get_supabase_client
from app.repositories.supabase_cliente import SupabaseClienteRepository
from app.schemas.cliente import ClienteResponse, ClienteUpdate
from app.services.supabase_cliente import SupabaseClienteService

router = APIRouter(prefix="/clientes", tags=["clientes"])

def get_service() -> SupabaseClienteService:
    return SupabaseClienteService(SupabaseClienteRepository(get_supabase_client()))

Service = Annotated[SupabaseClienteService, Depends(get_service)]
AdminUser = Annotated[dict[str, object], Depends(require_admin)]

@router.get("/", response_model=list[ClienteResponse])
def listar(service: Service, admin: AdminUser, buscar: str | None = None) -> list[ClienteResponse]:
    return [ClienteResponse.model_validate(c) for c in service.get_all(buscar)]

@router.get("/{id}", response_model=ClienteResponse)
def obtener(id: str, service: Service, admin: AdminUser) -> ClienteResponse:
    cliente = service.get_by_id(id)
    if not cliente:
        raise HTTPException(status_code=404, detail="Cliente no encontrado")
    return ClienteResponse.model_validate(cliente)

@router.put("/{id}", response_model=ClienteResponse)
def actualizar(id: str, data: ClienteUpdate, service: Service, admin: AdminUser) -> ClienteResponse:
    actualizado = service.update(id, data.model_dump(exclude_unset=True))
    return ClienteResponse.model_validate(actualizado)

@router.delete("/{id}", status_code=status.HTTP_204_NO_CONTENT)
def eliminar(id: str, service: Service, admin: AdminUser):
    service.delete(id)
    return None
