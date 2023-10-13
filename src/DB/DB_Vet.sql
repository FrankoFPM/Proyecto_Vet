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
    color varchar(50) not null,
    id_cliente varchar(15) not null,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);
create table cita(
	id_cita varchar(8) primary key not null,
    id_cliente varchar(8) not null,
    cliente varchar(15) not null,
    paciente varchar(15) not null,
    veterinario varchar(15) not null,
    fecha date not null,
    hora time not null,
    estado varchar(20) not null
);

CREATE TABLE ReporteClinico (
    id_rpclinico VARCHAR(8) PRIMARY KEY NOT NULL,
    codigo_entidad VARCHAR(9),
    entidad VARCHAR(50),
    causa VARCHAR(150),
    pronostico VARCHAR(40),
    sintomas TEXT,
    tratamiento TEXT,
    fecha DATE,
    hora TIME
);

CREATE TABLE Productos(
    id_producto VARCHAR(8) NOT NULL PRIMARY KEY,
    nombre VARCHAR(50),
    marca VARCHAR(50),
    precio DECIMAL(5, 2),
    cantidad INT,
    categoria VARCHAR(50),
    fecha DATE
);


-- Vistas

-- Vista personal
CREATE VIEW v_personal AS
SELECT id_personal, nombre, apellido, correo, dni, cargo, nick
FROM personal;


-- Fin Vistas

DELIMITER //
CREATE PROCEDURE sp_actualizar_producto(IN p_id_producto VARCHAR(8), IN p_nombre VARCHAR(50), IN p_marca VARCHAR(50), IN p_precio DECIMAL(5, 2), IN p_cantidad INT, IN p_categoria VARCHAR(50), IN p_fecha DATE)
BEGIN
    UPDATE Productos 
    SET nombre = p_nombre,
        marca = p_marca,
        precio = p_precio,
        cantidad = p_cantidad,
        categoria = p_categoria,
        fecha = p_fecha
    WHERE id_producto = p_id_producto;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_insertar_producto(IN p_id_producto VARCHAR(8), IN p_nombre VARCHAR(50), IN p_marca VARCHAR(50), IN p_precio DECIMAL(5, 2), IN p_cantidad INT, IN p_categoria VARCHAR(50), IN p_fecha DATE)
BEGIN
    INSERT INTO Productos(id_producto, nombre, marca, precio, cantidad, categoria, fecha) 
    VALUES (p_id_producto, p_nombre, p_marca, p_precio, p_cantidad, p_categoria, p_fecha);
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE sp_insertar_reporteclinico(IN p_id_rpclinico VARCHAR(8), IN p_codigo_entidad VARCHAR(9), IN p_entidad VARCHAR(50), IN p_causa VARCHAR(150), IN p_pronostico VARCHAR(40), IN p_sintomas TEXT, IN p_tratamiento TEXT, IN p_fecha DATE, IN p_hora TIME)
BEGIN
    INSERT INTO ReporteClinico (id_rpclinico, codigo_entidad, entidad, causa, pronostico, sintomas, tratamiento, fecha, hora)
    VALUES (p_id_rpclinico, p_codigo_entidad, p_entidad, p_causa, p_pronostico, p_sintomas, p_tratamiento, p_fecha, p_hora);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_actualizar_reporteclinico(IN p_id_rpclinico VARCHAR(8), IN p_codigo_entidad VARCHAR(9), IN p_entidad VARCHAR(50), IN p_causa VARCHAR(150), IN p_pronostico VARCHAR(40), IN p_sintomas TEXT, IN p_tratamiento TEXT, IN p_fecha DATE, IN p_hora TIME)
BEGIN
    UPDATE ReporteClinico 
    SET codigo_entidad = p_codigo_entidad,
        entidad = p_entidad,
        causa = p_causa,
        pronostico = p_pronostico,
        sintomas = p_sintomas,
        tratamiento = p_tratamiento,
        fecha = p_fecha,
        hora = p_hora
    WHERE id_rpclinico = p_id_rpclinico;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_seleccionar_citas_programadas()
BEGIN
    SELECT * FROM cita WHERE estado = 'Programada';
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE buscar_citas(IN dni varchar(11))
BEGIN
    SELECT cita.* 
    FROM cliente 
    JOIN cita ON cliente.id_cliente = cita.id_cliente 
    WHERE cliente.dni = dni;
END //
DELIMITER ;

call buscar_citas('12345678');


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
    IN p_color VARCHAR(50), 
    IN p_id_cliente VARCHAR(8))
BEGIN
    INSERT INTO paciente(id_paciente, nombre, especie, raza, sexo, color, id_cliente) 
    VALUES (p_id_paciente, p_nombre, p_especie, p_raza, p_sexo, p_color, p_id_cliente);
