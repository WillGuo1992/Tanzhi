<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<link type="text/css" rel="stylesheet" href="../css/ui.css" />
	<script type="text/javascript" src="../js/index.js"></script>
	<script type="text/javascript">
		var nowDate = new Date();
		var NewHour = "";
		var NewData = 0;
		var yesterday = new Date((new Date().getTime()-1000*60*60*24));
		var monthago = new Date((new Date().getTime()-1000*60*60*24*30));
		var cur_year = nowDate.getFullYear();
		var cur_month = nowDate.getMonth() + 1 + "";
		if(cur_month.length <= 1){
			cur_month = "0" + cur_month;
		}
		var cur_date = nowDate.getDate()+"";
		if(cur_date.length <= 1){
			cur_date = "0" + cur_date;
		}
		var now_date = cur_year + "-" + cur_month + "-" + cur_date;
		var y_year = yesterday.getFullYear();
		var y_month = yesterday.getMonth() + 1 + "";
		if(y_month.length <= 1){
			y_month = "0" + y_month;
		}
		var y_date = yesterday.getDate()+"";
		if(y_date.length <= 1){
			y_date = "0" + y_date;
		}
		var yesterday_date = y_year + "-" + y_month + "-" + y_date;
		var m_year = monthago.getFullYear();
		var m_month = monthago.getMonth() + 1 + "";
		if(m_month.length <= 1){
			m_month = "0" + m_month;
		}
		var m_date = monthago.getDate()+"";
		if(m_date.length <= 1){
			m_date = "0" + m_date;
		}
		var monthago_date = m_year + "-" + m_month + "-" + m_date;
		$(document).ready(function () {
			$("#now_date_new").html(now_date);
// 			getRealtimeDatas();
			loadIndoorSum_new();
			loadStaySum_new();
			getSummariesStat_new();
			getTrafficChart_new();
// 			getThisHourRtData();
			getEntryPie_new();
			getMobileChart_new();
			getStoreyBar_new();
			getBusinessBar_new();
			getBrandBar_new();
		});
		function loadIndoorSum_new(){
			var url = basePath + "jaxrs/entirety/indoor/statistics";
			var cxl = Math.random();
			var para = {
					startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
			}
			$.post(url,para,loadIndoorSumCallBack_new,"json");
		}
		function loadIndoorSumCallBack_new (data) {
			var code = data.code;
			if(code == 0){
				var this_count = data.this_count;
				$("#today_indoor_new").html(this_count);
			}
		}
		function loadStaySum_new(){
			var url = basePath + "jaxrs/entirety/stay/statistics";
			var cxl = Math.random();
			var para = {
					startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
			}
			$.post(url,para,loadStaySumCallBack_new,"json");
		}
		function loadStaySumCallBack_new (data) {
			var code = data.code;
			if(code == 0){
				var this_count = data.this_count;
				$("#today_stay_new").html(this_count)
			}
		}
		function getThisHourRtData () {
			var url = "http://47.93.123.129:8080/view/rt_counts/" + GroupId;
			$.ajax({
				url:url,
				dataType:"json",
				success: getThisHourRtDataCallback
			});
		}
		function getThisHourRtDataCallback (data) {
			var code = data.c;
			if(code == 0){
				var tlist = data.d.ts;
				var dlist = data.d.cs;
				tlist.pop();
				dlist.pop();
				doRealTimeChart(tlist,dlist,"realtime_chart");
			}else{
				$("#realtime_chart").html("未查询到数据");
			}
		}
