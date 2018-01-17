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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title> 探知-基于室内定位的线下顾客行为分析系统 </title>
<link type="text/css" rel="stylesheet" href="../css/ui.css" />
<link type="text/css" rel="stylesheet" href="../css/entirety.css" />
<script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	$(document).ready(function () {
		
	});
	function goNext () {
		var url = basePath + "jaxrs/account/forgetpwd";
		var cxl = Math.random();
		var userinfo = $("#userinfo").val();
		if(userinfo == null||userinfo == ""||userinfo == "用户名/手机号"){
			$("#infoerror").html("请输入用户名或手机号");
			return;
		}
		var validatecode = $("#verifycode").val();
		if(validatecode == null||validatecode == ""||validatecode == "验证码"){
			$("#validateerror").html("请输入验证码");
			return;
		}
		var para = {
				userinfo : userinfo,validatecode : validatecode,cxl : cxl
		}
		$.post(url,para,goNextCallBack,"json");
	}
	function goNextCallBack (data) {
		var code = data.code;
		if(code == 0){
			var mid = data.mid;
			window.location.href = "reset_pwd.jsp?id=" + mid ;
		}else if(code == -4){
			$("#validateerror").html("验证码错误");
		}else if(code == -2){
			var desc = data.desc;
			$("#infoerror").html(desc);
		}
	}
	function changeUrl () {
    	var cxl = Math.random();
    	$("#validateCode").attr("src","http://115.29.149.25/imageServlet?cxl="+cxl);
    }
	
</script>
</head>
<body onload="">
	<div class="container">
	<div style="width:1000px;height:60px;position:relative;left:50%;margin-left:-500px;">
		<!-- <div class="logo"></div> -->
		<a href="login.jsp" style="display:block;text-decoration:none;width:100px;height:30px;float:right;background-color:#FA4242;text-align:center;margin:15px;line-height:30px;cursor:pointer;color:white;font-family:'微软雅黑';border-radius:5px;">登录</a>
		<div style="height:60px;line-height:60px;float:right;font-family:'微软雅黑';font-size:18px;">已有探知账号？马上</div>
	</div>
	<div style="width:100%;height:558px;background:url(../img/hengtiao.png) repeat-x;">
		<div style="width:682px;height:440px;border-radius:5px;background-color:white;position:relative;left:50%;margin-left:-341px;top:59px;">
			<div style="width:622px;height:50px;margin-left:20px;border-bottom:dashed 1px #BCBABB;padding-left:20px;line-height:50px;font-size:24px;">请输入您的账号:</div>
			<div style="width:600px;height:300px;margin-left:31px;padding:0 10px;">
				<ul style="margin:0;padding:55px 0;">
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<input id="userinfo" type="text" style="width:310px;height:50px;color:#CCCCCC;padding-left:10px;font-size:22px;float:left;margin-left:140px;color:black;" placeholder="用户名/手机号" />
						<div id="infoerror" style="width:130px;height:50px;float:left;line-height:50px;font-size:10px;color:red;"></div>
					</li>
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<input id="verifycode" type="text" style="width:180px;height:50px;color:#CCCCCC;padding-left:10px;font-size:22px;float:left;margin-left:140px;color:black;" placeholder="验证码" />
						<div style="width:110px;height:40px;float:left;margin-left:10px;">
							<div onclick="changeUrl()" style="width:40px;height:40px;float:right;background:url(../img/jingling3.png) -438px -177px;cursor:pointer;"></div>
							<div style="width:70px;height:40px;float:right;"><img id="validateCode" src="http://115.29.149.25/imageServlet"/></div>
						</div>
						<div id="validateerror" style="width:130px;height:50px;float:left;line-height:50px;font-size:10px;color:red;"></div>
					</li>
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<a onclick="goNext()" class="by_button" style="margin-left:140px;" href="javascript:void(0)">下一步</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div style="width:1000px;height:50px;position:relative;left:50%;margin-left:-500px;line-height:50px;text-align:center;font-family:'Arial';">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
	</div>
</body>
</html>