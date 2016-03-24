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
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200',onSelect:showMeterdata">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeValveAll()" id="closevalveallbtn"><fmt:message key='m.close'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="warnAll()" id="warnallbtn"><fmt:message key='warnpay'/></a>
			</div>
		</form>
		<form id="exportform" method="post">
			<input type="hidden" name="n_id" id="n_id"/> 
			<input type="hidden" name="n_name" id="n_name"/>
		</form>
	</div>
	
	<table id="controlTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;">
		<label><fmt:message key='close.log'/></label>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="printControlError()" ><fmt:message key='close.export'/></a>
	</div>
	<table id="controlErrorTab" style="width:100%;height:200px;"></table>
<script>
var intervalbar;
var interval;
$(function(){
	$("#controlprogress").hide();
	$("#controlTab").datagrid({
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
		          {field:'warnStyle',title:'<fmt:message key='c.warnstyle'/>',width:80,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='c.sms'/>";
		        	  }else{
		        		  return "<fmt:message key='c.email'/>";
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
						return "<fmt:message key='common.exception'/>";
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
		          {field:'warnCount',title:'<fmt:message key='close.warncount'/>',width:80},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:160,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='closeValveSingle("+row.m_id+","+index+")'><fmt:message key='m.close'/></a>"
							+"<a href='#' class='operateHref' onclick='warnSingle("+row.c_id+","+index+")'><fmt:message key='warnpay'/></a>";
				  }}
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
						return "<fmt:message key='common.exception'/>";
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
function showMeterdata(){
	var n_id = $("#neighbor").combobox("getValue");
	if(n_id != ""){
		$('#controlTab').datagrid({
			url:"${path}/charge/valve/listcontrol.do",
			queryParams: {
				n_id:n_id
			}
		});
		$('#controlErrorTab').datagrid({
			url:"${path}/charge/valve/listerror.do",
			queryParams: {
				n_id:n_id
			}  
		});
	}
}

function warnAll(){
	$('#warnallbtn').linkbutton('disable');
	var c_ids = [];
	var rows = $('#controlTab').datagrid('getSelections');
	
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

var closevalve_done = true;
function warnSingle(cid,index){
	if(closevalve_done){
		closevalve_done = false;
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
		closevalve_done = true;
	}else{
		$.messager.show({
			title : 'Info',
			msg : '操作频繁，请稍后重试',
			showType : 'slide'
		});
	}
}

function closeValveAll(){
	$('#closevalveallbtn').linkbutton('disable');
	var m_ids = [];
	var rows = $('#controlTab').datagrid('getSelections');
	
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		m_ids.push(row.m_id);
	}
	if(m_ids.length != 0){
		$.ajax({
			type:"POST",
			url:"${path}/readme/valve/valvecontrolall.do",
			dataType:"json",  
	        traditional :true,
			data:{
				'm_ids':m_ids
			},
			success:function(data){
				if(data.result == "success"){
					$.messager.progress({title:"",text:"",interval:100});
					interval = setInterval(function(){checkcontroling(data.pid,-1);},1000);
				}else{
					$.messager.alert('Error','<fmt:message key='read.valvefail'/>');
				}
				$('#closevalveallbtn').linkbutton('enable');
			}
		});
	}else{
		$.messager.alert('Info','<fmt:message key='common.selectcustomer'/>');
	}
	
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

function closeValveSingle(mid,index){
	if(closevalve_done){
		closevalve_done = false;
		$.ajax({
			type:"POST",
			url:"${path}/readme/valve/valvecontrol.do",
			dataType:"json",
			data:{
				m_id:mid,
				control:0
			},
			success:function(data){
				if(data.result == "success"){
					$.messager.progress({title:"",text:"",interval:100});
					interval = setInterval(function(){checkcontroling(data.pid,index);},1000);
				}else{
					$.messager.alert('Error','<fmt:message key='read.valvefail'/>');
				}
			}
		});
		closevalve_done = true;
	}else{
		$.messager.show({
			title : 'Info',
			msg : '操作频繁，请稍后重试',
			showType : 'slide'
		});
	}
}
function checkcontroling(valvelogid,index){
	$.ajax({
		type:"POST",
		url:"${path}/readme/valve/checkcontroling.do",
		dataType:"json",
		data:{
			valvelogid:valvelogid
		},
		success:function(data){
			if(data.status == 100){
				clearInterval(interval);
				$.messager.progress("close");
				$.messager.alert('Result',"<fmt:message key='read.valvefinished'/>:"+data.completecount+"</br><fmt:message key='read.valveerror'/>:"+data.errorcount,'info'); 
				
				if(data.completecount+data.errorcount == 1){
					//单个表
					$("#controlTab").datagrid('updateRow', {index:index,row:{valveState:data.switch_}});
				}else{
					showMeterdata();
				}
			}
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