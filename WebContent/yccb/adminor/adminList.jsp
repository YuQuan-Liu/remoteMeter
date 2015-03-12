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
	    url:'${path}/admin/adminlistContent.do',
	    fit:true,
	    pagination:true,
	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    border:false,
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'adminName',title:'用户名',width:100},   
	        {field:'loginName',title:'登录名',width:100},
	        {field:'adminMobile',title:'手机',width:100},
	        {field:'adminTel',title:'固话',width:100},   
	        {field:'adminEmail',title:'邮箱',width:100},
	        {field:'adminAddr',title:'地址',width:100}
	    ]],
	    toolbar: [{ 
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addWin').window({   
	    		    href:'${path}/admin/addPage.do',
	    		    width:400,   
	    		    height:350,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加管理员', 
	    		    onLoad:function(){   
	    		        //alert('loaded successfully'); 
	    		    }   
	    		}); 
	        } 
	    }, '-', { 
	        text: '修改', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	var rows = $('#adminListTab').datagrid('getSelections');
	        	var leng = rows.length;
	        	if(leng==1){
	        		var pid = rows[0].pid;
	        		alert(pid);
	        		$('#updateWin').window({   
		    		    href:'${path}/admin/updatePage.do?pid='+pid,
		    		    width:400,   
		    		    height:350,
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
	        	var rows = $('#adminListTab').datagrid('getSelections');
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
	var queryParams = $('#adminListTab').datagrid('options').queryParams;
	queryParams.uid = 111;
	queryParams.uname = "fdasf";
	$('#adminListTab').datagrid('load');
}
</script>
	<table id="adminListTab"></table>
	<div id="addWin"></div>
	<div id="updateWin"></div>
</body>
</html>