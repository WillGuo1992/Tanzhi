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
<link type="text/css" rel="stylesheet" href="../libs/map.css" />
<script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="../js/highcharts.js"></script>
<script type="text/javascript" src="../js/highcharts.src.js"></script>
<script type="text/javascript" src="../libs/heatmap.min.js"></script>
<script type="text/javascript" src="../libs/map.js"></script>
<script type="text/javascript" src="../libs/zepto.min.js"></script>
<script type="text/javascript" src="../DZMSN/data/F1.js"></script>
<script type="text/javascript" src="../DZMSN/data/F2.js"></script>
<script type="text/javascript" src="../DZMSN/data/FB1.js"></script>
<script type="text/javascript" src="../SHSN/data/F1.js"></script>
<script type="text/javascript" src="../SHSN/data/F2.js"></script>
<script type="text/javascript" src="../SHSN/data/F3.js"></script>
<script type="text/javascript" src="../SHSN/data/FB1.js"></script>
<!-- <script type="text/javascript" src="../data/F1.js"></script>
<script type="text/javascript" src="../data/F2.js"></script>
<script type="text/javascript" src="../data/F3.js"></script>
<script type="text/javascript" src="../data/FB1.js"></script> -->
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
	var DateTab = 0;
	var StoreyTab = 0;
	var BusinessTab = 0;
	var RateTab = 0;
	var interval;
	var running = 0;
	var CurFloor;
	var CurFloorGid;
	var CurMapId;//当前选中楼层对应的refer_map的id
	var FloorCfgs;
	var map;
	var tempData = [];
	var MapCenter = [];
	var testData = {
		max: 16,
		data: tempData
	}
	var cfg = {
		"radius": 2,
		"maxOpacity": .8, 
		"scaleRadius": true, 
		"useLocalExtrema": true,
		latField: 'lat',
		lngField: 'lng',
		valueField: 'count'
	}
	var heatmapLayer = new HeatmapOverlay(cfg);
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
		document.getElementById("new_start").innerHTML=getDateAgo(1);
		StartDate = getDateAgo(1);
		getStoreyConfigs();
		
	});

	function initMap () {
		console.log(FloorCfgs);
		map = indoorMap.map("hotmap_chart", {
			mapCfg : {
				minZoom : 17,
				maxZoom : 20,
				errorTileUrl : "../libs/images/error.jpg",
				zoomControl : true,
				enableClickHighlight : false
				/*poiDefaultStyle : {
				    stroke:false
				}*/
			},
			handlersCfg : {
				areaClickedHandler : areaClickedHandler
			},
			showFloor : {
				id : CurFloor,
				center : MapCenter,
				zoom : 18
			},
			floorsCfg : FloorCfgs,
			routeCfg : {
				"startMarker" : {
					url : "../libs/images/marker.png",
					size : [16, 16],
					anchor : [8, 8]
				},
				"endMarker" : {
					url : "../libs/images/marker.png",
					size : [16, 16],
					anchor : [8, 8]
				},
				"cornerMarker" : {
					url : "../libs/images/marker2.png",
					size : [16, 16],
					anchor : [8, 8]
				}
			}
		});
		$(".leaflet-control-attribution").hide();
		$(".leaflet-control-zoom").css("width","26px");
		$(".leaflet-control-zoom").css("height","54px");
		/*var polygons = indoorMap.getFloorPolygons();
		for (var i = 0; i < polygons.length; i++) {
			indoorMap.setPolygonHighlightStyle(polygons[i], {stroke:true,fillColor:"#FD3224",fillOpacity:0.5});
		}*/
		
		//loadHotMap(0);
		//addHotmap();
	}

	function areaClickedHandler(data) {

		console.log(data);
	}
	function getStoreyConfigs () {
		var url = basePath + "jaxrs/map/config";
		var cxl = Math.random();
		var para = {
				mallId : MallId,cxl : cxl
		}
		$.post(url,para,getStoreyConfigsCallBack,"json");
	}
	function getStoreyConfigsCallBack (data) {
		var code = data.code;
		if(code == 0){
			FloorCfgs = data.result;
			MapCenter.push(data.cplat);
			MapCenter.push(data.cplon);
			getStoreyList();
		}
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
				$("#storeys").html("");
				/*var oHead = document.getElementsByTagName('head')[0]; 
				var firstChild = oHead.firstChild;*/
				for (var i = 0; i < storeys.length; i++) {
					if(i==1)
						continue;
					/*var oScript= document.createElement("script"); 
    				oScript.type = "text/javascript";
    				oScript.src="../data/F"+storeys[i].id+".js";
    				oHead.insertBefore(oScript,firstChild); */
					$("#storeys").append("<div id='"+storeys[i].floorId+"' class='tabs_' onclick='changeStorey(this.id,"+storeys[i].groupId+","+storeys[i].referMapId+")'>"+storeys[i].name+"</div>");
				}
				//console.log(oHead);
				$("#"+storeys[0].floorId).addClass("select_");
				CurFloor = storeys[0].floorId;
				CurFloorGid = storeys[0].groupId;
				CurMapId = storeys[0].referMapId;
				initMap();
			}
		}
		//getStoreyConfigs();
	}
	function changeStorey (id,gid,mid) {
		$("#"+id).addClass("select_");
		$("#"+id).siblings("div").removeClass("select_");
		CurFloor = id;
		CurFloorGid = gid;
		CurMapId = mid;
		indoorMap.setFloor(CurFloor, map);
	}
	function getStoreyPoi (sid) {
		var url = basePath + "jaxrs/map/poi";
		var cxl = Math.random();
		var para = {
				storeyId : sid,cxl : cxl
		}
		$.post(url,para,getStoreyPoiCallBack,"json");
	}
	function getStoreyPoiCallBack (data) {
		var code = data.code;
		if(code == 0){
			var st_id = data.storeyid;
			var st_pois = data.result;
			eval("var f"+st_id+"data = "+st_pois);
		}
	}

	function startTimer () {
		if(running == 0){
			running = 1;
			if(parseInt($("#sub_bar").css("width").split("px")[0]) == 617){
				$("#sub_bar").css("width","7px");
			}
			$("#start_timer").css("background","url(../img/jingling2.png) -30px -34px");
			interval = window.setInterval("timer_execute()",1000);
		}else if(running == 1){
			running = 0; 
			$("#start_timer").css("background","url(../img/jingling2.png) -30px -66px");
			window.clearInterval(interval);
		}
	}
	function terminateTimer () {
		$("#sub_bar").css("width","7px");
		$("#start_timer").css("background","url(../img/jingling2.png) -30px -66px");
		running = 0;
		window.clearInterval(interval);
	}
	function timer_execute () {
		$("#sub_bar").css("width",parseInt($("#sub_bar").css("width").split("px")[0])+10+"px");
		if(parseInt($("#sub_bar").css("width").split("px")[0]) == 617){
			window.clearInterval(interval);
			$("#start_timer").css("background","url(../img/jingling2.png) -30px -66px");
			running = 0;
		}else{
			if((parseInt($("#sub_bar").css("width").split("px")[0])-17)%50 == 0){
				var time = (parseInt($("#sub_bar").css("width").split("px")[0])-17)/50;
				loadHotMap(time);
				console.log(2*time);
			}
		}
		/*document.getElementById("sub_bar").style.width = parseInt(document.getElementById("sub_bar").style.width) + 1 +"%";
		if( document.getElementById("sub_bar").style.width == "100%"){
			window.clearInterval(interval);
		}else{
			if(document.getElementById("sub_bar").style.width%(1/6) == 0){
				console.log(document.getElementById("sub_bar").style.width/(1/6));
			}
		}*/
	}
	function loadHotMap (time) {
		map.removeLayer(heatmapLayer);
		//var gid = 3;
		var url = "http://47.93.123.129:8080/chart/thermodynamic/"+CurFloorGid+"/"+CurMapId+"/"+StartDate+" "+2*time+"/"+StartDate+" "+(2*time+1);
		console.log(url);
		$.ajax({
			url:url,
			dataType:"json",
			async:false,
			success:loadHotMapCallback
		});
	}
	function loadHotMapCallback (data) {
		var infos = data.d.contents[0];
		tempData = [];
		if(infos.length>0){
			for (var i = 0; i < infos.length; i++) {
				console.log(infos[i].count);
				tempData.push(infos[i]);
			}
		}
		testData.data = tempData;
		map.addLayer(heatmapLayer);
		console.log(testData.data);
		heatmapLayer.setData(testData);
	}
	function changeTimeTab (num,obj) {
		$(obj).addClass("select_");
		$(obj).siblings("div").removeClass("select_");
		if(num == 0){
			$("#count_tab").addClass("select_");
			$("#rate_tab").removeClass("select_");
		}else{
			$("#count_tab").removeClass("select_");
			$("#rate_tab").addClass("select_");
		}
	}

	function addHotmap() {
		
		for (var i = 0, len = f1data.poi_list.length; i < len; i++) {
			tempData.push({
				lat: f1data.poi_list[i].clat,
				lng: f1data.poi_list[i].clon,
				count: Math.ceil(8 * Math.random())
			});
		}
		map.addLayer(heatmapLayer);

		heatmapLayer.setData(testData);
	}

	function removeHotmap() {
		map.removeLayer(heatmapLayer);
	}
	function new_execute () {
		StartDate = $("#new_start").html();
		terminateTimer();
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
<jsp:include page="../jsp/summaries.jsp" />
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
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a class="select" href="#">热力图</a></li>
			<li id="edit_li"><a href="mallinfo.jsp">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div class="notitle_tool_bar">
				<div id="date_tool" style="margin-left:30px;" class="date_tool"><jsp:include page="../jsp/new_date.jsp" /></div>
				<div id="storeys" class="tabs_bar" style="margin-left:10px;">
					<div onclick="changeCharacterDate(0,this)" class="tabs_ select_">F1</div>
					<div onclick="changeCharacterDate(1,this)" class="tabs_">F2</div>
					<div onclick="changeCharacterDate(2,this)" class="tabs_">F3</div>
					<div onclick="changeCharacterDate(3,this)" class="tabs_">F4</div>
				</div>
			</div>
			<div class="dataDiv" style="height:640px;">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">楼层热力图</div>
					<!-- <div class="tabs_bar_title">
						<div onclick="changeTimeTab(0,this)" class="tabs_title_ select_">当日动态</div>
					</div> -->
					<div style="float:right;margin-right:20px;padding:5px 0;">
						<div style="width:60px;height:30px;float:right;background-color:#FD3224;"></div>
						<div style="width:60px;height:30px;float:right;text-align:center;line-height:30px;">拥挤:</div>
						<div style="width:60px;height:30px;float:right;background-color:#FFAD00;"></div>
						<div style="width:60px;height:30px;float:right;text-align:center;line-height:30px;">一般:</div>
						<div style="width:60px;height:30px;float:right;background-color:#9BC702;"></div>
						<div style="width:60px;height:30px;float:right;text-align:center;line-height:30px;">稀少:</div>
					</div>
				</div>
				<div class="dataDiv_content" style="height:540px;" id="hotmap_chart">
				</div>
				<div style="width:100%;height:60px;text-align:center;line-height:40px;padding:5px 0;border-top:solid 1px #BCBABB">
					<div style="width:720px;height:40px;margin-left:80px;float:left;">
						<div class="time_deg">
							<div class="deg">00:00</div>
							<div class="deg">04:00</div>
							<div class="deg">08:00</div>
							<div class="deg">12:00</div>
							<div class="deg">16:00</div>
							<div class="deg">20:00</div>
							<div class="deg">24:00</div>
						</div>
						<div class="time_bar_div">
							<div class="deg_line"></div>
							<div class="deg_line"></div>
							<div class="deg_line"></div>
							<div class="deg_line"></div>
							<div class="deg_line"></div>
							<div class="deg_line" style="border-right:solid 1px #BCBABB"></div>
						</div>
						<div class="time_bar">
							<div id="sub_bar" class="time_bar_sub" style="width:7px;"></div>
						</div>
					</div>
					<div id="start_timer" style="width:13px;height:13px;float:left;margin-top:20px;background:url(../img/jingling2.png) -30px -66px;cursor:pointer;" onclick="startTimer()"></div>
					<div id="terminate_timer" style="width:13px;height:13px;float:left;margin-left:20px;margin-top:20px;background:url(../img/terminate.png) -56px -52px;cursor:pointer;" onclick="terminateTimer()"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>