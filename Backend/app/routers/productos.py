from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, Response, status
from sqlalchemy.orm import Session

from app.database.session import get_db
from app.repositories.producto import ProductoRepository
from app.schemas.producto import ProductoCreate, ProductoResponse, ProductoUpdate
from app.services.producto import ProductoService

router = APIRouter(prefix="/productos", tags=["productos"])


def get_service(db: Annotated[Session, Depends(get_db)]) -> ProductoService:
    return ProductoService(ProductoRepository(db))


Service = Annotated[ProductoService, Depends(get_service)]


@router.get("/", response_model=list[ProductoResponse])
def get_productos(service: Service) -> list[ProductoResponse]:
    return service.get_all()


@router.get("/{producto_id}", response_model=ProductoResponse)
def get_producto(producto_id: int, service: Service) -> ProductoResponse:
    producto = service.get_by_id(producto_id)
    if producto is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Producto no encontrado")
    return producto


@router.post("/", response_model=ProductoResponse, status_code=status.HTTP_201_CREATED)
def create_producto(data: ProductoCreate, service: Service) -> ProductoResponse:
    return service.create(data.model_dump())


@router.put("/{producto_id}", response_model=ProductoResponse)
def update_producto(producto_id: int, data: ProductoUpdate, service: Service) -> ProductoResponse:
    producto = service.get_by_id(producto_id)
    if producto is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Producto no encontrado")
    return service.update(producto, data.model_dump(exclude_unset=True))


@router.delete("/{producto_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_producto(producto_id: int, service: Service) -> Response:
    producto = service.get_by_id(producto_id)
    if producto is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Producto no encontrado")
    service.delete(producto)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
