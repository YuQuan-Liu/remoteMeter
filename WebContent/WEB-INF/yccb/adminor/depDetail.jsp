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
	        {field:'neighborName',title:'小区名',width:100},   
	        {field:'neighborAddr',title:'地址',width:150}
	        <c:if test="${menus['areas']=='t'}">,
	        {field:'action',title:'操作',width:150,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='deleteDetail("+${dep.pid}+","+row.pid+","+index+")'>删除</a> ";
			}}
	        </c:if>
	    ]]
	});
});

function closeWin(){
	$('#depDetailWin').window('close');
}

function deleteDetail(dep_id,n_id,index_){
	$.messager.confirm('提示', '确定要删除选中小区吗？', function(r){
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
							title:"删除小区",
							msg:"删除成功",
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
		$.messager.alert("Info","请选择小区！");
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
					title:"添加小区",
					msg:"添加成功",
					showType:'slide'
				});
				$('#neibourListTab').datagrid('reload');
				$('#addNbr').linkbutton('enable');
			}
		}
	});
}
</script>
	<div style="padding: 10px">
		<input type="hidden" name="valid" value="1" />
		<table style="margin: 0px auto;">
			<tr>
				<td>片区名：</td>
				<td><input class="easyui-textbox" type="text" name="departmentName" data-options="disabled:true" readonly="readonly" value="${dep.departmentName }" /></td>
				<td>备注：</td>
				<td><input class="easyui-textbox" name="remark" type="text" data-options="disabled:true" readonly="readonly" value="${dep.remark }" /></td>
			</tr>
			<c:if test="${menus['areas']=='t'}">
			<tr>
				<td>小区名</td>
				<td>
					<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100%" data-options="panelHeight:'auto'">
					<option value="">请选择添加小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
				</td>
				<td></td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" id="addNbr" onclick="addNbr(${dep.pid})">添加小区</a></td>
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