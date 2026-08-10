-- =====================================================
-- Base de datos: Barberia Harry Estilos
-- Proyecto formativo SENA
-- GA7-220501096-AA2-EV01 / GA7-220501096-AA2-EV02
-- =====================================================

CREATE DATABASE IF NOT EXISTS barberia_harry_estilos
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE barberia_harry_estilos;

-- Tabla de barberos
CREATE TABLE IF NOT EXISTS barberos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabla de servicios
CREATE TABLE IF NOT EXISTS servicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    clave VARCHAR(50) NOT NULL UNIQUE,
    precio INT NOT NULL,
    duracion_minutos INT NOT NULL
) ENGINE=InnoDB;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL
) ENGINE=InnoDB;

-- Tabla de citas (entidad principal del CRUD)
CREATE TABLE IF NOT EXISTS citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    barbero_id INT NOT NULL,
    servicio_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (barbero_id) REFERENCES barberos(id),
    FOREIGN KEY (servicio_id) REFERENCES servicios(id)
) ENGINE=InnoDB;

-- Datos iniciales: Barberos
INSERT INTO barberos (nombre) VALUES ('Harry'), ('Andres'), ('Camilo');

-- Datos iniciales: Servicios
INSERT INTO servicios (nombre, clave, precio, duracion_minutos) VALUES
    ('Corte Clasico', 'corte', 25000, 45),
    ('Barba', 'barba', 14000, 25),
    ('Paquete Completo', 'paquete', 35000, 70);
