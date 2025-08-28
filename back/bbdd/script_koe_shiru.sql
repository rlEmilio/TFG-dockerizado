-- Para crear la base de datos. Por coherencia, le he puesto el nombre de nuestro proyecto
CREATE SCHEMA koe_shiru;

DROP SCHEMA koe_shiru;

-- ASeguraos de ejecutar esto, sino no vais a poder usar la bbdd ni ninguna de sus tablas.
USE koe_shiru;



-- USUARIOS
CREATE TABLE usuarios (
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(15),
    nombre VARCHAR(20), -- Por nombres compuestos
    apellidos VARCHAR(40), -- Por apellidos compuestos
    correo VARCHAR(35), -- No vamos a juzgar a gente que tenga correo kilometrico
    contrasenia VARCHAR(255), -- Habrá que introducirlas con HASH
    fecha_nacimiento DATE,
    genero VARCHAR(15),
    premium BOOLEAN, -- premium - true, free - false,
    imagen VARCHAR(255),
    UNIQUE (username)
);

-- No se han insertado usuarios debido al hash del back.
    
    
SELECT * FROM usuarios;

DROP TABLE usuarios;

DELETE FROM usuarios;


-- ACTORES DE VOZ
CREATE TABLE actores_de_voz (
	id_actor INT PRIMARY KEY,
    nombre VARCHAR(30),
    fecha_nacimiento DATE,
    popularidad INT,
    imagen VARCHAR(255)
);

INSERT INTO actores_de_voz (nombre, fecha_nacimiento, popularidad) 
	VALUES ("Miyuki Sawashiro", "1985-06-02", 40919);
INSERT INTO actores_de_voz (nombre, fecha_nacimiento, popularidad) 
	VALUES ("Park Romi", "1972-01-22", 18210);
INSERT INTO actores_de_voz (nombre, fecha_nacimiento, popularidad) 
	VALUES ("Kugimiya Rie", "1985-06-02", 40568);
    
SELECT * FROM actores_de_voz;

DROP TABLE actores_de_voz;




-- Favoritos
CREATE TABLE favoritos (
	id_usuario INT,
	id_actor INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_actor) REFERENCES actores_de_voz(id_actor)
);

INSERT INTO favoritos VALUES (
	(SELECT id_usuario FROM usuarios WHERE nombre = "Miguel Ángel"),
	(SELECT id_actor FROM actores_de_voz WHERE nombre = "Kugimiya Rie")
);

INSERT INTO favoritos VALUES (
	(SELECT id_usuario FROM usuarios WHERE nombre = "Miguel Ángel"),
	(SELECT id_actor FROM actores_de_voz WHERE nombre = "Park Romi")
);

INSERT INTO favoritos VALUES (
	(SELECT id_usuario FROM usuarios WHERE nombre = "Miguel Ángel"),
	(SELECT id_actor FROM actores_de_voz WHERE nombre = "Miyuki Sawashiro")
);

SELECT * FROM favoritos;

-- Los actores de voz favoritos del usuario Migue, con nombre de personaje y anime.
SELECT a.nombre, t.personaje, t.anime FROM favoritos f
	JOIN usuarios u ON f.id_usuario = u.id_usuario
	JOIN actores_de_voz a ON f.id_actor = a.id_actor
	JOIN trabajos t ON a.id_actor = t.id_actor
WHERE u.id_usuario = 
	(SELECT id_usuario FROM usuarios WHERE nombre = "Miguel Ángel");

DROP TABLE favoritos;




-- Trabajos
CREATE TABLE trabajos (
	id_trabajo INT PRIMARY KEY AUTO_INCREMENT,
    personaje VARCHAR(25),
    anime VARCHAR(35),
    rol BOOLEAN -- main - true, supporting - false
);

INSERT INTO trabajos (personaje, anime, rol) VALUES (
    "Edward Elric",
    "Fullmetal Alchemist Brotherhood",
    TRUE
);

INSERT INTO trabajos (personaje, anime, rol) VALUES (
    "Hitsugaya Toushiro",
    "Bleach",
    FALSE
);

INSERT INTO trabajos (personaje, anime, rol) VALUES (
    "Hange Zoë",
    "Shingeki no Kyojin",
    FALSE
);

INSERT INTO trabajos (personaje, anime, rol) VALUES (
    "Alphonse Elric",
    "Fullmetal Alchemist Brotherhood",
    TRUE
);

INSERT INTO trabajos (personaje, anime, rol) VALUES (
    "Hanako Koyanagi",
    "Wotaku ni Koi wa Muzukashii",
    TRUE
);

