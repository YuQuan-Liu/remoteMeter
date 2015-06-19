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
-- Table structure for table `warnlog`
--

DROP TABLE IF EXISTS `warnlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warnlog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `CustomerID` int(10) NOT NULL,
  `WarnStyle` int(10) NOT NULL,
  `Mobile` char(11) DEFAULT NULL,
  `Email` char(80) DEFAULT NULL,
  `WarnContent` varchar(200) DEFAULT NULL,
  `WarnReason` varchar(200) DEFAULT NULL,
  `ActionTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `WarnCount` int(10) NOT NULL,
  `SuccessCount` int(10) DEFAULT NULL,
  `FailCount` int(10) DEFAULT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Warn` int(10) NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_WarnLog` (`PID`),
  KEY `FK_WarnLog_Customer` (`CustomerID`),
  CONSTRAINT `FK_WarnLog_Customer` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warnlog`
--

LOCK TABLES `warnlog` WRITE;
/*!40000 ALTER TABLE `warnlog` DISABLE KEYS */;
INSERT INTO `warnlog` VALUES (1,15,1,'aa','aa','aa~0.0000','','2015-06-18 06:41:12',1,0,1,'1',0),(2,15,1,'aa','aa','aa~0.0000','','2015-06-18 06:46:34',1,0,1,'1',0);
/*!40000 ALTER TABLE `warnlog` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-19 13:31:10
