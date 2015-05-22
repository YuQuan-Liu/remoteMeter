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
								<option value="2" >高层</option>
								<option value="3" >商业</option>
								<option value="1" selected="selected">多层</option>
							</select>
						</td>
						<td><label>用户标识</label></td>
						<td><input type="text" name="customerId" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>用户号</label></td>
						<td><input type="text" id="c_num_" name="c_num" class="easyui-validate" onblur="check_cnum()"/></td>
<!-- 						<td><input type="text" id="c_num" name="c_num" onblur="check_cnum()"/></td> -->
						<td><label>用户名</label></td>
						<td><input type="text" name="customerName" class="easyui-textbox"/></td>
						<td><label>关联ID</label></td>
						<td><input type="text" name="apid" class="easyui-textbox"/></td>
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
						<td colspan="5"><input type="text" name="customerAddr" class="easyui-textbox" style="width:100%"/></td>
					</tr>
					<tr>
						<td><label>后付费</label></td>
						<td>
							<select class="easyui-combobox" name="prePaySign" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" selected="selected">预付费</option>
								<option value="0" >后付费</option>
							</select>
						</td>
						<td><label>金额</label></td>
						<td><input type="text" name="customerBalance" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>余额阀值</label></td>
						<td><input type="text" name="warnThre" class="easyui-textbox"/></td>
						<td><label>提醒开关</label></td>
						<td>
							<select class="easyui-combobox" name="warnSwitch" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="1" >开</option>
								<option value="0" selected="selected">关</option>
							</select>
						</td>
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
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitCustomer()">Submit</a>
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
						<td><input type="text" name="collectorAddr" class="easyui-textbox"/></td>
						<td><label>表地址</label></td>
						<td><input type="text" name="meterAddr" class="easyui-textbox"/></td>
						<td><label>关联ID</label></td>
						<td><input type="text" name="apid" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>单价</label></td>
						<td>
							<select class="easyui-combobox" name="pk_id" data-options="panelHeight:'auto'" style="width:148px;">
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
								<option value="" >请选择水表类型</option>
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
							<select class="easyui-combobox" name="isValve" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" selected="selected" >无</option>
								<option value="1" >有</option>
							</select>
						</td>
						<td><label>有阀结算方式</label></td>
						<td>
							<select class="easyui-combobox" name="deductionStyle" data-options="panelHeight:'auto'" style="width:148px;">
								<option value="0" selected="selected" >抄表后不结算</option>
								<option value="1" >抄表后结算</option>
							</select>
						</td>
						<td><label>关阀余额阀值</label></td>
						<td><input type="text" name="valveOffthre" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>定时检测</label></td>
						<td>
							<select class="easyui-combobox" name="timerSwitch" data-options="panelHeight:'auto'">
								<option value="1" >开</option>
								<option value="0" selected="selected">关</option>
							</select>
						</td>
						<td><label>检测时间</label></td>
						<td><input type="text" name="timer" class="easyui-textbox"/></td>
						<td><label>超量提醒值</label></td>
						<td><input type="text" name="overflow" class="easyui-textbox"/></td>
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
		alert(c_id);
		$("#meter").form('submit',{
			url:"${path}/infoin/meter/add.do",
			onSubmit:function(){
				//check the data ,bad data return false
				if(c_id > 0){
					
				}else{
					$.message.alert("提示","请选择用户");
					return false;
				}
			},
			success:function(data){
				if(data.add == 'true'){
					$.message.alert("提示","添加成功");
				}
			}
		})
	}
	function submitCustomer(){
		$("#customer").form('submit',{
			url:"${path}/infoin/customer/add.do",
			onSubmit:function(){
				//check the data ,bad data return false
			},
			success:function(){
				if(data.add > 0){
					$("#c_id").val(data.add);
					$.message.alert("提示","添加成功");
				}
			}
		})
	}
	function listGPRS(){
		var n_id = $("#n_id").combobox("getValue");
		if(n_id != ""){
			$('#gprs_id').combobox('reload','${path}/infoin/neighbor/gprsListContent.do?pid='+n_id);
		}
	}
	function check_c_num(c_num){
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
				if(data[0].pid > 0){
					$("#c_id").val(data[0].pid);
				}
			}
		});
	}
	</script>
</body>
</html>