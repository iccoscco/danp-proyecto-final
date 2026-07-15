from app.models.categoria import Categoria
from app.repositories.base import BaseRepository


class CategoriaRepository(BaseRepository[Categoria]):
    model = Categoria
