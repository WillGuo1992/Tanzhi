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
	var managerid = 0;
	var managertel = "";
	var timecount = 60;
	var identify_code = "";
	var t,rt;
	$(document).ready(function () {
		$("#content").css("height",document.documentElement.clientHeight - 60 + "px");
		console.log($("#content").css("height"));
		var uri = document.location.toString();
		if(uri.indexOf("?") > 0){
			managerid = uri.split("=")[1];
		}
		loadManagerInfo();
	});
	function loadManagerInfo () {
		var url = basePath + "jaxrs/account/getbean";
		var cxl = Math.random();
		var para = {
				managerid : managerid,cxl : cxl
		}
		$.post(url,para,loadManagerInfoCallBack,"json");
	}
	function loadManagerInfoCallBack (data) {
		var code = data.code;
		if(code == 0||code == "0"){
			var info = data.info;
			managertel = info.telephone;
			$("#telephone_num").html(managertel.substring(0,3)+"****"+managertel.substring(7,managertel.length));
		}
	}
	function getIdentifyCode () {
		identify_code = "";
		clearTimeout(rt);
		timedCount();
		var url = basePath + "jaxrs/message/local";
		var cxl = Math.random();
		var para = {
				telephone : managertel,cxl : cxl
		}
		$.post(url,para,getIdentifyCodeCallBack,"json");
	}
	function timedCount(){
		$("#ident_a").html("请稍等(" + timecount + ")");
		$("#ident_a").removeAttr("onclick");
        timecount = timecount - 1;
        t=setTimeout("timedCount()",1000);
        if(timecount < 0){
            timecount = 60;
            stopCount();
            $("#ident_a").html("获取验证码");
            $("#ident_a").click(function(){getIdentifyCode()});
        }
    }
    function stopCount(){
        clearTimeout(t);
    }

    function getIdentifyCodeCallBack (data) {
    	var code = data.code;
    	if(code == 0||code == "0"){
    		identify_code = data.info;
    		rt = setTimeout(function(){identify_code = "";},5*60000);
    	}else{
    		var desc = data.desc;
    		alert(desc);
    	}
    }
    function doReset () {
    	var url = basePath + "jaxrs/account/resetpwd";
    	var cxl = Math.random();
    	var code_input = $("#msgcode").val();
    	var password = $("#password").val();
    	var repassword = $("#repassword").val();
    	if(code_input == null||code_input == ""){
    		$("#msgcodeerror").html("请输入手机确认码");
    		return;
    	}
    	if(code_input != identify_code){
    		$("#msgcodeerror").html("手机确认码错误");
    		return;
    	}
    	if(password == null||password == ""){
    		$("#pwderror").html("请输入新密码");
    		return;
    	}
    	if(password.length<6||password.length>20){
    		$("#pwderror").html("密码长度6-20位");
    		return;
    	}
    	if(repassword == null||repassword == ""){
    		$("#repwderror").html("请再次输入新密码");
    		return;
    	}
    	if(password != repassword){
    		$("#repwderror").html("密码输入不一致");
    		return;
    	}
    	var para = {
    			password : password,managerid : managerid,cxl : cxl
    	}
    	$.post(url,para,doResetCallBack,"json");
    }
    function doResetCallBack (data) {
    	var code = data.code;
    	if(code == 0){
    		alert("密码重置成功");
	    	window.location.href = "login.jsp";
    	}
    }
</script>
</head>
<body onload="" style="overflow:hidden">
	<div class="container">
	<div style="width:1000px;height:60px;position:relative;left:50%;margin-left:-500px;">
		<div class="logo"></div>
		<a href="login.jsp" style="display:block;text-decoration:none;width:100px;height:30px;float:right;background-color:#FA4242;text-align:center;margin:15px;line-height:30px;cursor:pointer;color:white;font-family:'微软雅黑';border-radius:5px;">登录</a>
		<div style="height:60px;line-height:60px;float:right;font-family:'微软雅黑';font-size:18px;">已有探知账号？马上</div>
	</div>
	<div style="width:100%;height:558px;background:url(../img/hengtiao.png) repeat-x;">
		<div style="width:682px;height:440px;border-radius:5px;background-color:white;position:relative;left:50%;margin-left:-341px;top:59px;">
			<div style="width:622px;height:50px;margin-left:20px;border-bottom:dashed 1px #BCBABB;padding-left:20px;line-height:50px;font-size:24px;">您注册的手机号码为:<span id="telephone_num"></span></div>
			<div style="width:600px;height:300px;margin-left:31px;padding:0 10px;">
				<ul style="margin:0;padding:10px 0;">
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<input id="msgcode" type="text" style="width:200px;height:50px;color:#CCCCCC;padding-left:10px;font-size:22px;float:left;margin-left:140px;color:black;" placeholder="手机确认码" />
						<a class="bg_button" href="javascript:void(0)" onclick="getIdentifyCode()" id="ident_a" style="margin-left:10px;float:left;">获取确认码</a>
						<div id="msgcodeerror" style="width:130px;height:50px;float:left;line-height:50px;font-size:10px;color:red;"></div>
					</li>
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<input id="password" type="text" style="width:310px;height:50px;color:#CCCCCC;padding-left:10px;font-size:22px;float:left;margin-left:140px;color:black;" placeholder="新密码" />
						<div id="pwderror" style="width:130px;height:50px;float:left;line-height:50px;font-size:10px;color:red;"></div>
					</li>
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<input id="repassword" type="text" style="width:310px;height:50px;color:#CCCCCC;padding-left:10px;font-size:22px;float:left;margin-left:140px;color:black;" placeholder="确认新密码" />
						<div id="repwderror" style="width:130px;height:50px;float:left;line-height:50px;font-size:10px;color:red;"></div>
					</li>
					<li style="list-style:none;width:600px;height:50px;padding:20px 0;">
						<a onclick="doReset()" class="by_button" style="margin-left:140px;" href="javascript:void(0)">确定</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div style="width:1000px;height:50px;position:relative;left:50%;margin-left:-500px;line-height:50px;text-align:center;font-family:'Arial';">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
	</div>
	</div>
</body>
</html>