SELECT * FROM trabajos;

DROP TABLE trabajos;




-- Mensajes
CREATE TABLE mensajes (
	id_mensaje INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    texto VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

INSERT INTO mensajes (texto) VALUES ("Esto es una prueba");
INSERT INTO mensajes (texto) VALUES ("Y esto otra");

SELECT * FROM mensajes;

DROP TABLE mensajes;






-- Clubs
CREATE TABLE clubs (
	id_club INT PRIMARY KEY AUTO_INCREMENT,
    id_mensaje INT,
    nombre VARCHAR(50),
    miembros INT, -- numero de miembros
    fecha_creacion DATE,
    descripcion VARCHAR(255),
    imagen VARCHAR(255),
    FOREIGN KEY (id_mensaje) REFERENCES mensajes(id_mensaje)
);

SELECT * FROM clubs;

DROP TABLE clubs;




-- Pertenece (tabla intermedia entre club y usuario
CREATE TABLE pertenece (
	id_club INT,
	id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_club) REFERENCES clubs(id_club)
);

INSERT INTO pertenece VALUES (
	(SELECT id_club FROM clubs WHERE nombre = "Club de fans de Hiroshi Kamiya"),
    (SELECT id_usuario FROM usuarios WHERE nombre = "Miguel Ángel")
);

INSERT INTO pertenece VALUES (
	(SELECT id_club FROM clubs WHERE nombre = "Club de fans de Hiroshi Kamiya"),
    (SELECT id_usuario FROM usuarios WHERE username = "Miguel Ángel")
);

SELECT * FROM pertenece;

DROP TABLE pertenece;




-- Tiene
CREATE TABLE tiene (
	id_actor INT,
	id_trabajo INT,
    FOREIGN KEY (id_actor) REFERENCES actores_de_voz(id_actor),
    FOREIGN KEY (id_trabajo) REFERENCES trabajos(id_trabajo)
);

INSERT INTO tiene VALUES (
	(SELECT id_actor FROM actores_de_voz WHERE nombre = "Park Romi"),
    1
);

INSERT INTO tiene VALUES (
	(SELECT id_actor FROM actores_de_voz WHERE nombre = "Park Romi"),
    2
);

INSERT INTO tiene VALUES (
	(SELECT id_actor FROM actores_de_voz WHERE nombre = "Park Romi"),
    3
);

SELECT * FROM tiene;

DROP TABLE tiene;



-- Productos
CREATE TABLE productos (
	id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    enlace VARCHAR(255),
    imagen VARCHAR(255)
);

INSERT INTO productos (nombre, enlace) VALUES (
	"Insertar nombre de merchan",
    "Insertar enlace del merchan"
); -- Insertar imagen del merchan

SELECT * FROM productos;

DROP TABLE productos;





INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(17, 'Fans de Kana Hanazawa', 1400, '2020-06-20', 'Actualización: club oficial de Kana Hanazawa con contenido exclusivo.', 'https://upload.wikimedia.org/wikipedia/commons/8/82/240211_Kana_Hanazawa.jpg');
INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(18, 'Yuichi Nakamura Admirers', 1150, '2021-01-20', 'Seguidores de Yuichi Nakamura, voz de Gojo y Gray', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQobgaHEttltoT6-LAVb0kxBqpjuv_qXH5u0LAyoAb0xgCbgsYxgE6M1Y9VCnXaW0C44BQ&usqp=CAU');
INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(19, 'Ayane Sakura Club', 780, '2019-09-08', 'Ayane Sakura, la inconfundible voz de Uraraka y Natsumi.', 'https://upload.wikimedia.org/wikipedia/commons/a/a3/Sakura_Ayane_from_"PSYCHO-PASS_Sinners_of_the_System_Case.1_%26_Case.2"_at_Opening_Ceremony_of_the_Tokyo_International_Film_Festival_2018_(45568231662).jpg');
INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(20, 'Mamoru Miyano Fandom', 860, '2021-12-01', 'Miyuki Sawashiro', 'https://i.mydramalist.com/Z6WnO_5f.jpg');
INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(25, 'Club de Saori Hayami', 1120, '2021-05-10', 'Voz angelical y elegante: Yor Forger, Shinobu, Yukino.', 'https://cdn.myanimelist.net/images/voiceactors/3/53035.jpg');
INSERT INTO clubs (id_club, nombre, miembros, fecha_creacion, descripcion, imagen) VALUES
(26, 'Tomokazu Sugita Central', 1340, '2020-08-22', 'Legendario como Gintoki, Joseph Joestar y Kyon.', 'https://cdn.myanimelist.net/images/voiceactors/2/51011.jpg');




-- Inserts harcodeados - actores de voz

INSERT INTO actores_de_voz VALUES (123, 'Nakai Kazuya', '1967-11-25', 17286, 'https://cdn.myanimelist.net/images/voiceactors/1/62866.jpg');
INSERT INTO actores_de_voz VALUES (99, 'Miyuki Sawashiro', '1985-06-02', 40956, 'https://cdn.myanimelist.net/images/voiceactors/2/65500.jpg');
INSERT INTO actores_de_voz VALUES (82, 'Park Romi', '1972-01-22', 40956, 'https://cdn.myanimelist.net/images/voiceactors/1/54602.jpg');
INSERT INTO actores_de_voz VALUES (8, 'Kugimiya Rie', '1979-05-30', 40598, 'https://cdn.myanimelist.net/images/voiceactors/3/63374.jpg');
INSERT INTO actores_de_voz VALUES (2, 'Sugita Tomokazu', '1980-10-11', 47475, 'https://cdn.myanimelist.net/images/voiceactors/1/81054.jpg');
INSERT INTO actores_de_voz VALUES (79, 'Sakurai Takahiro', '1974-06-13', 25050, 'https://cdn.myanimelist.net/images/voiceactors/3/62791.jpg');
INSERT INTO actores_de_voz VALUES (95, 'Junichi Suwabe', '1978-05-04', 28336, 'https://cdn.myanimelist.net/images/voiceactors/3/66059.jpg');
INSERT INTO actores_de_voz VALUES (212, 'Daisuke Ono', '1972-03-29', 49165, 'https://cdn.myanimelist.net/images/voiceactors/1/54593.jpg');
INSERT INTO actores_de_voz VALUES (125, 'Hiroaki Hirata', '1963-08-07', 2778, 'https://cdn.myanimelist.net/images/voiceactors/1/54593.jpg');
INSERT INTO actores_de_voz VALUES (513, 'Yuuichi Nakamura', '1980-02-20', 32825, 'https://cdn.myanimelist.net/images/voiceactors/1/74056.jpg');
INSERT INTO actores_de_voz VALUES (270, 'Nobuhiko Okamoto', '1986-10-24', 45086, 'https://cdn.myanimelist.net/images/voiceactors/1/74056.jpg');
INSERT INTO actores_de_voz VALUES (11817, 'Yoshitsugu Matsuoka', '1986-09-17', 40969, 'https://cdn.myanimelist.net/images/voiceactors/2/40132.jpg');
INSERT INTO actores_de_voz VALUES (118, 'Hiroshi Kamiya', '1975-01-28', 107904, 'https://cdn.myanimelist.net/images/voiceactors/1/66163.jpg');
INSERT INTO actores_de_voz VALUES (185, 'Kana Hanazawa', '1989-02-25', 102112, 'https://cdn.myanimelist.net/images/voiceactors/3/69318.jpg');
INSERT INTO actores_de_voz VALUES (65, 'Mamoru Miyano', '1983-06-08', 91907, 'https://cdn.myanimelist.net/images/voiceactors/1/66414.jpg');
INSERT INTO actores_de_voz VALUES (672, 'Yuuki Kaji', '1985-09-03', 75325, 'https://cdn.myanimelist.net/images/voiceactors/2/66416.jpg');
INSERT INTO actores_de_voz VALUES (34785, 'Rie Takahashi', '1994-02-27', 60932, 'https://cdn.myanimelist.net/images/voiceactors/3/78665.jpg');
INSERT INTO actores_de_voz VALUES (6686, 'Aoi Yuuki', '1992-03-27', 34896, 'https://cdn.myanimelist.net/images/voiceactors/3/67808.jpg');
INSERT INTO actores_de_voz VALUES (160, 'Takehito Koyasu', '1967-05-05', 36590, 'https://cdn.myanimelist.net/images/voiceactors/1/63375.jpg');
INSERT INTO actores_de_voz VALUES (16635, 'Natsuki Hanae', '1991-06-26', 20539, 'https://cdn.myanimelist.net/images/voiceactors/3/85088.jpg');
INSERT INTO actores_de_voz VALUES (75, 'Mayumi Tanaka', '1955-01-15', 8020, 'https://cdn.myanimelist.net/images/voiceactors/3/73373.jpg');
INSERT INTO actores_de_voz VALUES (557, 'Masako Nozawa', '1936-10-25', 3699, 'https://cdn.myanimelist.net/images/voiceactors/2/77340.jpg');
INSERT INTO actores_de_voz VALUES (17, 'Akira Ishida', '1967-11-02', 12710, 'https://cdn.myanimelist.net/images/voiceactors/3/62624.jpg');
INSERT INTO actores_de_voz VALUES (13639, 'Maaya Uchida', '1989-12-27', 12671, 'https://cdn.myanimelist.net/images/voiceactors/1/85234.jpg');
INSERT INTO actores_de_voz VALUES (81, 'Nana Mizuki', '1980-01-21', 11886, 'https://cdn.myanimelist.net/images/voiceactors/2/79687.jpg');
INSERT INTO actores_de_voz VALUES (22, 'Shinichiro Miki', '1968-03-18', 7540, 'https://cdn.myanimelist.net/images/voiceactors/2/70845.jpg');
INSERT INTO actores_de_voz VALUES (11787, 'Asami Seto', '1993-04-02', 11787, 'https://cdn.myanimelist.net/images/voiceactors/3/65839.jpg');
INSERT INTO actores_de_voz VALUES (158, 'Marina Inoue', '1985-01-20', 7992, 'https://cdn.myanimelist.net/images/voiceactors/1/68016.jpg');
INSERT INTO actores_de_voz VALUES (67, 'Yamaguchi Kappei', '1965-05-23', 5752, 'https://cdn.myanimelist.net/images/voiceactors/2/74474.jpg');
INSERT INTO actores_de_voz VALUES (17215, 'Atsumi Tanezaki', '1990-09-27', 5201, 'https://cdn.myanimelist.net/images/voiceactors/3/77190.jpg');
INSERT INTO actores_de_voz VALUES (20, 'Katsuyuki Konishi', '1973-04-21', 5196, 'https://cdn.myanimelist.net/images/voiceactors/1/69727.jpg');
INSERT INTO actores_de_voz VALUES (21, 'Kazuhiko Inoue', '1954-03-26', 3493, 'https://cdn.myanimelist.net/images/voiceactors/3/68015.jpg');
INSERT INTO actores_de_voz VALUES (49011, 'Fairouz Ai', '1993-07-06', 5133, 'https://cdn.myanimelist.net/images/voiceactors/2/76160.jpg');
INSERT INTO actores_de_voz VALUES (146, 'Akio Ootsuka', '1959-11-24', 5164, 'https://cdn.myanimelist.net/images/voiceactors/1/58117.jpg');
INSERT INTO actores_de_voz VALUES (63, 'Keiji Fujiwara', '1964-10-05', 4435, 'https://cdn.myanimelist.net/images/voiceactors/3/57226.jpg');
INSERT INTO actores_de_voz VALUES (113, 'Rikiya Koyama', '1963-12-18', 4296, 'https://cdn.myanimelist.net/images/voiceactors/1/54010.jpg');
INSERT INTO actores_de_voz VALUES (8662, 'Asami Imai', '1977-05-16', 3671, 'https://cdn.myanimelist.net/images/voiceactors/1/68014.jpg');



-- Inserts harcodeados - actores de voz
-- id_trabajo, personake, anime, rol
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Roronoa Zoro', 'One Piece', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hijikata Toushiro', 'Gintama', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Mugen', 'Samurai Champloo', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Celty Sturluson', 'Durarara', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Suruga Kanbaru', 'Bakemonogatari', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Bishamon', 'Noragami', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hitsugaya Toushiro', 'Bleach', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Edward Elric', 'Fullmetal Alchemist Brotherhood', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hange Zoe', 'Shingeki no Kyojin', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kagura', 'Gintama', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Alphonse Elric', 'Fullmetal Alchemist Brotherhood', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Aisaka Taiga', 'Toradora', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sakata Gintoki', 'Gintama', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Escanor', 'Nanatsu no Taizai', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Charlotte Katakuri', 'One Piece', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Reigen Arataka', 'Mob Psycho', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Griffith', 'Berserk', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Giyuu Tomioka', 'Kimetsu no Yaiba', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Suguru Getou', 'Jujutsu Kaisen', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sukehiro Yami', 'Black Clover', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Ryoumen Sukuna', 'Jujutsu Kaisen', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Aomine Daiki', 'Kuroko no Basket', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Smith Erwin', 'Shingeki no Kyojin', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kuujou Joutarou', 'Jojo no Kimyou na Bouken', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Handa Sei', 'Barakamon', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hori Kyousuke', 'Hormiya', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sanji', 'One Piece', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Nanba Mutta', 'Uchuu Kyoudai', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Benny', 'Black Lagoon', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Satoru Gojou', 'Jujutsu Kaisen', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Okazaki Tomoya', 'Clannad', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Tetsurou Kuroo', 'Haikyuu', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Katsuki Bakugou', 'Boku no Hero Academia', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Takumi Usui', 'Kaichou wa Maid-sama!', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Akabane Karma', 'Ansatsu Kyoushitsu', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Yukihira Souma', 'Shokugeki no Souma', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hashibira Inosuke', 'Kimetsu no Yaiba', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kirigaya Kazuto', 'Sword Art Online', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Levi', 'Shingeki no Kyojin', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Yato', 'Noragami', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Koyomi Araragi', 'Bakemonogatari', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Trafalgar Law', 'One Piece', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Shiina Mayuri', 'Steins;Gate', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Tachibana Kaede', 'Angel Beats', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sengoku Nadeko', 'Bakemonogatari', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Tsunemori Akane', 'Psycho-Pass', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Yagami Light', 'Death Note', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Okabe Rintarou', 'Steins;Gate', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Shinmon Benimaru', 'Enen no Shouboutai', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Eren Yeager', 'Shingeki no Kyojin', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Todoroki Shouto', 'Boku no Hero Academia', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kozume Kenma', 'Haikyuu', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Megumin', 'KonoSuba', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Emilia', 'ReZero kara Hajimeru Isekai Sikatsu', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hoshino Ai', 'Oshi no Ko', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Maomao', 'Kusuriya no Hitorigoto', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hinazuki Kayo', 'Boku dake ga Inai Machi', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Tatsumaki', 'One Punch Man', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Dio Brando', 'Jojo no Kimyou na Bouken', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Takasugi Shinsuke', 'Gintama', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Zeke', 'Shingeki no Kyojin', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kaneki Ken', 'Tokyo Ghoul', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kamado Tanjirou', 'Kimetsu no Yaiba', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Arima Kousei', 'Shigatsu wa Kimi no Uso', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Takakura Ken', 'Dandadan', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Monkey D. Luffy', 'One Piece', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Turbo Granny', 'Dandadan', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Krillin', 'Dragon Ball', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Son Goku', 'Dragon Ball', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Gaara', 'Naruto', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Katsura Kotarou', 'Gintama', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Akaza', 'Kimetsu no Yaiba', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Takanashi Rikka', 'Chuunibyou', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Iki Hiyori', 'Noragami', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Yoshioka Futaba', 'Ao Haru Ride', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hyuuga Hinata', 'Naruto', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Ayase Seiko', 'Dandadan', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Lan Fan', 'Fullmetal Alchemist Brotherhood', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Mustang Roy', 'Fullmetal Alchemist Brotherhood', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Urahara Kisuke', 'Bleach', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kaiki Deishuu', 'Bakemonogatari', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sakamoto Tatsuma', 'Gintama', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sakurajima Mai', 'Seishun Buta Yarou', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hori Kyouko', 'Horimiya', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kugisaki Nobara', 'Jujutsu Kaisen', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Arlert Armin', 'Shingeki no Kyojin', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Littner Yoko', 'Tengen Toppa Gurren Lagann', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Yaoyorozu Momo', 'Boku no Hero Academia', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Lawliet L', 'Death Note', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Usopp', 'One Piece', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Edogawa Conan', 'Meitantei Conan', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Frieren', 'Sousou no Frieren', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Forger Anya', 'Spy x Family', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hatori Chise', 'Mahoutsukai no Yome', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kamina', 'Tengen Toppa Gurren Lagann', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Uzui Tengen', 'Kimetsu no Yaiba', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Diavolo', 'Jojo no Kimyou na Bouken', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Hatake Kakashi', 'Naruto', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Tsugikuni Yoriichi', 'Kimetsu no Yaiba', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Oboro', 'Gintama', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Power', 'Chainsaw Man', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Sano Manjirou', 'Tokyo Revengers', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kuujou Jolyne', 'Jojo no Kimyou na Bouken', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kyouraku Shunshui', 'Bleach', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Rider', 'Fate/Zero', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Thorkell', 'Vinland Saga', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Leorio', 'Hunter x Hunter', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Maes Hughes', 'Fullmetal Alchemist Brotherhood', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Ladd Russo', 'Baccano', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Emiya Kiritsugu', 'Fate/Zero', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Yukihira Jouichirou', 'Shokugeki no Souma', false);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Kogorou Mouri', 'Meitantei Conan', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Makise Kurisu', 'Steins;Gate', true);
INSERT INTO trabajos (personaje, anime, rol) VALUES ('Tomone', 'Noragami', false);
