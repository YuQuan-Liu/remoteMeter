<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<link rel="shortcut icon" href="resource/images/logo.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resource/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${path}/resource/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${path}/resource/css/default.css"> 

<script type="text/javascript" src="${path}/resource/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/locale/<fmt:message key='easyui.lang'/>"></script>
<script type="text/javascript" src="${path}/resource/js/msghelper.js"></script>
<!-- echarts -->
<script src="${path}/resource/echarts/echarts.js" type="text/javascript"></script>