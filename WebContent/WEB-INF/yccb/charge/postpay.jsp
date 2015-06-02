<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后付费</title>
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
	    			<a href="javascript:void(0)" class="easyui-linkbutton" id="settleall" onclick="printAll()" >全部打印</a>
	    		</span>
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chargePost()">交费</a>
	    		</span>
			</div>
		</form>
	</div>
	
	<table id="postpayTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;background-color:#ffee00;">
		<p>备注：选择框背景色为黄色未交费</p>
	</div>
	<div style="margin:10px;">
		<p>用水量</p>
	</div>
	<table id="ylTab" style="width:400px;height:200px;"></table>
<script>
$(function(){
	$("#postpayTab").datagrid({
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
		          {field:'printed',title:"打印",width:60,halign:'center',align:'center',formatter:function(value,row,index){
		        	  return "<a href='#' class='operateHref' onclick='printSingle("+row.mdl_id+","+index+")'>打印</a>";
			      }},		          
		          {field:'payed',title:"交费",width:30,checkbox:true,styler:function(value,row,index){
		        	  if(row.payed == 0){
		        		  return 'background-color:#ffee00;';
		        	  }
		          }},
		          {field:'c_id',title:'ID',width:60,hidden:true},
		          {field:'m_id',title:'MID',width:60,hidden:true},
		          {field:'mdl_id',title:'MID',width:60,hidden:true},
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
		          {field:'lastderead',title:'扣费读数',width:80},
		          {field:'meterread',title:'表读数',width:80},
		          {field:'changeend',title:'换表底数',width:80},
		          {field:'meterreadtime',title:'抄表时间',width:80},
		          {field:'yl',title:'用量',width:80,formatter:function(value,row,index){
						return row.meterread-row.lastderead;
		          }},
		          {field:'demoney',title:'扣费金额',width:80}
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
});

function searchSettle(){
	var n_id = $("#neighbor").combobox("getValue");
	
	if(n_id != "" ){
		$('#settlelog').combobox('reload','${path}/charge/settle/listsettlelog.do?n_id='+n_id);
	}
}

function searchCustomer(){
	var n_id = $("#neighbor").combobox("getValue");
	var settle_id = $("#settlelog").combobox("getValue");
	$('#postpayTab').datagrid({
		url:"${path}/charge/postpay/listpostpay.do",
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
			pre:0
		}
	});
}

function chargePost(){
	var mdl_ids = [];
	var rows = $('#postpayTab').datagrid('getSelections');
	
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		if(row.payed == 0){
			mdl_ids.push(row.mdl_id);
		}
	}
	if(mdl_ids.length != 0){
		$.ajax({
			type:"POST",
			url:"${path}/charge/postpay/chargepostpay.do",
			dataType:"json",  
	        traditional :true,
			data:{
				'mdl_ids':mdl_ids
			},
			success:function(data){
				if(data.done == true){
					searchCustomer();
				}else{
					//;
				}
			}
		});
	}else{
		$.messager.alert('Info','请选择记录');
	}
}

function printAll(){
	var mdl_ids = [];
	var rows = $('#postpayTab').datagrid('getSelections');
	
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		mdl_ids.push(row.mdl_id);
	}
	
	var len = mdl_ids.length;
	var ids = mdl_ids.join(",");
	
	if(len != 0){
		window.open("${path}/charge/postpay/printcharge.do?ids="+ids,"_blank");
// 		$.ajax({
// 			type:"POST",
// 			url:"${path}/charge/postpay/printcharge.do",
// 			dataType:"json",  
// 	        traditional :true,
// 			data:{
// 				'mdl_ids':mdl_ids
// 			},
// 			success:function(data){
				
// 			}
// 		});
	}else{
		$.messager.alert('Info','请选择记录');
	}
}

function printSingle(mdl_id,index){
	window.open("${path}/charge/postpay/printcharge.do?ids="+mdl_id,"_blank");
}

</script>
</body>
</html>