from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, Response, status
from sqlalchemy.orm import Session

from app.database.session import get_db
from app.api.dependencies import get_current_user
from app.repositories.categoria import CategoriaRepository
from app.schemas.categoria import CategoriaCreate, CategoriaResponse, CategoriaUpdate
from app.services.categoria import CategoriaService

router = APIRouter(
    prefix="/categorias",
    tags=["categorias"],
    dependencies=[Depends(get_current_user)],
)


def get_service(db: Annotated[Session, Depends(get_db)]) -> CategoriaService:
    return CategoriaService(CategoriaRepository(db))


Service = Annotated[CategoriaService, Depends(get_service)]


@router.get("/", response_model=list[CategoriaResponse])
def get_categorias(service: Service) -> list[CategoriaResponse]:
    return service.get_all()


@router.get("/{categoria_id}", response_model=CategoriaResponse)
def get_categoria(categoria_id: int, service: Service) -> CategoriaResponse:
    categoria = service.get_by_id(categoria_id)
    if categoria is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Categoría no encontrada")
    return categoria


@router.post("/", response_model=CategoriaResponse, status_code=status.HTTP_201_CREATED)
def create_categoria(data: CategoriaCreate, service: Service) -> CategoriaResponse:
    return service.create(data.model_dump())


@router.put("/{categoria_id}", response_model=CategoriaResponse)
def update_categoria(categoria_id: int, data: CategoriaUpdate, service: Service) -> CategoriaResponse:
    categoria = service.get_by_id(categoria_id)
    if categoria is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Categoría no encontrada")
    return service.update(categoria, data.model_dump(exclude_unset=True))


@router.delete("/{categoria_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_categoria(categoria_id: int, service: Service) -> Response:
    categoria = service.get_by_id(categoria_id)
    if categoria is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Categoría no encontrada")
    service.delete(categoria)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
