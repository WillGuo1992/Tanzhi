<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<script type="text/javascript">
	$(document).ready(function () {
		$("#ul_n").css("height",window.screen.availHeight);
		$("#content").css("height",window.screen.availHeight);
	});
</script>
</head>
<body>
<div class="container">
	<div class="banner">
		<div class="logo"></div>	
		<div id="mall_name">湘西部落</div>
	</div>
	<div id="content" class="content">
		<ul id="ul_n" class="navigation">
			<h2></h2>
			<li><a href="#">整体客流</a></li>
			<li><a href="#">楼层客流</a></li>
			<li><a href="#">业态客流</a></li>
			<li><a href="#">品牌店铺</a></li>
			<li><a href="#">顾客模型</a></li>
			<li><a href="#">特征日模型</a></li>
			<li><a href="#">手机数据</a></li>
			<li><a href="#">热力图</a></li>
		</ul>
		<div class="maindiv">
			<div id="tab_bar_div">
				<ul class="tab_bar">
					<li><a href="#">整体到达顾客</a></li>
					<li><a href="#">各入口比例</a></li>
					<li><a href="#">整体停留顾客</a></li>
					<li><a href="#">整体停留时长</a></li>
				</ul>
			</div>
			<table class="card">
				<tbody>
					<tr class="card_tr">
						<td class="card_td">
							<div id="box_1" class="card_box">
								<dl class="box_dl">
									<dt class="box_dl_title">今日累计</dt>
									<div class="box_dl_content">
										<div id="today_count" class="box_icon"></div>
										<div class="box_data">2358</div>
										<div class="clear"></div>
									</div>
									<div class="box_dl_foot">(截止到此刻)</div>
								</dl>
							</div>
						</td>
						<td class="card_td">
							<div id="box_2" class="card_box">
								<dl class="box_dl">
									<dt class="box_dl_title">对比昨日此刻</dt>
									<div class="box_dl_content">
										<div id="yesterday_compare" class="box_icon"></div>
										<div class="box_data">5%</div>
									</div>
									<div class="box_dl_foot">(3033)</div>
								</dl>
							</div>
						</td>
						<td class="card_td">
							<div id="box_3" class="card_box">
								<dl class="box_dl">
									<dt class="box_dl_title">对比上周同日此刻</dt>
									<div class="box_dl_content">
										<div id="week_compare" class="box_icon"></div>
										<div class="box_data">5%</div>
									</div>
									<div class="box_dl_foot">(3033)</div>
								</dl>
							</div>
						</td>
						<td class="card_td">
							<div id="box_4" class="card_box">
								<dl class="box_dl">
									<dt class="box_dl_title">对比上月同日此刻</dt>
									<div class="box_dl_content">
										<div id="month_compare" class="box_icon"></div>
										<div class="box_data">5%</div>
									</div>
									<div class="box_dl_foot">(3033)</div>
								</dl>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div id="tool_bar">
				<ul class="tool_bar_ul">
					<li><a href="#">今日</a></li>
					<li><a href="#">昨日</a></li>
					<li><a href="#">选择时段</a></li>
				</ul>
				<div id="date_tool" class="date_tool"><jsp:include page="../jsp/date.jsp" /></div>
				<div id="compare_date_tool" class="compare_date_tool"><jsp:include page="../jsp/compare_date.jsp" /></div>
				<div style="float:right;color:white;text-align:center;padding-top:14px;margin-right:10px;">对比时段:</div>
			</div>
			<div class="dataDiv">
				<div class="dataDiv_title">整体到达</div>
			</div>
		</div>
	</div>
	<div class="footer"></div>
</div>
</body>
</html>