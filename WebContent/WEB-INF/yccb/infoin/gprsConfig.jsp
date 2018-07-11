<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配置集中器</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
 <input type="hidden" id="gprsconfig_nid" name="neighborid" value="${gprs.neighbor.pid }"/>
 <input type="hidden" id="gprsconfig_pid" name="pid" value="${gprs.pid }"/>
 <input type="hidden" id="gprsconfig_addr" name="gprsconfaddr" value="${gprs.gprsaddr }"/>
 <c:if test="${(gprs.gprsprotocol==2) || (gprs.gprsprotocol==5)}">
 	<div style="margin:10px;">
 		<label><fmt:message key='g.addr'/>：</label><span>${gprs.gprsaddr }</span>
 		<label><fmt:message key='m.caddr'/></label>
		<select class="easyui-combobox" id="collectors" name="collectors" style="width:150px" data-options="onSelect:listmeters,panelHeight:'200'">
			<option value="">请选择采集器</option>
			<c:forEach var="col" items="${collector_list }">
				<option value="${col.caddr }">${col.caddr }</option>
			</c:forEach>
	    </select>
	    <br/><br/>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="querycjqs()" id="querycjqsbtn">查询采集器</a>
 		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addcjq()" id="addcjqbtn"><fmt:message key="g.addcjq"/></a>
 		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteAllmeters()" id="deleteallbtn"><fmt:message key="g.deleteall"/></a>
	    <br/><br/>
 		<select class="easyui-combobox" id="gprsslave" name="gprsslave" style="width:100px" data-options="panelHeight:'auto'">
 			<option value="0">请选择底层类型</option>
 			<option value="1">MBUS表</option>
 			<option value="2">485表</option>
 			<option value="3">采集器</option>
 		</select>
 		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="configgprsslave()" id="configslavebtn">设置底层类型</a>
 		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="querygprsslave()" id="queryslavebtn">查询底层类型</a>
 	</div>
 	
 	<table id="gprsmetersTbl" style="width:100%;height:300px;"></table>
 </c:if>
 <c:if test="${(gprs.gprsprotocol!=2) && (gprs.gprsprotocol !=5)}">
 	<div style="margin:10px;">
 		<label>暂不支持远程配置</label>
 	</div>
 </c:if>
<script type="text/javascript">
$(function(){
	$("#gprsmetersTbl").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
		columns:[[
		          {field:'pid',title:'ID',width:60,checkbox:true},
		          {field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:80},
		          {field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:80},
		          {field:'remark',title:'<fmt:message key='common.remark'/>',width:80}
		      ]],
		toolbar: [{
			text: '<fmt:message key="g.addmeter"/>', 
			iconCls: 'icon-add', 
			handler: addmeters
		}, '-', { 
			text: '<fmt:message key="g.deletemeter"/>', 
			iconCls: 'icon-remove',
			handler:  deletemeters
		}, '-', { 
			text: '<fmt:message key="g.readdata"/>', 
			iconCls: 'icon-reload',
			handler:  readJZQdata
		}]
	});
});
function listmeters(){
	var gid = $("#gprsconfig_pid").val();
	var caddr = $("#collectors").combobox("getValue");
	if(caddr==""){
		$.messager.show({
			title:"Info",
			msg:"请选择采集器",
			showType:'slide'
		});
		return;
	}
	$('#gprsmetersTbl').datagrid({
		url:"${path}/infoin/neighbor/listgprsmeters.do",
		queryParams: {
			pid:gid,
			caddr:caddr
		}  
	});
}
function querycjqs(){
	$('#querycjqsbtn').linkbutton('disable');
	var gid = $("#gprsconfig_pid").val();
	$.ajax({
		url:'${path}/infoin/neighbor/querycjqs.do',
		type:'post',
		data:{pid:gid},
		dataType: "json", 
		success:function(data){
			if(data.done == true){
				$.messager.show({
					height:400,
					timeout:0,
					title:"全部采集器",
					msg:data.cjqs,
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"全部采集器失败: "+data.reason,
					showType:'slide'
				});
			}
			$('#querycjqsbtn').linkbutton('enable');
		}
	});	
	
}
function addcjq(){
	$('#addcjqbtn').linkbutton('disable');
	var gid = $("#gprsconfig_pid").val();
	var caddr = $("#collectors").combobox("getValue");
	if(caddr==""){
		$.messager.show({
			title:"Info",
			msg:"请选择采集器",
			showType:'slide'
		});
		return;
	}
	$.ajax({
		url:'${path}/infoin/neighbor/addcjq.do',
		type:'post',
		data:{pid:gid,caddr:caddr},
		dataType: "json", 
		success:function(data){
			if(data.done == true){
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"添加采集器完成",
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"添加采集器失败: "+data.reason,
					showType:'slide'
				});
			}
			$('#addcjqbtn').linkbutton('enable');
		}
	});	
	
}
function deleteAllmeters(){
	$('#deleteallbtn').linkbutton('disable');
	var gid = $("#gprsconfig_pid").val();
	$.ajax({
		url:'${path}/infoin/neighbor/deleteAllmeters.do',
		type:'post',
		data:{pid:gid},
		dataType: "json", 
		success:function(data){
			if(data.done == true){
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"删除全部表完成",
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"删除全部表: "+data.reason,
					showType:'slide'
				});
			}
			$('#deleteallbtn').linkbutton('enable');
		}
	});	
	
}

