<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改用户信息</title>
</head>
<body>
	<form id="customer" method="post">
		<div style="padding:0px 10px;">
			<fieldset style="padding-left: 20px;">
				<legend><fmt:message key='c.info'/></legend>
				<table style="margin:0px auto" >
					<tr>
						<td><lable><fmt:message key='common.neighborName'/></lable></td>
						<td><input type="text" name="n_id" class="easyui-textbox" value="${cv.n_name }" data-options="disabled:true"/></td>
						<td><label><fmt:message key='c.hk'/></label></td>
						<td>
							<select class="easyui-combobox" name="hk_id" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="2" <c:if test="${cv.hk_id == '2' }">selected="selected"</c:if>><fmt:message key='c.hkgao'/></option>
								<option value="3" <c:if test="${cv.hk_id == '3' }">selected="selected"</c:if>><fmt:message key='c.hkbus'/></option>
								<option value="1" <c:if test="${cv.hk_id == '1' }">selected="selected"</c:if>><fmt:message key='c.hkduo'/></option>
							</select>
						</td>
						<td><label><fmt:message key='c.customerid'/></label></td>
						<td><input type="text" name="customerId" value="${cv.hk_id}" class="easyui-textbox" data-options="disabled:true"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.num'/></label></td>
						<td><input type="text" id="c_num" name="c_num" value="${cv.c_num }" class="easyui-textbox" data-options="disabled:true"/></td>
						<td><label><fmt:message key='c.name'/></label></td>
						<td><input type="text" name="customerName" value="${cv.customerName }" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.apid'/></label></td>
						<td><input type="text" name="apid" value="${cv.apid }" class="easyui-textbox" id="c_apid" 
						data-options="required:true,novalidate:true,onChange:check_capid,invalidMessage:'<fmt:message key='c.apid'/>'" validType="nonValidate[]"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='common.mobile'/></label></td>
						<td><input type="text" name="customerMobile" value="${cv.customerMobile }" class="easyui-textbox"/></td>
						<td><label><fmt:message key='common.email'/></label></td>
						<td><input type="text" name="customerEmail" value="${cv.customerEmail }" class="easyui-textbox"/></td>
						<td><label><fmt:message key='c.nationalid'/></label></td>
						<td><input type="text" name="nationalId" value="${cv.nationalId }" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='common.addr'/></label></td>
						<td colspan="5"><input type="text" name="customerAddr" value="${cv.customerAddr }" class="easyui-textbox" style="width:100%"/></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td><label>后付费</label></td> -->
<!-- 						<td> -->
<!-- 							<select class="easyui-combobox" name="prePaySign" data-options="panelHeight:'auto'" style="width:148px;"> -->
<%-- 								<option value="1" <c:if test="${cv.prePaySign == '1' }">selected="selected"</c:if>>预付费</option> --%>
<%-- 								<option value="0" <c:if test="${cv.prePaySign == '0' }">selected="selected"</c:if>>后付费</option> --%>
<!-- 							</select> -->
<!-- 						</td> -->
<!-- 						<td><label>金额</label></td> -->
<%-- 						<td><input type="text" name="customerBalance" value="${cv.customerBalance }" class="easyui-textbox"/></td> --%>
<!-- 					</tr> -->
					<tr>
						<td><label><fmt:message key='c.warnthre'/></label></td>
						<td><input type="text" name="warnThre" value="${cv.warnThre }" class="easyui-textbox"/></td>
<!-- 						<td><label>提醒开关</label></td> -->
<!-- 						<td> -->
<!-- 							<select class="easyui-combobox" name="warnSwitch" data-options="panelHeight:'auto'" style="width:148px;"> -->
<%-- 								<option value="1" <c:if test="${cv.warnSwitch == '1' }">selected="selected"</c:if>>开</option> --%>
<%-- 								<option value="0" <c:if test="${cv.warnSwitch == '0' }">selected="selected"</c:if>>关</option> --%>
<!-- 							</select> -->
<!-- 						</td> -->
						<td><label><fmt:message key='c.warnstyle'/></label></td>
						<td>
							<select class="easyui-combobox" name="warnStyle" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" <c:if test="${cv.warnStyle == '1' }">selected="selected"</c:if>><fmt:message key='c.sms'/></option>
								<option value="0" <c:if test="${cv.warnStyle == '2' }">selected="selected"</c:if>><fmt:message key='c.email'/></option>
							</select>
						</td>
						<td><label><fmt:message key='c.hushu'/></label></td>
						<td><input type="text" name="peoplecnt" id="peoplecnt" class="easyui-textbox" value="${cv.peoplecnt }"/></td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<input type="hidden" id="pid" name="pid" value="${cv.pid }"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitCustomer()"><fmt:message key='common.submit'/></a>
				</div>
			</fieldset>
		</div>
	</form>
	<script>
	
	$.extend($.fn.validatebox.defaults.rules, {
		nonValidate: {
	        validator: function(value, param){
	            return false;
	        }
	    }
	});
	
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
	function submitCustomer(){
		$("#customer").form('submit',{
			url:"${path}/infoin/customer/update.do",
			onSubmit:function(){
				return $('#customer').form('validate');
			},
			success:function(data){
				var data = eval('(' + data + ')'); // change the JSON string to javascript object 
				if(data.update > 0){
					$('#updateCustomerWin').window('close');
					$.messager.show({
						title:"Info",
						msg:"<fmt:message key='common.updateok'/>",
						showType:'slide'
					});
				}
			}
		});
	}
	</script>
</body>
</html>