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
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chooseFile()">选择文件...</a>
			<input type="file" id="file" name="file" accept=".xls" hidden="true" onchange="updateName()"/>
			<input type="text" id="name" name="name" hidden="true"/>
<!-- 			<input class="easyui-filebox" style="width:300px;"/> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" id="upload" onclick="submitUpload()">上传</a>
		</div>
	</form>
<!-- 	<div id="uploadprogress" class="easyui-progressbar" data-options="" style="width:100%;"></div> -->
	<script>
	function updateName(){
		$("#name").val($("#file").val());
	}
	function chooseFile(){
		$("#file").trigger("click");
	}
	function submitUpload(){
		$.messager.progress();
		$("#uploadcustomers").form("submit",{
			url:"${path}/infoin/customer/upload.do",
			onSubmit:function(){
				alert($("#name").val());
				if($("#name").val() == ""){
					$.messager.progress('close');
					return false;
				}
			},
			success:function(data){
				$.messager.progress('close');
				alert(data);
			}
		})
	}
	</script>
</body>
</html>