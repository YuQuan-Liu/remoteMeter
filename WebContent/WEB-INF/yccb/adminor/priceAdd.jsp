<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加单价</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#basicPriceTab').datagrid('validateRow', editIndex)){
		$('#basicPriceTab').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

$(function(){
	$('#basicPriceTab').datagrid({
	    fit:false,
	    border:false,
	    autoRowHeight:false,
	    onClickCell: function(index, field){
			if (endEditing()){
				$('#basicPriceTab').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
			}
		},
	    columns:[[
	        {field:'basicPriceName',title:'基本单价名',width:80,editor:'text'},   
	        {field:'basicPriceFirst',title:'一阶单价',width:80,editor:'text'},   
	        {field:'basicFirstOver',title:'一阶超量',width:80,editor:'text'},
	        {field:'basicPriceSecond',title:'二阶单价',width:80,editor:'text'},
	        {field:'basicSecondOver',title:'二阶超量',width:80,editor:'text'},
	        {field:'basicPriceThird',title:'三阶单价',width:80,editor:'text'},
	        {field:'operate',title:'操作',width:80}
	    ]],
	    toolbar: [{ 
	        text: '添加基本单价', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#basicPriceTab').datagrid('appendRow',{
	        		basicPriceName: '',
	        		basicPriceFirst: '',
	        		basicFirstOver: '',
	        		basicPriceSecond: '',
	        		basicSecondOver: '',
	        		basicPriceThird: '',
	        		operate:'<a onclick="deleteRow(this)">删除</a>'
	        	});
	        } 
	    }]
	});
	
	/**
	编辑列
	*/
	$.extend($.fn.datagrid.methods, {
		editCell: function(jq,param){
			return jq.each(function(){
				var opts = $(this).datagrid('options');
				var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
				for(var i=0; i<fields.length; i++){
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field){
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
				for(var i=0; i<fields.length; i++){
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		}
	});
})

function submitForm(){
	if($('#priceAddForm').form('validate')){
		$('#priceAddForm').form('submit', {   
		    success: function(data){   
		       if(data=="succ"){
		    	   $('#priceAddWin').window('close');
		    	   $.messager.show({
						title:'添加自来水公司',
						msg:'添加成功',
						showType:'slide',
						timeout:3000
					});
				 	$('#priceListTab').datagrid('reload');
		       }
		    }   
		});  
	}
}
function clearForm(){
	$('#priceAddForm').form('clear');
	}
function deleteRow(obj){
	var tr = $(obj).parent().parent().parent();
	//obj.parent().parent().parent().remove();
	var rowIndex = tr.attr("datagrid-row-index");
	if(rowIndex){
		$('#basicPriceTab').datagrid('deleteRow', rowIndex);  
	}
}
</script>

		<div style="padding:10px 0 10px 60px">
	    <form id="priceAddForm" method="post" action="${path}/admin/watcom/add.do">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td>单价名：</td>
	    			<td><input class="easyui-textbox" type="text" name="priceKindName" data-options="required:true"/></td>
	    			<td>备注：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" type="text"/>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	  </div>
	  
	   <div style="padding:10px 10px 0 10px">
		<table id="basicPriceTab"></table>
	  </div>
	    <div style="text-align:center;padding:5px;margin-bottom: 20px;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.submit'/></a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()"><fmt:message key='common.reset'/></a>
	    </div>
</body>
</html>