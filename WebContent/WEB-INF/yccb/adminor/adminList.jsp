<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员列表</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#adminListTab').datagrid({
	    url:'${path}/admin/listContent.do',
	    fit:true,
	    queryParams:{},
	    rownumbers:true,
	    border:true,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'adminName',title:'<fmt:message key='common.username'/>',width:100},   
	        {field:'loginName',title:'<fmt:message key='admin.loginName'/>',width:100},
	        {field:'adminMobile',title:'<fmt:message key='common.mobile'/>',width:100},
	        {field:'adminTel',title:'<fmt:message key='common.tel'/>',width:100},   
	        {field:'adminEmail',title:'<fmt:message key='common.email'/>',width:100},
	        {field:'adminAddr',title:'<fmt:message key='common.addr'/>',width:100},
	        {field:'depName',title:'<fmt:message key='areas'/>',width:100},
	        {field:'action',title:'<fmt:message key='common.action'/>',width:150,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='adminDetail("+row.pid+")'><fmt:message key='common.update'/></a> "+
				"<a href='#' class='operateHref' onclick='deleteadmin("+row.pid+","+index+")'><fmt:message key='common.delete'/></a> ";
			}}
	    ]],
	    toolbar: [{
	        text: '<fmt:message key='common.add'/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addWin').window({   
	    		    href:'${path}/admin/addPage.do',
	    		    width:500,   
	    		    height:400,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '<fmt:message key='admin.addadmin'/>'
	    		}); 
	        } 
	    }]
	});
});
function adminDetail(pid){
	$('#addWin').window({   
	    href:'${path}/admin/updatePage.do?pid='+pid,
	    width:500,   
	    height:700,
	    minimizable:false,
	    maximizable:false,
	    title: '<fmt:message key='admin.admininfo'/>'
	}); 
}
function deleteadmin(pid,index_){
	var adminName = $('#adminListTab').datagrid('getRows')[index_]["adminName"];
	$.messager.confirm('Info', '<fmt:message key='common.confirmdelete'/>'+adminName+'？', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/admin/delete.do',
				type:'post',
				data:{
					pid:pid
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.deleteok'/>",
							showType:'slide'
						});
						$('#adminListTab').datagrid('deleteRow',index_);
					}
				}
			});	
		}
	});
}
</script>
	<table id="adminListTab"></table>
	<div id="addWin"></div>
	<div id="updateWin"></div>
</body>
</html>