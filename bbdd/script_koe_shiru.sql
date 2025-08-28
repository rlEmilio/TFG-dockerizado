-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS koe_shiru;
USE koe_shiru;

-- TABLA USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(15),
    nombre VARCHAR(20),
    apellidos VARCHAR(40),
    correo VARCHAR(35),
    contrasenia VARCHAR(255),
    fecha_nacimiento DATE,
    genero VARCHAR(15),
    premium BOOLEAN,
    imagen VARCHAR(255),
    UNIQUE (username)
);

-- TABLA ACTORES DE VOZ
CREATE TABLE IF NOT EXISTS actores_de_voz (
    id_actor INT PRIMARY KEY,
    nombre VARCHAR(30),
    fecha_nacimiento DATE,
    popularidad INT,
    imagen VARCHAR(255)
);

-- TABLA TRABAJOS
CREATE TABLE IF NOT EXISTS trabajos (
    id_trabajo INT PRIMARY KEY AUTO_INCREMENT,
    personaje VARCHAR(25),
    anime VARCHAR(35),
    rol BOOLEAN
);

-- TABLA FAVORITOS
CREATE TABLE IF NOT EXISTS favoritos (
    id_usuario INT,
    id_actor INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_actor) REFERENCES actores_de_voz(id_actor)
);

-- TABLA MENSAJES
CREATE TABLE IF NOT EXISTS mensajes (
    id_mensaje INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    texto VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- TABLA CLUBS
CREATE TABLE IF NOT EXISTS clubs (
    id_club INT PRIMARY KEY AUTO_INCREMENT,
    id_mensaje INT,
    nombre VARCHAR(50),
    miembros INT,
    fecha_creacion DATE,
    descripcion VARCHAR(255),
    imagen VARCHAR(255),
    FOREIGN KEY (id_mensaje) REFERENCES mensajes(id_mensaje)
);

-- TABLA PERTENECE (intermedia club-usuario)
CREATE TABLE IF NOT EXISTS pertenece (
    id_club INT,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_club) REFERENCES clubs(id_club)
);

-- TABLA TIENE (intermedia actor-trabajo)
CREATE TABLE IF NOT EXISTS tiene (
    id_actor INT,
    id_trabajo INT,
    FOREIGN KEY (id_actor) REFERENCES actores_de_voz(id_actor),
    FOREIGN KEY (id_trabajo) REFERENCES trabajos(id_trabajo)
);

-- TABLA PRODUCTOS
CREATE TABLE IF NOT EXISTS productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    enlace VARCHAR(255),
    imagen VARCHAR(255)
);

-- INSERTS DE ACTORES DE VOZ
INSERT INTO actores_de_voz (id_actor, nombre, fecha_nacimiento, popularidad, imagen) VALUES
(123, 'Nakai Kazuya', '1967-11-25', 17286, 'https://cdn.myanimelist.net/images/voiceactors/1/62866.jpg'),
(99, 'Miyuki Sawashiro', '1985-06-02', 40956, 'https://cdn.myanimelist.net/images/voiceactors/2/65500.jpg'),
(82, 'Park Romi', '1972-01-22', 40956, 'https://cdn.myanimelist.net/images/voiceactors/1/54602.jpg');

-- INSERTS DE TRABAJOS
INSERT INTO trabajos (personaje, anime, rol) VALUES
('Roronoa Zoro', 'One Piece', true),
('Hijikata Toushiro', 'Gintama', false),
('Mugen', 'Samurai Champloo', true);

-- INSERTS DE CLUBS
INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(17, 'Fans de Kana Hanazawa', 1400, '2020-06-20', 'Actualización: club oficial de Kana Hanazawa con contenido exclusivo.', 'https://upload.wikimedia.org/wikipedia/commons/8/82/240211_Kana_Hanazawa.jpg');

-- INSERTS DE PRODUCTOS (ejemplo)
INSERT INTO productos (nombre, enlace, imagen) VALUES
('Insertar nombre de merchan', 'Insertar enlace del merchan', NULL);
