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
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" >查找</a>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="print()" >打印</a>
			</div>
		</form>
	</div>
	<table id="paylogTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;float:left;">
		<p>管理员统计</p>
		<table id="adminSumTab" style="width:400px;height:200px;"></table>
	</div>
	<div style="margin:10px;float:left;">
		<p>小区金额</p>
		<table id="nbalanceTab" style="width:400px;height:200px;"></table>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#paylogTab").datagrid({
				striped:true,
				fitColumns:true,
				method:'post',
	//	 		url:"${path}/readme/read/listread",
				queryParams:{
					
				},
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
	//	 		singleSelect:true,  
				rowStyler:function(index,row){
					
				},
				columns:[[
				          {field:'c_num',title:'用户号',width:80},
				          {field:'customerName',title:'用户名',width:80},
				          {field:'customerAddr',title:'地址',width:80},
				          {field:'customerBalance',title:'余额',width:80,styler:function(value,row,index){
				        	  if(value <= row.warnThre){
				        		  return 'background-color:#ffee00;color:red;';
				        	  }
				          }},
				          {field:'customerMobile',title:'手机',width:80},
				          {field:'prePaySign',title:'付费方式',width:80,formatter:function(value,row,index){
				        	  if(row.prePaySign==1){
				        		  return "预";
				        	  }else{
				        		  return "后";
				        	  }
				          }},
				          {field:'amount',title:'交费金额',width:80},
				          {field:'adminName',title:'管理员',width:80},
				          {field:'actionTime',title:'收费时间',width:80}
				      ]]
			});
			$("#adminSumTab").datagrid({
				striped:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'adminName',title:'管理员',width:100},
				          {field:'amount',title:'收费合计',width:100}
				      ]]
			});
			$("#nbalanceTab").datagrid({
				striped:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'pre',title:'付费',width:100},
				          {field:'balance',title:'余额',width:100}
				      ]]
			});
		});
		
		function search_(){
			var start = $("#from").datebox("getValue");
			var end = $("#to").datebox("getValue");
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var n_name = $("#neighbor").combobox("getText");

			if (n_id == "") {

				$.messager.alert('Info', '请选择小区');
				return;
			}
			if (start == "") {
				$.messager.alert('Info', '请选择开始时间！');
				return;
			}
			if (end == "") {
				$.messager.alert('Info', '请选择截止时间！');
				return;
			}
			$('#paylogTab').datagrid({
				url:"${path}/statistics/payinfo/listpayinfo.do",
				queryParams: {
					n_id:n_id,
					start:start,
					end:end,
					pre:pre,
					n_name:n_name
				}
			});
			
			$('#adminSumTab').datagrid({
				url:"${path}/statistics/payinfo/listadminsum.do",
				queryParams: {
					n_id:n_id,
					start:start,
					end:end,
					pre:pre,
					n_name:n_name
				}
			});
			
			$('#nbalanceTab').datagrid({
				url:"${path}/statistics/payinfo/neighborbalance.do",
				queryParams: {
					n_id:n_id
				}
			});
		}
		
		function print() {
			var start = $("#from").datebox("getValue");
			var end = $("#to").datebox("getValue");
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var n_name = $("#neighbor").combobox("getText");

			if (n_id == "") {

				$.messager.alert('Info', '请选择小区');
				return;
				$("#n_id").val(n_id);
				$("#n_name").val(n_name);

				$("#exportform").form('submit', {
					url : "${path}/readme/read/download.do",
				});
			}
			if (start == "") {
				$.messager.alert('Info', '请选择开始时间！');
				return;
			}
			if (end == "") {
				$.messager.alert('Info', '请选择截止时间！');
				return;
			}
			// 			var url = encodeURI();
			// 			alert(url);
			window.open("${path}/statistics/payinfo/print.do?n_id=" + n_id
					+ "&start=" + start + "&end=" + end + "&n_name=" + n_name
					+ "&pre=" + pre, "_blank");
		}
	</script>
</body>
</html>