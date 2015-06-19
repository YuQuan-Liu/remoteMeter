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
-- Table structure for table `valveconflog`
--

DROP TABLE IF EXISTS `valveconflog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valveconflog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `MeterID` int(10) DEFAULT NULL,
  `Switch` int(10) NOT NULL,
  `Result` int(10) NOT NULL,
  `ErrorReason` text,
  `ErrorStatus` int(10) DEFAULT NULL,
  `RemoveReason` text,
  `CompleteTime` timestamp NULL DEFAULT NULL,
  `ValveLogID` int(10) NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_ValveConfLog` (`PID`),
  KEY `FK_ValveConfLog_Meter` (`MeterID`),
  KEY `FK_ValveConfLog_ValveLog` (`ValveLogID`),
  CONSTRAINT `FK_ValveConfLog_Meter` FOREIGN KEY (`MeterID`) REFERENCES `meter` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ValveConfLog_ValveLog` FOREIGN KEY (`ValveLogID`) REFERENCES `valvelog` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valveconflog`
--

LOCK TABLES `valveconflog` WRITE;
/*!40000 ALTER TABLE `valveconflog` DISABLE KEYS */;
INSERT INTO `valveconflog` VALUES (1,2,0,1,'',0,'','2015-06-14 07:34:06',1),(2,2,1,1,'',0,'','2015-06-15 06:05:16',2),(3,2,0,1,'',0,'','2015-06-16 01:53:18',3),(4,8,0,2,'For input string: \"mm\"',0,'','2015-06-18 06:41:11',4),(5,8,0,2,'For input string: \"mm\"',0,'','2015-06-18 06:46:35',5);
/*!40000 ALTER TABLE `valveconflog` ENABLE KEYS */;
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
