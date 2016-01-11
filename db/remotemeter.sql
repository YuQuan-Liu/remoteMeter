CREATE DATABASE  IF NOT EXISTS `remotemeter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `remotemeter`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: remotemeter
-- ------------------------------------------------------
-- Server version	5.6.25-log

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
-- Dumping data for table `actiontype`
--

LOCK TABLES `actiontype` WRITE;
/*!40000 ALTER TABLE `actiontype` DISABLE KEYS */;
INSERT INTO `actiontype` VALUES (1,'添加管理员'),(2,'删除管理员'),(3,'更改角色'),(4,'更改片区'),(5,'片区添加'),(6,'片区删除'),(7,'单价添加'),(8,'单价删除'),(9,'调整单价'),(10,'小区添加'),(11,'小区删除'),(12,'集中器添加'),(13,'集中器删除'),(14,'用户添加'),(15,'用户删除'),(16,'表添加'),(17,'表删除'),(18,'用户表批量添加'),(19,'开阀关阀  -x'),(20,'人工修改  -x'),(21,'Excel上传-非远传'),(22,'预后付费转换'),(23,'更新单价'),(24,'水费减免'),(25,'撤销交费'),(26,'撤销扣费'),(27,'换表'),(28,'login'),(29,'logout'),(30,'调整表具');
/*!40000 ALTER TABLE `actiontype` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role`
--

