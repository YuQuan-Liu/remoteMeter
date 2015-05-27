<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>收费统计</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label>小区</label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto'">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<input class="easyui-datebox" id="from"></input>
	    		<input class="easyui-datebox" id="to"></input> 
	    		<select class="easyui-combobox" id="pre" name="pre" data-options="panelHeight:'auto'" style="width: 148px;">
					<option value="1" selected="selected">预付费</option>
					<option value="0">后付费</option>
					<option value="2">全部</option>
				</select> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="print()" >打印</a>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function print(){
			var start = $("#from").datebox("getValue");
			var end = $("#to").datebox("getValue");
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var n_name = $("#neighbor").combobox("getText");
			
			if(n_id == ""){
				
				$.messager.alert('Info','请选择小区');
				return;
				$("#n_id").val(n_id);
				$("#n_name").val(n_name);
				
				$("#exportform").form('submit',{
					url:"${path}/readme/read/download.do",
				});
			}
			if(start==""){
				$.messager.alert('Info','请选择开始时间！');
				return;
			}
			if(end==""){
				$.messager.alert('Info','请选择截止时间！');
				return;
			}
			window.open("${path}/statistics/payinfo/print.do?n_id="+encodeURI(n_id)+"&start="+encodeURI(start)+"&end="+encodeURI(end)+"&n_name="+encodeURI(n_name)+"&pre="+encodeURI(pre)+"","_blank");
		}
	</script>
</body>
</html>