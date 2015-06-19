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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-19 13:31:08
