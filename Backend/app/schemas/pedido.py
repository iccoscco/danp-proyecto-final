from datetime import datetime
from decimal import Decimal
from pydantic import BaseModel, ConfigDict


class PedidoDetalleBase(BaseModel):
    producto_id: int | None = None
    oferta_id: int | None = None
    cantidad: int
    precio_unitario: Decimal


class PedidoDetalleResponse(PedidoDetalleBase):
    id: int
    nombre_item: str | None = None # Nombre del producto u oferta
    imagen_url: str | None = None


class PedidoBase(BaseModel):
    estado: str = "Pendiente"


class PedidoCreate(PedidoBase):
    # En el Create el cliente_id se tomará del token
    detalles: list[PedidoDetalleBase]


class PedidoUpdate(BaseModel):
    estado: str | None = None
    total: Decimal | None = None
    detalles: list[PedidoDetalleBase] | None = None


class PedidoResponse(PedidoBase):
    model_config = ConfigDict(from_attributes=True)

    id: int
    numero_pedido: str
    cliente_id: str
    cliente_nombre: str | None = None
    fecha: datetime
    total: Decimal
    detalles: list[PedidoDetalleResponse] = []
