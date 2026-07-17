from typing import Any

from fastapi import UploadFile

from app.repositories.producto import ProductoRepository
from app.services.product_image import ProductImageService


class ProductoService:
    def __init__(self, repository: ProductoRepository, image_service: ProductImageService) -> None:
        self.repository = repository
        self.image_service = image_service

    def get_all(self, search: str | None = None) -> list[dict[str, Any]]:
        return self.repository.get_all(search)

    def get_by_id(self, product_id: int) -> dict[str, Any] | None:
        return self.repository.get_by_id(product_id)

    async def create(self, data: dict[str, Any], image: UploadFile | None) -> dict[str, Any]:
        product_data = await self._prepare_data(data, image)
        created_product = self.repository.create(product_data)
        return self.repository.get_by_id(created_product["id"]) or created_product

    async def update(
        self,
        product: dict[str, Any],
        data: dict[str, Any],
        image: UploadFile | None,
    ) -> dict[str, Any]:
        product_data = await self._prepare_data(data, image)
        previous_image_path = product.get("imagen_path") if image else None
        updated_product = self.repository.update(product["id"], product_data)
        if previous_image_path:
            self.image_service.delete(previous_image_path)
        return self.repository.get_by_id(updated_product["id"]) or updated_product

    def delete(self, product: dict[str, Any]) -> None:
        self.repository.delete(product["id"])
        self.image_service.delete(product.get("imagen_path"))

    async def _prepare_data(
        self,
        data: dict[str, Any],
        image: UploadFile | None,
    ) -> dict[str, Any]:
        category_name = data.pop("categoria", None)
        if category_name is not None:
            data["categoria_id"] = self.repository.get_or_create_categoria_id(category_name)

        if image and image.filename:
            image_path, image_url = await self.image_service.upload(image)
            data["imagen_path"] = image_path
            data["imagen_url"] = image_url
        return data
