from app.models.pedido import Pedido
from app.repositories.pedido import PedidoRepository
from app.services.base import BaseService


class PedidoService(BaseService[Pedido]):
    def __init__(self, repository: PedidoRepository) -> None:
        super().__init__(repository)
