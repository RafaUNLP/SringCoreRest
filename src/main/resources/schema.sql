CREATE TABLE roles (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Crear tabla de usuarios
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol_id BIGINT,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);