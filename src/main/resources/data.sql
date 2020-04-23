/*Crea las tablas y datos necesarios para testing en base de datos en memoria H2*/

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(45) NOT NULL,
  `mail_usuario` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(250) NOT NULL,
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
  CONSTRAINT `tarea_lista` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id_lista`) 
);

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `FK_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `usuario` (`id_usuario`)
);



INSERT INTO `usuario` VALUES (1,'Julian Franco','lapunzinajulian@gmail.com', 'juli123','$2y$12$BlpFZlqAyswX8f.2EMI/X.y6.ZdCKLayuUpGI05TvDKHVkuXh/gnK'),
							 (2,'Cristiano Ronaldo','cr7@hotmail.com', 'cris123','123'), 
							 (3,'Tevez','elapache@yahoo.com', 'carlos123','123');

INSERT INTO `lista` VALUES (1,'Trabajo',2),
						   (2,'Casa',1),
						   (3,'Entrenamiento',2),
						   (4,'Hobie',3);

INSERT INTO `tarea` VALUES (1, 'Tarea 1', 'Detalle 1', '2020-04-07', null, 1, 1),
						   (2, 'Tarea 2', 'Detalle 2', '2020-04-08', null, 1, 1),
						   (3, 'Tarea 3', 'Detalle 3', '2020-04-09', null, 0, 1),
						   (4, 'Tarea 4', 'Detalle 4', '2020-04-07', null, 0, 2),
						   (5, 'Tarea 5', 'Detalle 5', '2020-04-07', null, 0, 2),
						   (6, 'Tarea 6', 'Detalle 6', '2020-04-08', null, 0, 3);
						   
INSERT INTO `role` (`id`, `name`, `description`) VALUES (3,'ADMIN','Admin role'), 
														(4,'USER','User role');

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES (1,3), 
													   (2,3), 
													   (3,4);

