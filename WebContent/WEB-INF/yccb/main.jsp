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
			"menuname" : "管理员",
			"menus" : [ 
			<c:if test="${menus['watcom']=='t'}">
				 {
					"menuid" : "111",
					"menuname" : "自来水公司",
					"icon" : "micon-nav",
					"url" : "admin/watcom/list.do"
				}, 
			</c:if>
			<c:if test="${menus['watcom']=='t'}">
				{
					"menuid" : "113",
					"menuname" : "管理员",
					"icon" : "micon-nav",
					"url" : "admin/list.do"
				},
			</c:if>
			<c:if test="${menus['auth']=='t'}">
				{
					"menuid" : "115",
					"menuname" : "权限",
					"icon" : "micon-nav",
					"url" : "sys/auth/list.do"
				},
			</c:if>	
			<c:if test="${menus['role']=='t'}">
				{
					"menuid" : "115",
					"menuname" : "角色",
					"icon" : "micon-nav",
					"url" : "sys/role/list.do"
				},
			</c:if>
			<c:if test="${menus['areas']=='t'}">
				{
					"menuid" : "117",
					"menuname" : "片区",
					"icon" : "micon-nav",
					"url" : "#"
				},
			</c:if>]
		}],
		info : [{
			"menuid" : "20",
			"icon" : "micon-inf",
			"menuname" : "信息录入",
			"menus" : [ {
				"menuid" : "211",
				"menuname" : "小区信息",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "213",
				"menuname" : "用户信息",
				"icon" : "micon-nav",
				"url" : "#"
			} ,]

		}],
		read : [{
			"menuid" : "30",
			"icon" : "micon-read",
			"menuname" : "抄表",
			"menus" : [ {
				"menuid" : "311",
				"menuname" : "抄表",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "313",
				"menuname" : "非远程录入",
				"icon" : "micon-nav",
				"url" : "#"
			}, ]

		}],
		charge : [{
			"menuid" : "40",
			"icon" : "micon-charge",
			"menuname" : "收费",
			"menus" : [ {
				"menuid" : "411",
				"menuname" : "收费",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "412",
				"menuname" : "结算",
				"icon" : "micon-nav",
				"url" : "#"
			} ,{
				"menuid" : "413",
				"menuname" : "后付费",
				"icon" : "micon-nav",
				"url" : "#"
			},{
				"menuid" : "414",
				"menuname" : "关阀控水",
				"icon" : "micon-nav",
				"url" : "#"
			} ,{
				"menuid" : "415",
				"menuname" : "阀控预付费",
				"icon" : "micon-nav",
				"url" : "#"
			},]

		}],
		statis : [{
			"menuid" : "50",
			"icon" : "micon-stat",
			"menuname" : "统计",
			"menus" : [ {
				"menuid" : "511",
				"menuname" : "收费统计",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "512",
				"menuname" : "扣费统计",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "513",
				"menuname" : "楼宇统计",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "514",
				"menuname" : "结算用水统计",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "515",
				"menuname" : "重点用户检测",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "516",
				"menuname" : "水损分析",
				"icon" : "micon-nav",
				"url" : "#"
			}, {
				"menuid" : "517",
				"menuname" : "收费率统计",
				"icon" : "micon-nav",
				"url" : "#"
			},]

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
		<span id="clock">当前时间：<span id="bgclock"></span></span>
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
			欢迎您，${userInfo.adminName }
			<a href="${path}/logout.do">安全退出</a>
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
		<p align="center">copryright 2014 版权所有 淅川西岛光电仪表科技有限公司</p>
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
<!-- <style type="text/css">
body{font-size: 13px;}
</style> -->
</body>
</html>