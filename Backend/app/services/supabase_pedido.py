from typing import Any
from app.repositories.supabase_pedido import SupabasePedidoRepository

def _serializar(valor: Any) -> Any:
    from datetime import date
    from decimal import Decimal
    if isinstance(valor, date):
        return valor.isoformat()
    if isinstance(valor, Decimal):
        return float(valor)
    return valor


class SupabasePedidoService:
    def __init__(self, repository: SupabasePedidoRepository) -> None:
        self.repository = repository

    def get_all(self) -> list[dict[str, Any]]:
        return self.repository.get_all()

    def get_by_cliente(self, cliente_id: str) -> list[dict[str, Any]]:
        return self.repository.get_by_cliente(cliente_id)

    def get_by_id(self, pedido_id: int) -> dict[str, Any] | None:
        return self.repository.get_by_id(pedido_id)

    def create(self, cliente_id: str, data: dict[str, Any]) -> dict[str, Any]:
        detalles_raw = data.pop("detalles", [])

        # Serializar y calcular total
        detalles = []
        total = 0.0
        for d in detalles_raw:
            d_serializado = {k: _serializar(v) for k, v in d.items()}
            detalles.append(d_serializado)
            total += float(d["cantidad"]) * float(d["precio_unitario"])

        pedido_data = {
            "cliente_id": cliente_id,
            "total": float(total),
            "estado": data.get("estado", "Pendiente")
        }

        return self.repository.create(pedido_data, detalles)

    def update(self, pedido_id: int, data: dict[str, Any]) -> dict[str, Any]:
        detalles_raw = data.pop("detalles", None)

        # Si vienen detalles, recalculamos total
        if detalles_raw is not None:
            detalles = []
            total = 0.0
            for d in detalles_raw:
                d_serializado = {k: _serializar(v) for k, v in d.items()}
                detalles.append(d_serializado)
                total += float(d["cantidad"]) * float(d["precio_unitario"])
            data["total"] = total
        else:
            detalles = None

        # Serializar otros campos
        for k, v in data.items():
            if k != "detalles":
                data[k] = _serializar(v)

        return self.repository.update(pedido_id, data, detalles)

    def delete(self, pedido_id: int) -> None:
        self.repository.delete(pedido_id)
