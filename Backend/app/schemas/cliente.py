from pydantic import BaseModel, ConfigDict, EmailStr, Field, field_validator
from datetime import datetime
import re

class ClienteBase(BaseModel):
    nombre: str
    correo: EmailStr

class ClienteCreate(ClienteBase):
    contrasena: str = Field(min_length=8)

    @field_validator("contrasena")
    @classmethod
    def validar_contrasena(cls, v: str) -> str:
        if not any(c.isalpha() for c in v):
            raise ValueError("La contraseña debe incluir al menos una letra")
        if not any(c.isdigit() for c in v):
            raise ValueError("La contraseña debe incluir al menos un número")
        return v

class ClienteUpdate(BaseModel):
    nombre: str | None = None
    correo: EmailStr | None = None
    estado: str | None = None
    contrasena: str | None = Field(default=None, min_length=8)

    @field_validator("contrasena")
    @classmethod
    def validar_contrasena(cls, v: str | None) -> str | None:
        if v is None:
            return v
        if not any(c.isalpha() for c in v):
            raise ValueError("La contraseña debe incluir al menos una letra")
        if not any(c.isdigit() for c in v):
            raise ValueError("La contraseña debe incluir al menos un número")
        return v

class ClienteResponse(ClienteBase):
    model_config = ConfigDict(from_attributes=True)
    id: str
    estado: str
    fecha_registro: datetime | None = None

class ClienteLoginRequest(BaseModel):
    correo: EmailStr
    contrasena: str
