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
		RequestId = uri.split("=")[1].split("&")[0];
		currFloorId = uri.split("&")[1].split("=")[1];
		/*alert(RequestId);
		alert(currFloorId);*/
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
				minZoom : 15,
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
					anchor : [15, 30]
				},
				"endMarker" : {
					url : "../libs/images/zhong.png",
					size : [30, 30],
					anchor : [15, 30]
				},
				"cornerMarker" : {
					url : "../libs/images/marker2.png",
					size : [16, 16],
					anchor : [8, 8]
				}
			}
			
		});
		//highlightPoiByIdForIOS("6017996b-1698-44f4-9303-e750926859e3");
		/*var polygons = indoorMap.getFloorPolygons();
		highlightPOI(polygons[0].floorData);*/
		/*cleanLastHighLightPoi();
		highlightPoiById("6017996b-1698-44f4-9303-e750926859e3");
		window.setInterval(function(){
			cleanLastHighLightPoi();
		},5000);*/
		//console.log(indoorMap.getCurrFloorCfg());
		map.on('click', mapClick);
		/*highlightPoiById("6017996b-1698-44f4-9303-e750926859e3");
		window.setInterval(function(){
			cleanHighLightPoi();
		},5000);*/
		/*window.setInterval(function(){
			map.panTo([39.94057704922896,116.42865246660917]);
			map.zoomOut();//缩小 
			map.zoomIn();//放大
		},5000);*/
		/*var marker = indoorMap.addMarker([39.94124546236345, 116.42912707138754], {
			url : "../libs/images/gensui.png",
			size : [24, 24],
			anchor : [8, 8]
		}, map);*/ //添加坐标点图标
		//$(".leaflet-marker-icon").imageRotate(90);
		/*var marker1 = indoorMap.addMarker([39.94100515403017, 116.43139472426816], {
					url : "../libs/images/marker-icon.png",
					size : [16, 16],
					anchor : [8, 8]
				}, map);
		var marker2 = indoorMap.addMarker([39.94069702179421, 116.43311003941494], {
					url : "../libs/images/marker-icon.png",
					size : [16, 16],
					anchor : [8, 8]
				}, map);
		var marker3 = indoorMap.addMarker([39.94102350078118, 116.42922126362762], {
					url : "../libs/images/marker-icon.png",
					size : [16, 16],
					anchor : [8, 8]
				}, map);
		window.setInterval(function  () {
			map.removeLayer(marker1);
			map.removeLayer(marker2);
			map.removeLayer(marker3);
		},2000);*/
		//aroundPoi([39.94094278453571,116.42895017909011]);
		//map.removeLayer(marker);移除坐标点图标
		//console.log(indoorMap.getCurrFloorCfg());
		navi([39.94110797772234,116.4302053749923],"Floor1",[39.94124544340044,116.429127066067],"Floor1");//路线规划方法
		//indoorMap.addLabel([39.90532116681635,116.49124717859452], "123", map, null);
		/*window.setTimeout(function () {
			cleanRoute();
		},5000);*/
		//alert(navi([39.94072543893116,116.429384117909],[39.94059597597737,116.4300498057149]));
		//searchKey("站台");//查询站点信息
		//addHotmap();
		//animMarker([39.94054622438818, 116.42904248126902],[39.94031704405591, 116.42954181135322]);
		/*console.log(((39.9403170440-39.9405462243)/10).toFixed(10));
		console.log(39.94054622432818.toFixed(10));*/
		/*console.log(39.94054622438818.toFixed(10));
		console.log(parseFloat(39.94054622438818.toFixed(10)));
		console.log(39.94031704405591.toFixed(10));
		console.log((39.94054622438818-39.94031704405591).toFixed(10));*/
		//loadHotMapDatas();
		//abc();
		/*var byh = {"polygon":[{"lon":116.42904248126902,"lat":39.94054622438818},{"lon":116.429070859136,"lat":39.94054610041384},{"lon":116.42911393348616,"lat":39.94056484808821},{"lon":116.42946226529051,"lat":39.94056334662127},{"lon":116.42946160053516,"lat":39.940473045093505},{"lon":116.42956284996178,"lat":39.94047260429587},{"lon":116.42956239181959,"lat":39.94040895725002},{"lon":116.4295173501529,"lat":39.940409150098986},{"lon":116.42951715252295,"lat":39.94038286754007},{"lon":116.42946230122324,"lat":39.94038310171381},{"lon":116.42939224139907,"lat":39.94035261780245},{"lon":116.42941129472476,"lat":39.94032688624058},{"lon":116.42947688990826,"lat":39.94035543477483},{"lon":116.42954208983181,"lat":39.94035515238885},{"lon":116.42954181135322,"lat":39.94031704405591},{"lon":116.42956170909788,"lat":39.94031696140635},{"lon":116.42956091857799,"lat":39.94020793286661},{"lon":116.42942307167431,"lat":39.94020852518843},{"lon":116.42942364659785,"lat":39.94028718001622},{"lon":116.4292759541284,"lat":39.94028781366281},{"lon":116.4292763673547,"lat":39.94034359522588},{"lon":116.42921749159017,"lat":39.940343850062014},{"lon":116.42921707836386,"lat":39.94028806849895},{"lon":116.428998517584,"lat":39.94028900519392},{"lon":116.42899794266046,"lat":39.9402103572536},{"lon":116.42891180294332,"lat":39.940210729176606},{"lon":116.42869526337905,"lat":39.94050319840664},{"lon":116.42904215787453,"lat":39.94050171071463},{"lon":116.42904248126902,"lat":39.94054622438818}],"id":35,"clon":116.42910376272928,"name":"佰益汇","clat":39.94040676841427};
		for (var i = 0; i < f1data.poi_list.length; i++) {
			var p = new Array();
			p.push(f1data.poi_list[i].clat);
			p.push(f1data.poi_list[i].clon);
			indoorMap.addMarker(p, {
				url : "../libs/images/marker-icon.png",
				size : [16, 16],
				anchor : [8, 8]
			}, map);
		}*/
	}
	/*var marker = indoorMap.addMarker([39.94100515403017, 116.43139472426816], {
		url : "../libs/images/marker2.png",
		size : [16, 16],
		anchor : [8, 8]
	}, map);*/
	function abc () {
		var byh = {"polygon":[{"lon":116.42904248126902,"lat":39.94054622438818},{"lon":116.429070859136,"lat":39.94054610041384},{"lon":116.42911393348616,"lat":39.94056484808821},{"lon":116.42946226529051,"lat":39.94056334662127},{"lon":116.42946160053516,"lat":39.940473045093505},{"lon":116.42956284996178,"lat":39.94047260429587},{"lon":116.42956239181959,"lat":39.94040895725002},{"lon":116.4295173501529,"lat":39.940409150098986},{"lon":116.42951715252295,"lat":39.94038286754007},{"lon":116.42946230122324,"lat":39.94038310171381},{"lon":116.42939224139907,"lat":39.94035261780245},{"lon":116.42941129472476,"lat":39.94032688624058},{"lon":116.42947688990826,"lat":39.94035543477483},{"lon":116.42954208983181,"lat":39.94035515238885},{"lon":116.42954181135322,"lat":39.94031704405591},{"lon":116.42956170909788,"lat":39.94031696140635},{"lon":116.42956091857799,"lat":39.94020793286661},{"lon":116.42942307167431,"lat":39.94020852518843},{"lon":116.42942364659785,"lat":39.94028718001622},{"lon":116.4292759541284,"lat":39.94028781366281},{"lon":116.4292763673547,"lat":39.94034359522588},{"lon":116.42921749159017,"lat":39.940343850062014},{"lon":116.42921707836386,"lat":39.94028806849895},{"lon":116.428998517584,"lat":39.94028900519392},{"lon":116.42899794266046,"lat":39.9402103572536},{"lon":116.42891180294332,"lat":39.940210729176606},{"lon":116.42869526337905,"lat":39.94050319840664},{"lon":116.42904215787453,"lat":39.94050171071463},{"lon":116.42904248126902,"lat":39.94054622438818}],"id":35,"clon":116.42910376272928,"name":"佰益汇","clat":39.94040676841427};
		var byhary = byh.polygon;
		Marker = indoorMap.addMarker([39.94054622438818, 116.42904248126902], {
			url : "../libs/images/marker2.png",
			size : [16, 16],
			anchor : [8, 8]
		}, map);
		var i = 0;
		var it = window.setInterval(function(){
			console.log(i);
			var a1 = new Array();
			var a2 = new Array();
			a1.push(byhary[i].lat);
			a1.push(byhary[i].lon);
			a2.push(byhary[i+1].lat);
			a2.push(byhary[i+1].lon);
			map.removeLayer(Marker);
			animMarker(a1,a2);
			i++;
			if(i == byhary.length-1){
				console.log("it cancel");
				window.clearInterval(it);
			}
		},1000);
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
	/*function abc () {
		
	}*/
	function mapClick (e) {
		/*var userAgentInfo = navigator.userAgent;*/
		var clickRtn = new Object();
		clickRtn.isPoi = false;
		clickRtn.result = e;
		console.log(clickRtn.result.latlng);
		/*if(userAgentInfo.indexOf("Android") > 0){
			androidShow.show(JSON.stringify(clickRtn));
		}else{
			connectWebViewJavascriptBridge(function(bridge) { 

			    bridge.send(clickRtn, null);
			});
		}*/
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
				/*var oHead = document.getElementsByTagName('head')[0]; 
    			var oScript= document.createElement("script"); 
				for (var i = 0; i < storeys.length; i++) {
					var oScript= document.createElement("script"); 
    				oScript.type = "text/javascript";
    				oScript.src="../data/F"+storeys[i].id+".js";
    				oHead.appendChild(oScript); 
				}*/
				//currFloorId = storeys[0].floorId;
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

	/*function navi() {
		if (startPoiData && endPoiData) {
			console.log(startPoiData);
			console.log(endPoiData);
			indoorMap.setRoute(map,
				"http://115.29.149.25:8080/YCRouteServer/RouteServlet", 
				startPoiData, 
				endPoiData, 
				function(data) {
					console.log(data);
			});
		}
	};*/
	/*private static boolean pointInPolygon(double x, double y, double polyX[], double polyY[], int corners) {
		boolean oddNodes = false;

		double constant[] = new double[corners];
		double multiple[] = new double[corners];
		int j = corners - 1;
		{
			// precalc_values
			for (int i = 0; i < corners; i++) {
				if (polyY[j] == polyY[i]) {
					constant[i] = polyX[i];
					multiple[i] = 0;
				} else {
					constant[i] = polyX[i] - (polyY[i] * polyX[j]) / (polyY[j] - polyY[i]) + (polyY[i] * polyX[i]) / (polyY[j] - polyY[i]);
					multiple[i] = (polyX[j] - polyX[i]) / (polyY[j] - polyY[i]);
				}
				j = i;
			}
		}
		{
			// pointInPolygon
			for (int i = 0; i < corners; i++) {
				if ((polyY[i] < y && polyY[j] >= y || polyY[j] < y && polyY[i] >= y)) {
					oddNodes ^= (y * multiple[i] + constant[i] < x);
				}
				j = i;
			}
		}
		return oddNodes;
	}*/
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
				"http://115.29.149.25/YCRouteServer/RouteServlet", 
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
				"http://115.29.149.25/SHYCRouteServer/RouteServlet", 
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
			/*if(ary1[0]>ary2[0]){
				ary1[0] = ary1[0]-t1;
			}else if(ary1[0]<ary2[0]){
				ary1[0] = ary1[0]+t1;
			}
			if(ary1[1]>ary2[1]){
				ary1[1] = ary1[1]-t2;
			}else if(ary1[1]<ary2[1]){
				ary1[1] = ary1[1]+t2;
			}*/

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
			/*if(ary1[0]>=ary2[0]){
				console.log(ary1);
				console.log(ary2);
				changeMarker(ary1);
			}else{
				console.log("cancel");
				window.clearInterval(Interval);
			}*/
		},50);
		/*var points = [
			[39.90559081792348, 116.49249083681671],
			[39.90538033748013, 116.49260036570438],
			[39.905385585640374, 116.4926965501185]
		];
		var icon = {
			url : "marker3.png",
			size : [16, 16],
			anchor : [8, 8]
		};
		indoorMap.animMarker(points, icon, map, 1500);*/
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
	
	/*tempData.push({
		lat: 39.94083825239218,
		lng: 116.43098232778271,
		count: Math.ceil(8 * Math.random())
	});
	tempData.push({
		lat: 39.94075696300971,
		lng: 116.43057746309645,
		count: Math.ceil(8 * Math.random())
	});
	tempData.push({
		lat: 39.94065632816185,
		lng: 116.43116937205055,
		count: Math.ceil(8 * Math.random())
	});*/

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
<div id="container" style="width:1000px;height:667px;"></div>
</body>
</html>