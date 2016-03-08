<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抄表</title>
<%@include file="/commonjsp/top.jsp" %>
<c:set var="menus" scope="session" value="${userInfo.menus}"/>
</head>
<body>
	<div id="tb" style="float:left;">
		<fmt:message key='common.neighborName'/>： 
		<select class="easyui-combobox" style="width: 100px" id="sel_neibours" data-options="panelHeight:'200'">
			<option value=""><fmt:message key='common.choosenei'/></option>
			<c:forEach var="n" items="${neighbor_list }">
				<option value="${n.pid }">${n.neighborName }</option>
			</c:forEach>
		</select> 
		<fmt:message key='c.num'/>: <input class="easyui-textbox" id="cust_info"> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="loadCust()"><fmt:message key='search'/></a>
	</div>
	<div style="float:left;width:400px;text-align:center;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateCust()"><fmt:message key='charge.updatecust'/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changePre()"><fmt:message key='charge.changepre'/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="payFor()"><fmt:message key='charge.pay'/></a>
	</div>
	
	<form id="customer" method="post" style="margin-top:35px;">
		<div id="custInfoDiv">
			 <fieldset  style="border:1px solid #e3e3e3;margin-top:10px;">
				<legend><fmt:message key='c.info'/></legend>
				<table style="margin:10px;">
					<tr>
						<td><label><fmt:message key='c.customerid'/>：</label></td>
						<td><input type="text" name="customerId" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.num'/>：</label></td>
						<td><input type="text" name="c_num" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.apid'/>：</label></td>
						<td><input type="text" name="apid" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.hk'/>：</label></td>
						<td><input type="text" name="hk" data-options="disabled:true" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.name'/>：</label></td>
						<td><input type="text" name="customerName" id="customerName" class="easyui-textbox"/></td>
						<td><label><fmt:message key='common.mobile'/>：</label></td>
						<td><input type="text" name="customerMobile" id="customerMobile" class="easyui-textbox"/></td>
						<td><label><fmt:message key='common.email'/>：</label></td>
						<td><input type="text" name="customerEmail" id="customerEmail" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.nationalid'/>：</label></td>
						<td><input type="text" name="nationalId" id="nationalId" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='common.addr'/>：</label></td>
						<td colspan="7"><input type="text" name="customerAddr" class="easyui-textbox" data-options="disabled:true" style="width:100%"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.prestyle'/>：</label></td>
						<td><input type="text" name="prePaySign" id="prePaySign" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.balance'/>：</label></td>
						<td><input type="text" name="customerBalance" id="customerBalance" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.warnthre'/>：</label></td>
						<td><input type="text" name="warnThre" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.hushu'/>：</label></td>
						<td><input type="text" name="peoplecnt" data-options="disabled:true" class="easyui-textbox"/></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</form>
	
	<div style="margin:10px 0px;">
		<span><fmt:message key='m.info'/></span>
		<span style="margin-left:20px;"><fmt:message key='charge.newprice'/></span>
		<select class="easyui-combobox" panelHeight="auto" editable=false style="width:132px" id="price">
		<c:forEach var="p" items="${price_list }">
		<option value="${p.pid }">${p.priceKindName }</option>
		</c:forEach>
	</select>
	</div>
	<table id="custMeters" style="width:100%;height:100px;"></table>
	
	<div style="margin-top:10px;">
		<p><fmt:message key='charge.payinfo'/></p>
	</div>
	<table id="payInfoTab" style="width:100%;height:100px;"></table>
	
	<div style="margin-top:10px;">
		<p><fmt:message key='charge.deinfo'/></p>
	</div>
	<table id="costInfoTab" style="width:100%;height:100px;"></table>
	
<!-- 	<div id="meterCurveWin"></div> -->
	<div id="meterChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
