<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%
	response.setHeader("Pragma","No-cache");
 	response.setHeader("Cache-Control","no-cache");
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int login_id = 0;
	int login_level = 0;
	int mall_id = 0;
	int group_id = 0;
	String mall_name = "";
	String login_telephone = "";
	Cookie[] cookies = request.getCookies();
	
	if(request.getSession().getAttribute("login_id")==null){
		for(int i = 0;i<cookies.length;i++){
			if ("loginid".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0) {
				login_id = Integer.parseInt(cookies[i].getValue());
			} else if ("loginlevel".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0) {
				login_level = Integer.parseInt(cookies[i].getValue());
			} else if ("mallname".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0){
				mall_name = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
			} else if ("mallid".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0){
				mall_id = Integer.parseInt(cookies[i].getValue());
			} else if ("groupid".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0){
				group_id = Integer.parseInt(cookies[i].getValue());
			} else if ("telephone".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0){
				login_telephone = cookies[i].getValue();
			}
		}
		if(login_id == 0){
			response.sendRedirect("../jsp/login.jsp");
			return;
		}
	}else{
		login_id = (Integer)request.getSession().getAttribute("login_id");
		group_id = (Integer)request.getSession().getAttribute("group_id");
		mall_id = (Integer)request.getSession().getAttribute("mall_id");
		login_level = (Integer)request.getSession().getAttribute("login_level");
		mall_name = (String)request.getSession().getAttribute("mall_name");
		login_telephone = (String)request.getSession().getAttribute("telephone");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title> 探知-基于室内定位的线下顾客行为分析系统 </title>
<link type="text/css" rel="stylesheet" href="../css/ui.css" />
<link type="text/css" rel="stylesheet" href="../css/entirety.css" />
<script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="../js/highcharts.js"></script>
<script type="text/javascript" src="../js/highcharts.src.js"></script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var MallId = "<%=mall_id%>";
	var GroupId = "<%=group_id%>";
	var mall_name = "<%=mall_name%>";
	var Telephone = "<%=login_telephone%>";
	var LoginId = "<%=login_id%>";
	var LoginLevel = "<%=login_level%>";
	//var GroupId = "3";
	var IsCompare = 0;
	var StartDate;
	var EndDate;
	var CompareDate;
	var TimeTab = 0;//时间标签选择变量，0:今日,1:昨日,2:历史时段
	var RtBarDatas = new Array();//bar图实时数据
	var CRtBardatas = new Array();//对比时段bar图实时数据
	var RtLineDatas = new Array();//line图实时数据
	var CRtLinedatas = new Array();//对比时段line图实时数据
	var BarXDatas = ["小于60分钟","60-120分钟","120-180分钟","大于180分钟"];
	$(document).ready(function () {
		$("#mall_name").html(mall_name);
		if(LoginLevel != 0){
			$("#edit_li").hide();
			$("#seticon").hide();
			$("#usericon").css("border-left","solid 1px #BCBABB");
			if(LoginId == 4){
				$("#mall_name").html("功能演示");
				$("#my_account").hide();
			}
		}
		document.getElementById("start").innerHTML=getToday();
		document.getElementById("middle").innerHTML="~";
		document.getElementById("end").innerHTML=getToday();
		$("#content").css("height",document.body.scrollHeight);
		$("#ul_n").css("height",$("#content").css("height"));
		changeToolBarTab(0,$("#a_today"));
	});
	function execute () {
		if(document.getElementById("residence_cpie")){
			$("#residence_cpie").remove();
			$("#residence_pie").css("height","280px");
		}
		$("#residence_bar").html("<img src='../img/big_load.gif' />");
		$("#residence_pie").html("<img src='../img/big_load.gif' />");
		$("#residence_chart").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
// 		if(TimeTab == 0){
// 			loadRealtimeResidenceBar();
// 			loadRealtimeResidenceChart();
// 		}else{
			loadResidenceBar();
			loadResidenceChart();
// 		}
	}
	function compare_execute () {
		if(!document.getElementById("residence_cpie")){
			$("#residence_pie").css("height","140px");
			$("#entry_bar").append("<div id='residence_cpie' style='width:50%;height:140px;text-align:center;line-height:140px'></div>");
		}
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
		$("#residence_bar").html("<img src='../img/big_load.gif' />");
		$("#residence_pie").html("<img src='../img/big_load.gif' />");
		$("#residence_cpie").html("<img src='../img/big_load.gif' />");
		$("#residence_chart").html("<img src='../img/big_load.gif' />");
// 		if (TimeTab == 0) {
// 			loadCompareRealtimeResidenceBar();
// 			loadCompareRealtimeResidenceChart();
// 		}else{
			loadCompareResidenceBar();
			loadCompareResidenceChart();
// 		}
	}
	function loadRealtimeResidenceBar () {
		var url = "http://47.93.123.129:8080/timeDistribution/entireStay/"+GroupId+"/"+StartDate;
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeBarCallback
		});
	}
	function realtimeBarCallback (data) {
		console.log("loadRealtimeResidenceBar:"+data);
		var code = data.c;
		if(code == 0){
			var tdbs = data.d.timeDistributions;
			RtBarDatas = new Array();
			for (var i = 0; i < tdbs.length; i++) {
				RtBarDatas.push(tdbs[i][0]);
			}
			doBarChart(BarXDatas,RtBarDatas,"residence_bar","");
			doPieChart(BarXDatas,RtBarDatas,"residence_pie");
		}
	}
	function loadRealtimeResidenceChart () {
		var url = "http://47.93.123.129:8080/timeDistribution/entireAVG/"+GroupId+"/"+StartDate;
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeChartCallback
		});
	}
	function realtimeChartCallback (data) {
		var code = data.c;
		if(code == 0){
			var dates = new Array();
			dates.push(StartDate);
			RtLineDatas = data.d.contents;
			for (var i = 0; i < RtLineDatas.length; i++) {
				RtLineDatas[i] = parseFloat((RtLineDatas[i]/60).toFixed(1));
			}
			doColumnChart(dates,RtLineDatas,"residence_chart","分钟");
		}
	}
	function loadCompareRealtimeResidenceBar () {
		var url = "http://47.93.123.129:8080/timeDistribution/entireStay/"+GroupId+"/"+CompareDate;
		$.ajax({
			url:url,
			dataType:"json",
			success: compareRealtimeBarCallback
		});
	}
	function compareRealtimeBarCallback (data) {
		console.log("loadCompareRealtimeResidenceBar:"+data);
		var code = data.c;
		if(code == 0){
			var tdbs = data.d.timeDistributions;
			CRtBarDatas = new Array();
			for (var i = 0; i < tdbs.length; i++) {
				CRtBarDatas.push(tdbs[i][0]);
			}
			doCompareBarChart(BarXDatas,RtBarDatas,CRtBardatas,"residence_bar","");
			doPieChart(BarXDatas,RtBarDatas,"residence_pie");
			doPieChart(BarXDatas,CRtBardatas,"residence_cpie");
		}
	}
	function loadCompareRealtimeResidenceChart () {
		var url = "http://47.93.123.129:8080/timeDistribution/entireAVG/"+GroupId+"/"+CompareDate;
		$.ajax({
			url:url,
			dataType:"json",
			success: compareRealtimeChartCallback
		});
	}
	function compareRealtimeChartCallback (data) {
		var code = data.c;
		if(code == 0){
			var dates = new Array();
			dates.push(StartDate);
			CRtLinedatas = data.d.contents;
			for (var i = 0; i < CRtLinedatas.length; i++) {
				CRtLinedatas[i] = parseFloat((CRtLinedatas[i]/60).toFixed(1));
			}
			doCompareColumnChart(dates,RtLineDatas,CRtLinedatas,"residence_chart","分钟");
		}
	}
	function loadResidenceBar () {
		var url = basePath + "jaxrs/entirety/residence/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadResidenceBarCallBack,"json");
	}
	function loadResidenceBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var yrArea;
			var less_60 = data.less_60;
			var range_60_120 = data.range_60_120;
			var range_120_180 = data.range_120_180;
			var more_180 = data.more_180;
			var counts = less_60 + range_60_120 + range_120_180 + more_180;
			var xArea = new Array("小于60分钟","60-120分钟","120-180分钟","大于180分钟");
			var yArea = new Array(less_60,range_60_120,range_120_180,more_180);
			if(counts != 0){
				yrArea = new Array(less_60*100/counts,range_60_120*100/counts,range_120_180*100/counts,more_180*100/counts);
			}else{
				yrArea = new Array(0,0,0,0);
			}
			doBarChart(xArea,yArea,"residence_bar","");
			doPieChart(xArea,yrArea,"residence_pie");
		}else{
			$("#residence_bar").html("未查询到数据");
			$("#residence_pie").html("未查询到数据");
		}
	}
	function loadResidenceChart () {
		var url = basePath + "jaxrs/entirety/residence/nocompare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadResidenceChartCallBack,"json");
	}
	function loadResidenceChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			for (var i = 0; i < datas.length; i++) {
				datas[i] = parseFloat((datas[i]/60).toFixed(1));
			}
			if(dates.length > 1){
				doLineChart(dates,datas,"residence_chart","分钟");
			}else{
				doColumnChart(dates,datas,"residence_chart","分钟");
			}
		}else{
			$("#residence_chart").html("未查询到数据");
		}
	}
	function loadCompareResidenceBar () {
		var url = basePath + "jaxrs/entirety/residence/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadCompareResidenceBarCallBack,"json");
	}
	function loadCompareResidenceBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var yrArea,com_yrArea;
			var less_60 = data.less_60;
			var range_60_120 = data.range_60_120;
			var range_120_180 = data.range_120_180;
			var more_180 = data.more_180;
			var less_60_com = data.less_60_com;
			var range_60_120_com = data.range_60_120_com;
			var range_120_180_com = data.range_120_180_com;
			var more_180_com = data.more_180_com;
			var counts = less_60 + range_60_120 + range_120_180 + more_180;
			var com_counts = less_60_com + range_60_120_com + range_120_180_com + more_180_com;
			var xArea = new Array("小于60分钟","60-120分钟","120-180分钟","大于180分钟");
			var yArea = new Array(less_60,range_60_120,range_120_180,more_180);
			var com_yArea = new Array(less_60_com,range_60_120_com,range_120_180_com,more_180_com);
			if(counts != 0){
				yrArea = new Array(less_60*100/counts,range_60_120*100/counts,range_120_180*100/counts,more_180*100/counts);
			}else{
				yrArea = new Array(0,0,0,0);
			}
			if(com_counts != 0){
				com_yrArea = new Array(less_60_com*100/com_counts,range_60_120_com*100/com_counts,range_120_180_com*100/com_counts,more_180_com*100/com_counts);
			}else{
				com_yrArea = new Array(0,0,0,0);
			}
			doCompareBarChart(xArea,yArea,com_yArea,"residence_bar","");
			doPieChart(xArea,yrArea,"residence_pie");
			doPieChart(xArea,com_yrArea,"residence_cpie");
		}else{
			$("#residence_bar").html("未查询到数据");
			$("#residence_pie").html("未查询到数据");
			$("#residence_cpie").html("未查询到数据");
		}
	}
	function loadCompareResidenceChart () {
		var url = basePath + "jaxrs/entirety/residence/compare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadCompareResidenceChartCallBack,"json");
	}
	function loadCompareResidenceChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			for (var i = 0; i < datas.length; i++) {
				datas[i] = parseFloat((datas[i]/60).toFixed(1));
			}
			for (var i = 0; i < compareDatas.length; i++) {
				compareDatas[i] = parseFloat((compareDatas[i]/60).toFixed(1));
			}
			if(dates.length > 1){
				doCompareLineChart(dates,datas,compareDatas,"residence_chart","分钟");
			}else{
				doCompareColumnChart(dates,datas,compareDatas,"residence_chart","分钟");
			}
		}else{
			$("#residence_chart").html("未查询到数据");
		}
	}
	/*function doBarChart (xList,yList,divId) {
		alert(456);
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
	}*/
	function doCompareBarChart (xList,yList1,yList2,divId,unit) {
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
		        tooltip: {
		            valueSuffix: unit
		        },
			    legend: {
	                enabled : true
	            },
	            credits: {
				    enabled: false
				},
			    series: [{
			    	name: '对比时段',
		            data: yList2,
		            color :"#0d233a"
		        }, {
		            name: '所选时段',
		            data: yList1,
		            color : "#2f7ed8"
		        }]
			});
		}else{
			$("#"+divId).html("未查询到数据");
		}
	}
	function doColumnChart (xList,yList,divId,unit) {
		if(xList!=null&&xList.length>0){
			var gap=0;
			if(xList!=null||xList.length>0){
				if(xList.length >= 15){
					gap = 3;
				}
				if(xList.length > 58){
					gap = 10;
				}
				if(xList.length > 100){
					gap = 20;
				}
				if(xList.length > 150){
					gap = 30;
				}
				if(xList.length > 300){
					gap = 50;
				}
				if(xList.length > 600){
					gap = 100;
				}
			}
			$("#"+divId).html("");
			new Highcharts.Chart({
				chart: {
		        	renderTo: divId,
		        	type : 'column'
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
		            min: 0,                                                        
		            title: null,                                                             
		            labels: {                                                      
		                overflow: 'justify'                                        
		            }     
		        },
			    legend: {
	                enabled : false
	            },
	            tooltip: {
		            valueSuffix: unit
		        },
	            credits: {
				    enabled: false
				},
				plotOptions: {
		            column: {
		                pointWidth : 20
		            }
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
	function doCompareColumnChart (xList,yList1,yList2,divId,unit) {
		if(xList!=null&&xList.length>0){
			var gap=0;
			if(xList!=null||xList.length>0){
				if(xList.length >= 15){
					gap = 3;
				}
				if(xList.length > 58){
					gap = 10;
				}
				if(xList.length > 100){
					gap = 20;
				}
				if(xList.length > 150){
					gap = 30;
				}
				if(xList.length > 300){
					gap = 50;
				}
				if(xList.length > 600){
					gap = 100;
				}
			}
			$("#"+divId).html("");
			new Highcharts.Chart({
				chart: {
		        	renderTo: divId,
		        	type : 'column'
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
		            min: 0,                                                        
		            title: null,                                                             
		            labels: {                                                      
		                overflow: 'justify'                                        
		            }     
		        },
		        tooltip: {
		            valueSuffix: unit
		        },
			    legend: {
	                enabled : true
	            },
	            credits: {
				    enabled: false
				},
				plotOptions: {
		            column: {
		                pointWidth : 20
		            }
		        },
			    series : [{
			    	name : "所选时段",
			    	data : yList1
			    },{
			    	name : "对比时段",
			    	data : yList2
			    }]
			});
		}else{
			$("#"+divId).html("未查询到数据");
		}
	}
	function doLineChart (xList,yList,divId,unit) {
		if(xList!=null&&xList.length>0){
			var gap=0;
			if(xList!=null||xList.length>0){
				if(xList.length >= 15){
					gap = 3;
				}
				if(xList.length > 58){
					gap = 10;
				}
				if(xList.length > 100){
					gap = 20;
				}
				if(xList.length > 150){
					gap = 30;
				}
				if(xList.length > 300){
					gap = 50;
				}
				if(xList.length > 600){
					gap = 100;
				}
			}
			$("#"+divId).html("");
			new Highcharts.Chart({
				chart: {
		        	renderTo: divId,
		        	type : 'line'
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
			$("#"+divId).html("<div class='loading'>未查询到数据<div>");
		}
	}
	/*function doPieChart (xList,yList,divId) {
		alert(123);
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
	                enabled : false
	            },
	            credits: {
				    enabled: false
				},
		        tooltip: {
		    	    pointFormat: '{point.percentage:.1f}'
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
	}*/
	function doCompareLineChart (xList,yList1,yList2,divId,unit) {
		if(xList!=null&&xList.length>0){
			var gap=0;
			if(xList!=null||xList.length>0){
				if(xList.length >= 15){
					gap = 3;
				}
				if(xList.length > 58){
					gap = 10;
				}
				if(xList.length > 100){
					gap = 20;
				}
				if(xList.length > 150){
					gap = 30;
				}
				if(xList.length > 300){
					gap = 50;
				}
				if(xList.length > 600){
					gap = 100;
				}
			}
			$("#"+divId).html("");
			new Highcharts.Chart({
				chart: {
		        	renderTo: divId,
		        	type : 'line'
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
		        tooltip: {
		            valueSuffix: unit
		        },
			    legend: {
	                enabled : true
	            },
	            credits: {
				    enabled: false
				},
			    series: [{
			    	name: '所选时段',
		            data: yList1
		        }, {
		            name: '对比时段',
		            data: yList2
		        }]
			});
		}else{
			$("#"+divId).html("<div class='loading'>未查询到数据<div>");
		}
	}


	function changeToolBarTab (num,obj) {
		TimeTab = num;
		$(obj).addClass("select");
		$(obj).parent().siblings("li").find("a").removeClass("select");
		if(num == 0){
			$("#start").css("color","#CCCCCC");
			$("#middle").css("color","#CCCCCC");
			$("#end").css("color","#CCCCCC");
			$("#time_a").removeAttr("onclick");
			$("#time_a").removeAttr("onclick");
			searchTimeFlag(0);
			//execute();
		}else if(num == 1){
			$("#start").css("color","#CCCCCC");
			$("#middle").css("color","#CCCCCC");
			$("#end").css("color","#CCCCCC");
			$("#time_a").removeAttr("onclick");
			$("#time_a").removeAttr("onclick");
			searchTimeFlag(1);
			//execute();
		}else if(num == 2){
			$("#start").css("color","black");
			$("#middle").css("color","black");
			$("#end").css("color","black");
			$("#time_a").removeAttr("onclick");
			document.getElementById("time_a").setAttribute("onclick","timet('time','timecolse','Custom',$('#time_a'));closeWin();");
		}
	}
	function logout () {
		if(confirm("确定注销并退出?")){
			var url = basePath + "jaxrs/account/logout";
			var cxl = Math.random();
			var para = {
					cxl : cxl
			}
			$.post(url,para,logoutCallBack,"json");
		}
	}
	function logoutCallBack (data) {
		window.location.href = "login.jsp";
	}
</script>
</head>
<body>
<jsp:include page="summaries.jsp" />
<jsp:include page="aledit.jsp" />
<div class="container">
	<div class="banner">
		<div class="logo"></div>
		<div id="mall_name" class="mall_name" style="font-size:22px;"></div>
		<a id="usericon" class="usericon" href="javascript:void(0)" onclick="showManageDiv()"><div style="width:20px;height:20px;position:relative;left:50%;top:50%;margin-left:-10px;margin-top:-10px;background:url(../img/jingling1.png) -92px -140px;"></div></a>
		<a id="seticon" class="seticon" href="mallinfo.jsp"><div style="width:20px;height:20px;position:relative;left:50%;top:50%;margin-left:-10px;margin-top:-10px;background:url(../img/jingling1.png) -141px -140px;"></div></a>
		<div class="manageDiv">
			<a id="my_account" href="javascript:void(0)" onclick="showOut('aleditcont_1','aleditbg_1','close')">我的账号</a>
			<a href="javascript:void(0)" onclick="logout()">注销</a>
		</div>
	</div>
	<div id="content" class="content">
		<ul id="ul_n" class="navigation">
			<h2 onclick="showOut('xytxt','xy','close')"></h2>
			<li><a class="select" href="#">整体客流</a></li>
			<li><a href="storey_summ.jsp">楼层客流</a></li>
			<li><a href="business_summ.jsp">业态客流</a></li>
			<li><a href="brand_summ.jsp">品牌店铺</a></li>
			<li><a href="custom_summ.jsp">顾客模型</a></li>
			<!-- <li><a href="character_date.jsp">特征日模型</a></li> -->
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a href="entirety_inside.jsp">整体到达顾客</a></li>
					<li><a href="entirety_entry.jsp">各入口比例</a></li>
					<li><a href="entirety_stay.jsp">整体停留顾客</a></li>
					<li><a class="select" href="#">整体停留时长</a></li>
				</ul>
			</div>
			<div id="top_tool_bar">
				<ul class="tool_bar_ul">
					<li><a id="a_today" class="select" href="#" onclick="changeToolBarTab(0,this)">今日</a></li>
					<li><a href="#" onclick="changeToolBarTab(1,this)">昨日</a></li>
					<li><a href="#" onclick="changeToolBarTab(2,this)">选择时段</a></li>
				</ul>
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">停留时长分布</div>
				</div>
				<div class="dataDiv_content" id="entry_bar">
					<div id="residence_bar"></div>
					<div id="residence_pie"></div>
				</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">平均停留时长</div>
				</div>
				<div class="dataDiv_content" id="residence_chart"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>