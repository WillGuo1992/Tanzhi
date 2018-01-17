<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%
	response.setHeader("Pragma","No-cache");
 	response.setHeader("Cache-Control","no-cache");
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="../libs/map.css" />
<script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="../js/jQueryRotate.js"></script>
<script type="text/javascript" src="../js/WebViewJavascriptBridge.js"></script>
<script type="text/javascript" src="../js/json.js"></script>
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
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var nowDate = new Date();
	var cur_year = nowDate.getFullYear();
	var cur_month = nowDate.getMonth() + 1 + "";
	if(cur_month.length <= 1){
		cur_month = "0" + cur_month;
	}
	var cur_date = nowDate.getDate()+"";
	if(cur_date.length <= 1){
		cur_date = "0" + cur_date;
	}
	var now_date = cur_year + "-" + cur_month + "-" + cur_date;
	var now_hour;
	//alert(nowDate.getHours());
	var RequestId = 0; 
	var currFloorId;
	var currFloorGId;
	var currMapId;
	var FloorCfgs;
	var map;
	var selectPoiData = null;
	var startPoiData = null;
	var endPoiData = null;
	var Storeys;
	var ChosenPoint;
	var endPoiData = null;
	var startPoiData = null;
	var tempData = [];
	var MapCenter = [];
	var Marker;
	var HighLightPOI = null;
	var uri = document.location.toString();
	if(uri.indexOf("?")>0){
		RequestId = uri.split("=")[1];
	}
	var hlstyle = {
		stroke:true,
		fillColor:"#E70012",
		fillOpacity:0.5
	}
	//indoorMap.setFloor(currFloorId, map);更改楼层时调用方法
	getStoreyConfigs();
	function initMap () {
		console.log(FloorCfgs);
		map = indoorMap.map("container", {
			mapCfg : {
				minZoom : 17,
				maxZoom : 20,
				errorTileUrl : "../libs/images/error.jpg",
				zoomControl : false,
				poiDefaultStyle : {stroke:false, fillColor:"white",fillOpacity:0.1},
				poiHighlightStyle : {stroke:false, fillColor:"red",fillOpacity:0.5}
			},
			handlersCfg : {
				areaClickedHandler : areaClickedHandler
			},
			showFloor : {
				id : currFloorId,
				center : MapCenter,
				zoom : 18
			},
			floorsCfg : FloorCfgs,
			routeCfg : {
				"startMarker" : {
					url : "../libs/images/qi.png",
					size : [30, 30],
					anchor : [8, 8]
				},
				"endMarker" : {
					url : "../libs/images/zhong.png",
					size : [30, 30],
					anchor : [8, 8]
				},
				"cornerMarker" : {
					url : "../libs/images/marker2.png",
					size : [16, 16],
					anchor : [8, 8]
				}
			}
			
		});
	}
	function changeFloor (fid) {
		currFloorId = fid;
		indoorMap.setFloor(currFloorId, map);
	}
	
	function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false)
		}
	}
	function mapClick (e) {
		var userAgentInfo = navigator.userAgent;
		var clickRtn = new Object();
		clickRtn.isPoi = false;
		clickRtn.result = e;
		console.log(clickRtn.result.latlng);
		if(userAgentInfo.indexOf("Android") > 0){
			androidShow.show(JSON.stringify(clickRtn));
		}else{
			connectWebViewJavascriptBridge(function(bridge) { 

			    bridge.send(clickRtn, null);
			});
		}
	}
	function areaClickedHandler(data) {
		var userAgentInfo = navigator.userAgent;
		ChosenPoint = data;
		var clickRtn = new Object();
		clickRtn.isPoi = true;
		clickRtn.result = ChosenPoint;
		console.log(clickRtn);
		if(userAgentInfo.indexOf("Android") > 0){
			androidShow.show(JSON.stringify(clickRtn));
		}else{
			connectWebViewJavascriptBridge(function(bridge) { 

		    /* Init your app here */ 

		    /*bridge.init(function(message, responseCallback) { 
		    }); */
		    //bridge.send('Hello from the javascript') 
			    bridge.send(clickRtn, null);
			});
		}
		//map.removeLayer(marker);
		selectPoiData = data;
		//$("#selectpoi").html(data.name);
	}
	function getPoiDataById (poiid) {
		var polygons = indoorMap.getFloorPolygons();
		var poi = null;
		for (var i = 0; i < polygons.length; i++) {
			if(polygons[i].floorData.id == poiid){
				poi = polygons[i].floorData;
			}
		}
		return poi;
	}
	function highlightPOI (poi) {
		var obj = new Object();
		obj.floorData = poi;
		indoorMap.setPolygonHighlightStyle(obj,hlstyle);
	}
	function highlightPoiByIdForIOS (poiid) {
		var polygons = indoorMap.getFloorPolygons();
		var poi = null;
		var resultstr = "";
		for (var i = 0; i < polygons.length; i++) {
			if(polygons[i].floorData.id == poiid){
				poi = polygons[i].floorData;
				indoorMap.setPolygonHighlightStyle(polygons[i],hlstyle);
				HighLightPOI = polygons[i];
				resultstr = indoorMap.getCurrFloorCfg().serFloor + "*" + poi.name + "*" + poi.clat + "*" + poi.clon;
				var point = new Array();
				point.push(polygons[i].floorData.clat);
				point.push(polygons[i].floorData.clon);
				map.panTo(point);
			}
		}
		console.log(resultstr);
		return resultstr;
	}
	function highlightPoiById (poiid) {
		var polygons = indoorMap.getFloorPolygons();
		var poi = null;
		for (var i = 0; i < polygons.length; i++) {
			if(polygons[i].floorData.id == poiid){
				poi = polygons[i].floorData;
				console.log(polygons[i]);
				indoorMap.setPolygonHighlightStyle(polygons[i],hlstyle);
				HighLightPOI = polygons[i];
				var point = new Array();
				point.push(polygons[i].floorData.clat);
				point.push(polygons[i].floorData.clon);
				map.panTo(point);
			}
		}
		var userAgentInfo = navigator.userAgent;
		if(userAgentInfo.indexOf("Android") > 0){
			androidShow.highlight(JSON.stringify(poi));
		}
		return poi;
	}
	function cleanLastHighLightPoi () {
		console.log("enter clean");
		if(HighLightPOI){
			console.log("start clean");
			indoorMap.setPolygonDefaultStyle(HighLightPOI);
			HighLightPOI = null;
		}
	}
	function cleanHighLightPoi () {
		var polygons = indoorMap.getFloorPolygons();
		for (var i = 0; i < polygons.length; i++) {
			indoorMap.setPolygonDefaultStyle(polygons[i]);
			/*if(polygons[i].id == poiid){
				indoorMap.setPolygonHighlightStyle(polygons[i],hlstyle);
				indoorMap.setPolygonDefaultStyle(polygons[i]);
			}*/
		}
	}
	function loadHotMapDatas () {
		var gid = currFloorGId;
		var mid = currMapId;
		var url = "http://47.93.123.129:8080/chart/thermodynamic/"+gid+"/"+mid+"/"+now_date+" "+now_hour+"/"+now_date+" "+(now_hour+1);
		//var url = "http://zanrenpin.vicp.net:8180/chart/thermodynamic/"+gid+"/"+now_date+" "+now_hour;
		$.ajax({
			url:url,
			dataType:"json",
			success: loadHotMapDatasCallback
		});
	}
	function loadHotMapDatasCallback (data) {
		var infos = data.d.contents[0];
		if(infos.length>0){
			for (var i = 0; i < infos.length; i++) {
				tempData.push(infos[i]);
			}
		}
		map.addLayer(heatmapLayer);
		heatmapLayer.setData(testData);
	}
	function getStoreyList () {
		var url = basePath + "jaxrs/storey/list";
		var cxl = Math.random();
		var para = {
				mallId : RequestId,cxl : cxl
		}
		$.post(url,para,getStoreyListCallBack,"json");
	}
	function getStoreyListCallBack (data) {
		var code = data.code;
		if(code == 0){
			var storeys = data.storeys;
			Storeys = data.storeys;
			if(storeys.length > 0){
				currFloorId = storeys[0].floorId;
				currFloorGId = storeys[0].groupId;
				currMapId = storeys[0].referMapId;
				initMap();
			}
		}
	}
	function setStorey(){
		return Storeys;
	}
	function getStoreyConfigs () {
		var url = basePath + "jaxrs/map/config";
		var cxl = Math.random();
		var para = {
				mallId : RequestId,cxl : cxl
		}
		$.post(url,para,getStoreyConfigsCallBack,"json");
	}
	function getStoreyConfigsCallBack (data) {
		var code = data.code;
		if(code == 0){
			FloorCfgs = data.result;
			MapCenter.push(parseFloat(data.cplat));
			MapCenter.push(parseFloat(data.cplon));
			getStoreyList();
		}
	}

	
	function pointInPolygon (x,y,Xary,Yary,corners) {
		var oddNodes = false;
		var constant = new Array();
		var multiple = new Array();
		var j = corners - 1;
		for (var i = 0; i < corners; i++) {
			if (Yary[j] == Yary[i]) {
				constant[i] = Xary[i];
				multiple[i] = 0;
			} else {
				constant[i] = Xary[i] - (Yary[i] * Xary[j]) / (Yary[j] - Yary[i]) + (Yary[i] * Xary[i]) / (Yary[j] - Yary[i]);
				multiple[i] = (Xary[j] - Xary[i]) / (Yary[j] - Yary[i]);
			}
			j = i;
		}
		// pointInPolygon
		for (var i = 0; i < corners; i++) {
			if ((Yary[i] < y && Yary[j] >= y) || (Yary[j] < y && Yary[i] >= y)) {
				oddNodes ^= (y * multiple[i] + constant[i] < x);
			}
			j = i;
		}
		return oddNodes;
	}
	function nearestPoi (ary) {
		ary[0] = parseFloat(ary[0]);
		ary[1] = parseFloat(ary[1]);
		var polygons = indoorMap.getFloorPolygons();
		if(polygons.length > 1){
			var flag = false;
			var point;
			for (var i = 0; i < polygons.length; i++) {
				/*var x = polygons[i].floorData.clat;
				var y = polygons[i].floorData.clon;*/
				var x = ary[0];
				var y = ary[1];
				var xAry = new Array();
				var yAry = new Array();
				var points = polygons[i].floorData.polygon;
				for (var j = 0; j < points.length; j++) {
					xAry.push(points[j].lat);
					yAry.push(points[j].lon);
				}
				if(pointInPolygon(x,y,xAry,yAry,points.length)){
					flag = true;
					point = polygons[i].floorData;
				}
			}
			if(flag){
				return point;
			}else{
				var result = polygons[0].floorData;
				var minLength = Math.sqrt((polygons[0].floorData.clat-ary[0])*(polygons[0].floorData.clat-ary[0])+(polygons[0].floorData.clon-ary[1])*(polygons[0].floorData.clon-ary[1]));
				for (var i = 0; i < polygons.length; i++) {
					if(Math.sqrt((polygons[i].floorData.clat-ary[0])*(polygons[i].floorData.clat-ary[0])+(polygons[i].floorData.clon-ary[1])*(polygons[i].floorData.clon-ary[1]))<=minLength){
						minLength = Math.sqrt((polygons[i].floorData.clat-ary[0])*(polygons[i].floorData.clat-ary[0])+(polygons[i].floorData.clon-ary[1])*(polygons[i].floorData.clon-ary[1]));
						result = polygons[i].floorData;
					}
					var corners = polygons[i].floorData.polygon;
					for (var j = 0; j < corners.length; j++) {
						if (Math.sqrt((corners[j].lat-ary[0])*(corners[j].lat-ary[0])+(corners[j].lon-ary[1])*(corners[j].lon-ary[1]))<=minLength) {
							minLength = Math.sqrt((corners[j].lat-ary[0])*(corners[j].lat-ary[0])+(corners[j].lon-ary[1])*(corners[j].lon-ary[1]));
							result = polygons[i].floorData;
						}
					}
				}
				return result;
			}
		}else{
			return polygons[0].floorData;
		}
	}
	function navi (startAry,startFloor,endAry,endFloor) {
		if(startAry && endAry){
			//var cfg = indoorMap.getCurrFloorCfg();
			startPoiData =  {
				lon : parseFloat(startAry[1]),
				lat : parseFloat(startAry[0]),
				floor : startFloor
			}
			endPoiData = {
				lon : parseFloat(endAry[1]),
				lat : parseFloat(endAry[0]),
				floor : endFloor
			}
			console.log(startPoiData);
			console.log(endPoiData);
			if(RequestId == 1||RequestId == "1"){
				indoorMap.setRoute(map,
				"http://182.92.104.216:8050/YCRouteServer/RouteServlet", 
				startPoiData,
				endPoiData,
				function(data) {
					console.log(data);
					if(data.state == "fail"){
						alert("该路径尚未收录，敬请期待");
					}
				});
			}else if(RequestId == 2||RequestId == "2"){
				indoorMap.setRoute(map,
				"http://182.92.104.216:8050/SHYCRouteServer/RouteServlet", 
				startPoiData,
				endPoiData,
				function(data) {
					console.log(data);
					if(data.state == "fail"){
						alert("该路径尚未收录，敬请期待");
					}
				});
			}
		}
	}
	function showPoi(name, lon, lat) {
		if (marker && map) {
			marker.setLatLng([lat, lon]);
			map.setView([lat, lon]);
		}
	};
	function searchKey(kw) {
		var data, temp, i, len, poiName, htmls = [];
		if (kw) {
			var data = indoorMap.getFloorPoisByKey(currFloorId, kw);
			if (data) {
				for (i = 0, len = data.length; i < len; i++) {
					var pdata = new Array();
					pdata.push(data[i].clat);
					pdata.push(data[i].clon);
					indoorMap.addMarker(pdata, {
						url : "../libs/images/marker2.png",
						size : [16, 16],
						anchor : [8, 8]
					}, map, {
						enableClick : true,
						clickCbk : clickMarkerCbk,
						params : data[i]
					});
					/*htmls.push('<div onclick="showPoi(\''+data[i].name+'\','+data[i].clon+','+data[i].clat+');"><span class="item">'+data[i].name+'</span></div>');*/
				}
				//$("#searchlist").html(htmls.join(""));
			}
		}
	};

	
	function clickMarkerCbk(params) {
		alert(params.name);
	}
	
	//设置路线起点
	function setStart() {
		if (selectPoiData) {
			console.log(selectPoiData);
			var cfg = indoorMap.getCurrFloorCfg();
			console.log(cfg);
			startPoiData = {
				lon : selectPoiData.clon,
				lat : selectPoiData.clat,
				floor : cfg.serFloor
			};
			//$("#startpoi").html(selectPoiData.name);
		}
	};
	function animMarker(ary1,ary2) {
		Marker = indoorMap.addMarker(ary1, {
			url : "../libs/images/marker2.png",
			size : [16, 16],
			anchor : [8, 8]
		}, map, {
			enableClick : false
		});
		var t1 = (ary2[0]-ary1[0])/10;
		var t2 = (ary2[1]-ary1[1])/10;
		console.log("t2:"+t2);
		var step = 1;
		Interval = window.setInterval(function() {

			if(step == 10){
				changeMarker(ary2);
				map.panTo(ary2);
				window.clearInterval(Interval);
			}else{
				changeMarker(ary1);
				ary1[0] = ary1[0]+t1;
				ary1[1] = ary1[1]+t2;
				step ++;
			}
		},50);
	}
	function changeMarker (ary1) {
		map.removeLayer(Marker);
		Marker = indoorMap.addMarker(ary1, {
			url : "../libs/images/marker2.png",
			size : [16, 16],
			anchor : [8, 8]
		}, map, {
			enableClick : false
		});
		//map.panTo(ary1);
	}
	//移除线路规划
	function cleanRoute() {
		indoorMap.cleanRoute(map);
	}

	//设置路线终点
	function setEnd() {
		if (selectPoiData) {
			var cfg = indoorMap.getCurrFloorCfg();
			endPoiData = {
				lon : selectPoiData.clon,
				lat : selectPoiData.clat,
				floor : cfg.serFloor
			};
		}
	};
	
	var testData = {
		max: 100,
		data: tempData
	};

	var cfg = {
		"radius": 2,
		"maxOpacity": .8, 
		"scaleRadius": true, 
		"useLocalExtrema": true,
		latField: 'lat',
		lngField: 'lng',
		valueField: 'count'
	};

	var heatmapLayer = new HeatmapOverlay(cfg);
	//添加热图
	function addHotmap() {
		now_hour = nowDate.getHours();
		loadHotMapDatas();
		/*map.addLayer(heatmapLayer);
		heatmapLayer.setData(testData);*/
	}
	//移除热图
	function removeHotmap() {
		map.removeLayer(heatmapLayer)
	}
</script>
</head>
<body>
<div id="container" style="width:375px;height:667px;"></div>
</body>
</html>