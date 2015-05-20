<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label>小区</label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto',onSelect:showMeterdata">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="readNeighbor()" >抄当前小区</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="readNeighbors()" >抄全部小区</a>
			</div>
		</form>
	</div>
	
	
	<table id="readmeterTab" style="width:100%;height:400px;"></table>
	<p>水损统计</p>
	<div>
		<label>水损原因</label>
		<input type="text" id="wastereason" name="wastereason" class="easyui-textbox" style="width:300px;"/>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addwaste()" >记入水损</a>
	</div>
	<table id="louwasteTab" style="width:500px;height:200px;"></table>
<script>
$(function(){
	$("#readmeterTab").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
// 		url:"${path}/readme/read/listread",
		queryParams:{
			
		},
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
// 		singleSelect:true,  
		rowStyler:function(index,row){
			
		},
		columns:[[
		          {field:'m_id',title:'ID',width:60,checkbox:true},
		          {field:'c_num',title:'用户号',width:80},
		          {field:'customerName',title:'用户名',width:80},
		          {field:'customerBalance',title:'余额',width:80,styler:function(value,row,index){
		        	  if(value <= 0){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'n_name',title:'小区',width:80},
		          {field:'g_addr',title:'集中器',width:80},
		          {field:'collectorAddr',title:'采集器',width:80},
		          {field:'meterAddr',title:'表地址',width:80},
		          {field:'meterState',title:'表状态',width:80},
		          {field:'valveState',title:'阀门状态',width:80,editor:'text'},
		          {field:'deread',title:'扣费读数',width:80},
		          {field:'readdata',title:'表读数',width:80},
		          {field:'readtime',title:'抄表时间',width:80,editor:'text'},
		          {field:'action',title:'操作',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.m_id;
							return "<a href='#' class='operateHref' onclick='openValve("+id+","+index+")'> 开/关阀 </a>"
							+"<a href='#' class='operateHref' onclick='readMeter("+id+","+index+")'> 抄表 </a>"
							+"<a href='#' class='operateHref' onclick='readMeterManual("+id+","+index+")'> 修改 </a>";
				  }}
		      ]]
	});
	
	
	$("#louwasteTab").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
// 		url:"${path}/readme/read/listread",
		queryParams:{
			
		},
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
// 		singleSelect:true,
		columns:[[
		          {field:'lou',title:'楼',width:60},
		          {field:'wasted',title:'已计入水损',width:80},
		          {field:'newwasted',title:'本次水损',width:80},
		          {field:'louread',title:'管理表读数',width:80},
		          {field:'sumread',title:'用户表和',width:80}
		      ]]
	});
});
function showMeterdata(){
	var n_id = $("#neighbor").combobox("getValue");
	if(n_id != ""){
		$('#readmeterTab').datagrid({
			url:"${path}/readme/read/listread.do",
			queryParams: {
				n_id:n_id
			}  
		});
	}
}
//水损统计只支持抄单个小区
//单击某个小区之后  抄表成功返回  设置当前抄表对应的readlog id
var readlogid = 0;
function readNeighbor(){
	var n_id = $("#neighbor").combobox("getValue");
	if(n_id != ""){
		$.ajax({
			type:"POST",
			url:"${path}/readme/read/readneighbor.do",
			dataType:"json",
			data:{
				n_id:n_id
			},
			success:function(data){
				if(data[0].pid > 0){
					
				}
			}
		});
	}
}
function readNeighbors(){
	$.ajax({
		type:"POST",
		url:"${path}/readme/read/readneighbors.do",
		dataType:"json",
		success:function(data){
			alert(data);
		}
	});
}
function openValve(mid,index){
	
}
function readMeter(mid,index){
	$.ajax({
		type:"POST",
		url:"${path}/readme/read/readmeter.do",
		dataType:"json",
		data:{
			m_id:mid
		},
		success:function(data){
			if(data[0].pid > 0){
				alert(data);
			}
		}
	});
}
function readMeterManual(mid,index){
	
}
</script>
</body>
</html>