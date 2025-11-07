-- Migración Flyway V1: Crear tabla de tecnologías
-- Base de datos: bootcamp-tecnologia

CREATE TABLE IF NOT EXISTS `tecnologias` (
    `id_tecnologia` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(50) NOT NULL,
    `descripcion` VARCHAR(90) NOT NULL,
    INDEX `idx_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

