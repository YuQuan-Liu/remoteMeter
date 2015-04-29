<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>片区列表</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/datagrid-detailview.js"></script>
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
			{field:'neighborName',title:'小区名',width:100,halign:'center',align:'left'},	
			{field:'neighborAddr',title:'地址',width:100,halign:'center',align:'left'},
			{field:'mainMeter',title:'有无管理表',width:100,halign:'center',align:'center',formatter:TFFormatter},
			{field:'timerSwitch',title:'定时抄表开关',width:100,halign:'center',align:'center',formatter:TFFormatter},
			{field:'timer',title:'定时抄表时间',width:100,halign:'center',align:'center'},
			{field:'watercompany',title:'自来水公司',width:100,halign:'center',align:'left'},
			{field:'remark',title:'备注',width:100,halign:'center',align:'left'},
			{field:'aa',title:'操作',width:100,halign:'center',align:'center',
				formatter: function(value,row,index){
					var id = row.pid;
					return "<a href='#' class='operateHref' onclick='addGprs("+id+")'>添加集中器</a> ";
				}}
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
					}	*/
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
						}	*/
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
				$.messager.confirm('提示','确定要删除选中记录吗？',function(r){	
					if (r){	
						$.ajax({
							url:'delete.do',
							type:'post',
							data:{'pids':pids},
							success:function(typ){
								if(typ=="succ"){
									$.messager.alert('提示','删除成功！','info',
											function(){
											 	$('#neighborListTab').datagrid('reload');
											 });
								}
							}
						});	
					}	
				});  
			} 
		}]
	});
	//expand row
	$('#neighborListTab').datagrid({
		view: detailview,
		detailFormatter:function(index,row){
			return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
		},
		onExpandRow: function(index,row){
			$('#ddv-'+index).datagrid({
				url:'gprsListContent.do?pid='+row.pid,
				fitColumns:true,
				singleSelect:true,
				rownumbers:true,
				loadMsg:'',
				height:'auto',
				columns:[[
					{field:'gprstel',title:'集中器SIM',width:100,halign:'center',align:'right'},
					{field:'gprsaddr',title:'集中器地址',width:100,halign:'center',align:'left'},
					{field:'installAddr',title:'安装地址',width:100,halign:'center',align:'left'},
					{field:'gprsprotocol',title:'使用协议',width:100,halign:'center',align:'right'},
					{field:'remark',title:'备注',width:100,halign:'center'},
					{field:'aa',title:'操作',width:100,halign:'center',align:'center',
						formatter: function(value,row,index){
							console.log(row);
							var id = row.pid;
							return "<a href='#' class='operateHref' onclick='showRole("+id+")'>修改</a> | <a href='#' class='operateHref' onclick='delGprsById("+id+")'>删除</a>";
						}
					}
				]],
				onResize:function(){
					$('#neighborListTab').datagrid('fixDetailRowHeight',index);
				},
				onLoadSuccess:function(){
					setTimeout(function(){
						$('#neighborListTab').datagrid('fixDetailRowHeight',index);
					},0);
				}
			});
			$('#neighborListTab').datagrid('fixDetailRowHeight',index);
		}
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
//根据小区 id 添加集中器
function addGprs(pid){
	$('#addGprsWin').window({	
		href:'addPageGprs.do?neighborid='+pid,
		width:467,	
		height:300,
		minimizable:false,
		maximizable:false,
		title: '添加集中器'/* , 
		onLoad:function(){	
			//alert('loaded successfully'); 
		}	*/
	}); 
}

//删除单个集中器
function deleteGprsById(id){
	$.ajax({
		url:'deleteGprsById.do',
		type:'post',
		data:{'pid':id},
		success:function(typ){
			if(typ=="succ"){
				$.messager.alert('提示','删除成功！','info',
						function(){
						 	$('#neighborListTab').datagrid('reload');
						 });
			}
		}
	});	
}
</script>
	<table id="neighborListTab"></table>
	<div id="addNeighborWin"></div>
	<div id="updateNeighborWin"></div>
	<div id="addGprsWin"></div>
	<div id="updateGprsWin"></div>
</body>
</html>