<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<%@include file="/commonjsp/top.jsp" %>
<link href="../resource/css/index.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
$(function(){
	$('#pp').pagination({
	    total:2000,   
	    pageSize:10,
	    onSelectPage:function(pageNumber, pageSize){
			$(this).pagination('loading');
			alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
			$(this).pagination('loaded');
		}
	});
	
	$('#dg').datagrid({
	    url:'datagrid_data.json',   
	    columns:[[   
	        {field:'code',title:'Code',width:100},   
	        {field:'name',title:'Name',width:100},   
	        {field:'price',title:'Price',width:100,align:'right'}   
	    ]]   
	});  
	
});

/* function test(){
	var deliver = "111"
	var sender ="22";
	var msg = "街坊地块撒酒疯";
	$.post("http://sms.sd.cmcc/09411/SMSSender",
			{deliver:deliver,sender:sender,msg:msg},
			function(data,status){
		alert("data:"+data+"********status:"+status)})
} */
</script>
<body>
<%-- <a href="${path}/lang.do?langType=zh">中文</a><a href="${path}/lang.do?langType=en">英文</a>
<fmt:message key="title"/>
<fmt:message key="hello"/>
<input type="button" value="<fmt:message key='add'/>"/>
	<table id="dg"></table>
	<div id="pp" style="background:#efefef;border:1px solid #ccc;"></div> --%>
	<div id="main">
		<div id="header">
			<div id="logo">
				<h1>远程智能抄表</h1>
			</div>
			<div id="logout">
				<span id="welcome">您好，
				
				</span>&nbsp;&nbsp;
				<a href="">[退出]</a>
			</div>
		</div>
		<div id="nav">
			<ul>
				<li><a href="javascript:;"onclick="iframesrc('common.html');"><img src="img/admin.png"alt="">首页</a></li>
				<!-- 根据权限判断 -->	
				<li><a href="javascript:;"onclick="iframesrc('info/user.jsp');"><img src="img/info.png"alt="">信息录入</a></li>
				<li><a href="javascript:;"onclick="iframesrc('readmeter/readmeter.jsp');"><img src="img/read.png"alt="">抄表</a></li>
				<li><a href="javascript:;"onclick="iframesrc('charge/charge.jsp');"><img src="img/expense.png"alt="">收费</a></li>
				<li><a href="javascript:;"onclick="iframesrc('statistic/chargestatistics.jsp');"><img src="img/statistic.png"alt="">统计</a></li>
				<li><a href="javascript:;"onclick="iframesrc('help.doc');"><img src="img/statistic.png"alt="">帮助</a></li>
			</ul>
		</div>
		<div id="content">
			<!--ie 7 8不加frameborder="none"会出现边框-->
			<iframe src="common.html"id="myiframe"></iframe>
		</div>
		<!-- <div id="footer">
			copryright 2014 版权所有 淅川西岛光电仪表科技有限公司
		</div> -->
		<%@include file="/commonjsp/footer.jsp" %>
	</div>
</body>
</html>