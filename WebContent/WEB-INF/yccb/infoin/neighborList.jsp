<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>片区列表</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#neighborListTab').datagrid({
	    url:'${path}/infoin/neighbor/listContent.do',
	    fit:true,
	    pagination:true,
	    pageList:[5,10,15,20],
	    queryParams:{},
	    rownumbers:true,
	    border:false,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'pid',title:'ID',width:100,checkbox:true},   
	        {field:'neighborName',title:'小区名',width:100},   
	        {field:'neighborAddr',title:'地址',width:100},
	        {field:'mainMeter',title:'有无管理表',width:100,formatter:TFFormatter},
	        {field:'timerSwitch',title:'定时抄表开关',width:100,formatter:TFFormatter},
	        {field:'timer',title:'定时抄表时间',width:100},
	        {field:'watercompany',title:'自来水公司',width:100},
	        {field:'remark',title:'备注',width:100}
	    ]],
	    toolbar: [{ 
	        text: '<fmt:message key="common.add"/>', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#addNeighborWin').window({   
	    		    href:'${path}/infoin/neighbor/addPage.do',
	    		    width:467,   
	    		    height:300,
	    		    minimizable:false,
	    		    maximizable:false,
	    		    title: '添加小区'/* , 
	    		    onLoad:function(){   
	    		        //alert('loaded successfully'); 
	    		    }    */
	    		}); 
	        } 
	    }, '-', { 
	        text: '<fmt:message key="common.update"/>', 
	        iconCls: 'icon-edit', 
	        handler: function() { 
	        	var rows = $('#neighborListTab').datagrid('getSelections');
	        	var leng = rows.length;
	        	if(leng==1){
	        		var pid = rows[0].pid;
	        		alert(pid);
	        		$('#updateNeighborWin').window({   
		    		    href:'${path}/infoin/neighbor/updatePage.do?pid='+pid,
		    		    width:467,   
		    		    height:300,
		    		    minimizable:false,
		    		    maximizable:false,
		    		    title: '更新小区'/* , 
		    		    onLoad:function(){   
		    		        //alert('loaded successfully'); 
		    		    }    */
		    		}); 
	        		
	        	}else if(leng>1){
	        		alert("single selected");
	        	}else{
	        		alert("unselected");
	        	}
	        } 
	    }, '-',{ 
	        text: '<fmt:message key="common.delete"/>', 
	        iconCls: 'icon-remove', 
	        handler: function(){ 
	        	var rows = $('#neighborListTab').datagrid('getSelections');
	        	var pids = "";
	        	rows.forEach(function(obj){  
	        	    pids += obj.pid+",";
	        	});
	        	alert(pids);
	        } 
	    }]
	});
});
function TFFormatter(val,row){
	if(val=="1")
	return "有";
	if(val=="0")
	return "无";
}
//查询
function query(){
	var queryParams = $('#neighborListTab').datagrid('options').queryParams;
	queryParams.uid = 111;
	queryParams.uname = "fdasf";
	$('#neighborListTab').datagrid('load');
}
</script>
	<table id="neighborListTab"></table>
	<div id="addNeighborWin"></div>
	<div id="updateNeighborWin"></div>
</body>
</html>