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
var isChecked = undefined;
var bpTab = $('#basicPriceTab');
function endEditing(){
	if (editIndex == undefined){return true;}
	if ($('#basicPriceTab').datagrid('validateRow', editIndex)){
		$('#basicPriceTab').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
$(function(){
	bpTab.datagrid({
	    fit:false,
	    singleSelect:true,
	    autoRowHeight:false,
	    onClickRow: function(index,row){
	    	$('#basicPriceTab').datagrid('endEdit', index);
		},
		onDblClickRow: function(index,row){
	    	if (editIndex != index){
	            if (endEditing()){
	                $('#basicPriceTab').datagrid('selectRow', index).datagrid('beginEdit', index);
	                editIndex = index;
	            } else {
	                $('#basicPriceTab').datagrid('selectRow', editIndex);
	            }
	        }
	    	bpTab.datagrid('beginEdit', index);
		},
	    columns:[[
	        {field:'pid',title:'',width:80,checkbox:true},
	        {field:'basicPriceName',title:'<fmt:message key='price.basicname'/>',width:80,editor:{type:'validatebox'}},
	        {field:'basicPriceFirst',title:'<fmt:message key='price.first'/>',width:80,editor:{type:'numberbox',options:{precision:2}}},   
	        {field:'basicFirstOver',title:'<fmt:message key='price.firstover'/>',width:80,editor:{type:'numberbox',options:{precision:0}}},
	        {field:'basicPriceSecond',title:'<fmt:message key='price.second'/>',width:80,editor:{type:'numberbox',options:{precision:2}}},
	        {field:'basicSecondOver',title:'<fmt:message key='price.secondover'/>',width:80,editor:{type:'numberbox',options:{precision:0}}},
	        {field:'basicPriceThird',title:'<fmt:message key='price.third'/>',width:80,editor:{type:'numberbox',options:{precision:2}}}
	    ]],
	    data:[{"basicPriceName":"","basicPriceFirst":0,"basicFirstOver":0,"basicPriceSecond":0,"basicSecondOver":0,"basicSecondOver":0,"basicPriceThird":0}],
	    toolbar: [{
	        text: '<fmt:message key='common.add'/>', 
	        iconCls: 'icon-add', 
	        handler: function() {
	        	$('#basicPriceTab').datagrid('insertRow',{
	        		row: {"basicPriceName":"","basicPriceFirst":0,"basicFirstOver":0,"basicPriceSecond":0,"basicSecondOver":0,"basicSecondOver":0,"basicPriceThird":0}
	        	});
	        } 
	    },
	    '-',{ 
	        text: '<fmt:message key='common.delete'/>', 
	        iconCls: 'icon-remove', 
	        handler: function(){
	        	var row = bpTab.datagrid('getSelected');
	        	if(row){
	        		var rowIndex = bpTab.datagrid('getRowIndex',row);
		        	bpTab.datagrid('deleteRow', rowIndex);  
	        	}else{
	        		$.messager.show({
						title:'Info',
						msg:'<fmt:message key='common.chooserecord'/>',
						showType:'slide',
						timeout:3000
					});
	        	}
	        } 
	    }]
	});
	
	
})

function submitForm(){
	if(getBasicData()){
		$('#priceAddForm').form('submit', {
			onSubmit:function(){
				return $('#priceAddForm').form('validate');
			},
		    success: function(data){   
		       if(data=="succ"){
		    	   $('#priceAddWin').window('close');
		    	   $.messager.show({
						title:'Info',
						msg:'<fmt:message key='common.addok'/>',
						showType:'slide',
						timeout:3000
					});
				 	$('#priceListTab').datagrid('reload');
		       }
		    }   
		});
	}
}

function checkPKName(){
	var name = $("#priceKindName").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/admin/price/checkpkname.do",
		data:{
			pkname:name
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#priceKindName").textbox("enableValidation");
			}else{
				$("#priceKindName").textbox("disableValidation");
			}
		}
	});
}

$.extend($.fn.validatebox.defaults.rules, {
	nonValidate: {
        validator: function(value, param){
            return false;
        }
    }
});