// 		function getRealtimeDatas () {
// 			var thisday = new Date();
// 			var thishour = thisday.getHours();
// 			var url1 = "http://47.93.123.129:8080/view/stays/"+GroupId+"/"+now_date+" 0"+"/"+now_date+" "+thishour;
// 			var url2 = "http://47.93.123.129:8080/view/stays/"+GroupId+"/"+yesterday_date+" 0"+"/"+yesterday_date+" "+thishour;
// 			var url3 = "http://47.93.123.129:8080/timeDistribution/entirePCRs/"+GroupId+"/"+now_date+" 0"+"/"+now_date+" "+thishour;
// 			var url4 = "http://47.93.123.129:8080/timeDistribution/entirePCRs/"+GroupId+"/"+yesterday_date+" 0"+"/"+yesterday_date+" "+thishour;
// 			$.ajax({
// 				url:url1,
// 				dataType:"json",
// 				success: tiCallback
// 			});
// 			$.ajax({
// 				url:url2,
// 				dataType:"json",
// 				success: yiCallback
// 			});
// 			$.ajax({
// 				url:url3,
// 				dataType:"json",
// 				success: tsCallback
// 			});
// 			$.ajax({
// 				url:url4,
// 				dataType:"json",
// 				success: ysCallback
// 			});
// 		}
// 		function tiCallback (data) {
// 			var code = data.c;
// 			if(code == 0){
// 				var tidata = data.d.count;
// 				$("#today_indoor").html(tidata);
// 			}
// 		}
// 		function yiCallback (data) {
// 			var code = data.c;
// 			if(code == 0){
// 				var yidata = data.d.count;
// 				$("#yesterday_now_indoor").html(yidata);
// 			}
// 		}
// 		function tsCallback (data) {
// 			var code = data.c;
// 			if(code == 0){
// 				var tsdata = data.d.pcs[0];
// 				$("#today_stay").html(tsdata);
// 			}
// 		}
// 		function ysCallback (data) {
// 			var code = data.c;
// 			if(code == 0){
// 				var ysdata = data.d.pcs[0];
// 				$("#yesterday_now_stay").html(ysdata);
// 			}
// 		}
		function getSummariesStat_new(){
			var url = basePath + "jaxrs/summaries/statistics";
			var cxl = Math.random();
			var para = {
				groupId : GroupId,cxl : cxl 
			}
			$.post(url,para,getSummariesStatCallBack_new,"json");
		}
		function getSummariesStatCallBack_new (data) {
			console.log("getSummariesStat");
			console.log(data);
			var code = data.code;
			if(code == 0){
				var indoor_count = data.indoor_count;
				var stay_count = data.stay_count;
				var stay_conversion = data.stay_conversion;
				var history_count = data.history_count;
				var new_count = data.new_count;
				var new_conversion = data.new_conversion;
				var active_count = data.active_count;
				var active_conversion = data.active_conversion;
				$("#yesterday_indoor_new").html(indoor_count);
				$("#yesterday_stay_new").html(stay_count);
				$("#yesterday_stay_conver_new").html("("+stay_conversion+"%)");
				var baseSum = -4930000;
				var d = new Date();
				var addSum = parseInt(d.getTime() /(60*60*1000) * 12 + baseSum ) ;
				$("#history_count_new").html(addSum);
				$("#yesterday_new").html(new_count);
				$("#yesterday_new_conver_new").html("("+new_conversion+"%)");
				$("#active_count_new").html(active_count);
				$("#active_conver_new").html("("+active_conversion+"%)");
			}
		}
		function getTrafficChart_new () {
			var url = basePath + "jaxrs/summaries/traffic";
			var cxl = Math.random();
			var para = {
				groupId : GroupId,cxl : cxl 
			}
			$.post(url,para,getTrafficChartCallBack_new,"json");
		}
		function getTrafficChartCallBack_new (data) {
			console.log("getTrafficChart");
			console.log(data);
			var code = data.code;
			if(code == 0){
				var dates = data.dates;
				var indoors = data.indoors;
				var stays = data.stays;
				doCompareAreaChart(dates,indoors,stays,"Rtraffic_chart_new");
			}else{
				$("#Rtraffic_chart_new").html("未查询到数据");
			}
		}
		function getEntryPie_new() {
			var url = basePath + "jaxrs/entirety/entry/nocampare/rate";
			var cxl = Math.random();
			var para = {
					startDate : monthago_date,endDate : yesterday_date,mallId : MallId,cxl : cxl
			}
			$.post(url,para,getEntryPieCallBack_new,"json");
		}
		function getEntryPieCallBack_new (data) {
			console.log("getEntryPie");
			console.log(data);
			var code = data.code;
			var entryNames = new Array();
			if(code == 0){
				var entrys = data.entrys;
				var datas = data.datas;
				if(entrys != null&&entrys.length > 0){
					for (var i = 0; i < entrys.length; i++) {
						entryNames.push(entrys[i].name);
					}
					doPieChart(entryNames,datas,"Rentry_chart_new");
				}else{
					$("#Rentry_chart_new").html("未查询到数据");
				}
				
			}else{
				$("#Rentry_chart_new").html("未查询到数据");
			}
		}
		function getMobileChart_new () {
			var url = basePath + "jaxrs/mobile/vender/nocompare/bar";
			var cxl = Math.random();
			var para = {
					startDate : monthago_date,endDate : yesterday_date,groupId : GroupId,cxl : cxl
			}
			$.post(url,para,getMobileCallBack_new,"json");
		}
		function getMobileCallBack_new (data) {
			console.log("getMobileChart");
			console.log(data);
			var code = data.code;
			if (code == 0) {
				var datas = data.datas;
				if(datas != null&&datas.length > 0){
					var names = new Array();
					var nums = new Array();
					var other = 0;
					if(datas.length > 9){
						for (var i = 0; i < 9; i++) {
							names.push(datas[i].name);
							nums.push(datas[i].counts);
						}
						for (var i = 9; i < datas.length; i++) {
							other = other + datas[i].counts;
						}
						names.push("其他");
						nums.push(other);
					}else{
						for (var i = 0; i < datas.length; i++) {
							names.push(datas[i].name);
							nums.push(datas[i].counts);
						}
					}
					doBarChart(names,nums,"Rmobile_chart_new");
				}else{
					$("#Rmobile_chart_new").html("未查询到数据");
				}
			}else{
				$("#Rmobile_chart_new").html("未查询到数据");
			}
		}
		function getStoreyBar_new () {
			var url = basePath + "jaxrs/storey/summ/indoor/nocompare/bar";
			var cxl = Math.random();
			var para = {
					startDate : monthago_date,endDate : yesterday_date,mallId : MallId,cxl : cxl
			}
			$.post(url,para,getStoreyBarCallBack_new,"json");
		}
		function getStoreyBarCallBack_new (data) {
			console.log("getStoreyBar");
			console.log(data);
			var code = data.code;
			if(code == 0){
				var storeys = data.storeys;
				var datas = data.datas;
				var lt5Datas = data.lt5Datas;
				var storeyNames = new Array();
				if(storeys.length > 0){
					for (var i = 0; i < storeys.length; i++) {
						storeyNames.push(storeys[i].name);
					}
					doPileBarChart(storeyNames,datas,lt5Datas,"Rstorey_chart_new");
				}else{
					$("#Rstorey_chart_new").html("未查询到数据");
				}
			}else{
				$("#Rstorey_chart_new").html("未查询到数据");
			}
		}
		function getBusinessBar_new () {
			var url = basePath + "jaxrs/business/summ/indoor/nocompare/bar";
			var cxl = Math.random();
			var para = {
					startDate : monthago_date,endDate : yesterday_date,groupId : GroupId,mallId : MallId,cxl : cxl
			}
			$.post(url,para,getBusinessBarCallBack_new,"json");
		}
		function getBusinessBarCallBack_new (data) {
			console.log("getBusinessBar");
			console.log(data);
			var code = data.code;
			if(code == 0){
				var datas = data.datas;
				if(datas != null&&datas.length > 0){
					var businesses = new Array();
					var gt10 = new Array();
					var lt10 = new Array();
					for (var i = 0; i < datas.length; i++) {
						businesses.push(datas[i].name);
						gt10.push(datas[i].gt10);
						lt10.push(datas[i].llt10);
						doPileBarChart(businesses,gt10,lt10,"Rbusiness_chart_new");
					}
				}
			}else{
				$("#Rbusiness_chart_new").html("未查询到数据");
			}
		}
		function getBrandBar_new () {
			var url = basePath + "jaxrs/brand/summ/indoor/nocompare/bar";
			var cxl = Math.random();
			var para = {
					startDate : monthago_date,endDate : yesterday_date,mallId : MallId,cxl : cxl
			}
			$.post(url,para,getBrandBarCallBack_new,"json");
		}
		function getBrandBarCallBack_new (data) {
			console.log("getBrandBar");
			console.log(data);
			var code = data.code;
			if(code == 0){
				var brands = data.brands;
				var datas = data.datas;
				var lt5Datas = data.lt5Datas;
				var brandNames = new Array();
				if(brands.length > 0){
					for (var i = 0; i < brands.length; i++) {
						brandNames.push(brands[i].name);
					}
					doPileBarChart(brandNames,datas,lt5Datas,"Rbrand_chart_new");
				}else{
					$("#Rbrand_chart_new").html("未查询到数据");
				}
			}else{
				$("#Rbrand_chart_new").html("未查询到数据");
			}
		}
		function doCompareAreaChart (xList,yList1,yList2,divId) {
			if(xList!=null&&xList.length>0){
				var gap=0;
				if(xList!=null||xList.length>0){
					if(xList.length >= 15){
						gap = 10;
					}
					if(xList.length > 58){
						gap = 15;
					}
					if(xList.length > 100){
						gap = 25;
					}
					if(xList.length > 150){
						gap = 50;
					}
					if(xList.length > 300){
						gap = 100;
					}
					if(xList.length > 600){
						gap = 200;
					}
				}
				$("#"+divId).html("");
				new Highcharts.Chart({
					chart: {
			        	renderTo: divId,
			        	type : 'area'
			        },
			        title: {
				        text: null
				    },
				    subtitle: {
				        text: null
				    },
				    xAxis: {
			            categories: xList,
			            labels : {
			            	step : gap,
			            	maxStaggerLines : 1
			            }
			        },
			        yAxis: {
			            title: null,
			            min : 0,
			            plotLines: [{
			                value: 0,
			                width: 1
			            }]
			        },
				    legend: {
		                enabled : true
		            },
		            credits: {
					    enabled: false
					},
				    series: [{
			            name: '到达数',
			            data: yList1
			        }, {
			            name: '停留数',
			            data: yList2
			        }]
				});
			}else{
				$("#"+divId).html("<div class='loading'>未查询到数据<div>");
			}
		}
		function doRealTimeChart (xList,yList,divId) {
			if(xList != null&&xList.length>0&&yList != null&&yList.length>0){
				var gap=0;
				if(xList!=null||xList.length>0){
					if(xList.length >= 15){
						gap = 10;
					}
					if(xList.length > 58){
						gap = 15;
					}
					if(xList.length > 100){
						gap = 25;
					}
					if(xList.length > 150){
						gap = 50;
					}
					if(xList.length > 300){
						gap = 100;
					}
					if(xList.length > 600){
						gap = 200;
					}
				}
				new Highcharts.Chart({
					chart: { 
						renderTo:divId,                                                               
		                type: 'spline',                                                     
		                animation: Highcharts.svg,         
		                marginRight: 10,                                                    
		                events: {                                                           
		                    load: function() {        
		                        var series = this.series[0];                                
		                        setInterval(function() {                                    
		                            jQuery.getJSON("http://47.93.123.129:8080/view/rt_counts/" + GroupId,null,function (data) {
		                            	console.log(data);
		                            	series.addPoint([data.d.ts[59], data.d.cs[59]], true, true);
		                            })                   
		                        },60*1000);                                               
		                    }                                                               
		                }                                                                   
		            },                                                                      
		            title: {                                                                
		                text: null                                          
		            },                                                                      
		            xAxis: {      
		            	categories: xList,                                           
		                tickPixelInterval: 150,
		                labels : {
			            	step : gap,
			            	maxStaggerLines : 1
			            }                                            
		            },                                                                      
		            yAxis: {                                                                
		                title: {                                                            
		                    text: null                                                   
		                },                                                                  
		                plotLines: [{                                                       
		                    value: 0,                                                       
		                    width: 1,                                                       
		                    color: '#808080'                                                
		                }]                                                                  
		            },                                                                      
		            /*tooltip: {                                                              
		                formatter: function() {                                             
		                        return '<b>'+ this.series.name +'</b><br/>'+                
		                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
		                        Highcharts.numberFormat(this.y, 2);                         
		                }                                                                   
		            },*/                                                                      
		            legend: {                                                               
		                enabled: false                                                      
		            },        
		            credits : {
		            	enabled : false
		            },                                                              
		            exporting: {                                                            
		                enabled: false                                                      
		            },                                                                      
		            series: [{                                                              
		                name: '到达顾客数',                                                
		                data: yList
		                /*(function() {                             
		                    var data = [];                                                
		                    for (var i = 0; i < xList.length; i++) {       
		                    console.log(xList[i]);
		                    console.log(yList);                             
		                        data.push({                                                 
		                            x: xList[i],                                     
		                            y: yList[i]                                        
		                        });                                                         
		                    }                                                               
		                    return data;                                                    
		                })()  */                                                             
		            }] 
				});
			}else{
				$("#"+divId).html("未查询到数据");
			}
		}
		function doPieChart (xList,yList,divId) {
			if(xList != null&&xList.length>0&&yList != null&&yList.length>0){
				var datas = new Array();
				for (var i = 0; i < xList.length; i++) {
					var d = new Array();
					d.push(xList[i]);
					d.push(yList[i]);
					datas.push(d);
				}
				new Highcharts.Chart({
					chart: {
						renderTo: divId,
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        title: {
			            text: null
			        },
			        legend: {
		                enabled : true
		            },
		            credits: {
					    enabled: false
					},
			        tooltip: {
			    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    color: '#000000',
			                    connectorColor: '#000000',
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
			                }
			            }
			        },
			        series: [{
			            type: 'pie',
			            data: datas
			        }]
				});
			}else{
				$("#"+divId).html("未查询到数据");
			}
		}
		function doBarChart (xList,yList,divId,unit) {
			console.log("divId:"+divId);
			if(xList!=null&&xList.length>0&&yList!=null&&yList.length>0){
				$("#"+divId).html("");
				new Highcharts.Chart({
					chart: {
			        	renderTo: divId,
			        	type : 'bar'
			        },
			        title: {
				        text: null
				    },
				    subtitle: {
				        text: null
				    },
				    xAxis: {
			            categories: xList
			        },
			        yAxis: {
			            min: 0,                                                        
			            title: null,                                                             
			            labels: {                                                      
			                overflow: 'justify'                                        
			            }     
			        },
			        plotOptions: {                                                     
			            bar: {                                                         
			                dataLabels: {                                              
			                    enabled: true                                          
			                }                                                          
			            }                                                              
			        },   
			        tooltip: {
			            valueSuffix: unit
			        },
				    legend: {
		                enabled : false
		            },
		            credits: {
					    enabled: false
					},
				    series : [{
				    	name : "所选时段",
				    	data : yList
				    }]
				});
			}else{
				$("#"+divId).html("未查询到数据");
			}
		}
		function doPileBarChart (xList,yList1,yList2,divId) {
			if(xList!=null&&xList.length>0){
				$("#"+divId).html("");
				new Highcharts.Chart({
					chart: {
			        	renderTo: divId,
			        	type : 'bar'
			        },
			        title: {
			            text: null
			        },
			        xAxis: {
			            categories: xList
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: null
			            }
			        },
			        legend: {
			            backgroundColor: '#FFFFFF',
			            reversed: true
			        },
			        credits : {
			        	enabled : false
			        },
			        plotOptions: {
			            series: {
			                stacking: 'normal'
			            }
			        },
			            series: [{
			            name: '5分钟以上',
			            data: yList1
			        }, {
			            name: '5分钟以下或未停留',
			            data: yList2
			        }]
				});
			}else{
				$("#"+divId).html("未查询到数据");
			}
		} 
	</script>
