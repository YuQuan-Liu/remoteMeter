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
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200'">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<input class="easyui-datebox" id="from"></input>
	    		<input class="easyui-datebox" id="to"></input> 
	    		<select class="easyui-combobox" id="pre" name="pre" data-options="panelHeight:'auto'" style="width: 148px;">
					<option value="1" selected="selected"><fmt:message key='statis.pre'/></option>
					<option value="0"><fmt:message key='statis.post'/></option>
					<option value="2"><fmt:message key='statis.all'/></option>
				</select> 
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" ><fmt:message key='search'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="print()" ><fmt:message key='print'/></a>
			</div>
		</form>
	</div>
	<table id="paylogTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;float:left;">
		<p><fmt:message key='payinfo.admin'/></p>
		<table id="adminSumTab" style="width:400px;height:200px;"></table>
	</div>
	<div style="margin:10px;float:left;">
		<p><fmt:message key='neighborbalance'/></p>
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
				          {field:'c_num',title:'<fmt:message key='c.num'/>',width:80},
				          {field:'customerName',title:'<fmt:message key='c.name'/>',width:80},
				          {field:'customerAddr',title:'<fmt:message key='common.addr'/>',width:80},
				          {field:'customerBalance',title:'<fmt:message key='c.balance'/>',width:80,styler:function(value,row,index){
				        	  if(value <= row.warnThre){
				        		  return 'background-color:#ffee00;color:red;';
				        	  }
				          }},
				          {field:'customerMobile',title:'<fmt:message key='common.mobile'/>',width:80},
				          {field:'prePaySign',title:'<fmt:message key='c.prestyle'/>',width:60,formatter:function(value,row,index){
				        	  if(value == 1){
				        		  return "<fmt:message key='c.pre'/>";
				        	  }else{
				        		  return "<fmt:message key='c.post'/>";
				        	  }
				          }},
						  {field:'amount',title:'<fmt:message key='charge.amount'/>',width:60},
						  {field:'actionTime',title:'<fmt:message key='charge.paytime'/>',width:60},
						  {field:'adminName',title:'<fmt:message key='charge.payadmin'/>',width:60}
				      ]]
			});
			$("#adminSumTab").datagrid({
				striped:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'adminName',title:'<fmt:message key='admin'/>',width:100},
				          {field:'amount',title:'<fmt:message key='payinfo.sum'/>',width:100}
				      ]]
			});
			$("#nbalanceTab").datagrid({
				striped:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'pre',title:'<fmt:message key='payinfo.paystyle'/>',width:100},
				          {field:'balance',title:'<fmt:message key='balance'/>',width:100}
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

				$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
			}
			if (start == "") {
				$.messager.alert('Info', '<fmt:message key='payinfo.start'/>');
				return;
			}
			if (end == "") {
				$.messager.alert('Info', '<fmt:message key='payinfo.end'/>');
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

				$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
				$("#n_id").val(n_id);
				$("#n_name").val(n_name);

				$("#exportform").form('submit', {
					url : "${path}/readme/read/download.do",
				});
			}
			if (start == "") {
				$.messager.alert('Info', '<fmt:message key='payinfo.start'/>');
				return;
			}
			if (end == "") {
				$.messager.alert('Info', '<fmt:message key='payinfo.end'/>');
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