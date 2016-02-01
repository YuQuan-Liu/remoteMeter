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
			{field:'neighborName',title:'<fmt:message key='n.name'/>',width:100,halign:'center',align:'left'},	
			{field:'neighborAddr',title:'<fmt:message key='common.addr'/>',width:100,halign:'center',align:'left'},
			{field:'mainMeter',title:'<fmt:message key='n.main'/>',width:100,halign:'center',align:'center',formatter:function(value,row,index){
				if(value == 1){
					return "<fmt:message key='common.have'/>";
				}else{
					return "<fmt:message key='common.nothave'/>";
				}
			}},
			{field:'timerSwitch',title:'<fmt:message key='n.switch'/>',width:100,halign:'center',align:'center',formatter:function(value,row,index){
				if(value == 1){
					return "<fmt:message key='common.open'/>";
				}else{
					return "<fmt:message key='common.close'/>";
				}
			}},
			{field:'timer',title:'<fmt:message key='n.timer'/>',width:100,halign:'center',align:'center'},
			{field:'ip',title:'<fmt:message key='n.ip'/>',width:100,halign:'center',align:'left'},
			{field:'remark',title:'<fmt:message key='common.remark'/>',width:100,halign:'center',align:'left'},
			{field:'action',title:'<fmt:message key='common.action'/>',width:100,halign:'center',align:'center',formatter: function(value,row,index){
					var id = row.pid;
					return "<a href='#' class='operateHref' onclick='addGprs("+id+")'><fmt:message key='g.add'/></a> ";
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
					title: '<fmt:message key='n.add'/>'
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
						title: '<fmt:message key='n.update'/>'
					});
					
				}else{
					$.messager.alert('Info','<fmt:message key='common.selectsingle'/>');
				}
			}
		}, '-',{ 
			text: '<fmt:message key="common.delete"/>', 
			iconCls: 'icon-remove',
			handler: function(){
				var rows = $('#neighborListTab').datagrid('getSelections');
				if(rows.length==1){
					var pid = rows[0].pid;
					var index_ = $('#neighborListTab').datagrid('getRowIndex',rows[0]);
					var name = rows[0].neighborName;
					
					$.messager.confirm('Info','<fmt:message key='common.confirmdelete'/>？',function(r){	
						if (r){	
							$.ajax({
								url:'delete.do',
								type:'post',
								data:{pid:pid},
								success:function(data){
									if(data=="succ"){
										$.messager.show({
											title:"Info",
											msg:"<fmt:message key='common.deleteok'/>",
											showType:'slide'
										});
										$('#neighborListTab').datagrid('deleteRow',index_);
									}
								}
							});	
						}	
					}); 
				}else{
					$.messager.alert('Info','<fmt:message key='common.selectsingle'/>');
					return;
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
					{field:'gprstel',title:'<fmt:message key='g.sim'/>',width:100,halign:'center'},
					{field:'gprsaddr',title:'<fmt:message key='g.addr'/>',width:100,halign:'center'},
					{field:'installAddr',title:'<fmt:message key='g.installaddr'/>',width:100,halign:'center'},
					{field:'gprsprotocol',title:'<fmt:message key='g.protocol'/>',width:100,halign:'center',formatter:function(value,row,index){
						if(value == 1){
							return "<fmt:message key='g.hdeg'/>";
						}else{
							if(value == 2){
								return "<fmt:message key='g.188'/>";
							}else{
								if(value == 3){
									return "<fmt:message key='g.hdeg'/>"+2;
								}else{
									if(value == 4){
										return "D10";
									}else{
										return "<fmt:message key='common.exception'/>";
									}
								}
							}
						}
					}},
					{field:'ip',title:'<fmt:message key='g.ip'/>',width:100,halign:'center'},
					{field:'port',title:'<fmt:message key='g.port'/>',width:100,halign:'center'},
					{field:'remark',title:'<fmt:message key='common.remark'/>',width:100,halign:'center'},
					{field:'action',title:'<fmt:message key='common.action'/>',width:100,halign:'center',align:'center',
						formatter: function(value,row_,index){
							var id = row_.pid;
							var nid = row.pid;
							return "<a href='#' class='operateHref' onclick='updatePageGprs("+id+")'><fmt:message key='common.update'/></a>"
							+"<a href='#' class='operateHref' onclick='configPageGprs("+id+")'><fmt:message key='common.config'/></a>"
							+"<a href='#' class='operateHref' onclick='deleteGprsById("+id+","+nid+","+index+")'><fmt:message key='common.delete'/></a>";
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
		height:400,
		minimizable:false,
		maximizable:false,
		title:'<fmt:message key='g.add'/>'
	}); 
}

//修改集中器信息
function updatePageGprs(pid){
	$('#updateGprsWin').window({	
		href:'${path}/infoin/neighbor/updatePageGprs.do?pid='+pid,
		width:500,	
		height:400,
		minimizable:false,
		maximizable:false,
		title: '<fmt:message key='g.update'/>'
	}); 
}

//配置集中器内信息
function configPageGprs(pid){
	$('#configGprsWin').window({	
		href:'${path}/infoin/neighbor/configPageGprs.do?pid='+pid,
		width:550,	
		height:500,
		minimizable:false,
		maximizable:false,
		title: '<fmt:message key='common.config'/>'
	});
}

//删除单个集中器
function deleteGprsById(id,nid,index){
	$.messager.confirm('Info', '<fmt:message key='common.confirmdelete'/>？', function(r){
		if(r){
			$.ajax({
				url:'${path}/infoin/neighbor/deleteGprsById.do',
				type:'post',
				data:{pid:id},
				success:function(typ){
					if(typ=="succ"){
						
						$.messager.show({
							title:"Info",
							msg:"'<fmt:message key='common.deleteok'/>'",
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
	<div id="configGprsWin"></div>
</body>
</html>