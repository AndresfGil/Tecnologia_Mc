-- Script SQL para crear la tabla de tecnologías en MySQL
-- Base de datos: bootcamp-tecnologia

CREATE DATABASE IF NOT EXISTS `bootcamp-tecnologia` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `bootcamp-tecnologia`;

CREATE TABLE IF NOT EXISTS `tecnologias` (
    `id_tecnologia` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(50) NOT NULL,
    `descripcion` VARCHAR(90) NOT NULL,
    INDEX `idx_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

