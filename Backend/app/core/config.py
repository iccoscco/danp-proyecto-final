import os
from dataclasses import dataclass


@dataclass(frozen=True)
class Settings:
    app_name: str = os.getenv("APP_NAME", "DANP API")
    app_version: str = os.getenv("APP_VERSION", "0.1.0")
    frontend_origins: list[str] | None = None

    def __post_init__(self) -> None:
        origins = os.getenv("FRONTEND_ORIGINS", "http://localhost:3000")
        object.__setattr__(
            self,
            "frontend_origins",
            [origin.strip() for origin in origins.split(",") if origin.strip()],
        )


settings = Settings()
