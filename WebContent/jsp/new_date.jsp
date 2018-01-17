<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<script src="../js/index.js"></script>
	<script src="../js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>	
	<script type="text/javascript">
	
		function searchNewTimeFlag(vtf){
			IsCompare = 1;
			if(vtf!=6){
				if(vtf==0){
					document.getElementById("new_start").innerHTML = "请选择日期";
					IsCompare = 0;
					new_execute();
				}else if(vtf==1){
					document.getElementById("new_start").innerHTML = getDateAgo(1);
					new_execute();
				}else if(vtf==7){
					document.getElementById("new_start").innerHTML = getDateAgo(7);
					new_execute();
				}else if(vtf==30){
					document.getElementById("new_start").innerHTML = getDateAgo(31);
					new_execute();
				}
			}else{
				var s=document.getElementById("new_startTime").value;
				var sm = s.split("-")[1];
				if(sm.length<2){
					sm = "0"+ sm;
				}
				s = s.split("-")[0]+"-"+sm+"-"+s.split("-")[2];
				if(s==null||s==""){
					document.getElementById("new_datamessage").style.display="block";
				}else{
					document.getElementById("new_start").innerHTML = s;
					document.getElementById("new_datamessage").style.display="none";
					new_execute();
				}
			}
			$("#new_Custom").hide();
			$("#new_time").slideUp("slow");
		}
		function closeWin(){
			document.getElementById("new_datamessage").style.display="none";
		}
	</script>
</head>
<body>
	<div class="relative" style="z-index:99; float:left;margin-left:10px;">
    	<a id="new_time_a" class="time" href="javascript:void(0);" onclick="timet('new_time','timecolse','new_Custom',this);closeWin();">
    	<span><font id="new_start"></font><em></em></span></a>
        <ul class="timetxt" style="z-index:999999;" id="new_time">
            <li>
            	<a href="javascript:void(0)" class="select" id="new_aid2" onclick="searchNewTimeFlag(1);">昨日</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="new_aid3" class="on_choose" onclick="searchNewTimeFlag(7);">7日前</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="new_aid4" onclick="searchNewTimeFlag(30);">30日前</a>
            </li>
            <!-- <li style="border-bottom:0;">
				<a href="javascript:void(0)" class="select" onclick="addCustom('n_custom')">自定义</a>			
				<div id="n_custom" class="hide Custom" style="z-index:999999">
					选择日期 : <input type="text" id="new_starttime" name="startTime" class="Wdate" style="margin-left:0;" onfocus="WdatePicker({datefmt:'yyyy-m-dd'})"/>
					<small id="new_datamessage" style="display: none; margin-left:-7px; float:left"  >请填写日期</small>
					<a href="javascript:void(0)" class="buttoncr r timecolse" id="ntimecolse">取消</a> 
					<a href="javascript:void(0)" class="button r"  onclick="searchnewtimeflag(6);">确定</a>
				</div>
			</li> -->
			
			
			
			<li style="border-bottom:0;">
				<a href="javascript:void(0)" class="select" onclick="addCustom('n_custom')">自定义</a>			
				<div id="n_custom" class="hide Custom" style="z-index:999999">
					选择日期 : <input type="text" id="new_startTime" name="startTime" class="Wdate" style="margin-left:0;" onfocus="WdatePicker({dateFmt:'yyyy-M-dd',maxDate:'%y-%M-\#\{%d-1\}'})"/>
					<small id="new_datamessage" style="display: none; margin-left:-7px; float:left"  >请填写日期</small>
					<a href="javascript:void(0)" class="Buttoncr r timecolse" id="timecolse">取消</a> 
					<a href="javascript:void(0)" class="Button r"  onclick="searchNewTimeFlag(6);">确定</a>
				</div>
			</li>
			
			
			<!-- <li style="border-bottom:0;">
				<a href="javascript:void(0)" class="select" onclick="addCustom('c_Custom')">自定义对比</a>			
				<div id="c_Custom" class="hide Custom">
					对比起始日 : <input type="text" id="c_startTime" name="startTime" class="Wdate" style="margin-left:0;" onfocus="WdatePicker({dateFmt:'yyyy-M-dd'})"/><small id="c_datamessage" style="display: none; margin-left:-7px; float:left"  >请填写日期</small><a href="javascript:void(0)" class="Buttoncr r timecolse" id="ctimecolse">取消</a> <a href="javascript:void(0)" class="Button r"  onclick="searchCompareTimeFlag(6);">确定</a>
				</div>
			</li> -->
		</ul>
    </div>
</body>
</html>
