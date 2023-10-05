drop database if exists vet;
create database vet;
use vet;

create table cargo(
	id_cargo int primary key auto_increment not null,
    descripcion varchar(15)
);

create table personal(
	id_personal int primary key auto_increment not null,
    nombre varchar(15) not null,
    -- apellido
    -- dni
    pass varchar(10) not null,
    cargo int,
    FOREIGN KEY (cargo) REFERENCES cargo(id_cargo)
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
    WHERE nombre = p_nombre_usuario AND pass = p_contraseña;
    
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

CALL sp_codigo_autogenerado("cliente","id_cliente","CLI-",4,@xcodigoCliente);
SELECT @xcodigoCliente;

SELECT * FROM personal WHERE nombre = '<username>' AND pass = '' OR '1'='1';


CALL ValidarCredenciales('Juan', "123", @resultado);
SELECT @resultado;


-- Insertar datos en la tabla "cargo"
INSERT INTO cargo (descripcion) VALUES
    ('Gerente'),
    ('Supervisor'),
    ('Asistente'),
    ('Técnico');

-- Insertar datos en la tabla "personal"
INSERT INTO personal (nombre, pass, cargo) VALUES
    ('Juan', '123', 1),
    ('Maria', '123', 2),
    ('Carlos', '123', 3),
    ('Luis', '123', 4);  