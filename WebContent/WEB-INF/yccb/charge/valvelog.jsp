<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>阀控日志</title>
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
	    		
	    		<select class="easyui-combobox" id="settlelog" name="settlelog" style="width:200px" data-options="panelHeight:'auto',valueField:'pid',textField:'startTime',onSelect:searchCustomer">
					<option value="">请选择结算</option>
	    		</select>
	    		
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="printstatistic()">打印扣费统计</a>
	    		</span>
			</div>
		</form>
		<form id="exportform" method="post">
			<input type="hidden" name="n_id" id="n_id"/> 
			<input type="hidden" name="n_name" id="n_name"/>
		</form>
	</div>
	
	<table id="valvelogTab" style="width:100%;height:400px;"></table>
<!-- 	<div style="margin:10px;"> -->
<!-- 		<p>总用水量：</p><p id="allYL"></p> -->
<!-- 	</div> -->
	<div style="margin:10px;">
		<p>用水量</p>
	</div>
	<table id="ylTab" style="width:400px;height:200px;"></table>
	<div style="margin:10px;">
		<label>阀门开关异常日志</label>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="printControlError()" >导出日志</a>
	</div>
	<table id="controlErrorTab" style="width:100%;height:200px;"></table>
<script>
$(function(){
	$("#valvelogTab").datagrid({
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
		          {field:'meterreadtime',title:'抄表时间',width:80},
		          {field:'yl',title:'用量',width:80,formatter:function(value,row,index){
						return row.meterread-row.lastderead;
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
		          {field:'demoney',title:'用水量',width:100}
		      ]]
	});
	$("#controlErrorTab").datagrid({
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
		          {field:'m_id',title:'ID',width:60,checkbox:true},
		          {field:'c_id',title:'CID',width:60,hidden:true},
		          {field:'c_num',title:'用户号',width:80},
		          {field:'customerName',title:'用户名',width:80},
		          {field:'customerAddr',title:'地址',width:80},
		          {field:'customerMobile',title:'手机',width:80},
		          {field:'customerBalance',title:'余额',width:80,styler:function(value,row,index){
		        	  if(value <= row.warnThre){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'g_addr',title:'集中器',width:80},
		          {field:'collectorAddr',title:'采集器',width:80},
		          {field:'meterAddr',title:'表地址',width:80},
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
		          {field:'valveState',title:'阀门状态',width:80,editor:'text',formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "开";
		        	  }else{
		        		  if(value == 0){
		        			  return "关";
		        		  }else{
		        			  return "异常";
		        		  }
		        	  }
		          }},
		          {field:'switch_',title:'阀门操作',width:80},
		          {field:'errorReason',title:'异常原因',width:80},
		          {field:'completeTime',title:'操作时间',width:80},
		          {field:'action',title:'操作',width:80,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='resolveError("+row.conf_id+","+index+")'>解除异常</a>";
				  }}
		      ]]
	});
});

function searchSettle(){
	var n_id = $("#neighbor").combobox("getValue");
	
	if(n_id != "" ){
		$('#settlelog').combobox('reload','${path}/charge/settle/listsettlelog_auto.do?n_id='+n_id);
		
		$('#controlErrorTab').datagrid({
			url:"${path}/charge/valve/listerror.do",
			queryParams: {
				n_id:n_id
			}  
		});
	}
}


function searchCustomer(){
	var n_id = $("#neighbor").combobox("getValue");
	var settle_id = $("#settlelog").combobox("getValue");
	$('#valvelogTab').datagrid({
		url:"${path}/charge/valvelog/listsettleauto.do",
		queryParams: {
			n_id:n_id,  		
			settle_id:settle_id
		}
	});
	
	$('#ylTab').datagrid({
		url:"${path}/charge/settle/settleallyl.do",
		queryParams: {
			n_id:n_id,  		
			settle_id:settle_id,
			pre:1
		}
	});
}

function resolveError(conf_id,index_){
	$.messager.prompt('解除阀控异常', '请输入异常原因', function(r){
        if (r){
        	$.ajax({
				type:"POST",
				url:"${path}/charge/valve/resolveError.do",
				dataType:"json",
				data:{
					conf_id:conf_id,
					reason:r
				},
				success:function(data){
					if(data.pid > 0 && data.errorStatus == 1){
						$("#controlErrorTab").datagrid("deleteRow", index_);
					}
				}
			});
        }
    });
}

function printControlError(){
	var n_id = $("#neighbor").combobox("getValue");
	var n_name = $("#neighbor").combobox("getText");
	
	if(n_id != ""){
		$("#n_id").val(n_id);
		$("#n_name").val(n_name);
		
		$("#exportform").form('submit',{
			url:"${path}/charge/valve/downloaderror.do",
		});
	}else{
		$.messager.alert('Info','请选择小区');
	}
}
</script>
</body>
</html>