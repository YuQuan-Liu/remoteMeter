<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>楼宇统计</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto',onSelect:searchSettle">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<label><fmt:message key='settle'/></label>
				<select class="easyui-combobox" id="settlelog" name="settlelog" style="width:200px" data-options="panelHeight:'auto',valueField:'pid',textField:'startTime'">
					<option value=""><fmt:message key='selectsettlelog'/></option>
	    		</select>
	    		
	    		<label><fmt:message key='lou'/></label>
				<select class="easyui-combobox" id="lou" name="lou" style="width:200px" data-options="panelHeight:'auto',valueField:'id',textField:'lou'">
					<option value=""><fmt:message key='lou.selectlou'/></option>
	    		</select>
	    		
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
	<table id="delogTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;float:left;">
		<p><fmt:message key='settlelog.yl'/></p>
		<table id="ylTab" style="width:400px;height:200px;"></table>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#delogTab").datagrid({
				striped:true,
				fitColumns:true,
				method:'post',
//		 		url:"${path}/readme/read/listread",
				queryParams:{
					
				},
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
//		 		singleSelect:true,  
				rowStyler:function(index,row){
					
				},
				columns:[[
				          {field:'c_id',title:'ID',width:60,checkbox:true},
				          {field:'m_id',title:'MID',width:60,hidden:true},
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
				          {field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:80},
				          {field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:80},
				          {field:'steelNum',title:'<fmt:message key='m.steel'/>',width:80},
				          {field:'meterState',title:'<fmt:message key='m.mstate'/>',width:80,styler:function(value,row,index){
				        	  if(value != 1){
				        		  return 'background-color:#ffee00;color:red;';
				        	  }
				          },formatter:function(value,row,index){
								if(value == 1){
									return "<fmt:message key='m.mstateok'/>";
								}
								if(value == 2){
									return "<fmt:message key='m.mstateerror'/>";
								}
								if(value == 3){
									return "<fmt:message key='m.mstatebreak'/>";
								}
								if(value == 4){
									return "<fmt:message key='m.mstatetimeout'/>";
								}
								if(value == 5){
									return "<fmt:message key='m.mstatechange'/>";
								}
				          }},
				          {field:'pricekindname',title:'<fmt:message key='m.pk'/>',width:80},
				          {field:'lastderead',title:'<fmt:message key='m.deread'/>',width:80},
				          {field:'meterread',title:'<fmt:message key='m.readdata'/>',width:80},
				          {field:'changeend',title:'<fmt:message key='m.changeend'/>',width:80},
				          {field:'meterreadtime',title:'<fmt:message key='m.readtime'/>',width:80},
				          {field:'yl',title:'<fmt:message key='yl'/>',width:80,formatter:function(value,row,index){
				        	  if(row.changeend > 0){
				        		  return row.meterread+row.changeend-row.lastderead;
				        	  }else{
				        		  return row.meterread-row.lastderead;
				        	  }
				          }},
				          {field:'demoney',title:'<fmt:message key='demoney'/>',width:80},
				          {field:'printed',title:'printed',width:60,hidden:true},
				          {field:'payed',title:'payed',width:60,hidden:true}
				      ]]
			});
			$("#ylTab").datagrid({
				striped:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'pricekindname',title:'<fmt:message key='m.pk'/>',width:100},
				          {field:'yl',title:'<fmt:message key='yl'/>',width:100},
				          {field:'demoney',title:'<fmt:message key='demoney'/>',width:100}
				      ]]
			});
		});
		
		function search_(){
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var settle_id = $("#settlelog").combobox("getValue");
			var lou = $("#lou").combobox("getText");

			if (n_id == "") {
				$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
			}
			if (settle_id == "") {
				$.messager.alert('Info', '<fmt:message key='selectsettlelog'/>');
				return;
			}
			if (lou == "") {
				$.messager.alert('Info', '<fmt:message key='lou.selectlou'/>');
				return;
			}
			$('#delogTab').datagrid({
				url:"${path}/statistics/lou/listsettled.do",
				queryParams: {
					n_id:n_id,
					pre:pre,
					settle_id:settle_id,
					lou:lou
				}
			});
			
			$('#ylTab').datagrid({
				url:"${path}/statistics/lou/listsettledyl.do",
				queryParams: {
					n_id:n_id,
					pre:pre,
					settle_id:settle_id,
					lou:lou
				}
			});
		}
		
		function print() {
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var n_name = $("#neighbor").combobox("getText");
			var settle_id = $("#settlelog").combobox("getValue");
			var settle_time = $("#settlelog").combobox("getText");
			var lou = $("#lou").combobox("getText");

			if (n_id == "") {
				$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
			}
			if (settle_id == "") {
				$.messager.alert('Info', '<fmt:message key='selectsettlelog'/>');
				return;
			}
			if (lou == "") {
				$.messager.alert('Info', '<fmt:message key='lou.selectlou'/>');
				return;
			}
			
			window.open("${path}/statistics/lou/print.do?n_id=" + n_id
					+ "&settle_id=" + settle_id + "&n_name=" + n_name + "&settle_time=" + settle_time+ "&lou=" + lou
					+ "&pre=" + pre, "_blank");
		}
		function searchSettle(){
			var n_id = $("#neighbor").combobox("getValue");
			
			if(n_id != "" ){
				$('#settlelog').combobox('reload','${path}/charge/settle/listsettlelog.do?n_id='+n_id);
				$('#lou').combobox('reload','${path}/statistics/lou/listlou.do?n_id='+n_id);
			}
		}
	</script>
</body>
</html>