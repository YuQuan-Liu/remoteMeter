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
    
    declare yl int;
	declare demoney DECIMAL(19,4);
    declare mainmeter_ int;
    
    declare cm_cursor cursor for 
    select c.pid,c.CustomerBalance,c.PrePaySign,m.pid,m.PriceKindID,m.DeRead,m.DeTime,m.readdata,m.readtime,m.changend,m.mainmeter from meter m 
    join customer c
    on m.customerid = c.pid
    where m.pid = mid_;
    
    declare continue handler for NOT FOUND set fetch_ok = false;
    
    set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,yl = 0,demoney = 0,changeend = 0,mainmeter_ = 0;
    set fetch_ok = true;
    
    open cm_cursor;
    fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_;
    while fetch_ok=true do
    
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
			if(yl >= 0) then
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
			values(mid,read_,read_time,deread,detime,pkid,demoney,settlelogid_,0,now(),pre,0,0,changeend,circle,1);
			
			commit;
			
			end;
        end if;
        
		
        set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,changeend = 0,mainmeter_=0;
        fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_;
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
    
    declare yl int;
	declare demoney DECIMAL(19,4);
    declare mainmeter_ int;
    
    declare cm_cursor cursor for 
    select c.pid,c.CustomerBalance,c.PrePaySign,m.pid,m.PriceKindID,m.DeRead,m.DeTime,m.readdata,m.readtime,m.changend,m.mainmeter from meter m 
    join customer c
    on m.customerid = c.pid
    where c.neighborid = nid_ and c.valid = 1 and m.valid = 1 and deductionstyle = 0 ;
    
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
            values(readlogid_,nid_,1,100,adid_,now(),0,'');
			
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
			if(yl >= 0) then
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
		
        set cid = 0,balance = 0,pre = 0,mid = 0,pkid = 0,deread = 0,read_ = 0,readtype = 0,changeend = 0,mainmeter_=0;
        fetch cm_cursor into cid,balance,pre,mid,pkid,deread,detime,read_,read_time,changeend,mainmeter_;
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
			if(yl >= 0) then
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

-- Dump completed on 2015-06-19 13:31:10
