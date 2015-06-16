<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改小区</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">

function submitForm(){
	$('#updateNeighborForm').form('submit', {	
		onSubmit:function(){
			var switch_ = $("#timerSwitch").combobox("getValue");
			var timer = $("#timer").textbox("getValue");
			if(switch_ != 0){
				//判断timer 
				if(timer == ""){
					$.messager.show({
						title:"Info",
						msg:"定时不可以空",
						showType:'slide'
					});
					return false;
				}else{
					var d_h = timer.split(/[-]/);
					if(d_h.length != 2){
						$.messager.show({
							title:"Info",
							msg:"定时格式为:几号(1-31)-几点(0-23)",
							showType:'slide'
						});
						return false;
					}else{
						var d = d_h[0];
						var h = d_h[1];
						if((d >= 1) && (d <= 31) && (h >=0) && (h<=23)){
							//is good
						}else{
							$.messager.show({
								title:"Info",
								msg:"定时格式为:几号(1-31)-几点(0-23)",
								showType:'slide'
							});
							return false;
						}
					}
				}
			}
			return $('#updateNeighborForm').form('validate');
		},
		success: function(data){
			if(data=="succ"){
				$.messager.show({
					title:"Info",
					msg:"更新成功",
					showType:'slide'
				});
				$('#updateNeighborWin').window('close');
				$('#neighborListTab').datagrid('reload');
			}
		}	
	}); 
}
//检查本自来水公司下此小区是否已经存在
function checkNbrName(){
	var name = $("#neighborName").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/infoin/neighbor/searchn_name.do",
		data:{
			n_name:name
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#neighborName").textbox("enableValidation");
			}else{
				$("#neighborName").textbox("disableValidation");
			}
		}
	});
}

$.extend($.fn.validatebox.defaults.rules, {
	nonValidate: {
        validator: function(value, param){
            return false;
        }
    }
});
function timeswitchChange(){
	var switch_ = $("#timerSwitch").combobox("getValue");
	if(switch_ == 0){
		$("#timer").textbox("disable");
	}else{
		$("#timer").textbox("enable");
	}
}
</script>
	<form action="${path}/infoin/neighbor/update.do" id="updateNeighborForm" method="post">
	 	<input type="hidden" name="valid" id="valid" value="${neighbor.valid }"/>
	 	<input type="hidden" name="pid" id="pid" value="${neighbor.pid }"/>
	 	<input type="hidden" name="wcid" id="wcid" value="${neighbor.watercompany.pid }"/>
	 	<div style="padding:10px">
			<table style="margin:0px auto;">
				<tr>
					<td>小区名：</td>
					<td><input class="easyui-textbox" type="text" name="neighborName" id="neighborName" value="${neighbor.neighborName }"
						data-options="required:true,novalidate:true,onChange:checkNbrName,invalidMessage:'小区已存在！'" validType="nonValidate[]"/></td>
				</tr>
				<tr>
					<td>地址：</td>
					<td><input class="easyui-textbox" type="text" name="neighborAddr" value="${neighbor.neighborAddr }" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>有无管理表：</td>
					<td>
					<select class="easyui-combobox" name="mainMeter" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1" <c:if test="${neighbor.mainMeter==1 }">selected="selected"</c:if>>有</option>
							<option value="0" <c:if test="${neighbor.mainMeter==0 }">selected="selected"</c:if>>无</option>
					</select>
					 </td>
				</tr>
				<tr>
					<td>定时抄表开关：</td>
					<td>
						<select class="easyui-combobox" name="timerSwitch" id="timerSwitch" data-options="panelHeight:'auto',onSelect:timeswitchChange" style="width: 80px">
							<option value="1" <c:if test="${neighbor.timerSwitch==1 }">selected="selected"</c:if>>开</option>
							<option value="0" <c:if test="${neighbor.timerSwitch==0 }">selected="selected"</c:if>>关</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>定时抄表时间：</td>
					<td><input class="easyui-textbox" type="text" name="timer" id="timer" value="${neighbor.timer }" data-options="required:true,<c:if test="${neighbor.timerSwitch == 0}">disabled:true</c:if>"/></td>
				</tr>
				<tr>
					<td>抄表IP：</td>
					<td><input class="easyui-textbox" type="text" name="ip" value="${neighbor.ip }" data-options="required:true" value=""/></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td>
					<input class="easyui-textbox" name="remark" value="${neighbor.remark }" data-options="multiline:true" style="height:60px">
					</td>
				</tr>
			</table>
		</div>
		 <div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">更新</a>
		</div>
	</form>
</body>
</html>