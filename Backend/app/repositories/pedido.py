from app.models.pedido import Pedido
from app.repositories.base import BaseRepository


class PedidoRepository(BaseRepository[Pedido]):
    model = Pedido