var bps = [];
//获取添加基本单价的数据
function getBasicData(){
	var rows = bpTab.datagrid('getRows');
	$('#basicPriceTab').datagrid('endEdit', editIndex);
	
	if(rows.length>0){
		var basicPriceName='',basicPriceFirst='',basicFirstOver='',
		basicPriceSecond='',basicSecondOver='',basicPriceThird='';
		
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			
			if(row.basicPriceName == ""){
				$.messager.show({
					title : 'Info',
					msg : '<fmt:message key='price.basicnamenull'/>',
					showType : 'slide',
					timeout:0
				});
				return false;
			}
			
			if(row.basicPriceFirst == 0){
				$.messager.show({
					title : 'Info',
					msg : '<fmt:message key='price.firstnull'/>',
					showType : 'slide',
					timeout:0
				});
				return false;
			}
			
			if(row.basicFirstOver < row.basicSecondOver){
				$.messager.show({
					title : 'Info',
					msg : '<fmt:message key='price.secondoversmall'/>',
					showType : 'slide',
					timeout:0
				});
				return false;
			}
			
			basicPriceName += row.basicPriceName;
			basicPriceFirst += row.basicPriceFirst;
			basicFirstOver += row.basicFirstOver;
			basicPriceSecond += row.basicPriceSecond;
			basicSecondOver +=row.basicSecondOver;
			basicPriceThird+=row.basicPriceThird;
			
			if(i != rows.length-1){
				basicPriceName += ",";
				basicPriceFirst += ",";
				basicFirstOver += ",";
				basicPriceSecond += ",";
				basicSecondOver += ",";
				basicPriceThird += ",";
			}
// 			var bp = new Object();
// 			bp.basicPriceName = row.basicPriceName;
// 			bp.basicPriceFirst = row.basicPriceFirst;
// 			bp.basicFirstOver = row.basicFirstOver;
// 			bp.basicPriceSecond = row.basicPriceSecond;
// 			bp.basicSecondOver = row.basicSecondOver;
// 			bp.basicPriceThird = row.basicPriceThird;
// // 			alert(JSON.stringify(bp));
// 			bps.push(JSON.stringify(bp));
		}
		$('#basicPriceName').val(basicPriceName);
		$('#basicPriceFirst').val(basicPriceFirst);
		$('#basicFirstOver').val(basicFirstOver);
		$('#basicPriceSecond').val(basicPriceSecond);
		$('#basicSecondOver').val(basicSecondOver);
		$('#basicPriceThird').val(basicPriceThird);
		return true;
	}
}
</script>

	<div style="padding: 10px 0 10px 60px">
		<form id="priceAddForm" method="post" action="${path}/admin/price/addprice.do">
			<input type="hidden" name="valid" value="1" />
			<!-- 添加基本单价 -->
			<input type="hidden" name="basicPriceName" value="" id="basicPriceName" /> 
			<input type="hidden" name="basicPriceFirst" value="" id="basicPriceFirst" /> 
			<input type="hidden" name="basicFirstOver" value="" id="basicFirstOver" /> 
			<input type="hidden" name="basicPriceSecond" value="" id="basicPriceSecond" />
			<input type="hidden" name="basicSecondOver" value="" id="basicSecondOver" /> 
			<input type="hidden" name="basicPriceThird" value="" id="basicPriceThird" />
			<table>
				<tr>
					<td><fmt:message key='price.name'/>：</td>
					<td><input class="easyui-textbox" type="text" name="priceKindName" id="priceKindName" 
					data-options="required:true,novalidate:true,onChange:checkPKName,invalidMessage:'<fmt:message key='price.exist'/>'" validType="nonValidate[]" /></td>
					<td><fmt:message key='common.remark'/>：</td>
					<td><input class="easyui-textbox" name="remark" type="text" />
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div style="padding: 10px">
		<table id="basicPriceTab"></table>
	</div>
	<div style="text-align: center; padding: 5px; margin-bottom: 20px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.submit' /></a> 
	</div>
</body>
</html>