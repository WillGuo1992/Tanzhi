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
	var Brands = new Array();
	var TimeTab = 0;//时间标签选择变量，0:今日,1:昨日,2:历史时段
	var RtltDatas = new Array();
	var RtgtDatas = new Array();
	var CRtltDatas = new Array();
	var CRtgtDatas = new Array();
	var RtAvgDatas = new Array();
	var CRtAvgDatas = new Array();
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
			Brands = data.brands;
		}
		changeToolBarTab(0,$("#a_today"));
	}
	function execute () {
		$("#storey_sum").html("<img src='../img/big_load.gif' />");
		$("#storey_residence").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
// 		if(TimeTab == 0){
// 			loadRealtimeSummBar();
// 			loadRealtimeResidenceBar();
// 		}else{
			loadBrandSummBar();
			loadBrandResidenceBar();
// 		}
	}
	function compare_execute () {
		$("#storey_sum").html("<img src='../img/big_load.gif' />");
		$("#storey_residence").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
// 		if(TimeTab == 0){
// 			loadCompareRealtimeSummBar();
// 			loadCompareRealtimeResidenceBar();
// 		}else{
			loadCompareBrandSummBar();
			loadCompareBrandResidenceBar();
// 		}
	}
	function loadRealtimeSummBar () {
		/*var url = "http://192.168.0.81:8080/timeDistribution/entireStayLt5Gt5/3,5/"+StartDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeSummBarCallback
			});*/
		if(Brands != null&&Brands.length>0){
			var gids = new Array();
			for (var i = 0; i < Brands.length; i++) {
				gids.push(Brands[i].groupId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/entireStayLt5Gt5/"+gids+"/"+StartDate;
			console.log(url);
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeSummBarCallback
			});
		}else{
			$("#storey_sum").html("未查询到数据");
		}
	}

	function realtimeSummBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					var suma = a.gt5 + a.lt5;
					var sumb = b.gt5 + b.lt5;
					return suma > sumb ? -1:1;
				});
				var nameArray = new Array();
				RtltDatas = new Array();
				RtgtDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					RtltDatas.push(list[i].lt5);
					RtgtDatas.push(list[i].gt5);
					for (var j = 0; j < Brands.length; j++) {
						if(Brands[j].groupId == list[i].gId){
							nameArray.push(Brands[j].name);
						}
					}
				}
				doPileBarChart(nameArray,RtgtDatas,RtltDatas,"storey_sum");
				//doPileBarChart(["海底捞","金鼎轩"],RtgtDatas,RtltDatas,"storey_sum");
			}
		}
	}
	function loadRealtimeResidenceBar () {
		/*var url = "http://192.168.0.81:8080/timeDistribution/entireAVGs/3,5/"+StartDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeResidenceBarCallback
			});*/
		if(Brands != null&&Brands.length>0){
			var gids = new Array();
			for (var i = 0; i < Brands.length; i++) {
				gids.push(Brands[i].groupId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/entireAVGs/"+gids+"/"+StartDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeResidenceBarCallback
			});
		}else{
			$("#storey_residence").html("未查询到数据");
		}
	}
	function realtimeResidenceBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					return a.avg > b.avg ? -1:1;
				});
				var nameArray = new Array();
				RtAvgDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					RtAvgDatas.push(parseFloat((list[i].avg/60).toFixed(1)));
					for (var j = 0; j < Brands.length; j++) {
						if(Brands[j].groupId == list[i].gId){
							nameArray.push(Brands[j].name);
						}
					}
				}
				doBarChart(nameArray,RtAvgDatas,"storey_residence","分钟");
				//doBarChart(["海底捞","金鼎轩"],RtAvgDatas,"storey_residence");
			}
		}
	}
	function loadCompareRealtimeSummBar () {
		/*var url = "http://192.168.0.81:8080/timeDistribution/entireStayLt5Gt5/3,5/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeSummBarCallback
			});*/
		if(Brands != null&&Brands.length>0){
			var gids = new Array();
			for (var i = 0; i < Brands.length; i++) {
				gids.push(Brands[i].groupId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/entireStayLt5Gt5/"+gids+"/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeSummBarCallback
			});
		}else{
			$("#storey_sum").html("未查询到数据");
		}
	}
	function compareRealtimeSummBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					var suma = a.gt5 + a.lt5;
					var sumb = b.gt5 + b.lt5;
					return suma > sumb ? -1:1;
				});
				var nameArray = new Array();
				CRtltDatas = new Array();
				CRtgtDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					CRtltDatas.push(list[i].lt5);
					CRtgtDatas.push(list[i].gt5);
					for (var j = 0; j < Brands.length; j++) {
						if(Brands[j].groupId == list[i].gId){
							nameArray.push(Brands[j].name);
						}
					}
				}
				doStackPileBarChart(nameArray,RtgtDatas,RtltDatas,CRtgtDatas,CRtltDatas,"storey_sum");
				//doStackPileBarChart(["海底捞","金鼎轩"],RtgtDatas,RtltDatas,CRtgtDatas,CRtltDatas,"storey_sum");
			}
		}
	}
	function loadCompareRealtimeResidenceBar () {
		/*var url = "http://192.168.0.81:8080/timeDistribution/entireAVGs/3,5/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeResidenceBarCallback
			});*/
		if(Brands != null&&Brands.length>0){
			var gids = new Array();
			for (var i = 0; i < Brands.length; i++) {
				gids.push(Brands[i].groupId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/entireAVGs/"+gids+"/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeResidenceBarCallback
			});
		}else{
			$("#storey_residence").html("未查询到数据");
		}
	}
	function compareRealtimeResidenceBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					return a.avg > b.avg ? -1:1;
				});
				var nameArray = new Array();
				CRtAvgDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					CRtAvgDatas.push(parseFloat((list[i].avg/60).toFixed(1)));
					for (var j = 0; j < Brands.length; j++) {
						if(Brands[j].groupId == list[i].gId){
							nameArray.push(Brands[j].name);
						}
					}
				}
				doCompareBarChart(nameArray,RtAvgDatas,CRtAvgDatas,"storey_residence","分钟");
				//doCompareBarChart(["海底捞","金鼎轩"],RtAvgDatas,CRtAvgDatas,"storey_residence");
			}
		}
	}
	function loadBrandSummBar () {
		var url = basePath + "jaxrs/brand/summ/indoor/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadBrandSummBarCallBack,"json");
	}
	function loadBrandSummBarCallBack (data) {
		var code = data.code;
		console.log(code);
		if(code == 0){
			var brands = data.brands;
			var datas = data.datas;
			var lt5Datas = data.lt5Datas;
			var brandNames = new Array();
			if(brands.length > 0){
				for (var i = 0; i < brands.length; i++) {
					brandNames.push(brands[i].name);
				}
				doPileBarChart(brandNames,datas,lt5Datas,"storey_sum");
			}else{
				$("#storey_sum").html("未查询到数据");
			}
		}else{
			$("#storey_sum").html("未查询到数据");
		}
	}
	function loadBrandResidenceBar () {
		var url = basePath + "jaxrs/brand/summ/residence/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadBrandResidenceBarCallBack,"json");
	}
	function loadBrandResidenceBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var brands = data.brands;
			var datas = data.datas;
			for (var i = 0; i < datas.length; i++) {
				datas[i] = parseFloat((datas[i]/60).toFixed(1));
			}
			var brandNames = new Array();
			if(brands.length > 0){
				for (var i = 0; i < brands.length; i++) {
					brandNames.push(brands[i].name);
				}
				doBarChart(brandNames,datas,"storey_residence","分钟");
			}else{
				$("#storey_residence").html("未查询到数据");
			}
		}else{
			$("#storey_residence").html("未查询到数据");
		}
	}
	function loadCompareBrandSummBar () {
		var url = basePath + "jaxrs/brand/summ/indoor/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadCompareBrandSummBarCallBack,"json");
	}
	function loadCompareBrandSummBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var brands = data.brands;
			var datas = data.datas;
			var compareDatas = data.compareDatas;
			var lt5Datas = data.lt5Datas;
			var compareLt5Datas = data.compareLt5Datas;
			var brandNames = new Array();
			if(brands.length > 0){
				for (var i = 0; i < brands.length; i++) {
					brandNames.push(brands[i].name);
				}
				doStackPileBarChart(brandNames,datas,lt5Datas,compareDatas,compareLt5Datas,"storey_sum");
			}else{
				$("#storey_sum").html("未查询到数据");
			}
		}else{
			$("#storey_sum").html("未查询到数据");
		}
	}
	function loadCompareBrandResidenceBar () {
		var url = basePath + "jaxrs/brand/summ/residence/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadCompareBrandResidenceBarCallBack,"json");
	}
	function loadCompareBrandResidenceBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var brands = data.brands;
			var datas = data.datas;
			for (var i = 0; i < datas.length; i++) {
				datas[i] = parseFloat((datas[i]/60).toFixed(1));
			}
			var compareDatas = data.compareDatas;
			for (var i = 0; i < compareDatas.length; i++) {
				compareDatas[i] = parseFloat((compareDatas[i]/60).toFixed(1));
			}
			var brandNames = new Array();
			if(brands.length > 0){
				for (var i = 0; i < brands.length; i++) {
					brandNames.push(brands[i].name);
				}
				doCompareBarChart(brandNames,datas,compareDatas,"storey_residence","分钟");
			}else{
				$("#storey_residence").html("未查询到数据");
			}
		}else{
			$("#storey_residence").html("未查询到数据");
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
		        credits: {
				    enabled: false
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
	function doStackPileBarChart (xList,yList1,yList2,yList3,yList4,divId) {
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
		        credits: {
				    enabled: false
				},
		        plotOptions: {
		            series: {
		                stacking: 'normal'
		            }
		        },
		            series: [{
		            name: '所选时段停留5分钟以上',
		            data: yList1,
		            stack: 2
		        }, {
		            name: '所选时段停留5分钟以下或未停留',
		            data: yList2,
		            stack: 2
		        }, {
		            name: '对比时段停留5分钟以上',
		            data: yList3,
		            stack: 1
		        }, {
		            name: '对比时段停留5分钟以下或未停留',
		            data: yList4,
		            stack: 1
		        }]
			});
		}else{
			$("#"+divId).html("未查询到数据");
		}
	}
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
			<li><a href="entirety_inside.jsp">整体客流</a></li>
			<li><a href="storey_summ.jsp">楼层客流</a></li>
			<li><a href="business_summ.jsp">业态客流</a></li>
			<li><a class="select" href="#">品牌店铺</a></li>
			<li><a href="custom_summ.jsp">顾客模型</a></li>
			<!-- <li><a href="character_date.jsp">特征日模型</a></li> -->
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a class="select" href="#">重点品牌概况</a></li>
					<li><a href="brand_inside.jsp">品牌经过顾客</a></li>
					<li><a href="brand_stay.jsp">品牌停留顾客</a></li>
					<li><a href="brand_residence.jsp">品牌停留时长</a></li>
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
					<div class="dataDiv_title_txt">各品牌客流概况</div>
				</div>
				<div class="dataDiv_content" id="storey_sum"></div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">平均停留时长</div>
				</div>
				<div class="dataDiv_content" id="storey_residence"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>