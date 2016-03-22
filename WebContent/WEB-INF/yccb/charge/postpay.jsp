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
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200',onSelect:searchSettle">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<select class="easyui-combobox" id="settlelog" name="settlelog" style="width:200px" data-options="panelHeight:'200',valueField:'pid',textField:'startTime'">
					<option value=""><fmt:message key='selectsettlelog'/></option>
	    		</select>
	    		
	    		<label><fmt:message key='lou'/></label>
				<select class="easyui-combobox" id="lou" name="lou" style="width:200px" data-options="panelHeight:'200',valueField:'id',textField:'lou'">
					<option value=""><fmt:message key='lou.selectlou'/></option>
	    		</select>
	    		
	    		<span style="margin-left:20px;">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" id="settleall" onclick="searchCustomer()" ><fmt:message key='search'/></a>
	    		</span>
	    		
	    		<span style="margin-left:20px;">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" id="settleall" onclick="printAll()" ><fmt:message key='allprint'/></a>
	    		</span>
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chargePost()"><fmt:message key='charge.pay'/></a>
	    		</span>
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="sendMessage()"><fmt:message key='warnpay'/></a>
	    		</span>
			</div>
		</form>
	</div>
	
	<table id="postpayTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;background-color:#ffee00;">
		<p><fmt:message key='postpay.remark'/></p>
	</div>
<!-- 	<div style="margin:10px;"> -->
<%-- 		<p><fmt:message key='yl'/></p> --%>
<!-- 	</div> -->
<!-- 	<table id="ylTab" style="width:400px;height:200px;"></table> -->
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
		          {field:'printed',title:"<fmt:message key='print'/>",width:60,halign:'center',align:'center',formatter:function(value,row,index){
		        	  return "<a href='#' class='operateHref' onclick='printSingle("+row.mdl_id+","+index+")'><fmt:message key='print'/></a>";
			      }},		          
		          {field:'payed',title:"<fmt:message key='charge.pay'/>",width:30,checkbox:true,styler:function(value,row,index){
		        	  if(row.payed == 0){
		        		  return 'background-color:#ffee00;';
		        	  }
		          }},
		          {field:'c_id',title:'ID',width:60,hidden:true},
		          {field:'m_id',title:'MID',width:60,hidden:true},
		          {field:'mdl_id',title:'MDLID',width:60,hidden:true},
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
						return "<fmt:message key='common.exception'/>";
		          }},
		          {field:'pricekindname',title:'<fmt:message key='m.pk'/>',width:80},
		          {field:'lastderead',title:'<fmt:message key='m.deread'/>',width:80},
		          {field:'destartread',title:'<fmt:message key='m.destartread'/>',width:80},
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
		          {field:'formula',title:'阶梯',width:80},
				  {field:'minusderead',title:'减免',width:80},
				  {field:'tovirtual',title:'转到虚表',width:80},
		          {field:'demoney',title:'<fmt:message key='demoney'/>',width:80}
		      ]]
	});
// 	$("#ylTab").datagrid({
// 		striped:true,
// 		method:'post',
// 		loadMsg:'<fmt:message key="main.loading"/>',
// 		rownumbers:true,
// 		columns:[[
// 		          {field:'pricekindname',title:'<fmt:message key='m.pk'/>',width:100},
// 		          {field:'yl',title:'<fmt:message key='yl'/>',width:100},
// 		          {field:'demoney',title:'<fmt:message key='demoney'/>',width:100}
// 		      ]]
// 	});
});

function searchSettle(){
	var n_id = $("#neighbor").combobox("getValue");
	
	if(n_id != "" ){
		$('#settlelog').combobox('reload','${path}/charge/settle/listsettlelog.do?n_id='+n_id);
		$('#lou').combobox('reload','${path}/statistics/lou/listlou.do?n_id='+n_id);
	}
}

function searchCustomer(){
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
	
	$('#postpayTab').datagrid({
		url:"${path}/charge/postpay/listpostpay.do",
		queryParams: {
			n_id:n_id,  		
			settle_id:settle_id,
			lou:lou
		}
	});

	
// 	$('#ylTab').datagrid({
// 		url:"${path}/charge/settle/settleallyl.do",
// 		queryParams: {
// 			n_id:n_id,  		
// 			settle_id:settle_id,
// 			lou:lou,
// 			pre:0
// 		}
// 	});
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
		$.messager.alert('Info','<fmt:message key='common.chooserecord'/>');
	}
}

function sendMessage(){
	var mdl_ids = [];
	var rows = $('#postpayTab').datagrid('getSelections');
	
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		mdl_ids.push(row.mdl_id);
	}
	if(mdl_ids.length != 0){
		$.ajax({
			type:"POST",
			url:"${path}/charge/postpay/warnpostpay.do",
			dataType:"json",  
	        traditional :true,
			data:{
				'mdl_ids':mdl_ids
			},
			success:function(data){
				if(data.done == true){
					$.messager.show({title:'Info',msg:'<fmt:message key='sending'/>'});
				}else{
					//;
				}
			}
		});
	}else{
		$.messager.alert('Info','<fmt:message key='common.chooserecord'/>');
	}
}

function printAll(){
// 	var mdl_ids = [];
// 	var rows = $('#postpayTab').datagrid('getSelections');
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
	
	window.open("${path}/charge/postpay/printchargeall.do?n_id="+n_id+"&settle_id="+settle_id+"&lou="+lou,"_blank");
	
// 	for(var i=0; i<rows.length; i++){
// 		var row = rows[i];
// 		mdl_ids.push(row.mdl_id);
// 	}
	
// 	var len = mdl_ids.length;
// 	var ids = mdl_ids.join(",");
	
// 	if(len != 0){
// 		window.open("${path}/charge/postpay/printcharge.do?ids="+ids,"_blank");
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
// 	}else{
// 		$.messager.alert('Info','<fmt:message key='common.chooserecord'/>');
// 	}
}

function printSingle(mdl_id,index){
	window.open("${path}/charge/postpay/printcharge.do?ids="+mdl_id,"_blank");
}

</script>
</body>
</html>