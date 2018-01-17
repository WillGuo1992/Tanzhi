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
	var msg_code = "";
	var timecount = 60;
	var t,rt;
	var altimecount = 5;  
	$(document).ready(function () {
	});
	function doRegister () {
		var url = basePath + "jaxrs/account/register";
		var cxl = Math.random();
		var username = $("#username").val();
		if(username == null||username == ""){
			$("#usernameerror").html("用户名不能为空");
			$("#usernameerror").show();
			return;
		}else if(username.length > 20||username.length < 6){
			$("#usernameerror").html("用户名长度需在6-20位之间");
			$("#usernameerror").show();
			return;
		}
		var password = $("#password").val();
		if (password == null||password == "") {
			$("#pwderror").html("密码不能为空");
			$("#pwderror").show();
			return;
		}else if(password.length < 6||password.length > 20){
			$("#pwderror").html("密码长度需在6-20位之间");
			$("#pwderror").show();
			return;
		}
		var repassword = $("#repassword").val();
		if(repassword == null||repassword == ""){
			$("#repwderror").html("请再次输入您的密码");
			$("#repwderror").show();
			return;
		}else if (repassword != password) {
			$("#repwderror").html("密码输入不一致");
			$("#repwderror").show();
			return;
		}
		var validatecode = $("#verifycode").val();
		if(validatecode == null||validatecode == ""){
			$("#verifyerror").html("请输入验证码");
			$("#verifyerror").show();
			return;
		}
		var telephone = $("#telephone").val();
		if(telephone == null||telephone == ""){
			$("#telephoneerror").html("请输入您的手机号码");
			$("#telephoneerror").show();
			return;
		}
		var msgcode = $("#msgcode").val();
		if(msgcode == null||msgcode == ""){
			$("#msgerror").html("请输入您的短信确认码");
			$("#msgerror").show();
			return;
		}else if(msgcode != msg_code){
			$("#msgerror").html("手机确认码错误");
			$("#msgerror").show();
			return;
		}
		var para = {
				username : username,password : password,telephonenum : telephone,validatecode : validatecode,cxl : cxl
		}
		$.post(url,para,doRegisterCallBack,"json");
	}
	function doRegisterCallBack (data) {
		var code = data.code;
		console.log(code);
		if(code == 0){
			document.body.style.overflow='hidden';
			$("#albg0").show();
			$("#alcont0").show();
			altimedCount();
		}else{
			if(code == -3){
				$("#usernameerror").html("该用户名已被注册");
				$("#usernameerror").show();
			}else if(code == -4){
				$("#verifyerror").html("验证码错误");
				$("#verifyerror").show();
			}else if(code == -5){
				$("#telephoneerror").html("该手机号已被注册");
				$("#telephoneerror").show();
			}
		}
	}
	function altimedCount(){
		$("#time_count").html(altimecount);
        altimecount = altimecount - 1;
        t=setTimeout("altimedCount()",1000);
        if(altimecount == 0){
        	clearTimeout(t);
            window.location.href = "../jsp/entirety_inside.jsp?t=0";
        }
    }
	function getIdentifyCode () {
		var telephone = $("#telephone").val();
		if(telephone == null||telephone == ""){
			$("#telephoneerror").html("请输入您的手机号码");
			$("#telephoneerror").show();
			return;
		}
		msg_code = "";
		clearTimeout(rt);
		timedCount();
		var url = basePath + "jaxrs/message/local";
		var cxl = Math.random();
		var para = {
				telephone : telephone,cxl : cxl
		}
		$.post(url,para,getIdentifyCodeCallBack,"json");
	}
	function timedCount(){
		$("#ident_a").html("请稍等(" + timecount + ")");
		$("#ident_a").removeAttr("onclick");
		//$("#ident_a").attr("onclick","");
        timecount = timecount - 1;
        rt=setTimeout("timedCount()",1000);
        if(timecount < 0){
            timecount = 60;
            stopCount();
            $("#ident_a").html("获取验证码");
			$("#ident_a").click(function(){getIdentifyCode()});
        }
    }
    function stopCount(){
        clearTimeout(rt);
    }

    function getIdentifyCodeCallBack (data) {
    	var code = data.code;
    	if(code == 0||code == "0"){
    		msg_code = data.info;
    		rt = setTimeout(function(){msg_code = "";},5*60000);
    	}else{
    		var desc = data.desc;
    		alert(desc);
    	}
    }
    function validateCode () {
    	var url = basePath + "jaxrs/image/validate";
    	var cxl = Math.random();
    	var para = {
    			cxl : cxl
    	}
    	$.post(url,para,validateCodeCallBack,"json");
    }
    function validateCodeCallBack (data) {
    	var code = data.code;
    	alert(code);
    }
    function changeUrl () {
    	var cxl = Math.random();
    	var url = basePath + "imageServlet?cxl="+cxl;
    	$("#validateCode").attr(url);
    }
    function chooseInput (obj) {
    	$(obj).parent().find(".tips").show();
    	$(obj).parent().find(".errors").hide();
    }
    function blurInput (obj) {
    	$(obj).parent().find(".tips").hide();
    }
