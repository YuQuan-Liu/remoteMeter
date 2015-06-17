<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>VIP</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				<label><fmt:message key='common.neighborName'/></label>
				<select class="easyui-combobox" id="neighbor" name="neighbor" style="width:100px" data-options="panelHeight:'auto'">
					<option value=""><fmt:message key='common.choosenei'/></option>
					<c:forEach var="n" items="${neighbor_list }">
					<option value="${n.pid }">${n.neighborName }</option>
					</c:forEach>
	    		</select>
	    		
	    		<label><fmt:message key='month'/></label>
				<input class="easyui-datetimespinner" id="month" data-options="highlight:1,formatter:formatter2,parser:parser2,selections:[[0,4],[5,7]]"  style="width:100px"/>
				
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" ><fmt:message key='search'/></a>
			</div>
		</form>
	</div>
	<div id="charts">
		<div id='vipchart0' style='width:96%;height:300px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;'></div>
	</div>
	<script type="text/javascript">
		$(function(){
			var now_ = new Date();
			$("#month").datetimespinner("setValue",now_.getFullYear()+"-"+now_.getMonth());
		});
		
		function formatter2(date){
            if (!date){return '';}
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            return y + '-' + (m<10?('0'+m):m);
        }
        function parser2(s){
            if (!s){return null;}
            var ss = s.split('-');
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            if (!isNaN(y) && !isNaN(m)){
                return new Date(y,m-1,1);
            } else {
                return new Date();
            }
        }
        
        var option = {
    			title:{
    				text:"表数",
    				subtest:""
    			},
    			tooltip:{
    				show : true
    			},
    			legend:{
    				data : ['用量']
    			},
    			xAxis:[{
    	            	type : 'category',
    	            	data : ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"]
    	        }],
    			yAxis:[{
    				type:'value'
    			}],
    			series:[{
    					"name":"用量",
    					"type":"line",
    					"data":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    				}
    			]
    		};
    		// 路径配置
            require.config({
                paths: {
                    echarts: 'http://echarts.baidu.com/build/dist'
                }
            });
    		
    		var EChart;
            // 使用
            require(
                [
                    'echarts',
                    'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                    'echarts/chart/line'
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    EChart = ec;
        			var myChart = EChart.init(document.getElementById('vipchart0')); 
            	    myChart.setOption(option);
                }
            );
            
		var i = 0;
        function search_(){
        	var n_id = $("#neighbor").combobox("getValue");
        	var month = $("#month").datetimespinner('getValue');
        	
        	if(n_id !="" && month !=""){
        		//ajax get the data 
        		$.ajax({
        			type:"POST",
        			url:"${path}/statistics/vip/listvipdata.do",
        			dataType:"json",
        			data:{
        				n_id:n_id,
        				month:month
        			},
        			success:function(data){
        				//load the data in myChart;
        				if(data.length > 0){
							//清空已有
        	     			$("#charts").empty();
        				}
        				for(var i = 0;i < data.length;i++){
        					var show = data[i];
        					if(show.meteraddr != ""){
            					$("#charts").append("<div id='vipchart"+show.id+"' style='width:96%;height:300px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;'></div>");
            					var myChart = EChart.init(document.getElementById('vipchart'+show.id)); 
    	       	     	        // 为echarts对象加载数据 
    	        				option.series[0].data = show.data;
    	        				option.title.text = month+"表数";
    	        				option.title.subtext = '表地址：'+show.meteraddr;
    	       	     	        myChart.setOption(option);
            				}
        				}
        				
        			}
        		});
        	}else{
        		$.messager.alert('Info', '<fmt:message key='common.choosenei'/>');
				return;
        	}
        }
	</script>
</body>
</html>