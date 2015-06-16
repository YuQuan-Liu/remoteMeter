<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抄表</title>
<%@include file="/commonjsp/top.jsp" %>
<c:set var="menus" scope="session" value="${userInfo.menus}"/>
</head>
<body>

	<div id="tb" style="float:left;">
		小区： 
		<select class="easyui-combobox" panelHeight="auto"
			style="width: 100px" id="sel_neibours" data-options="editable:false">
			<option value="">请选择小区</option>
			<c:forEach var="n" items="${neighbor_list }">
				<option value="${n.pid }">${n.neighborName }</option>
			</c:forEach>
		</select> 
		用户号: <input class="easyui-textbox" id="cust_info"> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="loadCust()">查找</a>
	</div>
	<div style="float:left;width:400px;text-align:center;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateCust()">更新用户资料</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changePre()">预后付费转换</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="payFor()">交费</a>
	</div>
	
	<form id="customer" method="post" style="margin-top:35px;">
		<div id="custInfoDiv">
			 <fieldset  style="border:1px solid #e3e3e3;margin-top:10px;">
				<legend>用户信息</legend>
				<table style="margin:10px;">
					<tr>
						<td><label>用户标识：</label></td>
						<td><input type="text" name="customerId" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label>用户号：</label></td>
						<td><input type="text" name="c_num" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label>关联ID：</label></td>
						<td><input type="text" name="apid" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label>住宅类型：</label></td>
						<td><input type="text" name="hk" data-options="disabled:true" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>用户名：</label></td>
						<td><input type="text" name="customerName" id="customerName" class="easyui-textbox"/></td>
						<td><label>手机：</label></td>
						<td><input type="text" name="customerMobile" id="customerMobile" class="easyui-textbox"/></td>
						<td><label>邮箱：</label></td>
						<td><input type="text" name="customerEmail" id="customerEmail" class="easyui-textbox"/></td>
						<td><label>身份证：</label></td>
						<td><input type="text" name="nationalId" id="nationalId" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td><label>地址：</label></td>
						<td colspan="7"><input type="text" name="customerAddr" class="easyui-textbox" data-options="disabled:true" style="width:100%"/></td>
					</tr>
					<tr>
						<td><label>预后付费：</label></td>
						<td><input type="text" name="prePaySign" id="prePaySign" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label>金额：</label></td>
						<td><input type="text" name="customerBalance" id="customerBalance" data-options="disabled:true" class="easyui-textbox"/></td>
						<td><label>余额阀值：</label></td>
						<td><input type="text" name="warnThre" data-options="disabled:true" class="easyui-textbox"/></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</form>
	
	<div style="margin:10px 0px;">
		<span>表信息</span>
		<span style="margin-left:20px;">新单价</span>
		<select class="easyui-combobox" panelHeight="auto" editable=false style="width:132px" id="price">
		<c:forEach var="p" items="${price_list }">
		<option value="${p.pid }">${p.priceKindName }</option>
		</c:forEach>
	</select>
	</div>
	<table id="custMeters" style="width:100%;height:100px;"></table>
	
	<div style="margin-top:10px;">
		<p>交费信息</p>
	</div>
	<table id="payInfoTab" style="width:100%;height:100px;"></table>
	
	<div style="margin-top:10px;">
		<p>扣费信息</p>
	</div>
	<table id="costInfoTab" style="width:100%;height:100px;"></table>
	
<!-- 	<div id="meterCurveWin"></div> -->
	<div id="meterChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
