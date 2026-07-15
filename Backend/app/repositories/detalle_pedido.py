from app.models.detalle_pedido import DetallePedido
from app.repositories.base import BaseRepository


class DetallePedidoRepository(BaseRepository[DetallePedido]):
    model = DetallePedido
