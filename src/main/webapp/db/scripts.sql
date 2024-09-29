INSERT INTO productos(id_producto, nombre, descripcion, valor, imagen) VALUES (1, 'POLO JS', 'POLO PARA DESARROLLADORES DE JS', 15.0, 'img/js.jpg');

INSERT INTO productos(id_producto, nombre, descripcion, valor, imagen) VALUES (2, 'POLO PHP', 'POLO PARA DESARROLLADORES DE PHP', 20.0, 'img/php.jpg')

INSERT INTO productos(id_producto, nombre, descripcion, valor, imagen) VALUES (3, 'POLO CSS', 'POLO PARA DESARROLLADORES DE CSS', 10.0, 'img/css.jpg')


CREATE TABLE ventas_encabezado (
    id_venta SERIAL PRIMARY KEY,        -- Campo entero, autoincrementado y clave primaria
    fecha DATE DEFAULT CURRENT_DATE,    -- Campo de fecha con valor predeterminado como la fecha actual
    nombre VARCHAR(255),                -- Campo de cadena para el nombre
    direccion VARCHAR(255)              -- Campo de cadena para la dirección
);


CREATE TABLE ventas_detalle (
    id_venta_detalle SERIAL PRIMARY KEY,       -- Campo entero, autoincrementado y clave primaria
    id_venta_encabezado INTEGER NOT NULL,      -- Clave foránea que apunta a ventas_encabezado
    id_producto INTEGER NOT NULL,              -- ID del producto
    id_talla INTEGER NOT NULL,                 -- ID de la talla
    valor DECIMAL(10, 2) NOT NULL              -- Campo para valor decimal, con precisión de hasta 10 dígitos y 2 decimales
);







INSERT INTO tallas(id_talla, abreviatura, nombre) VALUES (1, 'S', 'SMALL');
INSERT INTO tallas(id_talla, abreviatura, nombre) VALUES (2, 'M', 'MEDIUM');
INSERT INTO tallas(id_talla, abreviatura, nombre) VALUES (3, 'L', 'LARGE');

SELECT * FROM ventas_encabezado;
SELECT * FROM ventas_detalle;



SELECT * FROM tallas;