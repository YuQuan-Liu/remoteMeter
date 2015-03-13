<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commonjsp/top.jsp" %>
<title><fmt:message key="main.title"></fmt:message></title>
<script type="text/javascript" src="${path}/resource/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/default.css"> 
<script type="text/javascript">
var _menus = {
		"menus": [{
			"menuid": "1",
			"icon": "icon-sys",
			"menuname": "管理员",
			"menus": [{
				"menuid": "12",
				"menuname": "自来水公司",
				"icon": "icon-add",
				"url": "${path}/admin/watcom/list.do"
				/* "child": [{
					"menuid": "140",
					"menuname": "角色管理 3",
					"icon": "icon-role",
					"url": "demo2.html"
				},
				{
					"menuid": "150",
					"menuname": "权限设置 3",
					"icon": "icon-set",
					"url": "demo.html"
				}] */
			},
			{
				"menuid": "13",
				"menuname": "管理员",
				"icon": "icon-users",
				"url": "${path}/admin/list.do",
				"child": [{
					"menuid": "141",
					"menuname": "角色管理 3",
					"icon": "icon-role",
					"url": "demo2.html"
				},
				{
					"menuid": "151",
					"menuname": "权限设置 3",
					"icon": "icon-set",
					"url": "demo.html"
				}]
			},
			{
				"menuid": "14",
				"menuname": "角色管理",
				"icon": "icon-role",
				"url": "demo2.html",
				"child": [{
					"menuid": "142",
					"menuname": "角色管理 3",
					"icon": "icon-role",
					"url": "demo2.html"
				},
				{
					"menuid": "152",
					"menuname": "权限设置 3",
					"icon": "icon-set",
					"url": "demo.html"
				}]
			},
			{
				"menuid": "15",
				"menuname": "权限设置",
				"icon": "icon-set",
				"url": "demo.html",
				"child": [{
					"menuid": "143",
					"menuname": "角色管理 3",
					"icon": "icon-role",
					"url": "demo2.html"
				},
				{
					"menuid": "153",
					"menuname": "权限设置 3",
					"icon": "icon-set",
					"url": "demo.html"
				}]
			},
			{
				"menuid": "16",
				"menuname": "系统日志",
				"icon": "icon-log",
				"url": "demo1.html",
				"child": [{
					"menuid": "144",
					"menuname": "角色管理 3",
					"icon": "icon-role",
					"url": "demo2.html"
				},
				{
					"menuid": "154",
					"menuname": "权限设置 3",
					"icon": "icon-set",
					"url": "demo.html"
				}]
			}]
		},
		{
			"menuid": "8",
			"icon": "icon-sys",
			"menuname": "员工管理",
			"menus": [{
				"menuid": "21",
				"menuname": "员工列表",
				"icon": "icon-nav",
				"url": "demo.html"
			},
			{
				"menuid": "22",
				"menuname": "视频监控",
				"icon": "icon-nav",
				"url": "demo1.html",
				"child": [{
					"menuid": "221",
					"menuname": "员工列表 3",
					"icon": "icon-nav",
					"url": "demo.html"
				},
				{
					"menuid": "222",
					"menuname": "视频监控 3",
					"icon": "icon-nav",
					"url": "demo1.html"
				}]
			}]
		},
		{
			"menuid": "56",
			"icon": "icon-sys",
			"menuname": "部门管理",
			"menus": [{
				"menuid": "31",
				"menuname": "添加部门",
				"icon": "icon-nav",
				"url": "demo1.html"
			},
			{
				"menuid": "321",
				"menuname": "部门列表",
				"icon": "icon-nav",
				"url": "demo2.html",
				"child": [{
					"menuid": "311",
					"menuname": "添加部门 4",
					"icon": "icon-nav",
					"url": "demo1.html"
				},
				{
					"menuid": "312",
					"menuname": "部门列表 4",
					"icon": "icon-nav",
					"url": "demo2.html"
				}]
			}]
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
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="${path}/resource/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>

	<div data-options="region:'north',border:false" style="height:95px;padding:10px;">头部
		 <span style="float:right; padding-right:20px;" class="head">
        	<a href="${path}/lang.do?langType=zh" >中文</a> 
        	<a href="${path}/lang.do?langType=en" >English</a>
        </span>
	</div>
	<div data-options="region:'west',split:true,title:'<fmt:message key="main.navi" />' " style="width:180px;padding:1px;">
		<div id="nav">
		<!--  导航内容 -->
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div id="tabs" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true">  
		     <div title='<fmt:message key="main.welcome"/>' data-options="closable:false,select:true" style="padding:5px;"></div>  
		</div>  
	</div>
	<div data-options="region:'south',border:false" style="height:50px;padding-bottom:10px; background: #D2E0F2;">
		<p align="center">copryright 2014 版权所有 淅川西岛光电仪表科技有限公司</p>
	</div>
	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="exit">退出</div>
	</div>
</body>
</html>