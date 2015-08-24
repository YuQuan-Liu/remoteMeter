<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>批量添加</title>
</head>
<body>
	<form id="uploadcustomers" method="post" enctype="multipart/form-data">
		<div style="margin:10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chooseFile()"><fmt:message key='c.choosefile'/></a>
			<input type="file" id="file" name="file" accept=".xls" hidden="true" onchange="updateName()"/>
			<input type="text" id="name" name="name" hidden="true"/>
			<input type="text" id="filename" name="filename" class="easyui-textbox" data-options="disabled:true"/>
<!-- 			<input class="easyui-filebox" style="width:300px;"/> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" id="upload" onclick="submitUpload()"><fmt:message key='common.upload'/></a>
		</div>
	</form>
	<div style="padding:10px;">
	<table >
		<tr>
			<td><lable><fmt:message key='c.cadd'/></lable></td>
			<td><input type="text" class="easyui-textbox" id="c_add" data-options="disabled:true"/></td>
		</tr>
		<tr>
			<td><lable><fmt:message key='c.cadded'/></lable></td>
			<td><input type="text" class="easyui-textbox" id="c_added" data-options="disabled:true"/></td>
		</tr>
		<tr>
			<td><lable><fmt:message key='c.madd'/></lable></td>
			<td><input type="text" class="easyui-textbox" id="m_add" data-options="disabled:true"/></td>
		</tr>
		<tr>
			<td><lable><fmt:message key='c.madded'/></lable></td>
			<td><input type="text" class="easyui-textbox" id="m_added" data-options="disabled:true"/></td>
		</tr>
		<tr>
			<td><lable><fmt:message key='c.cnums'/></lable></td>
			<td><input type="text" class="easyui-textbox" id="c_nums" /></td>
		</tr>
		<tr>
			<td><lable><fmt:message key='c.maddrs'/></lable></td>
			<td><input type="text" class="easyui-textbox" id="m_addrs" /></td>
		</tr>
	</table>
	</div>
	<script>
	function updateName(){
		var filevalue = $("#file").val();
		var files = filevalue.split("\\");
		var filesize = files.length;
		$("#filename").textbox("setValue",files[filesize-1]);
		$("#name").val($("#file").val());
	}
	function chooseFile(){
		$("#file").trigger("click");
	}
	function submitUpload(){
		$.messager.progress({text:""});
		$("#uploadcustomers").form("submit",{
			url:"${path}/infoin/customer/upload.do",
			onSubmit:function(){
				if($("#name").val() == ""){
					$.messager.progress('close');
					return false;
				}
			},
			success:function(data){
				$.messager.progress('close');
// 				alert(data);
				var data = eval('(' + data + ')');
				if(data.done == true){
					$("#c_add").textbox("setValue",data.c_add);
					$("#c_added").textbox("setValue",data.c_added);
					$("#m_add").textbox("setValue",data.m_add);
					$("#m_added").textbox("setValue",data.m_added);
					$("#c_nums").textbox("setValue",data.c_nums);
					$("#m_addrs").textbox("setValue",data.m_addrs);
				}else{
					$.messager.alert("error",data.reason);
				}
			}
		});
	}
	</script>
</body>
</html>