<script type="text/javascript">
$(function(){
	$('#custMeters').datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
	    columns:[[
	        {field:'pid',title:'ID',hidden:true},
			{field:'gprs',title:'集中器',width:40},
	        {field:'qfh',title:'铅封号',width:40},
	      	{field:'steelNum',title:'钢印号',width:40},
	      	{field:'collectorAddr',title:'采集器地址',width:60},
	      	{field:'meterAddr',title:'表地址',width:40},
	      	{field:'meterSolid',title:'虚实表',width:40,formatter:function(value,row,index){
	      		if(value==1){
	      			return "实表";
	      		}else{
	      			return "虚表";
	      		}
	      	}},
	      	{field:'mk',title:'表类型',width:40},
	      	{field:'pk',title:'单价',width:40},
	      	{field:'valveState',title:'阀门状态',width:30,formatter:function(value,row,index){
	      		if(value==1){
	      			return "开";
	      		}else{
	      			if(value == 0){
	        			  return "关";
	        		  }else{
	        			  return "异常";
	        		  }
	      		}
	      	}},
	      	{field:'deductionStyle',title:'结算方式',width:40},
	      	{field:'valveOffthre',title:'关阀余额',width:40},
	      	{field:'timerSwitch',title:'定时检测',width:40},
	      	{field:'timer',title:'定时时间',width:40},
	      	{field:'readdata',title:'表读数',width:40},
	        {field:'readtime',title:'抄表时间',width:40},
	      	{field:'changend',title:'换表底数',width:40},
	      	{field:'action',title:'操作',width:130,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a class='operateHref' onclick='openValue("+row.pid+","+index+")'> 开阀 </a>"
				+"<a class='operateHref' onclick='updatePrice("+row.pid+","+index+")'>更新单价 </a>"
				+"<a class='operateHref' onclick='waterwaste("+row.pid+","+index+")'>水费减免 </a>"
				+"<a class='operateHref' onclick='draw("+row.pid+","+index+")'>水表曲线</a>";
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
			{field:'c_num',title:'用户号',width:60},
	        {field:'customerId',title:'用户ID',width:60},
	      	{field:'customerName',title:'用户名',width:60},
	      	{field:'customerAddr',title:'地址',width:60},
	      	{field:'amount',title:'交费金额',width:60},
	      	{field:'actionTime',title:'交费时间',width:60},
	      	{field:'adminName',title:'收费员',width:60},
	      	{field:'action',title:'操作',width:90,halign:'center',align:'center',formatter: function(value,row,index){
				return <c:if test="${menus['undo']=='t'}"> 
				"<a href='#' class='operateHref' onclick='cancelPay("+row.pid+","+index+")' > 撤销 </a>"</c:if>
				"<a href='#' class='operateHref' onclick='chargeprint("+row.pid+")' >收费打印</a>"+
				"<a href='#' class='operateHref' onclick='chargedetailprint("+row.pid+")'> 详单打印</a>";
	  		}}
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
	        {field:'collectorAddr',title:'采集器地址',width:60},
	      	{field:'meterAddr',title:'表地址',width:60},
	      	{field:'steelNum',title:'钢印号',width:60},
	      	{field:'pricekindname',title:'扣费单价',width:80},
	        {field:'lastderead',title:'扣费读数',width:80},
	        {field:'meterread',title:'表读数',width:80},
	        {field:'changeend',title:'换表底数',width:80},
	        {field:'meterreadtime',title:'抄表时间',width:80},
	        {field:'yl',title:'用量',width:80,formatter:function(value,row,index){
	        	if(row.changeend > 0){
	       	  		return row.meterread+row.changeend-row.lastderead;
	        	}else{
	        		return row.meterread-row.lastderead;
	        	}
	          }},
	        {field:'demoney',title:'扣费金额',width:80}<c:if test="${menus['undo']=='t'}"> ,
	      	{field:'action',title:'操作',width:90,halign:'center',align:'center',formatter: function(value,row,index){
				return "<a href='#' class='operateHref' onclick='cancleCost("+row.mdl_id+","+index+")' >撤销</a>";
	  		}}</c:if>
	    ]],
	});
});

	var pid = 0;
	function loadCust(){
		var custId = $('#cust_info').textbox("getValue");
		var nbrId = $('#sel_neibours').combobox('getValue');
		if(nbrId != "" && custId != ""){
			$.ajax({
				url : "${path}/charge/custinfo.do",
				data : {
					nbrId : nbrId,
					custId : custId
				},
				dataType : "json",
				success : function(data) {
					if(data.pid > 0){
						//加载form数据
						pid = data.pid;
						$('#customer').form('load', data);
						if(data.prePaySign == 1){
							$('#prePaySign').textbox('setValue','预付费');
						}else{
							$('#prePaySign').textbox('setValue','后付费');
						}
						$('#custMeters').datagrid({
							url:"${path}/charge/custMeters.do",
							queryParams: {
								custId:data.pid
							}
						});
						$('#payInfoTab').datagrid({
							url:"${path}/charge/payInfoContent.do",
							queryParams: {
								custId:data.pid
							}
						});
						$('#costInfoTab').datagrid({
							url:"${path}/charge/costInfoContent.do",
							queryParams: {
								custId:data.pid
							}
						});
					}else{
						pid = 0;
						$.messager.alert("Info","未找到用户！");
					}
				}
			});
		}else{
			$.messager.show({
				title : 'Info',
				msg : '请输入信息',
				showType : 'slide'
			});
		}
	}
	function updateCust() {
		if(pid == 0){
			$.messager.show({
				title : 'Info',
				msg : '请选择用户',
				showType : 'slide'
			});
			return;
		}
		var customerName =$("#customerName").textbox("getValue");
		var customerEmail =$("#customerEmail").textbox("getValue");
		var customerMobile =$("#customerMobile").textbox("getValue");
		var nationalId =$("#nationalId").textbox("getValue");
		
		$.messager.confirm('更新用户资料', '用户名：'+customerName+'</br>手机：'+customerMobile+'</br>邮箱：'+customerEmail+'</br>身份证号：'+nationalId+'</br>', function(r) {
			if (r) {
				//更新用户资料
				$.ajax({
            		type:"POST",
    	    		url:"${path}/charge/updateCustInfo.do",
    	    		data:{
    	    			pid:pid,
    	    			customerName:customerName,
    	    			customerEmail:customerEmail,
    	    			customerMobile:customerMobile,
    	    			nationalId:nationalId
    	    		},
    	    		dataType:"json",
    	    		success:function(data){
    	    			if(data == 1){
    	    				$.messager.show({
								title : 'Info',
								msg : '更新成功！',
								showType : 'slide'
							});
    	    			}else{
    	    				$.messager.show({
								title : 'Info',
								msg : '更新成功！',
								showType : 'slide'
							});
    	    			}
    	    		}
    	    	});
			}
		});
	}
	function changePre() {
		if(pid == 0){
			$.messager.show({
				title : 'Info',
				msg : '请选择用户',
				showType : 'slide'
			});
			return;
		}
		var prePaySign = $('#prePaySign').textbox('getValue');
		var pre =0;
		var prestr;
		if(prePaySign =="预付费"){
			pre = 0;
			prestr = '后付费';
		}else{
			pre = 1;
			prestr = '预付费';
		}
		//预后付费转换
		$.messager.confirm('确认操作', '确认转换为'+prestr+'?', function(r) {
			if (r) {
				$.ajax({
					url : "${path}/charge/updatePrepaySign.do",
					data : {
						custId : pid,
						prePaySign : pre
					},
					dataType : "json",
					success : function(data) {
						if (data > 0) {
							$.messager.show({
								title : 'Info',
								msg : '转换成功！',
								showType : 'slide'
							});
							$('#prePaySign').textbox('setValue',prestr);
						}
					}
				});
			}
		});

	}
	function payFor() {
		//交费
		if(pid == 0){
			$.messager.show({
				title : 'Info',
				msg : '请选择用户',
				showType : 'slide'
			});
			return;
		}
		var prePaySign = $('#prePaySign').textbox('getValue');
		if(prePaySign !="预付费"){
			$.messager.alert('Error','后付费用户不可在此交费！');
			return;
		}
		$.messager.prompt('Info', '请输入交费金额:', function(r){
			if (r){
				var amount = parseFloat(r);
				if(amount.toString() != "NaN" && amount > 0){
					$.ajax({
						url : "${path}/charge/pay.do",
						data : {
							c_id : pid,
							amount : amount
						},
						dataType : "json",
						success : function(data) {
							if (data.balance > 0) {
								$.messager.show({
									title : 'Info',
									msg : '交费成功！',
									showType : 'slide'
								});
								window.open("${path}/charge/charge/printcharge.do?cplid="+data.cplid,"_blank");
								$('#customerBalance').textbox('setValue',data.balance);
							}else{
								$.messager.show({
									title : 'Info',
									msg : '交费失败！',
									showType : 'slide',
									timeout : 0
								});
							}
						}
					});
				}else{
					$.messager.alert('Error','金额有误！');
				}
				
			}
		});
	}
	
	function chargeprint(cplid){
		window.open("${path}/charge/charge/printcharge.do?cplid="+cplid,"_blank");
	}
	
	function chargedetailprint(cplid){
		window.open("${path}/charge/charge/printdetailcharge.do?cplid="+cplid+"&cid="+pid,"_blank");
	}
	
	function openValue(mid,index) {
		$.messager.confirm('确认操作', '确认开阀?', function(r) {
			if (r) {
				$.ajax({
					type:"POST",
					url:"${path}/readme/valve/valvecontrol.do",
					dataType:"json",
					data:{
						m_id:mid,
						control:1
					},
					success:function(data){
						if(data.result == "success"){
							$.messager.progress({title:"操作中...",text:"",interval:100});
							interval = setInterval(function(){checkcontroling(data.pid,index);},1000);
						}else{
							$.messager.alert('Error','操作失败,请稍后再试');
						}
					}
				});
			}
		});
	}
	function checkcontroling(valvelogid,index){
		$.ajax({
			type:"POST",
			url:"${path}/readme/valve/checkcontroling.do",
			dataType:"json",
			data:{
				valvelogid:valvelogid
			},
			success:function(data){
				if(data.status == 100){
					$.messager.progress('close');
					clearInterval(interval);
					$.messager.show({
						title : 'Info',
						msg : '开阀完成！',
						showType : 'slide'
					});
// 					$.messager.alert('操作结果',"完成个数:"+data.completecount+"\r\n异常个数:"+data.errorcount,'info'); 
					if(data.completecount+data.errorcount == 1){
						//单个表
						$("#custMeters").datagrid('updateRow', {index:index,row:{valveState:data.switch_}});
					}
				}
			}
		});
	}

	function updatePrice(mid,index) {
		var priceId = $('#price').combobox('getValue');
		var priceName = $('#price').combobox('getText');
		$.messager.confirm('确认操作', '确认更新单价为</br>'+priceName+'?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "${path}/charge/updatePrice.do",
					dataType : "json",
					data : {
						meterId : mid,
						priceId : priceId
					},
					success : function(data) {
						if (data == 1) {
							$.messager.show({
								title : 'Info',
								msg : '更新成功！',
								showType : 'slide'
							});
							$("#custMeters").datagrid('updateRow', {index:index,row:{pk:priceName}});
						} else {
							$.messager.show({
								title : 'Info',
								msg : '更新失败！',
								showType : 'slide',
								timeout : 0
							});
						}
					}
				});
			}
		});
	}
	
	function waterwaste(mid,index_){
		$.messager.prompt('水费减免', '请输入水费减免吨数', function(r){
	        if (r){
				if(r > 0 && r < 1000){
					$.ajax({
						type:"POST",
						url:"${path}/charge/waterwaste.do",
						dataType:"json",
						data:{
							m_id:mid,
							waste:r
						},
						success:function(data){
//	 						alert(data.id+data.read);
							if(data == 1){
								$.messager.show({
									title : 'Info',
									msg : '操作成功！',
									showType : 'slide'
								});
								$('#costInfoTab').datagrid({
									url:"${path}/charge/costInfoContent.do",
									queryParams: {
										custId:pid
									}
								});
							}
						}
					});
				}else{
					 $.messager.alert('Error','减免吨数异常：'+r+',请重新输入');
				}
	        }
	    });
	}
	
	function meterQX(meterId) {
		//水表曲线
		$('#meterCurveWin').window({
			href : '${path}/charge/meterCurve.do',
			width : 600,
			height : 450,
			minimizable : false,
			maximizable : false,
			title : '水表曲线'
		});
	}
	function cancelPay(id,index_) {
		
		var amount = $('#payInfoTab').datagrid('getRows')[index_]["amount"];
		var time = $('#payInfoTab').datagrid('getRows')[index_]["actionTime"];
		
		$.messager.confirm('确认操作', '确认撤销</br>'+time+'</br>交费'+amount+'?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "${path}/cahrge/canclePay.do",
					dataType : "json",
					data : {
						'custPayId' : id
					},
					success : function(data) {
						if (data.state == "succ") {
							$.messager.show({
								title : 'Info',
								msg : '撤销成功！',
								showType : 'slide',
								timeout : 3000
							});
							$('#payInfoTab').datagrid("deleteRow",index_);
							$('#customerBalance').textbox('setValue',$('#customerBalance').textbox('getValue')-amount);
							
						} else {
							$.messager.show({
								title : 'Info',
								msg : '撤销失败！',
								showType : 'slide',
								timeout : 0
							});
						}
					}
				});
			}
		});
	}

	function cancleCost(id,index_) {
		var demoney = $('#costInfoTab').datagrid('getRows')[index_]["demoney"];
		var time = $('#costInfoTab').datagrid('getRows')[index_]["meterreadtime"];
		
		$.messager.confirm('确认操作', '确认撤销</br>抄表时间'+time+'</br>扣费'+demoney+'?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "${path}/charge/cancleCost.do",
					dataType : "json",
					data : {
						'meterDeLogId' : id
					},
					success : function(data) {
						if (data.state == "succ") {
							$.messager.show({
								title : 'Info',
								msg : '撤销成功！',
								showType : 'slide'
							});
							$('#costInfoTab').datagrid("deleteRow",index_);
							$('#customerBalance').textbox('setValue',$('#customerBalance').textbox('getValue')+demoney);
							
						} else {
							$.messager.show({
								title : 'Info',
								msg : '撤销失败！',
								showType : 'slide',
								timeout : 0
							});
						}
					}
				});
			}
		});
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