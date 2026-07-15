from app.models.producto import Producto
from app.repositories.base import BaseRepository


class ProductoRepository(BaseRepository[Producto]):
    model = Producto
