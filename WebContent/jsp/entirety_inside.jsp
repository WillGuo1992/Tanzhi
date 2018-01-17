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
		console.log("groupid : "+GroupId+", mallid : "+MallId);
		$("#content").css("height",document.body.scrollHeight);
		$("#ul_n").css("height",$("#content").css("height"));
		var uri = document.location.toString();
		if(uri.indexOf("=") >= 0){
			var lt = uri.split("=")[1];
			if(lt == "0"){
				document.body.style.overflow='hidden';
				$("#albg1").show();
				$("#alcont1").show();
			}
		}
		changeToolBarTab(0,$("#a_today"));
	});
	function execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		console.log(StartDate+"----"+EndDate);
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
			loadIndoorSum();
// 		}
// 		if(StartDate  == EndDate){
// 			loadRealTimeBar();
// 		}else{
			loadIndoorChart();
// 		}
	}
	function compare_execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
// 		if(StartDate == EndDate){
// 			loadCompareRealTimeBar();
// 		}else{
			loadCompareIndoorChart();
// 		}
	}
	function realtimeChartCallback (data) {
		console.log(data);
		var code = data.c;
		if(code == 0){
			if(IsCompare == 1){
				CRtdatas = data.d.data;
				console.log("compare:"+CRtdatas);
				doCompareColumnChart(Times,RtDatas,CRtdatas,"indoor_chart");
			}else{
				RtDatas = data.d.data;
				doColumnChart(Times,RtDatas,"indoor_chart");
			}
		}
		/*var times = ["00:00","4:00","8:00","12:00","16:00","20:00","24:00"];
		var datas = [12,43,32,44,14,23,22];
		doColumnChart(times,datas,"indoor_chart");*/
	}
	function loadRealTimeSum(){
		var thisday = new Date();
		var thishour = thisday.getHours();
		var url1 = "http://120.27.44.69:8080/view/stays/"+GroupId+"/"+StartDate+" 0"+"/"+StartDate+" "+(thishour+1);
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
		var url2 = "http://120.27.44.69:8080/view/stays/"+GroupId+"/"+oneagostr+" 0"+"/"+oneagostr+" "+(thishour+1);
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
		var url3 = "http://120.27.44.69:8080/view/stays/"+GroupId+"/"+weekagostr+" 0"+"/"+weekagostr+" "+(thishour+1);
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
		var url4 = "http://47.93.123.129:8080/view/stays/"+GroupId+"/"+monthagostr+" 0"+"/"+monthagostr+" "+(thishour+1);
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
	function loadRealTimeBar () {
		$("#indoor_chart").html("<img src='../img/big_load.gif'/>")
		var url = "http://120.27.44.69:8080/view/stays/"+GroupId+"/"+StartDate+"?unit=HOUR";
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeChartCallback
		});
	}
	function loadCompareRealTimeBar () {
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		var url = "http://120.27.44.69:8080/view/stays/"+GroupId+"/"+CompareDate+"?unit=HOUR";
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeChartCallback
		});
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
	function loadIndoorSum(){
		var url = basePath + "jaxrs/entirety/indoor/statistics";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadIndoorSumCallBack,"json");
	}
	function loadIndoorSumCallBack (data) {
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
				$("#oneday_ago_rate").html("--");
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
				$("#week_ago_rate").html("--");
			}
			if(month_ago_count != 0){
				if(this_count >= month_ago_count){
					$("#month_compare").attr("class","box_icon_up");
					$("#month_ago_rate").html(((this_count - month_ago_count)*100/month_ago_count).toFixed(1) + "%");
				}else{
					$("#month_compare").attr("class","box_icon_down");
					$("#month_ago_rate").html(((month_ago_count - this_count)*100/month_ago_count).toFixed(1) + "%");
				}
			}else{
				$("#month_ago_rate").html("--");
			}
		}
	}
	function loadIndoorChart () {
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		var url = basePath + "jaxrs/entirety/indoor/nocompare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadIndoorChartCallBack,"json");
	}
	function loadIndoorChartCallBack (data) {
		console.log("loadIndoorChart:"+data);
		var code = data.code;
		if (code == 0) {
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

	function loadCompareIndoorChart () {
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		var  url = basePath + "jaxrs/entirety/indoor/compare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl :cxl
		}
		$.post(url,para,loadCompareIndoorChartCallBack,"json");
	}
	function loadCompareIndoorChartCallBack (data) {
		var code = data.code;
		if (code == 0) {
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
			    series : [{
			    	name : "所选时段",
			    	data : yList
			    }]
			});
		}else{
			$("#"+divId).html("未查询到数据");
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
			$("#"+divId).html("未查询到数据");
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
			searchTimeFlag(0);
			//execute();
		}else if(num == 1){
			$("#start").css("color","#CCCCCC");
			$("#middle").css("color","#CCCCCC");
			$("#end").css("color","#CCCCCC");
			$("#time_a").removeAttr("onclick");
			searchTimeFlag(1);
			//execute();
		}else if(num == 2){
			$("#start").css("color","black");
			$("#middle").css("color","black");
			$("#end").css("color","black");
			document.getElementById("time_a").setAttribute("onclick","timet('time','timecolse','Custom',$('#time_a'));closeWin();");
		}
	}
	function showManageDiv () {
		$(".manageDiv").slideToggle();
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
	<div class="xy" id="albg1"></div>
	<div class="xytxt" id="alcont1" style="width:600px;height:400px;left:50%;top:50%;margin-left:-300px;margin-top:-200px;">
		<div style="width:500px;height:100px;padding:20px 50px 0 50px;font-size:22px;">&nbsp;&nbsp;尊敬的用户您好:<br>&nbsp;&nbsp;感谢您注册探知账号，请您在系统管理中完善您的企业信息与设备信息后开始使用。</div>
		<div style="width:500px;height:200px;padding:0 50px;"></div>
		<a class="by_button" style="margin-left:140px;margin-top:20px;" href="mallinfo.jsp">前往系统管理页</a>
	</div>
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
					<li><a class="select" href="#">整体到达顾客</a></li>
					<li><a href="entirety_entry.jsp">各入口比例</a></li>
					<li><a href="entirety_stay.jsp">整体停留顾客</a></li>
					<li><a href="entirety_residence.jsp">整体停留时长</a></li>
				</ul>
			</div>
			<table class="card">
				<tbody>
					<tr class="card_tr">
						<td class="card_td">
							<div id="box_1" class="card_box">
								<dl class="box_dl">
									<dt id="en_title1" class="box_dl_title">今日累计</dt>
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
									<div id="oneday_ago_count" class="box_dl_foot">(0)</div>
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
									<div id="week_ago_count" class="box_dl_foot">(0)</div>
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
									<div id="month_ago_count" class="box_dl_foot">(0)</div>
								</dl>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div id="tool_bar">
				<ul class="tool_bar_ul">
					<li><a id="a_today" class="select" href="javascript:void(0)" onclick="changeToolBarTab(0,this)">今日</a></li>
					<li><a id="a_yesterday" href="javascript:void(0)" onclick="changeToolBarTab(1,this)">昨日</a></li>
					<li><a id="a_choose" href="javascript:void(0)" onclick="changeToolBarTab(2,this)">选择时段</a></li>
				</ul>
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">整体到达</div>
				</div>
				<div id="indoor_chart" class="dataDiv_content"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>