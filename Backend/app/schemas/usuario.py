from pydantic import BaseModel, ConfigDict


class UsuarioCreate(BaseModel):
    nombre: str
    correo: str
    contrasena: str
    estado: str


class UsuarioUpdate(BaseModel):
    nombre: str | None = None
    correo: str | None = None
    contrasena: str | None = None
    estado: str | None = None


class UsuarioResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    nombre: str
    correo: str
    es_administrador: bool
    estado: str
