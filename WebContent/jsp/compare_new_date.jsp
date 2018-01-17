<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<script src="../js/index.js"></script>
	<script src="../js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>	
	<script type="text/javascript">
		function searchCompareNewTimeFlag(vtf){
			IsCompare = 1;
			if(vtf!=6){
				if(vtf==0){
					document.getElementById("c_new_start").innerHTML = "请选择日期";
					IsCompare = 0;
					new_execute();
				}else if(vtf==1){
					document.getElementById("c_new_start").innerHTML = getDateAgo(1);
					new_compare_execute();
				}else if(vtf==7){
					document.getElementById("c_new_start").innerHTML = getDateAgo(7);
					new_compare_execute();
				}else if(vtf==30){
					document.getElementById("c_new_start").innerHTML = getDateAgo(31);
					new_compare_execute();
				}
			}else{
				var s=document.getElementById("c_new_startTime").value;
				var sm = s.split("-")[1];
				if(sm.length<2){
					sm = "0"+ sm;
				}
				s = s.split("-")[0]+"-"+sm+"-"+s.split("-")[2];
				if(s==null||s==""){
					document.getElementById("c_new_datamessage").style.display="block";
				}else{
					document.getElementById("c_new_start").innerHTML = s;
					document.getElementById("c_new_datamessage").style.display="none";
					new_compare_execute();
				}
			}
			$("#c_new_Custom").hide();
			$("#c_new_time").slideUp("slow");
		}
	</script>
</head>
<body>
	<div class="relative" style="z-index:99; float:left">
    	<a id="c_new_time_a" class="time" href="javascript:void(0);" onclick="timet('c_new_time','timecolse','c_new_Custom',this);closeWin();">
    	<span><font id="c_new_start"></font><em></em></span></a>
        <ul class="timetxt" id="c_new_time">
			<li>
        		<a href="javascript:void(0)" class="select" id="c_new_aid1" onclick="searchCompareNewTimeFlag(0);">不对比</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="c_new_aid2" onclick="searchCompareNewTimeFlag(1);">前一日</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="c_new_aid3" class="on_choose" onclick="searchCompareNewTimeFlag(7);">前一周</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="c_new_aid4" onclick="searchCompareNewTimeFlag(30);">前一月</a>
            </li>
            <li style="border-bottom:0;">
				<a href="javascript:void(0)" class="select" onclick="addCustom('Custom')">自定义对比</a>			
				<div id="c_new_Custom" class="hide Custom">
					对比起始日 : <input type="text" id="c_new_startTime" name="startTime" class="Wdate" style="margin-left:0;" onfocus="WdatePicker({dateFmt:'yyyy-M-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'})"/><small id="c_new_datamessage" style="display: none; margin-left:-7px; float:left"  >请填写日期</small><a href="javascript:void(0)" class="Buttoncr r timecolse">取消</a> <a href="javascript:void(0)" class="Button r"  onclick="searchCompareNewTimeFlag(6);">确定</a>
				</div>
			</li>
		</ul>
    </div>
</body>
</html>
