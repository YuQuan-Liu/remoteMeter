<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单价列表</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript">
$(function(){
	$('#priceListTab').datagrid({
	    url:'${path}/admin/price/listContent.do',
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
	        {field:'priceKindName',title:'<fmt:message key='price.name'/>',width:100,halign:'center'},
// 	        {field:'watercompany',title:'自来水公司',width:100},
	        {field:'remark',title:'<fmt:message key='common.remark'/>',width:100,halign:'center'},
	        {field:'action',title:'<fmt:message key='common.action'/>',width:100,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='priceDetail("+row.pid+")'><fmt:message key='common.action'/></a> " 
				<c:if test="${menus['basicprice']=='t'}">+
				"<a href='#' class='operateHref' onclick='deleteprice("+row.pid+","+index+")'><fmt:message key='common.delete'/></a> "</c:if>;
			}}
	    ]],
	    <c:if test="${menus['basicprice']=='t'}">
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#priceAddWin').window({   
	    		    href:'${path}/admin/price/priceAddPage.do',
	    		    width:600,
	    		    height:400,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '<fmt:message key='price.add'/>'
	    		}); 
	        } 
	    }, '-', { 
	        text: '<fmt:message key='price.changeprice'/>', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	$('#priceChangeWin').window({   
	    		    href:'${path}/admin/price/pricechange.do',
	    		    width:600,
	    		    height:250,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '<fmt:message key='price.changeprice'/>'
	    		});
	        }
	    }]
		</c:if>
	});
})

function priceDetail(priceId){
	$('#priceDetailWin').window({   
	    href:'${path}/admin/price/priceDetailPage.do?priceId='+priceId,
	    width:600,   
	    height:350,
	    minimizable:false,
	    maximizable:false,
	    title: 'Detail' 
	}); 
}

function deleteprice(pid,index_){
	
	$.messager.confirm('Info', '<fmt:message key='common.confirmdelete'/>?', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/price/deletepk.do',
				type:'post',
				data:{pid:pid},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.deleteok'/>",
							showType:'slide'
						});
						$('#priceListTab').datagrid('deleteRow',index_);
					}
				}
			});	
		}
	});
}
</script>
</head>
<body>
<table id="priceListTab"></table>
<div id="priceAddWin"></div>
<div id="priceChangeWin"></div>
<div id="priceDetailWin"></div>
</body>
</html>