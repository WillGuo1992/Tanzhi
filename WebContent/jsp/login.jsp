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
	int loginid = 0; 
	Cookie[] cookies = request.getCookies();
	if(cookies != null&&cookies.length > 0){
		for(int i = 0;i<cookies.length;i++){
			if ("loginid".equals(cookies[i].getName())&&cookies[i].getValue()!=null&&cookies[i].getValue().length()>0) {
				loginid = Integer.parseInt(cookies[i].getValue());
			}
		}
	}
	if(loginid != 0){
		response.sendRedirect("entirety_inside.jsp");
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
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var iskeep = true;
	$(document).ready(function () {
		
	});
	function doLogin () {
		var url = basePath + "jaxrs/account/login";
		var cxl = Math.random();
		var logininfo = $("#logininfo").val();
		if(logininfo == null||logininfo == ""){
			$("#errors").html("请输入您的用户名或手机号");
			return;
		}
		var password = $("#password").val();
		if(password == null||password == ""){
			$("#errors").html("请输入您的密码");
			return;
		}
		var para = {
				logininfo : logininfo,password : password,iskeep : iskeep,cxl : cxl
		}
		$.post(url,para,doLoginCallBack,"json");
	}
	function doLoginCallBack (data) {
		var code = data.code;
		if(code == 0){
			window.location.href = "entirety_inside.jsp";
		}else{
			var desc = data.desc;
			$("#errors").html(desc);
		}
	}
	function keepLogin(data){
		iskeep = data;
	}
	/*function chooseInput (obj) {
		$(obj).val("");
		$("#errors").html("");
	}
	function blurInput (obj,txt) {
		if($(obj).val() == ""){
			$(obj).val(txt);
		}
	}*/
	
</script>
</head>
<body onload="">
	<div class="container">
	<div style="width:1280px;height:80px;position:relative;left:50%;margin-left:-640px;">
		<div class="logo"></div>
		<!-- <a href="register.jsp" style="display:block;text-decoration:none;width:100px;height:30px;float:right;background-color:#FA4242;text-align:center;margin:25px;line-height:30px;cursor:pointer;color:white;font-family:'微软雅黑';border-radius:5px;">马上注册</a>
		<div style="height:80px;line-height:80px;float:right;font-family:'微软雅黑';font-size:18px;">还没有账号？</div> -->
	</div>
	<div style="width:100%;height:590px;background-color:#142543;">
		<div style="width:1280px;height:590px;position:relative;left:50%;margin-left:-640px;background:url(../img/banner_new.jpg)">
			<div style="width:438px;height:460px;position:relative;left:750px;top:70px;border-radius:5px;background:rgba(255,255,255,0.45);">
				<ul style="padding:0;margin:0;">
					<li style="list-style:none;width:400px;height:60px;padding:20px 15px;">
						<div style="width:100%;height:60px;text-align:center;line-height:60px;font-size:30px;font-family:'微软雅黑';color:white;">登录探知</div>
					</li>
					<li style="list-style:none;width:400px;height:60px;padding:20px 15px;">
						<div style="width:310px;height:46px;border-radius:5px;border:solid 1px #BCBABB;margin-left:50px;background-color:white;">
							<div style="width:26px;height:26px;float:left;margin-top:10px;margin-left:5px;background:url(../img/jingling3.png) -459px -132px;"></div>
							<input id="logininfo" type="text" style="width:268px;height:44px;border:0px;outline:none;font-size:20px;color:#CCCCCC;line-height:44px;color:black;" placeholder="用户名/手机号"/>
						</div>
					</li>
					<li style="list-style:none;width:400px;height:80px;padding:20px 15px 0 15px;">
						<div style="width:310px;height:46px;border-radius:5px;border:solid 1px #BCBABB;margin-left:50px;background-color:white;">
							<div style="width:26px;height:27px;float:left;margin-top:10px;margin-left:5px;background:url(../img/jingling3.png) -450px -291px;"></div>
							<input id="password" type="password" style="width:268px;height:44px;border:0px;outline:none;font-size:20px;color:#CCCCCC;line-height:44px;color:black;" placeholder="密码" />
						</div>
						<div style="width:300px;height:30px;font-size:13px;line-height:30px;margin-left:52px;">
							<input id="keeplogin" onclick="keepLogin(this.checked)" type="checkbox" checked="true" />保持登录状态
							<a style="margin-left:136px;" href="forget_pwd.jsp">忘记密码?</a>
						</div>
					</li>
					<li style="list-style:none;width:400px;height:80px;padding:0px 15px;">
						<div id="errors" style="width:320px;height:30px;margin-left:45px;color:red;text-align:center;"></div>
						<a href="javascript:void(0)" onclick="doLogin()" class="by_button" style="margin-left:45px;">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div style="width:1000px;height:50px;position:relative;left:50%;margin-left:-500px;line-height:50px;text-align:center;font-family:'Arial';">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
	</div>
	
	<!-- <div id="content" class="content">
		<div style="height:100%;width:50%;border:solid 1px #BCBABB;float:right;">
			<div class="content_registe" style="width:450px;height:400px;background-color:white;position:relative;left:50%;top:50%;margin-left:-225px;margin-top:-200px;">
				<ul style="padding:0;margin:0;">
					<li style="list-style:none;width:430px;height:40px;display:block;padding:20px 10px;float:left;">
						<div style="width:100%;height:40px;text-align:center;line-height:40px;font-family:'微软雅黑';font-size:26px;">探知登录</div>
					</li>
					<li style="list-style:none;width:430px;height:40px;display:block;padding:20px 10px;float:left;">
						<input id="logininfo" type="text" class="input_regist" style="width:300px;height:40px;border: solid 1px #BCBABB;" onblur="" value="用户名/手机号" /><span style="color:blue;" class="tip"></span><strong><em style="color:red;" id=""></em></strong>
					</li>
					<li style="list-style:none;width:430px;height:60px;display:block;padding:20px 10px 0 10px;float:left;">
						<input id="password" type="text" class="input_regist" style="width:300px;height:40px;border: solid 1px #BCBABB;" onblur="" value="密码" /><span style="color:blue;" class="tip"></span><strong><em style="color:red;" id=""></em></strong>
						<div style="width:300px;height:20px;font-size:13px;">
							<input id="keeplogin" onclick="keepLogin(this.checked)" type="checkbox" checked="true" />保持登录状态
							<a style="margin-left:136px;" href="javascript:void(0)">忘记密码？</a>
						</div>
					</li>
					<li style="list-style:none;width:430px;height:40px;display:block;padding:20px 10px;float:left;">
						<a onclick="doLogin()" style="display:block;width:300px;height:40px;text-decoration:none;text-align:center;line-height:40px;background-color:#3FB165;color:white;" href="javascript:void(0)">登录</a>
					</li>
					<li style="list-style:none;width:430px;height:40px;display:block;padding:20px 10px;float:left;">
						<div style="width:100px;height:40px;float:right;background-color:#00CCFF;text-align:center;margin-right:130px;line-height:40px;cursor:pointer;">立即注册</div><div style="height:40px;line-height:40px;float:right;margin-right:20px;">还没有探知账号?</div>
					</li>
				</ul>
			</div>
		</div>
	</div> -->
</div>
</body>
</html>