<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="info.customer"/></title>
</head>
<body>
	<div style="margin:10px;">
		<form id="search_customer" method="post">
			<div>
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200'">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<div style="display:inline;padding:0 10px;">
		    		<label><fmt:message key='c.num'/></label>
					<input type="text" id="c_num" name="c_num" class="easyui-textbox"/>
				</div>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchCustomer_()" ><fmt:message key='common.submit'/></a>
			</div>
		</form>
	</div>
	
	
	<table id="customerTab" style="width:100%;height:400px;"></table>
	<div id="addCustomerWin"></div>
	<div id="addCustomerWins"></div>
	<div id="updateCustomerWin"></div>
	<div id="updateMeterWin"></div>
	<div class="easyui-dialog" title="<fmt:message key='m.changemeter'/>" id="changedialog" data-options="closed:true" style="width:500px;height:300px;padding:10px;">
		<form id="changemeterform" method="post">
		<table style="margin:0px auto;padding-top:20px;">
			<tr>
				<td><lable><fmt:message key='m.newaddr'/>：</lable></td>
				<td><input type="text" class="easyui-textbox" name="new_maddr" id="new_maddr" data-options="required:true"/></td>
			</tr>
			<tr>
				<td><lable><fmt:message key='m.changeend'/></lable></td>
				<td><input type="text" class="easyui-numberbox" name="end" id="end" data-options="min:0,max:1000000"/>
					<input type="hidden" name="meterid" id="meterid"/>
				</td>
			</tr>
		</table>
		</form>
		<div style="text-align:center;padding-top:10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="changeMeter" onclick="changeMeter()"><fmt:message key='common.submit'/></a>
		</div>
	</div>
	
	<div class="easyui-dialog" title="<fmt:message key='m.adjustmeter'/>" id="adjustdialog" data-options="closed:true" style="width:500px;height:300px;padding:10px;">
		<form id="adjustmeterform" method="post">
		<table style="margin:0px auto;padding-top:20px;">
			<tr>
				<td><lable><fmt:message key='g.addr'/>：</lable></td>
				<td><input type="text" class="easyui-textbox" name="gaddr" id="gaddr" data-options="required:true"/></td>
			</tr>
			<tr>
				<td><lable><fmt:message key='m.caddr'/>：</lable></td>
				<td><input type="text" class="easyui-textbox" name="caddr" id="caddr" data-options="required:true"/></td>
			</tr>
			<tr>
				<td><lable><fmt:message key='m.maddr'/></lable></td>
				<td><input type="text" class="easyui-textbox" name="maddr" id="maddr" data-options="required:true"/></td>
				<input type="hidden" name="customerid" id="customerid"/>
			</tr>
			<tr>
				<td colspan=2><lable style="color:red">将此表扣过的水费，加回给原来的用户，在新用户中扣掉，并将此表对应到新用户下。</lable></td>
			</tr>
		</table>
		</form>
		<div style="text-align:center;padding-top:10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="adjustMeter" onclick="adjustMeter()"><fmt:message key='common.submit'/></a>
		</div>
	</div>
