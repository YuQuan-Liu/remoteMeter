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
	        {field:'basicPriceName',title:'基本单价名',width:80},   
	        {field:'basicPriceFirst',title:'一阶单价',width:80,formatter:numFormatter},   
	        {field:'basicFirstOver',title:'一阶超量',width:80},
	        {field:'basicPriceSecond',title:'二阶单价',width:80,formatter:numFormatter},
	        {field:'basicSecondOver',title:'二阶超量',width:80},
	        {field:'basicPriceThird',title:'三阶单价',width:80,formatter:numFormatter}
	    ]],
	});
})

function numFormatter(val,row){
	return val.toFixed(2);;
}
function closeWin(){
	$('#priceDetailWin').window('close');
}
</script>
		<div style="padding:10px 0 10px 60px">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td>单价名：</td>
	    			<td><input class="easyui-textbox" type="text" name="priceKindName" data-options="disabled:true" readonly="readonly" value="${price.priceKindName }"/></td>
	    			<td>备注：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" type="text" data-options="disabled:true" readonly="readonly" value="${price.remark }"/>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	     <div style="padding:10px 10px 0 10px">
		<table id="basicPriceListTab"></table>
	  </div>
	  
	   <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()"><fmt:message key='main.mm.close'/></a>
	    </div>
</body>
</html>