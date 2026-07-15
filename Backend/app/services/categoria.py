from app.models.categoria import Categoria
from app.repositories.categoria import CategoriaRepository
from app.services.base import BaseService


class CategoriaService(BaseService[Categoria]):
    def __init__(self, repository: CategoriaRepository) -> None:
        super().__init__(repository)
