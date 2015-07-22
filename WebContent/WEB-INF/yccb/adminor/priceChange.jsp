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
		$.messager.alert("Info","<fmt:message key='price.oldnull'/>");
		return;
	}
	if(new_ == ""){
		$.messager.alert("Info","<fmt:message key='price.newnull'/>");
		return;
	}
	
	if(old_ == new_){
		$.messager.alert("Info","<fmt:message key='price.oldequalsnew'/>");
		return;
	}
	$.messager.confirm('Info', '<fmt:message key='price.confirmchange'/>？', function(r){
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
							msg:"<fmt:message key='common.updateok'/>",
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
	    			<td><fmt:message key='price.old'/>：</td>
	    			<td>
	    				<select class="easyui-combobox" name="pk_old" id="pk_old" data-options="panelHeight:'200'" style="width:148px;">
							<option value="" ><fmt:message key='price.chooseprice'/></option>
							<c:forEach var="pk" items="${pk_list }">
							<option value="${pk.pid }">${pk.priceKindName }</option>
							</c:forEach>
						</select>
	    			</td>
	    			<td><fmt:message key='price.new'/>：</td>
	    			<td>
	    				<select class="easyui-combobox" name="pk_new" id="pk_new" data-options="panelHeight:'200'" style="width:148px;">
							<option value="" ><fmt:message key='price.chooseprice'/></option>
							<c:forEach var="pk" items="${pk_list }">
							<option value="${pk.pid }">${pk.priceKindName }</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	  
	   <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changePrice()"><fmt:message key='price.changeprice'/></a>
	    </div>
</body>
</html>