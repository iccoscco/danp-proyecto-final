from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, Response, status

from app.api.dependencies import require_admin
from app.core.supabase import get_supabase_client
from app.repositories.supabase_usuario import SupabaseUsuarioRepository
from app.schemas.usuario import UsuarioCreate, UsuarioResponse, UsuarioUpdate
from app.services.supabase_usuario import SupabaseUsuarioService

router = APIRouter(
    prefix="/usuarios",
    tags=["usuarios"],
    dependencies=[Depends(require_admin)],
)


def get_service() -> SupabaseUsuarioService:
    client = get_supabase_client()
    return SupabaseUsuarioService(SupabaseUsuarioRepository(client))


Service = Annotated[SupabaseUsuarioService, Depends(get_service)]


def _to_response(usuario: dict[str, object]) -> UsuarioResponse:
    return UsuarioResponse(
        id=str(usuario["id"]),
        nombre=str(usuario["nombre"]),
        correo=str(usuario["correo"]),
        rol=str(usuario.get("rol") or ("Administrador" if usuario.get("es_administrador") else "Operador")),
        genero=usuario.get("genero"),
        estado=str(usuario.get("estado") or "Activo"),
        es_administrador=bool(usuario.get("es_administrador") or usuario.get("rol") == "Administrador"),
        foto_url=usuario.get("foto_url"),
    )


@router.get("/", response_model=list[UsuarioResponse])
def get_usuarios(service: Service, buscar: str | None = None) -> list[UsuarioResponse]:
    return [_to_response(usuario) for usuario in service.get_all(buscar)]


@router.get("/{usuario_id}", response_model=UsuarioResponse)
def get_usuario(usuario_id: str, service: Service) -> UsuarioResponse:
    usuario = service.get_by_id(usuario_id)
    if usuario is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    return _to_response(usuario)


@router.post("/", response_model=UsuarioResponse, status_code=status.HTTP_201_CREATED)
def create_usuario(data: UsuarioCreate, service: Service) -> UsuarioResponse:
    return _to_response(service.create(data.model_dump()))


@router.put("/{usuario_id}", response_model=UsuarioResponse)
def update_usuario(usuario_id: str, data: UsuarioUpdate, service: Service) -> UsuarioResponse:
    usuario = service.get_by_id(usuario_id)
    if usuario is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    return _to_response(service.update(usuario_id, data.model_dump(exclude_unset=True)))


@router.delete("/{usuario_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_usuario(usuario_id: str, service: Service) -> Response:
    usuario = service.get_by_id(usuario_id)
    if usuario is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    service.delete(usuario_id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
