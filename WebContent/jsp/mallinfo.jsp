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
<script type="text/javascript" src="../js/jquery.dataTables-1.9.4.js"></script>
<script type="text/javascript" src="../js/TableTools.js"></script>
<script type="text/javascript" src="../js/jquery.select-1.3.6.js"></script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var MallId = "<%=mall_id%>";
	var GroupId = "<%=group_id%>";
	var mall_name = "<%=mall_name%>";
	var LoginId = "<%=login_id%>";
	var Telephone = "<%=login_telephone%>";
	var LoginLevel = "<%=login_level%>";
	$(document).ready(function () {
		$("#mall_name").html(mall_name);
		if(LoginLevel != 0){
			$("#edit_li").hide();
			$("#seticon").hide();
			$("#usericon").css("border-left","solid 1px #BCBABB");
		}
		$("#content").css("height",document.body.scrollHeight);
		$("#ul_n").css("height",$("#content").css("height"));
		loadMallInfo();
	});
	function loadMallInfo () {
		var url = basePath + "jaxrs/mall/load/info";
		var cxl = Math.random();
		var para = {
				mallId : MallId,cxl : cxl
		}
		$.post(url,para,loadMallInfoCallBack,"json");
	}
	function loadMallInfoCallBack (data) {
		var code = data.code;
		if(code == 0){
			var mall = data.info;
			console.log(mall);
			$("#mallname").val(mall.name);
			$("#licence").val(mall.licenceCode);
			$("#address").val(mall.address);
			$("#timepicker1").val(mall.openTime);
			$("#timepicker2").val(mall.closeTime);
			$("#contacts").val(mall.contactName);
			$("#telephone").val(mall.contactNum);
		}
	}
	function doSave () {
		var url = basePath + "jaxrs/mall/info";
		var cxl = Math.random();
		var mallname = $("#mallname").val();
		if(mallname == null||mallname == ""){
			$("#errors").html("请输入您的企业名称");
			return;
		}
		var licence = $("#licence").val();
		var address = $("#address").val();
		var stime = $("#timepicker1").val();
		var etime = $("#timepicker2").val();
		var contacts = $("#contacts").val();
		var telephone = $("#telephone").val();
		var para = {
				mallId : MallId,mallname : mallname,licence : licence,address : address,stime : stime,etime : etime,contacts : contacts,telephone : telephone,cxl : cxl
		}
		$.post(url,para,doSaveCallBack,"json");
	}
	function doSaveCallBack (data) {
		var code = data.code;
		if(code == 0){
			alert("保存成功");
			window.location.href = "mallinfo.jsp";
		}else{
			alert("保存失败");
		}
	}
	function chooseInput () {
		$("#errors").html("");
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
			<li><a href="#">顾客模型</a></li>
			<!-- <li><a href="character_date.jsp">特征日模型</a></li> -->
			<li><a href="mobile_summ.jsp">手机数据</a></li>
			<li><a href="hotmap.jsp">热力图</a></li>
			<li id="edit_li"><a class="select"  href="">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a class="select" href="#">企业信息</a></li>
					<li><a href="devices.jsp">设备管理</a></li>
					<li><a href="users.jsp">用户管理</a></li>
					<li><a href="">地图管理</a></li>
				</ul>
			</div>
			<div style="margin-top:100px;margin-left:120px;width:800px;height:420px;padding:20px;background-color:white;border: solid 1px #BCBABB;">
				<ul style="padding:0;margin:0;">
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<div style="width:120px;height:40px;text-align:center;line-height:40px;float:left;">企业名称:</div><input id="mallname" type="text" class="input_regist" style="display:block;width:360px;height:40px;border: solid 1px #BCBABB;float:left;padding-left:10px;font-size:14px;" onfocus="chooseInput()" value="" /><div id="errors" style="width:240px;height:40px;text-align:center;line-height:40px;float:left;color:red;"></div>
					</li>
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<div style="width:120px;height:40px;text-align:center;line-height:40px;float:left;">营业执照:</div><input id="licence" type="text" class="input_regist" style="display:block;width:360px;height:40px;border: solid 1px #BCBABB;float:left;padding-left:10px;font-size:14px;" onblur="" value="" /><div style="width:240px;height:40px;text-align:center;line-height:40px;float:left;color:red;"></div>
					</li>
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<div style="width:120px;height:40px;text-align:center;line-height:40px;float:left;">企业地址:</div><input id="address" type="text" class="input_regist" style="display:block;width:360px;height:40px;border: solid 1px #BCBABB;float:left;padding-left:10px;font-size:14px;" onblur="" value="" /><div style="width:240px;height:40px;text-align:center;line-height:40px;float:left;color:red;"></div>
					</li>
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<div style="width:120px;height:40px;text-align:center;line-height:40px;float:left;">营业时间:</div><input style="display:block;width:145px;height:40px;float:left;padding-left:10px;font-size:14px;" id="timepicker1" type="text" /><span style="display:block;float:left;margin:0 10px;">至</span><input style="display:block;width:145px;height:40px;float:left;padding-left:10px;font-size:14px;" id="timepicker2" type="text" /><div style="width:240px;height:40px;text-align:center;line-height:40px;float:left;margin-left:28px;color:red;"></div>
					</li>
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<div style="width:120px;height:40px;text-align:center;line-height:40px;float:left;">联系人:</div><input id="contacts" type="text" class="input_regist" style="display:block;width:360px;height:40px;border: solid 1px #BCBABB;float:left;padding-left:10px;font-size:14px;" onblur="" value="" /><div style="width:240px;height:40px;text-align:center;line-height:40px;float:left;color:red;"></div>
					</li>
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<div style="width:120px;height:40px;text-align:center;line-height:40px;float:left;">手机号:</div><input id="telephone" type="text" class="input_regist" style="display:block;width:360px;height:40px;border: solid 1px #BCBABB;float:left;padding-left:10px;font-size:14px;" onblur="" value="" /><div style="width:240px;height:40px;text-align:center;line-height:40px;float:left;color:red;"></div>
					</li>
					<li style="list-style:none;width:740px;height:40px;line-height:40px;display:block;padding:10px 30px;float:left;">
						<a href="javascript:void(0)" style="margin-left:240px;" class="sy_button" onclick="doSave()">保存</a>
					</li>
				</ul>
			</div>
			
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>