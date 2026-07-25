from datetime import date
from decimal import Decimal

from pydantic import BaseModel, ConfigDict, Field


class OfertaExistenteCreate(BaseModel):
    """Crea una oferta sobre un producto que ya existe."""
    producto_id: int
    nombre: str
    descuento: Decimal = Field(ge=0, le=100)
    fecha_inicio: date
    fecha_fin: date
    estado: str = "Activa"


class OfertaUpdate(BaseModel):
    nombre: str | None = None
    descuento: Decimal | None = Field(default=None, ge=0, le=100)
    fecha_inicio: date | None = None
    fecha_fin: date | None = None
    estado: str | None = None


class OfertaResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    nombre: str
    descuento: Decimal
    fecha_inicio: date
    fecha_fin: date
    estado: str
    producto_id: int
    imagen_url: str | None = None
    nombre_producto: str | None = None
    precio_original: Decimal | None = None
    stock_producto: int | None = None
    fecha_vencimiento: date | None = None
