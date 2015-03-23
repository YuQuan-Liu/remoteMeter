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
	    border:false,
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'authorityCode',title:'权限编码',width:100},   
	        {field:'actUrl',title:'操作路径',width:100},
	        {field:'remark',title:'备注',width:100},
	        {field:'pname',title:'父级',width:100}
	    ]],
	    toolbar: [{ 
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addRoleWin').window({   
	    		    href:'${path}/sys/auth/addPage.do',
	    		    width:400,   
	    		    height:250,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加权限', 
	    		    onLoad:function(){   
	    		        //alert('loaded successfully'); 
	    		    }   
	    		}); 
	        } 
	    }, '-', { 
	        text: '修改', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	var rows = $('#roleListTab').datagrid('getSelections');
	        	var leng = rows.length;
	        	if(leng==1){
	        		var pid = rows[0].pid;
	        		alert(pid);
	        		$('#updateRoleWin').window({   
		    		    href:'${path}/sys/role/updatePage.do?pid='+pid,
		    		    width:400,   
		    		    height:250,
		    		    minimizable:false,
		    		    maximizable:false,
		    		    title: '更新管理员', 
		    		    onLoad:function(){   
		    		        //alert('loaded successfully'); 
		    		    }   
		    		}); 
	        		
	        	}else if(leng>1){
	        		alert("single selected");
	        	}else{
	        		alert("unselected");
	        	}
	        } 
	    }, '-',{ 
	        text: '删除', 
	        iconCls: 'icon-remove', 
	        handler: function(){ 
	        	var rows = $('#roleListTab').datagrid('getSelections');
	        	var pids = "";
	        	rows.forEach(function(obj){  
	        	    pids += obj.pid+",";
	        	}) 
	        	alert(pids);
	        } 
	    }]
	});
})
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