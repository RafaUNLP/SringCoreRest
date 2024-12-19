/*TRUNCATE TABLE rol RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;
TRUNCATE TABLE dia RESTART IDENTITY CASCADE;

INSERT INTO rol(tipo_rol,id, nombre)
VALUES ('administrador',1,'administrador');

INSERT INTO rol(tipo_rol,id, nombre)
VALUES ('cliente',2,'cliente');


	
INSERT INTO usuario(id,apellido,dni,email,nombre,password,pathimagen,rol_id)
VALUES (1,'Levis', 41106252, 'marcos@gmail.com','Marcos','password','/img',1);

INSERT INTO usuario(id,apellido,dni,email,nombre,password,pathimagen,rol_id)
VALUES (2,'Gomez', 41109313, 'rafael@gmail.com','rafael','password','/img',1);

INSERT INTO usuario(id,apellido,dni,email,nombre,password,pathimagen,rol_id)
VALUES (3,'Gutierrez', 41107713, 'diego@gmail.com','Diego','password','/img',2);


INSERT INTO dia(id,enumdia)
VALUES (1,'LUNES');

INSERT INTO dia(id,enumdia)
VALUES (2,'MARTES');

INSERT INTO dia(id,enumdia)
VALUES (3,'MIERCOLES');

INSERT INTO dia(id,enumdia)
VALUES (4,'JUEVES');

INSERT INTO dia(id,enumdia)
VALUES (5,'VIERNES');
*/


