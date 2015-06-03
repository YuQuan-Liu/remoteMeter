<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>结算用水量统计</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				
	    		<label style="margin-right:10px;">年</label>
	    		<input class="easyui-numberspinner" style="width:100px;" id="year_" name="year_" data-options="min:2015,max:2099" value=""/>
	    		
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" >查找</a>
			</div>
		</form>
	</div>
	<table id="settlewaterTab" style="width:700px;height:300px;"></table>
	<div id="settleChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
	<script type="text/javascript">
		$(function(){
			var now_ = new Date();
			$("#year_").numberspinner('setValue',now_.getFullYear());
			
			$("#settlewaterTab").datagrid({
				striped:true,
				fitColumns:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				singleSelect:true,
				columns:[[
				          {field:'n_id',title:'ID',width:60,hidden:true},
				          {field:'n_name',title:'小区',width:80},
				          {field:'yl',title:'用量',width:80},
				          {field:'demoney',title:'金额',width:80},
				          {field:'startTime',title:'结算月',width:80},
				          {field:'action',title:'操作',width:100,halign:'center',align:'center',
								formatter: function(value,row,index){
									return "<a href='#' class='operateHref' onclick='draw("+row.n_id+","+index+")'>绘制小区曲线</a>";
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
				data : ['用量','金额']
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
				},
				{
					"name":"金额",
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
                myChart = ec.init(document.getElementById('settleChart')); 
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        function search_(){
        	var year_ = $("#year_").numberspinner('getValue');
        	if(year_ != ""){
        		$('#settlewaterTab').datagrid({
    				url:"${path}/statistics/settlelogwater/listsettlewater.do",
    				queryParams: {
    					year:year_
    				}
    			});
        	}
        }
        function draw(n_id,index){
        	var n_name = $('#settlewaterTab').datagrid('getRows')[index]["n_name"];
        	var year_ = $("#year_").numberspinner('getValue');
        	$.ajax({
				type:"POST",
				url:"${path}/statistics/settlelogwater/draw.do",
				dataType:"json",
				data:{
					n_id:n_id,
					year:year_
				},
				success:function(data){
					option.title.text = n_name+"水量";
					option.series[0].data = data.yl;
    				option.series[1].data = data.demoney;
    				myChart.setOption(option); 
				}
			});
        }
	</script>
</body>
</html>