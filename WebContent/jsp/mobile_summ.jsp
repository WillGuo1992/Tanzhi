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
		$("#start").css("color","black");
		$("#middle").css("color","black");
		$("#end").css("color","black");
		if(document.body.scrollHeight > document.body.clientHeight){
			$("#content").css("height",document.body.scrollHeight);
			$("#ul_n").css("height",$("#content").css("height"));
		}else{
			$("#content").css("height",document.body.clientHeight);
			$("#ul_n").css("height",$("#content").css("height"));
		}
		$("#aid1").parent().remove();
		document.getElementById("start").innerHTML=getDateAgo(1);
		document.getElementById("middle").innerHTML="~";
		document.getElementById("end").innerHTML=getDateAgo(1);
		execute();
	});
	function execute () {
		if(document.getElementById("residence_cpie")){
			$("#residence_cpie").remove();
			$("#residence_pie").css("height","280px");
		}
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		$("#residence_pie").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
		if(RateTab == 0){
			loadVendorChart();
		}else{
			loadOsChart();
		}
	}
	function compare_execute () {
		if(!document.getElementById("residence_cpie")){
			$("#residence_pie").css("height","140px");
			$("#entry_bar").append("<div id='residence_cpie' style='width:50%;height:140px;text-align:center;line-height:140px'></div>");
		}
		$("#indoor_chart").html("<img src='../img/big_load.gif' />");
		$("#residence_pie").html("<img src='../img/big_load.gif' />");
		$("#residence_cpie").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
		if(RateTab == 0){
			loadCompareVendorChart();
		}else{
			loadCompareOsChart();
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
				var other = 0;
				if(datas.length > 9){
					for (var i = 0; i < 9; i++) {
						names.push(datas[i].name);
						nums.push(datas[i].counts);
						all = all + datas[i].counts;
					}
					for (var i = 9; i < datas.length; i++) {
						/*names.push("其他");
						nums.push(datas[i].counts);*/
						other = other + datas[i].counts;
						all = all + datas[i].counts;
					}
					names.push("其他");
					nums.push(other);
					if (all != 0) {
						for (var i = 0; i < nums.length; i++) {
							rates.push(nums[i]*100/all);
						}
					}
				}else{
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
				}
				doBarChart(names,nums,"indoor_chart");
				doPieChart(names,rates,"residence_pie");
			}else{
				$("#indoor_chart").html("未查询到数据");
				$("#residence_pie").html("未查询到数据");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
			$("#residence_pie").html("未查询到数据");
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
				doBarChart(names,nums,"indoor_chart");
				doPieChart(names,rates,"residence_pie");
			}
		}else{
			$("#indoor_chart").html("未查询到数据");
			$("#residence_pie").html("未查询到数据");
		}
	}

	function loadCompareVendorChart () {
		var  url = basePath + "jaxrs/mobile/vender/compare/bar";
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
				var other = 0;
				var com_all = 0;
				var com_other = 0;
				if(datas.length > 9){
					for (var i = 0; i < 9; i++) {
						names.push(datas[i].name);
						nums.push(datas[i].counts);
						com_nums.push(datas[i].compare_count);
						all = all + datas[i].counts;
						com_all = com_all + datas[i].compare_count;
					}
					for (var i = 9; i < datas.length; i++) {
						other = other + datas[i].counts;
						com_other = com_other + datas[i].compare_count;
						all = all + datas[i].counts;
						com_all = com_all + datas[i].compare_count;
					}
					names.push("其他");
					nums.push(other);
					com_nums.push(com_other);
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
				}else{
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
		var  url = basePath + "jaxrs/mobile/os/compare/bar";
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
	/*function doBarChart (xList,yList,divId) {
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
	function doCompareBarChart (xList,yList1,yList2,divId) {
		if(xList!=null&&xList.length>0&&yList1!=null&&yList1.length>0){
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
	/*function doPieChart (xList,yList,divId) {
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
	function changeToolBarTab (num,obj) {
		$(obj).addClass("select");
		$(obj).parent().siblings("li").find("a").removeClass("select");
		if(num == 2){
			/*$("#time_a").removeAttr("onclick");
			$("#time_a").attr("onclick","timet('time','timecolse','Custom',$('#time_a'));closeWin();");*/
		}else{
			/*$("#time_a").removeAttr("onclick");*/
			searchTimeFlag(num);
		}
	}

	function changeConversionTab (num) {
		RateTab = num;
		if(num == 0){
			$("#count_tab").addClass("select_");
			$("#rate_tab").removeClass("select_");
			$("#data_title").html("手机品牌");
		}else{
			$("#count_tab").removeClass("select_");
			$("#rate_tab").addClass("select_");
			$("#data_title").html("操作系统");
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
			<li><a href="custom_summ.jsp">顾客模型</a></li>
			<!-- <li><a href="character_date.jsp">特征日模型</a></li> -->
			<li><a class="select" href="#">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div class="notitle_tool_bar">
				<div class="tabs_bar" style="margin-left:10px;margin_top:2px;">
					<div id="count_tab" style="width:80px;" class="tabs_ select_" onclick="changeConversionTab(0)">手机品牌</div>
					<div id="rate_tab" style="width:80px;" class="tabs_" onclick="changeConversionTab(1)">操作系统</div>
				</div>
				<!-- <div id="count_tab" style="top:6px;" class="left_tab tab_select" onclick="changeConversionTab(0)">手机品牌</div>
				<div id="rate_tab" style="top:6px;" class="right_tab" onclick="changeConversionTab(1)">操作系统</div> -->
				<div id="date_tool" style="margin-left:30px;" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt"><span id="data_title">手机品牌</span>排名</div>
				</div>
				<div class="dataDiv_content" id="entry_bar">
					<div id="indoor_chart"></div>
					<div id="residence_pie"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>