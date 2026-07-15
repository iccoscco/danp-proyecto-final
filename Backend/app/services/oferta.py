from app.models.oferta import Oferta
from app.repositories.oferta import OfertaRepository
from app.services.base import BaseService


class OfertaService(BaseService[Oferta]):
    def __init__(self, repository: OfertaRepository) -> None:
        super().__init__(repository)
