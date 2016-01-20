CREATE DATABASE  IF NOT EXISTS `remotemeter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `remotemeter`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: remotemeter
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `actiontype`
--

DROP TABLE IF EXISTS `actiontype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actiontype` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `ActionName` char(20) NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_ActionType` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin_role`
--

DROP TABLE IF EXISTS `admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_role` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `AdminID` int(10) NOT NULL,
  `RoleID` int(10) NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Admin_Role` (`PID`),
  KEY `FK_Admin_Role_AdminInfo` (`AdminID`),
  KEY `FK_Admin_Role_Role` (`RoleID`),
  CONSTRAINT `FK_Admin_Role_AdminInfo` FOREIGN KEY (`AdminID`) REFERENCES `admininfo` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Admin_Role_Role` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `PPID` int(10) DEFAULT NULL COMMENT '父级，自关联',
  `AuthorityCode` char(20) NOT NULL COMMENT '权限名编码用于国际化',
  `ActUrl` varchar(255) DEFAULT NULL COMMENT '操作路径URL',
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Authority` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `perYL` int(11) NOT NULL DEFAULT '0' COMMENT '每户人口超出4人后，每增加一人超量增加水量',
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_BasicPriceKindNew` (`PID`),
  KEY `FK_BasicPriceKindNew_PriceKind` (`PriceKindID`),
  CONSTRAINT `FK_BasicPriceKindNew_PriceKind` FOREIGN KEY (`PriceKindID`) REFERENCES `pricekind` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `APID` char(20) DEFAULT NULL,
  `CustomerID` char(10) NOT NULL,
  `CustomerName` char(40) DEFAULT NULL,
  `LoginName` char(20) DEFAULT NULL,
  `LoginKey` char(32) NOT NULL DEFAULT '96e79218965eb72c92a549dd5a330112',
  `CustomerMobile` char(20) DEFAULT NULL,
  `CustomerEmail` char(80) DEFAULT NULL,
  `NationalID` char(20) DEFAULT NULL,
  `CustomerAddr` char(80) NOT NULL,
  `LouNum` char(10) NOT NULL,
  `DYNum` char(10) NOT NULL,
  `HuNum` char(10) NOT NULL,
  `NeighborID` int(10) NOT NULL,
  `HouseKindID` int(10) NOT NULL,
  `CustomerBalance` decimal(19,4) DEFAULT NULL,
  `PrePaySign` tinyint(3) unsigned NOT NULL,
  `WarnSwitch` int(10) NOT NULL,
  `WarnStyle` int(10) NOT NULL,
  `WarnThre` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  `peoplecnt` int(11) DEFAULT '4',
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Customer` (`PID`),
  KEY `FK_Customer_HouseKind` (`HouseKindID`),
  KEY `FK_Customer_Neighbor` (`NeighborID`),
  CONSTRAINT `FK_Customer_HouseKind` FOREIGN KEY (`HouseKindID`) REFERENCES `housekind` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Customer_Neighbor` FOREIGN KEY (`NeighborID`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=87042 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=38622 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `DepartmentName` char(20) NOT NULL,
  `WCID` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Department` (`PID`),
  KEY `FK_Department_WaterCompany` (`WCID`),
  CONSTRAINT `FK_Department_WaterCompany` FOREIGN KEY (`WCID`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detaildepart`
--

DROP TABLE IF EXISTS `detaildepart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detaildepart` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `DID` int(10) NOT NULL,
  `NID` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_DetailDepart` (`PID`),
  KEY `FK_DetailDepart_Department` (`DID`),
  KEY `FK_DetailDepart_Neighbor` (`NID`),
  CONSTRAINT `FK_DetailDepart_Department` FOREIGN KEY (`DID`) REFERENCES `department` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_DetailDepart_Neighbor` FOREIGN KEY (`NID`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gprs`
--

DROP TABLE IF EXISTS `gprs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gprs` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `NeighborID` int(10) NOT NULL,
  `GPRSTel` char(20) NOT NULL,
  `GPRSAddr` char(20) NOT NULL,
  `InstallAddr` char(80) DEFAULT NULL,
  `InstallTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `InstallPerson` char(20) DEFAULT NULL,
  `GPRSProtocol` int(10) NOT NULL,
  `IP` char(30) NOT NULL,
  `Port` int(10) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_GprsConcentrator` (`PID`),
  KEY `FK_GprsConcentrator_Neighbor` (`NeighborID`),
  CONSTRAINT `FK_GprsConcentrator_Neighbor` FOREIGN KEY (`NeighborID`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `housekind`
--

DROP TABLE IF EXISTS `housekind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `housekind` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `HKName` char(20) NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_HouseKind` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `import_de_cinfo`
--

DROP TABLE IF EXISTS `import_de_cinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_de_cinfo` (
  `cname` varchar(45) DEFAULT NULL,
  `cmobile` varchar(45) DEFAULT NULL,
  `user_code` varchar(45) DEFAULT NULL,
  `balance` decimal(19,4) DEFAULT NULL,
  `pre` int(11) DEFAULT NULL,
  `lou` varchar(45) DEFAULT NULL,
  `dy` varchar(45) DEFAULT NULL,
  `hu` varchar(45) DEFAULT NULL,
  `use_meter_code` int(11) DEFAULT NULL,
  `yb_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `import_de_cpay`
--

DROP TABLE IF EXISTS `import_de_cpay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_de_cpay` (
  `user_code` varchar(45) DEFAULT NULL,
  `amount` decimal(9,4) DEFAULT NULL,
  `actiontime` varchar(45) DEFAULT NULL,
  `lou` varchar(45) DEFAULT NULL,
  `dy` varchar(45) DEFAULT NULL,
  `hu` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `import_de_de`
--

DROP TABLE IF EXISTS `import_de_de`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_de_de` (
  `user_code` varchar(45) DEFAULT NULL,
  `lastderead` int(11) DEFAULT NULL,
  `lastdetime` varchar(45) DEFAULT NULL,
  `meterread` int(11) DEFAULT NULL,
  `meterreadtime` varchar(45) DEFAULT NULL,
  `price` decimal(9,4) DEFAULT NULL,
  `demoney` decimal(9,4) DEFAULT NULL,
  `actiontime` varchar(45) DEFAULT NULL,
  `pre` char(1) DEFAULT NULL,
  `lou` varchar(45) DEFAULT NULL,
  `dy` varchar(45) DEFAULT NULL,
  `hu` varchar(45) DEFAULT NULL,
  `yb_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meter`
--

DROP TABLE IF EXISTS `meter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meter` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `APID` char(20) DEFAULT NULL,
  `NeighborId` int(11) DEFAULT NULL,
  `CustomerID` int(10) NOT NULL,
  `SteelNum` char(20) NOT NULL,
  `qfh` char(20) DEFAULT NULL,
  `CollectorAddr` char(20) NOT NULL,
  `MeterAddr` char(20) NOT NULL,
  `MeterSolid` tinyint(3) unsigned NOT NULL,
  `Lihu` char(1) NOT NULL DEFAULT '',
  `MeterKindID` int(10) NOT NULL,
  `MainMeter` int(10) NOT NULL,
  `GPRSID` int(10) DEFAULT NULL,
  `PriceKindID` int(10) NOT NULL,
  `SuppleMode` int(10) NOT NULL,
  `IsValve` int(10) NOT NULL,
  `ValveState` tinyint(3) unsigned DEFAULT NULL,
  `DeductionStyle` int(10) DEFAULT NULL,
  `ValveOFFThre` int(10) DEFAULT NULL,
  `MeterState` tinyint(3) unsigned DEFAULT NULL,
  `DeRead` int(10) DEFAULT NULL,
  `DeTime` timestamp NULL DEFAULT NULL,
  `destartread` int(11) DEFAULT '0',
  `destarttime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `TimerSwitch` int(10) NOT NULL,
  `Timer` char(20) DEFAULT '',
  `Overflow` int(10) DEFAULT NULL,
  `changend` int(11) DEFAULT '0',
  `changestart` int(11) DEFAULT '0',
  `readdata` int(11) DEFAULT '0',
  `readtime` timestamp NULL DEFAULT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Meter` (`PID`),
  KEY `FK_Meter_Customer` (`CustomerID`),
  KEY `FK_Meter_GPRS` (`GPRSID`),
  KEY `FK_Meter_MeterKind` (`MeterKindID`),
  KEY `FK_Meter_PriceKind` (`PriceKindID`),
  KEY `FK_Meter_Neighbor_idx` (`NeighborId`),
  CONSTRAINT `FK_Meter_Customer` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Meter_GPRS` FOREIGN KEY (`GPRSID`) REFERENCES `gprs` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Meter_MeterKind` FOREIGN KEY (`MeterKindID`) REFERENCES `meterkind` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Meter_Neighbor` FOREIGN KEY (`NeighborId`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Meter_PriceKind` FOREIGN KEY (`PriceKindID`) REFERENCES `pricekind` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=89885 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=206893 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meterkind`
--

DROP TABLE IF EXISTS `meterkind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meterkind` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `MeterTypeName` char(20) DEFAULT NULL,
  `MeterMM` char(10) NOT NULL,
  `Remote` int(10) NOT NULL,
  `HandStyle` int(10) DEFAULT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_MeterMMKind` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `neighbor`
--

DROP TABLE IF EXISTS `neighbor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neighbor` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `WCID` int(10) NOT NULL,
  `NeighborName` char(20) NOT NULL,
  `NeighborAddr` char(80) DEFAULT NULL,
  `MainMeter` int(10) NOT NULL,
  `TimerSwitch` int(10) DEFAULT NULL,
  `Timer` char(20) DEFAULT '01,00,00',
  `IP` char(30) DEFAULT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Neighbor` (`PID`),
  KEY `FK_Neighbor_WaterCompany` (`WCID`),
  CONSTRAINT `FK_Neighbor_WaterCompany` FOREIGN KEY (`WCID`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nonremoteexport`
--

DROP TABLE IF EXISTS `nonremoteexport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nonremoteexport` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `clazzup` varchar(100) NOT NULL,
  `clazzdown1` varchar(100) NOT NULL,
  `clazzdown2` varchar(100) NOT NULL,
  `exportname` varchar(45) NOT NULL,
  `wcid` int(11) NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `pid_idx` (`wcid`),
  CONSTRAINT `FK wcid` FOREIGN KEY (`wcid`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='非远传导入导出';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pricekind`
--

DROP TABLE IF EXISTS `pricekind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pricekind` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `WCID` int(10) NOT NULL,
  `PriceKindName` char(20) NOT NULL,
  `PriceKindFine` double(24,2) NOT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_PriceKind` (`PID`),
  KEY `FK_PriceKind_WaterCompany1` (`WCID`),
  CONSTRAINT `FK_PriceKind_WaterCompany1` FOREIGN KEY (`WCID`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `propertyresp`
--

DROP TABLE IF EXISTS `propertyresp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `propertyresp` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `NeighborID` int(10) NOT NULL,
  `RespName` char(20) DEFAULT NULL,
  `RespTel` char(12) DEFAULT NULL,
  `RespMobile` char(20) DEFAULT NULL,
  `RespEmail` char(80) DEFAULT NULL,
  `RespAddr` char(80) DEFAULT NULL,
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_PropertyResp` (`PID`),
  KEY `FK_PropertyResp_Neighbor` (`NeighborID`),
  CONSTRAINT `FK_PropertyResp_Neighbor` FOREIGN KEY (`NeighborID`) REFERENCES `neighbor` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `readlog`
--

DROP TABLE IF EXISTS `readlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `readlog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `ObjectID` int(10) DEFAULT NULL,
  `ReadType` int(10) NOT NULL,
  `Remote` int(10) NOT NULL,
  `ReadObject` int(10) NOT NULL,
  `IP` char(30) NOT NULL,
  `ReadStatus` int(10) NOT NULL,
  `FailReason` text,
  `StartTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CompleteTime` timestamp NULL DEFAULT NULL,
  `AdminID` int(10) NOT NULL,
  `Settle` int(10) NOT NULL,
  `Result` text,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_ReadLog` (`PID`),
  KEY `FK_ReadLog_AdminInfo` (`AdminID`),
  CONSTRAINT `FK_ReadLog_AdminInfo` FOREIGN KEY (`AdminID`) REFERENCES `admininfo` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5751 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `readmeterlog`
--

DROP TABLE IF EXISTS `readmeterlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `readmeterlog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `MeterID` int(10) NOT NULL,
  `ActionType` tinyint(3) unsigned NOT NULL,
  `ActionTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ActionResult` int(10) NOT NULL,
  `ReadLogID` int(10) NOT NULL,
  `Remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_MeterDownLog` (`PID`),
  KEY `FK_MeterDownLog_Meter` (`MeterID`),
  KEY `FK_ReadMeterLog_ReadLog` (`ReadLogID`),
  CONSTRAINT `FK_MeterDownLog_Meter` FOREIGN KEY (`MeterID`) REFERENCES `meter` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ReadMeterLog_ReadLog` FOREIGN KEY (`ReadLogID`) REFERENCES `readlog` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1918433 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `remoteexport`
--

DROP TABLE IF EXISTS `remoteexport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remoteexport` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `clazz` varchar(100) NOT NULL COMMENT '导出时需要反射的类',
  `exportname` varchar(45) NOT NULL COMMENT '导出名称',
  `wcid` int(11) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  KEY `wcid_idx` (`wcid`),
  CONSTRAINT `wcid` FOREIGN KEY (`wcid`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='自来水公司导出格式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS `role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_authority` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleID` int(10) NOT NULL,
  `AuthorityID` int(10) NOT NULL,
  PRIMARY KEY (`PID`),
  KEY `FK_Role_Authority_Authority` (`AuthorityID`),
  KEY `FK_Role_Authority_Role` (`RoleID`),
  CONSTRAINT `FK_Role_Authority_Authority` FOREIGN KEY (`AuthorityID`) REFERENCES `authority` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Role_Authority_Role` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleName` char(20) NOT NULL,
  `WCID` int(10) DEFAULT NULL,
  `SystemRole` char(1) NOT NULL DEFAULT '',
  `Valid` char(1) NOT NULL DEFAULT '',
  `Remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_Role` (`PID`),
  KEY `FK_Roles_WaterCompany` (`WCID`),
  CONSTRAINT `FK_Roles_WaterCompany` FOREIGN KEY (`WCID`) REFERENCES `watercompany` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=7531 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `valvelog`
--

DROP TABLE IF EXISTS `valvelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valvelog` (
  `PID` int(10) NOT NULL AUTO_INCREMENT,
  `AdminID` int(10) NOT NULL,
  `ActionTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Auto` int(10) NOT NULL,
  `ActionCount` int(10) NOT NULL,
  `CompleteCount` int(10) NOT NULL,
  `ErrorCount` int(10) NOT NULL,
  `Status` int(10) NOT NULL,
  `FailReason` text,
  `Remark` text,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PK_ValveLog` (`PID`),
  KEY `FK_ValveLog_AdminInfo` (`AdminID`),
  CONSTRAINT `FK_ValveLog_AdminInfo` FOREIGN KEY (`AdminID`) REFERENCES `admininfo` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=8618 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'remotemeter'
--

--
-- Dumping routines for database 'remotemeter'
--
/*!50003 DROP PROCEDURE IF EXISTS `addbreakdowns` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addbreakdowns`(readlogid_ int,gprsid_ int,coladdr_ varchar(20),meteraddr_ varchar(20))
BEGIN

	
	insert into ReadMeterLog 
	(MeterId,ActionType,ActionResult,ReadLogid,remark) 
	select pid,4,-1,readlogid_,'' from Meter 
	where gprsid = gprsid_ and CollectorAddr = coladdr_ and MeterAddr = meteraddr_ and valid = '1'; 
    
    update Meter 
	set meterstate = 4,valvestate = 1,readtime = now() 
	where gprsid = gprsid_ and CollectorAddr = coladdr_ and MeterAddr = meteraddr_ and valid = '1'; 
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addreadmeterlog` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addreadmeterlog`(mid int,actiontype int,meterread int,valvestate int,readlogid int,remark varchar(500))
BEGIN

	insert into ReadMeterLog 
	(MeterId,ActionType,ActionResult,ReadLogid,remark) 
	values(mid,actiontype,meterread,readlogid,remark);
    
    if(meterread = -1)then 
		update Meter 
        set meterstate = actiontype,valvestate = valvestate,readtime = now() 
		where pid = mid;
	else
		update Meter 
        set meterstate = actiontype,readdata = meterread,valvestate = valvestate,readtime = now() 
		where pid = mid;
    end if;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addreadmeterlogs` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addreadmeterlogs`(readlogid_ int,gprsid_ int,coladdr_ varchar(20),meteraddr_ varchar(20),actiontype_ int,meterread_ int,valvestate_ int,remark_ varchar(500))
BEGIN

	insert into ReadMeterLog 
	(MeterId,ActionType,ActionResult,ReadLogid,remark) 
    select pid,actiontype_,meterread_,readlogid_,remark_ from Meter 
	where gprsid = gprsid_ and CollectorAddr = coladdr_ and MeterAddr = meteraddr_ and valid = '1'; 
    
    if(meterread_ = -1)then 
		update Meter 
        set meterstate = actiontype_,valvestate = valvestate_,readtime = now() 
		where gprsid = gprsid_ and CollectorAddr = coladdr_ and MeterAddr = meteraddr_ and valid = '1'; 
	else
		update Meter 
        set meterstate = actiontype_,readdata = meterread_,valvestate = valvestate_,readtime = now() 
		where gprsid = gprsid_ and CollectorAddr = coladdr_ and MeterAddr = meteraddr_ and valid = '1'; 
    end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addreadmeterlogsnational` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addreadmeterlogsnational`(readlogid_ int,gprsid_ int,meteraddr_ varchar(20),actiontype_ int,meterread_ int,valvestate_ int,remark_ varchar(500))
BEGIN

	insert into ReadMeterLog 
	(MeterId,ActionType,ActionResult,ReadLogid,remark) 
    select pid,actiontype_,meterread_,readlogid_,remark_ from Meter 
	where gprsid = gprsid_ and MeterAddr = meteraddr_ and valid = '1'; 
    
    if(meterread_ = -1)then 
		update Meter 
        set meterstate = actiontype_,valvestate = valvestate_,readtime = now() 
		where gprsid = gprsid_ and MeterAddr = meteraddr_ and valid = '1'; 
	else
		update Meter 
        set meterstate = actiontype_,readdata = meterread_,valvestate = valvestate_,readtime = now() 
		where gprsid = gprsid_ and MeterAddr = meteraddr_ and valid = '1'; 
    end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `adjustmeter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `adjustmeter`(cid_ int,mid_ int,old_cid int)
BEGIN
	declare demoney_ DECIMAL(19,4);
    
    set demoney_ = 0;
    
    select sum(demoney) into demoney_ from meterdeductionlog
	where meterid = mid_ and valid = 1;
    
    update customer
    set customerbalance = customerbalance + demoney_
    where pid = old_cid;
    
    update meter
    set customerid = cid_
    where pid = mid_;
    
    update customer
    set customerbalance = customerbalance - demoney_
    where pid = cid_;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `calculate_hu` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `calculate_hu`(mid_ int,adid_ int,settlelogid_ int)
BEGIN
	declare cid int;
    declare balance DECIMAL(19,4);
    declare pre int;
    declare mid int;
    declare pkid int;
    declare deread int;
    declare detime timestamp;
    declare read_ int;
    declare read_time timestamp;
    declare readtype int;
    declare changeend int;
    declare circle varchar(20) default '';
    
    declare fetch_ok boolean;
    
    declare this_yl int;
	declare year_yl int;
	declare demoney DECIMAL(19,4);
    declare mainmeter_ int;
	declare destartread int;
	declare destarttime timestamp;
    declare peoplecnt int;
    
    declare cm_cursor cursor for 
    select c.pid,c.CustomerBalance,c.PrePaySign,m.pid,m.PriceKindID,m.DeRead,m.DeTime,m.readdata,m.readtime,m.changend,m.mainmeter,m.destartread,m.destarttime,c.peoplecnt from meter m 
    join customer c
    on m.customerid = c.pid
    where m.pid = mid_;
    
    declare continue handler for NOT FOUND set fetch_ok = false;
    
    set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,this_yl = 0,demoney = 0,changeend = 0,mainmeter_ = 0,destartread=0,destarttime=now(),peoplecnt=4;
    set fetch_ok = true;
    
    open cm_cursor;
    fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_,destartread,destarttime,peoplecnt;
    while fetch_ok=true do
    
        set this_yl = 0,year_yl=0,demoney = 0;
        
        if(mainmeter_ = 0) then
			begin 
			
			##单独结算不更新destartread destarttime 即和destarttime 在同一年
			
            #select cid,balance,pre,mid,pkid,deread,detime,read_,read_time;
			if(changeend > 0) then
				# the meter changed
				set this_yl = changeend - deread + read_;
				set year_yl = this_yl;##如果换表了  在处理完之后将destartread=0;
				set circle = '';
				
				##换表了将 将destartread = 0  好处理
				update meter 
				set destartread = 0
				where pid = mid;
				
			elseif (deread > 9000) and (read_ < 5000) then
				set this_yl = read_+(10000-deread);
				set year_yl = read_+(10000-destartread);
				set circle = '套圈';
			else
				set this_yl = read_-deread;
				set year_yl = read_-destartread;
				set circle = '';
			end if;
			
			#计算扣费
			if(this_yl >= 0 and this_yl < 9000) then
			begin
				declare first_ decimal(19,4);
				declare first_over int;
				declare second_ decimal(19,4);
				declare second_over int;
				declare third decimal(19,4);
				declare perYL int;
				declare bp_cnt int;
				
				set first_ = 0,first_over = 0,second_ = 0,second_over = 0,third = 0,perYL=0,bp_cnt=0;
                
                select count(*) into bp_cnt from BasicPrice
                where pricekindid = pkid and valid = 1;
                if(bp_cnt > 1)then
					select sum(BasicPriceFirst),sum(BasicFirstOver),sum(BasicPriceSecond),sum(BasicSecondOver),sum(BasicPriceThird),sum(perYL) into first_,first_over,second_,second_over,third,perYL from BasicPrice
					where pricekindid = pkid and valid = 1 limit 1;
                    set first_over = first_over / bp_cnt;
                    set second_over = second_over / bp_cnt;
                    set perYL = perYL / bp_cnt;
                else
					select BasicPriceFirst,BasicFirstOver,BasicPriceSecond,BasicSecondOver,BasicPriceThird,perYL into first_,first_over,second_,second_over,third,perYL from BasicPrice
					where pricekindid = pkid and valid = 1 limit 1;
                end if;
				
				##如果户人数>4人  更新阶梯超量
				if(peoplecnt > 4)then
					if(first_over > 0)then
						set first_over = first_over +perYL*(peoplecnt-4);
					end if;
					if(second_over > 0)then
						set second_over = second_over +perYL*(peoplecnt-4);
					end if;
				end if;
				if(first_over = 0)then
					##不分阶
					set demoney = demoney + this_yl*first_;
				elseif (first_over > 0 and second_over = 0)then
					##只分2阶
					if(year_yl > first_over)then
						##超了一阶
						if((deread-destartread) > first_over)then
							##上次的就超了
							set demoney = demoney + this_yl*second_;
						else
							##本次超的
							set demoney = demoney + (first_over-(deread-destartread))*first_+second_*(year_yl-first_over);
						end if;
					else
						##没超一阶
						set demoney = demoney + this_yl*first_;
					end if;
				elseif(first_over > 0 and second_over > 0)then
					##分3阶
					if(year_yl > second_over)then
						##超了二阶
						if((deread-destartread) > second_over)then
							##上次的就超了
							set demoney = demoney + this_yl*third;
						else
							##本次超的二阶  需要查看本次是否也直接超了一阶
							if((deread-destartread) > first_over)then
								##本次超的二阶 并且上次超了一阶
								set demoney = demoney + (second_over-(deread-destartread))*second_+third*(year_yl-second_over);
							else
								##本次超的二阶 并且同时超了一阶
								set demoney = demoney + (first_over-(deread-destartread))*first_ +(second_over-first_over)*second_+third*(year_yl-second_over);
							end if;
						end if;
					elseif (year_yl > first_over)then
						##超了一阶
						if((deread-destartread) > first_over)then
							##上次的就超了
							set demoney = demoney + this_yl*second_;
						else
							##本次超的
							set demoney = demoney + (first_over-(deread-destartread))*first_+second_*(year_yl-first_over);
						end if;
					else
						##没超一阶
						set demoney = demoney + this_yl*first_;
					end if;
				end if;
			end;
			end if;
			
			#更新数据库
			
			start transaction ;
			if(this_yl > 0 and this_yl < 9000)then
				update customer 
				set customerbalance = customerbalance - demoney
				where pid = cid;
				update meter
				set deread = read_,detime = read_time,changend = 0
				where pid = mid;
			end if;
			
			#扣的钱为0  往扣费记录里面插入时 本次读数和上次读数相同  防止出现9999的情况
			if(demoney = 0)then 
				set read_ = deread;
			end if;
			
			insert into meterdeductionlog
			(meterid,meterread,meterreadtime,lastderead,lastdetime,pricekindid,demoney,settlelogid,settlesingleid,actiontime,paytype,payed,printed,changend,remark,valid)
			values(mid,read_,read_time,deread,detime,pkid,demoney,settlelogid_,0,now(),pre,0,0,changeend,circle,1);
			
			commit;
			
			end;
        end if;
        
		
        set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,changeend = 0,mainmeter_=0,destartread=0,destarttime=now(),peoplecnt=4;
        fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_,destartread,destarttime,peoplecnt;
    end while;
	close cm_cursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `calculate_neighbor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `calculate_neighbor`(nid_ int,adid_ int,readlogid_ int)
BEGIN
	declare cid int;
    declare balance DECIMAL(19,4);
    declare pre int;
    declare mid int;
    declare pkid int;
    declare deread int;
    declare detime timestamp;
    declare read_ int;
    declare read_time timestamp;
    declare readtype int;
    declare changeend int;
    declare circle varchar(20) default '';
    
    declare fetch_ok boolean;
    declare settlelogadd boolean;
    declare settlelogid int;
    
    declare this_yl int;
	declare year_yl int;
	declare demoney DECIMAL(19,4);
    declare mainmeter_ int;
	declare destartread int;
	declare destarttime timestamp;
    declare peoplecnt int;
    
    declare cm_cursor cursor for 
    select c.pid,c.CustomerBalance,c.PrePaySign,m.pid,m.PriceKindID,m.DeRead,m.DeTime,m.readdata,m.readtime,m.changend,m.mainmeter,m.destartread,m.destarttime,c.peoplecnt from meter m 
    join customer c
    on m.customerid = c.pid
    where c.neighborid = nid_ and c.valid = 1 and m.valid = 1 and deductionstyle = 0 ;
    
    declare continue handler for NOT FOUND set fetch_ok = false;
    
    set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,settlelogid = 0,this_yl = 0,year_yl=0,demoney = 0,changeend = 0,mainmeter_ = 0,destartread=0,destarttime=now(),peoplecnt=4;
    set fetch_ok = true;
    set settlelogadd = false;
    
    open cm_cursor;
    fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_,destartread,destarttime,peoplecnt;
    while fetch_ok=true do
		if(settlelogadd = false) then
			insert into settlelog
            (readlogid,objectid,objecttype,settlestatus,adminid,starttime,auto,remark)
            values(readlogid_,nid_,1,100,adid_,now(),0,'');
			
            select max(pid) into settlelogid from settlelog
            where readlogid = readlogid_ and objectid = nid_ and adminid = adid_;
            
            set settlelogadd = true;
            
            update readlog
            set settle = 1
            where pid = readlogid_;
            
        end if;
        
        set this_yl = 0,year_yl=0,demoney = 0;
        
        if(mainmeter_ = 0) then
			begin 
			
			if(year(now()) = year(destarttime)+1) then 
				##今年第一次结算  
                ##将今年第一次抄表信息  更新为今年阶梯起始
				update meter 
				set destartread = read_,destarttime=read_time
				where pid = mid;
			end if;
			
            #select cid,balance,pre,mid,pkid,deread,detime,read_,read_time;
			if(changeend > 0) then
				# the meter changed
				set this_yl = changeend - deread + read_;
				set year_yl = this_yl;##如果换表了  在处理完之后将destartread=0;
				set circle = '';
				
				##换表了将 将destartread = 0  好处理
				update meter 
				set destartread = 0
				where pid = mid;
				
			elseif (deread > 9000) and (read_ < 5000) then
				set this_yl = read_+(10000-deread);
				set year_yl = read_+(10000-destartread);
				set circle = '套圈';
			else
				set this_yl = read_-deread;
				set year_yl = read_-destartread;
				set circle = '';
			end if;
			
			#计算扣费
			if(this_yl >= 0 and this_yl < 9000) then
			begin
				declare first_ decimal(19,4);
				declare first_over int;
				declare second_ decimal(19,4);
				declare second_over int;
				declare third decimal(19,4);
				declare perYL int;
				declare bp_cnt int;
				
				set first_ = 0,first_over = 0,second_ = 0,second_over = 0,third = 0,perYL=0,bp_cnt=0;
                
                select count(*) into bp_cnt from BasicPrice
                where pricekindid = pkid and valid = 1;
                if(bp_cnt > 1)then
					select sum(BasicPriceFirst),sum(BasicFirstOver),sum(BasicPriceSecond),sum(BasicSecondOver),sum(BasicPriceThird),sum(perYL) into first_,first_over,second_,second_over,third,perYL from BasicPrice
					where pricekindid = pkid and valid = 1 limit 1;
                    set first_over = first_over / bp_cnt;
                    set second_over = second_over / bp_cnt;
                    set perYL = perYL / bp_cnt;
                else
					select BasicPriceFirst,BasicFirstOver,BasicPriceSecond,BasicSecondOver,BasicPriceThird,perYL into first_,first_over,second_,second_over,third,perYL from BasicPrice
					where pricekindid = pkid and valid = 1 limit 1;
                end if;
				
				##如果户人数>4人  更新阶梯超量
				if(peoplecnt > 4)then
					if(first_over > 0)then
						set first_over = first_over +perYL*(peoplecnt-4);
					end if;
					if(second_over > 0)then
						set second_over = second_over +perYL*(peoplecnt-4);
					end if;
				end if;
				
				if(first_over = 0)then
					##不分阶
					set demoney = demoney + this_yl*first_;
				elseif (first_over > 0 and second_over = 0)then
					##只分2阶
					if(year_yl > first_over)then
						##超了一阶
						if((deread-destartread) > first_over)then
							##上次的就超了
							set demoney = demoney + this_yl*second_;
						else
							##本次超的
							set demoney = demoney + (first_over-(deread-destartread))*first_+second_*(year_yl-first_over);
						end if;
					else
						##没超一阶
						set demoney = demoney + this_yl*first_;
					end if;
				elseif(first_over > 0 and second_over > 0)then
					##分3阶
					if(year_yl > second_over)then
						##超了二阶
						if((deread-destartread) > second_over)then
							##上次的就超了
							set demoney = demoney + this_yl*third;
						else
							##本次超的二阶  需要查看本次是否也直接超了一阶
							if((deread-destartread) > first_over)then
								##本次超的二阶 并且上次超了一阶
								set demoney = demoney + (second_over-(deread-destartread))*second_+third*(year_yl-second_over);
							else
								##本次超的二阶 并且同时超了一阶
								set demoney = demoney + (first_over-(deread-destartread))*first_ +(second_over-first_over)*second_+third*(year_yl-second_over);
							end if;
						end if;
					elseif (year_yl > first_over)then
						##超了一阶
						if((deread-destartread) > first_over)then
							##上次的就超了
							set demoney = demoney + this_yl*second_;
						else
							##本次超的
							set demoney = demoney + (first_over-(deread-destartread))*first_+second_*(year_yl-first_over);
						end if;
					else
						##没超一阶
						set demoney = demoney + this_yl*first_;
					end if;
				end if;
			end;
			end if;
			
			#更新数据库
			
			start transaction ;
			if(this_yl > 0 and this_yl < 9000)then
				update customer 
				set customerbalance = customerbalance - demoney
				where pid = cid;
				update meter
				set deread = read_,detime = read_time,changend = 0
				where pid = mid;
			end if;
			
			#扣的钱为0  往扣费记录里面插入时 本次读数和上次读数相同  防止出现9999的情况
			if(demoney = 0)then 
				set read_ = deread;
			end if;
			
			insert into meterdeductionlog
			(meterid,meterread,meterreadtime,lastderead,lastdetime,pricekindid,demoney,settlelogid,settlesingleid,actiontime,paytype,payed,printed,changend,remark,valid)
			values(mid,read_,read_time,deread,detime,pkid,demoney,settlelogid,0,now(),pre,0,0,changeend,circle,1);
			
			commit;
			
			end;
        end if;
		
        set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,changeend = 0,mainmeter_=0,destartread=0,destarttime=now(),peoplecnt=4;
        fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_,destartread,destarttime,peoplecnt;
    end while;
	close cm_cursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `postpay` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `postpay`(adid_ int,mdlid_ int)
BEGIN
	declare cid int;
    declare demoney decimal(19,4);
    declare pre int;
    declare payed int;
	select c.pid,mdl.demoney,c.prepaysign,mdl.payed into cid,demoney,pre,payed from customer c
    join meter m 
    on c.pid = m.customerid 
    join meterdeductionlog mdl
    on m.pid = mdl.meterid
    where mdl.pid = mdlid_;
    
    if(payed = 0)then
		update meterdeductionlog
		set payed = 1
		where pid = mdlid_;
		update customer
		set customerbalance = customerbalance + demoney
		where pid = cid;
		insert into customerpaylog
		(adminid,customerid,amount,actiontime,prepaysign,valid,remark)
		values(adid_,cid,demoney,now(),pre,1,'');
    end if;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-20 17:08:53
