/*Crea las tablas y datos necesarios para testing en base de datos en memoria H2*/

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(45) NOT NULL,
  `mail_usuario` varchar(45) NOT NULL,
  PRIMARY KEY (`id_usuario`)
);

CREATE TABLE `lista` (
  `id_lista` int(11) NOT NULL AUTO_INCREMENT,
  `titulo_lista` varchar(45) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_lista`),
  CONSTRAINT `lista_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
);

CREATE TABLE `tarea` (
  `id_tarea` int(10) NOT NULL AUTO_INCREMENT,
  `titulo_tarea` varchar(45) NOT NULL,
  `detalle_tarea` varchar(45) DEFAULT NULL,
  `fecha_realizar_tarea` date DEFAULT NULL,
  `hora_realizar_tarea` time DEFAULT NULL,
  `realizada` tinyint(4) NOT NULL DEFAULT '0',
  `id_lista` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_tarea`),
  CONSTRAINT `tarea_lista` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id_lista`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO `usuario` VALUES (1,'Julian Franco','lapunzinajulian@gmail.com'),(2,'Cristiano Ronaldo','cr7@hotmail.com');
INSERT INTO `lista` VALUES (1,'Trabajo',1),(2,'Casa',1),(3,'Entrenamiento',1),(4,'Hobie',1);
INSERT INTO `tarea` VALUES (1, 'Tarea 1', 'Detalle 1', '2020-04-07', null, 1, 1),
						   (2, 'Tarea 2', 'Detalle 2', '2020-04-07', null, 1, 1),
						   (3, 'Tarea 3', 'Detalle 3', '2020-04-08', null, 0, 1);
