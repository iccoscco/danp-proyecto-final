from datetime import date
from decimal import Decimal
from typing import Any

from fastapi import UploadFile

from app.repositories.producto import ProductoRepository
from app.repositories.supabase_oferta import SupabaseOfertaRepository
from app.services.product_image import ProductImageService
from app.services.producto import ProductoService


def _serializar(valor: Any) -> Any:
    """Convierte date/Decimal a tipos JSON-serializables para Supabase/PostgREST."""
    if isinstance(valor, date):
        return valor.isoformat()
    if isinstance(valor, Decimal):
        return float(valor)
    return valor


class SupabaseOfertaService:
    def __init__(
        self,
        repository: SupabaseOfertaRepository,
        producto_service: ProductoService,
    ) -> None:
        self.repository = repository
        self.producto_service = producto_service

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        return self.repository.get_all(search)

    def get_by_id(self, oferta_id: int) -> dict[str, Any] | None:
        return self.repository.get_by_id(oferta_id)

    def crear_sobre_existente(self, data: dict[str, Any]) -> dict[str, Any]:
        """Oferta sobre un producto ya existente. Hereda la imagen del producto."""
        producto_id = int(data["producto_id"])
        # Tomamos la imagen del producto para que la oferta la muestre sin subida extra.
        producto = self.producto_service.get_by_id(producto_id)
        if producto is None:
            raise ValueError("Producto no encontrado")

        oferta_data = {
            "nombre": data["nombre"],
            "descuento": _serializar(data["descuento"]),
            "fecha_inicio": _serializar(data["fecha_inicio"]),
            "fecha_fin": _serializar(data["fecha_fin"]),
            "estado": data.get("estado", "Activa"),
            "producto_id": producto_id,
            "imagen_url": producto.get("imagen_url"),
        }
        creada = self.repository.create(oferta_data)
        return self.repository.get_by_id(creada["id"]) or creada

    async def crear_producto_y_oferta(
        self,
        producto: dict[str, Any],
        oferta: dict[str, Any],
        imagen: UploadFile | None,
    ) -> dict[str, Any]:
        """Crea un producto nuevo (con imagen opcional) y su oferta asociada."""
        nuevo_producto = await self.producto_service.create(producto, imagen)

        oferta_data = {
            "nombre": oferta["nombre"],
            "descuento": _serializar(oferta["descuento"]),
            "fecha_inicio": _serializar(oferta["fecha_inicio"]),
            "fecha_fin": _serializar(oferta["fecha_fin"]),
            "estado": oferta.get("estado", "Activa"),
            "producto_id": int(nuevo_producto["id"]),
            "imagen_url": nuevo_producto.get("imagen_url"),
        }
        creada = self.repository.create(oferta_data)
        return self.repository.get_by_id(creada["id"]) or creada

    def update(self, oferta_id: int, data: dict[str, Any]) -> dict[str, Any]:
        data_serializada = {key: _serializar(value) for key, value in data.items()}
        return self.repository.update(oferta_id, data_serializada)

    def delete(self, oferta_id: int) -> None:
        self.repository.delete(oferta_id)
