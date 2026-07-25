import os
from dataclasses import dataclass
from pathlib import Path

from dotenv import load_dotenv


load_dotenv(Path(__file__).resolve().parents[2] / ".env")


@dataclass(frozen=True)
class Settings:
    app_name: str = os.getenv("APP_NAME", "DANP API")
    app_version: str = os.getenv("APP_VERSION", "0.1.0")
    database_url: str = os.getenv(
        "DATABASE_URL",
        "postgresql+psycopg://postgres:postgres@localhost:5432/danp",
    )
    jwt_secret_key: str = os.getenv("JWT_SECRET_KEY", "change-this-secret-key")
    jwt_algorithm: str = os.getenv("JWT_ALGORITHM", "HS256")
    access_token_expire_minutes: int = int(os.getenv("ACCESS_TOKEN_EXPIRE_MINUTES", "60"))
    
    supabase_url: str | None = os.getenv("SUPABASE_URL")
    supabase_key: str | None = os.getenv("SUPABASE_KEY")
    supabase_products_bucket: str = os.getenv("SUPABASE_PRODUCTS_BUCKET", "productos")
    supabase_avatars_bucket: str = os.getenv("SUPABASE_AVATARS_BUCKET", "avatars")
    master_admin_email: str = os.getenv("MASTER_ADMIN_EMAIL", "mika@savebite.com")
    master_admin_password: str = os.getenv("MASTER_ADMIN_PASSWORD", "123456")
    frontend_origins: list[str] | None = None

    def __post_init__(self) -> None:
        origins = os.getenv("FRONTEND_ORIGINS", "http://localhost:3000")
        object.__setattr__(
            self,
            "frontend_origins",
            [origin.strip() for origin in origins.split(",") if origin.strip()],
        )


settings = Settings()
