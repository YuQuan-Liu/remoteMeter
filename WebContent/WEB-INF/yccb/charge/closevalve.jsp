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
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeValveAll()" >关阀</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="warnAll()" >提醒交费</a>
			</div>
		</form>
		<form id="exportform" method="post">
			<input type="hidden" name="n_id" id="n_id"/> 
			<input type="hidden" name="n_name" id="n_name"/>
		</form>
	</div>
	
	<table id="controlTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;">
		<label>阀门开关异常日志</label>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="printControlError()" >导出日志</a>
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
		          {field:'warnStyle',title:'提醒方式',width:80,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "短信";
		        	  }else{
		        		  return "邮件";
		        	  }
		          }},
		          {field:'g_addr',title:'集中器',width:80},
		          {field:'collectorAddr',title:'采集器',width:80},
		          {field:'meterAddr',title:'表地址',width:80},
		          {field:'meterState',title:'表状态',width:80,styler:function(value,row,index){
		        	  if(value != 1){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          },formatter:function(value,row,index){
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
		          {field:'warnCount',title:'提醒次数',width:80},
		          {field:'action',title:'操作',width:160,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='closeValveSingle("+row.m_id+","+index+")'>关阀</a>"
							+"<a href='#' class='operateHref' onclick='warnSingle("+row.c_id+","+index+")'>提醒交费</a>";
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
		          {field:'meterState',title:'表状态',width:80,styler:function(value,row,index){
		        	  if(value != 1){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          },formatter:function(value,row,index){
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
					$.messager.show({title:'Info',msg:'服务器正努力发送中...'});
				}else{
					$.messager.alert('Info','请选择用户');
				}
			}
		});
	}else{
		$.messager.alert('Info','请选择用户');
	}
}

function warnSingle(cid,index){
	$.ajax({
		type:"POST",
		url:"${path}/charge/valve/warnsingle.do",
		dataType:"json",
		data:{
			c_id:cid
		},
		success:function(data){
			if(data.done == true){
				$.messager.alert('Info','信息已发送');
			}else{
				$.messager.alert('Error','信息发送失败');
			}
		}
	});
}

function closeValveAll(){
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
					$.messager.progress({title:"操作中...",text:"",interval:100});
					interval = setInterval(function(){checkcontroling(data.pid,-1);},1000);
				}else{
					$.messager.alert('Error','操作失败,请稍后再试');
				}
			}
		});
	}else{
		$.messager.alert('Info','请选择用户');
	}
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

function closeValveSingle(mid,index){
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
				$.messager.progress({title:"操作中...",text:"",interval:100});
				interval = setInterval(function(){checkcontroling(data.pid,index);},1000);
			}else{
				$.messager.alert('Error','操作失败,请稍后再试');
			}
		}
	});
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
				$.messager.alert('Result',"完成个数:"+data.completecount+"</br>异常个数:"+data.errorcount,'info'); 
				
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
		$.messager.alert('Info','请选择小区');
	}
}
</script>
</body>
</html>