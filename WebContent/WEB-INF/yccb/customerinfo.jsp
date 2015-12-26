<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抄表</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
	<div style="float:right;width:200px;text-align:center;">
		<a href="${path}/logout.do" class="easyui-linkbutton"><fmt:message key="logout"/></a>
	</div>
	<form id="customer" method="post" style="margin-top:35px;">
		<div id="custInfoDiv">
			 <fieldset  style="border:1px solid #e3e3e3;margin-top:10px;">
				<legend><fmt:message key='c.info'/></legend>
				<div style="width:200px;text-align:center;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="payFor()"><fmt:message key='charge.pay'/></a>
				</div>
				<table style="margin:10px;">
					<tr>
						<td><label><fmt:message key='c.customerid'/>：</label></td>
						<td><input type="text" name="customerId" data-options="disabled:true" class="easyui-textbox" value="${c.customerId }"/></td>
						<td><label><fmt:message key='c.num'/>：</label></td>
						<td><input type="text" name="c_num" data-options="disabled:true" class="easyui-textbox" value="${c.louNum }-${c.dyNum }-${c.huNum }"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.name'/>：</label></td>
						<td><input type="text" name="customerName" id="customerName" data-options="disabled:true" class="easyui-textbox" value="${c.customerName }"/></td>
						<td><label><fmt:message key='common.mobile'/>：</label></td>
						<td><input type="text" name="customerMobile" id="customerMobile" data-options="disabled:true" class="easyui-textbox" value="${c.customerMobile }"/></td>
						<td><label><fmt:message key='common.email'/>：</label></td>
						<td><input type="text" name="customerEmail" id="customerEmail" data-options="disabled:true" class="easyui-textbox" value="${c.customerEmail }"/></td>
						<td><label><fmt:message key='c.nationalid'/>：</label></td>
						<td><input type="text" name="nationalId" id="nationalId" data-options="disabled:true" class="easyui-textbox" value="${c.nationalId }"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='common.addr'/>：</label></td>
						<td colspan="7"><input type="text" name="customerAddr" class="easyui-textbox" data-options="disabled:true" style="width:100%" value="${c.customerAddr }"/></td>
					</tr>
					<tr>
						<td><label><fmt:message key='c.prestyle'/>：</label></td>
						<td><input type="text" name="prePaySign" id="prePaySign" data-options="disabled:true" class="easyui-textbox" 
							<c:if test="${c.prePaySign=='1'}"> value='<fmt:message key='c.pre'/>'</c:if><c:if test="${c.prePaySign=='2'}"> value='<fmt:message key='c.post'/>'</c:if>/></td>
						<td><label><fmt:message key='c.balance'/>：</label></td>
						<td><input type="text" name="customerBalance" id="customerBalance" data-options="disabled:true" class="easyui-textbox" value="${c.customerBalance }"/></td>
						<td><label><fmt:message key='c.warnthre'/>：</label></td>
						<td><input type="text" name="warnThre" data-options="disabled:true" class="easyui-textbox" value="${c.warnThre }"/></td>
						<td><label><fmt:message key='c.hushu'/>：</label></td>
						<td><input type="text" name="peoplecnt" data-options="disabled:true" class="easyui-textbox" value="${c.peoplecnt }"/></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</form>
	
	<table id="custMeters" style="width:100%;height:100px;"></table>
	
	<div style="margin-top:10px;">
		<p><fmt:message key='charge.payinfo'/></p>
	</div>
	<table id="payInfoTab" style="width:100%;height:100px;"></table>
	
	<div style="margin-top:10px;">
		<p><fmt:message key='charge.deinfo'/></p>
	</div>
	<table id="costInfoTab" style="width:100%;height:100px;"></table>
	
	<div id="meterChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
	
	<script type="text/javascript">
	var cid = ${c.pid };
	$(function(){
		$('#custMeters').datagrid({
			striped:true,
			fitColumns:true,
			method:'post',
			loadMsg:'<fmt:message key="main.loading"/>',
			rownumbers:true,
		    columns:[[
		        {field:'pid',title:'ID',hidden:true},
		        {field:'qfh',title:'<fmt:message key='m.qfh'/>',width:40},
		      	{field:'steelNum',title:'<fmt:message key='m.steel'/>',width:40},
		      	{field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:40},
		      	{field:'mk',title:'<fmt:message key='m.mk'/>',width:40},
		      	{field:'pk',title:'<fmt:message key='m.pk'/>',width:40},
		      	{field:'valveState',title:'<fmt:message key='m.vstate'/>',width:30,formatter:function(value,row,index){
		      		if(value==1){
		      			return "<fmt:message key='common.open'/>";
		      		}else{
		      			if(value == 0){
		        			  return "<fmt:message key='common.close'/>";
		        		  }else{
		        			  return "<fmt:message key='common.exception'/>";
		        		  }
		      		}
		      	}},
		      	{field:'deductionStyle',title:'<fmt:message key='m.destyle'/>',width:40,formatter:function(value,row,index){
		        	  if(value == 1){
		        		  return "<fmt:message key='m.deread'/>";
		        	  }else{
		        		  return "<fmt:message key='m.noderead'/>";
		        	  }
		        }},
		      	{field:'valveOffthre',title:'<fmt:message key='m.valveoffthre'/>',width:40},
		      	{field:'deRead',title:'<fmt:message key='m.deread'/>',width:40},
		      	{field:'destartread',title:'<fmt:message key='m.destartread'/>',width:40},
		      	{field:'readdata',title:'<fmt:message key='m.readdata'/>',width:40},
		        {field:'readtime',title:'<fmt:message key='m.readtime'/>',width:40},
		      	{field:'changend',title:'<fmt:message key='m.changeend'/>',width:40},
		      	{field:'action',title:'<fmt:message key='common.action'/>',width:130,halign:'center',align:'center',formatter: function(value,row,index){
					return "<a class='operateHref' onclick='draw("+row.pid+","+index+")'><fmt:message key='charge.draw'/></a>";
		  		}}
		    ]],
		});
		// 缴费信息
		$('#payInfoTab').datagrid({
		   	striped:true,
			fitColumns:true,
			method:'post',
			loadMsg:'<fmt:message key="main.loading"/>',
			rownumbers:true,
		    columns:[[
		        {field:'pid',title:'ID',hidden:true},
				{field:'c_num',title:'<fmt:message key='c.num'/>',width:60},
	// 	        {field:'customerId',title:'用户ID',width:60},
		      	{field:'customerName',title:'<fmt:message key='c.name'/>',width:60},
		      	{field:'customerAddr',title:'<fmt:message key='common.addr'/>',width:60},
		      	{field:'amount',title:'<fmt:message key='charge.amount'/>',width:60},
		      	{field:'actionTime',title:'<fmt:message key='charge.paytime'/>',width:60},
		      	{field:'adminName',title:'<fmt:message key='charge.payadmin'/>',width:60}
		    ]]
		});
		//扣费信息
		$('#costInfoTab').datagrid({
		   	striped:true,
			fitColumns:true,
			method:'post',
			loadMsg:'<fmt:message key="main.loading"/>',
			rownumbers:true,
		    columns:[[
		        {field:'mdl_id',title:'ID',hidden:true},
		      	{field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:60},
		      	{field:'steelNum',title:'<fmt:message key='m.steel'/>',width:60},
		      	{field:'pricekindname',title:'<fmt:message key='m.pk'/>',width:80},
		        {field:'lastderead',title:'<fmt:message key='m.deread'/>',width:80},
		        {field:'meterread',title:'<fmt:message key='m.readdata'/>',width:80},
		        {field:'changeend',title:'<fmt:message key='m.changeend'/>',width:80},
		        {field:'meterreadtime',title:'<fmt:message key='m.readtime'/>',width:80},
		        {field:'yl',title:'<fmt:message key='yl'/>',width:80,formatter:function(value,row,index){
		        	if(row.changeend > 0){
		       	  		return row.meterread+row.changeend-row.lastderead;
		        	}else{
		        		return row.meterread-row.lastderead;
		        	}
		          }},
		        {field:'demoney',title:'<fmt:message key='demoney'/>',width:80}
		    ]],
		});
		
		
		$('#custMeters').datagrid({
			url:"${path}/charge/custMeters.do",
			queryParams: {
				custId:cid
			}
		});
		$('#payInfoTab').datagrid({
			url:"${path}/charge/payInfoContent.do",
			queryParams: {
				custId:cid
			}
		});
		$('#costInfoTab').datagrid({
			url:"${path}/charge/costInfoContent.do",
			queryParams: {
				custId:cid
			}
		});
	});
	
		function payFor() {
			//交费
			$.messager.alert('Info','正在努力开发中……');
		}
		
		var option = {
				title:{
					text:"扣费读数"
				},
				tooltip:{
					show : true
				},
				legend:{
					data : ['用量']
				},
				xAxis:[{
		            	type : 'category',
		            	data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        }],
				yAxis:[{
					type:'value'
				}],
				series:[{
						"name":"用量",
						"type":"bar",
						"data":[0,0,0,0,0,0,0,0,0,0,0,0]
					}
				]
			};
			// 路径配置
	        require.config({
	            paths: {
	                echarts: 'http://echarts.baidu.com/build/dist'
	            }
	        });
			
			var myChart;
	        // 使用
	        require(
	            [
	                'echarts',
	                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
	                'echarts/chart/line'
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                myChart = ec.init(document.getElementById('meterChart')); 
	                // 为echarts对象加载数据 
	                myChart.setOption(option); 
	            }
	        );
	        function draw(mid,index){
	        	var steelNum = $('#custMeters').datagrid('getRows')[index]["steelNum"];
	        	$.ajax({
					type:"POST",
					url:"${path}/charge/charge/draw.do",
					dataType:"json",
					data:{
						mid : mid
					},
					success:function(data){
						option.title.text = steelNum+"扣费读数";
						option.series[0].data = data.yl;
	    				myChart.setOption(option); 
					}
				});
	        }
	</script>
</body>
</html>