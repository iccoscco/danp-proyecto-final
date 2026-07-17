from datetime import date
from decimal import Decimal

from pydantic import BaseModel, ConfigDict


class ProductoCreate(BaseModel):
    nombre: str
    precio: Decimal
    stock: int
    fecha_vencimiento: date
    categoria: str


class ProductoUpdate(BaseModel):
    nombre: str | None = None
    precio: Decimal | None = None
    stock: int | None = None
    fecha_vencimiento: date | None = None
    categoria: str | None = None
    categoria_id: int | None = None


class ProductoResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    nombre: str
    precio: Decimal
    stock: int
    fecha_vencimiento: date
    categoria_id: int
    categoria: str
    imagen_url: str | None = None
