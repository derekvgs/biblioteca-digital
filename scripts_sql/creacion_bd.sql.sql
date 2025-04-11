-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_digital;

-- Usar la base de datos
USE biblioteca_digital;

-- Crear la tabla de categorías
CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- Crear la tabla de libros
CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

-- Insertar algunas categorías de ejemplo
INSERT INTO categorias (nombre) VALUES
('Novela'),
('Historia'),
('Ciencia'),
('Informática'),
('Biografía');
