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
	        {field:'adminName',title:'用户名',width:100},   
	        {field:'loginName',title:'登录名',width:100},
	        {field:'adminMobile',title:'手机',width:100},
	        {field:'adminTel',title:'固话',width:100},   
	        {field:'adminEmail',title:'邮箱',width:100},
	        {field:'adminAddr',title:'地址',width:100},
	        {field:'depName',title:'片区',width:100},
	        {field:'action',title:'操作',width:150,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='adminDetail("+row.pid+")'>修改</a> "+
				"<a href='#' class='operateHref' onclick='deleteadmin("+row.pid+","+index+")'>删除</a> ";
			}}
	    ]],
	    toolbar: [{
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addWin').window({   
	    		    href:'${path}/admin/addPage.do',
	    		    width:500,   
	    		    height:400,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加管理员'
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
	    title: '管理员信息'
	}); 
}
function deleteadmin(pid,index_){
	var adminName = $('#adminListTab').datagrid('getRows')[index_]["adminName"];
	$.messager.confirm('提示', '确定要删除'+adminName+'吗？', function(r){
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
							msg:"删除成功",
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