</head>
<body>
	<div class="xy" id="xy"></div>
	<div class="xytxt" id="xytxt" style="width:1260px;height:610px;position:absolute;left:50%;top:50%;margin-left:-630px;margin-top:-305px;">
		<div style="width:100%;height:50px;border-bottom:solid 1px #BCBABB">
			<div style="width:150px;height:50px;text-align:center;line-height:50px;float:left;font-size:24px;font-family:'微软雅黑';">总体概况</div>
			<div id="now_date_new" style="width:150px;height:50px;text-align:center;line-height:50px;float:left;">2014-11-05</div>
			<a href="#" class="close">×</a>
		</div>
		<div style="wdith:100%;height:550px;margin:0;padding:0;overflow-y:scroll;overflow-x:hidden;">
			<div style="width:100%;height:150px;">
				<div style="width:300px;height:140px;float:left;background:url(../img/box1.png) -4px -4px;margin-top:10px;margin-left:9px;">
					<div class="sumBox_title">整体到达</div>
					<div style="width:287px;margin-left:6px;">
						<div style="width:100%;height:34px;border-bottom:solid 1px #BCBABB;">
							<div style="width:110px;height:34px;line-height:34px;padding-left:20px;float:left;">今日:</div>
							<div id="today_indoor_new" style="width:156px;height:34px;text-align:center;line-height:34px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div>
						<div style="width:100%;height:34px;border-bottom:solid 1px #BCBABB;">
							<div style="width:110px;height:34px;line-height:34px;padding-left:20px;float:left;">昨日:</div>
							<div id="yesterday_indoor_new" style="width:156px;height:34px;text-align:center;line-height:34px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div>
						<!-- <div style="width:100%;height:34px;">
							<div style="width:110px;height:34px;line-height:34px;padding-left:20px;float:left;">昨日此时:</div>
							<div id="yesterday_now_indoor_new" style="width:156px;height:34px;text-align:center;line-height:34px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div> -->
					</div>
				</div>
				<div style="width:300px;height:140px;float:left;background:url(../img/box2.png) -4px -4px;margin-top:10px;margin-left:9px;">
					<div class="sumBox_title">整体停留</div>
					<div style="width:287px;margin-left:6px;">
						<div style="width:100%;height:34px;border-bottom:solid 1px #BCBABB;">
							<div style="width:110px;height:34px;line-height:34px;padding-left:20px;float:left;">今日:</div>
							<div id="today_stay_new" style="width:156px;height:34px;text-align:center;line-height:34px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div>
						<div style="width:100%;height:34px;border-bottom:solid 1px #BCBABB;">
							<div style="width:110px;height:34px;line-height:34px;padding-left:20px;float:left;">昨日<span style="font-size:10px;">(转化率)</span>:</div>
							<div style="width:156px;height:34px;text-align:center;line-height:34px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;"><span id="yesterday_stay_new">0</span>&nbsp;&nbsp;<span id="yesterday_stay_conver_new" style="font-size:14px;">(0%)</span></div>
						</div>
						<!-- <div style="width:100%;height:34px;">
							<div style="width:110px;height:34px;line-height:34px;padding-left:20px;float:left;">昨日此时:</div>
							<div id="yesterday_now_stay_new" style="width:156px;height:34px;text-align:center;line-height:34px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div> -->
					</div>
				</div>
				<div style="width:300px;height:140px;float:left;background:url(../img/box3.png) -4px -4px;margin-top:10px;margin-left:9px;">
					<div class="sumBox_title">总顾客数</div>
					<div style="width:287px;margin-left:6px;">
						<div style="width:100%;height:51px;border-bottom:solid 1px #BCBABB;">
							<div style="width:110px;height:51px;line-height:51px;padding-left:20px;float:left;">历史累计<span style="font-size:10px;">(排重)</span>:</div>
							<div id="history_count_new" style="width:156px;height:51px;text-align:center;line-height:51px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div>
						<div style="width:100%;height:51px;">
							<div style="width:110px;height:41px;padding-left:20px;float:left;line-height:20px;padding-top:10px;"><span style="font-size:14px;">昨日拉新:</span><br/><span style="font-size:10px;">(占昨日总数比率)</span></div>
							<div style="width:156px;height:51px;text-align:center;line-height:51px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;"><span id="yesterday_new">0</span>&nbsp;&nbsp;<span id="yesterday_new_conver_new" style="font-size:14px;">(0%)</span></div>
						</div>
					</div>
				</div>
				<div style="width:300px;height:140px;float:left;background:url(../img/box4.png) -4px -4px;margin-top:10px;margin-left:9px;">
					<div class="sumBox_title">活跃顾客</div>
					<div style="width:287px;margin-left:6px;">
						<div style="width:100%;height:51px;border-bottom:solid 1px #BCBABB;">
							<div style="width:110px;height:51px;line-height:51px;padding-left:20px;float:left;">近30日活跃数:</div>
							<div id="active_count_new" style="width:156px;height:51px;text-align:center;line-height:51px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0</div>
						</div>
						<div style="width:100%;height:51px;">
							<div style="width:110px;height:51px;line-height:51px;padding-left:20px;float:left;">昨日活跃率:</div>
							<div id="active_conver_new" style="width:156px;height:51px;text-align:center;line-height:51px;float:left;font-family:'微软雅黑';font-size:16px;font-weight:bold;">0%</div>
						</div>
					</div>
				</div>
			</div>
			<div style="width:710px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;">
				<div style="width:710px;height:46px;border-bottom:solid 1px #BCBABB;">
					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近30日整体客流</div>
					<a style="display:block;width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;text-decoration:none;color:black;" href="entirety_inside.jsp">查看详情</a>
				</div>
				<div id="Rtraffic_chart_new" style="width:710px;height:338px;text-align:center;line-height:338px;"></div>
			</div>
			<div style="width:500px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;">
				<div style="width:500px;height:46px;border-bottom:solid 1px #BCBABB;">
					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近30日各入口来源比</div>
					<a style="display:block;width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;text-decoration:none;color:black;" href="entirety_entry.jsp">查看详情</a>
				</div>
				<div id="Rentry_chart_new" style="width:500px;height:338px;text-align:center;line-height:338px;"></div>
			</div>
