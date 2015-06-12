<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label>小区</label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto',onSelect:showMeterdata">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="upload_()" >Excel上传</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="download_()" >手持导出</a>
				
			</div>
		</form>
		<form id="exportform" method="post">
			<input type="hidden" name="n_id" id="n_id"/> 
			<input type="hidden" name="n_name" id="n_name"/>
		</form>
	</div>
	<div id="uploadWin"></div>
	<div style="margin:10px;">
		<label>抄表批次</label>
		<select class="easyui-combobox" id="readlog" name="readlog" style="width:200px" data-options="panelHeight:'auto',valueField:'pid',textField:'completetime'">
			<option value="">请选择批次</option>
	    </select>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addreadlog()" >添加批次</a><span style="color:red;margin-left:20px;">"添加批次"仅用于没有远传表的小区使用</span>
	</div>
	<table id="nonRemoteTab" style="width:100%;height:400px;"></table>
<script>
$(function(){
	$("#nonRemoteTab").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
// 		url:"${path}/readme/read/listread",
		queryParams:{
			
		},
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
// 		singleSelect:true,  
		rowStyler:function(index,row){
			
		},
		onClickRow: onClickRow,
		columns:[[
		          {field:'m_id',title:'ID',width:60,checkbox:true},
		          {field:'c_num',title:'用户号',width:80},
		          {field:'customerName',title:'用户名',width:80},
		          {field:'customerBalance',title:'余额',width:80,styler:function(value,row,index){
		        	  if(value <= 0){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'prePaySign',title:'付费',width:40,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "预";
		        	  }else{
		        		  return "后";
		        	  }
		          }},
		          {field:'g_addr',title:'集中器',width:80},
		          {field:'collectorAddr',title:'采集器',width:80},
		          {field:'meterAddr',title:'表地址',width:80},
		          {field:'meterState',title:'表状态',width:80,formatter:function(value,row,index){
						if(value == 1){
							return "正常";
						}
						if(value == 2){
							return "数据错误";
						}
						if(value == 3){
							return "线路故障";
						}
						if(value == 4){
							return "超时";
						}
						if(value == 5){
							return "人工修改";
						}
		          }},
		          {field:'deread',title:'扣费读数',width:80},
		          {field:'readdata',title:'上次表读数',width:80},
		          {field:'readtime',title:'上次抄表时间',width:80},
		          {field:'newread',title:'表读数',width:70,editor:{type:'numberbox'}},
		          {field:'yl',title:'用量',width:70,styler:function(value,row,index){
		        	  if(value > 30){
		        		  return 'color:red;';
		        	  }
		          },formatter: function(value,row,index){
						return row.readdata-row.deread;
				  }},
		          {field:'action',title:'操作',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.m_id;
							return "<a href='#' class='operateHref' onclick='readManual("+id+","+index+")'> 录入 </a>";
				  }}
		      ]]
	});
});
function showMeterdata(){
	var n_id = $("#neighbor").combobox("getValue");
	if(n_id != ""){
		$('#nonRemoteTab').datagrid({
			url:"${path}/readme/read/listnonremote.do",
			queryParams: {
				n_id:n_id
			}  
		});
		//get the readlogs unsettle
		$('#readlog').combobox('reload','${path}/readme/read/readloglist.do?n_id='+n_id);
		
	}
}

var editIndex = undefined;
function endEditing(){
	
    if (editIndex == undefined){return true;}
    
    if ($('#nonRemoteTab').datagrid('validateRow', editIndex)){
        
    	$('#nonRemoteTab').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickRow(index){
	if (editIndex != index){
        if (endEditing()){
            $('#nonRemoteTab').datagrid('selectRow', index).datagrid('beginEdit', index);
            editIndex = index;
        } else {
            $('#nonRemoteTab').datagrid('selectRow', editIndex);
        }
    }
}

function readManual(id,index){
	if (editIndex == index){
        if (endEditing()){
        	var readlogid = $("#readlog").combobox("getValue");
        	var newread = $('#nonRemoteTab').datagrid('getRows')[index]["newread"];
        	if(readlogid > 0 && newread >0){
            	$.ajax({
            		type:"POST",
    	    		url:"${path}/readme/read/addnonremote.do",
    	    		data:{
    	    			m_id:id,
    	    			newread:newread,
    	    			readlogid:readlogid
    	    		},
    	    		dataType:"json",
    	    		success:function(data){
    	    			//the new data is add to the db
    	    			$('#nonRemoteTab').datagrid('getRows')[index]["readdata"] = data.actionResult;
    	    			$('#nonRemoteTab').datagrid('getRows')[index]["readtime"] = data.actionTime;
//     	    			$('#nonRemoteTab').datagrid('getRows')[index]["yl"] = data.mnum - deread;
    	    			$('#nonRemoteTab').datagrid('refreshRow', index);
    	    		}
    	    	});
        	}else{
        		if(readlogid > 0){
            		$.messager.alert('Error','请选择抄表批次');
        		}
        	}
        }
    }
	

}
function download_(){
	var n_id = $("#neighbor").combobox("getValue");
	var n_name = $("#neighbor").combobox("getText");
	
	if(n_id != ""){
		$("#n_id").val(n_id);
		$("#n_name").val(n_name);
		
		$("#exportform").form('submit',{
			url:"${path}/readme/nonremote/download.do",
		});
	}else{
		$.messager.alert('Info','请选择小区');
	}
	
}

function upload_(){
	var readlog = $("#readlog").combobox("getValue");
	if(readlog == ""){
		$.messager.alert('Info','请选择抄表批次');
		return;
	}
	
	$('#uploadWin').window({	
		href:'${path}/readme/read/uploadPage.do',
		width:467,	
		height:300,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		title: '上传非远传表具'
	});
}
</script>
</body>
</html>