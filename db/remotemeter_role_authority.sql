CREATE DATABASE  IF NOT EXISTS `remotemeter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `remotemeter`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: remotemeter
-- ------------------------------------------------------
-- Server version	5.6.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS `role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_authority` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleID` int(10) NOT NULL,
  `AuthorityID` int(10) NOT NULL,
  PRIMARY KEY (`PID`),
  KEY `FK_Role_Authority_Authority` (`AuthorityID`),
  KEY `FK_Role_Authority_Role` (`RoleID`),
  CONSTRAINT `FK_Role_Authority_Authority` FOREIGN KEY (`AuthorityID`) REFERENCES `authority` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Role_Authority_Role` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_authority`
--

LOCK TABLES `role_authority` WRITE;
/*!40000 ALTER TABLE `role_authority` DISABLE KEYS */;
INSERT INTO `role_authority` VALUES (1,1,6),(2,1,7),(3,1,8),(4,1,9),(5,1,10),(6,1,11),(7,1,12),(8,1,13),(9,1,14),(10,1,15),(11,1,16),(12,1,17),(13,1,18),(14,1,19),(15,1,20),(16,1,21),(17,1,22),(18,1,23),(19,1,24),(20,1,25),(21,1,26),(22,1,27),(23,1,28),(24,1,29),(25,1,30),(26,1,31),(27,1,32),(28,1,33),(29,2,7),(30,2,8),(31,2,9),(32,2,10),(33,2,11),(34,2,12),(35,2,13),(36,2,14),(37,2,15),(38,2,16),(39,2,17),(40,2,18),(41,2,19),(42,2,20),(43,2,21),(44,2,22),(45,2,23),(46,2,24),(47,2,25),(48,2,26),(49,2,27),(50,2,28),(51,2,29),(52,2,30),(53,2,31),(54,2,32),(55,2,33),(59,3,11),(60,3,12),(61,3,13),(62,3,14),(63,3,15),(64,3,16),(65,3,17),(69,4,11),(70,4,12),(71,4,13),(72,4,18),(73,4,19),(74,4,20),(75,4,21),(76,4,22),(77,4,23),(78,4,24);
/*!40000 ALTER TABLE `role_authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-19 13:31:08
