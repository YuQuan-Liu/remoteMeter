<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抄表</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
//客户id
var pid="";
function loadCust(){
	var custId = $('#cust_info').val();
	var nbrId = $('#sel_neibours').combobox('getValue');
	$.ajax({
		url: "${path}/charge/custinfo.do",
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		async:false,
		data:{ nbrId: nbrId, custId: custId},
		dataType:"json",
		success: function(data){
			//加载form数据
			$('#customer').form('load',data);
			pid = data.pid;
			//$('#hid_id').val(pid);
      }});
	//获取表信息
	$("#meterFd").show();
	$('#custMeters').datagrid({
	    url:'${path}/charge/custMeters.do?custId='+pid,
	   	fitColumns:true,
	    fit:false,
	    border:false,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
				return 'height:30px;';
			},
	    columns:[[
	        {field:'pid',title:'ID',hidden:true},
			{field:'gprs',title:'集中器',width:60},
	        {field:'qfh',title:'铅封号',width:60},
	      	{field:'steelNum',title:'钢印号',width:60},
	      	{field:'suppleMode',title:'供水方式',width:60},
	      	{field:'collectorAddr',title:'采集器地址',width:60},
	      	{field:'meterAddr',title:'表地址',width:60},
	      	{field:'meterSolid',title:'虚实表',width:60},
	      	{field:'mk',title:'表类型',width:60},
	      	{field:'pk',title:'单价',width:60},
	      	{field:'isValve',title:'阀门',width:60},
	      	{field:'deductionStyle',title:'结算方式',width:50},
	      	{field:'valveOffthre',title:'关阀余额',width:50},
	      	{field:'timerSwitch',title:'定时检测',width:50},
	      	{field:'timer',title:'定时时间',width:50},
	      	{field:'overflow',title:'用量阀值',width:50},
	      	{field:'changend',title:'换表读数',width:50},
	      	{field:'changestart',title:'起始读数',width:50},
	      	{field:'action',title:'操作',width:90,halign:'center',align:'center',formatter: function(value,row,index){
				var id = row.pid;
				return "<a href='#' class='operateHref' onclick='updateMeter("+id+")' title='开阀'> 开阀 </a>"
				+"<a href='#' class='operateHref' onclick='deleteMeter("+id+")' title='更新单价'> 更新单价 </a>"
				+"<a href='#' class='operateHref' onclick='changemeter("+id+")' title='水表曲线'> 水表曲线</a>";
	  		}}
	    ]],
	});
}
function updateCust(){
	//更新用户资料
	$('#customer').form('submit', {   
	    success: function(data){
	    	var data = eval('(' + data + ')'); 
	       if(data.update>0){
	    	   $.messager.show({
					title:'更新用户资料',
					msg:'更新 成功！',
					showType:'slide',
					timeout:3000
				});	
	       }else{
				 $.messager.show({
						title:'更新用户资料',
						msg:'更新失败！',
						showType:'slide',
						timeout:0
					});
			}
	    }   
	});  
}
function changePre(){
	var prePaySign = $('#prePaySign').combobox('getValue');
	//预后付费转换
	$.messager.confirm('确认操作','确认转换预后付费状态?',function(r){   
	    if (r){
	    	$.ajax({ 
	    		url: "${path}/charge/updatePrepaySign.do",
	    		data:{custId: pid,prePaySign:prePaySign},
	    		dataType:"json",
	    		success: function(data){
	    			//var data = eval('(' + data + ')'); 
	    			if(data.update>0){
	    				$.messager.show({
	    					title:'预后付费转换',
	    					msg:'转换成功！',
	    					showType:'slide',
	    					timeout:3000
	    				});	
	    				loadCust();
	    			}
	          }});
	    }   
	});  

}
function payFor(){
	//缴费
	if(pid!=""){
		$('#payInfoWin').window({   
		    href:'${path}/charge/chargePay.do?custId='+pid,
		    width:'80%',   
		    height:350,
		    minimizable:false,
		    maximizable:false,
		    title: '缴费扣费信息'
		}); 
	}else{
		$.messager.show({
			title:'缴费提示',
			msg:'尚未选择用户！',
			showType:'slide',
			timeout:3000
		});	
	}
}
</script>
 <div id="tb" style="padding:2px 5px;">小区：
        <select class="easyui-combobox" panelHeight="auto" style="width:100px" id="sel_neibours">
            <option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
			<option value="${n.pid }">${n.neighborName }</option>
			</c:forEach>
        </select>
         用户号/用户ID: <input class="easyui-textbox" id="cust_info">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="loadCust()">Search</a>
  </div>
