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
	    queryParams:{},
	    rownumbers:true,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'departmentName',title:'片区名',width:100},
	        {field:'action',title:'操作',width:150,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='depDetail("+row.pid+")'>查看</a> "+
				"<a href='#' class='operateHref' onclick='deleteDep("+row.pid+","+index+")'>删除</a> ";
			}}
	    ]],
	    toolbar: [{ 
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#depAddWin').window({   
	    		    href:'${path}/admin/dep/addPage.do',
	    		    width:550,   
	    		    height:500,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加片区'
	    		}); 
	        } 
	    }]
	});
})

function deleteDep(pid,index){
	$.messager.confirm('提示', '确定要删除选中片区吗？', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/dep/deletedep.do',
				type:'post',
				data:{pid:pid},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"删除片区",
							msg:"删除成功",
							showType:'slide'
						});
						$('#depListTab').datagrid('deleteRow',index_);
					}
				}
			});	
		}
	});
}

function depDetail(depId){
	$('#depDetailWin').window({   
	    href:'${path}/admin/dep/detail.do?depId='+depId,
	    width:550,
	    height:500,
	    minimizable:false,
	    maximizable:false,
	    title: '片区详情' 
	}); 
}
</script>
</head>
<body>
<table id="depListTab"></table>
<div id="depAddWin"></div>
<div id="depDetailWin"></div>
</body>
</html>