<script type="text/javascript">
$(function(){
	$('#custMeters').datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
	    columns:[[
	        {field:'pid',title:'ID',hidden:true},
			{field:'gprs',title:'<fmt:message key='gprs'/>',width:40},
	        {field:'qfh',title:'<fmt:message key='m.qfh'/>',width:40},
	      	{field:'steelNum',title:'<fmt:message key='m.steel'/>',width:40},
	      	{field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:60},
	      	{field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:40},
	      	{field:'meterSolid',title:'<fmt:message key='m.solid'/>',width:40,formatter:function(value,row,index){
	      		if(value==1){
	      			return "<fmt:message key='m.solidyes'/>";
	      		}else{
	      			return "<fmt:message key='m.solidno'/>";
	      		}
	      	}},
	      	{field:'mk',title:'<fmt:message key='m.mk'/>',width:40},
	      	{field:'pk',title:'<fmt:message key='m.pk'/>',width:40},
	      	{field:'valveState',title:'<fmt:message key='m.vstate'/>',width:30,formatter:function(value,row,index){
	      		if(value==1){
	      			return "<fmt:message key='common.open'/>";
	      		}else{
	      			if(value == 0){
	        			  return "<fmt:message key='common.close'/>";
	        		  }else{
	        			  return "<fmt:message key='common.exception'/>";
	        		  }
	      		}
	      	}},
	      	{field:'deductionStyle',title:'<fmt:message key='m.destyle'/>',width:40,formatter:function(value,row,index){
	        	  if(value == 1){
	        		  return "<fmt:message key='m.deread'/>";
	        	  }else{
	        		  return "<fmt:message key='m.noderead'/>";
	        	  }
	        }},
	      	{field:'valveOffthre',title:'<fmt:message key='m.valveoffthre'/>',width:40},
	      	{field:'timerSwitch',title:'<fmt:message key='m.switch'/>',width:40,formatter:function(value,row,index){
	        	  if(value == 1){
	        		  return "<fmt:message key='common.open'/>";
	        	  }else{
	        		  return "<fmt:message key='common.close'/>";
	        	  }
	        }},
	      	{field:'timer',title:'<fmt:message key='m.timer'/>',width:40},
	      	{field:'deRead',title:'<fmt:message key='m.deread'/>',width:40},
	      	{field:'destartread',title:'<fmt:message key='m.destartread'/>',width:40},
	      	{field:'readdata',title:'<fmt:message key='m.readdata'/>',width:40},
	        {field:'readtime',title:'<fmt:message key='m.readtime'/>',width:40},
	      	{field:'changend',title:'<fmt:message key='m.changeend'/>',width:40},
	      	{field:'action',title:'<fmt:message key='common.action'/>',width:130,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a class='operateHref' onclick='openValue("+row.pid+","+index+")'><fmt:message key='m.open'/></a>"
				+"<a class='operateHref' onclick='updatePrice("+row.pid+","+index+")'><fmt:message key='charge.updateprice'/> </a>"
				+"<a class='operateHref' onclick='updateDeread("+row.pid+","+index+","+row.deRead+")'>更新扣费读数 </a>"
				+"<a class='operateHref' onclick='draw("+row.pid+","+index+")'><fmt:message key='charge.draw'/></a>";
	  		}}
	    ]],
	});
	// 缴费信息
	$('#payInfoTab').datagrid({
	   	striped:true,
		fitColumns:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
	    columns:[[
	        {field:'pid',title:'ID',hidden:true},
			{field:'c_num',title:'<fmt:message key='c.num'/>',width:60},
// 	        {field:'customerId',title:'用户ID',width:60},
	      	{field:'customerName',title:'<fmt:message key='c.name'/>',width:60},
	      	{field:'customerAddr',title:'<fmt:message key='common.addr'/>',width:60},
	      	{field:'amount',title:'<fmt:message key='charge.amount'/>',width:60},
	      	{field:'actionTime',title:'<fmt:message key='charge.paytime'/>',width:60},
	      	{field:'adminName',title:'<fmt:message key='charge.payadmin'/>',width:60},
	      	{field:'action',title:'<fmt:message key='common.action'/>',width:90,halign:'center',align:'center',formatter: function(value,row,index){
				return  "<c:if test="${menus['undo']=='t'}"><a href='#' class='operateHref' onclick='cancelPay("+row.pid+","+index+")' ><fmt:message key='undo'/></a></c:if><a href='#' class='operateHref' onclick='chargeprint("+row.pid+")' ><fmt:message key='charge.chargeprint'/></a>"+
				"<a href='#' class='operateHref' onclick='chargedetailprint("+row.pid+")'><fmt:message key='charge.detailprint'/></a>";
	  		}}
	    ]]
	});
	//扣费信息
	$('#costInfoTab').datagrid({
	   	striped:true,
		fitColumns:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
	    columns:[[
	        {field:'mdl_id',title:'ID',hidden:true},
	        {field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:60},
	      	{field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:60},
	      	{field:'steelNum',title:'<fmt:message key='m.steel'/>',width:60},
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
		    {field:'minusderead',title:'减免',width:80},
		    {field:'tovirtual',title:'转到虚表',width:80},
	        {field:'demoney',title:'<fmt:message key='demoney'/>',width:80}
	        <c:if test="${menus['undo']=='t'}">,
	      	{field:'action',title:'<fmt:message key='common.action'/>',width:90,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='cancleCost("+row.mdl_id+","+index+")' ><fmt:message key='undo'/></a>"
				+"<a class='operateHref' onclick='minusDeread("+row.mdl_id+","+index+")'>减免</a>"
				+"<a class='operateHref' onclick='toVirtual("+row.mdl_id+","+index+")'>转到虚表</a>";;
	  		}}
	        </c:if>
	    ]],
	});
});

	var pid = 0;
	function loadCust(){
		var custId = $('#cust_info').textbox("getValue");
		var nbrId = $('#sel_neibours').combobox('getValue');
		if(nbrId != "" && custId != ""){
			$.ajax({
				url : "${path}/charge/custinfo.do",
				data : {
					nbrId : nbrId,
					custId : custId
				},
				dataType : "json",
				success : function(data) {
					if(data.pid > 0){
						//加载form数据
						pid = data.pid;
						$('#customer').form('load', data);
						if(data.prePaySign == 1){
							$('#prePaySign').textbox('setValue','<fmt:message key='c.pre'/>');
						}else{
							$('#prePaySign').textbox('setValue','<fmt:message key='c.post'/>');
						}
						$('#custMeters').datagrid({
							url:"${path}/charge/custMeters.do",
							queryParams: {
								custId:data.pid
							}
						});
						$('#payInfoTab').datagrid({
							url:"${path}/charge/payInfoContent.do",
							queryParams: {
								custId:data.pid
							}
						});
						$('#costInfoTab').datagrid({
							url:"${path}/charge/costInfoContent.do",
							queryParams: {
								custId:data.pid
							}
						});
					}else{
						pid = 0;
						$.messager.alert("Info","<fmt:message key='c.nocustomer'/>");
					}
				}
			});
		}else{
			$.messager.show({
				title : 'Info',
				msg : '<fmt:message key='common.enterinfo'/>',
				showType : 'slide'
			});
		}
	}
	function updateCust() {
		if(pid == 0){
			$.messager.show({
				title : 'Info',
				msg : '<fmt:message key='common.selectcustomer'/>',
				showType : 'slide'
			});
			return;
		}
		var customerName =$("#customerName").textbox("getValue");
		var customerEmail =$("#customerEmail").textbox("getValue");
		var customerMobile =$("#customerMobile").textbox("getValue");
		var nationalId =$("#nationalId").textbox("getValue");
		
		$.messager.confirm('<fmt:message key='charge.updatecust'/>', '<fmt:message key='c.name'/>：'+customerName+'</br><fmt:message key='common.mobile'/>：'+customerMobile+'</br><fmt:message key='common.email'/>：'+customerEmail+'</br><fmt:message key='c.nationalid'/>：'+nationalId+'</br>', function(r) {
			if (r) {
				//更新用户资料
				$.ajax({
            		type:"POST",
    	    		url:"${path}/charge/updateCustInfo.do",
    	    		data:{
    	    			pid:pid,
    	    			customerName:customerName,
    	    			customerEmail:customerEmail,
    	    			customerMobile:customerMobile,
    	    			nationalId:nationalId
    	    		},
    	    		dataType:"json",
    	    		success:function(data){
    	    			if(data == 1){
    	    				$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.updateok'/>',
								showType : 'slide'
							});
    	    			}else{
    	    				$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.updatefail'/>',
								showType : 'slide'
							});
    	    			}
    	    		}
    	    	});
			}
		});
	}
	function changePre() {
		if(pid == 0){
			$.messager.show({
				title : 'Info',
				msg : '<fmt:message key='common.selectcustomer'/>',
				showType : 'slide'
			});
			return;
		}
		var prePaySign = $('#prePaySign').textbox('getValue');
		var pre =0;
		var prestr;
		if(prePaySign =="<fmt:message key='c.pre'/>"){
			pre = 0;
			prestr = '<fmt:message key='c.post'/>';
		}else{
			pre = 1;
			prestr = '<fmt:message key='c.pre'/>';
		}
		//预后付费转换
		$.messager.confirm('<fmt:message key='confirm'/>', '<fmt:message key='common.confirmupdate'/>'+prestr+'?', function(r) {
			if (r) {
				$.ajax({
					url : "${path}/charge/updatePrepaySign.do",
					data : {
						custId : pid,
						prePaySign : pre
					},
					dataType : "json",
					success : function(data) {
						if (data > 0) {
							$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.updateok'/>',
								showType : 'slide'
							});
							$('#prePaySign').textbox('setValue',prestr);
						}
					}
				});
			}
		});

	}
	function payFor() {
		//交费
		if(pid == 0){
			$.messager.show({
				title : 'Info',
				msg : '<fmt:message key='common.selectcustomer'/>',
				showType : 'slide'
			});
			return;
		}
		var prePaySign = $('#prePaySign').textbox('getValue');
		if(prePaySign !="<fmt:message key='c.pre'/>"){
			$.messager.alert('Error','<fmt:message key='charge.posterror'/>');
			return;
		}
		$.messager.prompt('Info', '<fmt:message key='charge.payamount'/>:', function(r){
			if (r){
				var amount = parseFloat(r);
				if(amount.toString() != "NaN" && amount > 0){
					$.ajax({
						url : "${path}/charge/pay.do",
						data : {
							c_id : pid,
							amount : amount
						},
						dataType : "json",
						success : function(data) {
							if (data.balance > 0) {
								$.messager.show({
									title : 'Info',
									msg : '<fmt:message key='charge.chargeok'/>',
									showType : 'slide'
								});
								window.open("${path}/charge/charge/printcharge.do?cplid="+data.cplid,"_blank");
								$('#customerBalance').textbox('setValue',data.balance);
							}else{
								$.messager.show({
									title : 'Info',
									msg : '<fmt:message key='charge.chargefail'/>',
									showType : 'slide',
									timeout : 0
								});
							}
						}
					});
				}else{
					$.messager.alert('Error','<fmt:message key='charge.balanceerror'/>');
				}
				
			}
		});
	}
	
	function chargeprint(cplid){
		window.open("${path}/charge/charge/printcharge.do?cplid="+cplid,"_blank");
	}
	
	function chargedetailprint(cplid){
		window.open("${path}/charge/charge/printdetailcharge.do?cplid="+cplid+"&cid="+pid,"_blank");
	}
	
	function openValue(mid,index) {
		$.messager.confirm('<fmt:message key='confirm'/>', '<fmt:message key='charge.confirmopen'/>?', function(r) {
			if (r) {
				$.ajax({
					type:"POST",
					url:"${path}/readme/valve/valvecontrol.do",
					dataType:"json",
					data:{
						m_id:mid,
						control:1
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
					$.messager.progress('close');
					clearInterval(interval);
					$.messager.show({
						title : 'Info',
						msg : '<fmt:message key='charge.openok'/>',
						showType : 'slide'
					});
// 					$.messager.alert('操作结果',"完成个数:"+data.completecount+"\r\n异常个数:"+data.errorcount,'info'); 
					if(data.completecount+data.errorcount == 1){
						//单个表
						$("#custMeters").datagrid('updateRow', {index:index,row:{valveState:data.switch_}});
					}
				}
			}
		});
	}

	function updatePrice(mid,index) {
		var priceId = $('#price').combobox('getValue');
		var priceName = $('#price').combobox('getText');
		$.messager.confirm('<fmt:message key='confirm'/>', '<fmt:message key='charge.updatepk'/></br>'+priceName+'?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "${path}/charge/updatePrice.do",
					dataType : "json",
					data : {
						meterId : mid,
						priceId : priceId
					},
					success : function(data) {
						if (data == 1) {
							$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.updateok'/>',
								showType : 'slide'
							});
							$("#custMeters").datagrid('updateRow', {index:index,row:{pk:priceName}});
						} else {
							$.messager.show({
								title : 'Info',
								msg : '操作失败',
								showType : 'slide',
								timeout : 0
							});
						}
					}
				});
			}
		});
	}
	
	function updateDeread(mid,index_,old_deread){
		$.messager.prompt('更新扣费读数', '请输入新的扣费读数', function(r){
	        if (r){
				if(r >= 0){
					$.ajax({
						type:"POST",
						url:"${path}/charge/updateDeread.do",
						dataType:"json",
						data:{
							m_id:mid,
							deread:r,
							old:old_deread
						},
						success:function(data){
							if (data == 1) {
								$.messager.show({
									title : 'Info',
									msg : '<fmt:message key='common.updateok'/>',
									showType : 'slide'
								});
								$("#custMeters").datagrid('updateRow', {index:index_,row:{deRead:r}});
							} else {
								$.messager.show({
									title : 'Info',
									msg : '操作失败',
									showType : 'slide'
								});
							}
						}
					});
				}else{
					 $.messager.alert('Error','请输入正确的扣费读数');
				}
	        }
	    });
	}
	
	function meterQX(meterId) {
		//水表曲线
		$('#meterCurveWin').window({
			href : '${path}/charge/meterCurve.do',
			width : 600,
			height : 450,
			minimizable : false,
			maximizable : false,
			title : '<fmt:message key='charge.draw'/>'
		});
	}
	function cancelPay(id,index_) {
		
		var amount = $('#payInfoTab').datagrid('getRows')[index_]["amount"];
		var time = $('#payInfoTab').datagrid('getRows')[index_]["actionTime"];
		
		$.messager.confirm('<fmt:message key='confirm'/>', '<fmt:message key='common.confirmundo'/></br>'+time+'</br><fmt:message key='charge.pay'/>'+amount+'?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "${path}/cahrge/canclePay.do",
					dataType : "json",
					data : {
						'custPayId' : id
					},
					success : function(data) {
						if (data.state == "succ") {
							$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.undook'/>',
								showType : 'slide',
								timeout : 3000
							});
// 							$('#payInfoTab').datagrid("deleteRow",index_);
							$('#payInfoTab').datagrid({
								url:"${path}/charge/payInfoContent.do",
								queryParams: {
									custId:pid
								}
							});
							$('#customerBalance').textbox('setValue',$('#customerBalance').textbox('getValue')-amount);
							
						} else {
							$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.undofail'/>',
								showType : 'slide',
								timeout : 0
							});
						}
					}
				});
			}
		});
	}

	function cancleCost(id,index_) {
		var demoney = $('#costInfoTab').datagrid('getRows')[index_]["demoney"];
		var time = $('#costInfoTab').datagrid('getRows')[index_]["meterreadtime"];
		
		$.messager.confirm('<fmt:message key='confirm'/>', '<fmt:message key='common.confirmundo'/></br>'+time+'</br><fmt:message key='demoney'/>'+demoney+'?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "${path}/charge/cancleCost.do",
					dataType : "json",
					data : {
						'meterDeLogId' : id
					},
					success : function(data) {
						if (data.state == "succ") {
							$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.undook'/>',
								showType : 'slide'
							});
// 							$('#costInfoTab').datagrid("deleteRow",index_);
							$('#costInfoTab').datagrid({
								url:"${path}/charge/costInfoContent.do",
								queryParams: {
									custId:pid
								}
							});
							$('#customerBalance').textbox('setValue',$('#customerBalance').textbox('getValue')+demoney);
							
						} else {
							$.messager.show({
								title : 'Info',
								msg : '<fmt:message key='common.undofail'/>',
								showType : 'slide',
								timeout : 0
							});
						}
					}
				});
			}
		});
	}
	
	function minusDeread(mdlid,index_) {
		var changeend = $('#costInfoTab').datagrid('getRows')[index_]["changeend"];
		var meterread = $('#costInfoTab').datagrid('getRows')[index_]["meterread"];
		var lastderead = $('#costInfoTab').datagrid('getRows')[index_]["lastderead"];
		var yl = 0;
		if(changeend > 0){
   	  		yl = meterread+changeend-lastderead;
    	}else{
    		yl = meterread-lastderead;
    	}
		$.messager.prompt('水费减免', '请输入水费减免吨数', function(r){
	        if (r){
				if(r >= 0 && r <= yl){
					$.ajax({
						type:"POST",
						url:"${path}/charge/minusDeread.do",
						dataType:"json",
						data:{
							mdlid:mdlid,
							minus:r
						},
						success:function(data){
							if (data == 1) {
								loadCust();
							} else {
								$.messager.show({
									title : 'Info',
									msg : '操作失败',
									showType : 'slide'
								});
							}
						}
					});
				}else{
					 $.messager.alert('Error','请输入正确的减免吨数');
				}
	        }
	    });
	}
	
	function toVirtual(mdlid,index_) {
		var changeend = $('#costInfoTab').datagrid('getRows')[index_]["changeend"];
		var meterread = $('#costInfoTab').datagrid('getRows')[index_]["meterread"];
		var lastderead = $('#costInfoTab').datagrid('getRows')[index_]["lastderead"];
		var yl = 0;
		if(changeend > 0){
   	  		yl = meterread+changeend-lastderead;
    	}else{
    		yl = meterread-lastderead;
    	}
		$.messager.prompt('转到虚表', '请输入转到虚表吨数', function(r){
	        if (r){
				if(r >= 0 && r <= yl){
					$.ajax({
						type:"POST",
						url:"${path}/charge/toVirtual.do",
						dataType:"json",
						data:{
							mdlid:mdlid,
							tovirtual:r
						},
						success:function(data){
							if (data == 1) {
								loadCust();
							} else {
								$.messager.show({
									title : 'Info',
									msg : '操作失败',
									showType : 'slide'
								});
							}
						}
					});
				}else{
					 $.messager.alert('Error','请输入正确的转到虚表吨数');
				}
	        }
	    });
	}
	
	var option = {
			title:{
				text:"扣费读数"
			},
			tooltip:{
				show : true
			},
			legend:{
				data : ['用量']
			},
			xAxis:[{
	            	type : 'category',
	            	data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	        }],
			yAxis:[{
				type:'value'
			}],
			series:[{
					"name":"用量",
					"type":"bar",
					"data":[0,0,0,0,0,0,0,0,0,0,0,0]
				}
			]
		};
		// 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
		
		var myChart;
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                myChart = ec.init(document.getElementById('meterChart')); 
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        function draw(mid,index){
        	var steelNum = $('#custMeters').datagrid('getRows')[index]["steelNum"];
        	$.ajax({
				type:"POST",
				url:"${path}/charge/charge/draw.do",
				dataType:"json",
				data:{
					mid : mid
				},
				success:function(data){
					option.title.text = steelNum+"扣费读数";
					option.series[0].data = data.yl;
    				myChart.setOption(option); 
				}
			});
        }
</script>
</body>
</html>