-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gestion_empleados
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `adm_codigo` int NOT NULL AUTO_INCREMENT,
  `adm_contrasenia` varchar(255) NOT NULL,
  `adm_nombre_completo` varchar(100) NOT NULL,
  `adm_nombre_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`adm_codigo`),
  UNIQUE KEY `UK5anayewku9qm2l7386lqghtu9` (`adm_nombre_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (1,'$2a$10$gCCmUwoKrzHNKzmsHT60MO7YJmpEC5jEyY16MGJhAfSlmYgfeagsG','asdasdasd','asdasdasd');
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `area_id` int NOT NULL AUTO_INCREMENT,
  `area_nombre` varchar(50) NOT NULL,
  `area_salario_base` decimal(8,2) NOT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'Administración',1200.00),(2,'Recursos Humanos',1400.00),(3,'Contabilidad',1600.00),(4,'Marketing',1800.00),(5,'Tecnología',2000.00),(6,'Logística',2200.00),(7,'Ventas',2400.00),(8,'Atención al Cliente',2600.00);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banco`
--

DROP TABLE IF EXISTS `banco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banco` (
  `banco_id` int NOT NULL AUTO_INCREMENT,
  `banco_nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`banco_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banco`
--

LOCK TABLES `banco` WRITE;
/*!40000 ALTER TABLE `banco` DISABLE KEYS */;
INSERT INTO `banco` VALUES (1,'Banco de la Nación'),(2,'BBVA'),(3,'Banco de Crédito del Perú'),(4,'Scotiabank'),(5,'Interbank'),(6,'MiBanco'),(7,'Banco Pichincha'),(8,'Viva Red'),(9,'BanBif'),(10,'Ripley Bank');
/*!40000 ALTER TABLE `banco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contrato`
--

DROP TABLE IF EXISTS `contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contrato` (
  `contrato_id` int NOT NULL AUTO_INCREMENT,
  `contrato_detalle` tinytext NOT NULL,
  `contrato_estado` enum('C','P','V') NOT NULL,
  `contrato_fecha_fin` date DEFAULT NULL,
  `contrato_fecha_inicio` date NOT NULL,
  `contrato_jornada` enum('TC','TP') NOT NULL,
  `contrato_modalidad` enum('FA','IN','OS','PF','SU') NOT NULL,
  `contrato_area_id` int NOT NULL,
  `contrato_empleado_id` varchar(8) NOT NULL,
  PRIMARY KEY (`contrato_id`),
  KEY `FKm6usc1hkh17t9ipopomihqps8` (`contrato_area_id`),
  KEY `FK608u7qj2mdmnqb20gm9pmjgc` (`contrato_empleado_id`),
  CONSTRAINT `FK608u7qj2mdmnqb20gm9pmjgc` FOREIGN KEY (`contrato_empleado_id`) REFERENCES `empleado` (`emp_codigo`),
  CONSTRAINT `FKm6usc1hkh17t9ipopomihqps8` FOREIGN KEY (`contrato_area_id`) REFERENCES `area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrato`
--

LOCK TABLES `contrato` WRITE;
/*!40000 ALTER TABLE `contrato` DISABLE KEYS */;
INSERT INTO `contrato` VALUES (1,'zxczxczxczxc','C','2025-04-08','2025-04-06','TC','OS',4,'SKP25N26'),(2,'asdasdsad','V','2025-09-06','2025-05-06','TC','PF',6,'SKP25N26'),(3,'zxczxczxczxc','C','2025-04-08','2025-04-06','TC','OS',4,'SKP25N26'),(4,'zxczxczxczxc','C','2025-04-08','2025-04-06','TC','OS',4,'SKP25N26');
/*!40000 ALTER TABLE `contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `emp_codigo` varchar(8) NOT NULL,
  `emp_apellido_materno` varchar(50) NOT NULL,
  `emp_apellido_paterno` varchar(50) NOT NULL,
  `emp_dni` varchar(8) NOT NULL,
  `emp_estado` enum('A','I') NOT NULL,
  `emp_estado_civil` enum('C','D','S','V') NOT NULL,
  `emp_fecha_nacimiento` date NOT NULL,
  `emp_fecha_registro` date DEFAULT NULL,
  `emp_foto` varchar(255) NOT NULL,
  `emp_genero` enum('F','M') NOT NULL,
  `emp_nombres` varchar(100) NOT NULL,
  `emp_banco_id` int NOT NULL,
  PRIMARY KEY (`emp_codigo`),
  UNIQUE KEY `UKdq2pv4uen1nim9r9rhi06m8w` (`emp_dni`),
  KEY `FK9sx83oy0uqv12eyefi8wy26lr` (`emp_banco_id`),
  CONSTRAINT `FK9sx83oy0uqv12eyefi8wy26lr` FOREIGN KEY (`emp_banco_id`) REFERENCES `banco` (`banco_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES ('1T1DSBWD','Vazquez','Morales','73256786','I','V','2005-09-22','2025-05-06','hombre-traje-sonriendo-camara_921026-15204.jpg','M','Diego',1),('FNGGHF6O','Sanchez','Paredes','87654345','I','V','2003-05-02','2025-05-06','OIP (1).jpeg','M','Andres',2),('GP6L30PX','Sanchez','Alberto','63451672','I','C','1998-05-07','2025-05-06','istockphoto-499537283-612x612.jpg','M','Luis',4),('SKP25N26','Medina','Carlos','31312312','I','C','2025-04-30','2025-05-06','BPC8_14_23-43028-Edit-2.jpg','M','Juan',9),('WQZYTLT7','Torres','Lucia','42342342','I','D','2001-04-05','2025-05-06','happy-young-woman-teacher_917664-33305.jpg','F','Ana',9),('Z431JSOP','Lopez','Fernanda','65656565','I','D','2025-05-05','2025-05-06','OIP (3).jpeg','M','Maria',9);
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-06 20:57:52
