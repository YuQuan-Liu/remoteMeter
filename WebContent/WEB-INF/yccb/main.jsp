<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commonjsp/top.jsp" %>
<c:set var="menus" scope="session" value="${userInfo.menus}"/>
<title><fmt:message key="main.title"/></title>
<script type="text/javascript" src="${path}/resource/js/main.js"></script>
<script type="text/javascript">
var _menus = {
		admin : [ {
			"menuid" : "10",
			"icon" : "micon-sys",
			"menuname" :"<fmt:message key='menu.admin'/>",
			"menus" : [ 
			<c:if test="${menus['watcom']=='t'}">
				 {
					"menuid" : "111",
					"menuname" : "<fmt:message key='menu.watcom'/>",
					"icon" : "micon-nav",
					"url" : "admin/watcom/list.do"
				}, 
			</c:if>
			<c:if test="${menus['watcom']=='t'}">
				{
					"menuid" : "113",
					"menuname" : "<fmt:message key='menu.admin'/>",
					"icon" : "micon-nav",
					"url" : "admin/list.do"
				},
			</c:if>
			<c:if test="${menus['auth']=='t'}">
				{
					"menuid" : "115",
					"menuname" : "<fmt:message key='menu.auth'/>",
					"icon" : "micon-nav",
					"url" : "sys/auth/list.do"
				},
			</c:if>	
			<c:if test="${menus['role']=='t'}">
				{
					"menuid" : "115",
					"menuname" : "<fmt:message key='menu.role'/>",
					"icon" : "micon-nav",
					"url" : "sys/role/list.do"
				},
			</c:if>
			<c:if test="${menus['areas']=='t'}">
				{
					"menuid" : "117",
					"menuname" : "<fmt:message key='menu.areas'/>",
					"icon" : "micon-nav",
					"url" : "admin/dep/list.do"
				},
			</c:if>
			<c:if test="${menus['basicprice']=='t'}">
				{
					"menuid" : "118",
					"menuname" : "<fmt:message key='menu.basicprice'/>",
					"icon" : "micon-nav",
					"url" : "admin/price/list.do"
				},
			</c:if>]
		}],
		info : [{
			"menuid" : "20",
			"icon" : "micon-inf",
			"menuname" : "<fmt:message key='menu.info'/>",
			"menus" : [ 
			<c:if test="${menus['community']=='t'}">
			 {
				"menuid" : "211",
				"menuname" : "<fmt:message key='menu.community'/>",
				"icon" : "micon-nav",
				"url" : "infoin/neighbor/list.do",
			},
			</c:if>
			<c:if test="${menus['userinfo']=='t'}">
			{
				"menuid" : "213",
				"menuname" : "<fmt:message key='menu.user'/>",
				"icon" : "micon-nav",
				"url" : "infoin/customer/list.do"
			} ,
			</c:if>]

		}],
		read : [{
			"menuid" : "30",
			"icon" : "micon-read",
			"menuname" : "<fmt:message key='menu.read'/>",
			"menus" : [ 
			<c:if test="${menus['readmeter']=='t'}"> 
			  {
				"menuid" : "311",
				"menuname" : "<fmt:message key='menu.readmeter'/>",
				"icon" : "micon-nav",
				"url" : "readme/read/remotelist.do"
			}, 
			</c:if>
			<c:if test="${menus['unremote']=='t'}">
			{
				"menuid" : "313",
				"menuname" : "<fmt:message key='menu.unremote'/>",
				"icon" : "micon-nav",
				"url" : "readme/read/unremotelist.do"
			}, </c:if>]

		}],
		charge : [
			<c:if test="${menus['chargech']=='t'}">
		   {
			"menuid" : "40",
			"icon" : "micon-charge",
			"menuname" : "<fmt:message key='menu.charge'/>",
			"menus" : [ {
				"menuid" : "411",
				"menuname" : "<fmt:message key='menu.charge'/>",
				"icon" : "micon-nav",
				"url" : "charge/charge.do"
			}, 
			</c:if>
			<c:if test="${menus['calcost']=='t'}">
			{
				"menuid" : "412",
				"menuname" : "<fmt:message key='menu.calcost'/>",
				"icon" : "micon-nav",
				"url" : "charge/settle.do"
			} ,
			</c:if>
			<c:if test="${menus['chargelat']=='t'}">
			{
				"menuid" : "413",
				"menuname" : "<fmt:message key='menu.chargelat'/>",
				"icon" : "micon-nav",
				"url" : "charge/postpay.do"
			},
			</c:if>
			<c:if test="${menus['closetap']=='t'}">
			{
				"menuid" : "414",
				"menuname" : "<fmt:message key='menu.closetap'/>",
				"icon" : "micon-nav",
				"url" : "charge/closevalve.do"
			} ,
			</c:if>
			<c:if test="${menus['domaintap']=='t'}">
			{
				"menuid" : "415",
				"menuname" : "<fmt:message key='menu.domaintap'/>",
				"icon" : "micon-nav",
				"url" : "charge/valvelog.do"
			},</c:if>]

		}],
		statis : [{
			"menuid" : "50",
			"icon" : "micon-stat",
			"menuname" : "<fmt:message key='menu.statis'/>",
			"menus" : [ 
			<c:if test="${menus['chargestat']=='t'}">
			 {
				"menuid" : "511",
				"menuname" : "<fmt:message key='menu.chargestat'/>",
				"icon" : "micon-nav",
				"url" : "statistics/payinfo.do"
			},
			</c:if>
			<c:if test="${menus['chargedstat']=='t'}">
			{
				"menuid" : "512",
				"menuname" : "<fmt:message key='menu.chargedstat'/>",
				"icon" : "micon-nav",
				"url" : "statistics/settlelog.do"
			},
			</c:if>
			<c:if test="${menus['chargewaterstat']=='t'}">
			{
				"menuid" : "513",
				"menuname" : "<fmt:message key='menu.chargewaterstat'/>",
				"icon" : "micon-nav",
				"url" : "#"
			}, 
			</c:if>
			<c:if test="${menus['buildingstat']=='t'}">
			{
				"menuid" : "514",
				"menuname" : "<fmt:message key='menu.buildingstat'/>",
				"icon" : "micon-nav",
				"url" : "statistics/lou.do"
			},
			</c:if>
			<c:if test="${menus['chargewaterstat']=='t'}">
			{
				"menuid" : "515",
				"menuname" : "<fmt:message key='menu.chargewaterstat'/>",
				"icon" : "micon-nav",
				"url" : "#"
			},
			</c:if>
			<c:if test="${menus['maincustmonitor']=='t'}">
			{
				"menuid" : "516",
				"menuname" : "<fmt:message key='menu.maincustmonitor'/>",
				"icon" : "micon-nav",
				"url" : "#"
			}, 
			</c:if>
			<c:if test="${menus['waterlossanaly']=='t'}">
			{
				"menuid" : "517",
				"menuname" : "<fmt:message key='menu.waterlossanaly'/>",
				"icon" : "micon-nav",
				"url" : "#"
			},</c:if>]

		}]
	};
