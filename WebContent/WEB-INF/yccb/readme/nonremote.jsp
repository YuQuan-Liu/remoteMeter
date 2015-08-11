<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>非远传录入</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'200',onSelect:showMeterdata">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="upload_()" ><fmt:message key='readnon.upload'/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="download_()" ><fmt:message key='readnon.download'/></a>
				
			</div>
		</form>
		<form id="exportform" method="post">
			<input type="hidden" name="n_id" id="n_id"/> 
			<input type="hidden" name="n_name" id="n_name"/>
		</form>
	</div>
	<div id="uploadDialog" class="easyui-dialog" title="Upload" data-options="closed:true,modal:true" style="width:467px;height:300px">
		<form id="uploadreads" method="post" enctype="multipart/form-data">
			<div style="margin:10px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="chooseFile()"><fmt:message key='c.choosefile'/></a>
				<input type="file" id="file" name="file" accept=".xls" hidden="true" onchange="updateName()"/>
				<input type="text" id="name" name="name" hidden="true"/>
	<!-- 			<input class="easyui-filebox" style="width:300px;"/> -->
				<a href="javascript:void(0)" class="easyui-linkbutton" id="upload" onclick="submitUpload()"><fmt:message key='common.upload'/></a>
			</div>
		</form>
	</div>
	<div style="margin:10px;">
		<label><fmt:message key='readnon.readlog'/></label>
		<select class="easyui-combobox" id="readlog" name="readlog" style="width:200px" data-options="panelHeight:'200',valueField:'pid',textField:'completetime'">
			<option value=""><fmt:message key='common.selectreadlog'/></option>
	    </select>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addreadlog()" ><fmt:message key='readnon.addreadlog'/></a><span style="color:red;margin-left:20px;"><fmt:message key='readnon.addreadlogremark'/></span>
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
		          {field:'c_num',title:'<fmt:message key='c.num'/>',width:80},
		          {field:'customerName',title:'<fmt:message key='c.name'/>',width:80},
		          {field:'customerBalance',title:'<fmt:message key='c.balance'/>',width:80,styler:function(value,row,index){
		        	  if(value <= 0){
		        		  return 'background-color:#ffee00;color:red;';
		        	  }
		          }},
		          {field:'prePaySign',title:'<fmt:message key='c.prestyle'/>',width:40,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='c.pre'/>";
		        	  }else{
		        		  return "<fmt:message key='c.post'/>";
		        	  }
		          }},
		          {field:'g_addr',title:'<fmt:message key='gprs'/>',width:80},
		          {field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:80},
		          {field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:80},
		          {field:'meterState',title:'<fmt:message key='m.mstate'/>',width:80,formatter:function(value,row,index){
		        	  if(value == 1){
							return "<fmt:message key='m.mstateok'/>";
						}
						if(value == 2){
							return "<fmt:message key='m.mstateerror'/>";
						}
						if(value == 3){
							return "<fmt:message key='m.mstatebreak'/>";
						}
						if(value == 4){
							return "<fmt:message key='m.mstatetimeout'/>";
						}
						if(value == 5){
							return "<fmt:message key='m.mstatechange'/>";
						}
		          }},
		          {field:'deread',title:'<fmt:message key='m.deread'/>',width:80},
		          {field:'readdata',title:'<fmt:message key='m.readdata'/>',width:80},
		          {field:'readtime',title:'<fmt:message key='m.readtime'/>',width:80},
		          {field:'newread',title:'<fmt:message key='readnon.newread'/>',width:70,editor:{type:'numberbox'}},
		          {field:'yl',title:'<fmt:message key='yl'/>',width:70,styler:function(value,row,index){
		        	  if(value > 30){
		        		  return 'color:red;';
		        	  }
		          },formatter: function(value,row,index){
						return row.readdata-row.deread;
				  }},
		          {field:'action',title:'<fmt:message key='common.action'/>',width:200,halign:'center',align:'center',
						formatter: function(value,row,index){
							var id = row.m_id;
							return "<a href='#' class='operateHref' onclick='readManual("+id+","+index+")'><fmt:message key='readnon.enter'/></a>";
				  }}
		      ]]
	});
});
function showMeterdata(){
	var n_id = $("#neighbor").combobox("getValue");
	if(n_id != ""){
		$('#nonRemoteTab').datagrid({
			url:"${path}/readme/nonremote/listnonremote.do",
			queryParams: {
				n_id:n_id
			}  
		});
		//get the readlogs unsettle
		$('#readlog').combobox('reload','${path}/readme/nonremote/readloglist.do?n_id='+n_id);
		
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
    	    		url:"${path}/readme/nonremote/addnonremote.do",
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
        		if(readlogid == ""){
            		$.messager.alert('Error','<fmt:message key='common.selectreadlog'/>');
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
		$.messager.alert('Info','<fmt:message key='common.choosenei'/>');
	}
	
}

function upload_(){
	var readlog = $("#readlog").combobox("getValue");
	if(readlog == ""){
		$.messager.alert('Info','<fmt:message key='common.selectreadlog'/>');
		return;
	}
	
	$('#uploadDialog').dialog('open');
}

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
			$('#uploadDialog').dialog('close');
			showMeterdata();
		}
	})
}

function addreadlog(){
	var n_id = $("#neighbor").combobox("getValue");
	
	if(n_id != ""){
		$.ajax({
    		type:"POST",
    		url:"${path}/readme/nonremote/addreadlog.do",
    		data:{
    			n_id:n_id
    		},
    		dataType:"json",
    		success:function(data){
    			if(data.success == true){
    				$('#readlog').combobox('reload','${path}/readme/nonremote/readloglist.do?n_id='+n_id);
    			}else{
    				$.messager.show({
    					title:'Info',
    					msg:data.reason,
    					showType:'slide',
    					timeout:3000
    				});
    			}
    		}
    	});
	}else{
		$.messager.alert('Info','<fmt:message key='common.choosenei'/>');
	}
}
</script>
</body>
</html>