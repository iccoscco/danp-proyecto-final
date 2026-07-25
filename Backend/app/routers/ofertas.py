from datetime import date
from decimal import Decimal
from typing import Annotated

from fastapi import APIRouter, Depends, File, Form, HTTPException, Response, UploadFile, status

from app.api.dependencies import get_current_user
from app.core.supabase import get_supabase_client
from app.repositories.producto import ProductoRepository
from app.repositories.supabase_oferta import SupabaseOfertaRepository
from app.schemas.oferta import OfertaExistenteCreate, OfertaResponse, OfertaUpdate
from app.services.product_image import ProductImageService
from app.services.producto import ProductoService
from app.services.supabase_oferta import SupabaseOfertaService

router = APIRouter(
    prefix="/ofertas",
    tags=["ofertas"],
    dependencies=[Depends(get_current_user)],
)


def get_service() -> SupabaseOfertaService:
    client = get_supabase_client()
    producto_service = ProductoService(ProductoRepository(client), ProductImageService(client))
    return SupabaseOfertaService(SupabaseOfertaRepository(client), producto_service)


Service = Annotated[SupabaseOfertaService, Depends(get_service)]


def _to_response(oferta: dict[str, object]) -> OfertaResponse:
    return OfertaResponse(
        id=int(oferta["id"]),
        nombre=str(oferta["nombre"]),
        descuento=oferta["descuento"],
        fecha_inicio=oferta["fecha_inicio"],
        fecha_fin=oferta["fecha_fin"],
        estado=str(oferta.get("estado") or "Activa"),
        producto_id=int(oferta["producto_id"]),
        imagen_url=oferta.get("imagen_url"),
        nombre_producto=oferta.get("nombre_producto"),
        precio_original=oferta.get("precio_original"),
        stock_producto=oferta.get("stock_producto"),
        fecha_vencimiento=oferta.get("fecha_vencimiento"),
    )


@router.get("/", response_model=list[OfertaResponse])
def get_ofertas(service: Service, buscar: str | None = None) -> list[OfertaResponse]:
    return [_to_response(o) for o in service.get_all(buscar)]


@router.get("/{oferta_id}", response_model=OfertaResponse)
def get_oferta(oferta_id: int, service: Service) -> OfertaResponse:
    oferta = service.get_by_id(oferta_id)
    if oferta is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Oferta no encontrada")
    return _to_response(oferta)


@router.post("/existente", response_model=OfertaResponse, status_code=status.HTTP_201_CREATED)
def crear_oferta_existente(data: OfertaExistenteCreate, service: Service) -> OfertaResponse:
    try:
        oferta = service.crear_sobre_existente(data.model_dump())
    except ValueError as exc:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=str(exc)) from exc
    return _to_response(oferta)


@router.post("/", response_model=OfertaResponse, status_code=status.HTTP_201_CREATED)
async def crear_producto_y_oferta(
    service: Service,
    # Campos del producto
    nombre_producto: str = Form(...),
    categoria: str = Form(...),
    precio: Decimal = Form(...),
    stock: int = Form(...),
    fecha_vencimiento: date = Form(...),
    imagen: UploadFile | None = File(default=None),
    # Campos de la oferta
    nombre_oferta: str = Form(...),
    descuento: Decimal = Form(..., ge=0, le=100),
    fecha_inicio: date = Form(...),
    fecha_fin: date = Form(...),
    estado: str = Form(default="Activa"),
) -> OfertaResponse:
    if fecha_fin < fecha_inicio:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="La fecha fin no puede ser anterior a la fecha de inicio")

    producto = {
        "nombre": nombre_producto,
        "categoria": categoria,
        "precio": str(precio),
        "stock": stock,
        "fecha_vencimiento": fecha_vencimiento.isoformat(),
    }
    oferta = {
        "nombre": nombre_oferta,
        "descuento": descuento,
        "fecha_inicio": fecha_inicio.isoformat(),
        "fecha_fin": fecha_fin.isoformat(),
        "estado": estado,
    }
    creada = await service.crear_producto_y_oferta(producto, oferta, imagen)
    return _to_response(creada)


@router.put("/{oferta_id}", response_model=OfertaResponse)
def update_oferta(oferta_id: int, data: OfertaUpdate, service: Service) -> OfertaResponse:
    oferta = service.get_by_id(oferta_id)
    if oferta is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Oferta no encontrada")
    actualizada = service.update(oferta_id, data.model_dump(exclude_unset=True))
    return _to_response(service.get_by_id(oferta_id) or actualizada)


@router.delete("/{oferta_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_oferta(oferta_id: int, service: Service) -> Response:
    oferta = service.get_by_id(oferta_id)
    if oferta is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Oferta no encontrada")
    service.delete(oferta_id)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
