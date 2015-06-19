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
-- Table structure for table `meterdeductionlog`
--

DROP TABLE IF EXISTS `meterdeductionlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meterdeductionlog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `MeterID` int(10) NOT NULL,
  `MeterRead` int(10) NOT NULL,
  `MeterReadTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LastDeRead` int(10) NOT NULL,
  `LastDeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `PriceKindID` int(10) NOT NULL,
  `DeMoney` decimal(19,4) NOT NULL,
  `SettleLogID` int(10) NOT NULL,
  `SettleSingleID` int(10) DEFAULT NULL,
  `ActionTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  `paytype` char(1) DEFAULT NULL,
  `payed` char(1) DEFAULT NULL,
  `printed` char(1) DEFAULT NULL,
  `changend` int(11) DEFAULT '0' COMMENT '换表时的底数',
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_MeterDeductionLog` (`PID`),
  KEY `FK_MeterDeductionLog_Meter` (`MeterID`),
  KEY `FK_MeterDeductionLog_PriceKind` (`PriceKindID`),
  KEY `FK_MeterDeductionLog_SettleLog` (`SettleLogID`),
  CONSTRAINT `FK_MeterDeductionLog_Meter` FOREIGN KEY (`MeterID`) REFERENCES `meter` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_MeterDeductionLog_PriceKind` FOREIGN KEY (`PriceKindID`) REFERENCES `pricekind` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_MeterDeductionLog_SettleLog` FOREIGN KEY (`SettleLogID`) REFERENCES `settlelog` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meterdeductionlog`
--

LOCK TABLES `meterdeductionlog` WRITE;
/*!40000 ALTER TABLE `meterdeductionlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `meterdeductionlog` ENABLE KEYS */;
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
