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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espacios`
--

LOCK TABLES `espacios` WRITE;
/*!40000 ALTER TABLE `espacios` DISABLE KEYS */;
INSERT INTO `espacios` VALUES (1,'Prueba','XL','09:00:00','10:00:00','si',1),(2,'teatro','XL','08:00:00','20:00:00','descripciopn',2),(3,'Piscina','XL','10:30:00','18:00:00','Campo de futbol dle real madrid',2),(4,'Campo de Futbol','L','05:00:00','19:00:00','Fuuuuuuuuuuuuuuuuuuuuuuuuutboooooool',2),(5,'Sala de Juegos','M','16:00:00','21:00:00','Sala con futbolines y pin pones',2),(6,'Cancha','xl','01:15:00','01:00:10','aupa',2),(7,'Cancha','xl','01:15:00','01:00:10','aupa',2),(8,'laboratorio','xl','03:30:00','01:16:40','aupa',2),(9,'Sala de estar','M','08:00:00','22:30:00','Sala de descanso para profesorado y personal del centro educativo. Hay mesas y una minicoina equipada para todo lo necesario.',2),(10,'Despacho','S','09:00:00','15:00:00','Despacho de personal administrativo',2),(11,'Sala de Reuniones','S','10:00:00','19:00:00','Sala para reunirese',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instalaciones`
--

LOCK TABLES `instalaciones` WRITE;
/*!40000 ALTER TABLE `instalaciones` DISABLE KEYS */;
INSERT INTO `instalaciones` VALUES (1,'instalacion',' si'),(2,'Casa','casaza'),(3,'',''),(4,'',''),(5,'Naves','Centro juvenil salesiano		');
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,1,1,'12:00:00','18:00:00','2024-01-01','si','queso'),(2,4,2,'00:00:00','00:30:00','1970-01-12','CANCELADA','Modificacion'),(3,4,1,'09:20:00','09:53:20','2024-05-19','MODIFICADA','Modificacion'),(4,2,2,'01:00:10','01:00:12','1970-01-12','CANCELADA','siuu'),(5,2,2,'01:00:10','01:00:12','1970-01-12','CANCELADA','siuu'),(6,2,2,'01:00:10','01:00:12','1970-01-12','CANCELADA','siuu'),(7,2,2,'01:00:10','01:20:00','1970-01-12','CANCELADA','Yep'),(8,1,2,'01:00:10','01:20:00','1970-01-12','CANCELADA','Yep'),(9,5,2,'01:00:10','01:20:00','1970-01-12','CANCELADA','Yep'),(10,5,2,'01:00:10','01:20:00','1970-01-12','CANCELADA','Yep'),(11,5,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(12,5,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(13,5,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(14,5,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(15,1,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(16,2,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(17,3,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(18,4,2,'01:00:10','01:20:00','2001-09-09','CANCELADA','Yep'),(19,1,4,'01:00:10','01:20:00','2128-06-11','CANCELADA','Yep'),(20,1,4,'01:00:10','01:20:00','2128-06-11','CANCELADA','Yep'),(21,1,4,'01:00:10','01:20:00','2128-06-11','CANCELADA','Yep'),(22,1,4,'01:00:10','01:20:00','2128-06-11','CANCELADA','Yep'),(23,1,4,'01:00:10','01:20:00','2128-06-11','CANCELADA','Yep'),(24,1,4,'01:00:10','01:20:00','2128-06-11','CANCELADA','siuu'),(25,3,2,'00:00:00','23:30:00','2024-05-19','','RESERVADA'),(26,4,2,'14:00:00','15:00:00','2024-05-19','','RESERVADA'),(27,4,2,'14:00:00','15:00:00','2024-05-19','','RESERVADA'),(28,4,2,'14:00:00','15:00:00','2024-05-19','','RESERVADA'),(29,4,2,'14:00:00','15:00:00','2024-05-19','','RESERVADA'),(30,2,4,'13:00:00','14:00:00','2024-05-19','','RESERVADA'),(31,2,4,'13:30:00','18:00:00','2024-05-20','','RESERVADA'),(32,2,4,'18:00:00','18:30:00','2024-05-20','CANCELADA','RESERVADA'),(33,2,4,'09:00:00','10:00:00','2024-05-20','RESERVADA',''),(34,2,4,'06:00:00','16:30:00','2024-06-05','','RESERVADA');
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secciones`
--

LOCK TABLES `secciones` WRITE;
/*!40000 ALTER TABLE `secciones` DISABLE KEYS */;
INSERT INTO `secciones` VALUES (1,'Chiqui','Seccion enfocadad en el desarrollo socioeducativo de niños',2),(29,'Preas','12-15 años',2),(30,'Centro','15.18 años. BAchillerato incluido.',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Dani','Admin',1,1),(2,'Dani','ADMINISTRADOR',1,2),(3,'Juan','Usuario',1,2),(4,'Juan','Usuario',1,2),(5,'Juan','Usuario',1,2),(6,'Juan','Usuario',1,2),(7,'Juan','Usuario',1,2),(8,'','ADMINISTRADOR',1,3),(9,'','ADMINISTRADOR',1,3),(10,'Ruben','ADMINISTRADOR',1,5),(11,'Pablo','ADMINISTRADOR',29,2);
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

-- Dump completed on 2024-05-21 10:46:41
