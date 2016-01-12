<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commonjsp/top.jsp" %>
<c:set var="menus" scope="session" value="${userInfo.menus}"/>
<title><fmt:message key="main.index"/></title>
<script type="text/javascript" src="${path}/resource/js/main.js"></script>
<script type="text/javascript">
var _menus = {
		admin : [ {
			"menuid" : "10",
			"icon" : "micon-sys",
			"menuname" :"<fmt:message key='admin'/>",
			"menus" : [ 
			<c:if test="${menus['watcom']=='t'}">
				 {
					"menuid" : "111",
					"menuname" : "<fmt:message key='watcom'/>",
					"icon" : "micon-nav",
					"url" : "${path}/admin/watcom/list.do"
				}, 
			</c:if>
			
				{
					"menuid" : "113",
					"menuname" : "<fmt:message key='admin'/>",
					"icon" : "micon-nav",
					"url" : <c:if test="${menus['adminor']=='t'}">"${path}/admin/list.do"</c:if><c:if test="${menus['adminor']!='t'}">"${path}/admin/updatePage.do?pid=${userInfo.pid}"</c:if>
				},
			
			<c:if test="${menus['role']=='t'}">
				{
					"menuid" : "115",
					"menuname" : "<fmt:message key='role'/>",
					"icon" : "micon-nav",
					"url" : "${path}/sys/role/list.do"
				},
			</c:if>
			
				{
					"menuid" : "117",
					"menuname" : "<fmt:message key='areas'/>",
					"icon" : "micon-nav",
					"url" : <c:if test="${menus['areas']=='t'}">"${path}/admin/dep/list.do"</c:if><c:if test="${menus['areas']!='t'}">"${path}/admin/dep/detail.do?depId=${userInfo.depart_id}"</c:if>
				},
				
				{
					"menuid" : "118",
					"menuname" : "<fmt:message key='basicprice'/>",
					"icon" : "micon-nav",
					"url" : "${path}/admin/price/list.do"
				},
			]
		}],
		<c:if test="${menus['infoin']=='t'}">
		info : [{
			"menuid" : "20",
			"icon" : "micon-inf",
			"menuname" : "<fmt:message key='info'/>",
			"menus" : [ 
			<c:if test="${menus['community']=='t'}">
			 {
				"menuid" : "211",
				"menuname" : "<fmt:message key='community'/>",
				"icon" : "micon-nav",
				"url" : "${path}/infoin/neighbor/list.do",
			},
			</c:if>
			<c:if test="${menus['userinfo']=='t'}">
			{
				"menuid" : "213",
				"menuname" : "<fmt:message key='userinfo'/>",
				"icon" : "micon-nav",
				"url" : "${path}/infoin/customer/list.do"
			} ,
			</c:if>]

		}],
		</c:if>
		<c:if test="${menus['readview']=='t'}"> 
		read : [{
			"menuid" : "30",
			"icon" : "micon-read",
			"menuname" : "<fmt:message key='readmeter'/>",
			"menus" : [ 
			<c:if test="${menus['readmeter']=='t'}"> 
			  {
				"menuid" : "311",
				"menuname" : "<fmt:message key='readmeter'/>",
				"icon" : "micon-nav",
				"url" : "${path}/readme/read/remotelist.do"
			}, 
			</c:if>
			<c:if test="${menus['unremote']=='t'}">
			{
				"menuid" : "313",
				"menuname" : "<fmt:message key='unremote'/>",
				"icon" : "micon-nav",
				"url" : "${path}/readme/read/unremotelist.do"
			}, </c:if>]

		}],
		</c:if>
		<c:if test="${menus['chargeview']=='t'}"> 
		charge : [
			<c:if test="${menus['charge']=='t'}">
		   {
			"menuid" : "40",
			"icon" : "micon-charge",
			"menuname" : "<fmt:message key='charge'/>",
			"menus" : [ {
				"menuid" : "411",
				"menuname" : "<fmt:message key='charge'/>",
				"icon" : "micon-nav",
				"url" : "${path}/charge/charge.do"
			}, 
			</c:if>
			<c:if test="${menus['settle']=='t'}">
			{
				"menuid" : "412",
				"menuname" : "<fmt:message key='settle'/>",
				"icon" : "micon-nav",
				"url" : "${path}/charge/settle.do"
			} ,
			</c:if>
			<c:if test="${menus['postpay']=='t'}">
			{
				"menuid" : "413",
				"menuname" : "<fmt:message key='postpay'/>",
				"icon" : "micon-nav",
				"url" : "${path}/charge/postpay.do"
			},
			</c:if>
			<c:if test="${menus['closevalve']=='t'}">
			{
				"menuid" : "414",
				"menuname" : "<fmt:message key='closevalve'/>",
				"icon" : "micon-nav",
				"url" : "${path}/charge/closevalve.do"
			} ,
			</c:if>
			<c:if test="${menus['vavlelog']=='t'}">
			{
				"menuid" : "415",
				"menuname" : "<fmt:message key='vavlelog'/>",
				"icon" : "micon-nav",
				"url" : "${path}/charge/valvelog.do"
			},</c:if>]

		}],
		</c:if>
		<c:if test="${menus['statisticview']=='t'}"> 
		statis : [{
			"menuid" : "50",
			"icon" : "micon-stat",
			"menuname" : "<fmt:message key='statis'/>",
			"menus" : [ 
			<c:if test="${menus['chargestat']=='t'}">
			 {
				"menuid" : "511",
				"menuname" : "<fmt:message key='chargestat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/payinfo.do"
			},
			</c:if>
			<c:if test="${menus['settlelogstat']=='t'}">
			{
				"menuid" : "512",
				"menuname" : "<fmt:message key='settlelogstat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/settlelog.do"
			},
			</c:if>
			<c:if test="${menus['settlelogwaterstat']=='t'}">
			{
				"menuid" : "513",
				"menuname" : "<fmt:message key='settlelogwaterstat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/settlelogwater.do"
			}, 
			</c:if>
			<c:if test="${menus['loustat']=='t'}">
			{
				"menuid" : "514",
				"menuname" : "<fmt:message key='loustat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/lou.do"
			},
			</c:if>
			<c:if test="${menus['owestat']=='t'}">
			{
				"menuid" : "514",
				"menuname" : "<fmt:message key='owestat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/owe.do"
			},
			</c:if>
			<c:if test="${menus['vipstat']=='t'}">
			{
				"menuid" : "516",
				"menuname" : "<fmt:message key='vipstat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/vip.do"
			}, 
			</c:if>
			<c:if test="${menus['wastestat']=='t'}">
			{
				"menuid" : "517",
				"menuname" : "<fmt:message key='wastestat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/waste.do"
			},</c:if>
			<c:if test="${menus['chargeratestat']=='t'}">
			{
				"menuid" : "515",
				"menuname" : "<fmt:message key='chargeratestat'/>",
				"icon" : "micon-nav",
				"url" : "${path}/statistics/chargerate.do"
			},
			</c:if>]

		}]</c:if>
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
<%-- 		<span id="clock"><fmt:message key="currenttime"/>：<span id="bgclock"></span></span> --%>
		<ul id="topmenu">
				<li class="nav_ico1"><a class="active" name="index" href="javascript:;" title="<fmt:message key='main.index'/>">
				<fmt:message key='main.index'/></a></li>
<%-- 			<c:if test="${menus['admin']=='t'}"> --%>
				<li class="nav_ico2"><a name="admin" href="javascript:;" title="<fmt:message key='admin'/>">
				<fmt:message key="admin"/></a></li>
<%-- 			</c:if> --%>
			<c:if test="${menus['infoin']=='t'}">
				<li class="nav_ico3"><a name="info" href="javascript:;" title="<fmt:message key='info'/>">
				<fmt:message key='info'/></a></li>
			</c:if>
			<c:if test="${menus['readview']=='t'}">
				<li class="nav_ico4"><a name="read" href="javascript:;" title="<fmt:message key='read'/>">
				<fmt:message key='read'/></a></li>
			</c:if>
			<c:if test="${menus['chargeview']=='t'}">
				<li class="nav_ico5"><a name="charge" href="javascript:;" title="<fmt:message key='charge'/>">
				<fmt:message key='charge'/></a></li>
			</c:if>
			<c:if test="${menus['statisticview']=='t'}">
				<li class="nav_ico6"><a name="statis" href="javascript:;" title="<fmt:message key='statis'/>">
				<fmt:message key='statis'/></a></li>
			</c:if>
		</ul>
		<span id="userInfo" style="color:rgb(199, 199, 224)">
			<fmt:message key="main.welcome"/>,${userInfo.adminName }
			<a href="${path}/logout.do" style="color:rgb(199, 199, 224)"><fmt:message key="logout"/></a>
		</span>
		 <span id="lang-chose" style="float:right; padding-right:20px;" class="head">
        	<a href="${path}/resource/lang.do?langType=zh" style="color:rgb(199, 199, 224)">中文</a> 
        	<a href="${path}/resource/lang.do?langType=en" style="color:rgb(199, 199, 224)">English</a>
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
		     <iframe style="border:0px;width:100%;height:100%;" src="${path}/intro.do"></iframe>
		     </div>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="background: #D2E0F2;overflow: hidden;">
		<p align="center">copyright 2016 版权所有 淅川西岛光电仪表科技有限公司</p>
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