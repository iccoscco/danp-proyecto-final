from typing import Any, Generic, TypeVar

from sqlalchemy import select
from sqlalchemy.orm import Session

from app.database.base import Base

ModelType = TypeVar("ModelType", bound=Base)


class BaseRepository(Generic[ModelType]):
    model: type[ModelType]

    def __init__(self, db: Session) -> None:
        self.db = db

    def get_by_id(self, entity_id: int) -> ModelType | None:
        return self.db.get(self.model, entity_id)

    def get_all(self) -> list[ModelType]:
        return list(self.db.scalars(select(self.model)).all())

    def create(self, data: dict[str, Any]) -> ModelType:
        entity = self.model(**data)
        self.db.add(entity)
        self.db.commit()
        self.db.refresh(entity)
        return entity

    def update(self, entity: ModelType, data: dict[str, Any]) -> ModelType:
        for field, value in data.items():
            setattr(entity, field, value)
        self.db.commit()
        self.db.refresh(entity)
        return entity

    def delete(self, entity: ModelType) -> None:
        self.db.delete(entity)
        self.db.commit()