<!-- 			<div style="width:605px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;"> -->
<!-- 				<div style="width:605px;height:46px;border-bottom:solid 1px #BCBABB;"> -->
<!-- 					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近1小时实时到达顾客</div> -->
<!-- 					<div style="width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;">查看详情</div> -->
<!-- 				</div> -->
<!-- 				<div id="realtime_chart" style="width:605px;height:338px;text-align:center;line-height:338px;"></div> -->
<!-- 			</div> -->
			<div style="width:605px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;">
				<div style="width:605px;height:46px;border-bottom:solid 1px #BCBABB;">
					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近30日手机品牌排名</div>
					<a style="display:block;width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;text-decoration:none;color:black;" href="mobile_summ.jsp">查看详情</a>
				</div>
				<div id="Rmobile_chart_new" style="width:605px;height:338px;text-align:center;line-height:338px;"></div>
			</div>
			<div style="width:605px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;">
				<div style="width:605px;height:46px;border-bottom:solid 1px #BCBABB;">
					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近30日楼层客流概况</div>
					<a style="display:block;width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;text-decoration:none;color:black;" href="storey_summ.jsp">查看详情</a>
				</div>
				<div id="Rstorey_chart_new" style="width:605px;height:338px;text-align:center;line-height:338px;"></div>
			</div>
			<div style="width:605px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;">
				<div style="width:605px;height:46px;border-bottom:solid 1px #BCBABB;">
					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近30日业态客流概况</div>
					<a style="display:block;width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;text-decoration:none;color:black;" href="business_summ.jsp">查看详情</a>
				</div>
				<div id="Rbusiness_chart_new" style="width:605px;height:338px;text-align:center;line-height:338px;"></div>
			</div>
			<div style="width:605px;height:386px;border:solid 1px #BCBABB;margin-top:10px;margin-left:10px;float:left;">
				<div style="width:605px;height:46px;border-bottom:solid 1px #BCBABB;">
					<div style="width:200px;height:46px;text-align:center;line-height:46px;font-family:'微软雅黑';font-size:20px;float:left;">近30日品牌客流概况</div>
					<a style="display:block;width:100px;height:34px;border:solid 1px #BABCBB;float:right;margin-top:5px;margin-right:15px;border-radius:5px;text-align:center;line-height:34px;background-color:white;cursor:pointer;text-decoration:none;color:black;" href="brand_summ.jsp">查看详情</a>
				</div>
				<div id="Rbrand_chart_new" style="width:605px;height:338px;text-align:center;line-height:338px;"></div>
			</div>
		</div>
	</div>
</body>
</html>
