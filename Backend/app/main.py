from contextlib import asynccontextmanager

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.core.bootstrap import create_admin_user
from app.core.config import settings
from app.database.session import SessionLocal
from app.routers import (
    auth_router,
    categorias_router,
    ofertas_router,
    pedidos_router,
    productos_router,
    usuarios_router,
)


@asynccontextmanager
async def lifespan(_: FastAPI):
    db = SessionLocal()
    try:
        create_admin_user(db)
    finally:
        db.close()
    yield


app = FastAPI(title=settings.app_name, version=settings.app_version, lifespan=lifespan)

app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.frontend_origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(auth_router)
app.include_router(usuarios_router)
app.include_router(productos_router)
app.include_router(categorias_router)
app.include_router(ofertas_router)
app.include_router(pedidos_router)
