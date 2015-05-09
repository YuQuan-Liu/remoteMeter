<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="info.customer"/></title>
</head>
<body>
	<div style="margin:10px;">
		<form id="search_customer" method="post">
			<div>
				<label>小区</label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto'">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<div style="display:inline;padding:0 10px;">
		    		<label>用户号</label>
					<input type="text" id="c_num" name="c_num" class="easyui-textbox"/>
				</div>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchCustomer()" >Submit</a>
			</div>
		</form>
	</div>
	
	
	<table id="customerTab" style="width:100%;height:400px;"></table>
	<div id="addCustomerWin"></div>
	<div id="addCustomerWins"></div>
</body>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/datagrid-detailview.js"></script>
<script>
$(function(){
	$("#customerTab").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
// 		url:"${path}/infoin/customer/ListCustomer",
		queryParams:{
			
		},
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
// 		singleSelect:true,  
		rowStyler:function(index,row){
			
		},
		columns:[[
		          {field:'pid',title:'ID',width:60,checkbox:true},
// 				  {field:'n_name',title:'小区',width:80},
		          {field:'c_num',title:'用户号',width:80},
		          {field:'customerName',title:'用户名',width:80},
		          {field:'customerId',title:'用户ID',width:80},
		          {field:'apid',title:'关联ID',width:80},
		          {field:'customerMobile',title:'手机',width:80},
		          {field:'customerEmail',title:'邮箱',width:80},
		          {field:'nationalId',title:'身份证',width:80},
		          {field:'customerAddr',title:'地址',width:225},
		          {field:'hk',title:'用水类型',width:60,editor:'text'},
		          {field:'customerBalance',title:'余额',width:60,styler:function(value,row,index){
		        	  if(value <= 0){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'prePaySign',title:'预付费',width:60,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "预";
		        	  }else{
		        		  return "后";
		        	  }
		          }},
		          {field:'warnThre',title:'余额阀值',width:60},
		          {field:'warnStyle',title:'提醒方式',width:60,editor:'text',formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "预";
		        	  }else{
		        		  return "后";
		        	  }
		          }},
		          {field:'warnSwitch',title:'提醒开关',width:60,editor:'text',formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "预";
		        	  }else{
		        		  return "后";
		        	  }
		          }},
		          {field:'action',title:'操作',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.c_pid;
							return "<a href='#' class='operateHref' onclick='updateCustomer("+id+")'> 修改 </a>"
							+"<a href='#' class='operateHref' onclick='deleteCustomer("+id+")'> 删除 </a>";
				  }}
		      ]],
		      toolbar:[{
		    	  text: "添加用户",
		    	  iconCls: 'icon-add',
		    	  handler: addCustomer
		      },'-',{
		    	  text: "批量添加",
		    	  iconCls:'icon-add',
		    	  handler: addCustomers
		      }],
		      view: detailview,
	          detailFormatter:function(index,row){
	              return '<div style="padding:2px"><table class="ddv"></table></div>';
	          },
	          onExpandRow: function(index,row){
	              var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
	              ddv.datagrid({
	                  url:'${path}/infoin/customer/ListMetersByCid.do',
	                  queryParams:{
	                	  "cpid":row.pid,
	                  },
	                  fitColumns:true,
	                  singleSelect:true,
	                  rownumbers:true,
	                  loadMsg:'',
	                  height:'auto',
	                  columns:[[
							{field:'pid',title:'ID',width:60},
							{field:'gprs',title:'集中器',width:60},
	                      	{field:'qfh',title:'铅封号',width:60},
				          	{field:'steelNum',title:'钢印号',width:60},
				          	{field:'suppleMode',title:'供水方式',width:60},
				          	{field:'collectorAddr',title:'采集器地址',width:60},
				          	{field:'meterAddr',title:'表地址',width:60},
				          	{field:'meterSolid',title:'虚实表',width:60},
				          	{field:'mk',title:'表类型',width:60},
				          	{field:'pk',title:'单价',width:60},
				          	{field:'isValve',title:'阀门',width:60},
				          	{field:'deductionStyle',title:'结算方式',width:50},
				          	{field:'valveOffthre',title:'关阀余额',width:50},
				          	{field:'timerSwitch',title:'定时检测',width:50},
				          	{field:'timer',title:'定时时间',width:50},
				          	{field:'overflow',title:'用量阀值',width:50},
				          	{field:'changend',title:'换表读数',width:50},
				          	{field:'changestart',title:'起始读数',width:50},
				          	{field:'action',title:'操作',width:90,formatter: function(value,row,index){
								var id = row.m_pid;
								return "<a href='#' class='operateHref' onclick='updateMeter("+id+")'> 修改 </a>"
								+"<a href='#' class='operateHref' onclick='deleteMeter("+id+")'> 删除 </a>"
								+"<a href='#' class='operateHref' onclick='changemeter("+id+")'> 换表 </a>";
					  		}}
				          	
	                  ]],
	                  onResize:function(){
	                      $('#customerTab').datagrid('fixDetailRowHeight',index);
	                  },
	                  onLoadSuccess:function(){
	                      setTimeout(function(){
	                          $('#customerTab').datagrid('fixDetailRowHeight',index);
	                      },0);
	                  }
	              });
	              $('#customerTab').datagrid('fixDetailRowHeight',index);
	          }
	});
});
function check_c_num(c_num){
	if(c_num.trim() != ''){
		var ldh = c_num.split(/[ ,.-]/);
		var len = ldh.length;
		if(len == 3 || len == 1){
			//这个地方还可以加上判断是不是全是数字
			return true;
		}else{
			return false;
		}
	}
	return true;
}
function searchCustomer(){
	var n_id = $("#neighbor").combobox("getValue");
	var c_num = $("#c_num").val();
	
	if(n_id == ''){
		$.messager.alert('提示','请选择小区！');
		return;
	}
	if(check_c_num(c_num)){
		$('#customerTab').datagrid({
			url:"${path}/infoin/customer/ListCustomer.do",
			queryParams: {
				n_id:n_id,  		
				c_num:c_num
			}  
		});
	}
}
function updateCustomer(){
	alert("haha");
}
function addCustomer(){
	$('#addCustomerWin').window({	
		href:'${path}/infoin/customer/addPage.do',
		width:800,	
		height:500,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		title: '添加用户'
	}); 
}
function addCustomers(){
	$('#addCustomerWins').window({	
		href:'${path}/infoin/customer/addPages.do',
		width:467,	
		height:300,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		title: '批量添加用户'
	});
}
function deleteCustomer(){
	
}
function updateMeter(){
	
}
function deleteMeter(){
	
}
function changemeter(){
	
}
</script>
</html>