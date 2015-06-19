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
-- Table structure for table `useractionlog`
--

DROP TABLE IF EXISTS `useractionlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useractionlog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `AdminID` int(10) NOT NULL,
  `ActionType` int(10) NOT NULL,
  `TargerID` int(10) DEFAULT NULL,
  `ActionTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Remark` text,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_UserActionLog` (`PID`),
  KEY `FK_UserActionLog_ActionType` (`ActionType`),
  KEY `FK_UserActionLog_Admin` (`AdminID`),
  CONSTRAINT `FK_UserActionLog_ActionType` FOREIGN KEY (`ActionType`) REFERENCES `actiontype` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_UserActionLog_Admin` FOREIGN KEY (`AdminID`) REFERENCES `admininfo` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useractionlog`
--

LOCK TABLES `useractionlog` WRITE;
/*!40000 ALTER TABLE `useractionlog` DISABLE KEYS */;
INSERT INTO `useractionlog` VALUES (2,1,28,NULL,'2015-06-18 07:26:09','login:adminid1'),(3,1,28,NULL,'2015-06-18 23:36:16','login:adminid1');
/*!40000 ALTER TABLE `useractionlog` ENABLE KEYS */;
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
