-- Script para insertar las categorías oficiales

INSERT INTO categorias (nombre, descripcion)
VALUES
    ('Panadería', 'Panes, pasteles y productos de masa'),
    ('Bebidas', 'Gaseosas, jugos, aguas y bebidas calientes'),
    ('Frutas', 'Frutas frescas de temporada'),
    ('Comidas', 'Platos preparados y snacks sustanciosos'),
    ('Lácteos', 'Leche, quesos, yogures y derivados'),
    ('Postres', 'Dulces, tortas y helados'),
    ('Snacks', 'Piqueos y aperitivos ligeros')
ON CONFLICT (nombre) DO NOTHING;
