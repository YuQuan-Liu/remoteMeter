<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript">
	function addNewTab(obj,url){
		var title = $(obj).html();
		$('#rightContent').tabs('add',{   
		    title:title,
		    href:url,
		    closable:true,  
		    border:false,
		    tools:[{   
		        iconCls:'icon-mini-refresh',   
		        handler:function(){   
		           // alert('refresh');   
		        }   
		    }]   
		});
	}
</script>
<style type="text/css">
.menuButton{
float: left;
width: 100%;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'West'" style="width:180px;padding:1px;">
		<div class="easyui-accordion" data-options="fit:false,border:false">
			<div title="管理员" style="padding:5px;" data-options="selected:true,fit:true">
				<a onclick="addNewTab(this,'admin/list.do')" class="easyui-linkbutton menuButton" data-options="plain:true">test</a>
				<a href="#" class="easyui-linkbutton menuButton" data-options="plain:true">Home</a>
				<a href="#" class="easyui-linkbutton menuButton" data-options="plain:true">Home</a>
				<a href="#" class="easyui-linkbutton menuButton" data-options="plain:true">Home</a>
			</div>
			<div title="Title2" style="padding:5px;" data-options="fit:true">
				content2
			</div>
			<div title="Title3" style="padding:5px" data-options="fit:true">
				content3
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div id="rightContent" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true">  
		    <div title="Tab2" data-options="closable:true," style="padding:5px;"></div>  
		    <div title="Tab3" data-options="iconCls:'icon-reload',closable:true" style="padding:5px;"></div>  
		</div>  
	</div>
	<div data-options="region:'south',border:false" style="height:50px;padding:10px;">
		<p align="center">copryright 2014 版权所有 淅川西岛光电仪表科技有限公司</p>
	</div>
</body>
</html>