</script>
</head>
<body onload="">
	<div class="container">
	<div class="xy" id="albg0"></div>
	<div class="xytxt" id="alcont0" style="width:400px;height:200px;left:50%;top:50%;margin-left:-200px;margin-top:-100px;">
		<div style="width:200px;height:60px;text-align:center;line-height:60px;font-size:24px;margin-left:100px;margin-top:30px;">注册成功!</div>
		<div style="width:400px;height:20px;margin-top:30px;text-align:center;line-height:20px;"><span id="time_count"></span>秒钟后将进入系统主界面</div>
	</div>
	<div style="width:1000px;height:60px;position:relative;left:50%;margin-left:-500px;">
		<div class="logo"></div>
		<a href="login.jsp" style="display:block;text-decoration:none;width:100px;height:30px;float:right;background-color:#FA4242;text-align:center;margin:15px;line-height:30px;cursor:pointer;color:white;font-family:'微软雅黑';border-radius:5px;">登录</a>
		<div style="height:60px;line-height:60px;float:right;font-family:'微软雅黑';font-size:18px;">已有探知账号？马上</div>
	</div>
	<div style="width:100%;height:48px;font-size:24px;line-height:48px;background-color:#0277C0;color:white;text-align:center;">欢迎注册探知账号</div>
	<div style="width:1000px;height:566px;position:relative;left:50%;margin-left:-500px;border:solid 1px #BCBABB;">
		<div style="width:680px;height:546px;float:left;padding:10px 20px;">
			<ul style="padding:0;margin:0;">
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<div style="width:120px;height:40px;line-height:40px;text-align:right;float:left;">用户名:</div>
					<input id="username" type="text" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:15px;" value="" onfocus="chooseInput(this)" onblur="blurInput(this)" />
					<div class="tips" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #B8B8B8;padding-left:10px;font-size:12px;background-color:#F7F7F7;margin-left:20px;">6-20位字母、数字或下划线</div>
					<div class="errors" id="usernameerror" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #FA4242;padding-left:10px;font-size:12px;background-color:#FFEBEB;margin-left:20px;"></div>
				</li>
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<div style="width:120px;height:40px;line-height:40px;text-align:right;float:left;">密码:</div>
					<input id="password" type="password" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:15px;" value="" onfocus="chooseInput(this)" onblur="blurInput(this)"/>
					<div class="tips" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #B8B8B8;padding-left:10px;font-size:12px;background-color:#F7F7F7;margin-left:20px;">密码长度为6-20位</div>
					<div class="errors" id="pwderror" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #FA4242;padding-left:10px;font-size:12px;background-color:#FFEBEB;margin-left:20px;"></div>
				</li>
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<div style="width:120px;height:40px;line-height:40px;text-align:right;float:left;">确认密码:</div>
					<input id="repassword" type="password" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:15px;" value="" onfocus="chooseInput(this)" onblur="blurInput(this)"/>
					<div class="tips" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #B8B8B8;padding-left:10px;font-size:12px;background-color:#F7F7F7;margin-left:20px;">请再次输入您的密码</div>
					<div class="errors" id="repwderror" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #FA4242;padding-left:10px;font-size:12px;background-color:#FFEBEB;margin-left:20px;"></div>
				</li>
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<div style="width:120px;height:40px;line-height:40px;text-align:right;float:left;">验证码:</div>
					<input id="verifycode" type="text" style="display:block;width:120px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:15px;" value="" onfocus="chooseInput(this)" onblur="blurInput(this)" />
					<div style="width:110px;height:40px;float:left;margin-left:10px;">
						<div onclick="changeUrl()" style="width:40px;height:40px;float:right;background:url(../img/jingling3.png) -438px -177px;cursor:pointer;"></div>
						<div style="width:70px;height:40px;float:right;"><img id="validateCode" src= "http://47.93.128.28:8080/imageServlet"/></div>
					</div>
					<div class="tips" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #B8B8B8;padding-left:10px;font-size:12px;background-color:#F7F7F7;margin-left:20px;">请输入图片中验证码，不区分大小写</div>
					<div class="errors" id="verifyerror" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #FA4242;padding-left:10px;font-size:12px;background-color:#FFEBEB;margin-left:20px;"></div>
				</li>
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<div style="width:120px;height:40px;line-height:40px;text-align:right;float:left;">手机号:</div>
					<input id="telephone" type="text" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:15px;" value="" onfocus="chooseInput(this)" onblur="blurInput(this)" />
					<div class="tips" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #B8B8B8;padding-left:10px;font-size:12px;background-color:#F7F7F7;margin-left:20px;">完成验证后您可以通过该号码登录或找回密码</div>
					<div class="errors" id="telephoneerror" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #FA4242;padding-left:10px;font-size:12px;background-color:#FFEBEB;margin-left:20px;"></div>
				</li>
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<div style="width:120px;height:40px;line-height:40px;text-align:right;float:left;">短信确认码:</div>
					<input id="msgcode" type="text" style="display:block;width:120px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:15px;" value="" onfocus="chooseInput(this)" onblur="blurInput(this)" />
					<a href="javascript:void(0)" id="ident_a" class="sg_button" onclick="getIdentifyCode()" style="float:left;margin-left:20px;">获取确认码</a>
					<div class="tips" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #B8B8B8;padding-left:10px;font-size:12px;background-color:#F7F7F7;margin-left:20px;">请输入您的短信确认码</div>
					<div class="errors" id="msgerror" style="display:none;width:260px;height:40px;line-height:40px;float:left;border:solid 1px #FA4242;padding-left:10px;font-size:12px;background-color:#FFEBEB;margin-left:20px;"></div>
				</li>
				<li style="list-style:none;width:680px;height:40px;display:block;padding:20px 0;">
					<a onclick="doRegister()" class="sy_button" style="margin-left:140px;" href="javascript:void(0)">注册</a>
				</li>
			</ul>
		</div>
		<div style="width:280px;height:566px;float:left;background:url(../img/cloud.png);"></div>
	</div>
	<div style="width:1000px;height:50px;position:relative;left:50%;margin-left:-500px;line-height:50px;text-align:center;font-family:'Arial';">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
	</div>
</body>
</html>