<form id="customer" method="post" action="${path }/charge/updateCustInfo.do">
<input type="hidden" name="pid" id="hid_pid"/>
		<div>
			 <fieldset style="padding-left: 10px;">
				<legend>用户信息</legend>
				<table style="margin:0px auto" >
					<tr>
						<td><lable>小区：</lable></td>
						<td>
							<input type="text" name="n_name" class="easyui-textbox" readonly="readonly"/>
						</td>
						<td><label>住宅类型：</label></td>
						<td>
							<select class="easyui-combobox" name="hk_id" data-options="panelHeight:'auto'" style="width:132px;">
								<option value="2" >高层</option>
								<option value="3" >商业</option>
								<option value="1" selected="selected">多层</option>
							</select>
						</td>
						<td><label>用户标识：</label></td>
						<td><input type="text" name="customerId" class="easyui-textbox"/></td>
						<td><label>用户号：</label></td>
						<td><input type="text" name="c_num" class="easyui-textbox" readonly="readonly"/></td>
						<td><label>用户名：</label></td>
						<td><input type="text" name="customerName" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>关联ID：</label></td>
						<td><input type="text" name="apid" class="easyui-textbox"/></td>
						<td><label>手机：</label></td>
						<td><input type="text" name="customerMobile" class="easyui-textbox"/></td>
						<td><label>邮箱：</label></td>
						<td><input type="text" name="customerEmail" class="easyui-textbox"/></td>
						<td><label>身份证：</label></td>
						<td><input type="text" name="nationalId" class="easyui-textbox"/></td>
						<td><label>地址：</label></td>
						<td><input type="text" name="customerAddr" class="easyui-textbox" style="width:100%"/></td>
						
					</tr>
					<tr>
						<td><label>预后付费：</label></td>
						<td>
							<select class="easyui-combobox" id="prePaySign" name="prePaySign" data-options="panelHeight:'auto'" style="width:132px;">
								<option value="1">预付费</option>
								<option value="0" >后付费</option>
							</select>
						</td>
						<td><label>金额：</label></td>
						<td><input type="text" name="customerBalance" class="easyui-textbox"/></td>
						<td><label>余额阀值：</label></td>
						<td><input type="text" name="warnThre" class="easyui-textbox"/></td>
					</tr>
					<tr>
						
						<td><label>提醒开关：</label></td>
						<td>
							<select class="easyui-combobox" name="warnSwitch" data-options="panelHeight:'auto'" style="width:132px;">
								<option value="1" >开</option>
								<option value="0">关</option>
							</select>
						</td>
						<td><label>提醒方式：</label></td>
						<td>
							<select class="easyui-combobox" name="warnStyle" data-options="panelHeight:'auto'" style="width:132px;">
								<option value="0">邮件</option>
								<option value="1" >短信</option>
							</select>
						</td>
						<td><label>新单价：</label></td>
						<td colspan="1">
							<select class="easyui-combobox" panelHeight="auto" style="width:132px">
					            <option value="java">Java</option>
					            <option value="c">C</option>
					            <option value="basic">Basic</option>
					            <option value="perl">Perl</option>
					            <option value="python">Python</option>
					        </select>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</form>
	 <fieldset style="padding-left: 10px;margin-top: 10px;display: none;" id="meterFd">
	 	<legend>表信息</legend>
		<table id="custMeters"></table>
	</fieldset>
	<div style="text-align:center;padding-top:10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateCust()">更新用户资料</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changePre()">预后付费转换</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="payFor()">缴费</a>
	</div>
	<div id="payInfoWin"></div>
</body>
</html>