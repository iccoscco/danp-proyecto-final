from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, Response, status
from sqlalchemy.orm import Session

from app.database.session import get_db
from app.repositories.pedido import PedidoRepository
from app.schemas.pedido import PedidoCreate, PedidoResponse, PedidoUpdate
from app.services.pedido import PedidoService

router = APIRouter(prefix="/pedidos", tags=["pedidos"])


def get_service(db: Annotated[Session, Depends(get_db)]) -> PedidoService:
    return PedidoService(PedidoRepository(db))


Service = Annotated[PedidoService, Depends(get_service)]


@router.get("/", response_model=list[PedidoResponse])
def get_pedidos(service: Service) -> list[PedidoResponse]:
    return service.get_all()


@router.get("/{pedido_id}", response_model=PedidoResponse)
def get_pedido(pedido_id: int, service: Service) -> PedidoResponse:
    pedido = service.get_by_id(pedido_id)
    if pedido is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Pedido no encontrado")
    return pedido


@router.post("/", response_model=PedidoResponse, status_code=status.HTTP_201_CREATED)
def create_pedido(data: PedidoCreate, service: Service) -> PedidoResponse:
    return service.create(data.model_dump())


@router.put("/{pedido_id}", response_model=PedidoResponse)
def update_pedido(pedido_id: int, data: PedidoUpdate, service: Service) -> PedidoResponse:
    pedido = service.get_by_id(pedido_id)
    if pedido is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Pedido no encontrado")
    return service.update(pedido, data.model_dump(exclude_unset=True))


@router.delete("/{pedido_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_pedido(pedido_id: int, service: Service) -> Response:
    pedido = service.get_by_id(pedido_id)
    if pedido is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Pedido no encontrado")
    service.delete(pedido)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
