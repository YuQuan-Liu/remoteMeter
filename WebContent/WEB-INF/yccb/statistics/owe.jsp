<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>欠费统计</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200',onSelect:searchSettle">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<label><fmt:message key='lou'/></label>
				<select class="easyui-combobox" id="lou" name="lou" style="width:200px" data-options="panelHeight:'200',valueField:'id',textField:'lou',onSelect:searchDy">
					<option value=""><fmt:message key='lou.selectlou'/></option>
	    		</select>
	    		
	    		<label><fmt:message key='dy'/></label>
				<select class="easyui-combobox" id="dy" name="dy" style="width:200px" data-options="panelHeight:'200',valueField:'id',textField:'dy'">
					<option value=""><fmt:message key='selectdy'/></option>
	    		</select>
	    		
	    		<select class="easyui-combobox" id="pre" name="pre" data-options="panelHeight:'auto'" style="width: 148px;">
					<option value="1" selected="selected"><fmt:message key='statis.pre'/></option>
					<option value="0"><fmt:message key='statis.post'/></option>
					<option value="2"><fmt:message key='statis.all'/></option>
				</select> 
				
				<label style="margin-left:10px;"><fmt:message key='owe.lowbalance'/></label>
				<input class="easyui-validatebox" type="text" name="low" id="low" value="0"/>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" ><fmt:message key='search'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="print()" ><fmt:message key='print'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="warnAll()" id="warnallbtn"><fmt:message key='warnpay'/></a>
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
				          {field:'c_num',title:'<fmt:message key='c.num'/>',width:80},
				          {field:'customerName',title:'<fmt:message key='c.name'/>',width:80},
				          {field:'customerAddr',title:'<fmt:message key='common.addr'/>',width:80},
				          {field:'customerMobile',title:'<fmt:message key='common.mobile'/>',width:80},
				          {field:'customerEmail',title:'<fmt:message key='common.email'/>',width:80},
				          {field:'customerBalance',title:'<fmt:message key='c.balance'/>',width:80,styler:function(value,row,index){
				        	  if(value <= row.warnThre){
				        		  return 'background-color:#ffee00;color:red;';
				        	  }
				          }},
				          {field:'warnThre',title:'<fmt:message key='c.warnthre'/>',width:80},
				          {field:'prePaySign',title:'<fmt:message key='c.prestyle'/>',width:60,formatter:function(value,row,index){
				        	  if(value == 1){
				        		  return "<fmt:message key='c.pre'/>";
				        	  }else{
				        		  return "<fmt:message key='c.post'/>";
				        	  }
				          }},
				          {field:'warnCount',title:'<fmt:message key='close.warncount'/>',width:80},
				          {field:'action',title:'<fmt:message key='common.action'/>',width:160,halign:'center',align:'center',
								formatter: function(value,row,index){
									return "<a href='javascript:void(0)' class='operateHref' onclick='warnSingle("+row.c_id+","+index+")'><fmt:message key='warnpay'/></a>";
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
				$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
			}
			if (low == "") {
				$.messager.alert('Info', '<fmt:message key='owe.lownull'/>');
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
				$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
			}
			if (low == "") {
				$.messager.alert('Info', '<fmt:message key='owe.lownull'/>');
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
			$('#warnallbtn').linkbutton('disable');
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
							$.messager.show({title:'Info',msg:'<fmt:message key='sending'/>'});
						}else{
							$.messager.alert('Info','<fmt:message key='common.selectcustomer'/>');
						}
						$('#warnallbtn').linkbutton('enable');
					}
				});
			}else{
				$.messager.alert('Info','<fmt:message key='common.selectcustomer'/>');
			}
		}
		var warnsingle_done = true;
		function warnSingle(cid,index){
			if(warnsingle_done){
				warnsingle_done = false;
				$.ajax({
					type:"POST",
					url:"${path}/charge/valve/warnsingle.do",
					dataType:"json",
					data:{
						c_id:cid
					},
					success:function(data){
						if(data.done == true){
							$.messager.alert('Info','<fmt:message key='sendok'/>');
						}else{
							$.messager.alert('Error','<fmt:message key='sendfail'/>');
						}
					}
				});
				warnsingle_done = true;
			}else{
				$.messager.show({
					title : 'Info',
					msg : '操作频繁，请稍后重试',
					showType : 'slide'
				});
			}
		}
	</script>
</body>
</html>