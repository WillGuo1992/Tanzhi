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
	var IsCompare = 0;
	var StartDate;
	var EndDate;
	var CompareDate;
	var RateTab = 0;
	var ChosenGroupId;
	var TimeTab = 0;//时间标签选择变量，0:今日,1:昨日,2:历史时段
	var Times = ["00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"];//小时时段
	var RtDatas = new Array();//实时数据
	var CRtdatas = new Array();//对比时段实时数据
	var TodayRTSum = 0;//今日此刻到达
	var YesRTSum = 0;//昨日此刻到达
	var WeekRTSum = 0;//一周前此刻到达
	var MonthRTSum = 0;//一月前此刻到达
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
		$("#start").css("color","black");
		$("#middle").css("color","black");
		$("#end").css("color","black");
		getBrandList();
	});
	function getBrandList () {
		var url = basePath + "jaxrs/brand/list";
		var cxl = Math.random();
		var para = {
				mallId : MallId,cxl : cxl
		}
		$.post(url,para,getBrandListCallBack,"json");
	}
	function getBrandListCallBack (data) {
		var code = data.code;
		if(code == 0){
			var brands = data.brands;
			if(brands.length > 0){
				ChosenGroupId = brands[0].groupId;
				$("#storeys_list").html("");
				for (var i = 0; i < brands.length; i++) {
					$("#storeys_list").append("<div id='a_"+brands[i].groupId+"' class='tabs_' onclick='chooseBrandTab("+brands[i].groupId+",this)'>"+brands[i].name+"</div>");
				}
				chooseBrandTab(brands[0].groupId,$("#a_"+brands[0].groupId));
			}
		}
	}
	function execute () {
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
// 		if(TimeTab == 0){
// 			$("#en_title1").html("今日累计");
// 			$("#en_foot1").html("截止到此刻");
// 			$("#en_title2").html("对比昨日此刻");
// 			$("#en_title3").html("对比上周同日此刻");
// 			$("#en_title4").html("对比上月同日此刻");
// 			loadRealTimeSum();
// 		}else{
			$("#en_title1").html("该时段累计");
			$("#en_foot1").html("");
			$("#en_title2").html("对比前一日同期");
			$("#en_title3").html("对比前一周同期");
			$("#en_title4").html("对比前一月同期");
			loadBrandStatistics();
// 		}
// 		if(TimeTab == 0){
// 			loadRealtimeChart();
// 		}else{
			loadBrandChart();
// 		}
	}
	function compare_execute () {
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
// 		if(TimeTab == 0){
// 			loadRealtimeCompareChart();
// 		}else{
			loadBrandCompareChart();
// 		}
	}
	function loadRealTimeSum(){
		var thisday = new Date();
		var thishour = thisday.getHours();
		var url1 = "http://47.93.123.129:8080/view/stays/"+ChosenGroupId+"/"+StartDate+" 0"+"/"+StartDate+" "+thishour;
		console.log(url1);
		$.ajax({
			url:url1,
			dataType:"json",
			success: todayRealtimeSumCallback
		});
	}
	function todayRealtimeSumCallback (data) {
		var code = data.c;
		if(code == 0){
			TodayRTSum = data.d.count;
			console.log("TodayRTSum : "+TodayRTSum);
			$("#this_count").html(TodayRTSum);
		}
		var thisday = new Date();
		var thishour = thisday.getHours();
		var oneago = new Date(thisday.getTime()-1000*60*60*24);
		var oneagostr = oneago.getFullYear() + "-" + (oneago.getMonth() + 1) + "-" + oneago.getDate();
		var url2 = "http://47.93.123.129:8080/view/stays/"+ChosenGroupId+"/"+oneagostr+" 0"+"/"+oneagostr+" "+thishour;
		console.log(url2);
		$.ajax({
			url:url2,
			dataType:"json",
			success: oneagoRealtimeSumCallback
		});
	}
	function oneagoRealtimeSumCallback (data) {
		var code = data.c;
		if(code == 0){
			YesRTSum = data.d.count;
			console.log("YesRTSum : "+YesRTSum);
			$("#oneday_ago_count").html("("+YesRTSum+")");
			if(YesRTSum != 0){
				if(TodayRTSum >= YesRTSum){
					$("#yesterday_compare").attr("class","box_icon_up");
					$("#oneday_ago_rate").html(((TodayRTSum - YesRTSum)*100/YesRTSum).toFixed(1) + "%");
				}else{
					$("#yesterday_compare").attr("class","box_icon_down");
					$("#oneday_ago_rate").html(((YesRTSum - TodayRTSum)*100/YesRTSum).toFixed(1) + "%");
				}
			}else{
				$("#oneday_ago_rate").html("--");
			}
		}
		var thisday = new Date();
		var thishour = thisday.getHours();
		var weekago = new Date(thisday.getTime()-1000*60*60*24*7);
		var weekagostr = weekago.getFullYear() + "-" + (weekago.getMonth() + 1) + "-" + weekago.getDate();
		var url3 = "http://47.93.123.129:8080/view/stays/"+ChosenGroupId+"/"+weekagostr+" 0"+"/"+weekagostr+" "+thishour;
		console.log(url3);
		$.ajax({
			url:url3,
			dataType:"json",
			success: weekagoRealtimeSumCallback
		});
	}
	function weekagoRealtimeSumCallback (data) {
		 var code = data.c;
		 if(code == 0){
		 	WeekRTSum = data.d.count;
		 	console.log("WeekRTSum : "+WeekRTSum);
		 	$("#week_ago_count").html("("+WeekRTSum+")");
		 	if(WeekRTSum != 0){
				if(TodayRTSum >= WeekRTSum){
					$("#week_compare").attr("class","box_icon_up");
					$("#week_ago_rate").html(((TodayRTSum - WeekRTSum)*100/WeekRTSum).toFixed(1) + "%");
				}else{
					$("#week_compare").attr("class","box_icon_down");
					$("#week_ago_rate").html(((WeekRTSum - TodayRTSum)*100/WeekRTSum).toFixed(1) + "%");
				}
			}else{
				$("#week_ago_rate").html("--");
			}
		}
		var thisday = new Date();
		var thishour = thisday.getHours();
		var monthago = new Date(thisday.getTime()-1000*60*60*24*30);
		var monthagostr = monthago.getFullYear() + "-" + (monthago.getMonth() + 1) + "-" + monthago.getDate();
		var url4 = "http://47.93.123.129:8080/view/stays/"+ChosenGroupId+"/"+monthagostr+" 0"+"/"+monthagostr+" "+thishour;
		console.log(url4);
		$.ajax({
			url:url4,
			dataType:"json",
			success: monthagoRealtimeSumCallback
		});
	}
	function monthagoRealtimeSumCallback (data) {
		var code = data.c;
		if(code == 0){
			MonthRTSum = data.d.count;
			console.log("MonthRTSum : "+MonthRTSum)
			$("#month_ago_count").html("("+MonthRTSum+")");
			if(MonthRTSum != 0){
				if(TodayRTSum >= MonthRTSum){
					$("#month_compare").attr("class","box_icon_up");
					$("#month_ago_rate").html(((TodayRTSum - MonthRTSum)*100/MonthRTSum).toFixed(1) + "%");
				}else{
					$("#month_compare").attr("class","box_icon_down");
					$("#month_ago_rate").html(((MonthRTSum - TodayRTSum)*100/MonthRTSum).toFixed(1) + "%");
				}
			}else{
				$("#month_ago_rate").html("--");
			}
		}
	}
	function loadRealtimeChart () {
		var url = "http://47.93.123.129:8080/view/stays/"+ChosenGroupId+"/"+StartDate+"?unit=HOUR";
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeChartCallback
		});
	}
	function loadRealtimeCompareChart () {
		var url = "http://47.93.123.129:8080/view/stays/"+ChosenGroupId+"/"+CompareDate+"?unit=HOUR";
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeChartCallback
		});
	}
	function realtimeChartCallback (data) {
		var code = data.c;
		if(code == 0){
			if(IsCompare == 1){
				CRtdatas = data.d.data;
				doCompareColumnChart(Times,RtDatas,CRtdatas,"indoor_chart");
			}else{
				/*var dataCount = data.d.count;
				$("#this_count").html(dataCount);*/
				RtDatas = data.d.data;
				doColumnChart(Times,RtDatas,"indoor_chart");
			}
		}
	}
	function chooseBrandTab (gid,obj) {
		ChosenGroupId = gid;
		$("#title_storey").html($(obj).html());
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		$("#c_start").html("请选择日期");
		execute();
	}
	function loadBrandChart () {
		var url = basePath + "jaxrs/brand/indoor/nocompare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : ChosenGroupId,cxl : cxl
		}
		$.post(url,para,loadBrandChartCallBack,"json");
	}
	function loadBrandChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			if(dates.length > 1){
				doLineChart(dates,datas,"indoor_chart");
			}else{
				doColumnChart(dates,datas,"indoor_chart");
			}	
		}else{
			$("#indoor_chart").html("未查询到数据");
		}
	}
	function loadBrandStatistics () {
		var url = basePath + "jaxrs/brand/indoor/statistics";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : ChosenGroupId,cxl : cxl
		}
		$.post(url,para,loadBrandStatisticsCallBack,"json");
	}
	function loadBrandStatisticsCallBack (data) {
		var code = data.code;
		if(code == 0){
			var this_count = data.this_count;
			var oneday_ago_count = data.oneday_ago_count;
			var week_ago_count = data.week_ago_count;
			var month_ago_count = data.month_ago_count;
			$("#this_count").html(this_count);
			$("#oneday_ago_count").html("("+oneday_ago_count+")");
			$("#week_ago_count").html("("+week_ago_count+")");
			$("#month_ago_count").html("("+month_ago_count+")");
			if(oneday_ago_count != 0){
				if(this_count >= oneday_ago_count){
					$("#yesterday_compare").attr("class","box_icon_up");
					$("#oneday_ago_rate").html(((this_count - oneday_ago_count)*100/oneday_ago_count).toFixed(1) + "%");
				}else{
					$("#yesterday_compare").attr("class","box_icon_down");
					$("#oneday_ago_rate").html(((oneday_ago_count - this_count)*100/oneday_ago_count).toFixed(1) + "%");
				}
			}else{
				$("#oneday_ago_rate").html("0%");
			}
			if(week_ago_count != 0){
				if(this_count >= week_ago_count){
					$("#week_compare").attr("class","box_icon_up");
					$("#week_ago_rate").html(((this_count - week_ago_count)*100/week_ago_count).toFixed(1) + "%");
				}else{
					$("#week_compare").attr("class","box_icon_down");
					$("#week_ago_rate").html(((week_ago_count - this_count)*100/week_ago_count).toFixed(1) + "%");
				}
			}else{
				$("#week_ago_rate").html("0%");
			}
			if(month_ago_count != 0){
				if(this_count >= month_ago_count){
					$("#month_compare").attr("class","box_icon_up");
					$("#month_ago_rate").html(((this_count - month_ago_count)*100/month_ago_count).toFixed(1) + "%");
				}else{
					$("#week_compare").attr("class","box_icon_down");
					$("#month_ago_rate").html(((month_ago_count - this_count)*100/month_ago_count).toFixed(1) + "%");
				}
			}else{
				$("#month_ago_rate").html("0%");
			}
		}
	}
	function loadBrandCompareChart () {
		var url = basePath + "jaxrs/storey/indoor/compare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : ChosenGroupId,cxl :cxl
		}
		$.post(url,para,loadBrandCompareChartCallBack,"json");
	}
	function loadBrandCompareChartCallBack (data) {
		var code = data.code; 
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			if(dates.length > 1){
				doCompareLineChart(dates,datas,compareDatas,"indoor_chart");
			}else{
				doCompareColumnChart(dates,datas,compareDatas,"indoor_chart");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
		}
	}
	function doColumnChart (xList,yList,divId) {
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
	function doCompareColumnChart (xList,yList1,yList2,divId) {
		console.log(yList1);
		console.log(yList2);
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
	function doLineChart (xList,yList,divId) {
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
			    legend: {
	                enabled : false
	            },
	            credits: {
				    enabled: false
				},
			    series:[{
			    	name : "所选时段",
			    	data : yList
			    }]
			});
		}else{
			$("#"+divId).html("<div class='loading'>未查询到数据<div>");
		}
	}
	function doCompareLineChart (xList,yList1,yList2,divId) {
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
			<li><a href="entirety_inside.jsp">整体客流</a></li>
			<li><a href="storey_summ.jsp">楼层客流</a></li>
			<li><a href="business_summ.jsp">业态客流</a></li>
			<li><a class="select" href="#">品牌店铺</a></li>
			<li><a href="custom_summ.jsp">顾客模型</a></li>
<!-- 			<li><a href="character_date.jsp">特征日模型</a></li> -->
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a href="brand_summ.jsp">重点品牌概况</a></li>
					<li><a class="select" href="#">品牌经过顾客</a></li>
					<li><a href="brand_stay.jsp">品牌停留顾客</a></li>
					<li><a href="brand_residence.jsp">品牌停留时长</a></li>
				</ul>
			</div>
			<div id="top_tool_bar">
				
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="storeys_list" class="tabs_bar" style="margin-left:10px;">
					<div onclick="" class="tabs_ select_">F1</div>
					<div onclick="" class="tabs_">F2</div>
					<div onclick="" class="tabs_">F3</div>
					<div onclick="" class="tabs_">F4</div>
				</div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt"><span id="title_storey"></span>经过顾客</div>
				</div>
				<div class="dataDiv_content" id="indoor_chart"></div>
			</div>
			<table class="card">
				<tbody>
					<tr class="card_tr">
						<td class="card_td">
							<div id="box_1" class="card_box">
								<dl class="box_dl">
									<dt id="en_title1" class="box_dl_title">该时段累计经过</dt>
									<div class="box_dl_content">
										<div id="today_count" class="box_icon"></div>
										<div id="this_count" class="box_data">0</div>
										<div class="clear"></div>
									</div>
									<div id="en_foot1" class="box_dl_foot">(截止到此刻)</div>
								</dl>
							</div>
						</td>
						<td class="card_td">
							<div id="box_2" class="card_box">
								<dl class="box_dl">
									<dt id="en_title2" class="box_dl_title">对比昨日此刻</dt>
									<div class="box_dl_content">
										<div id="yesterday_compare" class="box_icon_up"></div>
										<div id="oneday_ago_rate" class="box_data">0%</div>
									</div>
									<div id="oneday_ago_count" class="box_dl_foot">(0%)</div>
								</dl>
							</div>
						</td>
						<td class="card_td">
							<div id="box_3" class="card_box">
								<dl class="box_dl">
									<dt id="en_title3" class="box_dl_title">对比上周同日此刻</dt>
									<div class="box_dl_content">
										<div id="week_compare" class="box_icon_up"></div>
										<div id="week_ago_rate" class="box_data">0%</div>
									</div>
									<div id="week_ago_count" class="box_dl_foot">(0%)</div>
								</dl>
							</div>
						</td>
						<td class="card_td">
							<div id="box_4" class="card_box">
								<dl class="box_dl">
									<dt id="en_title4" class="box_dl_title">对比上月同日此刻</dt>
									<div class="box_dl_content">
										<div id="month_compare" class="box_icon_up"></div>
										<div id="month_ago_rate" class="box_data">0%</div>
									</div>
									<div id="month_ago_count" class="box_dl_foot">(0%)</div>
								</dl>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>