from app.models.detalle_pedido import DetallePedido
from app.repositories.detalle_pedido import DetallePedidoRepository
from app.services.base import BaseService


class DetallePedidoService(BaseService[DetallePedido]):
    def __init__(self, repository: DetallePedidoRepository) -> None:
        super().__init__(repository)
