<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改表信息</title>
</head>
<body>
	<form id="meter" method="post">
		<div style="padding:0px 10px;">
			<fieldset style="padding-left: 20px;">
				<legend><fmt:message key='m.info'/></legend>
				<table style="margin:0px auto">
					<tr>
						<td><lable><fmt:message key='gprs'/></lable></td>
						<td><input type="text" name="gprs_name" class="easyui-textbox" value="${mv.gprs }" data-options="disabled:true"/></td>
						<td><label><fmt:message key='m.steel'/></label></td>
						<td><input type="text" name="steelNum" class="easyui-textbox" value="${mv.steelNum }"/></td>
						<td><label><fmt:message key='m.qfh'/></label></td>
						<td><input type="text" name="qfh" class="easyui-textbox" value="${mv.qfh }"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.caddr'/></label></td>
						<td><input type="text" name="collectorAddr" id="collectorAddr"  value="${mv.collectorAddr }" class="easyui-textbox" data-options="required:true"/></td>
						<td><label><fmt:message key='m.maddr'/></label></td>
						<td><input type="text" name="meterAddr" id="meterAddr" value="${mv.meterAddr }" class="easyui-textbox" data-options="required:true,onChange:check_maddr"/></td>
						<td><label><fmt:message key='m.apid'/></label></td>
						<td><input type="text" name="apid" class="easyui-textbox" id="m_apid"  value="${mv.apid }"
						data-options="required:true,novalidate:true,onChange:check_mapid,invalidMessage:'<fmt:message key='c.apidexist'/>'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
<!-- 						<td><label>单价</label></td> -->
<!-- 						<td> -->
<!-- 							<select class="easyui-combobox" name="pk_id" data-options="panelHeight:'200'" style="width:148px;"> -->
<%-- 								<c:forEach var="pk" items="${pk_list }"> --%>
<%-- 								<option value="${pk.pid }" >${pk.priceKindName }</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</td> -->
						<td><label><fmt:message key='m.main'/></label></td>
						<td>
							<select class="easyui-combobox" name="mainMeter" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" <c:if test="${mv.mainMeter == 0 }">selected="selected"</c:if>><fmt:message key='m.putong'/></option>
								<option value="1" <c:if test="${mv.mainMeter == 1 }">selected="selected"</c:if>><fmt:message key='m.nmain'/></option>
								<option value="2" <c:if test="${mv.mainMeter == 2 }">selected="selected"</c:if>><fmt:message key='m.loumain'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.supplemode'/></label></td>
						<td>
							<select class="easyui-combobox" name="suppleMode" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" <c:if test="${mv.suppleMode == 1 }">selected="selected"</c:if>><fmt:message key='m.firstsupple'/></option>
								<option value="2" <c:if test="${mv.suppleMode == 2 }">selected="selected"</c:if>><fmt:message key='m.secondsupple'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.mk'/></label></td>
						<td>
							<select class="easyui-combobox" name="mk_id" data-options="panelHeight:'200'" style="width:148px;">
								<c:forEach var="mk" items="${mk_list }">
								<option value="${mk.pid }" <c:if test="${mv.mk_id == mk.pid}">selected="selected"</c:if>>${mk.meterTypeName }</option>
								</c:forEach>
							</select>
						</td>
						<td><label><fmt:message key='m.solid'/></label></td>
						<td>
							<select class="easyui-combobox" name="meterSolid" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" <c:if test="${mv.meterSolid == 0 }">selected="selected"</c:if>><fmt:message key='m.solidno'/></option>
								<option value="1" <c:if test="${mv.meterSolid == 1 }">selected="selected"</c:if>><fmt:message key='m.solidyes'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.lihu'/></label></td>
						<td>
							<select class="easyui-combobox" name="lihu" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" <c:if test="${mv.lihu < 1 }">selected="selected"</c:if>><fmt:message key='m.nolihu'/></option>
								<option value="1" <c:if test="${mv.lihu > 0 }">selected="selected"</c:if>><fmt:message key='m.lihu'/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.valve'/></label></td>
						<td>
							<select class="easyui-combobox" name="isValve" id="isValve" data-options="panelHeight:'auto',onSelect:valveChange" style="width:148px;">
								<option value="0" <c:if test="${mv.isValve == 0}">selected="selected"</c:if>><fmt:message key='common.nothave'/></option>
								<option value="1" <c:if test="${mv.isValve == 1}">selected="selected"</c:if>><fmt:message key='common.have'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.destyle'/></label></td>
						<td>
							<select class="easyui-combobox" name="deductionStyle" id="deductionStyle" data-options="panelHeight:'auto',<c:if test="${mv.isValve == 0}">disabled:true</c:if>" style="width:148px;">
								<option value="0" <c:if test="${mv.deductionStyle == 0}">selected="selected"</c:if>><fmt:message key='m.noderead'/></option>
								<option value="1" <c:if test="${mv.deductionStyle == 1}">selected="selected"</c:if>><fmt:message key='m.deread'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.valveoffthre'/></label></td>
						<td><input type="text" name="valveOffthre" class="easyui-textbox" value="${mv.valveOffthre }"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='m.switch'/></label></td>
						<td>
							<select class="easyui-combobox" name="timerSwitch" id="timerSwitch" data-options="panelHeight:'auto',onSelect:timeswitchChange" style="width:148px;">
								<option value="1" <c:if test="${mv.timerSwitch == 1}">selected="selected"</c:if>><fmt:message key='common.open'/></option>
								<option value="0" <c:if test="${mv.timerSwitch == 0}">selected="selected"</c:if>><fmt:message key='common.close'/></option>
							</select>
						</td>
						<td><label><fmt:message key='m.timer'/></label></td>
						<td><input type="text" name="timer" id="timer" class="easyui-textbox" value="${mv.timer }" 
						data-options="<c:if test="${mv.timerSwitch == 0}">disabled:true</c:if>"/></td>
						<td><label><fmt:message key='m.overflow'/></label></td>
						<td><input type="text" name="overflow" class="easyui-textbox" value="${mv.overflow }"/></td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<input type="hidden" id="c_id" name="c_id" value="${mv.c_id }"/>
					<input type="hidden" id="pid" name="pid" value="${mv.pid }"/>
					<input type="hidden" id="gprs_id" name="gprs_id" value="${mv.gprs_id }"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitMeter()"><fmt:message key='common.submit'/></a>
				</div>
			</fieldset>
		</div>
	</form>
	<script>
	function submitMeter(){
		$("#meter").form('submit',{
			url:"${path}/infoin/meter/update.do",
			onSubmit:function(){
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
				return $('#meter').form('validate');
			},
			success:function(data){
				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data.update > 0){
					$('#updateMeterWin').window('close');
					$.messager.show({
						title:"Info",
						msg:"<fmt:message key='common.updateok'/>",
						showType:'slide'
					});
				}
			}
		});
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
	
	function check_maddr(){
		var maddr = $("#meterAddr").textbox("getValue");
		var caddr = $("#collectorAddr").textbox("getValue");
		var gprs_id = $("#gprs_id").val();
		
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
	</script>
</body>
</html>