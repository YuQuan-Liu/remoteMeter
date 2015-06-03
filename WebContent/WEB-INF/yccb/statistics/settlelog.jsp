<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>扣费统计</title>
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
	    		
	    		<label>结算</label>
				<select class="easyui-combobox" id="settlelog" name="settlelog" style="width:200px" data-options="panelHeight:'auto',valueField:'pid',textField:'startTime'">
					<option value="">请选择结算</option>
	    		</select>
	    		
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
	<table id="delogTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;float:left;">
		<p>水量统计</p>
		<table id="ylTab" style="width:400px;height:200px;"></table>
	</div>
	<div style="margin:10px;float:left;">
		<p>小区金额</p>
		<table id="nbalanceTab" style="width:400px;height:200px;"></table>
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
				          {field:'collectorAddr',title:'采集器',width:80},
				          {field:'meterAddr',title:'表地址',width:80},
				          {field:'steelNum',title:'钢印号',width:80},
				          {field:'meterState',title:'表状态',width:80,formatter:function(value,row,index){
								if(value == 1){
									return "正常";
								}
								if(value == 2){
									return "数据错误";
								}
								if(value == 3){
									return "线路故障";
								}
								if(value == 4){
									return "超时";
								}
								if(value == 5){
									return "人工修改";
								}
				          }},
				          {field:'pricekindname',title:'扣费单价',width:80},
				          {field:'lastderead',title:'上次读数',width:80},
				          {field:'meterread',title:'本次读数',width:80},
				          {field:'changeend',title:'换表底数',width:80},
				          {field:'meterreadtime',title:'抄表时间',width:80},
				          {field:'yl',title:'用量',width:80,formatter:function(value,row,index){
				        	  if(row.changeend > 0){
				        		  return row.meterread+row.changeend-row.lastderead;
				        	  }else{
				        		  return row.meterread-row.lastderead;
				        	  }
				          }},
				          {field:'demoney',title:'扣费金额',width:80},
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
				          {field:'pricekindname',title:'单价',width:100},
				          {field:'yl',title:'用水量',width:100},
				          {field:'demoney',title:'金额',width:100}
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
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var settle_id = $("#settlelog").combobox("getValue");

			if (n_id == "") {
				$.messager.alert('Info', '请选择小区！');
				return;
			}
			if (settle_id == "") {
				$.messager.alert('Info', '请选择结算！');
				return;
			}
			$('#delogTab').datagrid({
				url:"${path}/statistics/settlelog/listsettled.do",
				queryParams: {
					n_id:n_id,
					pre:pre,
					settle_id:settle_id
				}
			});
			
			$('#ylTab').datagrid({
				url:"${path}/statistics/settlelog/listsettledyl.do",
				queryParams: {
					n_id:n_id,
					pre:pre,
					settle_id:settle_id
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
			var pre = $("#pre").combobox("getValue");
			var n_id = $("#neighbor").combobox("getValue");
			var n_name = $("#neighbor").combobox("getText");
			var settle_id = $("#settlelog").combobox("getValue");
			var settle_time = $("#settlelog").combobox("getText");

			if (n_id == "") {
				$.messager.alert('Info', '请选择小区！');
				return;
			}
			if (settle_id == "") {
				$.messager.alert('Info', '请选择结算！');
				return;
			}
			window.open("${path}/statistics/settlelog/print.do?n_id=" + n_id
					+ "&settle_id=" + settle_id + "&n_name=" + n_name + "&settle_time=" + settle_time
					+ "&pre=" + pre, "_blank");
		}
		function searchSettle(){
			var n_id = $("#neighbor").combobox("getValue");
			
			if(n_id != "" ){
				$('#settlelog').combobox('reload','${path}/charge/settle/listsettlelog.do?n_id='+n_id);
			}
		}
	</script>
</body>
</html>