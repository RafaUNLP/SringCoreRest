INSERT INTO roles (id, nombre) VALUES (1, 'ADMIN'), (2, 'USER');
INSERT INTO usuarios (id, nombre, apellido, dni, email, password, rol_id)
VALUES 
(1, 'Administrador', 'Admin', '12345678', 'admin@example.com', 'admin123', 1),
(2, 'Usuario', 'Normal', '87654321', 'user@example.com', 'user123', 2);