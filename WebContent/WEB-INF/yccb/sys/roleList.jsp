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
	    pagination:true,
	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    singleSelect:true,
	    border:false,
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'roleName',title:'角色名称',width:100},   
	        {field:'watercompany',title:'自来水公司',width:100},
	        {field:'systemRole',title:'系统角色',width:100,formatter:TFFormatter},
	        {field:'remark',title:'备注',width:100}
	    ]],
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addRoleWin').window({   
	    		    href:'${path}/sys/role/addPage.do',
	    		    width:467,   
	    		    height:300,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加角色'/* , 
	    		    onLoad:function(){   
	    		        //alert('loaded successfully'); 
	    		    }    */
	    		}); 
	        } 
	    }, '-', { 
	        text: '<fmt:message key="common.update"/>', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	var rows = $('#roleListTab').datagrid('getSelections');
	        	var leng = rows.length;
	        	if(leng==1){
	        		var pid = rows[0].pid;
	        		alert(pid);
	        		$('#updateRoleWin').window({   
		    		    href:'${path}/sys/role/updatePage.do?pid='+pid,
		    		    width:467,   
		    		    height:300,
		    		    minimizable:false,
		    		    maximizable:false,
		    		    title: '更新角色'/* , 
		    		    onLoad:function(){   
		    		        //alert('loaded successfully'); 
		    		    }    */
		    		}); 
	        		
	        	}else if(leng>1){
	        		alert("single selected");
	        	}else{
	        		alert("unselected");
	        	}
	        } 
	    }, '-',{ 
	        text: '<fmt:message key="common.delete"/>', 
	        iconCls: 'icon-remove', 
	        handler: function(){ 
	        	var rows = $('#roleListTab').datagrid('getSelections');
	        	var pids = "";
	        	rows.forEach(function(obj){  
	        	    pids += obj.pid+",";
	        	});
	        	alert(pids);
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
//查询
function query(){
	var queryParams = $('#roleListTab').datagrid('options').queryParams;
	queryParams.uid = 111;
	queryParams.uname = "fdasf";
	$('#roleListTab').datagrid('load');
}
</script>
	<table id="roleListTab"></table>
	<div id="addRoleWin"></div>
	<div id="updateRoleWin"></div>
</body>
</html>