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
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto',onSelect:searchSettle">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<label>楼</label>
				<select class="easyui-combobox" id="lou" name="lou" style="width:200px" data-options="panelHeight:'auto',valueField:'id',textField:'lou',onSelect:searchDy">
					<option value="">请选择楼</option>
	    		</select>
	    		
	    		<label>单元</label>
				<select class="easyui-combobox" id="dy" name="dy" style="width:200px" data-options="panelHeight:'auto',valueField:'id',textField:'dy'">
					<option value="">请选择单元</option>
	    		</select>
	    		
	    		<select class="easyui-combobox" id="pre" name="pre" data-options="panelHeight:'auto'" style="width: 148px;">
					<option value="1" selected="selected">预付费</option>
					<option value="0">后付费</option>
					<option value="2">全部</option>
				</select> 
				
				<label style="margin-left:10px;">最低金额</label>
				<input class="easyui-validatebox" type="text" name="low" id="low" value="0"/>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" >查找</a>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="print()" >打印</a>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="warnAll()" >提醒交费</a>
			</div>
		</form>
	</div>
	<table id="oweTab" style="width:100%;height:400px;"></table>
	<script type="text/javascript">
		$(function(){
			$("#oweTab").datagrid({
				striped:true,
				fitColumns:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'c_id',title:'ID',width:60,checkbox:true},
				          {field:'c_num',title:'用户号',width:80},
				          {field:'customerName',title:'用户名',width:80},
				          {field:'customerAddr',title:'地址',width:80},
				          {field:'customerMobile',title:'手机',width:80},
				          {field:'customerEmail',title:'邮箱',width:80},
				          {field:'customerBalance',title:'余额',width:80,styler:function(value,row,index){
				        	  if(value <= row.warnThre){
				        		  return 'background-color:#ffee00;color:red;';
				        	  }
				          }},
				          {field:'warnThre',title:'提醒阀值',width:80},
				          {field:'pre',title:'交费类型',width:60},
				          {field:'warnCount',title:'提醒次数',width:80},
				          {field:'action',title:'操作',width:160,halign:'center',align:'center',
								formatter: function(value,row,index){
									return "<a href='#' class='operateHref' onclick='warnSingle("+row.c_id+","+index+")'>提醒交费</a>";
						  }}
				      ]]
			});
		});
		
		function search_(){
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var lou = $("#lou").combobox("getText");
			var dy = $("#dy").combobox("getText");
			var low = $("#low").val();

			if (n_id == "") {
				$.messager.alert('Info', '请选择小区！');
				return;
			}
			if (low == "") {
				$.messager.alert('Info', '请输入最低金额！');
				return;
			}
			$('#oweTab').datagrid({
				url:"${path}/statistics/owe/listowe.do",
				queryParams: {
					n_id:n_id,
					pre:pre,
					dy:dy,
					lou:lou,
					low:low
				}
			});
		}
		
		function print() {
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var n_name = $("#neighbor").combobox("getText");
			var lou = $("#lou").combobox("getText");
			var dy = $("#dy").combobox("getText");
			var low = $("#low").val();
			

			if (n_id == "") {
				$.messager.alert('Info', '请选择小区！');
				return;
			}
			if (low == "") {
				$.messager.alert('Info', '请输入最低金额！');
				return;
			}
			
			window.open("${path}/statistics/owe/print.do?n_id=" + n_id
					+ "&dy=" + dy + "&n_name=" + n_name + "&low=" + low+ "&lou=" + lou
					+ "&pre=" + pre, "_blank");
		}
		function searchSettle(){
			var n_id = $("#neighbor").combobox("getValue");
			
			if(n_id != "" ){
				$('#lou').combobox('reload','${path}/statistics/owe/listlou.do?n_id='+n_id);
			}
		}
		function searchDy(){
			var n_id = $("#neighbor").combobox("getValue");
			var lou = $("#lou").combobox("getText");
			if(n_id != "" && lou != ""){
				$('#dy').combobox('reload','${path}/statistics/owe/listdy.do?n_id='+n_id+'&lou='+lou);
			}
		}
		

		function warnAll(){
			var c_ids = [];
			var rows = $('#oweTab').datagrid('getSelections');
			
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				c_ids.push(row.c_id);
			}
			if(c_ids.length != 0){
				$.ajax({
					type:"POST",
					url:"${path}/charge/valve/warnall.do",
					dataType:"json",  
			        traditional :true,
					data:{
						'c_ids':c_ids
					},
					success:function(data){
						if(data.done == true){
							$.messager.show({title:'Info',msg:'服务器正努力发送中...'});
						}else{
							$.messager.alert('Info','请选择用户');
						}
					}
				});
			}else{
				$.messager.alert('Info','请选择用户');
			}
		}

		function warnSingle(cid,index){
			$.ajax({
				type:"POST",
				url:"${path}/charge/valve/warnsingle.do",
				dataType:"json",
				data:{
					c_id:cid
				},
				success:function(data){
					if(data.done == true){
						$.messager.alert('Info','信息已发送');
					}else{
						$.messager.alert('Error','信息发送失败');
					}
				}
			});
		}
	</script>
</body>
</html>