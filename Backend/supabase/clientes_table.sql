-- Script para crear la tabla de clientes en Supabase

CREATE TABLE IF NOT EXISTS clientes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre TEXT NOT NULL,
    correo TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    estado TEXT NOT NULL DEFAULT 'Activo',
    fecha_registro TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Habilitar RLS (opcional, pero recomendado)
ALTER TABLE clientes ENABLE ROW LEVEL SECURITY;

-- Política para que los administradores puedan hacer todo
CREATE POLICY "Admin full access" ON clientes
    FOR ALL
    USING (true)
    WITH CHECK (true);
