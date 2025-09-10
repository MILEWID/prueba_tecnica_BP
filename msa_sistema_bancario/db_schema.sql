-- Tabla Persona
CREATE TABLE persona (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100),
    genero VARCHAR(10),
    edad INT,
    identificacion VARCHAR(20) UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

-- Tabla Cliente (hereda de Persona)
CREATE TABLE cliente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    persona_id UUID REFERENCES persona(id),
    contrasena VARCHAR(100),
    estado BOOLEAN
);

-- Tabla Cuenta
CREATE TABLE cuenta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID REFERENCES cliente(id),
    numero_cuenta VARCHAR(20) UNIQUE,
    tipo_cuenta VARCHAR(20),
    saldo_inicial NUMERIC(15,2),
    estado BOOLEAN,
    cliente_nombre VARCHAR(100), -- Para compatibilidad con DTO
    cliente_identificacion VARCHAR(20) -- Para compatibilidad con DTO
);

-- Tabla Movimiento
CREATE TABLE movimiento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cuenta_id UUID REFERENCES cuenta(id),
    fecha TIMESTAMP,
    tipo_movimiento VARCHAR(20), -- Debito/Credito
    valor NUMERIC(15,2),
    saldo NUMERIC(15,2),
    saldo_inicial NUMERIC(15,2)
);
