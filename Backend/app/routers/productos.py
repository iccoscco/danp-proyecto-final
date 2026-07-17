from datetime import date
from decimal import Decimal
from typing import Annotated

from fastapi import APIRouter, Depends, File, Form, HTTPException, Response, UploadFile, status

from app.api.dependencies import get_current_user
from app.core.supabase import get_supabase_client
from app.repositories.producto import ProductoRepository
from app.schemas.producto import ProductoResponse
from app.services.product_image import ProductImageService
from app.services.producto import ProductoService

router = APIRouter(
    prefix="/productos",
    tags=["productos"],
    dependencies=[Depends(get_current_user)],
)


def get_service() -> ProductoService:
    client = get_supabase_client()
    return ProductoService(ProductoRepository(client), ProductImageService(client))


Service = Annotated[ProductoService, Depends(get_service)]


@router.get("/", response_model=list[ProductoResponse])
def get_productos(service: Service, buscar: str | None = None) -> list[ProductoResponse]:
    return service.get_all(buscar)


@router.get("/{producto_id}", response_model=ProductoResponse)
def get_producto(producto_id: int, service: Service) -> ProductoResponse:
    producto = service.get_by_id(producto_id)
    if producto is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Producto no encontrado")
    return producto


@router.post("/", response_model=ProductoResponse, status_code=status.HTTP_201_CREATED)
async def create_producto(
    service: Service,
    nombre: str = Form(...),
    categoria: str = Form(...),
    precio: Decimal = Form(...),
    stock: int = Form(...),
    fecha_vencimiento: date = Form(...),
    imagen: UploadFile | None = File(default=None),
) -> ProductoResponse:
    return await service.create(
        {
            "nombre": nombre,
            "categoria": categoria,
            "precio": str(precio),
            "stock": stock,
            "fecha_vencimiento": fecha_vencimiento.isoformat(),
        },
        imagen,
    )


@router.put("/{producto_id}", response_model=ProductoResponse)
async def update_producto(
    producto_id: int,
    service: Service,
    nombre: str | None = Form(default=None),
    categoria: str | None = Form(default=None),
    precio: Decimal | None = Form(default=None),
    stock: int | None = Form(default=None),
    fecha_vencimiento: date | None = Form(default=None),
    imagen: UploadFile | None = File(default=None),
) -> ProductoResponse:
    producto = service.get_by_id(producto_id)
    if producto is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Producto no encontrado")

    data = {
        key: value.isoformat() if isinstance(value, date) else str(value) if isinstance(value, Decimal) else value
        for key, value in {
            "nombre": nombre,
            "categoria": categoria,
            "precio": precio,
            "stock": stock,
            "fecha_vencimiento": fecha_vencimiento,
        }.items()
        if value is not None
    }
    return await service.update(producto, data, imagen)


@router.delete("/{producto_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_producto(producto_id: int, service: Service) -> Response:
    producto = service.get_by_id(producto_id)
    if producto is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Producto no encontrado")
    service.delete(producto)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
