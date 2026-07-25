from typing import Literal

from pydantic import BaseModel, ConfigDict, EmailStr, Field

RolUsuario = Literal["Administrador", "Operador"]
GeneroUsuario = Literal["Masculino", "Femenino"]
EstadoUsuario = Literal["Activo", "Inactivo"]


class UsuarioCreate(BaseModel):
    nombre: str
    correo: EmailStr
    genero: GeneroUsuario
    rol: RolUsuario
    estado: EstadoUsuario = "Activo"
    contrasena: str = Field(min_length=6)


class UsuarioUpdate(BaseModel):
    nombre: str | None = None
    correo: EmailStr | None = None
    genero: GeneroUsuario | None = None
    rol: RolUsuario | None = None
    estado: EstadoUsuario | None = None
    contrasena: str | None = Field(default=None, min_length=6)


class UsuarioResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: str
    nombre: str
    correo: EmailStr
    rol: str
    genero: str | None = None
    estado: str
    es_administrador: bool
