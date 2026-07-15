from app.models.oferta import Oferta
from app.repositories.base import BaseRepository


class OfertaRepository(BaseRepository[Oferta]):
    model = Oferta
