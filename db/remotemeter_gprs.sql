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
-- Table structure for table `gprs`
--

DROP TABLE IF EXISTS `gprs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gprs` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `NeighborID` int(10) NOT NULL,
  `GPRSTel` char(20) NOT NULL,
  `GPRSAddr` char(20) NOT NULL,
  `InstallAddr` char(80) DEFAULT NULL,
  `InstallTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `InstallPerson` char(20) DEFAULT NULL,
  `GPRSProtocol` int(10) NOT NULL,
  `IP` char(30) NOT NULL,
  `Port` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_GprsConcentrator` (`PID`),
  KEY `FK_GprsConcentrator_Neighbor` (`NeighborID`),
  CONSTRAINT `FK_GprsConcentrator_Neighbor` FOREIGN KEY (`NeighborID`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gprs`
--

LOCK TABLES `gprs` WRITE;
/*!40000 ALTER TABLE `gprs` DISABLE KEYS */;
INSERT INTO `gprs` VALUES (1,1,'15700000000','00aaaaaaaa','西岛','2015-06-12 00:15:52',NULL,2,'192.168.1.199',3333,'1',''),(2,1,'15700000001','ffffffffff','办公室',NULL,NULL,2,'192.168.1.199',3333,'1',''),(3,1,'15700000002','1234567890','aa','2015-06-12 00:21:55',NULL,2,'192.168.1.199',3333,'0',''),(4,1,'157','9999999999','11','2015-06-12 00:45:54',NULL,2,'11',11,'0',''),(5,1,'1','999999999','1','2015-06-12 00:47:55',NULL,2,'1',1,'0',''),(6,2,'131','15700000016','~','2015-06-14 06:10:42',NULL,1,'218.28.41.74',2222,'1','');
/*!40000 ALTER TABLE `gprs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-19 13:31:07
