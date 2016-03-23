<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>片区详情</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
	<script type="text/javascript">
$(function(){
	$('#neibourListTab').datagrid({
	    url:'${path}/admin/dep/nbrTabContent.do?depId=${dep.pid}',
	    striped:true,
	    fit:true,
	    rownumbers:true,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
				return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:50,hidden:true},   
	        {field:'neighborName',title:'<fmt:message key='common.neighborName'/>',width:100},   
	        {field:'neighborAddr',title:'<fmt:message key='common.addr'/>',width:150}
	        <c:if test="${menus['areas']=='t'}">,
	        {field:'action',title:'<fmt:message key='common.action'/>',width:150,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='deleteDetail("+${dep.pid}+","+row.pid+","+index+")'><fmt:message key='common.delete'/></a> ";
			}}
	        </c:if>
	    ]]
	});
});

function closeWin(){
	$('#depDetailWin').window('close');
}

function deleteDetail(dep_id,n_id,index_){
	$.messager.confirm('Info', '<fmt:message key='common.confirmdelete'/>？', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/dep/deletedetail.do',
				type:'post',
				data:{
					dep_id:dep_id,
					n_id:n_id
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.deleteok'/>",
							showType:'slide'
						});
						$('#neibourListTab').datagrid('deleteRow',index_);
					}
				}
			});	
		}
	});
}

function addNbr(dep_id){
	var n_id = $("#neighbor").combobox("getValue");
	if(n_id == ""){
		$.messager.alert("Info","<fmt:message key='dep.choosenei'/>");
		return;
	}

	$('#addNbr').linkbutton('disable');
	$.ajax({
		url:'${path}/admin/dep/adddetail.do',
		type:'post',
		data:{
			dep_id:dep_id,
			n_id:n_id
		},
		success:function(data){
			if(data > 0){
				$.messager.show({
					title:"Info",
					msg:"<fmt:message key='common.addok'/>",
					showType:'slide'
				});
				$('#neibourListTab').datagrid('reload');
			}
		}
	});
	$('#addNbr').linkbutton('enable');
}
</script>
	<div style="padding: 10px">
		<input type="hidden" name="valid" value="1" />
		<table style="margin: 0px auto;">
			<tr>
				<td><fmt:message key='areas'/>：</td>
				<td><input class="easyui-textbox" type="text" name="departmentName" data-options="disabled:true" readonly="readonly" value="${dep.departmentName }" /></td>
				<td><fmt:message key='common.remark'/>：</td>
				<td><input class="easyui-textbox" name="remark" type="text" data-options="disabled:true" readonly="readonly" value="${dep.remark }" /></td>
			</tr>
			<c:if test="${menus['areas']=='t'}">
			<tr>
				<td><fmt:message key='common.neighborName'/></td>
				<td>
					<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100%" data-options="panelHeight:'200'">
					<option value=""><fmt:message key='dep.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
				</td>
				<td></td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" id="addNbr" onclick="addNbr(${dep.pid})"><fmt:message key='dep.addnei'/></a></td>
			</tr>
			</c:if>
		</table>
	</div>
	<table id="neibourListTab"></table>

	<div style="text-align: center; padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()"><fmt:message key='main.mm.close' /></a>
	</div>
</body>
</html>