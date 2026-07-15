from datetime import datetime
from decimal import Decimal

from pydantic import BaseModel, ConfigDict


class PedidoCreate(BaseModel):
    fecha: datetime
    total: Decimal
    estado: str
    usuario_id: int


class PedidoUpdate(BaseModel):
    fecha: datetime | None = None
    total: Decimal | None = None
    estado: str | None = None
    usuario_id: int | None = None


class PedidoResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    fecha: datetime
    total: Decimal
    estado: str
    usuario_id: int
