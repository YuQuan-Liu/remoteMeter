<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>抄表</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<span style="width:400px;">
				<label>小区</label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto',onSelect:showMeterdata">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="readNeighbor()" >抄当前小区</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="readNeighbors()" >抄全部小区</a>
			</span>
			<span style="margin-left:200px;">
				<select class="easyui-combobox" id="export_frame" name="export_frame" style="width:200px;" data-options="panelHeight:'auto',onSelect:showMeterdata">
					<option value="">请选择导出格式</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="export()" >导出</a>
			</span>
		</form>
	</div>
	
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
		          {field:'deread',title:'扣费读数',width:80},
		          {field:'readdata',title:'表读数',width:80},
		          {field:'readtime',title:'抄表时间',width:80,editor:'text'},
		          {field:'action',title:'操作',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.m_id;
							var action = "开阀";
							if(row.valveState == 1){
								action = "关阀";
							}
							return "<a href='#' class='operateHref' onclick='openValve("+id+","+index+")'>"+action+" </a>"
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
				  {field:'pid',title:'ID',width:60,hidden:true},
		          {field:'louNum',title:'楼',width:60},
		          {field:'wasted',title:'已计入水损',width:100},
		          {field:'waste',title:'本次水损',width:80},
		          {field:'meterRead',title:'管理表读数',width:100},
		          {field:'salveSum',title:'用户表和',width:80},
		          {field:'action',title:'操作',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='addwaste("+row.pid+","+index+")'>记入水损</a>";
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
					$.messager.progress({title:"抄表中...",text:"",interval:100});
					interval = setInterval(function(){checkreading(data.pid,-1);},1000);
				}else{
					$.messager.alert('Error','抄表失败,请稍后再试');
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
				$.messager.progress({title:"抄表中...",text:"",interval:100});
				interval = setInterval(function(){checkreading(data.pid,-1);},1000);
			}else{
				$.messager.alert('Error','抄表失败,请稍后再试');
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
				$.messager.progress({title:"抄表中...",text:"",interval:100});
				interval = setInterval(function(){checkreading(data.pid,index);},1000);
			}else{
				$.messager.alert('Error','抄表失败,请稍后再试');
			}
		}
	});
}
function readMeterManual(mid,index){
	$.messager.prompt('修改表读数', '请输入正确的表读数', function(r){
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
				 $.messager.alert('Error','表数错误：'+r+',请重新输入');
			}
        }
    });
}

function addwaste(wid,index){
	$.messager.prompt('记入水损', '请输入水损原因', function(r){
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
				$.messager.alert('抄表结果',"结果:"+data.result+"\r\n失败原因:"+data.failReason,'info');  
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
				$.messager.alert('操作结果',"完成个数:"+data.completecount+"\r\n异常个数:"+data.errorcount,'info'); 
				
				if(data.completecount+data.errorcount == 1){
					//单个表
					$("#readmeterTab").datagrid('updateRow', {index:index,row:{valveState:data.switch_}});
				}
			}
		}
	});
}

</script>
</body>
</html>