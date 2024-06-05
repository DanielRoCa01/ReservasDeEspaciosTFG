-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: tfg_reservas
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `espacios`
--

DROP TABLE IF EXISTS `espacios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espacios` (
  `idEspacio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `tamaño` varchar(255) DEFAULT NULL,
  `horaApertura` time DEFAULT NULL,
  `horaCierre` time DEFAULT NULL,
  `descripcion` mediumtext,
  `idInstalacion` int NOT NULL,
  PRIMARY KEY (`idEspacio`),
  KEY `fgk_espacio_instalacion` (`idInstalacion`),
  CONSTRAINT `fgk_espacio_instalacion` FOREIGN KEY (`idInstalacion`) REFERENCES `instalaciones` (`idInstalacion`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espacios`
--

LOCK TABLES `espacios` WRITE;
/*!40000 ALTER TABLE `espacios` DISABLE KEYS */;
INSERT INTO `espacios` VALUES (12,'Escritorio 1','XS','07:00:00','21:00:00','Escritorio pegado a la pared derecha',8),(13,'Escritorio 2','XS','08:00:00','20:30:00','Escritorio pegado a la pared izquierda',8),(14,'Escritorio 3','S','07:00:00','22:00:00','Escritorio pegado a la pared del fondo',8),(15,'Despacho 1','S','08:00:00','19:00:00','Despacho pequeño para le gestion de tramites con privacidad',8),(16,'Despacho 2','M','08:00:00','19:00:00','Despacho mediano para la gestion de tramites con privacidad a un mayor numero de personas.',8),(17,'Sala de reuniones 1','S','09:00:00','19:00:00','Sala de reuniones pequeña ubicada en la primera planta',8),(18,'Sala de reuniones 2','M','10:00:00','19:00:00','Sala de reuniones mediana ubicada en la segunda planta',8),(19,'Sala de reuniones 3','L','10:00:00','18:00:00','Sala de reuniones grande ubicada en la tercera planta',8),(20,'Pistas deportivas','L','08:00:00','20:00:00','Se trata de unas pistas de futbol y baloncesto.',9),(21,'Sala de juegos','L','08:00:00','16:00:00','Sala con pin pon y futbolin',9),(22,'Dormitorio','S','12:00:00','20:00:00','Fondo derecha',10),(23,'Cocina','M','11:00:00','22:00:00','Fondo izquierda',10),(24,'Almacen','S','06:00:00','18:00:00','Almacen del fondo',11),(25,'Sala 1','M','09:00:00','21:30:00','A la derecha',11),(26,'Campo de futbol','M','06:30:00','18:30:00','Campo de futbol 7',12),(27,'Gimnasio','L','09:00:00','17:00:00','Gimnasio a cubierto',12),(28,'Aula 1','S','08:00:00','12:00:00','Aula pequeña',12),(29,'AUla Multiusos','L','15:30:00','18:30:00','JAJAJ Culo',5),(30,'Sala de Juegos','L','06:00:00','18:00:00','Sala de juegos',5),(31,'Sala de Reuniones','M','12:00:00','19:00:00','Es una sala para reunirse',12);
/*!40000 ALTER TABLE `espacios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instalaciones`
--

DROP TABLE IF EXISTS `instalaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instalaciones` (
  `idInstalacion` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `descripcion` mediumtext,
  PRIMARY KEY (`idInstalacion`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instalaciones`
--

LOCK TABLES `instalaciones` WRITE;
/*!40000 ALTER TABLE `instalaciones` DISABLE KEYS */;
INSERT INTO `instalaciones` VALUES (5,'Naves','Centro juvenil salesiano		'),(8,'Oficinas','Oficinas de una aseguradora a todo riesgo'),(9,'LasNaves','Centro socieducativo con especializacion formando jovenes.'),(10,'Instalacion1','esto es una prueba'),(11,'VideoClub','Esto es un videoclub'),(12,'Colegio','Esto es un colegio'),(15,'PruebaExistente','PruebaExistente'),(16,'InstalacionPrueba','Prueba de creacion de instalacion'),(17,'Creacion prueba','Creacion prueba');
/*!40000 ALTER TABLE `instalaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `idReserva` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int DEFAULT NULL,
  `idEspacio` int NOT NULL,
  `horaInicio` time DEFAULT NULL,
  `horaFinal` time DEFAULT NULL,
  `fechaReserva` date DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `descripcion` mediumtext,
  PRIMARY KEY (`idReserva`),
  KEY `fgk_reservas_espacio` (`idEspacio`),
  KEY `fgk_reservas_usuario_idx` (`idUsuario`),
  CONSTRAINT `fgk_reservas_espacio` FOREIGN KEY (`idEspacio`) REFERENCES `espacios` (`idEspacio`),
  CONSTRAINT `fgk_reservas_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (35,16,20,'10:00:00','14:00:00','2024-05-31','Torneo de futbol','RESERVADA'),(36,16,21,'10:00:00','14:00:00','2024-05-31','Torneo de pin pon','RESERVADA'),(37,17,23,'16:00:00','18:00:00','2024-05-31','','RESERVADA'),(38,18,24,'10:00:00','12:00:00','2024-05-31','','RESERVADA'),(39,20,26,'10:00:00','13:00:00','2024-05-31','CANCELADA','RESERVADA'),(40,20,27,'10:00:00','13:00:00','2024-05-31','CANCELADA','RESERVADA'),(41,20,27,'12:00:00','14:00:00','2024-05-30','CANCELADA','RESERVADA'),(42,21,28,'10:00:00','14:00:00','2024-06-07','CANCELADA','Curso de prevencion de riesgos laborales'),(43,20,27,'14:00:00','16:30:00','2024-06-08','CANCELADA',''),(44,20,27,'12:00:00','14:00:00','2024-06-07','RESERVADA',''),(45,22,26,'12:30:00','17:30:00','2024-05-30','RESERVADA',''),(46,20,26,'15:00:00','16:00:00','2024-06-28','RESERVADA','');
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secciones`
--

DROP TABLE IF EXISTS `secciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secciones` (
  `idSeccion` int NOT NULL AUTO_INCREMENT,
  `nombreSeccion` varchar(255) DEFAULT NULL,
  `descripcion` mediumtext,
  `idInstalacion` int DEFAULT NULL,
  PRIMARY KEY (`idSeccion`),
  KEY `fk_seccion_instalacion_idx` (`idInstalacion`),
  CONSTRAINT `fk_seccion_instalacion` FOREIGN KEY (`idInstalacion`) REFERENCES `instalaciones` (`idInstalacion`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secciones`
--

LOCK TABLES `secciones` WRITE;
/*!40000 ALTER TABLE `secciones` DISABLE KEYS */;
INSERT INTO `secciones` VALUES (31,'GENERAL','Proposito general',8),(32,'Informaticos','Seccion de control de la red informatica de la oficina',8),(33,'GENERAL','Proposito general',9),(34,'GENERAL','Proposito general',10),(35,'Chupapingas','Son expertos mamaescrotos y fan de sanderson',10),(36,'GENERAL','Proposito general',11),(37,'Vendedores','Estos venden',11),(38,'GENERAL','Proposito general',12),(39,'Educadores','Expertos educando mucho',12),(40,'GENERAL','Proposito general',5),(41,'GENERAL','Proposito general',5),(42,'GENERAL','Proposito general',15),(43,'GENERAL','Proposito general',16),(44,'GENERAL','Proposito general',17),(45,'Secretaria','Son secretarios',12),(46,'E.Primaria','Profesionales de Educacion primaria',12);
/*!40000 ALTER TABLE `secciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombreUsuario` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `idSeccion` int DEFAULT NULL,
  `idInstalacion` int DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fgk_usuario_instalacion_idx` (`idInstalacion`),
  KEY `fgk_usuario_seccion_idx` (`idSeccion`),
  CONSTRAINT `fgk_usuario_instalacion` FOREIGN KEY (`idInstalacion`) REFERENCES `instalaciones` (`idInstalacion`),
  CONSTRAINT `fgk_usuario_seccion` FOREIGN KEY (`idSeccion`) REFERENCES `secciones` (`idSeccion`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (14,'Nacho','ADMINISTRADOR',31,8),(15,'Luis','USUARIO',32,8),(16,'Raul','ADMINISTRADOR',33,9),(17,'Felipe','ADMINISTRADOR',34,10),(18,'Fernando','ADMINISTRADOR',37,11),(19,'Dani','USUARIO',37,11),(20,'Julio','ADMINISTRADOR',38,12),(21,'Fernando','USUARIO',38,12),(22,'Jose Antonio','USUARIO',39,12),(23,'juan','ADMINISTRADOR',40,5),(24,'assda','ADMINISTRADOR',40,5),(25,'Dani','ADMINISTRADOR',40,5),(26,'PruebaExistente','ADMINISTRADOR',42,15),(27,'Administrador','ADMINISTRADOR',43,16),(28,'Creacion prueba','ADMINISTRADOR',44,17),(29,'Paco','ADMINISTRADOR',46,12);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'tfg_reservas'
--
/*!50003 DROP FUNCTION IF EXISTS `comprobarDisponibilidad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `comprobarDisponibilidad`(idEspacio int,  fecha DATE, hora1 TIME, hora2 TIME) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
    DECLARE count_spaces boolean;
    DECLARE horaOpen boolean;
     DECLARE horaClose TIME;
     SELECT !(horaApertura > hora1 or horaCierre < hora1
    or  horaApertura > hora2 or horaCierre <hora2) into horaOpen from espacios where espacios.idEspacio =idEspacio;
    
    SELECT COUNT(idReserva)=0
    INTO count_spaces  FROM reservas R 
    WHERE R.idEspacio = idEspacio AND datediff(fechaReserva,fecha)  =0 AND R.estado !='CANCELADA'
    AND ((R.horaInicio >=hora1 AND R.horaInicio <hora2)
    or ( R.horaFinal >hora1 AND R.horaFinal <=hora2)
    or ( R.horaInicio <= hora1 AND R.horaFinal >=hora2)) 
    ;
    
    
    
   
        RETURN count_spaces AND horaOpen ;
    
       
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `comprobarDisponibilidadModif` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `comprobarDisponibilidadModif`(idEspacio int,  fecha DATE, hora1 TIME, hora2 TIME, idReserva int) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
    DECLARE count_spaces boolean;
    DECLARE horaOpen boolean;
     DECLARE horaClose TIME;
     SELECT !(horaApertura > hora1 or horaCierre < hora1
    or  horaApertura > hora2 or horaCierre <hora2) into horaOpen from espacios where espacios.idEspacio =idEspacio;
    
    SELECT COUNT(idReserva)=0
    INTO count_spaces  FROM reservas R 
    WHERE R.idEspacio = idEspacio And R.idReserva!=idReserva AND datediff(fechaReserva,fecha)  =0 AND R.estado !='CANCELADA'
    AND ((R.horaInicio >=hora1 AND R.horaInicio <hora2)
    or ( R.horaFinal >hora1 AND R.horaFinal <=hora2)
    or ( R.horaInicio <= hora1 AND R.horaFinal >=hora2)) 
    ;
    
    
    
   
        RETURN count_spaces AND horaOpen ;
    
       
  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `comprobarNombreEspacio` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `comprobarNombreEspacio`(nombre VARCHAR(255),idInstalacion INT) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
    DECLARE count_spaces INT;
    
    SELECT COUNT(idEspacio) INTO count_spaces FROM espacios WHERE espacios.nombre = nombre and espacios.idInstalacion=idInstalacion;
    
    IF count_spaces != 0 THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consultarEspaciosDisponibles` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `consultarEspaciosDisponibles`(idInstalacion int,IN fecha DATE, hora1 TIME, hora2 TIME)
BEGIN
Select * from espacios where espacios.idInstalacion=idInstalacion 
AND !(horaApertura > hora1 or horaCierre < hora1
    or  horaApertura > hora2 or horaCierre <hora2)
and espacios.idEspacio not IN(
select reservas.idEspacio From reservas
 WHERE datediff(fechaReserva,fecha)  =0 AND reservas.estado !='CANCELADA' 
AND((reservas.horaInicio >=hora1 AND reservas.horaInicio <hora2)
 or ( reservas.horaFinal >hora1 AND reservas.horaFinal <=hora2)
or ( reservas.horaInicio <= hora1 AND reservas.horaFinal >=hora2)) group by idEspacio) 

     ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-05 13:29:38
