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
				<legend>用户信息</legend>
				<table style="margin:0px auto" >
					<tr>
						<td><lable>小区</lable></td>
						<td><input type="text" name="n_id" class="easyui-textbox" value="${cv.n_name }"/></td>
						<td><label>住宅类型</label></td>
						<td>
							<select class="easyui-combobox" name="hk_id" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="2" <c:if test="${cv.hk_id == '2' }">selected="selected"</c:if>>高层</option>
								<option value="3" <c:if test="${cv.hk_id == '3' }">selected="selected"</c:if>>商业</option>
								<option value="1" <c:if test="${cv.hk_id == '1' }">selected="selected"</c:if>>多层</option>
							</select>
						</td>
						<td><label>用户标识</label></td>
						<td><input type="text" name="customerId" value="${cv.hk_id}" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>用户号</label></td>
						<td><input type="text" id="c_num" name="c_num" value="${cv.c_num }" class="easyui-validate" onblur="check_cnum()"/></td>
<!-- 						<td><input type="text" id="c_num" name="c_num" onblur="check_cnum()"/></td> -->
						<td><label>用户名</label></td>
						<td><input type="text" name="customerName" value="${cv.customerName }" class="easyui-textbox"/></td>
						<td><label>关联ID</label></td>
						<td><input type="text" name="apid" value="${cv.apid }" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>手机</label></td>
						<td><input type="text" name="customerMobile" value="${cv.customerMobile }" class="easyui-textbox"/></td>
						<td><label>邮箱</label></td>
						<td><input type="text" name="customerEmail" value="${cv.customerEmail }" class="easyui-textbox"/></td>
						<td><label>身份证</label></td>
						<td><input type="text" name="nationalId" value="${cv.nationalId }" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>地址</label></td>
						<td colspan="5"><input type="text" name="customerAddr" value="${cv.customerAddr }" class="easyui-textbox" style="width:100%"/></td>
					</tr>
					<tr>
						<td><label>后付费</label></td>
						<td>
							<select class="easyui-combobox" name="prePaySign" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" <c:if test="${cv.prePaySign == '1' }">selected="selected"</c:if>>预付费</option>
								<option value="0" <c:if test="${cv.prePaySign == '0' }">selected="selected"</c:if>>后付费</option>
							</select>
						</td>
						<td><label>金额</label></td>
						<td><input type="text" name="customerBalance" value="${cv.customerBalance }" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>余额阀值</label></td>
						<td><input type="text" name="warnThre" value="${cv.warnThre }" class="easyui-textbox"/></td>
						<td><label>提醒开关</label></td>
						<td>
							<select class="easyui-combobox" name="warnSwitch" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" <c:if test="${cv.warnSwitch == '1' }">selected="selected"</c:if>>开</option>
								<option value="0" <c:if test="${cv.warnSwitch == '0' }">selected="selected"</c:if>>关</option>
							</select>
						</td>
						<td><label>提醒方式</label></td>
						<td>
							<select class="easyui-combobox" name="warnStyle" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" <c:if test="${cv.warnStyle == '1' }">selected="selected"</c:if>>短信</option>
								<option value="0" <c:if test="${cv.warnStyle == '0' }">selected="selected"</c:if>>邮件</option>
							</select>
						</td>
					</tr>
				</table>
				<div style="text-align:center;padding-top:10px;">
					<input type="hidden" id="pid" name="pid" value="${cv.pid }"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitCustomer()">Submit</a>
				</div>
			</fieldset>
		</div>
	</form>
	<script>
	function submitCustomer(){
		$("#customer").form('submit',{
			url:"${path}/infoin/customer/update.do",
			onSubmit:function(){
				//check the data ,bad data return false
			},
			success:function(data){
				if(data.update > 0){
					$.message.alert("提示","修改成功");
				}
			}
		})
	}
	</script>
</body>
</html>