</body>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/datagrid-detailview.js"></script>
<script>
$(function(){
	$("#customerTab").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
// 		url:"${path}/infoin/customer/ListCustomer",
		queryParams:{
			
		},
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
// 		singleSelect:true,  
		rowStyler:function(index,row){
			
		},
		columns:[[
		          {field:'pid',title:'ID',width:60,checkbox:true},
// 				  {field:'n_name',title:'小区',width:80},
		          {field:'c_num',title:'<fmt:message key='c.num'/>',width:80},
		          {field:'customerName',title:'<fmt:message key='c.name'/>',width:80},
		          {field:'customerId',title:'<fmt:message key='c.customerid'/>',width:80},
		          {field:'apid',title:'<fmt:message key='c.apid'/>',width:80},
		          {field:'customerMobile',title:'<fmt:message key='common.mobile'/>',width:80},
		          {field:'customerEmail',title:'<fmt:message key='common.email'/>',width:80},
		          {field:'nationalId',title:'<fmt:message key='c.nationalid'/>',width:80},
		          {field:'customerAddr',title:'<fmt:message key='common.addr'/>',width:225},
		          {field:'hk',title:'<fmt:message key='c.hk'/>',width:60,editor:'text'},
		          {field:'peoplecnt',title:'<fmt:message key='c.hushu'/>',width:60},
		          {field:'customerBalance',title:'<fmt:message key='c.balance'/>',width:60,styler:function(value,row,index){
		        	  if(value <= 0){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'prePaySign',title:'<fmt:message key='c.prestyle'/>',width:60,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='c.pre'/>";
		        	  }else{
		        		  return "<fmt:message key='c.post'/>";
		        	  }
		          }},
		          {field:'warnThre',title:'<fmt:message key='c.warnthre'/>',width:60},
		          {field:'warnStyle',title:'<fmt:message key='c.warnstyle'/>',width:60,editor:'text',formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='c.sms'/>";
		        	  }else{
		        		  return "<fmt:message key='c.email'/>";
		        	  }
		          }},
		          {field:'warnSwitch',title:'<fmt:message key='c.warnswitch'/>',width:60,editor:'text',formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='common.open'/>";
		        	  }else{
		        		  return "<fmt:message key='common.close'/>";
		        	  }
		          }},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.pid;
							return "<a href='#' class='operateHref' onclick='updateCustomer("+id+","+index+")'><fmt:message key='common.update'/></a>"
							+"<a href='#' class='operateHref' onclick='deleteCustomer("+id+","+index+")'><fmt:message key='common.delete'/></a>"
							+"<a href='#' class='operateHref' onclick='adjustDialog("+id+")'><fmt:message key='m.adjustmeter'/></a>";
				  }}
		      ]],
		      toolbar:[{
		    	  text: "<fmt:message key='c.add'/>",
		    	  iconCls: 'icon-add',
		    	  handler: addCustomer
		      },'-',{
		    	  text: "<fmt:message key='c.adds'/>",
		    	  iconCls:'icon-add',
		    	  handler: addCustomers
		      }],
		      view: detailview,
	          detailFormatter:function(index,row){
	              return '<div style="padding:2px"><table class="ddv"></table></div>';
	          },
	          onExpandRow: function(index,row){
	              var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
	              ddv.datagrid({
	                  url:'${path}/infoin/customer/ListMetersByCid.do',
	                  queryParams:{
	                	  "cpid":row.pid,
	                  },
	                  fitColumns:true,
	                  singleSelect:true,
	                  rownumbers:true,
	                  loadMsg:'',
	                  height:'auto',
	                  columns:[[
							{field:'pid',title:'ID',width:60,checkbox:true},
							{field:'apid',title:'<fmt:message key='m.apid'/>',width:60},
							{field:'gprs',title:'<fmt:message key='gprs'/>',width:60},
	                      	{field:'qfh',title:'<fmt:message key='m.qfh'/>',width:60},
				          	{field:'steelNum',title:'<fmt:message key='m.steel'/>',width:60},
				          	{field:'suppleMode',title:'<fmt:message key='m.supplemode'/>',width:60,formatter:function(value,row,index){
					        	  if(value == 1){
					        		  return "<fmt:message key='m.firstsupple'/>";
					        	  }else{
					        		  return "<fmt:message key='m.secondsupple'/>";
					        	  }
					        }},
				          	{field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:60},
				          	{field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:60},
				          	{field:'meterSolid',title:'<fmt:message key='m.solid'/>',width:60,formatter:function(value,row,index){
					        	  if(value == 1){
					        		  return "<fmt:message key='m.solidyes'/>";
					        	  }else{
					        		  return "<fmt:message key='m.solidno'/>";
					        	  }
					        }},
				          	{field:'mk',title:'<fmt:message key='m.mk'/>',width:60},
				          	{field:'pk',title:'<fmt:message key='m.pk'/>',width:60},
				          	{field:'isValve',title:'<fmt:message key='m.valve'/>',width:60,formatter:function(value,row,index){
					        	  if(value == 1){
					        		  return "<fmt:message key='common.have'/>";
					        	  }else{
					        		  return "<fmt:message key='common.nothave'/>";
					        	  }
					        }},
				          	{field:'deductionStyle',title:'<fmt:message key='m.destyle'/>',width:50,formatter:function(value,row,index){
					        	  if(value == 1){
					        		  return "<fmt:message key='m.deread'/>";
					        	  }else{
					        		  return "<fmt:message key='m.noderead'/>";
					        	  }
					        }},
				          	{field:'valveOffthre',title:'<fmt:message key='m.valveoffthre'/>',width:50},
				          	{field:'timerSwitch',title:'<fmt:message key='m.switch'/>',width:50,formatter:function(value,row,index){
					        	  if(value == 1){
					        		  return "<fmt:message key='common.open'/>";
					        	  }else{
					        		  return "<fmt:message key='common.close'/>";
					        	  }
					        }},
				          	{field:'timer',title:'<fmt:message key='m.timer'/>',width:50},
				          	{field:'overflow',title:'<fmt:message key='m.overflow'/>',width:50},
				          	{field:'deRead',title:'<fmt:message key='m.deread'/>',width:50},
				          	{field:'destartread',title:'<fmt:message key='m.destartread'/>',width:50},
				          	{field:'changend',title:'<fmt:message key='m.changeend'/>',width:50},
// 				          	{field:'changestart',title:'起始读数',width:50},
				          	{field:'action',title:'<fmt:message key='common.action'/>',width:90,halign:'center',align:'center',formatter: function(value,row_,index_){
								var c_id = row.pid;
								var m_id = row_.pid;
								return "<a href='#' class='operateHref' onclick='updateMeter("+c_id+","+index+","+m_id+")'><fmt:message key='common.update'/></a>"
								+"<a href='#' class='operateHref' onclick='deleteMeter("+m_id+","+index+","+index_+")'><fmt:message key='common.delete'/></a>"
								+"<a href='#' class='operateHref' onclick='changemeter("+m_id+","+index+","+index_+")'><fmt:message key='m.changemeter'/></a>";
					  		}}
				          	
	                  ]],
	                  onResize:function(){
	                      $('#customerTab').datagrid('fixDetailRowHeight',index);
	                  },
	                  onLoadSuccess:function(){
	                      setTimeout(function(){
	                          $('#customerTab').datagrid('fixDetailRowHeight',index);
	                      },0);
	                  }
	              });
	              $('#customerTab').datagrid('fixDetailRowHeight',index);
	          }
	});
});
function check_c_num_(c_num){
	if(c_num.trim() != ''){
		var ldh = c_num.split(/[ ,.-]/);
		var len = ldh.length;
		if(len == 3 || len == 1){
			//这个地方还可以加上判断是不是全是数字
			return true;
		}else{
			return false;
		}
	}
	return true;
}
function searchCustomer_(){
	var n_id = $("#neighbor").combobox("getValue");
	var c_num = $("#c_num").textbox("getValue");
	
	if(n_id == ''){
		$.messager.alert('Info','<fmt:message key='common.choosenei'/>');
		return;
	}
	if(check_c_num_(c_num)){
		$('#customerTab').datagrid({
			url:"${path}/infoin/customer/ListCustomer.do",
			queryParams: {
				n_id:n_id,  		
				c_num:c_num
			}  
		});
	}
}
function addCustomer(){
	$('#addCustomerWin').window({	
		href:'${path}/infoin/customer/addPage.do',
		width:800,	
		height:500,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		title: '<fmt:message key='c.add'/>'
	}); 
}
function addCustomers(){
	$('#addCustomerWins').window({	
		href:'${path}/infoin/customer/addPages.do',
		width:467,	
		height:300,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		title: '<fmt:message key='c.adds'/>'
	});
}

