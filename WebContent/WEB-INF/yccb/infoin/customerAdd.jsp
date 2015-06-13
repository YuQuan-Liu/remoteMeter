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
				<legend>用户信息</legend>
				<table style="margin:0px auto" >
					<tr>
						<td><lable>小区</lable></td>
						<td>
							<select class="easyui-combobox" id="n_id" name="n_id" data-options="onSelect:listGPRS,panelHeight:'auto'" style="width:148px;">
								<option value="">请选择小区</option>
								<c:forEach var="n" items="${neighbor_list }">
								<option value="${n.pid }">${n.neighborName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label>住宅类型</label></td>
						<td>
							<select class="easyui-combobox" name="hk_id" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" selected="selected">多层</option>
								<option value="2" >高层</option>
								<option value="3" >商业</option>
							</select>
						</td>
						<td><label>用户标识</label></td>
						<td><input type="text" name="customerId" class="easyui-textbox" data-options="disabled:true"/></td>
					</tr>
					<tr>
						<td><label>用户号</label></td>
<!-- 						<td><input type="text" id="c_num_" name="c_num" class="easyui-validate" onblur="check_cnum()"/></td> -->
						<td><input class="easyui-textbox" type="text" name="c_num" id="c_num_" 
						data-options="required:true,novalidate:true,onChange:check_cnum,invalidMessage:'用户号格式不正确'" validType="nonValidate[]"/></td>
						<td><label>用户名</label></td>
						<td><input type="text" name="customerName" class="easyui-textbox"/></td>
						<td><label>关联ID</label></td>
						<td><input type="text" name="apid" class="easyui-textbox" id="c_apid" 
						data-options="required:true,novalidate:true,onChange:check_capid,invalidMessage:'关联ID重复'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label>手机</label></td>
						<td><input type="text" name="customerMobile" class="easyui-textbox"/></td>
						<td><label>邮箱</label></td>
						<td><input type="text" name="customerEmail" class="easyui-textbox"/></td>
						<td><label>身份证</label></td>
						<td><input type="text" name="nationalId" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>地址</label></td>
						<td colspan="5"><input type="text" name="customerAddr" id="customerAddr" class="easyui-textbox" style="width:100%"/></td>
					</tr>
					<tr>
						<td><label>后付费</label></td>
						<td>
							<select class="easyui-combobox" name="prePaySign" id="prePaySign" data-options="panelHeight:'auto',onSelect:prepayChange" style="width:148px;">
								<option value="1" selected="selected">预付费</option>
								<option value="0" >后付费</option>
							</select>
						</td>
						<td><label>金额</label></td>
						<td><input type="text" name="customerBalance" id="customerBalance" class="easyui-textbox" value="0"
						data-options="required:true,novalidate:true,onChange:check_balance,invalidMessage:'金额不正确'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label>提醒余额阀值</label></td>
						<td><input type="text" name="warnThre" class="easyui-textbox" value="20"/></td>
<!-- 						<td><label>提醒开关</label></td> -->
<!-- 						<td> -->
<!-- 							<select class="easyui-combobox" name="warnSwitch" data-options="panelHeight:'auto'" style="width:148px;"> -->
<!-- 								<option value="1" >开</option> -->
<!-- 								<option value="0" selected="selected">关</option> -->
<!-- 							</select> -->
<!-- 						</td> -->
						<td><label>提醒方式</label></td>
						<td>
							<select class="easyui-combobox" name="warnStyle" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" >短信</option>
								<option value="0" selected="selected">邮件</option>
							</select>
						</td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="customersubmit" onclick="submitCustomer()">Submit</a>
				</div>
			</fieldset>
		</div>
	</form>
	<form id="meter" method="post">
		<div style="padding:0px 10px;">
			<fieldset style="padding-left: 20px;">
				<legend>表信息</legend>
				<table style="margin:0px auto">
					<tr>
						<td><lable>集中器</lable></td>
						<td>
							<select class="easyui-combobox" id="gprs_id" name="gprs_id" data-options="panelHeight:'auto',valueField:'pid',textField:'gprsaddr'" style="width:148px;">
								<option value="" >请选择集中器</option>
							</select>
						</td>
						<td><label>钢印号</label></td>
						<td><input type="text" name="steelNum" class="easyui-textbox"/></td>
						<td><label>铅封号</label></td>
						<td><input type="text" name="qfh" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>采集器地址</label></td>
						<td><input type="text" name="collectorAddr" id="collectorAddr" class="easyui-textbox" data-options="required:true"/></td>
						<td><label>表地址</label></td>
						<td><input type="text" name="meterAddr" id="meterAddr" class="easyui-textbox" data-options="required:true,onChange:check_maddr"/></td>
						<td><label>关联ID</label></td>
						<td><input type="text" name="apid" class="easyui-textbox" id="m_apid" 
						data-options="required:true,novalidate:true,onChange:check_mapid,invalidMessage:'关联ID重复'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label>单价</label></td>
						<td>
							<select class="easyui-combobox" name="pk_id" id="pk_id" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="" >请选择单价</option>
								<c:forEach var="pk" items="${pk_list }">
								<option value="${pk.pid }">${pk.priceKindName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label>管理表</label></td>
						<td>
							<select class="easyui-combobox" name="mainMeter" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" selected="selected">普通表</option>
								<option value="1" >小区总表</option>
								<option value="2" >楼总表</option>
							</select>
						</td>
						<td><label>供水方式</label></td>
						<td>
							<select class="easyui-combobox" name="suppleMode" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" selected="selected">一次供水</option>
								<option value="2" >二次供水</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>水表类型</label></td>
						<td>
							<select class="easyui-combobox" name="mk_id" data-options="panelHeight:'auto'" style="width:148px;">
<!-- 								<option value="" >请选择水表类型</option> -->
								<c:forEach var="mk" items="${mk_list }">
								<option value="${mk.pid }">${mk.meterTypeName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label>虚实表</label></td>
						<td>
							<select class="easyui-combobox" name="meterSolid" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" >虚表</option>
								<option value="1" selected="selected" >实表</option>
							</select>
						</td>
						<td><label>立户表</label></td>
						<td>
							<select class="easyui-combobox" name="lihu" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" selected="selected" >非立户表</option>
								<option value="1" >立户表</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>阀门</label></td>
						<td>
							<select class="easyui-combobox" name="isValve" id="isValve" data-options="panelHeight:'auto',onSelect:valveChange" style="width:148px;">
								<option value="0" selected="selected" >无</option>
								<option value="1" >有</option>
							</select>
						</td>
						<td><label>有阀结算方式</label></td>
						<td>
							<select class="easyui-combobox" name="deductionStyle" id="deductionStyle" data-options="panelHeight:'auto',disabled:true" style="width:148px;">
								<option value="0" selected="selected" >抄表后不结算</option>
								<option value="1" >抄表后结算</option>
							</select>
						</td>
						<td><label>关阀余额阀值</label></td>
						<td><input type="text" name="valveOffthre" class="easyui-textbox" value="0"/></td>
					</tr>
					<tr>
						<td><label>定时检测</label></td>
						<td>
							<select class="easyui-combobox" name="timerSwitch" id="timerSwitch" style="width:148px;" data-options="panelHeight:'auto',onSelect:timeswitchChange">
								<option value="1" >开</option>
								<option value="0" selected="selected">关</option>
							</select>
						</td>
						<td><label>检测时间</label></td>
						<td><input type="text" name="timer" id="timer" class="easyui-textbox" data-options="disabled:true"/></td>
						<td><label>用水超量值</label></td>
						<td><input type="text" name="overflow" class="easyui-textbox" value="50"/></td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<input type="hidden" id="c_id" name="c_id"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitMeter()">Submit</a>
				</div>
			</fieldset>
		</div>
	</form>
	<script>
	function submitMeter(){
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
							title:"请选择集中器",
							msg:"请选择集中器",
							showType:'slide'
						});
						return false;
					}
					var pk_id = $("#pk_id").combobox("getValue");
					if(pk_id == ""){
						$.messager.show({
							title:"请选择单价",
							msg:"请选择单价",
							showType:'slide'
						});
						return false;
					}
				}else{
					$.messager.alert("提示","请选择用户");
					return false;
				}
			},
			success:function(data){
				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data.success == "true"){
					$("#customer").form('reset');
					$("#meter").form('reset');
					$("#c_id").val();
					$.messager.alert("提示","添加成功");
				}
			}
		});
	}
	function submitCustomer(){
		$("#customer").form('submit',{
			url:"${path}/infoin/customer/add.do",
			onSubmit:function(){
				//check the data ,bad data return false
				return $('#customer').form('validate');
			},
			success:function(data){
				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data.success == "true"){
					$("#customer").form('reset');
					$("#c_id").val(data.add);
					$.messager.alert("提示","添加成功");
				}
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
				title:"请选择集中器",
				msg:"请选择集中器",
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
				msg:"输入采集器",
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
							msg:"表地址已存在",
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
				$("#customerAddr").textbox("setValue",n_name+ldh[0]+"号楼"+ldh[1]+"单元"+ldh[2]);
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
			$.messager.alert('提示','请选择小区！');
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
						msg:"无此用户信息",
						showType:'slide'
					});
				}
			}
		});
	}
	</script>
</body>
</html>