<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>结算</title>
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
	    		<span style="margin-left:20px;">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" id="settleall" onclick="settleAll()" >结算小区</a>
	    		</span>
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="warnAll()">提醒交费</a>
	    		</span>
			</div>
		</form>
	</div>
	
	<table id="settleTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;">
		<p>用水量</p>
	</div>
	<table id="ylTab" style="width:400px;height:200px;"></table>
<script>
$(function(){
	$("#settleTab").datagrid({
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
		          {field:'valveState',title:'阀门状态',width:80,formatter:function(value,row,index){
		        	  if(row.isValve == 1){
		        		  if(value == 1){
			        		  return "开";
			        	  }else{
			        		  if(value == 0){
			        			  return "关";
			        		  }else{
			        			  return "异常";
			        		  }
			        	  }
		        	  }else{
		        		  return "无";
		        	  }
		          }},
		          {field:'deread',title:'扣费读数',width:80},
		          {field:'readdata',title:'表读数',width:80},
		          {field:'readtime',title:'抄表时间',width:80},
		          {field:'yl',title:'用量',width:80,formatter:function(value,row,index){
		        	  return row.readdata-row.deread;
		          }},
		          {field:'action',title:'操作',width:160,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='readMeterManual("+row.m_id+","+index+")'>修改表数</a>"
							+"<a href='#' class='operateHref' onclick='settleSingle("+row.m_id+","+index+")'>单个结算</a>";
				  }}
		      ]]
	});
	$("#ylTab").datagrid({
		striped:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
		columns:[[
		          {field:'pricekindname',title:'单价',width:100},
		          {field:'yl',title:'用水量',width:100}
		      ]]
	});
});

function searchSettle(){
	var n_id = $("#neighbor").combobox("getValue");
	
	if(n_id != "" ){
		$('#settleTab').datagrid({
			url:"${path}/charge/settle/listsettle.do",
			queryParams: {
				n_id:n_id
			}
		});
		
		$('#ylTab').datagrid({
			url:"${path}/charge/settle/allyl.do",
			queryParams: {
				n_id:n_id
			}
		});
	}
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
							$("#settleTab").datagrid('updateRow', {index:index,row:{readdata:data.read}});
						}
					}
				});
			}else{
				 $.messager.alert('Error','表数错误：'+r+',请重新输入');
			}
        }
    });
}

function warnAll(){
	var c_ids = [];
	var rows = $('#settleTab').datagrid('getSelections');
	
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
					$.messager.alert('Info','后台正在处理中...');
				}else{
					$.messager.alert('Info','请选择用户');
				}
			}
		});
	}else{
		$.messager.alert('Info','请选择用户');
	}
}

function settleAll(){
	var n_name = $("#neighbor").combobox("getText");
	var n_id = $("#neighbor").combobox("getValue");
	
	$.messager.confirm('结算', '您确定结算'+n_name+"?", function(r){
		if(r){
			$("#settleall").prop("disabled",true);
			$.ajax({
				type:"POST",
				url:"${path}/charge/settle/settleall.do",
				dataType:"json",
				data:{
					n_id:n_id
				},
				success:function(data){
					if(data.done == true){
						$.messager.alert('Info','结算完成');
						searchSettle();
					}else{
						$.messager.alert('Info',data.reason);
					}
					$("#settleall").prop("disabled",false);
				}
			});
		}
	});
}

function settleSingle(mid,index){
	var meteraddr = $('#settleTab').datagrid('getRows')[index]["meterAddr"];
	var yl = $('#settleTab').datagrid('getRows')[index]["readdata"]-$('#settleTab').datagrid('getRows')[index]["deread"];
	if(yl == 0){
		$.messager.alert('Info','此表用量为0！');
		return;
	}
	$.messager.confirm('结算', '您确定结算'+meteraddr+'表?', function(r){
		if(r){
			$.ajax({
				type:"POST",
				url:"${path}/charge/settle/settlesingle.do",
				dataType:"json",
				data:{
					m_id:mid
				},
				success:function(data){
					if(data.done == true){
						$.messager.alert('Info','结算完成');
						$("#settleTab").datagrid('updateRow', 
								{index:index,row:{readdata:data.read,customerBalance:data.customerBalance,deread:data.deread}});
					}else{
						$.messager.alert('Info',data.reason);
					}
				}
			});
		}
	});
}
</script>
</body>
</html>