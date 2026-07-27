from pydantic import BaseModel
from typing import List, Dict

class VentasDia(BaseModel):
    fecha: str
    total: float

class DashboardStats(BaseModel):
    ventas_semana: List[VentasDia]
    pedidos_por_estado: Dict[str, int]
    total_productos: int
    total_usuarios: int
    total_ofertas: int
    ultimos_pedidos: List[dict]
