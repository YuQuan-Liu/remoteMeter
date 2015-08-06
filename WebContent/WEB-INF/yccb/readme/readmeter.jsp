<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<c:set var="menus" scope="session" value="${userInfo.menus}"/>
<title>抄表</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<span style="width:400px;">
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200',onSelect:showMeterdata">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="readNeighbor()" ><fmt:message key='read.this'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="readNeighbors()" ><fmt:message key='read.all'/></a>
			</span>
			<span style="margin-left:200px;">
				<select class="easyui-combobox" id="export_frame" name="export_frame" style="width:200px;" data-options="panelHeight:'200'">
					<c:forEach var="e" items="${export_list }">
					<option value="${e.pid }">${e.exportName }</option>
					</c:forEach>
					<option value="0"><fmt:message key='read.exportdefault'/></option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton " onclick="exportsingle()" ><fmt:message key='read.exportthis'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton " onclick="exportall()" ><fmt:message key='read.exportall'/></a>
			</span>
		</form>
	</div>
	<form id="exportform" method="post">
		<input type="hidden" name="n_id" id="n_id"/> 
		<input type="hidden" name="n_name" id="n_name"/>
		<input type="hidden" name="export_id" id="export_id"/>
	</form>
	<table id="readmeterTab" style="width:100%;height:400px;"></table>
	<p>水损统计</p>
	<table id="louwasteTab" style="width:500px;height:200px;"></table>
<script>
var interval;
$(function(){
	$("#readprogress").hide();
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
		          {field:'c_num',title:'<fmt:message key='c.num'/>',width:80},
		          {field:'customerName',title:'<fmt:message key='c.name'/>',width:80},
		          {field:'customerBalance',title:'<fmt:message key='c.balance'/>',width:80,styler:function(value,row,index){
		        	  if(value <= 0){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'n_name',title:'<fmt:message key='common.neighborName'/>',width:80},
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
		          {field:'deread',title:'<fmt:message key='m.deread'/>',width:80},
		          {field:'readdata',title:'<fmt:message key='m.readdata'/>',width:80},
		          {field:'readtime',title:'<fmt:message key='m.readtime'/>',width:80,editor:'text'},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.m_id;
							var action = "<fmt:message key='m.open'/>";
							if(row.valveState == 1){
								action = "<fmt:message key='m.close'/>";
							}
							
							return "<a href='#' class='operateHref' onclick='readMeter("+id+","+index+")'><fmt:message key='read'/></a><c:if test="${menus['readvalve']=='t'}"><a href='#' class='operateHref' onclick='openValve("+id+","+index+")'>"+action+" </a></c:if><a href='#' class='operateHref' onclick='readMeterManual("+id+","+index+")'><fmt:message key='common.update'/></a>";
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
				  {field:'pid',title:'ID',width:60,hidden:true},
		          {field:'louNum',title:'<fmt:message key='lou'/>',width:60},
		          {field:'wasted',title:'<fmt:message key='read.wasted'/>',width:100},
		          {field:'waste',title:'<fmt:message key='read.waste'/>',width:80},
		          {field:'meterRead',title:'<fmt:message key='read.meterread'/>',width:100},
		          {field:'salveSum',title:'<fmt:message key='read.slavesum'/>',width:80},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='addwaste("+row.pid+","+index+")'><fmt:message key='read.addwaste'/></a>";
				  }}
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
				if(data.result == "success"){
					$.messager.progress({title:"<fmt:message key='read.reading'/>",text:"",interval:100});
					interval = setInterval(function(){checkreading(data.pid,-1);},1000);
				}else{
					$.messager.alert('Error','<fmt:message key='read.fail'/>');
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
			if(data.result == "success"){
				$.messager.progress({title:"<fmt:message key='read.reading'/>",text:"",interval:100});
				interval = setInterval(function(){checkreading(data.pid,-1);},1000);
			}else{
				$.messager.alert('Error','<fmt:message key='read.fail'/>');
			}
		}
	});
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
			if(data.result == "success"){
				$.messager.progress({title:"<fmt:message key='read.reading'/>",text:"",interval:100});
				interval = setInterval(function(){checkreading(data.pid,index);},1000);
			}else{
				$.messager.alert('Error','<fmt:message key='read.fail'/>');
			}
		}
	});
}
function readMeterManual(mid,index){
	$.messager.prompt('<fmt:message key='read.readchange'/>', '<fmt:message key='read.readright'/>', function(r){
        if (r){
			if(r > 0 && r < 9999){
				$.ajax({
					type:"POST",
					url:"${path}/readme/read/changeread.do",
					dataType:"json",
					data:{
						m_id:mid,
						m_read:r
					},
					success:function(data){
// 						alert(data.id+data.read);
						if(data.id > 0){
							$("#readmeterTab").datagrid('updateRow', {index:index,row:{readdata:data.read}});
						}
					}
				});
			}else{
				 $.messager.alert('Error','<fmt:message key='read.readerror'/>');
			}
        }
    });
}

