<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>添加用户信息</title>
</head>
<body>
	<form id="customer" method="post">
		<div style="padding:0px 10px;">
			<fieldset style="padding-left: 20px;">
				<legend><fmt:message key='c.info'/></legend>
				<table style="margin:0px auto" >
					<tr>
						<td><lable><fmt:message key='common.neighborName'/></lable></td>
						<td>
							<select class="easyui-combobox" id="n_id" name="n_id" data-options="onSelect:listGPRS,panelHeight:'200'" style="width:148px;">
								<option value=""><fmt:message key='common.choosenei'/></option>
								<c:forEach var="n" items="${neighbor_list }">
								<option value="${n.pid }">${n.neighborName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label><fmt:message key='c.hk'/></label></td>
						<td>
							<select class="easyui-combobox" name="hk_id" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" selected="selected"><fmt:message key='c.hkduo'/></option>
								<option value="2" ><fmt:message key='c.hkgao'/></option>
								<option value="3" ><fmt:message key='c.hkbus'/></option>
							</select>
						</td>
						<td><label><fmt:message key='c.customerid'/></label></td>
						<td><input type="text" name="customerId" class="easyui-textbox" data-options="disabled:true"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.num'/></label></td>
<!-- 						<td><input type="text" id="c_num_" name="c_num" class="easyui-validate" onblur="check_cnum()"/></td> -->
						<td><input class="easyui-textbox" type="text" name="c_num" id="c_num_" 
						data-options="required:true,novalidate:true,onChange:check_cnum,invalidMessage:'<fmt:message key='c.numerror'/>'" validType="nonValidate[]"/></td>
						<td><label><fmt:message key='c.name'/></label></td>
						<td><input type="text" name="customerName" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.apid'/></label></td>
						<td><input type="text" name="apid" class="easyui-textbox" id="c_apid" 
						data-options="required:true,novalidate:true,onChange:check_capid,invalidMessage:'<fmt:message key='c.apidexist'/>'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='common.mobile'/></label></td>
						<td><input type="text" name="customerMobile" class="easyui-textbox"/></td>
						<td><label><fmt:message key='common.email'/></label></td>
						<td><input type="text" name="customerEmail" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.nationalid'/></label></td>
						<td><input type="text" name="nationalId" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='common.addr'/></label></td>
						<td colspan="5"><input type="text" name="customerAddr" id="customerAddr" class="easyui-textbox" style="width:100%"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.prestyle'/></label></td>
						<td>
							<select class="easyui-combobox" name="prePaySign" id="prePaySign" data-options="panelHeight:'auto',onSelect:prepayChange" style="width:148px;">
								<option value="1" selected="selected"><fmt:message key='c.pre'/></option>
								<option value="0" ><fmt:message key='c.post'/></option>
							</select>
						</td>
						<td><label><fmt:message key='c.balance'/></label></td>
						<td><input type="text" name="customerBalance" id="customerBalance" class="easyui-textbox" value="0"
						data-options="required:true,novalidate:true,onChange:check_balance,invalidMessage:'<fmt:message key='c.balanceerror'/>'" validType="nonValidate[]"/></td>
						<td><label><fmt:message key='c.hushu'/></label></td>
						<td><input type="text" name="peoplecnt" id="peoplecnt" class="easyui-textbox" value="4"
						data-options="novalidate:true" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.warnthre'/></label></td>
						<td><input type="text" name="warnThre" class="easyui-textbox" value="20"/></td>
<!-- 						<td><label>提醒开关</label></td> -->
<!-- 						<td> -->
<!-- 							<select class="easyui-combobox" name="warnSwitch" data-options="panelHeight:'auto'" style="width:148px;"> -->
<!-- 								<option value="1" >开</option> -->
<!-- 								<option value="0" selected="selected">关</option> -->
<!-- 							</select> -->
<!-- 						</td> -->
						<td><label><fmt:message key='c.warnstyle'/></label></td>
						<td>
							<select class="easyui-combobox" name="warnStyle" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" selected="selected"><fmt:message key='c.sms'/></option>
								<option value="2" ><fmt:message key='c.email'/></option>
							</select>
						</td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="customersubmit" onclick="submitCustomer()"><fmt:message key='common.submit'/></a>
				</div>
			</fieldset>
		</div>
	</form>
	<form id="meter" method="post">
		<div style="padding:0px 10px;">
			<fieldset style="padding-left: 20px;">
				<legend><fmt:message key='m.info'/></legend>
				<table style="margin:0px auto">
					<tr>
						<td><lable><fmt:message key='gprs'/></lable></td>
						<td>
							<select class="easyui-combobox" id="gprs_id" name="gprs_id" data-options="panelHeight:'200',valueField:'pid',textField:'gprsaddr'" style="width:148px;">
								<option value="" ><fmt:message key='m.selectgprs'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.steel'/></label></td>
						<td><input type="text" name="steelNum" class="easyui-textbox"/></td>
						<td><label><fmt:message key='m.qfh'/></label></td>
						<td><input type="text" name="qfh" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.caddr'/></label></td>
						<td><input type="text" name="collectorAddr" id="collectorAddr" class="easyui-textbox" data-options="required:true"/></td>
						<td><label><fmt:message key='m.maddr'/></label></td>
						<td><input type="text" name="meterAddr" id="meterAddr" class="easyui-textbox" data-options="required:true,onChange:check_maddr"/></td>
						<td><label><fmt:message key='m.apid'/></label></td>
						<td><input type="text" name="apid" class="easyui-textbox" id="m_apid" 
						data-options="required:true,novalidate:true,onChange:check_mapid,invalidMessage:'<fmt:message key='c.apidexist'/>'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.pk'/></label></td>
						<td>
							<select class="easyui-combobox" name="pk_id" id="pk_id" data-options="panelHeight:'200'" style="width:148px;">
								<option value="" ><fmt:message key='common.selectprice'/></option>
								<c:forEach var="pk" items="${pk_list }">
								<option value="${pk.pid }">${pk.priceKindName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label><fmt:message key='m.main'/></label></td>
						<td>
							<select class="easyui-combobox" name="mainMeter" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" selected="selected"><fmt:message key='m.putong'/></option>
								<option value="1" ><fmt:message key='m.nmain'/></option>
								<option value="2" ><fmt:message key='m.loumain'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.supplemode'/></label></td>
						<td>
							<select class="easyui-combobox" name="suppleMode" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" selected="selected"><fmt:message key='m.firstsupple'/></option>
								<option value="2" ><fmt:message key='m.secondsupple'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.mk'/></label></td>
						<td>
							<select class="easyui-combobox" name="mk_id" data-options="panelHeight:'200'" style="width:148px;">
<!-- 								<option value="" >请选择水表类型</option> -->
								<c:forEach var="mk" items="${mk_list }">
								<option value="${mk.pid }">${mk.meterTypeName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label><fmt:message key='m.solid'/></label></td>
						<td>
							<select class="easyui-combobox" name="meterSolid" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" ><fmt:message key='m.solidno'/></option>
								<option value="1" selected="selected" ><fmt:message key='m.solidyes'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.lihu'/></label></td>
						<td>
							<select class="easyui-combobox" name="lihu" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" selected="selected" ><fmt:message key='m.nolihu'/></option>
								<option value="1" ><fmt:message key='m.lihu'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.valve'/></label></td>
						<td>
							<select class="easyui-combobox" name="isValve" id="isValve" data-options="panelHeight:'auto',onSelect:valveChange" style="width:148px;">
								<option value="0" selected="selected" ><fmt:message key='common.nothave'/></option>
								<option value="1" ><fmt:message key='common.have'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.destyle'/></label></td>
						<td>
							<select class="easyui-combobox" name="deductionStyle" id="deductionStyle" data-options="panelHeight:'auto',disabled:true" style="width:148px;">
								<option value="0" selected="selected" ><fmt:message key='m.noderead'/></option>
								<option value="1" ><fmt:message key='m.deread'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.valveoffthre'/></label></td>
						<td><input type="text" name="valveOffthre" class="easyui-textbox" value="0"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.switch'/></label></td>
						<td>
							<select class="easyui-combobox" name="timerSwitch" id="timerSwitch" style="width:148px;" data-options="panelHeight:'auto',onSelect:timeswitchChange">
								<option value="1" ><fmt:message key='common.open'/></option>
								<option value="0" selected="selected"><fmt:message key='common.close'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.timer'/></label></td>
						<td><input type="text" name="timer" id="timer" class="easyui-textbox" data-options="disabled:true" value="60"/></td>
						<td><label><fmt:message key='m.overflow'/></label></td>
						<td><input type="text" name="overflow" class="easyui-textbox" value="50"/></td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<input type="hidden" id="c_id" name="c_id"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitMeter()" id="addmeterbtn"><fmt:message key='common.submit'/></a>
				</div>
			</fieldset>
		</div>
	</form>
	<script>
	function submitMeter(){
		$('#addmeterbtn').linkbutton('disable');
		var c_id = $("#c_id").val();
		$("#meter").form('submit',{
			url:"${path}/infoin/meter/add.do",
			onSubmit:function(){
				//check the data ,bad data return false
				if(!$('#meter').form('validate')){
					return false;
				}
				if(c_id > 0){
					var gprs_id = $("#gprs_id").combobox("getValue");
					if(gprs_id == ""){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='m.selectgprs'/>",
							showType:'slide'
						});
						return false;
					}
					var pk_id = $("#pk_id").combobox("getValue");
					if(pk_id == ""){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.selectprice'/>",
							showType:'slide'
						});
						return false;
					}
				}else{
					$.messager.alert("Info","<fmt:message key='common.selectcustomer'/>");
					return false;
				}
				
				var switch_ = $("#timerSwitch").combobox("getValue");
				var timer = $("#timer").textbox("getValue");
				if(switch_ != 0){
					//判断timer 
					if(timer == ""){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='m.timernull'/>",
							showType:'slide'
						});
						return false;
					}else{
						if(timer >= 1){
							//good
						}else{
							$.messager.show({
								title:"Info",
								msg:"<fmt:message key='m.timerstyle'/>",
								showType:'slide'
							});
							return false;
						}
					}
				}
			},
			success:function(data){
				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data.success == "true"){
					$("#customer").form('reset');
					$("#meter").form('reset');
					$("#c_id").val("");
					$.messager.alert("Info","<fmt:message key='common.addok'/>");
				}
				$('#addmeterbtn').linkbutton('enable');
			}
		});
		
	}
	function submitCustomer(){
		$('#customersubmit').linkbutton('disable');
		$("#customer").form('submit',{
			url:"${path}/infoin/customer/add.do",
			onSubmit:function(){
				//check the data ,bad data return false
				if(!$('#customer').form('validate')){
					return false;
				}
			},
			success:function(data){
				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data.add > 0){
					$("#customer").form('reset');
					$("#c_id").val(data.add);
					$.messager.show({
						title:"Info",
						msg:"<fmt:message key='common.addok'/>",
						showType:'slide'
					});
					if(data.cplid > 0){
						window.open("${path}/charge/charge/printcharge.do?cplid="+data.cplid,"_blank");
					}
				}
				$('#customersubmit').linkbutton('enable');
			}
		});
		
	}
	function listGPRS(){
		var n_id = $("#n_id").combobox("getValue");
		if(n_id != ""){
			$('#gprs_id').combobox('reload','${path}/infoin/neighbor/gprsListContent.do?pid='+n_id);
		}
	}
	
	function prepayChange(){
		var pre = $("#prePaySign").combobox("getValue");
		if(pre == 0){
			$("#customerBalance").textbox("disable");
		}else{
			$("#customerBalance").textbox("enable");
		}
	}
	
	function valveChange(){
		var valve = $("#isValve").combobox("getValue");
		if(valve == 0){
			$("#deductionStyle").textbox("disable");
		}else{
			$("#deductionStyle").textbox("enable");
		}
	}
	
	function timeswitchChange(){
		var switch_ = $("#timerSwitch").combobox("getValue");
		if(switch_ == 0){
			$("#timer").textbox("disable");
		}else{
			$("#timer").textbox("enable");
		}
	}
	function check_balance(){
		var balance = parseFloat($("#customerBalance").textbox("getValue"));
		if(balance.toString() != "NaN" && balance >= 0){
			$("#customerBalance").textbox("disableValidation");
		}else{
			$("#customerBalance").textbox("enableValidation");
		}
	}
	
	function check_mapid(){
		var m_apid = $("#m_apid").textbox("getValue");
		$.ajax({
			type:"POST",
			url:"${path}/infoin/customer/check_mapid.do",
			dataType:"json",
			data:{		
				m_apid:m_apid
			},
			success:function(data){
				if(data == 'true'){
					$("#m_apid").textbox("enableValidation");
				}else{
					$("#m_apid").textbox("disableValidation");
				}
			}
		});
	}
	
	function check_capid(){
		var c_apid = $("#c_apid").textbox("getValue");
		$.ajax({
			type:"POST",
			url:"${path}/infoin/customer/check_capid.do",
			dataType:"json",
			data:{		
				c_apid:c_apid
			},
			success:function(data){
				if(data == 'true'){
					$("#c_apid").textbox("enableValidation");
				}else{
					$("#c_apid").textbox("disableValidation");
				}
			}
		});
	}
	
	function check_maddr(){
		var maddr = $("#meterAddr").textbox("getValue");
		var caddr = $("#collectorAddr").textbox("getValue");
		var gprs_id = $("#gprs_id").combobox("getValue");
		
		if(gprs_id == ""){
			$.messager.show({
				title:"Info",
				msg:"<fmt:message key='m.selectgprs'/>",
				showType:'slide'
			});
			return;
		}

		if(maddr == 0 && caddr == 0){
			return;
		}
		if(caddr == ""){
			$("#meterAddr").textbox("setValue","");
			$.messager.show({
				title:"Info",
				msg:"<fmt:message key='m.caddrnull'/>",
				showType:'slide'
			});
			return;
		}
		if(maddr != "" && caddr != ""){
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
						$("#meterAddr").textbox("setValue","");
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
	$.extend($.fn.validatebox.defaults.rules, {
		nonValidate: {
	        validator: function(value, param){
	            return false;
	        }
	    }
	});
	function check_c_num(c_num){
		if(c_num.trim() != ''){
			var ldh = c_num.split(/[ ,.-]/);
			var len = ldh.length;
			if(len == 3){
				//这个地方还可以加上判断是不是全是数字
				var n_name = $("#n_id").combobox("getText");
				$("#customerAddr").textbox("setValue",n_name+ldh[0]+"<fmt:message key='c.lou'/>"+ldh[1]+"<fmt:message key='c.dy'/>"+ldh[2]);
				$("#c_num_").textbox("disableValidation");
				return true;
			}else{
				$("#c_num_").textbox("enableValidation");
				return false;
			}
		}
	}
	function check_cnum(){
		var n_id = $("#n_id").combobox("getValue");
		var c_num = $("#c_num_").val();
		
		//我操  这个c_num  和母页面中的c_num 冲突。。。   实在一个页面里面   如果在不同的iframe中就没事了
		
		if(n_id == ''){
			$.messager.alert('Info','<fmt:message key='common.choosenei'/>');
			return;
		}
		if(!check_c_num(c_num)){
			return;
		}
		$.ajax({
			type:"POST",
			url:"${path}/infoin/customer/ListCustomer.do",
			dataType:"json",
			data:{
				n_id:n_id,  		
				c_num:c_num
			},
			success:function(data){
				if(data.length > 0){
					if(data[0].pid > 0){
						$("#customer").form("load",data[0]);
						$("#c_id").val(data[0].pid);
						$('#customersubmit').linkbutton('disable');
					}
				}else{
					$('#customersubmit').linkbutton('enable');
					$.messager.show({
						title:"Info",
						msg:"<fmt:message key='c.nocustomer'/>",
						showType:'slide'
					});
				}
			}
		});
	}
	</script>
</body>
</html>