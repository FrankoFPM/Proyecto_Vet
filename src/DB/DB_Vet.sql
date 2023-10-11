drop database if exists vet;
create database vet;
use vet;

create table cargo(
	id_cargo int primary key auto_increment not null,
    descripcion varchar(15)
);

create table personal(
	id_registro int primary key auto_increment not null,
    id_personal varchar(8) not null,
    nombre varchar(15) not null,
    apellido varchar(15) not null,
	pass varchar(10) not null,
    correo varchar(50) not null,
    dni int not null,
    cargo varchar(15) not null,
    nick varchar(15) not null
);

create table cliente(
	id_cliente varchar(8) primary key not null,
    nombre varchar(15) not null,
    apellido varchar(15) not null,
    telefono varchar(12) not null,
    correo varchar(50) not null,
    dni int not null,
    direccion varchar(100) not null
);
create table paciente(
	id_paciente varchar(8) primary key not null,
    nombre varchar(15) not null,
    especie varchar(15) not null,
    raza varchar(15) not null,
    sexo varchar(15) not null,
    color varchar(15) not null,
    id_cliente varchar(15) not null,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);
-- Vistas

-- Vista personal
CREATE VIEW v_personal AS
SELECT id_personal, nombre, apellido, correo, dni, cargo, nick
FROM personal;


-- Fin Vistas


insert into cliente(id_cliente, nombre, apellido, telefono, correo, dni, direccion)
VALUES ('CLI-0001', 'Juan', 'Pérez', '123-456-7890', 'juan@example.com', 123456789, '123 Calle Principal');

DELIMITER //
CREATE PROCEDURE ValidarCredenciales(
    IN p_nombre_usuario VARCHAR(15),
    IN p_contraseña VARCHAR(15),
    OUT valido BOOLEAN
)
BEGIN
    DECLARE v_cont INT;
    SELECT COUNT(*) INTO v_cont
    FROM personal
    WHERE nick = p_nombre_usuario AND pass = p_contraseña;
    
    IF v_cont = 1 THEN
        SET valido = TRUE;
    ELSE
        SET valido = FALSE;
    END IF;
END;
//
DELIMITER ;
/*
DELIMITER //
CREATE PROCEDURE sp_codigoCliente(OUT codigo_generado CHAR(8))
BEGIN
    DECLARE serie CHAR(4);
    DECLARE max_codigo_existente INT;

    SELECT MAX(CAST(SUBSTRING(id_cliente, 5) AS SIGNED)) INTO max_codigo_existente FROM cliente;

    IF max_codigo_existente IS NULL THEN
        SET codigo_generado = 'CLI-0001';
    ELSE
        SET serie = 'CLI-';
        SET codigo_generado = CONCAT(serie, LPAD(max_codigo_existente + 1, 4, '0'));
    END IF;
END //
DELIMITER ;

CALL sp_codigoCliente(@xcodigoCliente);
SELECT @xcodigoCliente;
*/

DELIMITER //

CREATE PROCEDURE sp_listarDatos(IN tabla VARCHAR(20))
BEGIN
    SET @sql = CONCAT('SELECT * FROM ', tabla);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;
call sp_listarDatos("cliente");
DELIMITER //
CREATE PROCEDURE sp_codigo_autogenerado(
    IN tabla CHAR(64),
    IN columna CHAR(64),
    IN prefijo CHAR(4),
    IN longitud_codigo INT,
    OUT codigo_generado CHAR(64)
)
BEGIN
    DECLARE max_codigo_existente INT;
    DECLARE consulta VARCHAR(1000);

    SET @consulta = CONCAT('SELECT MAX(CAST(SUBSTRING(', columna, ', ', LENGTH(prefijo) + 1, ') AS SIGNED)) INTO @max_codigo_existente FROM ', tabla);
    PREPARE stmt FROM @consulta;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    IF @max_codigo_existente IS NULL THEN
        SET codigo_generado = CONCAT(prefijo, LPAD('1', longitud_codigo, '0'));
    ELSE
        SET codigo_generado = CONCAT(prefijo, LPAD(@max_codigo_existente + 1, longitud_codigo, '0'));
    END IF;
END //
DELIMITER ;
-- Codigo autogenerado modificado
DELIMITER //
CREATE PROCEDURE sp_codigo_autogenerado_modificado(
    IN tabla CHAR(64),
    IN columna CHAR(64),
    IN prefijo CHAR(4),
    IN longitud_codigo INT,
    OUT codigo_generado CHAR(64)
)
BEGIN
    DECLARE max_codigo_existente INT;
    DECLARE consulta VARCHAR(1000);

    SET @consulta = CONCAT('SELECT MAX(', columna, ') INTO @max_codigo_existente FROM ', tabla);
    PREPARE stmt FROM @consulta;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    IF @max_codigo_existente IS NULL THEN
        SET codigo_generado = CONCAT(prefijo, LPAD('1', longitud_codigo, '0'));
    ELSE
        SET codigo_generado = CONCAT(prefijo, LPAD(@max_codigo_existente + 1, longitud_codigo, '0'));
    END IF;
END //
DELIMITER ;


-- fin del mod


DELIMITER //
CREATE PROCEDURE sp_insertar_cliente(
    IN id_cliente VARCHAR(8),
    IN nombre VARCHAR(15),
    IN apellido VARCHAR(15),
    IN telefono VARCHAR(12),
    IN correo VARCHAR(50),
    IN dni INT,
    IN direccion VARCHAR(100)
)
BEGIN
    INSERT INTO cliente (id_cliente, nombre, apellido, telefono, correo, dni, direccion)
    VALUES (id_cliente, nombre, apellido, telefono, correo, dni, direccion);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_insertar_paciente(
	IN p_id_paciente VARCHAR(8), 
    IN p_nombre VARCHAR(15), 
    IN p_especie VARCHAR(15), 
    IN p_raza VARCHAR(15), 
    IN p_sexo VARCHAR(15), 
    IN p_color VARCHAR(15), 
    IN p_id_cliente VARCHAR(8))
