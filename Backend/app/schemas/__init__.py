"""Pydantic schemas for API data contracts."""

from app.schemas.categoria import CategoriaCreate, CategoriaResponse, CategoriaUpdate
from app.schemas.auth import LoginRequest, TokenResponse
from app.schemas.detalle_pedido import (
    DetallePedidoCreate,
    DetallePedidoResponse,
    DetallePedidoUpdate,
)
from app.schemas.oferta import OfertaExistenteCreate, OfertaResponse, OfertaUpdate
from app.schemas.pedido import PedidoCreate, PedidoResponse, PedidoUpdate
from app.schemas.producto import ProductoCreate, ProductoResponse, ProductoUpdate
from app.schemas.usuario import UsuarioCreate, UsuarioResponse, UsuarioUpdate

__all__ = [
    "CategoriaCreate",
    "CategoriaResponse",
    "CategoriaUpdate",
    "LoginRequest",
    "DetallePedidoCreate",
    "DetallePedidoResponse",
    "DetallePedidoUpdate",
    "OfertaExistenteCreate",
    "OfertaResponse",
    "OfertaUpdate",
    "PedidoCreate",
    "PedidoResponse",
    "PedidoUpdate",
    "ProductoCreate",
    "ProductoResponse",
    "ProductoUpdate",
    "UsuarioCreate",
    "UsuarioResponse",
    "UsuarioUpdate",
    "TokenResponse",
]
