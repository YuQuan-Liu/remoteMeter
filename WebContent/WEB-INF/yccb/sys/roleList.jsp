<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#roleListTab').datagrid({
	    url:'${path}/sys/role/listContent.do',
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
	        {field:'roleName',title:'<fmt:message key='role.name'/>',width:100},   
// 	        {field:'watercompany',title:'自来水公司',width:100},
	        {field:'systemRole',title:'<fmt:message key='role.system'/>',width:100,formatter:TFFormatter},
	        {field:'remark',title:'<fmt:message key='common.remark'/>',width:100},
	        {field:'action',title:'<fmt:message key='common.action'/>',width:100,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='roleDetail("+row.pid+")'><fmt:message key='common.look'/></a> ";
			}}
	    ]],
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addRoleWin').window({   
	    		    href:'${path}/sys/role/addPage.do',
	    		    width:500,   
	    		    height:400,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '<fmt:message key='role.add'/>'
	    		}); 
	        }
	    }]
	});
});
function TFFormatter(val,row){
	if(val=="1")
	return "<fmt:message key='common.yes'/>";
	if(val=="0")
	return "<fmt:message key='common.no'/>";
}

function roleDetail(role_id){
	$('#updateRoleWin').window({   
	    href:'${path}/sys/role/updatePage.do?pid='+role_id,
	    width:500,
	    height:400,
	    minimizable:false,
	    maximizable:false,
	    title: 'Detail' 
	}); 
}
</script>
	<table id="roleListTab"></table>
	<div id="addRoleWin"></div>
	<div id="updateRoleWin"></div>
</body>
</html>