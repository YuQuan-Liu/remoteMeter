<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>故障短信通知</title>
<%@include file="/commonjsp/top.jsp" %>
<script type="text/javascript" src="${path}/resource/jquery-easyui-1.4.1/datagrid-detailview.js"></script>
</head>
<body>
	<div style="margin:20px;">
		<div style="margin:20px;font-size: large;">短信模板：</div>
		<c:forEach var="t" items="${templates }">
			${t.content }
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openConfirm('${t.cid }','${t.para_cnt }')" style="margin-left:20px;">发送短信</a>
			<br/>
		</c:forEach>
	</div>
	
	<table id="neighborListTab" style="width:100%;height:400px;"></table>
	<div id="confirmDialog" class="easyui-dialog" title="请输入监督管理员密码" style="width:400px;height:200px;" data-options="iconCls:'icon-edit',closed:true,resizable:true,modal:true">
		<table style="width:300px;margin-left:20px;margin-top:20px;line-height:24px;text-align:center">
			<tr>
				<td><label>监督密码：</label></td>
				<td><input type="password" name="sendpassword" id="sendpassword"/></td>
			</tr>
			<tr>
				<td colspan="2" >
					<input name="submitderead" type="button" id="submitderead" value="确定" onclick="sendSMS()">
					<input type="hidden" id="sendsms_cid">
					<input type="hidden" id="sendsms_cnt">
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
	$(function(){
		$('#neighborListTab').datagrid({
			url:'${path}/infoin/neighbor/listContent.do',
			queryParams:{},
			rownumbers:true,
			autoRowHeight:false,
			rowStyler: function(index,row){
				return 'height:30px;';
			},
			columns:[[
				{field:'pid',title:'ID',width:100,checkbox:true},	
				{field:'neighborName',title:'<fmt:message key='n.name'/>',width:200,halign:'center',align:'left'},	
				{field:'neighborAddr',title:'<fmt:message key='common.addr'/>',width:300,halign:'center',align:'left'}
			]]
		});
	});
	function openConfirm(cid,cnt){
		var rows = $('#neighborListTab').datagrid('getSelections');
		if(rows.length == 0){
			$.messager.alert('Info','请选择小区');
			return;
		}
		$('#sendsms_cid').val(cid);
		$('#sendsms_cnt').val(cnt);
		$('#confirmDialog').window('open');
	}
	var breakdown_done = true;
	function sendSMS(){
		var cid = $('#sendsms_cid').val();
		var cnt = $('#sendsms_cnt').val();
		var nbr_ids = [];
		var para = [];
		var pwd = $('#sendpassword').val();
		$('#sendpassword').val(""); //清空
		var rows = $('#neighborListTab').datagrid('getSelections');
		
		if (pwd != ""){
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				nbr_ids.push(row.pid);
			}
			for(var i = 0;i < cnt;i++){
				para.push($("#"+cid+i).val());
			}
			if(breakdown_done){
				breakdown_done = false;
				$.ajax({
					type:"POST",
					url:"${path}/admin/breakdown/sendsms.do",
					dataType:"json",  
			        traditional :true,
					data:{
						cid:cid,
						pwd:pwd,
						'para':para,
						'nbr_ids':nbr_ids
					},
					success:function(data){
						if (data == 1) {
							$.messager.show({
								title : 'Info',
								msg : '操作成功',
								showType : 'slide'
							});
						} else {
							$.messager.show({
								title : 'Info',
								msg : '操作失败',
								showType : 'slide'
							});
						}
						$('#confirmDialog').window('close');
					}
				});
				breakdown_done = true;
			}else{
				$.messager.show({
					title : 'Info',
					msg : '操作频繁，请稍后重试',
					showType : 'slide'
				});
			}
		}else{
			$.messager.show({
				title : 'Info',
				msg : '监督密码为空！',
				showType : 'slide',
				timeout : 3000
			});
		}
	}
	</script>
</body>
</html>