var conf_interval;
var configgprs_done = true;
function addmeters(){
	var gid = $("#gprsconfig_pid").val();
	var caddr = $("#collectors").combobox("getValue");
	var gprsaddr = $("#gprsconfig_addr").val();
	if(caddr==""){
		$.messager.show({
			title:"Info",
			msg:"请选择采集器",
			showType:'slide'
		});
		return;
	}
	var rows = $('#gprsmetersTbl').datagrid('getSelections');
	var rows_all = $('#gprsmetersTbl').datagrid('getRows');
	
	if(rows.length == 0){
		$.messager.show({
			title:"Info",
			msg:"请选择表",
			showType:'slide'
		});
		return;
	}
	var maddrs = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		maddrs.push(row.meterAddr);
	}
	
	if(configgprs_done){
		configgprs_done = false;
		$.ajax({
			type:"POST",
			url:'${path}/infoin/neighbor/jzqaddmeters.do',
			dataType:"json",  
	        traditional :true,
			data:{
				'maddrs':maddrs,
				pid:gid,
				caddr:caddr
			},
			success:function(data){
				if(data.done == true){
					$.messager.progress({title:"努力中...",text:"",interval:100});
					conf_interval = setInterval(function(){checkconfiging(gprsaddr,'add');},5000);
				}else{
					$.messager.show({
						title:"Info",
						timeout:0,
						msg:"添加表失败: "+data.reason,
						showType:'slide'
					});
				}
			}
		});
		configgprs_done = true;
	}else{
		$.messager.show({
			title : 'Info',
			msg : '操作频繁，请稍后重试',
			showType : 'slide'
		});
	}
}

function checkconfiging(gprsaddr,action){
	var rows_all = $('#gprsmetersTbl').datagrid('getRows');
	$.ajax({
		type:"POST",
		url:"${path}/infoin/neighbor/checkgprsconfig.do",
		dataType:"json",
		data:{
			gprsaddr:gprsaddr
		},
		success:function(data){
			if(data.done == true){
				clearInterval(conf_interval);
				$.messager.progress("close");
				
				if(action == "add"){
					//添加表
					if(data.good == 0 && data.error == 0){
						$.messager.show({
							title:"Info",
							timeout:0,
							msg:"添加表完成! 出现了异常请稍后再试",
							showType:'slide'
						});
						return;
					}
					
					$.messager.show({
						title:"Info",
						timeout:0,
						msg:"添加表完成! good: "+data.good+" ;error: "+data.error,
						showType:'slide'
					});
					
					var result = data.result;
					console.log("result:"+result);
					for(var i = 0;i < result.length;i++){
						var m = result[i];
						for(var j=0; j<rows_all.length; j++){
							var row_ = rows_all[j];
							if(row_.meterAddr == m.maddr){
								var index_ = $("#gprsmetersTbl").datagrid('getRowIndex', row_);
								if(m.done == true){
									$("#gprsmetersTbl").datagrid('updateRow', {index:index_,row:{remark:'添加成功'}});
								}else{
									$("#gprsmetersTbl").datagrid('updateRow', {index:index_,row:{remark:'*添加失败*'}}); 
								}
							}
						}
					}
					
				}
				if(action == "delete"){
					//删除表
					if(data.good == 0 && data.error == 0){
						$.messager.show({
							title:"Info",
							timeout:0,
							msg:"删除表完成! 出现了异常请稍后再试",
							showType:'slide'
						});
						return;
					}
					
					$.messager.show({
						title:"Info",
						timeout:0,
						msg:"删除表完成! good: "+data.good+" ;error: "+data.error,
						showType:'slide'
					});
					
					var result = data.result;
					for(var i = 0;i < result.length;i++){
						var m = result[i];
						for(var j=0; j<rows_all.length; j++){
							var row_ = rows_all[j];
							if(row_.meterAddr == m.maddr){
								var index_ = $("#gprsmetersTbl").datagrid('getRowIndex', row_);
								if(m.done == true){
									$("#gprsmetersTbl").datagrid('updateRow', {index:index_,row:{remark:'删除成功'}});
								}else{
									$("#gprsmetersTbl").datagrid('updateRow', {index:index_,row:{remark:'*删除失败*'}}); 
								}
							}
						}
					}
				}
				
			}
		}
	});
}

