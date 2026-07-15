from decimal import Decimal

from pydantic import BaseModel, ConfigDict


class DetallePedidoCreate(BaseModel):
    cantidad: int
    precio_unitario: Decimal
    subtotal: Decimal
    pedido_id: int
    producto_id: int


class DetallePedidoUpdate(BaseModel):
    cantidad: int | None = None
    precio_unitario: Decimal | None = None
    subtotal: Decimal | None = None
    pedido_id: int | None = None
    producto_id: int | None = None


class DetallePedidoResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    cantidad: int
    precio_unitario: Decimal
    subtotal: Decimal
    pedido_id: int
    producto_id: int
