<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自来水公司列表</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript">
$(function(){
	$('#watComListTab').datagrid({
	    url:'${path}/admin/watcom/listContent.do',
	    fit:true,
	    pagination:true,
	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    border:false,
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'companyName',title:'公司名',width:100},   
	        {field:'companyAddr',title:'公司地址',width:100},
	        {field:'mark',title:'公司标识',width:100}
	    ]],
	    toolbar: [{ 
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#watComAddWin').window({   
	    		    href:'${path}/admin/watcom/addPage.do',
	    		    width:400,   
	    		    height:350,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加自来水公司', 
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
	        		$('#watComUpdateWin').window({   
		    		    href:'${path}/admin/watcom/updatePage.do?pid='+pid,
		    		    width:400,   
		    		    height:350,
		    		    minimizable:false,
		    		    maximizable:false,
		    		    title: '更新自来水公司', 
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
</script>
</head>
<body>
<table id="watComListTab"></table>
<div id="watComAddWin"></div>
<div id="watComUpdateWin"></div>
</body>
</html>