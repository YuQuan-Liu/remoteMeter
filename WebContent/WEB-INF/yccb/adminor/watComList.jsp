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
// 	    pagination:true,
// 	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    border:false,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'companyName',title:'公司名',width:100},   
	        {field:'companyAddr',title:'公司地址',width:100},
	        {field:'mark',title:'公司标识',width:100},
	        
	        {field:'emailHost',title:'邮箱主机',width:100},
	        {field:'emailUser',title:'邮箱用户名',width:100},	
	        {field:'telephone',title:'查询电话',width:100},
	        {field:'payAddr',title:'交费地址',width:100},
	        
	        {field:'remark',title:'备注',width:100}
	    ]],
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#watComAddWin').window({   
	    		    href:'${path}/admin/watcom/addPage.do',
	    		    width:500,
	    		    height:400,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加自来水公司'
	    		}); 
	        } 
	    }, '-', { 
	        text: '<fmt:message key="common.update"/>', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	var rows = $('#watComListTab').datagrid('getSelections');
	        	var leng = rows.length;
	        	if(leng==1){
	        		var pid = rows[0].pid;
	        		$('#watComUpdateWin').window({   
		    		    href:'${path}/admin/watcom/updatePage.do?pid='+pid,
		    		    width:500,   
		    		    height:400,
		    		    minimizable:false,
		    		    maximizable:false,
		    		    title: '更新自来水公司'
		    		}); 
	        		
	        	}else if(leng>1){
	        		$.messager.alert('更新自来水公司','Single selected!','info');
	        	}else{
	        		$.messager.alert('更新自来水公司','Select needed!','info');
	        	}
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