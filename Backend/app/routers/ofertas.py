from typing import Annotated

from fastapi import APIRouter, Depends, HTTPException, Response, status
from sqlalchemy.orm import Session

from app.database.session import get_db
from app.api.dependencies import get_current_user
from app.repositories.oferta import OfertaRepository
from app.schemas.oferta import OfertaCreate, OfertaResponse, OfertaUpdate
from app.services.oferta import OfertaService

router = APIRouter(
    prefix="/ofertas",
    tags=["ofertas"],
    dependencies=[Depends(get_current_user)],
)


def get_service(db: Annotated[Session, Depends(get_db)]) -> OfertaService:
    return OfertaService(OfertaRepository(db))


Service = Annotated[OfertaService, Depends(get_service)]


@router.get("/", response_model=list[OfertaResponse])
def get_ofertas(service: Service) -> list[OfertaResponse]:
    return service.get_all()


@router.get("/{oferta_id}", response_model=OfertaResponse)
def get_oferta(oferta_id: int, service: Service) -> OfertaResponse:
    oferta = service.get_by_id(oferta_id)
    if oferta is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Oferta no encontrada")
    return oferta


@router.post("/", response_model=OfertaResponse, status_code=status.HTTP_201_CREATED)
def create_oferta(data: OfertaCreate, service: Service) -> OfertaResponse:
    return service.create(data.model_dump())


@router.put("/{oferta_id}", response_model=OfertaResponse)
def update_oferta(oferta_id: int, data: OfertaUpdate, service: Service) -> OfertaResponse:
    oferta = service.get_by_id(oferta_id)
    if oferta is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Oferta no encontrada")
    return service.update(oferta, data.model_dump(exclude_unset=True))


@router.delete("/{oferta_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_oferta(oferta_id: int, service: Service) -> Response:
    oferta = service.get_by_id(oferta_id)
    if oferta is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Oferta no encontrada")
    service.delete(oferta)
    return Response(status_code=status.HTTP_204_NO_CONTENT)
