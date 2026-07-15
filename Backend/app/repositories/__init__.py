"""SQLAlchemy repositories."""

from app.repositories.categoria import CategoriaRepository
from app.repositories.detalle_pedido import DetallePedidoRepository
from app.repositories.oferta import OfertaRepository
from app.repositories.pedido import PedidoRepository
from app.repositories.producto import ProductoRepository
from app.repositories.usuario import UsuarioRepository

__all__ = [
    "CategoriaRepository",
    "DetallePedidoRepository",
    "OfertaRepository",
    "PedidoRepository",
    "ProductoRepository",
    "UsuarioRepository",
]
