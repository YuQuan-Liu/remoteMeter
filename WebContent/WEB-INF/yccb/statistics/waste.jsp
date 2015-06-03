<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>水损分析</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label>小区</label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto'">
					<option value="">请选择小区</option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<label style="margin-left:20px;">年</label>
	    		<input class="easyui-numberspinner" style="width:100px;" id="year_" name="year_" data-options="min:2015,max:2099" value=""/>
	    		
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" >查找</a>
			</div>
		</form>
	</div>
	<table id="wasteSettleTab" style="width:500px;height:200px;"></table>
	<div id="wasteChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
	<script type="text/javascript">
		$(function(){
			var now_ = new Date();
			$("#year_").numberspinner('setValue',now_.getFullYear());
			$("#wasteSettleTab").datagrid({
				striped:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				columns:[[
				          {field:'settleTime',title:'结算日期',width:100},
				          {field:'nread',title:'总表',width:100},
				          {field:'slaveSum',title:'户表和',width:100},
				          {field:'c',title:'差值',width:100,formatter:function(value,row,index){
				        	  return row.nread-row.slaveSum;
				          }}
				      ]]
			});
		});
		
		var option = {
			title:{
				text:"水量"
			},
			tooltip:{
				show : true
			},
			legend:{
// 				data : ['总表','楼表和','户表和']
				data : ['总表','户表和']
			},
			xAxis:[{
	            	type : 'category',
	            	data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	        }],
			yAxis:[{
				type:'value'
			}],
			series:[{
					"name":"总表",
					"type":"bar",
					"data":[0,0,0,0,0,0,0,0,0,0,0,0]
				},
// 				{
// 					"name":"楼表和",
// 					"type":"bar",
// 					"data":[0,0,0,0,0,0,0,0,0,0,0,0]
// 				},
				{
					"name":"户表和",
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
                myChart = ec.init(document.getElementById('wasteChart')); 
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        function search_(){
        	var n_id = $("#neighbor").combobox("getValue");
        	var year_ = $("#year_").numberspinner('getValue');
        	if(n_id != "" && year_ != ""){
        		$('#wasteSettleTab').datagrid({
    				url:"${path}/statistics/waste/listsettledyl.do",
    				queryParams: {
    					n_id:n_id,
        				year:year_
    				}
    			});
        		
        		$.ajax({
        			type:"POST",
        			url:"${path}/statistics/waste/listwastedata.do",
        			dataType:"json",
        			data:{
        				n_id:n_id,
        				year:year_
        			},
        			success:function(data){
        				//load the data in myChart;
        				option.series[0].data = data.nread;
        				option.series[1].data = data.slaveSum;
        				myChart.setOption(option); 
        			}
        		});
        	}
        }
	</script>
</body>
</html>