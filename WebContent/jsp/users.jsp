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
	var ChosenUserId = 0;
	$(document).ready(function () {
		$("#mall_name").html(mall_name);
		$("#content").css("height",document.body.scrollHeight);
		$("#ul_n").css("height",$("#content").css("height"));
	});
	
	function execute () {
		getUserList();
	}
	function ecancel () {
		$("#edit_div").slideUp("slow");
	}
	function icancel () {
		$("#insert_div").slideUp("slow");
	}
	function deleteUser () {
		var radios = document.getElementsByName("user");
		for (var i = 0; i < radios.length; i++) {
			if(radios[i].checked){
				ChosenUserId = radios[i].value;
			}
		}
		if(ChosenUserId == 0){
			$("#errormsg").html("请选择一个用户");
		}else{
			doDelete(ChosenUserId);
		}
	}
	function doDelete (uid) {
		if(confirm("确认删除该用户?")){
			var url = basePath + "jaxrs/account/user/delete";
			var cxl = Math.random();
			var para = {
					userId : uid,cxl : cxl
			}
			$.post(url,para,doDeleteCallBack,"json");
		}
	}
	function doDeleteCallBack () {
		var code = data.code;
		if(code == 0){
			alert("删除成功");
			ChosenUserId = 0;
			getUserList();
		}else{
			alert("删除失败");
			ChosenUserId = 0;
			getUserList();
		}
	}
	function editUser () {
		$("#edit_div").slideDown("slow");
		var radios = document.getElementsByName("user");
		for (var i = 0; i < radios.length; i++) {
			if(radios[i].checked){
				ChosenUserId = radios[i].value;
			}
		}
		if(ChosenUserId == 0){
			$("#errormsg").html("请选择一个用户");
		}else{
			loadUserInfo(ChosenUserId);
		}
	}
	function loadUserInfo (uid) {
		var url = basePath + "jaxrs/account/user/info";
		var cxl = Math.random();
		var para = {
				userId : uid,cxl : cxl
		}
		$.post(url,para,loadUserInfoCallBack,"json");
	}
	function loadUserInfoCallBack (data) {
		var code = data.code;
		if(code == 0){
			var user = data.info;
			$("#e_username").val(user.username);
			$("#e_password").val(user.password);
			$("#e_telephone").val(user.telephone);
			$("#e_description").val(user.description);
		}
	}
	function doEdit () {
		var url = basePath + "jaxrs/account/user/edit";
		var cxl = Math.random();
		var eusername = $("#e_username").val();
		if(eusername == null||eusername == ""){
			$("#errormsg").html("用户名不能为空");
		}
		var epassword = $("#e_password").val();
		if(epassword == null||epassword == ""){
			$("#errormsg").html("密码不能为空");
		}
		if(epassword.length<6||epassword.length>20){
			$("#errormsg").html("密码长度为6-20位");
		}
		var etelephone = $("#e_telephone").val();
		var edesc = $("#e_description").val();
		var para = {
				userId : ChosenUserId,username : eusername,password : epassword,telephone : etelephone,description : edesc,cxl : cxl
		}
		$.post(url,para,doEditCallBack,"json");
	}
	function doEditCallBack (data) {
		var code  = data.code;
		if(code == 0){
			alert("编辑成功");
			ChosenUserId = 0;
			getUserList();
		}else{
			alert("编辑失败");
			ChosenUserId = 0;
			getUserList();
		}
	}
	function insertUser () {
		$("#insert_div").slideDown("slow");
	}
	function doInsert () {
		var url = basePath + "jaxrs/account/user/add";
		var cxl = Math.random();
		var iusername = $("#i_username").val();
		if(iusername == null||iusername == ""){
			$("#errormsg").html("用户名不能为空");
		}
		var ipassword = $("#i_password").val();
		if(ipassword == null||ipassword == ""){
			$("#errormsg").html("密码不能为空");
		}
		if(ipassword.length<6||ipassword.length>20){
			$("#errormsg").html("密码长度为6-20位");
		}
		var itelephone = $("#i_telephone").val();
		var idesc = $("#i_description").val();
		var para = {
				mallId : MallId,username : iusername,password : ipassword,telephone : itelephone,description : idesc,cxl : cxl
		}
		$.post(url,para,doInsertCallBack,"json");
	}
	function doInsertCallBack (data) {
		var code  = data.code;
		if(code == 0){
			alert("添加成功");
			ChosenUserId = 0;
			getUserList();
		}else{
			alert("添加失败");
			ChosenUserId = 0;
			getUserList();
		}
	}
	function getUserList () {
		var url = basePath + "jaxrs/account/user/list";
		var cxl = Math.random();
		var para = {
				mallId : MallId,cxl : cxl
		}
		$.post(url,para,getUserListCallBack,"json");
	}
	function getUserListCallBack (data) {
		var code = data.code;
		if(code == 0){
			var users = data.users;
			var usersdata = new Array();
			if(users != null&&users.length > 0){
				for (var i = 0; i < users.length; i++) {
					var userdata = new Array();
					userdata.push(users[i].id);
					userdata.push(users[i].username);
					userdata.push(users[i].password);
					userdata.push(users[i].telephone);
					userdata.push(users[i].description);
					usersdata.push(userdata);
				}
			}
			if(usersdata.length > 0){
				$('#user_table').html('<table cellpadding="0px" cellspacing="0px" width="100%" height="20px" border="0" id="camTrendTable1" class="table_style1"></table>');
				$("#camTrendTable1").dataTable({
				"fnDrawCallback" : function(oSettings) {
					highlight("camTrendTable1");
				},
					"aaData" : usersdata,
					"bPaginate" : true, //去掉选择显示多少条
					"bLengthChange" : true,//左上角的分页
					"sDom" : 'rt<"page"flp>',
					"bFilter" : false, //去掉右上方查询查询
					"bSort" : false, //去掉排序							
					"bInfo" : true,
					"iDisplayLength" : 9,
					'sPaginationType' : 'full_numbers', //分页样式
					"aoColumns" : [
							{
								"sTitle" : "用户名",
								"sClass" : "center",
								"fnRender" : function(obj) {
									var uid = obj.aData[0];
									var uname = obj.aData[1];
									var info = "<input type='radio' name='user' value='"+uid+"'/>&nbsp;&nbsp;&nbsp;<span>"+uname+"</span>"
									return info;
								}
							},
							{
								"sTitle" : "密码",
								"sClass" : "center",
								"fnRender" : function(obj) {
									var info = obj.aData[2];
									return info;
								}
							},
							{
								"sTitle" : "手机号",
								"sClass" : "center",
								"fnRender" : function(obj) {
									var info = obj.aData[3];
									return info;
								}
							},
							{
								"sTitle" : "备注",
								"sClass" : "center",
								"fnRender" : function(obj) {
									var info = obj.aData[4];
									return info;
								}
							}],
					"oLanguage" : {
						"sLengthMenu" : "",
						"sZeroRecords" : "对不起，查询不到任何相关数据",
						"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
						"sInfoEmtpy" : "找不到相关数据",
						"sInfoFiltered" : "数据表中共为 _MAX_ 条记录)",
						"sProcessing" : "正在加载中...",
						"sSearch" : "搜索",
						"sUrl" : "", //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
						"oPaginate" : {
							"sFirst" : "&lt;&lt;",
							"sPrevious" : " &lt; ",
							"sNext" : " &gt; ",
							"sLast" : " &gt;&gt;"
						}
					}
				//多语言配置
				});
			}else{
				$("#user_table").html("<div style='width:100%;height:360px;text-align:center;line-height:360px;'>未查询到数据</div>");
			}
		}else{
			$("#user_table").html("<div style='width:100%;height:360px;text-align:center;line-height:360px;'>未查询到数据</div>");
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
	
</script>
</head>
<body onload="execute()">
<jsp:include page="summaries.jsp" />
<jsp:include page="aledit.jsp" />
<div class="container">
	<div class="banner">
		<div class="logo"></div>
		<div id="mall_name" class="mall_name" style="font-size:22px;"></div>
		<a class="usericon" href="javascript:void(0)" onclick="showManageDiv()"><div style="width:20px;height:20px;position:relative;left:50%;top:50%;margin-left:-10px;margin-top:-10px;background:url(../img/jingling1.png) -92px -140px;"></div></a>
		<a class="seticon" href="mallinfo.jsp"><div style="width:20px;height:20px;position:relative;left:50%;top:50%;margin-left:-10px;margin-top:-10px;background:url(../img/jingling1.png) -141px -140px;"></div></a>
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
			<li><a class="select"  href="">系统管理</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a href="mallinfo.jsp">企业信息</a></li>
					<li><a href="devices.jsp">设备管理</a></li>
					<li><a class="select" href="#">用户管理</a></li>
					<li><a href="">地图管理</a></li>
				</ul>
			</div>
			<div style="margin:70px 20px 0 20px;max-height:460px;background-color:white;border: solid 1px #BCBABB;">
				<div class="dataDiv_title">
					<div class="dataDiv_title_txt">用户列表</div>
				</div>
				<div style="width:100%;text-align:center;line-height:360px" id="user_table"></div>
			</div>
			<div style="height:40px;padding:5px 0;margin:0 20px;">
				<a style="display:block;width:100px;height:40px;float:left;background-color:#348AD5;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;color:white;" onclick="insertUser()" href="javascript:void(0)">添加</a>
				<a style="display:block;width:100px;height:40px;float:left;background-color:#348AD5;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;margin-left:20px;color:white;" onclick="editUser()" href="javascript:void(0)">编辑</a>
				<a style="display:block;width:100px;height:40px;float:left;background-color:#D64D54;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;margin-left:20px;color:white;" onclick="deleteUser()" href="javascript:void(0)">删除</a>
				<p id="errormsg" style="width:200px;height:40px;float:left;margin:0 0 0 10px;line-height:40px;color:red;"></p>
			</div>
			<div id="edit_div" style="height:50px;padding:5px 0;margin:0 20px;display:none;">
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:5px;">用户名:<input id="e_username" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:10px;">密码:<input id="e_password" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:10px;">手机号:<input id="e_telephone" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:10px;">备注:<input id="e_description" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:100px;height:50px;float:left;margin-left:10px;"><a style="display:block;width:100px;height:40px;float:left;background-color:#348AD5;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;margin-top:5px;color:white;" onclick="doEdit()" href="javascript:void(0)">保存</a></div>
				<div style="width:100px;height:50px;float:left;margin-left:10px;"><a style="display:block;width:100px;height:40px;float:left;background-color:#999999;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;margin-top:5px;color:white;" onclick="ecancel()" href="javascript:void(0)">取消</a></div>
			</div>
			<div id="insert_div" style="height:50px;padding:5px 0;margin:0 20px;display:none;">
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:5px;">用户名:<input id="i_username" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:10px;">密码:<input id="i_password" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:10px;">手机号:<input id="i_telephone" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:200px;height:50px;text-align:center;line-height:50px;float:left;margin-left:10px;">备注:<input id="i_description" style="width:140px;height:40px;" tyle="text" /></div>
				<div style=""></div>
				<div style="width:100px;height:50px;float:left;margin-left:10px;"><a style="display:block;width:100px;height:40px;float:left;background-color:#009933;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;margin-top:5px;" onclick="doInsert()" href="javascript:void(0)">保存</a></div>
				<div style="width:100px;height:50px;float:left;margin-left:10px;"><a style="display:block;width:100px;height:40px;float:left;background-color:#999999;border-radius:5px;text-align:center;line-height:40px;text-decoration:none;margin-top:5px;" onclick="icancel()" href="javascript:void(0)">取消</a></div>
			</div>
		</div>
	</div>
	<div class="footer">北京航空航天大学   | 软件开发环境国家重点实验室 </div>
</div>
</body>
</html>