var update_index;
var updata_cid;
function updateCustomer(cid,index){
	update_index = index;
	updata_cid = cid;
	$('#updateCustomerWin').window({	
		href:'${path}/infoin/customer/updatePage.do?cid='+cid+"&index="+index,
		width:800,	
		height:500,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		title: '<fmt:message key='c.update'/>',
		onClose:refreshRow
	}); 
}
function refreshRow(index){
	//去服务器获取index这行对应的值  更新这一行
	$.ajax({
		url:'${path}/infoin/customer/refresh.do',
		type:'post',
		data:{
			'cid':updata_cid
		},
		dataType:"json",
		success : function(data) {
			$("#customerTab").datagrid('updateRow', {index:update_index,row:data});
		}
	});	
}
	function deleteCustomer(cid, index) {
		$.messager.confirm('Info', '<fmt:message key='common.confirmdelete'/>？', function(r) {
			if (r) {
				$.ajax({
					url : '${path}/infoin/customer/delete.do',
					type : 'post',
					data : {
						'cid' : cid
					},
					success : function() {
						$("#customerTab").datagrid('deleteRow', index);
					}
				});
			}
		});
	}
	function updateMeter(cid,index,mid) {
		update_index = index;
// 		updata_cid = cid;
		$('#updateMeterWin').window({	
			href:'${path}/infoin/meter/updatePage.do?mid='+mid+"&cid="+cid,
			width:800,
			height:500,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			title: '<fmt:message key='m.update'/>',
		});
	}
	function deleteMeter(mid,index,index_) {
// 		$("#customerTab").datagrid('getRowDetail',0).find('table.ddv').datagrid('deleteRow', 0);
		$.messager.confirm('Info', '<fmt:message key='common.confirmdelete'/>？', function(r){
			if(r){
				$.ajax({
					url:'${path}/infoin/meter/delete.do',
					type:"post",
					data:{
						mid:mid
					},
					success:function(){
						$("#customerTab").datagrid('getRowDetail',index).find('table.ddv').datagrid('deleteRow', index_);
					}
				});
			}
		});
	}
	//
	var gprs_id ;
	var m_id;
	var caddr;
	function changemeter(mid,index,index_) {
		//获取当前 集中器  地址  采集器地址
		caddr = $("#customerTab").datagrid('getRowDetail',index).find('table.ddv').datagrid('getRows')[index_]["collectorAddr"];
		gprs_id = $("#customerTab").datagrid('getRowDetail',index).find('table.ddv').datagrid('getRows')[index_]["gprs_id"];
		m_id = mid;
		$("#meterid").val(mid);
		$("#changedialog").dialog('open');
	}
	
	function adjustDialog(cid){
		$("#customerid").val(cid);
		$("#adjustdialog").dialog('open');
	}
	
	function check_maddr(){
		var maddr=$("#new_maddr").textbox("getValue");
		if(maddr != ""){
			$.ajax({
				type:"POST",
				url:"${path}/infoin/customer/check_maddr.do",
				dataType:"json",
				data:{
					maddr:maddr,
					caddr:caddr,
					gprs_id:gprs_id
				},
				success:function(data){
					if(data == 'true'){
						$("#new_maddr").textbox("setValue","");
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='m.exist'/>",
							showType:'slide'
						});
					}
				}
			});
		}
	}
	function changeMeter(){
		$('#changeMeter').linkbutton('disable');
		var end=$("#end").textbox("getValue");
// 		var maddr=$("#new_maddr").textbox("getValue");
		$("#changemeterform").form('submit',{
			url:"${path}/infoin/customer/changemeter.do",
			onSubmit:function(){
				//check the data ,bad data return false
				if(!$('#changemeterform').form('validate')){
					return false;
				}
				if(end == ''){
					$.messager.show({
						title:"Info",
						msg:"<fmt:message key='m.oldend'/>",
						showType:'slide'
					});
					return false;
				}
			},
			success:function(data){
// 				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data == "true"){
					$("#changedialog").dialog('close');
				}
				$('#changeMeter').linkbutton('enable');
			}
		});
		
	}
	function adjustMeter(){
		$('#adjustMeter').linkbutton('disable');
		$("#adjustmeterform").form('submit',{
			url:"${path}/infoin/customer/adjustmeter.do",
			success:function(data){
				var data = eval('(' + data + ')');  // change the JSON string to javascript object
				if(data.r > 0){
					$("#adjustdialog").dialog('close');
					$.messager.show({
						title:"Info",
						msg:"调整成功!请刷新数据",
						showType:'slide'
					});
// 					window.location.reload();
				}else{
					$.messager.show({
						title:"Info",
						msg:"输入信息错误！",
						showType:'slide'
					});
				}
				$('#adjustMeter').linkbutton('enable');
			}
		});
		
	}
</script>
</html>