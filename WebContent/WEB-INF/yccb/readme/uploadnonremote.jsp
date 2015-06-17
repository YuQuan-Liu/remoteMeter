<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>上传非远传</title>
</head>
<body>
	<form id="uploadreads" method="post" enctype="multipart/form-data">
		<div style="margin:10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chooseFile()"><fmt:message key='c.choosefile'/></a>
			<input type="file" id="file" name="file" accept=".xls" hidden="true" onchange="updateName()"/>
			<input type="text" id="name" name="name" hidden="true"/>
<!-- 			<input class="easyui-filebox" style="width:300px;"/> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" id="upload" onclick="submitUpload()"><fmt:message key='common.upload'/></a>
		</div>
	</form>
	<script>
	function updateName(){
		$("#name").val($("#file").val());
	}
	function chooseFile(){
		$("#file").trigger("click");
	}
	function submitUpload(){
		
		var readlogid = $("#readlog").combobox("getValue");
		$.messager.progress({title:"<fmt:message key='common.uploading'/>",text:"",interval:100});
		$("#uploadreads").form("submit",{
			url:"${path}/readme/nonremote/upload.do",
			onSubmit:function(param){
				if($("#name").val() == ""){
					$.messager.progress('close');
					return false;
				}
				param.readlogid = readlogid;
			},
			success:function(data){
				$.messager.progress('close');
// 				alert(data);
			}
		})
	}
	</script>
</body>
</html>