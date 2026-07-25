from io import BytesIO
from uuid import uuid4

from fastapi import HTTPException, UploadFile, status
from PIL import Image, UnidentifiedImageError
from supabase import Client

from app.core.config import settings

# Avatares: pequeños y cuadrados. 400px es suficiente para un círculo.
MAX_AVATAR_SIZE = 400
MAX_UPLOAD_BYTES = 10 * 1024 * 1024


class UsuarioImageService:
    def __init__(self, client: Client) -> None:
        self.client = client
        self.bucket = settings.supabase_avatars_bucket

    async def upload(self, image_file: UploadFile) -> tuple[str, str]:
        content = await image_file.read()
        optimized = self._optimize(content)
        path = f"avatars/{uuid4().hex}.jpg"

        self._ensure_bucket()
        self.client.storage.from_(self.bucket).upload(
            path,
            optimized,
            file_options={"content-type": "image/jpeg", "upsert": "false"},
        )
        return path, self.client.storage.from_(self.bucket).get_public_url(path)

    def delete(self, path: str | None) -> None:
        if path:
            self.client.storage.from_(self.bucket).remove([path])

    def _ensure_bucket(self) -> None:
        try:
            self.client.storage.get_bucket(self.bucket)
        except Exception:
            self.client.storage.create_bucket(self.bucket, options={"public": True})

    @staticmethod
    def _optimize(content: bytes) -> bytes:
        if not content or len(content) > MAX_UPLOAD_BYTES:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="La imagen debe pesar como máximo 10 MB",
            )

        try:
            with Image.open(BytesIO(content)) as image:
                image = image.convert("RGB")
                image.thumbnail((MAX_AVATAR_SIZE, MAX_AVATAR_SIZE))
                output = BytesIO()
                image.save(output, format="JPEG", quality=85, optimize=True)
                return output.getvalue()
        except UnidentifiedImageError as exc:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="El archivo debe ser una imagen válida",
            ) from exc
