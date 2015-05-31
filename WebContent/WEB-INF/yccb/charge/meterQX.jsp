<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水表曲线</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
	<div id="mainChart" style="height:400px"></div>
	<script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '${path}/resource/echarts'
            }
        });
        
     // 使用
        require(
            [
                'echarts',
                'echarts/chart/line',
                'echarts/chart/bar'
                 // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('mainChart')); 
                var option = {
                	    title : {
                	        text: '水表曲线',
                	        subtext: '测试数据'
                	    },
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    legend: {
                	        data:['用水量']
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	           // mark : {show: true},
                	            dataView : {show: true, readOnly: true},
                	            magicType : {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : [
                	       
                	        {
                	            name:'用水量',
                	            type:'line',
                	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                	            markPoint : {
                	                data : [
                	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                	                ]
                	            },
                	            markLine : {
                	                data : [
                	                    {type : 'average', name : '平均值'}
                	                ]
                	            }
                	        }
                	    ]
                	};
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>
</body>
</html>