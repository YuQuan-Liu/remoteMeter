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
	        {field:'companyName',title:'<fmt:message key='watcom.name'/>',width:100},   
	        {field:'companyAddr',title:'<fmt:message key='watcom.addr'/>',width:100},
	        {field:'mark',title:'<fmt:message key='watcom.mark'/>',width:100},
	        {field:'emailHost',title:'<fmt:message key='watcom.emailHost'/>',width:100},
	        {field:'emailUser',title:'<fmt:message key='watcom.emailUser'/>',width:100},	
	        {field:'telephone',title:'<fmt:message key='watcom.tel'/>',width:100},
	        {field:'payAddr',title:'<fmt:message key='watcom.payaddr'/>',width:100},
	        
	        {field:'remark',title:'<fmt:message key='common.remark'/>',width:100}
	    ]],
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#watComAddWin').window({   
	    		    href:'${path}/admin/watcom/addPage.do',
	    		    width:500,
	    		    height:600,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '<fmt:message key="watcom.add"/>'
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
		    		    title: '<fmt:message key="watcom.update"/>'
		    		}); 
	        		
	        	}else{
	        		$.messager.alert('Info','Select one record!','info');
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