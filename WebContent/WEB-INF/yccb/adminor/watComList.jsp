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
	    autoRowHeight:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'companyName',title:'公司名',width:100},   
	        {field:'companyAddr',title:'公司地址',width:100},
	        {field:'mark',title:'公司标识',width:100},
	        {field:'remark',title:'备注',width:100}
	       /*  {field:'null',title:'操作',width:100,formatter:
	        	function operFmt(value, rec, index){
		        	if (!rec.pid) {
		        		return '';
		        	}
		        	var href = '';
		        	href += "[<a href='"+rec.pid+"' >";
		        	href += "删除</a>]";
		        	return href;
	        	}	
	        } */
	    ]],
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#watComAddWin').window({   
	    		    href:'${path}/admin/watcom/addPage.do',
	    		    width:400,   
	    		    height:250,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加自来水公司', 
	    		    onLoad:function(){   
	    		        //alert('loaded successfully'); 
	    		    }   
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
		    		    width:400,   
		    		    height:250,
		    		    minimizable:false,
		    		    maximizable:false,
		    		    title: '更新自来水公司', 
		    		    onLoad:function(){   
		    		        //alert('loaded successfully'); 
		    		    }   
		    		}); 
	        		
	        	}else if(leng>1){
	        		$.messager.alert('更新自来水公司','Single selected!','info');
	        	}else{
	        		$.messager.alert('更新自来水公司','Select needed!','info');
	        	}
	        } 
	    }, '-',{ 
	        text: '<fmt:message key="common.delete"/>', 
	        iconCls: 'icon-remove', 
	        handler: function(){ 
	        	var rows = $('#watComListTab').datagrid('getSelections');
	        	var pids = "";
	        	if(rows.length > 0){
	        		$.messager.confirm('删除自来水公司', 'Are you confirm this?', function(r){
	    				if (r){
	    					rows.forEach(function(obj){  
	    		        	    pids += obj.pid+",";
	    		        	});
	    					$.ajax({
	    						   type: "POST",
	    						   url: "some.php",
	    						   data: "name=John&location=Boston",
	    						   success: function(msg){
	    						     alert( "Data Saved: " + msg );
	    						   }
	    						});
	    					//alert('confirmed: '+pids);
	    				}
	    			});
	        	}else{
	        		$.messager.alert('删除自来水公司','Select needed!','info');
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