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
-- Table structure for table `settlelog`
--

DROP TABLE IF EXISTS `settlelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settlelog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `ReadLogID` int(10) NOT NULL,
  `ObjectID` int(10) NOT NULL,
  `ObjectType` int(10) NOT NULL,
  `SettleStatus` int(10) NOT NULL,
  `AdminID` int(10) NOT NULL,
  `StartTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Remark` text,
  `auto` int(11) NOT NULL COMMENT '是否是阀控自动结算  1自动  0手动',
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_SettleLog` (`PID`),
  KEY `FK_SettleLog_AdminInfo` (`AdminID`),
  KEY `FK_SettleLog_ReadLog` (`ReadLogID`),
  CONSTRAINT `FK_SettleLog_AdminInfo` FOREIGN KEY (`AdminID`) REFERENCES `admininfo` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SettleLog_ReadLog` FOREIGN KEY (`ReadLogID`) REFERENCES `readlog` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settlelog`
--

LOCK TABLES `settlelog` WRITE;
/*!40000 ALTER TABLE `settlelog` DISABLE KEYS */;
INSERT INTO `settlelog` VALUES (1,93,1,1,100,1,'2015-06-18 06:41:11','',1),(2,97,1,1,100,1,'2015-06-18 06:46:34','',1);
/*!40000 ALTER TABLE `settlelog` ENABLE KEYS */;
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