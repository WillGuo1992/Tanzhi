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
	var StartDate;
	var EndDate;
	var CompareDate;

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
		$("#aid1").parent().remove();
		document.getElementById("start").innerHTML=getDateAgo(1);
		document.getElementById("middle").innerHTML="~";
		document.getElementById("end").innerHTML=getDateAgo(1);
		$("#start").css("color","black");
		$("#middle").css("color","black");
		$("#end").css("color","black");
		execute();
	});
	function execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
		loadCustomStatistics();
		loadCustomChart();
	}
	function compare_execute () {
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
		loadCompareCustomChart();
	}
	function loadCustomStatistics () {
		var url = basePath + "jaxrs/custom/summ/statistics";
		var cxl = Math.random();
		var para = {
				groupId : GroupId,cxl : cxl
		}
		$.post(url,para,loadCustomStatisticsCallBack,"json");
	}
	function loadCustomStatisticsCallBack (data) {
		console.log(data);
		var code = data.code;
		if(code == 0){
			var history_count = data.history_count;
			var this_count = data.this_count;
			var oneday_ago_count = data.oneday_ago_count;
			var week_ago_count = data.week_ago_count;
			var month_ago_count = data.month_ago_count;
			var baseSum = -4930000;
			var d = new Date();
			var addSum = parseInt(d.getTime() /(60*60*1000) * 12 + baseSum ) ;
			$("#table_data_1").html(addSum);
			$("#table_data_2").html(this_count);
			$("#oneday_ago_count").html(oneday_ago_count);
			$("#week_ago_count").html(week_ago_count);
			$("#month_ago_count").html(month_ago_count);
			if(oneday_ago_count != 0){
				if (this_count >= oneday_ago_count) {
					$("#oneday_ago_rate").html(((this_count-oneday_ago_count)*100/oneday_ago_count).toFixed(2)+"%");
				}else{
					$("#oneday_ago_rate").html(((oneday_ago_count-this_count)*100/oneday_ago_count).toFixed(2)+"%");
				}
			}else{
				$("#oneday_ago_rate").html("0%");
			}
			if(week_ago_count != 0){
				if(this_count >= week_ago_count){
					$("#week_ago_rate").html(((this_count-week_ago_count)*100/week_ago_count).toFixed(2)+"%");
				}else{
					$("#week_ago_rate").html(((week_ago_count-this_count)*100/week_ago_count).toFixed(2)+"%");
				}
			}else{
				$("#week_ago_rate").html("0%");
			}
			if(month_ago_count != 0){
				if(this_count >= month_ago_count){
					$("#month_ago_rate").html(((this_count-month_ago_count)*100/month_ago_count).toFixed(2)+"%");
				}else{
					$("#month_ago_rate").html(((month_ago_count-this_count)*100/month_ago_count).toFixed(2)+"%");
				}
			}else{
				$("#month_ago_rate").html("0%");
			}
		}
	}
	function loadCustomChart () {
		var url = basePath + "jaxrs/custom/summ/nocompare/chart";
		var cxl = Math.random();
		var para = {
				groupId : GroupId,startDate : StartDate,endDate : EndDate,cxl : cxl
		}
		$.post(url,para,loadCustomChartCallBack,"json");
	}
	function loadCustomChartCallBack (data) {
		console.log("custom");
		console.log(data);
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
	function loadCompareCustomChart () {
		var url = basePath + "jaxrs/custom/summ/compare/chart";
		var cxl = Math.random();
		var para = {
				groupId : GroupId,startDate : StartDate,endDate : EndDate,compareDate : CompareDate,cxl : cxl
		}
		$.post(url,para,loadCompareCustomChartCallBack,"json");
	}
	function loadCompareCustomChartCallBack (data) {
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
		            	step: gap,
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
					<li><a class="select" href="#">顾客模型概况</a></li>
					<li><a href="custom_new.jsp">拉新顾客</a></li>
					<li><a href="custom_active.jsp">活跃顾客</a></li>
					<li><a href="custom_lost.jsp">流失顾客</a></li>
				</ul>
			</div>
			<table class="table_card">
				<tbody>
					<tr class="card_tr">
						<td class="card_td_new">
							<div class="new_card_box">
								<div id="table_title_1" class="table_dl_title">
									<div class="table_title_icon"></div>
									<div class="table_title_txt">总顾客数</div>
									<div class="clear"></div>
								</div>
								<div id="table_data_1" class="table_dl_data">0</div>
								<div id="table_foot_1" class="table_dl_foot">(历史累计排重总数)</div>
							</div>
						</td>
						<td class="card_td_new">
							<div class="new_card_box">
								<div id="table_title_2" class="table_dl_title">
									<div class="table_title_icon"></div>
									<div class="table_title_txt">活跃顾客数</div>
									<div class="clear"></div>
								</div>
								<div id="table_data_2" class="table_dl_data">892</div>
								<div id="table_foot_2" class="table_dl_foot">(30天内到达过4次)</div>
							</div>
						</td>
						<td class="table_td_new">
							<div class="new_table_box">
								<table style="width:100%;border-collapse:collapse;">
									<tr class="table_txt_row">
										<td class="txt_td_1">对比前一日：</td>
										<td id="oneday_ago_count" class="txt_td_2">852</td>
										<td class="txt_td_2"><div style="width:30px;height:30px;float:left;"></div><div id="oneday_ago_rate" style="float:left;">5%</div></td>
									</tr>
									<tr class="table_txt_row">
										<td class="txt_td_1">对比前一周同期：</td>
										<td id="week_ago_count" class="txt_td_2">852</td>
										<td class="txt_td_2"><div style="width:30px;height:30px;float:left;"></div><div id="week_ago_rate" style="float:left;">5%</div></td>
									</tr>
									<tr class="table_txt_row">
										<td class="txt_td_1" style="border:none">对比前一月同期：</td>
										<td id="month_ago_count" class="txt_td_2" style="border:none">852</td>
										<td class="txt_td_2" style="border:none"><div style="width:30px;height:30px;float:left;"></div><div id="month_ago_rate" style="float:left;">5%</div></td>
									</tr>
								</table>
							</div>
							<!-- <table class="new_table_box">
								<tr class="table_txt"></tr>
								<tr class="table_txt"></tr>
								<tr class="table_txt"></tr>
							</table>
							<div class="new_table_box">
								<div class="table_txt"><span style="text-align:left;margin-left:20px;font-family:'微软雅黑';">对比前一日：  </span><span style="margin-left:80px;font-family:'微软雅黑';">835</span></div>
								<div class="table_txt"><span style="text-align:left;margin-left:20px;font-family:'微软雅黑';">对比前一周同期：</span><span style="margin-left:80px;font-family:'微软雅黑';">835</span></div>
								<div style="border:none;" class="table_txt"><span style="text-align:left;margin-left:20px;font-family:'微软雅黑';">对比前一月同期：</span><span style="margin-left:80px;font-family:'微软雅黑';">835</span></div>
							</div> -->
						</td>
					</tr>
				</tbody>
			</table>
			<div id="tool_bar">
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">累计顾客总数</div>
				</div>
				<div id="indoor_chart" class="dataDiv_content"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>