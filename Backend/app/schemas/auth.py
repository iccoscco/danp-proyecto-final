from pydantic import BaseModel, EmailStr, Field


class LoginRequest(BaseModel):
    correo: EmailStr
    contrasena: str = Field(min_length=6)


class TokenResponse(BaseModel):
    access_token: str
    token_type: str = "bearer"


class UsuarioActualResponse(BaseModel):
    id: str
    nombre: str
    correo: EmailStr
    rol: str
    genero: str | None = None
    estado: str
    es_administrador: bool
    foto_url: str | None = None


class PerfilUpdate(BaseModel):
    """Edición del propio perfil: solo nombre, correo y contraseña.
    Rol y género no se modifican aquí."""
    nombre: str | None = None
    correo: EmailStr | None = None
    contrasena: str | None = Field(default=None, min_length=6)
