<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改片区</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#neighborCombo').combobox({   
	    required:true,   
	    url:'${path}/admin/dep/nbrlistContent.do?depId=${dep.pid}',
		method:'post',
		valueField:'id',
		textField:'text',
		multiple:true,
		panelHeight:'200'
	});  
})
function submitForm(){
	if($('#depAddForm').form('validate')){
		$('#depAddForm').form('submit', {   
		    success: function(data){   
		       if(data=="succ"){
		    	   $('#depUpdateWin').window('close');
		    	   $.messager.show({
						title:'Info',
						msg:'<fmt:message key='common.updateok'/>',
						showType:'slide',
						timeout:3000
					});
		    	   $('#depListTab').datagrid('reload');
		       }
		    }   
		});  
	}
}
</script>
<form id="depAddForm" method="post" action="${path}/admin/dep/update.do">
		<div style="padding:10px 0 10px 60px">
	    	<input type="hidden" name="valid" value="1"/>
	    	<input type="hidden" name="pid" value="${dep.pid }"/>
	    	<%-- <input type="hidden" name="watercompany.pid" value="${dep.watercompany.pid }"/> --%>
	    	<table>
	    		<tr>
	    			<td><fmt:message key='areas'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="departmentName" value="${dep.departmentName }" data-options="required:true"/></td>
	    			<td><fmt:message key='common.remark'/>：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" type="text" value="${dep.remark }"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.neighborName'/>：</td>
	    			<td><input id="neighborCombo" name="neighbors"></td>
	    		</tr>
	    	</table>
	    </div>
 </form>	    
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.submit'/></a>
	    </div>
</body>
</html>