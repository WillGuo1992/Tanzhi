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
	var ChosenSumGroupId = 0;
	var ChosenAvgGroupId = 0;
	var IsCompare = 0;
	var TimeTab = 0;//时间标签选择变量，0:今日,1:昨日,2:历史时段
	var RtltDatas = new Array();
	var RtgtDatas = new Array();
	var CRtltDatas = new Array();
	var CRtgtDatas = new Array();
	var RtAvgDatas = new Array();
	var CRtAvgDatas = new Array();
	var SumBusinessList = new Array();
	var AvgBusinessList = new Array();
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
		$("#time_a").removeAttr("onclick");
		getStoreyList();
		changeToolBarTab(0,$("#a_today"));
		//execute();
	});
	function getStoreyList () {
		var url = basePath + "jaxrs/storey/list";
		var cxl = Math.random();
		var para = {
				mallId : MallId,cxl : cxl
		}
		$.post(url,para,getStoreyListCallBack,"json");
	}
	function getStoreyListCallBack (data) {
		var code = data.code;
		if(code == 0){
			var storeys = data.storeys;
			if(storeys.length > 0){
				ChosenSumGroupId = storeys[0].groupId;
				ChosenAvgGroupId = storeys[0].groupId;
				$("#sum_storeys_list").html("");
				$("#avg_storeys_list").html("");
				$("#sum_storeys_list").append("<div onclick='chooseSumStoreyTab("+GroupId+",this)' id='suma_"+GroupId+"' class='tabs_ select_'>全部</div>");
				$("#avg_storeys_list").append("<div onclick='chooseAvgStoreyTab("+GroupId+",this)' id='avga_"+GroupId+"' class='tabs_ select_'>全部</div>");
				for (var i = 0; i < storeys.length; i++) {
					$("#sum_storeys_list").append("<div onclick='chooseSumStoreyTab("+storeys[i].groupId+",this)' id='suma_"+storeys[i].groupId+"' class='tabs_'>"+storeys[i].name+"</div>");
					$("#avg_storeys_list").append("<div onclick='chooseAvgStoreyTab("+storeys[i].groupId+",this)' id='avga_"+storeys[i].groupId+"' class='tabs_'>"+storeys[i].name+"</div>");
				}
				chooseSumStoreyTab(GroupId,$("#suma_"+GroupId));
				chooseAvgStoreyTab(GroupId,$("#avga_"+GroupId));
			}
		}
	}
	function chooseSumStoreyTab (gid,obj) {
		ChosenSumGroupId = gid;
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		getSumBusinessList();
	}
	function chooseAvgStoreyTab (gid,obj) {
		ChosenAvgGroupId = gid;
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		getAvgBusinessList();
	}

	function getSumBusinessList () {
		var url = basePath + "jaxrs/business/list/byid";
		var cxl = Math.random();
		var para = {
				groupId : ChosenSumGroupId,cxl : cxl
		}
		$.post(url,para,getSumBusinessListCallBack,"json");
	}
	function getSumBusinessListCallBack (data) {
		var code = data.code;
		if(code == 0){
			SumBusinessList = data.info;
			//changeToolBarTab(0,$("#a_today"));
			if(IsCompare == 0){
				execute();
			}else{
				compare_execute();
			}
		}else{
			$("#business_summ").html("未查询到数据");
		}
	}
	function getAvgBusinessList () {
		var url = basePath + "jaxrs/business/list/byid";
		var cxl = Math.random();
		var para = {
				groupId : ChosenAvgGroupId,cxl : cxl
		}
		$.post(url,para,getAvgBusinessListCallBack,"json");
	}
	function getAvgBusinessListCallBack (data) {
		var code = data.code;
		if(code == 0){
			AvgBusinessList = data.info;
			//changeToolBarTab(0,$("#a_today"));
			if(IsCompare == 0){
				execute();
			}else{
				compare_execute();
			}
		}else{
			$("#business_summ").html("未查询到数据");
		}
	}

	function execute () {
		$("#business_summ").html("<img src='../img/big_load.gif' />");
		$("#business_residence").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		console.log("start:"+StartDate);
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
// 		if(TimeTab == 0){
// 			loadRealtimeSummBar();
// 			loadRealtimeAvgBar();
// 		}else{
			loadSummBar();
			loadAvgBar();
// 		}
	}
	function compare_execute () {
		$("#business_summ").html("<img src='../img/big_load.gif' />");
		$("#business_residence").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		CompareDate = $("#c_start").html();
// 		if(TimeTab == 0){
// 			loadCompareRealtimeSummBar();
// 			loadCompareRealtimeAvgBar();
// 		}else{
			loadCompareSummBar();
			loadCompareAvgBar();
// 		}
	}
	function loadRealtimeSummBar () {
		/*var url = "http://192.168.0.81:8080/timeDistribution/businessSUM/1/"+StartDate;
		$.ajax({
			url:url,
			dataType:"json",
			success: realtimeSummBarCallback
		});*/
		if(SumBusinessList != null&&SumBusinessList.length>0){
			var bids = new Array();
			for (var i = 0; i < SumBusinessList.length; i++) {
				bids.push(SumBusinessList[i].bId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/businessSUM/"+bids+"/"+StartDate;
			console.log(url);
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeSummBarCallback
			});
		}else{
			$("#business_summ").html("未查询到数据");
		}
	}

	function realtimeSummBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			console.log(list);
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					var suma = a.data[0]+a.data[1];
					var sumb = b.data[0]+b.data[1];
					return suma > sumb ? -1:1;
				});
				var nameArray = new Array();
				RtltDatas = new Array();
				RtgtDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					RtltDatas.push(list[i].data[0]);
					RtgtDatas.push(list[i].data[1]);
					for (var j = 0; j < SumBusinessList.length; j++) {
						if(SumBusinessList[j].bId == list[i].bId){
							nameArray.push(SumBusinessList[j].bName);
						}
					}
				}
				doBuPileBarChart(nameArray,RtgtDatas,RtltDatas,"business_summ");
				//doPileBarChart(["餐饮"],RtgtDatas,RtltDatas,"business_summ");
			}
		}
	}

	function loadRealtimeAvgBar () {
		/*var url = "http://192.168.0.81:8080/timeDistribution/businessAVG/1/"+StartDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeAvgBarCallback
			});*/
		if(AvgBusinessList != null&&AvgBusinessList.length>0){
			var bids = new Array();
			for (var i = 0; i < AvgBusinessList.length; i++) {
				bids.push(AvgBusinessList[i].bId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/businessAVG/"+bids+"/"+StartDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: realtimeAvgBarCallback
			});
		}else{
			$("#business_residence").html("未查询到数据");
		}
	}
	function realtimeAvgBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					return a.data > b.data ? -1:1;
				});
				var nameArray = new Array();
				RtAvgDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					RtAvgDatas.push(parseFloat((list[i].data/60).toFixed(1)));
					for (var j = 0; j < AvgBusinessList.length; j++) {
						if(AvgBusinessList[j].bId == list[i].bId){
							nameArray.push(AvgBusinessList[j].bName);
						}
					}
				}
				doBarChart(nameArray,RtAvgDatas,"business_residence","分钟");
				//doBarChart(["餐饮"],RtAvgDatas,"business_residence");
			}
		}
	}
	function loadCompareRealtimeSummBar () {
		if(SumBusinessList != null&&SumBusinessList.length>0){
			var bids = new Array();
			for (var i = 0; i < SumBusinessList.length; i++) {
				bids.push(SumBusinessList[i].bId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/businessSUM/"+bids+"/"+CompareDate;
			//var url = "http://192.168.0.81:8080/timeDistribution/businessSUM/1/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeSummBarCallback
			});
		}else{
			$("#business_summ").html("未查询到数据");
		}
	}
	function compareRealtimeSummBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					var suma = a.data[0]+a.data[1];
					var sumb = b.data[0]+b.data[1];
					return suma > sumb ? -1:1;
				});
				var nameArray = new Array();
				CRtltDatas = new Array();
				CRtgtDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					CRtltDatas.push(list[i].data[0]);
					CRtgtDatas.push(list[i].data[1]);
					for (var j = 0; j < SumBusinessList.length; j++) {
						if(SumBusinessList[j].bId == list[i].bId){
							nameArray.push(SumBusinessList[j].bName);
						}
					}
				}
				doStackPileBarChart(nameArray,RtgtDatas,RtltDatas,CRtgtDatas,CRtltDatas,"business_summ");
			}
		}
	}
	function loadCompareRealtimeAvgBar () {
		if(AvgBusinessList != null&&AvgBusinessList.length>0){
			var bids = new Array();
			for (var i = 0; i < AvgBusinessList.length; i++) {
				bids.push(AvgBusinessList[i].bId);
			}
			var url = "http://47.93.123.129:8080/timeDistribution/businessAVG/"+bids+"/"+CompareDate;
			//var url = "http://192.168.0.81:8080/timeDistribution/businessAVG/1/"+CompareDate;
			$.ajax({
				url:url,
				dataType:"json",
				success: compareRealtimeAvgBarCallback
			});
		}else{
			$("#business_residence").html("未查询到数据");
		}
		
	}
	function compareRealtimeAvgBarCallback (data) {
		var code = data.c;
		if(code == 0){
			var list = data.d.contents;
			if(list != null&&list.length > 0){
				list.sort(function(a,b){
					return a.data > b.data ? -1:1;
				});
				var nameArray = new Array();
				CRtAvgDatas = new Array();
				for (var i = 0; i < list.length; i++) {
					CRtAvgDatas.push(parseFloat((list[i].data/60).toFixed(1)));
					for (var j = 0; j < AvgBusinessList.length; j++) {
						if(AvgBusinessList[j].bId == list[i].bId){
							nameArray.push(AvgBusinessList[j].bName);
						}
					}
				}
				doCompareBarChart(nameArray,RtAvgDatas,CRtAvgDatas,"business_residence","分钟");
			}
		}
	}
	function loadSummBar () {
		var url = basePath + "jaxrs/business/summ/indoor/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,groupId : ChosenSumGroupId,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadSummBarCallBack,"json");
	}
	function loadSummBarCallBack (data) {
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
					lt10.push(datas[i].lt10);
					doBuPileBarChart(businesses,gt10,lt10,"business_summ");
				}
			}
		}else{
			$("#business_summ").html("未查询到数据");
		}
	}
	function loadAvgBar () {
		var url = basePath + "jaxrs/business/summ/residence/nocompare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,mallId : MallId,groupId : ChosenAvgGroupId,cxl : cxl
		}
		$.post(url,para,loadAvgBarCallBack,"json");
	}
	function loadAvgBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var names = data.names;
			var datas = data.datas;
			for (var i = 0; i < datas.length; i++) {
				datas[i] = parseFloat((datas[i]/60).toFixed(1));
			}
			doBarChart(names,datas,"business_residence","分钟");
		}else{
			$("#business_residence").html("未查询到数据");
		}
	}
	function loadCompareSummBar () {
		var url = basePath + "jaxrs/business/summ/indoor/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : ChosenSumGroupId,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadCompareSummBarCallBack,"json");
	}
	function loadCompareSummBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var datas = data.datas;
			if(datas != null&&datas.length > 0){
				var businesses = new Array();
				var gt10 = new Array();
				var lt10 = new Array();
				var com_gt10 = new Array();
				var com_lt10 = new Array();
				for (var i = 0; i < datas.length; i++) {
					businesses.push(datas[i].name);
					gt10.push(datas[i].gt10);
					lt10.push(datas[i].lt10);
					com_gt10.push(datas[i].com_gt10);
					com_lt10.push(datas[i].com_lt10);
					doStackPileBarChart(businesses,gt10,lt10,com_gt10,com_lt10,"business_summ");
				}
			}
		}else{
			$("#business_summ").html("未查询到数据");
		}
	}
	function loadCompareAvgBar () {
		var url = basePath + "jaxrs/business/summ/residence/compare/bar";
		var cxl = Math.random();
		var para = {
				startDate : StartDate,endDate : EndDate,compareDate : CompareDate,groupId : ChosenAvgGroupId,mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadCompareAvgBarCallBack,"json");
	}
	function loadCompareAvgBarCallBack (data) {
		var code = data.code;
		if(code == 0){
			var names = data.names;
			var datas = data.datas;
			for (var i = 0; i < datas.length; i++) {
				datas[i] = parseFloat((datas[i]/60).toFixed(1));
			}
			var compareDatas = data.compareDatas;
			for (var i = 0; i < compareDatas.length; i++) {
				compareDatas[i] = parseFloat((compareDatas[i]/60).toFixed(1));
			}
			var typeNames = new Array();
			if(names.length > 0){
				doCompareBarChart(names,datas,compareDatas,"business_residence","分钟");
			}else{
				$("#business_residence").html("未查询到数据");
			}
		}else{
			$("#business_residence").html("未查询到数据");
		}
	}
	function doBuPileBarChart (xList,yList1,yList2,divId) {
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
		            name: '停留10分钟以上',
		            data: yList1
		        }, {
		            name: '10分钟以下或未停留',
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
		            name: '所选时段停留10分钟以上',
		            data: yList1,
		            stack: 2
		        }, {
		            name: '所选时段停留10分钟以下或未停留',
		            data: yList2,
		            stack: 2
		        }, {
		            name: '对比时段停留10分钟以上',
		            data: yList3,
		            stack: 1
		        }, {
		            name: '对比时段停留10分钟以下或未停留',
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
			<li><a class="select" href="#">业态客流</a></li>
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
					<li><a class="select" href="#">业态概况</a></li>
					<li><a href="business_inside.jsp">业态经过顾客</a></li>
					<li><a href="business_stay.jsp">业态停留顾客</a></li>
					<li><a href="business_residence.jsp">业态停留时长</a></li>
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
					<div class="dataDiv_title_txt" style="width:200px;">各业态客流概况</div>
					
					<div id="sum_storeys_list" class="tabs_bar" style="margin-left:10px;margin-top:2px;">
						<div onclick="" class="tabs_ select_">F1</div>
						<div onclick="" class="tabs_">F2</div>
						<div onclick="" class="tabs_">F3</div>
						<div onclick="" class="tabs_">F4</div>
					</div>
				</div>
				<div class="dataDiv_content" id="business_summ"></div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt" style="width:200px;">各业态平均停留时长</div>
					<!-- <ul class="data_small_tab_ul" id="avg_storeys_list">
						<li><a class="select" href="#" onclick="">F1</a></li>
						<li><a href="#" onclick="">F2</a></li>
						<li><a href="#" onclick="">F3</a></li>
					</ul> -->
					<div id="avg_storeys_list" class="tabs_bar" style="margin-left:10px;margin-top:2px;">
						<div onclick="" class="tabs_ select_">F1</div>
						<div onclick="" class="tabs_">F2</div>
						<div onclick="" class="tabs_">F3</div>
						<div onclick="" class="tabs_">F4</div>
					</div>
				</div>
				<div class="dataDiv_content" id="business_residence"></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>