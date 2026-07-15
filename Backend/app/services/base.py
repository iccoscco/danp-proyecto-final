from typing import Any, Generic, TypeVar

from app.database.base import Base
from app.repositories.base import BaseRepository

ModelType = TypeVar("ModelType", bound=Base)


class BaseService(Generic[ModelType]):
    def __init__(self, repository: BaseRepository[ModelType]) -> None:
        self.repository = repository

    def get_by_id(self, entity_id: int) -> ModelType | None:
        return self.repository.get_by_id(entity_id)

    def get_all(self) -> list[ModelType]:
        return self.repository.get_all()

    def create(self, data: dict[str, Any]) -> ModelType:
        return self.repository.create(data)

    def update(self, entity: ModelType, data: dict[str, Any]) -> ModelType:
        return self.repository.update(entity, data)

    def delete(self, entity: ModelType) -> None:
        self.repository.delete(entity)
