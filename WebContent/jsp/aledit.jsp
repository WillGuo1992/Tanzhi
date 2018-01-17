<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<link type="text/css" rel="stylesheet" href="../css/ui.css" />
	<script type="text/javascript" src="../js/index.js"></script>
	<script type="text/javascript">
		var msg_code = "";
		var timecount = 60;
		var t,rt;
		$(document).ready(function(){
			/*$("#aleditbg_1").show();
			$("#aleditcont_1").show();*/
			$("#now_telephone").html(Telephone);
			if(LoginLevel != 0){
				$("#edit_tab").hide();
				$("#phone_div").hide();
				$("#pwd_div").show();
			}
		});
		function closeAlertWin () {
			$("#aleditbg_1").hide();
			$("#aleditcont_1").hide();
			$("#telephone").html("");
			$("#msgcode").html("");
			$("#ex_password").html("");
			$("#new_password").html("");
			$("#re_password").html("");
			$("#phone_secc").css("display","none");
			$("#pwd_secc").css("display","none");
			if(LoginLevel == 0||LoginLevel == "0"){
				chooseEditTab($("#phone_tab"),'phone_div','pwd_div')
			}
		}
		function chooseEditTab(obj,txt1,txt2){
			/*$("#telephone").html("");
			$("#msgcode").html("");
			$("#telephoneerror").html("");
			$("#msgerror").html("");
			$("#ex_password").html("");
			$("#new_password").html("");
			$("#re_password").html("");
			$("#phone_secc").css("display","none");
			$("#pwd_secc").css("display","none");*/
			$(obj).css("background-color","#FFFFFF");
			$(obj).siblings("div").css("background-color","#E3E3E3");
			$("#"+txt2).css("display","none");
			$("#"+txt1).css("display","block");
		}
		function getIdentifyCode () {
			var telephone = $("#telephone").val();
			if(telephone == null||telephone == ""){
				$("#telephoneerror").html("请输入手机号码");
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
	    function doChangePhone () {
	    	var url = basePath + "jaxrs/account/update/telephone";
	    	var cxl = Math.random();
	    	var telephone = $("#telephone").val();
			if(telephone == null||telephone == ""){
				$("#telephoneerror").html("请输入手机号码");
				return;
			}
			var msgcode = $("#msgcode").val();
			if(msgcode == null||msgcode == ""){
				$("#msgerror").html("请输入确认码");
				return;
			}else if(msgcode != msg_code){
				$("#msgerror").html("短信确认码错误");
				return;
			}
	    	var para = {
	    			loginid : LoginId, telephone : telephone, cxl : cxl
	    	}
	    	$.post(url,para,doChangePhoneCallBack,"json");
	    }
	    function doChangePhoneCallBack (data) {
	    	var code = data.code;
	    	$("#phone_div").css("display","none");
	    	$("#phone_secc").css("display","block");
	    	if(code == 0){
	    		$("#phone_secc").html("绑定新号码成功");
	    	}else{
	    		$("#phone_secc").html("绑定新号码失败,请重试");
	    	}
	    }
	    function doChangePwd () {
	    	var url = basePath + "jaxrs/account/update/password";
	    	var cxl = Math.random();
	    	var expassword = $("#ex_password").html();
	    	if(expassword == null||expassword == ""){
	    		$("#expwderror").html("请输入原密码");
	    		return;
	    	}
	    	var newpassword = $("#new_password").html();
	    	if(newpassword == null||newpassword == ""){
	    		$("#newpwderror").html("请输入新密码");
	    		return;
	    	}else if(newpassword.length < 6||newpassword.length > 20){
	    		$("#newpwderror").html("密码长度6-20位");
	    		return;
	    	}
	    	var repassword = $("#re_password").html();
	    	if(repassword != newpassword){
	    		$("#repwderror").html("密码输入不一致");
	    		return;
	    	}
	    	var para = {
	    			loginid : LoginId, loginlevel : LoginLevel,password : newpassword,expassword : expassword,cxl : cxl
	    	}
	    	$.post(url,para,doChangePwdCallBack,"json");
	    }
	    function doChangePwdCallBack (data) {
	    	var code = data.code;
	    	$("#pwd_div").css("display","none");
	    	$("#pwd_secc").css("display","block");
	    	if(code == 0){
	    		$("#pwd_secc").html("密码修改成功！");
	    	}else if(code == -1){
	    		$("#expwderror").html("原密码错误");
	    	}else{
	    		$("#pwd_secc").html("密码修改失败，请重试");
	    	}
	    }
	    function focusInput (txt) {
	    	$("#"+txt).html("");
	    }
	</script>
</head>
<body>
	<div class="xy" id="aleditbg_1"></div>
	<div class="xytxt" id="aleditcont_1" style="width:500px;height:500px;position:absolute;left:50%;top:50%;margin-left:-250px;margin-top:-250px;border:0px;background-color:#FFFFFF;">
		<div style="width:500px;height:160px;background:url(../img/edit_banner.png);border-top-left-radius:3px;border-top-right-radius:3px;">
			<div style="float:left;width:100px;height:150px;font-size:22px;color:white;line-height:36px;text-align:center;font-family:'微软雅黑'">账户设置</div>
			<a href="#" style="border:0px;color:white;" onclick="closeAlertWin()" class="close">×</a>
			<div style="width:185px;height:80px;position:relative;top:40px;left:158px;border-radius:4px;background-color:white;opacity:0.4;border:1px solid white;">
				<!-- <div style="width:150px;height:30px;font-size:20px;font-family:'微软雅黑';text-align:center;">
					用户名
				</div>
				<div style="width:150px;height:20px;margin-left:18px;">
					
				</div> -->
			</div>
		</div>
		<div id="edit_tab" style="width:500px;height:50px;">
			<div id="phone_tab" style="width:250px;height:50px;text-align:center;line-height:50px;float:left;background-color:#FFFFFF;font-family:'微软雅黑';font-size:18px;cursor:pointer;" onclick="chooseEditTab(this,'phone_div','pwd_div')">手机绑定</div>
			<div id="pwd_tab" style="width:250px;height:50px;text-align:center;line-height:50px;float:left;background-color:#E3E3E3;font-family:'微软雅黑';font-size:18px;cursor:pointer;" onclick="chooseEditTab(this,'pwd_div','phone_div')">更改密码</div>
		</div>
		<div id="phone_div" style="width:500px;height:285px;">
			<div style="width:500px;height:90px;text-align:center;line-height:28px;font-size:16px;font-family:'微软雅黑'">
				您当前绑定的手机号码为:<font id="now_telephone" style="color:red;"></font><br>请填写新的手机号码并获取短信验证码<br>(绑定手机号可用于登录或找回密码)
			</div>
			<ul style="margin:0;padding:0;">
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<input id="telephone" type="text" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;font-size:14px;padding-left:10px;margin-left:125px;float:left;" placeholder="新手机号" onfocus="focusInput('telephoneerror')" /><span style="display:block;width:100px;height:40px;margin-left:10px;float:left;color:red;line-height:40px;font-size:10px;" id="telephoneerror"></span>
				</li>
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<input id="msgcode" type="text" style="display:block;width:120px;height:40px;border: solid 1px #A7A7A7;float:left;font-size:14px;padding-left:10px;margin-left:125px;" placeholder="短信确认码" onfocus="focusInput('msgerror')"/>
					<a href="javascript:void(0)" id="ident_a" class="sg_button" onclick="getIdentifyCode()" style="float:left;margin-left:20px;">获取确认码</a>
					<span style="display:block;width:100px;height:40px;margin-left:10px;float:left;color:red;line-height:40px;font-size:10px;" id="msgerror"></span>
				</li>
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<a onclick="doChangePhone()" class="sy_button" style="margin-left:125px;" href="javascript:void(0)">绑定</a>
				</li>
			</ul>
		</div>
		<div id="pwd_div" style="width:500px;height:285px;display:none;">
			<div style="width:500px;height:30px;text-align:center;line-height:28px;font-size:16px;font-family:'微软雅黑'">请填写您的初始密码及新密码,密码长度为6-20位</div>
			<ul style="margin:0;padding:0;">
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<input id="ex_password" type="password" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;font-size:14px;padding-left:10px;margin-left:125px;float:left;" placeholder="原密码"/><span style="display:block;width:100px;height:40px;margin-left:10px;float:left;color:red;line-height:40px;" id="expwderror"></span>
				</li>
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<input id="new_password" type="password" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;font-size:14px;padding-left:10px;margin-left:125px;float:left;" placeholder="新密码"/><span style="display:block;width:100px;height:40px;margin-left:10px;float:left;color:red;line-height:40px;" id="newpwderror"></span>
				</li>
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<input id="re_password" type="password" style="display:block;width:240px;height:40px;border: solid 1px #A7A7A7;font-size:14px;padding-left:10px;margin-left:125px;float:left;" placeholder="确认新密码"/><span style="display:block;width:100px;height:40px;margin-left:10px;float:left;color:red;line-height:40px;" id="repwderror"></span>
				</li>
				<li style="list-style:none;width:500px;height:40px;display:block;padding:10px 0;">
					<a onclick="doChangePwd()" class="sy_button" style="margin-left:125px;" href="javascript:void(0)">确定</a>
				</li>
			</ul>
		</div>
		<div style="display:none;width:500px;height:285px;line-height:285px;text-align:center;font-size:24px;" id="phone_secc">绑定新号码成功！</div>
		<div style="display:none;width:500px;height:285px;line-height:285px;text-align:center;font-size:24px;" id="pwd_secc">修改密码成功！</div>
	</div>
</body>
</html>
