TRUNCATE TABLE rol RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;

INSERT INTO rol(tipo_rol,id, nombre)
VALUES ('administrador',1,'administrador');
	
INSERT INTO usuario(id,apellido,dni,email,nombre,password,pathimagen,rol_id)
VALUES (1,'Levis', 41106252, 'marcos@gmail.com','Marcos','password','/img',1);

INSERT INTO usuario(id,apellido,dni,email,nombre,password,pathimagen,rol_id)
VALUES (2,'Gomez', 41109313, 'rafael@gmail.com','rafael','password','/img',1);



