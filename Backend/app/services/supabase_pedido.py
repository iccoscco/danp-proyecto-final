from typing import Any
from app.repositories.supabase_pedido import SupabasePedidoRepository

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
        detalles = data.pop("detalles", [])

        # Calcular total
        total = sum(d["cantidad"] * float(d["precio_unitario"]) for d in detalles)

        pedido_data = {
            "cliente_id": cliente_id,
            "total": total,
            "estado": data.get("estado", "Pendiente")
        }

        return self.repository.create(pedido_data, detalles)

    def update(self, pedido_id: int, data: dict[str, Any]) -> dict[str, Any]:
        # Si viene el total como Decimal, convertir a float
        if "total" in data and data["total"] is not None:
            data["total"] = float(data["total"])
        return self.repository.update(pedido_id, data)

    def delete(self, pedido_id: int) -> None:
        self.repository.delete(pedido_id)
