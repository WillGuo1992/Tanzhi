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
	var LoginId = "<%=login_id%>";
	var Telephone = "<%=login_telephone%>";
	var LoginLevel = "<%=login_level%>";
	//var GroupId = 3;
	var IsCompare = 0;
	var StartDate;
	var EndDate;
	var CompareDate;
	var RateTab = 0;
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
		if(document.body.scrollHeight > document.body.clientHeight){
			$("#content").css("height",document.body.scrollHeight);
			$("#ul_n").css("height",$("#content").css("height"));
		}else{
			$("#content").css("height",document.body.clientHeight);
			$("#ul_n").css("height",$("#content").css("height"));
		}
		$("#start").css("color","black");
		$("#middle").css("color","black");
		$("#end").css("color","black");
		document.getElementById("start").innerHTML=getDateAgo(1);
		document.getElementById("middle").innerHTML="~";
		document.getElementById("end").innerHTML=getDateAgo(1);
		$("#aid1").parent().remove();
	});
	function execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
		if(RateTab == 0){
			loadLostChart();
		}else{
			loadReActiveChart();
		}
	}
	function compare_execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
		if(RateTab == 0){
			loadCompareLostChart();
		}else{
			loadCompareReActiveChart();
		}
	}
	function loadLostChart () {
		var url = basePath + "jaxrs/custom/lost/nocompare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadLostChartCallBack,"json");
	}
	function loadLostChartCallBack (data) {
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
	function loadReActiveChart () {
		var url = basePath + "jaxrs/custom/reactive/nocompare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadReActiveChartCallBack,"json");
	}
	function loadReActiveChartCallBack (data) {
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
	function loadCompareLostChart () {
		var url = basePath + "jaxrs/custom/lost/compare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadCompareLostChartCallBack,"json");
	}
	function loadCompareLostChartCallBack (data) {
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
	function loadCompareReActiveChart () {
		var url = basePath + "jaxrs/custom/reactive/compare";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadCompareReActiveChartCallBack,"json");
	}
	function loadCompareReActiveChartCallBack (data) {
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
	function loadVendorChart () {
		var url = basePath + "jaxrs/mobile/vender/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadVendorChartCallBack,"json");
	}
	function loadVendorChartCallBack (data) {
		var code = data.code;
		if (code == 0) {
			var datas = data.datas;
			if(datas != null&&datas.length > 0){
				var names = new Array();
				var nums = new Array();
				var rates = new Array();
				var all = 0;
				for (var i = 0; i < datas.length; i++) {
					names.push(datas[i].name);
					nums.push(datas[i].counts);
					all = all + datas[i].counts;
				}
				if (all != 0) {
					for (var i = 0; i < nums.length; i++) {
						rates.push(nums[i]*100/all);
					}
				}
				doBarChart(names,counts,"indoor_chart");
				doPieChart(names,rates,"residence_pie");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
		}
	}
	function loadOsChart () {
		var url = basePath + "jaxrs/mobile/os/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadOsChartCallBack,"json");
	}
	function loadOsChartCallBack (data) {
		var code = data.code;
		if (code == 0) {
			var datas = data.datas;
			if(datas != null&&datas.length > 0){
				var names = new Array();
				var nums = new Array();
				var rates = new Array();
				var all = 0;
				for (var i = 0; i < datas.length; i++) {
					names.push(datas[i].name);
					nums.push(datas[i].counts);
					all = all + datas[i].counts;
				}
				if (all != 0) {
					for (var i = 0; i < nums.length; i++) {
						rates.push(nums[i]*100/all);
					}
				}
				doBarChart(names,counts,"indoor_chart");
				doPieChart(names,rates,"residence_pie");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
		}
	}

	function loadCompareVendorChart () {
		var  url = basePath = "jaxrs/mobile/vender/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl :cxl
		}
		$.post(url,para,loadCompareVendorChartCallBack,"json");
	}
	function loadCompareVendorChartCallBack (data) {
		var code = data.code;
		if (code == 0) {
			var datas = data.datas;
			if(datas != null&&datas.length > 0){
				var names = new Array();
				var nums = new Array();
				var com_nums = new Array();
				var rates = new Array();
				var com_rates = new Array();
				var all = 0;
				var com_all = 0;
				for (var i = 0; i < datas.length; i++) {
					names.push(datas[i].name);
					nums.push(datas[i].counts);
					com_nums.push(datas[i].compare_count);
					all = all + datas[i].counts;
					com_all = com_all + datas[i].compare_count;
				}
				if(all != 0){
					for (var i = 0; i < nums.length; i++) {
						rates.push(nums[i]*100/all);
					}
				}
				if(com_all != 0){
					for (var i = 0; i < com_nums.length; i++) {
						com_rates.push(com_nums[i]*100/com_all);
					}
				}
				doCompareBarChart(names,nums,com_nums,"indoor_chart");
				doPieChart(names,rates,"residence_pie");
				doPieChart(names,com_rates,"residence_cpie");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
		}
	}
	function loadCompareOsChart () {
		var  url = basePath = "jaxrs/mobile/os/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : GroupId,cxl :cxl
		}
		$.post(url,para,loadCompareOsChartCallBack,"json");
	}
	function loadCompareOsChartCallBack (data) {
		var code = data.code;
		if (code == 0) {
			var datas = data.datas;
			if(datas != null&&datas.length > 0){
				var names = new Array();
				var nums = new Array();
				var com_nums = new Array();
				var rates = new Array();
				var com_rates = new Array();
				var all = 0;
				var com_all = 0;
				for (var i = 0; i < datas.length; i++) {
					names.push(datas[i].name);
					nums.push(datas[i].counts);
					com_nums.push(datas[i].compare_count);
					all = all + datas[i].counts;
					com_all = com_all + datas[i].compare_count;
				}
				if(all != 0){
					for (var i = 0; i < nums.length; i++) {
						rates.push(nums[i]*100/all);
					}
				}
				if(com_all != 0){
					for (var i = 0; i < com_nums.length; i++) {
						com_rates.push(com_nums[i]*100/com_all);
					}
				}
				doCompareBarChart(names,nums,com_nums,"indoor_chart");
				doPieChart(names,rates,"residence_pie");
				doPieChart(names,com_rates,"residence_cpie");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
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
			    series : yList
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
	}

	function changeConversionTab (num) {
		RateTab = num;
		if(num == 0){
			$("#count_tab").addClass("select_");
			$("#rate_tab").removeClass("select_");
		}else{
			$("#count_tab").removeClass("select_");
			$("#rate_tab").addClass("select_");
		}
		if(IsCompare == 0){
			execute();
		}else{
			compare_execute();
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
<body onload="execute()">
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
					<li><a href="custom_active.jsp">活跃顾客</a></li>
					<li><a class="select" href="#">流失顾客</a></li>
				</ul>
			</div>
			<div id="top_tool_bar">
				<div class="tabs_bar" style="margin-left:10px;margin_top:2px;">
					<div id="count_tab" style="width:80px;" class="tabs_ select_" onclick="changeConversionTab(0)">流失顾客</div>
					<div id="rate_tab" style="width:80px;" class="tabs_" onclick="changeConversionTab(1)">回流顾客</div>
				</div>
				<!-- <div id="count_tab" style="top:6px;" class="left_tab tab_select" onclick="changeConversionTab(0)">流失顾客</div>
				<div id="rate_tab" style="top:6px;" class="right_tab" onclick="changeConversionTab(1)">回流顾客</div> -->
				<div id="date_tool" style="margin-left:30px;" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">流失顾客</div>
				</div>
				<div class="dataDiv_content" id="indoor_chart">
				</div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>