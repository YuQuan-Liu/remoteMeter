<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自来水公司列表</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript">
$(function(){
	$('#watComListTab').datagrid({
	    url:'${path}/admin/watcom/listContent.do',
	    fit:true,
	    pagination:true,
	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    border:false,
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'companyName',title:'公司名',width:100},   
	        {field:'companyAddr',title:'公司地址',width:100},
	        {field:'mark',title:'公司标识',width:100}
	    ]],
	});
})
</script>
</head>
<body>
<table id="watComListTab"></table>
<div id="watComAddWin"></div>
<div id="watComUpdateWin"></div>
</body>
</html>