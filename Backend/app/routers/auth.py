from typing import Annotated

from fastapi import APIRouter, Depends, File, HTTPException, UploadFile, status
from app.api.dependencies import get_current_user
from app.core.supabase import get_supabase_client
from app.repositories.supabase_usuario import SupabaseUsuarioRepository
from app.schemas.auth import LoginRequest, PerfilUpdate, TokenResponse, UsuarioActualResponse
from app.services.auth import AuthService
from app.services.supabase_usuario import SupabaseUsuarioService
from app.services.usuario_image import UsuarioImageService

router = APIRouter(prefix="/auth", tags=["autenticación"])


def get_service() -> AuthService:
    return AuthService(SupabaseUsuarioRepository(get_supabase_client()))


def get_usuario_service() -> SupabaseUsuarioService:
    return SupabaseUsuarioService(SupabaseUsuarioRepository(get_supabase_client()))


Service = Annotated[AuthService, Depends(get_service)]
UsuarioServiceDep = Annotated[SupabaseUsuarioService, Depends(get_usuario_service)]
CurrentUser = Annotated[dict[str, object], Depends(get_current_user)]


@router.post("/login", response_model=TokenResponse)
def login(data: LoginRequest, service: Service) -> TokenResponse:
    usuario = service.authenticate(data.correo, data.contrasena)
    if usuario is None:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Credenciales inválidas",
        )
    return TokenResponse(access_token=service.create_token(usuario))


@router.get("/me", response_model=UsuarioActualResponse)
def me(current_user: CurrentUser) -> UsuarioActualResponse:
    return UsuarioActualResponse(
        id=str(current_user["id"]),
        nombre=str(current_user["nombre"]),
        correo=str(current_user["correo"]),
        rol=str(current_user.get("rol") or ("Administrador" if current_user.get("es_administrador") else "Operador")),
        genero=current_user.get("genero"),
        estado=str(current_user.get("estado") or "Activo"),
        es_administrador=bool(current_user.get("es_administrador") or current_user.get("rol") == "Administrador"),
        foto_url=current_user.get("foto_url"),
    )


@router.put("/perfil", response_model=UsuarioActualResponse)
def actualizar_perfil(
    data: PerfilUpdate,
    current_user: CurrentUser,
    usuario_service: UsuarioServiceDep,
) -> UsuarioActualResponse:
    """Actualiza solo nombre, correo y/o contraseña del usuario autenticado.
    Rol, género y estado no se modifican."""
    actualizaciones = data.model_dump(exclude_unset=True)
    if not actualizaciones:
        return me(current_user)

    usuario_service.update(str(current_user["id"]), actualizaciones)
    actualizado = usuario_service.get_by_id(str(current_user["id"]))
    if actualizado is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    return me(actualizado)


@router.post("/foto", response_model=UsuarioActualResponse)
async def subir_foto(
    current_user: CurrentUser,
    usuario_service: UsuarioServiceDep,
    foto: UploadFile = File(...),
) -> UsuarioActualResponse:
    """Sube una foto de perfil optimizada (máx 400px, JPEG) al bucket de avatares.
    Borra la foto anterior si existía y actualiza foto_url/foto_path del usuario."""
    client = get_supabase_client()
    image_service = UsuarioImageService(client)
    usuario_id = str(current_user["id"])

    # Subir la nueva imagen.
    path, public_url = await image_service.upload(foto)

    # Borrar la foto anterior si la tenía.
    foto_path_anterior = current_user.get("foto_path")
    if foto_path_anterior:
        image_service.delete(str(foto_path_anterior))

    # Actualizar la base de datos.
    usuario_service.update(usuario_id, {"foto_url": public_url, "foto_path": path})

    actualizado = usuario_service.get_by_id(usuario_id)
    if actualizado is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Usuario no encontrado")
    return me(actualizado)

