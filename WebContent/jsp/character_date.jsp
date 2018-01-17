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
	var IsCompare = 0;
	var StartDate;
	var EndDate;
	var CompareDate;
	var DateTab = 2;
	var StoreyTab = 0;
	var BusinessTab = 0;
	var ChosenBId;
	var Times = ["00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"];//小时时段
	var DataList = new Array();
	var CDataList = new Array();
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
		document.getElementById("start").innerHTML=getDateAgo(2);
		document.getElementById("middle").innerHTML="~";
		document.getElementById("end").innerHTML=getDateAgo(1);
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
		StoreyTab = GroupId;
		getStoreyList();
		getBusinessTypeList();
		getChosenBusinessId();
	});
	function execute () {
		$("#character_chart").html("<img src='../img/big_load.gif' />");
		StartDate = $("#start").html();
		EndDate = $("#end").html();
		$("#c_start").html("请选择日期");
		var url = "";
		if(BusinessTab == 0||BusinessTab == 1){
			url = "http://47.93.123.129:8080/view/characteristic/"+StoreyTab+"/"+StartDate+"/"+EndDate+"/"+DateTab+"?kType=GROUP";
		}else{
			url = "http://47.93.123.129:8080/view/characteristic/"+ChosenBId+"/"+StartDate+"/"+EndDate+"/"+DateTab+"?kType=BUSINESS";
		}
		console.log(url);
		$.ajax({
			url:url,
			dataType:"json",
			success: characterCallBack
		});
	}
	function characterCallBack (data) {
		var code = data.c;
		if(code == 0&&data.d != null){
			DataList = data.d.data;
			doLineChart(Times,DataList,"character_chart");
		}else{
			$("#character_chart").html("未查询到数据");
		}
	}
	function compare_execute () {
		
	}
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
				$("#storeys_list").html("");
				$("#storeys_list").append("<div onclick='changeStoreyTab(0,this)'' id='a_"+GroupId+"' class='tabs_ select_'>全部</div>");
				for (var i = 0; i < storeys.length; i++) {
					$("#storeys_list").append("<div id='a_"+storeys[i].groupId+"' class='tabs_' onclick='changeStoreyTab("+storeys[i].groupId+",this)'>"+storeys[i].name+"</div>");
				}
			}
		}
	}
	function getBusinessTypeList () {
		var url = basePath + "jaxrs/business/type/list";
		var cxl = Math.random();
		var para = {
				groupId : GroupId,cxl : cxl
		}
		$.post(url,para,getBusinessTypeListCallBack,"json");
	}
	function getBusinessTypeListCallBack (data) {
		var code = data.code;
		if(code == 0){
			var types = data.types;
			if(types.length > 0){
				$("#business_list").html("");
				$("#business_list").append("<div onclick='changeBusinessTab("+GroupId+",this)'' id='b_0' class='tabs_ select_'>全部</div>");
				for (var i = 0; i < types.length; i++) {
					$("#business_list").append("<div id='b_"+types[i].bType+"' class='tabs_' onclick='changeBusinessTab("+types[i].bType+",this)'>"+types[i].bName+"</div>");
				}
			}
		}
	}
	function changeCharacterDate (num,obj) {
		DateTab = num;
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		execute();
	}
	function changeStoreyTab (num,obj) {
		StoreyTab = num;
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		if(BusinessTab == 0||BusinessTab == "0"){
			if(IsCompare == 0){
				execute();
			}else{
				compare_execute();
			}
		}else{
			getChosenBusinessId();
		}
	}
	function changeBusinessTab (num,obj) {
		BusinessTab = num;
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		if(BusinessTab == 0||BusinessTab == "0"){
			if(IsCompare == 0){
				execute();
			}else{
				compare_execute();
			}
		}else{
			getChosenBusinessId();
		}
	}
	function getChosenBusinessId () {
		var url = basePath + "jaxrs/business/getbId";
		var cxl = Math.random();
		var para = {
				groupId : StoreyTab,businessType : BusinessTab,cxl : cxl
		}
		$.post(url,para,getChosenBusinessIdCallBack,"json");
	}
	function getChosenBusinessIdCallBack (data) {
		var code = data.code;
		if(code == 0){
			ChosenBId = data.info;
		}
		if(IsCompare == 0){
			execute();
		}else{
			compare_execute();
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
		            	step : gap
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
		            	step : gap
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
	/*function changeRateTab (num,obj) {
		RateTab = num;
		$(obj).addClass("select_");
		$(obj).siblings().removeClass("select_");
	}*/
	
</script>
</head>
<body onload="">
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
			<li><a class="select" href="#">特征日模型</a></li>
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div class="tool_div">
				<div class="tool_div_row">
					<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
					<div class="tabs_bar">
						<div onclick="changeCharacterDate(2,this)" class="tabs_ select_" style="width:50px;">周一</div>
						<div onclick="changeCharacterDate(3,this)" class="tabs_" style="width:50px;">周二</div>
						<div onclick="changeCharacterDate(4,this)" class="tabs_" style="width:50px;">周三</div>
						<div onclick="changeCharacterDate(5,this)" class="tabs_" style="width:50px;">周四</div>
						<div onclick="changeCharacterDate(6,this)" class="tabs_" style="width:50px;">周五</div>
						<div onclick="changeCharacterDate(7,this)" class="tabs_" style="width:50px;">周六</div>
						<div onclick="changeCharacterDate(1,this)" class="tabs_" style="width:50px;">周日</div>
					</div>
					<!-- <div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
					<div style="float:right;color:black;text-align:center;margin-right:10px;padding:12px 0">对比时段:</div> -->
				</div>
				<div class="tool_div_row">
					<div style="width:80px;height:47px;margin-left:20px;line-height:47px;float:left;">楼层选择:</div>
					<div id="storeys_list" class="tabs_bar">
						<div onclick="changeStoreyTab(0,this)" class="tabs_ select_">全部</div>
						<div onclick="changeStoreyTab(1,this)" class="tabs_">F1</div>
						<div onclick="changeStoreyTab(2,this)" class="tabs_">F2</div>
						<div onclick="changeStoreyTab(3,this)" class="tabs_">F3</div>
						<div onclick="changeStoreyTab(4,this)" class="tabs_">F4</div>
					</div>
				</div>
				<div class="tool_div_row">
					<div style="width:80px;height:47px;margin-left:20px;line-height:47px;float:left;">业态选择:</div>
					<div class="tabs_bar" id="business_list">
						<div onclick="changeBusinessTab(0,this)" class="tabs_ select_">全部</div>
						<div onclick="changeBusinessTab(1,this)" class="tabs_">服装</div>
						<div onclick="changeBusinessTab(2,this)" class="tabs_">餐饮</div>
						<div onclick="changeBusinessTab(3,this)" class="tabs_">生活</div>
						<div onclick="changeBusinessTab(4,this)" class="tabs_">户外</div>
					</div>
				</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">特征日客流</div>
					<!-- <div class="tabs_bar_title">
						<div onclick="changeRateTab(0,this)" class="tabs_title_ select_">到达与停留</div>
						<div onclick="changeRateTab(1,this)" class="tabs_title_">停留转化率</div>
					</div> -->
				</div>
				<div class="dataDiv_content" id="character_chart">
				</div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>