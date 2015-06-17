<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/commonjsp/top.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>收费率统计</title>
</head>
<body>
	<div style="margin:10px;">
		<form id="" method="post">
			<div>
				
	    		<label style="margin-right:10px;"><fmt:message key='year'/></label>
	    		<input class="easyui-numberspinner" style="width:100px;" id="year_" name="year_" data-options="min:2015,max:2099" value=""/>
	    		
				<a href="javascript:void(0)" class="easyui-linkbutton operateHref" onclick="search_()" ><fmt:message key='search'/></a>
			</div>
		</form>
	</div>
	<div style="margin-top:10px;">
		<table id="chargerateTab" style="width:700px;height:300px;"></table>
	</div>
	<div id="owecountChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
	<div id="chargerateChart" style="width:96%;height:400px;border:1px solid #e3e3e3;padding:10px;margin-top:10px;"></div>
	<script type="text/javascript">
		$(function(){
			var now_ = new Date();
			$("#year_").numberspinner('setValue',now_.getFullYear());
			
			$("#chargerateTab").datagrid({
				striped:true,
				fitColumns:true,
				method:'post',
				loadMsg:'<fmt:message key="main.loading"/>',
				rownumbers:true,
				singleSelect:true,
				columns:[[
				          {field:'n_id',title:'ID',width:60,hidden:true},
				          {field:'n_name',title:'<fmt:message key='common.neighborName'/>',width:80},
				          {field:'owecount',title:'<fmt:message key="rate.owecount"/>',width:80},
				          {field:'allcount',title:'<fmt:message key="rate.allcount"/>',width:80},
				          {field:'owebalance',title:'<fmt:message key="rate.owebalance"/>',width:80},
				          {field:'demoney',title:'<fmt:message key="rate.demoney"/>',width:80},
				          {field:'balance',title:'<fmt:message key="rate.balance"/>',width:80},
				          {field:'owerate',title:'<fmt:message key="rate.owerate"/>',width:80},
				          {field:'chargerate',title:'<fmt:message key="rate.chargerate"/>',width:80}
				      ]]
			});
			
		});
		
		var owe_option = {
			title:{
				text:"户数"
			},
			tooltip:{
				show : true
			},
			legend:{
				data : ['欠费用户','正常用户']
			},
			xAxis:[{
	            	type : 'category',
	            	data : ['']
	        }],
			yAxis:[{
				name:"数量",
				type:'value'
			}],
			series:[{
					"name":"欠费用户",
					"type":"bar",
					"stack":'户数',
					"data":[0]
				},
				{
					"name":"正常用户",
					"type":"bar",
					"stack":'户数',
					"data":[0]
				}
			]
		};
		var money_option = {
				title:{
					text:"金额"
				},
				tooltip:{
					show : true
				},
				legend:{
					data : ['欠费金额','正常金额']
				},
				xAxis:[{
		            	type : 'category',
		            	data : ['']
		        }],
				yAxis:[{
					name:"数量",
					type:'value'
				}],
				series:[{
						"name":"欠费金额",
						"type":"bar",
						"stack":'金额',
						"data":[0]
					},
					{
						"name":"小区余额",
						"type":"bar",
						"stack":'金额',
						"data":[0]
					}
				]
			};
		// 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
		
		var owe;
		var money;
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                owe = ec.init(document.getElementById('owecountChart')); 
                // 为echarts对象加载数据 
                owe.setOption(owe_option); 
                
                money = ec.init(document.getElementById('chargerateChart')); 
                // 为echarts对象加载数据 
                money.setOption(money_option); 
            }
        );
        function search_(){
        	var year_ = $("#year_").numberspinner('getValue');
        	if(year_ != ""){
        		$('#chargerateTab').datagrid({
    				url:"${path}/statistics/chargerate/listchargerate.do",
    				queryParams: {
    					year:year_
    				}
    			});
        		$.ajax({
    				type:"POST",
    				url:"${path}/statistics/chargerate/listchargeratedraw.do",
    				dataType:"json",
    				data:{
    					year:year_
    				},
    				success:function(data){
    					owe_option.xAxis[0].data = data.n_name;
    					owe_option.series[0].data = data.owecount;
    					owe_option.series[1].data = data.count;
    					
    					money_option.xAxis[0].data = data.n_name;
    					money_option.series[0].data = data.owebalance;
    					money_option.series[1].data = data.balance;
        				owe.setOption(owe_option); 
        				money.setOption(money_option); 
    				}
    			});
        	}
        }
	</script>
</body>
</html>