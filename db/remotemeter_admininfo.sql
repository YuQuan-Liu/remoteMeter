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
-- Table structure for table `admininfo`
--

DROP TABLE IF EXISTS `admininfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admininfo` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `WCID` int(10) NOT NULL,
  `AdminName` char(20) NOT NULL,
  `LoginName` char(10) NOT NULL,
  `LoginKey` char(32) NOT NULL DEFAULT '96e79218965eb72c92a549dd5a330112',
  `AdminEmail` char(80) DEFAULT NULL,
  `AdminAddr` char(80) DEFAULT NULL,
  `AdminMobile` char(20) DEFAULT NULL,
  `AdminTel` char(20) DEFAULT NULL,
  `DepartmentID` int(10) DEFAULT NULL,
  `NoWC` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Admin` (`PID`),
  UNIQUE KEY `IX_Admin` (`LoginName`),
  KEY `FK_AdminInfo_Department` (`DepartmentID`),
  KEY `FK_Admin_WaterCompany` (`WCID`),
  CONSTRAINT `FK_AdminInfo_Department` FOREIGN KEY (`DepartmentID`) REFERENCES `department` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Admin_WaterCompany` FOREIGN KEY (`WCID`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admininfo`
--

LOCK TABLES `admininfo` WRITE;
/*!40000 ALTER TABLE `admininfo` DISABLE KEYS */;
INSERT INTO `admininfo` VALUES (1,1,'火箭','admin','96e79218965eb72c92a549dd5a330112',NULL,NULL,NULL,NULL,NULL,0,'1',NULL),(2,1,'西岛','xdkj','96e79218965eb72c92a549dd5a330112','','','','',1,0,'1',NULL),(3,1,'抄表','cb','96e79218965eb72c92a549dd5a330112','','','','',1,0,'1',NULL),(4,2,'西岛科技','xidao','96e79218965eb72c92a549dd5a330112','','','','',NULL,0,'1',NULL);
/*!40000 ALTER TABLE `admininfo` ENABLE KEYS */;
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
