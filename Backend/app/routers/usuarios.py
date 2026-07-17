from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, Response, status
from sqlalchemy.orm import Session

from app.database.session import get_db
from app.api.dependencies import get_current_user
from app.repositories.usuario import UsuarioRepository
from app.schemas.usuario import UsuarioCreate, UsuarioResponse, UsuarioUpdate
from app.services.usuario import UsuarioService

router = APIRouter(
    prefix="/usuarios",
    tags=["usuarios"],
    dependencies=[Depends(get_current_user)],
)


def get_service(db: Annotated[Session, Depends(get_db)]) -> UsuarioService:
    return UsuarioService(UsuarioRepository(db))


Service = Annotated[UsuarioService, Depends(get_service)]


@router.get("/", response_model=list[UsuarioResponse])
def get_usuarios(service: Service) -> list[UsuarioResponse]:
    return service.get_all()


@router.get("/{usuario_id}", response_model=UsuarioResponse)
def get_usuario(usuario_id: int, service: Service) -> UsuarioResponse:
    usuario = service.get_by_id(usuario_id)
    if usuario is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    return usuario


@router.post("/", response_model=UsuarioResponse, status_code=status.HTTP_201_CREATED)
def create_usuario(data: UsuarioCreate, service: Service) -> UsuarioResponse:
    return service.create(data.model_dump())


@router.put("/{usuario_id}", response_model=UsuarioResponse)
def update_usuario(usuario_id: int, data: UsuarioUpdate, service: Service) -> UsuarioResponse:
    usuario = service.get_by_id(usuario_id)
    if usuario is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    return service.update(usuario, data.model_dump(exclude_unset=True))


@router.delete("/{usuario_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_usuario(usuario_id: int, service: Service) -> Response:
    usuario = service.get_by_id(usuario_id)
    if usuario is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    service.delete(usuario)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
