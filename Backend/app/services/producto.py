from app.models.producto import Producto
from app.repositories.producto import ProductoRepository
from app.services.base import BaseService


class ProductoService(BaseService[Producto]):
    def __init__(self, repository: ProductoRepository) -> None:
        super().__init__(repository)
