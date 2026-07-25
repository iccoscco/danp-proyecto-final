"""REST API routers."""

from app.routers.auth import router as auth_router
from app.routers.auth_cliente import router as auth_cliente_router
from app.routers.categorias import router as categorias_router
from app.routers.clientes import router as clientes_router
from app.routers.ofertas import router as ofertas_router
from app.routers.pedidos import router as pedidos_router
from app.routers.productos import router as productos_router
from app.routers.usuarios import router as usuarios_router

__all__ = [
    "auth_router",
    "auth_cliente_router",
    "categorias_router",
    "clientes_router",
    "ofertas_router",
    "pedidos_router",
    "productos_router",
    "usuarios_router",
]
