from pydantic import BaseModel, ConfigDict, EmailStr, Field


class UsuarioCreate(BaseModel):
    nombre: str
    correo: EmailStr
    contrasena: str = Field(min_length=6)
    estado: str


class UsuarioUpdate(BaseModel):
    nombre: str | None = None
    correo: EmailStr | None = None
    contrasena: str | None = Field(default=None, min_length=6)
    estado: str | None = None


class UsuarioResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    nombre: str
    correo: EmailStr
    es_administrador: bool
    estado: str
