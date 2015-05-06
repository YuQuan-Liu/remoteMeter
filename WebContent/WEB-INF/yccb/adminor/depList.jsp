<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>片区列表</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript">
$(function(){
	$('#depListTab').datagrid({
	    url:'${path}/admin/dep/listContent.do',
	    fit:true,
	    pagination:true,
	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    border:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:100},   
	        {field:'companyName',title:'片区名',width:100},   
	        {field:'companyAddr',title:'管理员',width:100},
	        {field:'mark',title:'自来水公司',width:100},
	        {field:'mark',title:'操作',width:100,formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='addGprs("+row.pid+")'>查看</a> ";
			}}
	    ]],
	    toolbar: [{ 
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#depAddWin').window({   
	    		    href:'${path}/admin/dep/addPage.do',
	    		    width:550,   
	    		    height:250,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加片区', 
	    		    onLoad:function(){   
	    		        //alert('loaded successfully'); 
	    		    }   
	    		}); 
	        } 
	    }, '-', { 
	        text: '修改', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	var rows = $('#depListTab').datagrid('getSelections');
	        	var leng = rows.length;
	        	if(leng==1){
	        		var pid = rows[0].pid;
	        		alert(pid);
	        		$('#depUpdateWin').window({   
		    		    href:'${path}/admin/dep/updatePage.do?pid='+pid,
		    		    width:550,   
		    		    height:250,
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
	        	var rows = $('#depListTab').datagrid('getSelections');
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
<table id="depListTab"></table>
<div id="depAddWin"></div>
<div id="depUpdateWin"></div>
</body>
</html>