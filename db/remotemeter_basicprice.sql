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
-- Table structure for table `basicprice`
--

DROP TABLE IF EXISTS `basicprice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `basicprice` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `BasicPriceName` char(20) NOT NULL,
  `PriceKindID` int(10) NOT NULL,
  `BasicPriceFirst` decimal(19,4) NOT NULL,
  `BasicFirstOver` int(10) NOT NULL,
  `BasicPriceSecond` decimal(19,4) NOT NULL,
  `BasicSecondOver` int(10) NOT NULL,
  `BasicPriceThird` decimal(19,4) NOT NULL,
  `Valid` char(5) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_BasicPriceKindNew` (`PID`),
  KEY `FK_BasicPriceKindNew_PriceKind` (`PriceKindID`),
  CONSTRAINT `FK_BasicPriceKindNew_PriceKind` FOREIGN KEY (`PriceKindID`) REFERENCES `pricekind` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `basicprice`
--

LOCK TABLES `basicprice` WRITE;
/*!40000 ALTER TABLE `basicprice` DISABLE KEYS */;
INSERT INTO `basicprice` VALUES (1,'基本单价',1,2.9000,0,0.0000,0,0.0000,'1',NULL);
/*!40000 ALTER TABLE `basicprice` ENABLE KEYS */;
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