BEGIN
    INSERT INTO paciente(id_paciente, nombre, especie, raza, sexo, color, id_cliente) 
    VALUES (p_id_paciente, p_nombre, p_especie, p_raza, p_sexo, p_color, p_id_cliente);
END //
DELIMITER ;
CALL sp_insertar_paciente('PAC-0001', 'Fido', 'perro', 'chiwan', 'macho', 'negro', 'CLI-0001');
select * from paciente;

-- delete from cliente where id_cliente = "CLI-0001";

DELIMITER //
 CREATE PROCEDURE sp_eliminar(
	IN tabla varchar(15),
    IN columna varchar(15),
    IN id_dato varchar(15)
) 
BEGIN 
SET @sql = CONCAT('DELETE FROM ', tabla, ' WHERE ', columna, ' = "',id_dato,'"');
	PREPARE stmt FROM @sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

DELIMITER //
 CREATE PROCEDURE sp_buscar(
	IN tabla varchar(15),
    IN columna varchar(15),
    IN id_dato varchar(15)
) 
BEGIN 
SET @sql = CONCAT('SELECT * FROM ', tabla, ' WHERE ', columna, ' = "',id_dato,'"');
	PREPARE stmt FROM @sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_actualizar_cliente(
    IN p_id_cliente VARCHAR(8),
    IN p_nombre VARCHAR(15),
    IN p_apellido VARCHAR(15),
    IN p_telefono VARCHAR(12),
    IN p_correo VARCHAR(50),
    IN p_dni INT,
    IN p_direccion VARCHAR(100)
)
BEGIN
    UPDATE cliente
    SET
        nombre = p_nombre,
        apellido = p_apellido,
        telefono = p_telefono,
        correo = p_correo,
        dni = p_dni,
        direccion = p_direccion
    WHERE
        id_cliente = p_id_cliente;
END//

DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_actualizar_paciente (
    IN p_id_paciente VARCHAR(8),
    IN p_nombre VARCHAR(15),
    IN p_especie VARCHAR(15),
    IN p_raza VARCHAR(15),
    IN p_sexo VARCHAR(15),
    IN p_color VARCHAR(15),
    IN p_id_cliente VARCHAR(15)
)
BEGIN
    UPDATE paciente
    SET nombre = p_nombre,
        especie = p_especie,
        raza = p_raza,
        sexo = p_sexo,
        color = p_color,
        id_cliente = p_id_cliente
    WHERE id_paciente = p_id_paciente;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_contar_clientes()
BEGIN
select count(*) from cliente;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_listar_clientes()
BEGIN
select id_cliente, concat(nombre," ", apellido, " - ",dni) as "info" from cliente;
END //
DELIMITER ;

CALL sp_listar_clientes();
-- CALL sp_contar_clientes();

DELIMITER //
CREATE PROCEDURE sp_insertar_personal(
    IN p_id_personal VARCHAR(8),
    IN p_nombre VARCHAR(15),
    IN p_apellido VARCHAR(15),
    IN p_pass VARCHAR(10),
    IN p_correo VARCHAR(50),
    IN p_dni INT,
    IN p_cargo VARCHAR(15),
    IN p_nick VARCHAR(15)
)
BEGIN
    INSERT INTO personal (id_personal, nombre, apellido, pass, correo, dni, cargo, nick)
    VALUES (p_id_personal, p_nombre, p_apellido, p_pass, p_correo, p_dni, p_cargo, p_nick);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_actualizar_personal(
    IN p_id_personal VARCHAR(8),
    IN p_nombre VARCHAR(15),
    IN p_apellido VARCHAR(15),
    IN p_pass VARCHAR(10),
    IN p_correo VARCHAR(50),
    IN p_dni INT,
    IN p_cargo VARCHAR(15),
    IN p_nick VARCHAR(15)
)
BEGIN
    UPDATE personal 
    SET nombre = p_nombre,
        apellido = p_apellido,
        pass = p_pass,
        correo = p_correo,
        dni = p_dni,
        cargo = p_cargo,
        nick = p_nick
    WHERE id_personal = p_id_personal;
END //
DELIMITER ;



CALL sp_codigo_autogenerado_modificado("personal","id_registro","EMP-",4,@xcodigoCliente);
SELECT @xcodigoCliente;
/*
SELECT * FROM personal WHERE nombre = '<username>' AND pass = '' OR '1'='1';
SELECT * FROM cliente;

CALL ValidarCredenciales('Juan', "123", @resultado);
SELECT @resultado;

-- call sp_eliminar("cliente","id_cliente","CLI-0001");
call sp_buscar("cliente","dni","123456789");

*/
-- Insertar datos en la tabla "cargo"
INSERT INTO cargo (descripcion) VALUES
    ('Gerente'),
    ('Supervisor'),
    ('Asistente'),
    ('Técnico');

-- Insertar datos en la tabla "personal"
select * from personal;
INSERT INTO personal (id_personal, nombre, apellido, pass, correo, dni, cargo, nick)
VALUES 
('EMP-0001', 'Juan', 'Perez', '123', 'juan.perez@email.com', 12345678, 'Vet','juan'),
('EMP-0002', 'Maria', 'Gomez', '123', 'maria.gomez@email.com', 23456789, 'Recep','mari'),
('EMP-0003', 'Carlos', 'Rodriguez', '123', 'carlos.rodriguez@email.com', 34567890, 'venta','carlos');
 