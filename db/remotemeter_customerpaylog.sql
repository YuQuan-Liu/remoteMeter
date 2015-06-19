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
-- Table structure for table `customerpaylog`
--

DROP TABLE IF EXISTS `customerpaylog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerpaylog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `AdminID` int(10) NOT NULL,
  `CustomerID` int(10) NOT NULL,
  `Amount` decimal(19,4) NOT NULL,
  `ActionTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PrePaySign` tinyint(3) unsigned NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_CustomerPayLog` (`PID`),
  KEY `FK_CustomerPayLog_Admin` (`AdminID`),
  KEY `FK_CustomerPayLog_Customer` (`CustomerID`),
  CONSTRAINT `FK_CustomerPayLog_Admin` FOREIGN KEY (`AdminID`) REFERENCES `admininfo` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CustomerPayLog_Customer` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerpaylog`
--

LOCK TABLES `customerpaylog` WRITE;
/*!40000 ALTER TABLE `customerpaylog` DISABLE KEYS */;
INSERT INTO `customerpaylog` VALUES (1,1,441,100.0000,'2015-06-18 01:42:13',1,'1',NULL),(2,1,442,100.0000,'2015-06-18 01:42:47',1,'1',NULL),(3,1,443,50.0000,'2015-06-18 01:44:34',1,'1',NULL);
/*!40000 ALTER TABLE `customerpaylog` ENABLE KEYS */;
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