function deletemeters(){
	var gid = $("#gprsconfig_pid").val();
	var gprsaddr = $("#gprsconfig_addr").val();
	var caddr = $("#collectors").combobox("getValue");
	if(caddr==""){
		$.messager.show({
			title:"Info",
			msg:"请选择采集器",
			showType:'slide'
		});
		return;
	}
	var rows = $('#gprsmetersTbl').datagrid('getSelections');
	var rows_all = $('#gprsmetersTbl').datagrid('getRows');
	
	if(rows.length == 0){
		$.messager.show({
			title:"Info",
			msg:"请选择表",
			showType:'slide'
		});
		return;
	}
	var maddrs = [];
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		maddrs.push(row.meterAddr);
	}
	
	if(configgprs_done){
		configgprs_done = false;
		$.ajax({
			type:"POST",
			url:'${path}/infoin/neighbor/jzqdeletemeters.do',
			dataType:"json",  
	        traditional :true,
			data:{
				'maddrs':maddrs,
				pid:gid,
				caddr:caddr
			},
			success:function(data){
				if(data.done == true){
					$.messager.progress({title:"努力中...",text:"",interval:100});
					conf_interval = setInterval(function(){checkconfiging(gprsaddr,'delete');},5000);
				}else{
					$.messager.show({
						title:"Info",
						timeout:0,
						msg:"删除表失败: "+data.reason,
						showType:'slide'
					});
				}
			}
		});
		configgprs_done = true;
	}else{
		$.messager.show({
			title : 'Info',
			msg : '操作频繁，请稍后重试',
			showType : 'slide'
		});
	}
}
function readJZQdata(){
	var gid = $("#gprsconfig_pid").val();
	var caddr = $("#collectors").combobox("getValue");
	if(caddr==""){
		$.messager.show({
			title:"Info",
			msg:"请选择采集器",
			showType:'slide'
		});
		return;
	}
	var rows_all = $('#gprsmetersTbl').datagrid('getRows');
	$.messager.show({
		title:"Info",
		msg:"数据正在传送中...",
		showType:'slide'
	});
	if(configgprs_done){
		configgprs_done = false;
		$.ajax({
			url:'${path}/infoin/neighbor/readJZQdata.do',
			type:'post',
			data:{pid:gid,caddr:caddr},
			dataType: "json", 
			success:function(data){
				if(data.done == false){
					$.messager.show({
						title:"Info",
						timeout:0,
						msg:"读取数据失败: "+data.reason,
						showType:'slide'
					});
				}
				if(data.caddr == caddr){
					var result = data.result;
					for(var i = 0;i < result.length;i++){
						var m = result[i];
						var found = false;
						for(var j=0; j<rows_all.length; j++){
							var row_ = rows_all[j];
							if(row_.meterAddr == m.maddr){
								found = true;
								var index_ = $("#gprsmetersTbl").datagrid('getRowIndex', row_);
								$("#gprsmetersTbl").datagrid('updateRow', {index:index_,row:{remark:'已添加'}});
							}
						}
						if(!found){
							//没有找到  在最后面添加一条 
							$("#gprsmetersTbl").datagrid('appendRow', {pid:0,collectorAddr:caddr,meterAddr:m.maddr,remark:'*多余*'});
						}
					}
					$.messager.show({
						title:"Info",
						timeout:0,
						msg:"读取数据完成",
						showType:'slide'
					});
				}else{
					//不是这个采集器  do nothing
				}
				
			}
		});	
		configgprs_done = true;
	}else{
		$.messager.show({
			title : 'Info',
			msg : '操作频繁，请稍后重试',
			showType : 'slide'
		});
	}
}
function configgprsslave(){
	$('#configslavebtn').linkbutton('disable');
	var gid = $("#gprsconfig_pid").val();
	var gprsslave = $("#gprsslave").combobox("getValue");
	if(gprsslave == 0){
		$.messager.show({
			title:"Info",
			msg:"请选择类型",
			showType:'slide'
		});
		return;
	}
	$.ajax({
		url:'${path}/infoin/neighbor/configgprsslave.do',
		type:'post',
		data:{pid:gid,gprsslave:gprsslave},
		dataType: "json", 
		success:function(data){
			if(data.done == true){
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"设置底层类型完成",
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"设置底层类型: "+data.reason,
					showType:'slide'
				});
			}
			$('#configslavebtn').linkbutton('enable');
		}
	});	
	
}
function querygprsslave(){
	$('#queryslavebtn').linkbutton('disable');
	var gid = $("#gprsconfig_pid").val();
	$.ajax({
		url:'${path}/infoin/neighbor/querygprsslave.do',
		type:'post',
		data:{pid:gid},
		dataType: "json", 
		success:function(data){
			if(data.done == true){
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"底层类型: "+data.slave,
					showType:'slide'
				});
			}else{
				$.messager.show({
					title:"Info",
					timeout:0,
					msg:"底层类型失败: "+data.reason,
					showType:'slide'
				});
			}
			$('#queryslavebtn').linkbutton('enable');
		}
	});	
	
}
</script>
</body>
</html>