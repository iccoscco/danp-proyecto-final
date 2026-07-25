from typing import Annotated
from fastapi import APIRouter, Depends, HTTPException, Response, status
from app.api.dependencies import get_current_user
from app.core.supabase import get_supabase_client
from app.repositories.supabase_pedido import SupabasePedidoRepository
from app.schemas.pedido import PedidoCreate, PedidoResponse, PedidoUpdate
from app.services.supabase_pedido import SupabasePedidoService

router = APIRouter(
    prefix="/pedidos",
    tags=["pedidos"],
    dependencies=[Depends(get_current_user)],
)


def get_service() -> SupabasePedidoService:
    return SupabasePedidoService(SupabasePedidoRepository(get_supabase_client()))


Service = Annotated[SupabasePedidoService, Depends(get_service)]


@router.get("/", response_model=list[PedidoResponse])
def get_pedidos(
    service: Service,
    current_user: Annotated[dict[str, object], Depends(get_current_user)]
) -> list[PedidoResponse]:
    # Si es Cliente, solo ve sus pedidos. Si es Admin/Operador, ve todos.
    if current_user.get("rol") == "Cliente":
        return service.get_by_cliente(str(current_user["id"]))
    return service.get_all()


@router.get("/{pedido_id}", response_model=PedidoResponse)
def get_pedido(pedido_id: int, service: Service) -> PedidoResponse:
    pedido = service.get_by_id(pedido_id)
    if pedido is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Pedido no encontrado")
    return pedido


@router.post("/", response_model=PedidoResponse, status_code=status.HTTP_201_CREATED)
def create_pedido(
    data: PedidoCreate,
    service: Service,
    current_user: Annotated[dict[str, object], Depends(get_current_user)]
) -> PedidoResponse:
    if current_user.get("rol") != "Cliente":
         raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Solo los clientes pueden crear pedidos",
        )
    return service.create(str(current_user["id"]), data.model_dump())


@router.put("/{pedido_id}", response_model=PedidoResponse)
def update_pedido(pedido_id: int, data: PedidoUpdate, service: Service) -> PedidoResponse:
    return service.update(pedido_id, data.model_dump(exclude_unset=True))
