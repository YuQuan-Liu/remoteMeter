<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本单价</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#basicPriceListTab').datagrid({
	    url:'${path}/admin/price/priceListContent.do?priceId=${price.pid}',
	    striped:true,
	    fit:true,
	    rownumbers:true,
// 	    border:false,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
				return 'height:30px;';
		},
	    columns:[[
// 	        {field:'pid',title:'',width:80,checkbox:true},
	        {field:'basicPriceName',title:'<fmt:message key='price.basicname'/>',width:80},   
	        {field:'basicPriceFirst',title:'<fmt:message key='price.first'/>',width:80},   
	        {field:'basicFirstOver',title:'<fmt:message key='price.firstover'/>',width:80},
	        {field:'basicPriceSecond',title:'<fmt:message key='price.second'/>',width:80},
	        {field:'basicSecondOver',title:'<fmt:message key='price.secondover'/>',width:80},
	        {field:'basicPriceThird',title:'<fmt:message key='price.third'/>',width:80},
	        {field:'perYL',title:'<fmt:message key='price.perYL'/>',width:80}
	    ]],
	});
})

function closeWin(){
	$('#priceDetailWin').window('close');
}
</script>
		<div style="padding:10px 0 10px 60px">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td><fmt:message key='price.name'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="priceKindName" data-options="disabled:true" readonly="readonly" value="${price.priceKindName }"/></td>
	    			<td><fmt:message key='common.remark'/>：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" type="text" data-options="disabled:true" readonly="readonly" value="${price.remark }"/>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	    <table id="basicPriceListTab"></table>
	  
	   <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()"><fmt:message key='main.mm.close'/></a>
	    </div>
</body>
</html>