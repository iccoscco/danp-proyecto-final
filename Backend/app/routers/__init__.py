"""REST API routers."""

from app.routers.categorias import router as categorias_router
from app.routers.ofertas import router as ofertas_router
from app.routers.pedidos import router as pedidos_router
from app.routers.productos import router as productos_router
from app.routers.usuarios import router as usuarios_router

__all__ = [
    "categorias_router",
    "ofertas_router",
    "pedidos_router",
    "productos_router",
    "usuarios_router",
]