END //
DELIMITER ;

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
CREATE PROCEDURE sp_contar_citas_programadas()
BEGIN
select count(*) from cita where  estado = 'Programada';
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_listar_clientes()
BEGIN
select id_cliente, concat(nombre," ", apellido, " - ",dni) as "info" from cliente;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_listar_pacientes(in p_cliente varchar(15))
BEGIN
select id_paciente, concat(nombre, " - ",especie) as "info" from paciente where id_cliente = p_cliente;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_listar_todo_pacientes()
BEGIN
    SELECT p.id_paciente, CONCAT(p.nombre, " - ", p.especie, " - DNI Cliente: ", c.dni) AS "info"
    FROM paciente p
    INNER JOIN cliente c ON p.id_cliente = c.id_cliente;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_listar_veterinarios()
BEGIN
select id_personal, concat(nombre, " ",apellido) as "info" from personal where cargo = "Veterinario";
END //
DELIMITER ;
CALL sp_listar_pacientes('CLI-0001');
-- select * from paciente;
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

DELIMITER //
CREATE PROCEDURE sp_insertar_cita(
	IN p_id_cita VARCHAR(8), 
    IN p_id_cliente VARCHAR(8), 
    IN p_cliente VARCHAR(100), 
    IN p_paciente VARCHAR(15), 
    IN p_veterinario VARCHAR(15), 
    IN p_fecha DATE, IN p_hora TIME,
    IN p_estado varchar(20))
BEGIN
    INSERT INTO cita(id_cita,id_cliente, cliente, paciente, veterinario, fecha, hora, estado) 
    VALUES (p_id_cita,p_id_cliente , p_cliente, p_paciente, p_veterinario, p_fecha, p_hora, p_estado);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_actualizar_cita(
	IN p_id_cita VARCHAR(8),
    IN p_id_cliente VARCHAR(12), 
    IN p_cliente VARCHAR(50), 
    IN p_paciente VARCHAR(50), 
    IN p_veterinario VARCHAR(50), 
    IN p_fecha DATE, IN p_hora TIME,
    IN p_estado varchar(20))
BEGIN
    UPDATE cita 
    SET cliente = p_cliente,
		id_cliente = p_id_cliente,
        paciente = p_paciente,
        veterinario = p_veterinario,
        fecha = p_fecha,
        hora = p_hora,
        estado = p_estado
    WHERE id_cita = p_id_cita;
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
('EMP-0001', 'Juan', 'Perez', '123', 'juan.perez@email.com', 12345678, 'Veterinario','juan'),
('EMP-0002', 'Maria', 'Gomez', '123', 'maria.gomez@email.com', 23456789, 'Recep','mari'),
('EMP-0003', 'Carlos', 'Rodriguez', '123', 'carlos.rodriguez@email.com', 34567890, 'venta','carlos');
 -- Insertar clientes
INSERT INTO cliente(id_cliente, nombre, apellido, telefono, correo, dni, direccion)
VALUES 
('CLI-0001', 'Juan', 'Pérez', '123-456-7890', 'juan@example.com', 123456789, '123 Calle Principal'),
('CLI-0002', 'Maria', 'Gonzalez', '098-765-4321', 'maria@example.com', 987654321, '456 Calle Secundaria'),
('CLI-0003', 'Carlos', 'Rodriguez','111-222-3333','carlos@example.com','112233445','789 Calle Tercera');

-- Insertar pacientes
CALL sp_insertar_paciente('PAC-0001', 'Fido', 'perro', 'chiwan', 'macho', 'negro', 'CLI-0001');
CALL sp_insertar_paciente('PAC-0002', 'Rex', 'perro', 'labrador', 'macho', 'marrón', 'CLI-0001');
CALL sp_insertar_paciente('PAC-0003', 'Luna', 'gato', 'siamese', 'hembra', 'blanco y negro', 'CLI-0001');
CALL sp_insertar_paciente('PAC-0004', 'Bella', 'gato', 'persa', 'hembra', 'gris', 'CLI-0002');
CALL sp_insertar_paciente('PAC-0005', 'Max', 'perro', 'bulldog francés', 'macho', 'blanco y negro', 'CLI-0002');
CALL sp_insertar_paciente('PAC-0006', 'Simba', 'gato', 'bengalí', 'macho', 'naranja y negro', 'CLI-0002');
CALL sp_insertar_paciente('PAC-0007','Coco','loro','guacamayo azul','macho','azul y amarillo','CLI-0003');
CALL sp_insertar_paciente('PAC-0008','Nemo','pez','payaso','macho','naranja y blanco','CLI-0003');
CALL sp_insertar_paciente('PAC-0009','Daisy','conejo','angora','hembra','blanco','CLI-0003');

select * from paciente;