</script>
</head>
<body class="easyui-layout">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div>
</noscript>
<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
	<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center; 
	 border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
	    <img src="${path}/resource/images/loading.gif" align="absmiddle" />
	    <fmt:message key="main.loading"/>
	</div>
</div>
	<div data-options="region:'north',border:false,expand:true" style="height:95px;padding:0px;" id="north-head">
		<div class="logo"><a href="http://www.xcxdtech.com" target="_blank"><img width="465" height="95" src="${path}/resource/images/logo.jpg"></a></div>
		<span id="clock"><fmt:message key="currenttime"/>：<span id="bgclock"></span></span>
		<ul id="topmenu">
				<li ><a class="active" name="index" href="javascript:;" title="<fmt:message key='menu.index'/>"><fmt:message key='menu.index'/></a></li>
			<c:if test="${menus['admin']=='t'}">
				<li><a name="admin" href="javascript:;" title="<fmt:message key='menu.admin'/>">
				<fmt:message key="menu.admin"/></a></li>
			</c:if>
			<c:if test="${menus['info']=='t'}">
				<li ><a name="info" href="javascript:;" title="<fmt:message key='menu.info'/>">
				<fmt:message key='menu.info'/></a></li>
			</c:if>
			<c:if test="${menus['read']=='t'}">
				<li><a name="read" href="javascript:;" title="<fmt:message key='menu.read'/>">
				<fmt:message key='menu.read'/></a></li>
			</c:if>
			<c:if test="${menus['charge']=='t'}">
				<li ><a name="charge" href="javascript:;" title="<fmt:message key='menu.charge'/>">
				<fmt:message key='menu.charge'/></a></li>
			</c:if>
			<c:if test="${menus['statis']=='t'}">
				<li><a name="statis" href="javascript:;" title="<fmt:message key='menu.statis'/>">
				<fmt:message key='menu.statis'/></a></li>
			</c:if>
		</ul>
		<span id="userInfo">
			<fmt:message key="main.welcome"/>，${userInfo.adminName }
			<a href="${path}/logout.do"><fmt:message key="logout"/></a>
		</span>
		 <span id="lang-chose" style="float:right; padding-right:20px;" class="head">
        	<a href="${path}/lang.do?langType=zh" >中文</a> 
        	<a href="${path}/lang.do?langType=en" >English</a>
        </span>
	</div>
	<div data-options="region:'west',collapsed:true,split:true,title:'<fmt:message key="main.navi" />' " style="width:180px;padding:1px;" id="west-layout">
		<div id='wnav' class="easyui-accordion" fit="true" border="false">
		<!--  导航菜单 -->
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div id="tabs" class="easyui-tabs" data-options="fit:true">  
		     <div id='welcome' title='<fmt:message key="main.welcome"/>' data-options="closable:false,select:true" style="padding:5px;">
		     <h2><fmt:message key="main.welcome"/></h2>
		     </div>  
		</div>  
	</div>
	<div data-options="region:'south',border:false" style="background: #D2E0F2;overflow: hidden;">
		<p align="center">copyright 2015 版权所有 淅川西岛光电仪表科技有限公司</p>
	</div>
	<div id="mm" class="easyui-menu" style="width:150px;">
		<!-- <div id="mm-tabupdate">刷新</div> -->
		<div id="close"><fmt:message key="main.mm.close"/></div>
		<div id="closeall"><fmt:message key="main.mm.closeall"/></div>
		<div id="closeother"><fmt:message key="main.mm.closeother"/></div>
		<div class="menu-sep"></div>
		<div id="closeright"><fmt:message key="main.mm.closeright"/></div>
		<div id="closeleft"><fmt:message key="main.mm.closeleft"/></div>
		<div id="exit"><fmt:message key="main.mm.exit"/></div>
	</div>
</body>
</html>