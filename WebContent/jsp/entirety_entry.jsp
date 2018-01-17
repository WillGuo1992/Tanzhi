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
	var Entrys = new Array();
	var EntryDatas = new Array();
	var CEntryDatas = new Array();
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
		loadEnreys();
	});
	function loadEnreys(){
		var url = basePath + "jaxrs/mall/entrys";
		var cxl = Math.random();
		var para = {
				mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadEnreysCallBack,"json");
	}
	function loadEnreysCallBack(data){
		var code = data.code;
		if(code == 0){
			Entrys = data.info;
		}else{
			$("#entry_bar").html("未查询到数据");
			$("#entry_chart").html("未查询到数据");
		}
		changeToolBarTab(0,$("#a_today"));
	}
	function execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
// 		if(StartDate == EndDate){
// 			loadRealTimeBar();
// 		}else{
			loadEntryBar();
			loadEntryChart();
// 		}
	}
	function compare_execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
// 		if(StartDate == EndDate){
// 			loadCompareRealTimeBar();
// 		}else{
			loadCompareEntryBar();
// 		}
	}

	function loadRealTimeBar () {
		$("#entry_bar").html("<img src='../img/big_load.gif' />");
		$("#entry_chart").html("<img src='../img/big_load.gif' />");
		if(Entrys != null&&Entrys.length > 0){
			var gids = new Array();
			for (var i = 0; i < Entrys.length; i++) {
				gids.push(Entrys[i].groupId);
			}
			var url = "http://47.93.123.129:8080/view/enterPoints/"+gids+"/"+StartDate;
			console.log(url);
			//var url = "http://192.168.0.81:8080/view/enterPoints/3,5/"+StartDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeChartCallback
			});
		}
	}
	function realtimeChartCallback (data) {
		console.log(data);
		var code = data.c;
		if(code == 0){
			var points = data.d.enterPoints;
			EntryDatas = new Array();
			if(points != null){
				var dates = new Array();
				dates.push(StartDate);
				var entryNames = new Array();
				var barArray = new Array();
				var chartArray = new Array();
				var entrySum = 0;
				for (var key in points) {
					entrySum += points[key];
				}
				for (var key in points) {
					/*var endata = new Object();
					endata.name = key;
					endata.data = points[key];
					chartArray.push(endata);*/
					for (var i = 0; i < Entrys.length; i++) {
						if(Entrys[i].groupId == key){
							entryNames.push(Entrys[i].name);
						}
					}
					//alert(Number((points[key]*100/entrySum).toFixed(2)));
					EntryDatas.push(Number((points[key]*100/entrySum).toFixed(2)));
				}
				doBarChart(entryNames,EntryDatas,"entry_bar","%");
				for (var key in points) {
					var bardata = new Object();
					var bdata = new Array();
					for (var i = 0; i < Entrys.length; i++) {
						if(Entrys[i].groupId == key){
							bardata.name = Entrys[i].name;
						}
					}
					if(entrySum == 0){
						bdata.push(0);
						chartArray.push(0);
					}else{
						bdata.push(points[key]*100/entrySum);
						chartArray.push(points[key]*100/entrySum);
					}
					bardata.data = bdata;
					barArray.push(bardata);
				}

				doPieChart(entryNames,chartArray,"entry_chart");
				//doLineChart(dates,barArray,"entry_chart","%");
			}
		}
	}
	function loadCompareRealTimeBar () {
		$("#entry_bar").html("<img src='../img/big_load.gif' />");
		if(Entrys != null&&Entrys.length > 0){
			var gids = new Array();
			for (var i = 0; i < Entrys.length; i++) {
				gids.push(Entrys[i].groupId);
			}
			var url = "http://47.93.123.129:8080/view/enterPoints/"+gids+"/"+CompareDate;
			//var url = "http://192.168.0.81:8080/view/enterPoints/3,5/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeChartCallback
			});
		}
	}
	function compareRealtimeChartCallback (data) {
		var code = data.c;
		if(code == 0){
			CEntryDatas = new Array();
			var points = data.d.enterPoints;
			//alert(points);
			if(points != null){
				var entryNames = new Array();
				for (var key in points) {
					for (var i = 0; i < Entrys.length; i++) {
						if(Entrys[i].groupId == key){
							entryNames.push(Entrys[i].name);
						}
					}
					CEntryDatas.push(points[key]);
				}
				doCompareBarChart(entryNames,EntryDatas,CEntryDatas,"entry_bar","%");
			}
		}
	}
	function loadEntryBar() {
		$("#entry_bar").html("<img src='../img/big_load.gif' />");
		var url = basePath + "jaxrs/entirety/entry/nocampare/rate";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadEntryBarCallBack,"json");
	}
	function loadEntryBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var namelist = new Array();
			var entrys = data.entrys;
			if(entrys!=null&&entrys.length>0){
				for (var i = 0; i < entrys.length; i++) {
					namelist.push(entrys[i].name);
				}
			}
			var datas = data.datas;
			console.log("entrys");
			console.log(entrys);
			doBarChart(namelist,datas,"entry_bar","%");
		}else{
			$("#entry_bar").html("未查询到数据");
		}
	}

	function loadCompareEntryBar () {
		$("#entry_bar").html("<img src='../img/big_load.gif' />");
		var url = basePath + "jaxrs/entirety/entry/campare/rate";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadCompareEntryBarCallBack,"json");
	}
	function loadCompareEntryBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var namelist = new Array();
			var entrys = data.entrys;
			if(entrys!=null&&entrys.length>0){
				for (var i = 0; i < entrys.length; i++) {
					namelist.push(entrys[i].name);
				}
			}
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			doCompareBarChart(namelist,datas,compareDatas,"entry_bar","%");
		}else{
			$("#entry_bar").html("未查询到数据");
		}
	}
	/*function doBarChart (xList,yList,divId) {
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
	function loadEntryChart () {
		$("#entry_chart").html("<img src='../img/big_load.gif' />");
		var url = basePath + "jaxrs/entirety/entry/nocampare/chart";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadEntryChartCallBack,"json");
	}
	function loadEntryChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			console.log(datas);
			/*var ddd = [{"name":"一层南门出入口","data":[1,2,3]},{"name":"一层东南门出入口","data":[1,2,3]},{"name":"一层西门出入口","data":[1,2,3]},{"name":"安全出口","data":[1,2,3]},{"name":"安全出口","data":[1,2,3]},{"name":"出入口","data":[1,2,3]},{"name":"出入口","data":[1,2,3]}];*/
			/*if(dates.length > 1){
				doLineChart(dates,datas,"entry_chart");
			}else{
				doColumnChart(dates,datas,"entry_chart");
			}*/
			doStackAreaChart(dates,datas,"entry_chart","%");
		}else{
			$("#entry_chart").html("未查询到数据");
		}
	}

	function doStackAreaChart (xList,yList,divId,unit) {
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
		            },
		            tickmarkPlacement: 'on',
		            title: {
		                enabled: false
		            }
		        },
		        plotOptions: {
		            area: {
		                stacking: 'normal',
		                lineColor: '#666666',
		                lineWidth: 1,
		                marker: {
		                    lineWidth: 1,
		                    lineColor: '#666666'
		                }
		            }
		        },
		        yAxis: {
		            title: null
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
			    series : yList
			});
		}else{
			$("#"+divId).html("未查询到数据");
		}
	}

	function doLineChart (xList,yList,divId,unit) {
		console.log("yList:");
		console.log(yList);
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
		        tooltip: {
		            valueSuffix: unit
		        },
			    legend: {
	                enabled : false
	            },
	            credits: {
				    enabled: false
				},
			    series : yList
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
					<li><a class="select" href="#">各入口比例</a></li>
					<li><a href="entirety_stay.jsp">整体停留顾客</a></li>
					<li><a href="entirety_residence.jsp">整体停留时长</a></li>
				</ul>
			</div>
			<div id="top_tool_bar">
				<ul class="tool_bar_ul">
					<li><a id="a_today" class="select" href="#" onclick="changeToolBarTab(0,this)">今日</a></li>
					<li><a id="a_yesterday" href="#" onclick="changeToolBarTab(1,this)">昨日</a></li>
					<li><a id="a_choose" href="#" onclick="changeToolBarTab(2,this)">选择时段</a></li>
				</ul>
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">各入口来源比例</div>
				</div>
				<div class="dataDiv_content" id="entry_bar"></div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">每日比例详情</div>
				</div>
				<div class="dataDiv_content" id="entry_chart"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>