function addwaste(wid,index){
	$.messager.prompt('<fmt:message key='read.addwaste'/>', '<fmt:message key='read.addwastereason'/>', function(r){
        if (r){
        	$.ajax({
				type:"POST",
				url:"${path}/readme/read/addwaste.do",
				dataType:"json",
				data:{
					wid:wid,
					reason:r
				},
				success:function(data){
//					alert(data.id+data.read);
// 					if(data.id > 0){
// 						$("#readmeterTab").datagrid('updateRow', {index:index,row:{readdata:data.read}});
// 					}
				}
			});
        }
    });
}

function checkreading(readlogid,index){
	$.ajax({
		type:"POST",
		url:"${path}/readme/read/checkreading.do",
		dataType:"json",
		data:{
			readlogid:readlogid
		},
		success:function(data){
			if(data.readStatus == 100){
				clearInterval(interval);
				$.messager.progress("close");
// 				$.messager.alert('Result',"<fmt:message key='result'/>:"+data.result+"</br><fmt:message key='failreason'/>:"+data.failReason,'info');
				$.messager.alert('Result',"<fmt:message key='result'/>:"+data.result,'info'); 
				if(data.readobject == 1){
					//单个小区  
					$('#readmeterTab').datagrid({
						url:"${path}/readme/read/listread.do",
						queryParams: {
							n_id:data.objectid
						}
					});
					//显示小区的水损统计
					showNeighborWaste(readlogid);
				}else{
					if(data.readobject == 3){
						//单个表
						$("#readmeterTab").datagrid('updateRow', {index:index,row:{readdata:data.read,readtime:data.time,meterState:data.status}});
					}else{
						//全部小区  donothing
					}
				}
				
			}
		}
	});
}

function showNeighborWaste(readlogid){
	$('#louwasteTab').datagrid({
		url:"${path}/readme/read/listwaste.do",
		queryParams: {
			readlogid:readlogid
		}
	});
}

function openValve(mid,index_){
	var valveState = $('#readmeterTab').datagrid('getRows')[index_]["valveState"];
	var control = 0;
	if(valveState == 1){
		control = 0;
	}else{
		control = 1;
	}
	$.ajax({
		type:"POST",
		url:"${path}/readme/valve/valvecontrol.do",
		dataType:"json",
		data:{
			m_id:mid,
			control:control
		},
		success:function(data){
			if(data.result == "success"){
				$.messager.progress({text:"",interval:100});
				interval = setInterval(function(){checkcontroling(data.pid,index_);},1000);
			}else{
				$.messager.alert('Error','<fmt:message key='read.valvefail'/>');
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
				$.messager.alert('Result',"<fmt:message key='read.valvefinished'/>:"+data.completecount+"</br><fmt:message key='read.valveerror'/>:"+data.errorcount,'info'); 
				
				if(data.completecount+data.errorcount == 1){
					//单个表
					$("#readmeterTab").datagrid('updateRow', {index:index,row:{valveState:data.switch_}});
				}
			}
		}
	});
}

function exportsingle(){
	var n_id = $("#neighbor").combobox("getValue");
	var n_name = $("#neighbor").combobox("getText");
	var export_id = $("#export_frame").combobox("getValue");
	
	if(n_id != ""){
		$("#n_id").val(n_id);
		$("#n_name").val(n_name);
		$("#export_id").val(export_id);
		
		$("#exportform").form('submit',{
			url:"${path}/readme/read/downloadsingle.do",
		});
	}else{
		$.messager.alert('Info','<fmt:message key='common.choosenei'/>');
	}
}
function exportall(){
	var export_id = $("#export_frame").combobox("getValue");
	$("#n_id").val(0);
	$("#n_name").val("all");
	$("#export_id").val(export_id);
	
	$("#exportform").form('submit',{
		url:"${path}/readme/read/downloadall.do",
	});
}
</script>
</body>
</html>