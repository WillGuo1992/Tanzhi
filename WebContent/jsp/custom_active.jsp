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
	//var GroupId = 3;
	var IsCompare = 0;
	var IsNewCompare = 0;
	var StartDate;
	var EndDate;
	var CompareDate;
	var NewStartDate;
	var NewCompareDate;
	var RateTab = 0;
	var DayTab = 0;
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
		$("#content").css("height",document.body.scrollHeight);
		$("#ul_n").css("height",$("#content").css("height"));
		document.getElementById("start").innerHTML=getDateAgo(1);
		document.getElementById("middle").innerHTML="~";
		document.getElementById("end").innerHTML=getDateAgo(1);
		document.getElementById("new_start").innerHTML=getDateAgo(1);
		$("#start").css("color","black");
		$("#middle").css("color","black");
		$("#end").css("color","black");
		$("#aid1").parent().remove();
		execute();
		new_execute();
	});
	function execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
		if(RateTab == 0){
			loadCurrentChart();
		}else if(RateTab == 1){
			loadThirtyDaysChart();
		}else if(RateTab == 2){
			loadIncreaseChart();
		}
	}
	function compare_execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
		if(RateTab == 0){
			loadCompareCurrentChart();
		}else if(RateTab == 1){
			loadCompareThirtyDaysChart();
		}else if(RateTab == 2){
			loadCompareIncreaseChart();
		}
	}
	function new_execute () {
		NewStartDate = $("#new_start").html();
		$("#c_new_start").html("请选择日期");
		loadDistributeBar();
	}
	function new_compare_execute () {
		NewStartDate = $("#new_start").html();
		NewCompareDate = $("#c_new_start").html();
		loadCompareDistributeBar();
	}
	function loadDistributeBar () {
		var url = basePath + "jaxrs/custom/distribute/nocompare";
		var cxl = Math.random();
		var para = {
				date : NewStartDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadDistributeBarCallBack,"json");
	}
	function loadDistributeBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var datas = data.datas;
			var axises = ["4次","5次","6次","7次","8次","9次","10次","大于10次"];
			doBarChart(axises,datas,"active_distribute");
		}else{
			$("#active_distribute").html("未查询到数据");
		}
	}
	function loadCompareDistributeBar () {
		var url = basePath + "jaxrs/custom/distribute/compare";
		var cxl = Math.random();
		var para = {
				date : NewStartDate,compareDate : NewCompareDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadCompareDistributeBarCallBack,"json");
	}
	function loadCompareDistributeBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			var axises = ["4次","5次","6次","7次","8次","9次","10次","大于10次"];
			doCompareBarChart(axises,datas,compareDatas,"active_distribute");
		}else{
			$("#active_distribute").html("未查询到数据");
		}
	}
	function loadCurrentChart () {
		var url = basePath + "jaxrs/custom/current/nocompare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl 
		}
		$.post(url,para,loadCurrentChartCallBack,"json");
	}
	function loadCurrentChartCallBack (data) {
		console.log("loadCurrentChart");
		console.log(data);
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			if(dates.length > 1){
				doLineChart(dates,datas,"active_chart");
			}else{
				doColumnChart(dates,datas,"active_chart");
			}
		}else{
			$("#active_chart").html("未查询到数据");
		}
	}
	function loadThirtyDaysChart () {
		var url = basePath + "jaxrs/custom/thirty/nocompare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl 
		}
		$.post(url,para,loadThirtyDaysChartCallBack,"json");
	}
	function loadThirtyDaysChartCallBack (data) {
		console.log("loadThirtyDaysChart");
		console.log(data);
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			if(dates.length > 1){
				doLineChart(dates,datas,"active_chart");
			}else{
				doColumnChart(dates,datas,"active_chart");
			}
		}else{
			$("#active_chart").html("未查询到数据");
		}
	}
	function loadIncreaseChart () {
		var url = basePath + "jaxrs/custom/increase/nocompare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,dayTab : DayTab,cxl : cxl 
		}
		$.post(url,para,loadIncreaseChartCallBack,"json");
	}
	function loadIncreaseChartCallBack (data) {
		console.log("loadIncreaseChart");
		console.log(data);
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			if(dates.length > 1){
				doLineChart(dates,datas,"active_chart");
			}else{
				doColumnChart(dates,datas,"active_chart");
			}
		}else{
			$("#active_chart").html("未查询到数据");
		}
	}
	function loadCompareCurrentChart () {
		var url = basePath + "jaxrs/custom/current/compare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl : cxl 
		}
		$.post(url,para,loadCompareCurrentChartCallBack,"json");
	}
	function loadCompareCurrentChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			if(dates.length > 1){
				doCompareLineChart(dates,datas,compareDatas,"active_chart");
			}else{
				doCompareColumnChart(dates,datas,compareDatas,"active_chart");
			}
		}else{
			$("#active_chart").html("未查询到数据");
		}
	}
	function loadCompareThirtyDaysChart () {
		var url = basePath + "jaxrs/custom/thirty/compare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl : cxl 
		}
		$.post(url,para,loadCompareThirtyDaysChartCallBack,"json");
	}
	function loadCompareThirtyDaysChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			if(dates.length > 1){
				doCompareLineChart(dates,datas,compareDatas,"active_chart");
			}else{
				doCompareColumnChart(dates,datas,compareDatas,"active_chart");
			}
		}else{
			$("#active_chart").html("未查询到数据");
		}
	}
	function loadCompareIncreaseChart () {
		var url = basePath + "jaxrs/custom/increase/compare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,dayTab : DayTab,cxl : cxl 
		}
		$.post(url,para,loadCompareIncreaseChartCallBack,"json");
	}
	function loadCompareIncreaseChartCallBack (data) {
		var code = data.code;
		if(code == 0){
			var dates = data.dates;
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			if(dates.length > 1){
				doCompareLineChart(dates,datas,compareDatas,"active_chart");
			}else{
				doCompareColumnChart(dates,datas,compareDatas,"active_chart");
			}
		}else{
			$("#active_chart").html("未查询到数据");
		}
	}
	function changeConversionTab (num) {
		RateTab = num;
		if(num == 0){
			$("#c_select").attr("disabled","disabled");
			$("#tab_0").addClass("select_");
			$("#tab_1").removeClass("select_");
			$("#tab_2").removeClass("select_");
		}else if(num == 1){
			$("#c_select").attr("disabled","disabled");
			$("#tab_1").addClass("select_");
			$("#tab_0").removeClass("select_");
			$("#tab_2").removeClass("select_");
		}else if(num == 2){
			$("#c_select").removeAttr("disabled","disabled");
			$("#tab_2").addClass("select_");
			$("#tab_1").removeClass("select_");
			$("#tab_0").removeClass("select_");
		}
		if(IsCompare == 0){
			execute();
		}else{
			compare_execute();
		}
	}
	function changeDayTab (obj) {
		DayTab = $(obj).val();
		if(IsCompare == 0){
			loadIncreaseChart();
		}else{
			loadCompareIncreaseChart();
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
		if(xList!=null&&xList.length>0&&yList!=null&&yList.length>0){
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
			$("#"+divId).html("未查询到数据");
		}
	}
	function doCompareLineChart (xList,yList1,yList2,divId) {
		if(xList!=null&&xList.length>0&&yList1!=null&&yList1.length>0){
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
	function doBarChart (xList,yList,divId) {
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
	}
	function doCompareBarChart (xList,yList1,yList2,divId) {
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
			<li><a href="brand_summ.jsp">品牌店铺</a></li>
			<li><a class="select" href="#">顾客模型</a></li>
			<!-- <li><a href="character_date.jsp">特征日模型</a></li> -->
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a href="custom_summ.jsp">顾客模型概况</a></li>
					<li><a href="custom_new.jsp">拉新顾客</a></li>
					<li><a class="select" href="#">活跃顾客</a></li>
					<li><a href="custom_lost.jsp">流失顾客</a></li>
				</ul>
			</div>
			<div id="top_tool_bar">
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">活跃顾客</div>
					<div id="tab_0" class="tabs_ select_" onclick="changeConversionTab(0)" style="border-left:solid 1px #BCBABB;margin-top:2px;width:120px;">活跃率</div>
					<div id="tab_1" class="tabs_" onclick="changeConversionTab(1)" style="margin-top:2px;width:120px;">活跃顾客</div>
					<div id="tab_2" class="tabs_" onclick="changeConversionTab(2)" style="margin-top:2px;width:120px;">活跃顾客增长数 </div>
					<div style="float:left;margin-left:30px;margin-top:10px;">
						与<select id="c_select" onchange="changeDayTab(this)" disabled="disabled">
							<option value="0">上一日</option>
							<option value="1">上一周</option>
							<option value="2">上一月</option>
						</select>相比 
					</div>
				</div>
				<div class="dataDiv_content" id="active_chart"></div>
			</div>
			<div id="top_tool_bar">
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/new_date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_new_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt" style="width:500px;">活跃度分布(所选时段往前30天内的到达次数)</div>
				</div>
				<div class="dataDiv_content" id="active_distribute"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>