<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小区</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/datagrid-detailview.js"></script>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#neighborListTab').datagrid({
		url:'${path}/infoin/neighbor/listContent.do',
		fit:true,
		fitColumns:true,
// 		pagination:true,
// 		pageList:[5,10,15,20],
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
			{field:'mainMeter',title:'有无管理表',width:100,halign:'center',align:'center',formatter:function(value,row,index){
				if(value == 1){
					return "有";
				}else{
					return "无";
				}
			}},
			{field:'timerSwitch',title:'定时抄表开关',width:100,halign:'center',align:'center',formatter:function(value,row,index){
				if(value == 1){
					return "开";
				}else{
					return "关";
				}
			}},
			{field:'timer',title:'定时抄表时间',width:100,halign:'center',align:'center'},
			{field:'ip',title:'抄表IP',width:100,halign:'center',align:'left'},
			{field:'remark',title:'备注',width:100,halign:'center',align:'left'},
			{field:'action',title:'操作',width:100,halign:'center',align:'center',formatter: function(value,row,index){
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
					width:500,
					height:350,
					minimizable:false,
					maximizable:false,
					title: '添加小区'
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
					$('#updateNeighborWin').window({	
						href:'${path}/infoin/neighbor/updatePage.do?pid='+pid,
						width:500,
						height:350,
						minimizable:false,
						maximizable:false,
						title: '更新小区'
					});
					
				}else if(leng>1){
					$.messager.alert('Info','请选择一条记录！');
				}else{
					$.messager.alert('Info','未选中任何记录！');
				}
			}
		}, '-',{ 
			text: '<fmt:message key="common.delete"/>', 
			iconCls: 'icon-remove',
			handler: function(){
				var rows = $('#neighborListTab').datagrid('getSelections');
				if(rows.length==0){
					$.messager.alert('Info','请选择记录！');
					return;
				}else{
					if(rows.length > 1){
						$.messager.alert('Info','请选择一条记录！');
						return;
					}
					var pid = rows[0].pid;
					var index_ = $('#neighborListTab').datagrid('getRowIndex',rows[0]);
					var name = rows[0].neighborName;
					
					$.messager.confirm('Info','确定要删除'+name+'吗？',function(r){	
						if (r){	
							$.ajax({
								url:'delete.do',
								type:'post',
								data:{pid:pid},
								success:function(data){
									if(data=="succ"){
										$.messager.show({
											title:"Info",
											msg:"删除成功",
											showType:'slide'
										});
										$('#neighborListTab').datagrid('deleteRow',index_);
									}
								}
							});	
						}	
					}); 
				}
			} 
		}]
	});
	//expand row
	$('#neighborListTab').datagrid({
		view: detailview,
		detailFormatter:function(index,row){
			return '<div style="padding:2px"><table id="ddv-' + row.pid + '"></table></div>';
		},
		onExpandRow: function(index,row){
			$('#ddv-'+row.pid).datagrid({
				url:'${path}/infoin/neighbor/gprsListContent.do?pid='+row.pid,
				fitColumns:true,
				singleSelect:true,
				rownumbers:true,
				loadMsg:'',
				height:'auto',
				columns:[[
					{field:'gprstel',title:'集中器SIM',width:100,halign:'center'},
					{field:'gprsaddr',title:'集中器地址',width:100,halign:'center'},
					{field:'installAddr',title:'安装地址',width:100,halign:'center'},
					{field:'gprsprotocol',title:'使用协议',width:100,halign:'center',formatter:function(value,row,index){
						if(value == 1){
							return "自主协议";
						}else{
							if(value == 2){
								return "188协议";
							}else{
								return "异常";
							}
						}
					}},
					{field:'ip',title:'监听IP',width:100,halign:'center'},
					{field:'port',title:'监听端口',width:100,halign:'center'},
					{field:'remark',title:'备注',width:100,halign:'center'},
					{field:'action',title:'操作',width:100,halign:'center',align:'center',
						formatter: function(value,row_,index){
							var id = row_.pid;
							var nid = row.pid;
							return "<a href='#' class='operateHref' onclick='updatePageGprs("+id+")'>修改</a>"
							+"<a href='#' class='operateHref' onclick='deleteGprsById("+id+","+nid+","+index+")'>删除</a>";
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

//根据小区 id 添加集中器
function addGprs(pid){
	$('#addGprsWin').window({	
		href:'${path}/infoin/neighbor/addPageGprs.do?neighborid='+pid,
		width:500,
		height:300,
		minimizable:false,
		maximizable:false,
		title:'添加集中器'
	}); 
}

//修改集中器信息
function updatePageGprs(pid){
	$('#updateGprsWin').window({	
		href:'${path}/infoin/neighbor/updatePageGprs.do?pid='+pid,
		width:500,	
		height:300,
		minimizable:false,
		maximizable:false,
		title: '修改集中器'
	}); 
}

//删除单个集中器
function deleteGprsById(id,nid,index){
	$.messager.confirm('Info', '确定要删除选中记录吗？', function(r){
		if(r){
			$.ajax({
				url:'${path}/infoin/neighbor/deleteGprsById.do',
				type:'post',
				data:{pid:id},
				success:function(typ){
					if(typ=="succ"){
						
						$.messager.show({
							title:"Info",
							msg:"删除成功",
							showType:'slide'
						});
						$('#ddv-'+nid).datagrid('deleteRow',index);
					}
				}
			});	
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