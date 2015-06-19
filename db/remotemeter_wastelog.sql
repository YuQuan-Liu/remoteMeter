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
-- Table structure for table `wastelog`
--

DROP TABLE IF EXISTS `wastelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wastelog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `Neighborid` int(10) NOT NULL,
  `ReadLogID` int(10) NOT NULL,
  `Meterid` int(10) NOT NULL,
  `ActionTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `LouNum` varchar(10) DEFAULT NULL,
  `MeterRead` int(11) DEFAULT NULL,
  `SalveSum` int(11) DEFAULT NULL,
  `Waste` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` text,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_WasteLog` (`PID`),
  KEY `FK_WasteLog_Neighbor` (`Neighborid`),
  CONSTRAINT `FK_WasteLog_Neighbor` FOREIGN KEY (`Neighborid`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wastelog`
--

LOCK TABLES `wastelog` WRITE;
/*!40000 ALTER TABLE `wastelog` DISABLE KEYS */;
INSERT INTO `wastelog` VALUES (1,1,1,8,'2015-06-13 01:19:17','1',0,0,111,'1','换表'),(2,1,93,8,'2015-06-18 06:41:10','0',0,3355,-3355,'0',''),(3,1,97,8,'2015-06-18 06:46:34','0',0,3355,-3355,'0','');
/*!40000 ALTER TABLE `wastelog` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-19 13:31:09
