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
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto',onSelect:searchSettle">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<select class="easyui-combobox" id="settlelog" name="settlelog" style="width:200px" data-options="panelHeight:'auto',valueField:'pid',textField:'startTime',onSelect:searchCustomer">
					<option value=""><fmt:message key='selectsettlelog'/></option>
	    		</select>
	    		
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="printstatistic()"><fmt:message key='printdestatistic'/></a>
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
		<p><fmt:message key='yl'/></p>
	</div>
	<table id="ylTab" style="width:400px;height:200px;"></table>
	<div style="margin:10px;">
		<label><fmt:message key='close.log'/></label>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="printControlError()" ><fmt:message key='close.export'/></a>
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
		          {field:'c_num',title:'<fmt:message key='c.num'/>',width:80},
		          {field:'customerName',title:'<fmt:message key='c.name'/>',width:80},
		          {field:'customerAddr',title:'<fmt:message key='common.addr'/>',width:80},
		          {field:'customerMobile',title:'<fmt:message key='common.mobile'/>',width:80},
		          {field:'customerBalance',title:'<fmt:message key='c.balance'/>',width:80,styler:function(value,row,index){
		        	  if(value <= row.warnThre){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'g_addr',title:'<fmt:message key='gprs'/>',width:80},
		          {field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:80},
		          {field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:80},
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
		          {field:'valveState',title:'<fmt:message key='m.vstate'/>',width:80,editor:'text',formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='common.open'/>";
		        	  }else{
		        		  if(value == 0){
		        			  return "<fmt:message key='common.close'/>";
		        		  }else{
		        			  return "<fmt:message key='common.exception'/>";
		        		  }
		        		 
		        	  }
		          }},
		          {field:'switch_',title:'<fmt:message key='close.valvecontrol'/>',width:80},
		          {field:'errorReason',title:'<fmt:message key='close.reason'/>',width:80},
		          {field:'completeTime',title:'<fmt:message key='close.actiontime'/>',width:80},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:80,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='resolveError("+row.conf_id+","+index+")'><fmt:message key='close.resolveerror'/></a>";
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
	$.messager.prompt('<fmt:message key='close.resolveerror'/>', '<fmt:message key='close.reason'/>', function(r){
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
		$.messager.alert('Info','<fmt:message key='common.choosenei'/>');
	}
}
</script>
</body>
</html>