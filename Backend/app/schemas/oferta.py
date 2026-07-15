from datetime import date
from decimal import Decimal

from pydantic import BaseModel, ConfigDict


class OfertaCreate(BaseModel):
    nombre: str
    descuento: Decimal
    fecha_inicio: date
    fecha_fin: date
    estado: str
    producto_id: int


class OfertaUpdate(BaseModel):
    nombre: str | None = None
    descuento: Decimal | None = None
    fecha_inicio: date | None = None
    fecha_fin: date | None = None
    estado: str | None = None
    producto_id: int | None = None


class OfertaResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    nombre: str
    descuento: Decimal
    fecha_inicio: date
    fecha_fin: date
    estado: str
    producto_id: int
