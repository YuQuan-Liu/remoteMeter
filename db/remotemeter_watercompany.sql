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
-- Table structure for table `watercompany`
--

DROP TABLE IF EXISTS `watercompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `watercompany` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(80) NOT NULL,
  `CompanyAddr` varchar(80) DEFAULT NULL,
  `Mark` varchar(10) NOT NULL,
  `Authority` int(10) NOT NULL,
  `EmailHost` varchar(45) DEFAULT NULL,
  `EmailUser` varchar(45) DEFAULT NULL,
  `EmailPassword` varchar(45) DEFAULT NULL,
  `Remark` varchar(100) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL COMMENT '报表中的查询电话',
  `PayAddr` varchar(50) DEFAULT NULL COMMENT '报表中的交费地址',
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_WaterCompany` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watercompany`
--

LOCK TABLES `watercompany` WRITE;
/*!40000 ALTER TABLE `watercompany` DISABLE KEYS */;
INSERT INTO `watercompany` VALUES (1,'西岛','国家大学科技园东区7号楼8楼','3710000000',0,'smtp.163.com','avenger0422@163.com','bGw4ODAzMTlsbA==','西岛默认','53625462','国家大学科技园东区7号楼8楼'),(2,'西岛测试','大学科技园','3710000000',0,'smtp.163.com','avenger0422@163.com','ll880319ll','','53625462','国家大学科技园');
/*!40000 ALTER TABLE `watercompany` ENABLE KEYS */;
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
