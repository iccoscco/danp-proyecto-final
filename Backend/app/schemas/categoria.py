from pydantic import BaseModel, ConfigDict


class CategoriaCreate(BaseModel):
    nombre: str
    descripcion: str | None = None


class CategoriaUpdate(BaseModel):
    nombre: str | None = None
    descripcion: str | None = None


class CategoriaResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    nombre: str
    descripcion: str | None