LOCK TABLES `admin_role` WRITE;
/*!40000 ALTER TABLE `admin_role` DISABLE KEYS */;
INSERT INTO `admin_role` VALUES (1,1,1);
/*!40000 ALTER TABLE `admin_role` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admininfo`
--

LOCK TABLES `admininfo` WRITE;
/*!40000 ALTER TABLE `admininfo` DISABLE KEYS */;
INSERT INTO `admininfo` VALUES (1,1,'火箭','sa','96e79218965eb72c92a549dd5a330112','','','','',NULL,0,'1','');
/*!40000 ALTER TABLE `admininfo` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (0,NULL,'11','','0','系统内置不可删除'),(1,0,'admin','','1','管理员'),(2,0,'info','','1','信息录入'),(3,0,'read','','1','抄表'),(4,0,'charge','','1','收费'),(5,0,'statis','','1','统计'),(6,1,'watcom','','1','自来水公司'),(7,1,'role','','1','角色'),(8,1,'areas','','1','片区'),(9,1,'adminor','','1','管理员'),(10,1,'basicprice','','1','单价'),(11,2,'infoin','','1','信息录入'),(12,2,'community','','1','小区信息'),(13,2,'userinfo','','1','用户信息'),(14,3,'readview','','1','抄表界面'),(15,3,'readmeter','','1','抄表'),(16,3,'unremote','','1','非远程录入'),(17,3,'readvalve','','1','阀控'),(18,4,'chargeview','','1','收费界面'),(19,4,'undo','','1','撤销'),(20,4,'charge','','1','收费'),(21,4,'settle','','1','结算'),(22,4,'postpay','','1','后付费'),(23,4,'closevalve','','1','关阀控水'),(24,4,'vavlelog','','1','阀控预付费'),(25,5,'statisticview','','1','统计界面'),(26,5,'chargestat','','1','收费统计'),(27,5,'settlelogstat','','1','扣费统计'),(28,5,'loustat','','1','楼宇统计'),(29,5,'settlelogwaterstat','','1','结算用水统计'),(30,5,'vipstat','','1','重点用户监测'),(31,5,'wastestat','','1','水损分析'),(32,5,'chargeratestat','','1','收费率统计'),(33,5,'owestat','','1','欠费统计');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `basicprice`
--

LOCK TABLES `basicprice` WRITE;
/*!40000 ALTER TABLE `basicprice` DISABLE KEYS */;
INSERT INTO `basicprice` VALUES (1,'基本单价',1,0.0000,0,0.0000,0,0.0000,'1','无单价',0);
/*!40000 ALTER TABLE `basicprice` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=84867 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=37847 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerpaylog`
--

LOCK TABLES `customerpaylog` WRITE;
/*!40000 ALTER TABLE `customerpaylog` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerpaylog` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detaildepart`
--

LOCK TABLES `detaildepart` WRITE;
/*!40000 ALTER TABLE `detaildepart` DISABLE KEYS */;
/*!40000 ALTER TABLE `detaildepart` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gprs`
--

LOCK TABLES `gprs` WRITE;
/*!40000 ALTER TABLE `gprs` DISABLE KEYS */;
/*!40000 ALTER TABLE `gprs` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `housekind`
--

LOCK TABLES `housekind` WRITE;
/*!40000 ALTER TABLE `housekind` DISABLE KEYS */;
INSERT INTO `housekind` VALUES (1,'多层'),(2,'高层'),(3,'商业'),(4,'工业');
/*!40000 ALTER TABLE `housekind` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `import_de_cinfo`
--

LOCK TABLES `import_de_cinfo` WRITE;
/*!40000 ALTER TABLE `import_de_cinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_de_cinfo` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `import_de_cpay`
--

LOCK TABLES `import_de_cpay` WRITE;
/*!40000 ALTER TABLE `import_de_cpay` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_de_cpay` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `import_de_de`
--

LOCK TABLES `import_de_de` WRITE;
/*!40000 ALTER TABLE `import_de_de` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_de_de` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=86629 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meter`
--

LOCK TABLES `meter` WRITE;
/*!40000 ALTER TABLE `meter` DISABLE KEYS */;
/*!40000 ALTER TABLE `meter` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=182063 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meterdeductionlog`
--

LOCK TABLES `meterdeductionlog` WRITE;
/*!40000 ALTER TABLE `meterdeductionlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `meterdeductionlog` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meterkind`
--

LOCK TABLES `meterkind` WRITE;
/*!40000 ALTER TABLE `meterkind` DISABLE KEYS */;
INSERT INTO `meterkind` VALUES (1,'15平远传','15',1,0,'1',NULL),(2,'20平远传','20',1,0,'1',NULL),(3,'25平远传','25',1,0,'1',NULL),(4,'15立远传','15',1,0,'1',NULL),(5,'20立远传','20',1,0,'1',NULL),(6,'40平远传','40',1,0,'1',NULL),(7,'15平非远传','15',0,0,'1',NULL);
/*!40000 ALTER TABLE `meterkind` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neighbor`
--

LOCK TABLES `neighbor` WRITE;
/*!40000 ALTER TABLE `neighbor` DISABLE KEYS */;
/*!40000 ALTER TABLE `neighbor` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `nonremoteexport`
--

LOCK TABLES `nonremoteexport` WRITE;
/*!40000 ALTER TABLE `nonremoteexport` DISABLE KEYS */;
INSERT INTO `nonremoteexport` VALUES (1,'com.xdkj.yccb.main.readme.import_.impl.JM_NonRemoteReadUpload','com.xdkj.yccb.main.readme.export.impl.JM_1ExportReadImpl','com.xdkj.yccb.main.readme.export.impl.JM_2ExportReadImpl','即墨测试',1);
/*!40000 ALTER TABLE `nonremoteexport` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pricekind`
--

LOCK TABLES `pricekind` WRITE;
/*!40000 ALTER TABLE `pricekind` DISABLE KEYS */;
INSERT INTO `pricekind` VALUES (1,1,'无单价',0.00,'1','不准更改  非扣费表单价');
/*!40000 ALTER TABLE `pricekind` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `propertyresp`
--

LOCK TABLES `propertyresp` WRITE;
/*!40000 ALTER TABLE `propertyresp` DISABLE KEYS */;
/*!40000 ALTER TABLE `propertyresp` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=5303 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readlog`
--

LOCK TABLES `readlog` WRITE;
/*!40000 ALTER TABLE `readlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `readlog` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=1699797 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readmeterlog`
--

LOCK TABLES `readmeterlog` WRITE;
/*!40000 ALTER TABLE `readmeterlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `readmeterlog` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `remoteexport`
--

LOCK TABLES `remoteexport` WRITE;
/*!40000 ALTER TABLE `remoteexport` DISABLE KEYS */;
INSERT INTO `remoteexport` VALUES (1,'com.xdkj.yccb.main.readme.export.impl.YT2ExportReadImpl','烟台市立户导出',1),(2,'com.xdkj.yccb.main.readme.export.impl.RC3ExportReadImpl','荣成导出',1),(3,'com.xdkj.yccb.main.readme.export.impl.FS5ExportReadImpl','福山自来水导出',1);
/*!40000 ALTER TABLE `remoteexport` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `role_authority`
--

LOCK TABLES `role_authority` WRITE;
/*!40000 ALTER TABLE `role_authority` DISABLE KEYS */;
INSERT INTO `role_authority` VALUES (1,1,6),(2,1,7),(3,1,8),(4,1,9),(5,1,10),(6,1,11),(7,1,12),(8,1,13),(9,1,14),(10,1,15),(11,1,16),(12,1,17),(13,1,18),(14,1,19),(15,1,20),(16,1,21),(17,1,22),(18,1,23),(19,1,24),(20,1,25),(21,1,26),(22,1,27),(23,1,28),(24,1,29),(25,1,30),(26,1,31),(27,1,32),(28,1,33),(29,2,7),(30,2,8),(31,2,9),(32,2,10),(33,2,11),(34,2,12),(35,2,13),(36,2,14),(37,2,15),(38,2,16),(39,2,17),(40,2,18),(41,2,19),(42,2,20),(43,2,21),(44,2,22),(45,2,23),(46,2,24),(47,2,25),(48,2,26),(49,2,27),(50,2,28),(51,2,29),(52,2,30),(53,2,31),(54,2,32),(55,2,33),(59,3,11),(60,3,12),(61,3,13),(62,3,14),(63,3,15),(64,3,16),(65,3,17),(69,4,11),(70,4,12),(71,4,13),(72,4,18),(73,4,19),(74,4,20),(75,4,21),(76,4,22),(77,4,23),(78,4,24);
/*!40000 ALTER TABLE `role_authority` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'超级管理员',1,'0','1','系统默认超级管理员'),(2,'管理员',1,'1','1','自来水最高权限'),(3,'抄表员',1,'1','1','只抄表'),(4,'收费员',1,'1','1','只收费');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settlelog`
--

LOCK TABLES `settlelog` WRITE;
/*!40000 ALTER TABLE `settlelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `settlelog` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6993 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useractionlog`
--

LOCK TABLES `useractionlog` WRITE;
/*!40000 ALTER TABLE `useractionlog` DISABLE KEYS */;
INSERT INTO `useractionlog` VALUES (6992,1,28,NULL,'2016-01-11 09:52:10','login:adminid1');
/*!40000 ALTER TABLE `useractionlog` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `valveconflog`
--

LOCK TABLES `valveconflog` WRITE;
/*!40000 ALTER TABLE `valveconflog` DISABLE KEYS */;
/*!40000 ALTER TABLE `valveconflog` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `valvelog`
--

LOCK TABLES `valvelog` WRITE;
/*!40000 ALTER TABLE `valvelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `valvelog` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `warnlog`
--

LOCK TABLES `warnlog` WRITE;
/*!40000 ALTER TABLE `warnlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `warnlog` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wastelog`
--

LOCK TABLES `wastelog` WRITE;
/*!40000 ALTER TABLE `wastelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `wastelog` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watercompany`
--

LOCK TABLES `watercompany` WRITE;
/*!40000 ALTER TABLE `watercompany` DISABLE KEYS */;
INSERT INTO `watercompany` VALUES (1,'西岛','国家大学科技园东区7号楼8楼','3710000000',0,'smtp.163.com','zffyxdkj@163.com','YWJjMTIzNDU2','西岛默认','53625462','国家大学科技园东区7号楼8楼');
/*!40000 ALTER TABLE `watercompany` ENABLE KEYS */;
UNLOCK TABLES;

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
				
				set first_ = 0,first_over = 0,second_ = 0,second_over = 0,third = 0,perYL=0;
				select BasicPriceFirst,BasicFirstOver,BasicPriceSecond,BasicSecondOver,BasicPriceThird,perYL into first_,first_over,second_,second_over,third,perYL from BasicPrice
				where pricekindid = pkid and valid = 1 limit 1;
				
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
				update meter 
				set destartread = deread,destarttime=detime
				where pid = mid;
				##将去年最后一次结算的数据  更新为今年第一次数据
				set destartread = deread,destarttime=detime;
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
				
				set first_ = 0,first_over = 0,second_ = 0,second_over = 0,third = 0,perYL=0;
				select BasicPriceFirst,BasicFirstOver,BasicPriceSecond,BasicSecondOver,BasicPriceThird,perYL into first_,first_over,second_,second_over,third,perYL from BasicPrice
				where pricekindid = pkid and valid = 1 limit 1;
				
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
/*!50003 DROP PROCEDURE IF EXISTS `calculate_neighbor_auto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `calculate_neighbor_auto`(nid_ int,adid_ int,readlogid_ int)
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
    
    declare yl int;
	declare demoney DECIMAL(19,4);
    declare mainmeter_ int;
    
    declare cm_cursor cursor for 
    select c.pid,c.CustomerBalance,c.PrePaySign,m.pid,m.PriceKindID,m.DeRead,m.DeTime,m.readdata,m.readtime,m.changend,m.mainmeter from meter m 
    join customer c
    on m.customerid = c.pid
    where c.neighborid = nid_ and c.valid = 1 and m.valid = 1 and isvalve = 1 and deductionstyle = 1 ;
    
    declare continue handler for NOT FOUND set fetch_ok = false;
    
    set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,settlelogid = 0,yl = 0,demoney = 0,changeend = 0,mainmeter_ = 0;
    set fetch_ok = true;
    set settlelogadd = false;
    
    open cm_cursor;
    fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_;
    while fetch_ok=true do
		if(settlelogadd = false) then
			insert into settlelog
            (readlogid,objectid,objecttype,settlestatus,adminid,starttime,auto,remark)
            values(readlogid_,nid_,1,100,adid_,now(),1,'');
			
            select max(pid) into settlelogid from settlelog
            where readlogid = readlogid_ and objectid = nid_ and adminid = adid_;
            
            set settlelogadd = true;
            
            update readlog
            set settle = 1
            where pid = readlogid_;
        end if;
        
        set yl = 0,demoney = 0;
        if(mainmeter_ = 0) then
			begin 
			
            #select cid,balance,pre,mid,pkid,deread,detime,read_,read_time;
			if(changeend > 0) then
				# the meter changed
				set yl = changeend - deread + read_;
				set circle = '';
			elseif (deread > 9000) and (read_ < 5000) then
				set yl = read_+(10000-deread);
				set circle = '套圈';
			else
				set yl = read_-deread;
				set circle = '';
			end if;
			
			#计算扣费
			if(yl >= 0 and yl < 9000) then
			begin
				declare first_ decimal(19,4);
				declare first_over int;
				declare second_ decimal(19,4);
				declare second_over int;
				declare third decimal(19,4);
				declare fetch_price boolean;
				
				declare price_cursor cursor for
				select BasicPriceFirst,BasicFirstOver,BasicPriceSecond,BasicSecondOver,BasicPriceThird from BasicPrice
				where pricekindid = pkid and valid = 1 ;
				
				declare continue handler for NOT FOUND set fetch_price = false;
				
				set fetch_price = true;
				set first_ = 0,first_over = 0,second_ = 0,second_over = 0,third = 0;
				open price_cursor;
				fetch price_cursor into first_,first_over,second_,second_over,third;
				while(fetch_price = true) do
					begin
						if(first_over = 0)then
							set demoney = demoney + yl*first_;
						elseif (first_over > 0 and second_over = 0)then
							if(yl > first_over)then
								set demoney = demoney + first_over*first_+second_*(yl-first_over);
							else
								set demoney = demoney + yl*first_;
							end if;
						elseif(first_over > 0 and second_over > 0)then
							if(yl > second_over)then
								set demoney = demoney + first_over*first_+second_*(second_over-first_over)+third*(yl-second_over);
							elseif (yl > first_over)then
								set demoney = demoney + first_over*first_+second_*(yl-first_over);
							else
								set demoney = demoney + yl*first_;
							end if;
						end if;
					end;
					fetch price_cursor into first_,first_over,second_,second_over,third;
				end while;
				close price_cursor;
			end;
			end if;
			
			#更新数据库
			
			start transaction ;
			if(yl > 0 and yl < 9000)then
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
        
		
        set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,changeend = 0,mainmeter_ = 0;
        fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_;
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

-- Dump completed on 2016-01-11 17:55:53
