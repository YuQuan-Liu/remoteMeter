<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调整单价</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">

function changePrice(){

	var old_ = $("#pk_old").combobox("getValue");
	var new_ = $("#pk_new").combobox("getValue");
	
	var old_s = $("#pk_old").combobox("getText");
	var new_s = $("#pk_new").combobox("getText");

	if(old_ == ""){
		$.messager.alert("提示","旧单价不可为空！");
		return;
	}
	if(new_ == ""){
		$.messager.alert("提示","新单价不可为空！");
		return;
	}
	
	if(old_ == new_){
		$.messager.alert("提示","新旧单价不可相同");
		return;
	}
	$.messager.confirm('提示', '确定要调整单价'+old_s+'到'+new_s+'吗？', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/price/changepk.do',
				type:'post',
				data:{
					old_:old_,
					new_:new_
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"调整成功",
							showType:'slide'
						});
						$('#priceChangeWin').window('close');
					}
				}
			});	
		}
	});
}
</script>
		<div style="padding:10px ">
	    	<table style="margin:0px auto;">
	    		<tr>
	    			<td>原单价：</td>
	    			<td>
	    				<select class="easyui-combobox" name="pk_old" id="pk_old" data-options="panelHeight:'auto'" style="width:148px;">
							<option value="" >请选择旧单价</option>
							<c:forEach var="pk" items="${pk_list }">
							<option value="${pk.pid }">${pk.priceKindName }</option>
							</c:forEach>
						</select>
	    			</td>
	    			<td>新单价：</td>
	    			<td>
	    				<select class="easyui-combobox" name="pk_new" id="pk_new" data-options="panelHeight:'auto'" style="width:148px;">
							<option value="" >请选择新单价</option>
							<c:forEach var="pk" items="${pk_list }">
							<option value="${pk.pid }">${pk.priceKindName }</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	  
	   <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changePrice()">调整单价</a>
	    </div>
</body>
</html>