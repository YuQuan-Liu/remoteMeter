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
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200',onSelect:searchSettle">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<span style="margin-left:20px;">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" id="settleall" onclick="settleAll()" ><fmt:message key='settle.settlenei'/></a>
	    		</span>
	    		<span style="margin-left:20px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="warnallbtn" onclick="warnAll()"><fmt:message key='warnpay'/></a>
	    		</span>
			</div>
		</form>
	</div>
	
	<table id="settleTab" style="width:100%;height:400px;"></table>
	<div style="margin:10px;">
		<p><fmt:message key='yl'/></p>
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
		          {field:'deread',title:'<fmt:message key='m.deread'/>',width:80},
		          {field:'destartread',title:'<fmt:message key='m.destartread'/>',width:80},
		          {field:'readdata',title:'<fmt:message key='m.readdata'/>',width:80},
		          {field:'changeend',title:'<fmt:message key='m.changeend'/>',width:80},
		          {field:'readtime',title:'<fmt:message key='m.readtime'/>',width:80},
		          {field:'pkName',title:'<fmt:message key='price.name'/>',width:80},
		          {field:'yl',title:'<fmt:message key='yl'/>',width:80,formatter:function(value,row,index){
		        	  if(row.changeend > 0){
		        		  return row.readdata+row.changeend-row.deread;
		        	  }else{
		        		  return row.readdata-row.deread;
		        	  }
		          }},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:160,halign:'center',align:'center',
						formatter: function(value,row,index){
							return "<a href='#' class='operateHref' onclick='readMeterManual("+row.m_id+","+index+")'><fmt:message key='common.update'/></a>"
							+"<a href='#' class='operateHref' onclick='settleSingle("+row.m_id+","+index+")'><fmt:message key='settle.single'/></a>";
				  }}
		      ]]
	});
	$("#ylTab").datagrid({
		striped:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
		columns:[[
		          {field:'pricekindname',title:'<fmt:message key='m.pk'/>',width:100},
		          {field:'yl',title:'<fmt:message key='yl'/>',width:100}
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
	$.messager.prompt('<fmt:message key='read.readchange'/>', '<fmt:message key='read.readright'/>', function(r){
        if (r){
			if(r >= 0){
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
				 $.messager.alert('Error','<fmt:message key='read.readerror'/>');
			}
        }
    });
}

function warnAll(){
	$('#warnallbtn').linkbutton('disable');
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
					$.messager.alert('Info','<fmt:message key='doing'/>');
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

function settleAll(){
	$('#settleall').linkbutton('disable');
	var n_name = $("#neighbor").combobox("getText");
	var n_id = $("#neighbor").combobox("getValue");
	
	$.messager.confirm('<fmt:message key='settle'/>', '<fmt:message key='settle.confirm'/>'+n_name+"?", function(r){
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
						$.messager.alert('Info','<fmt:message key='settle.done'/>');
						searchSettle();
					}else{
						$.messager.alert('Info',data.reason);
					}
					$("#settleall").prop("disabled",false);
					$('#settleall').linkbutton('enable');
				}
			});
		}
	});
	
}

var settlesingle_done = true;
function settleSingle(mid,index){
	var meteraddr = $('#settleTab').datagrid('getRows')[index]["meterAddr"];
	var yl = $('#settleTab').datagrid('getRows')[index]["readdata"]-$('#settleTab').datagrid('getRows')[index]["deread"];
	if(yl == 0){
		$.messager.alert('Info','<fmt:message key='settle.zero'/>');
		return;
	}
	$.messager.confirm('<fmt:message key='settle'/>', '<fmt:message key='settle.confirm'/>'+meteraddr+'?', function(r){
		if(r){
			if(settlesingle_done){
				settlesingle_done = false;
				$.ajax({
					type:"POST",
					url:"${path}/charge/settle/settlesingle.do",
					dataType:"json",
					data:{
						m_id:mid
					},
					success:function(data){
						if(data.done == true){
							$.messager.alert('Info','<fmt:message key='settle.done'/>');
							$("#settleTab").datagrid('updateRow', 
									{index:index,row:{readdata:data.read,customerBalance:data.customerBalance,deread:data.deread}});
						}else{
							$.messager.alert('Info',data.reason);
						}
					}
				});
				settlesingle_done = true;
			}else{
				$.messager.show({
					title : 'Info',
					msg : '操作频繁，请稍后重试',
					showType : 'slide'
				});
			}
		}
	});
}
</script>
</body>
</html>