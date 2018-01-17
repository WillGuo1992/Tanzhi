<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<script src="../js/index.js"></script>
	<script src="../js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>	
	<script>
		var productid,clickflag=0,todaytime,registertime;
		var oneday = new Date((new Date().getTime()-1000*60*60*24));
		var seven = new Date((new Date().getTime()-1000*60*60*24*6));
		var yyyy = oneday.getFullYear().toString();
		var SMM = oneday.getMonth()+1;
		var MM = SMM.toString();
		var dd = oneday.getDate().toString();
		if(dd.length == 1){
			dd = "0"+dd;
		}
		var onedayAgo = yyyy+"-"+MM+"-"+dd;
		$(document).ready( function(){//起始时间
 			/*document.getElementById("start").innerHTML=getToday();
 			document.getElementById("middle").innerHTML="~";
 			document.getElementById("end").innerHTML=getToday();*/
 			/*document.getElementById("compareAnTime").value = onedayAgo;
 			document.getElementById("pickAnTime").value = onedayAgo;*/
		}); 
		
		//判断前后两个日期相隔几天
		function validatePeriod(fyear,fmonth,fday,byear,bmonth,bday){
			if(fyear < byear){
			return true;
			}else if(fyear == byear){
			if(fmonth < bmonth){
			   return true;
			} else if (fmonth == bmonth){
			   if(fday <= bday){
			    return true;
			   }else {
			    return false;
			   }
			} else {
			   return false;
			}
			}else {
			return false;
			}
		}
		//判断年份是否是闰年

		function isLeapYear(year){
			if(year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))){
		     return true;
			}
			return false;
		}

		function compareDate(date1,date2){
		    var regexp=/^(\d{1,4})[-|\.]{1}(\d{1,2})[-|\.]{1}(\d{1,2})$/;
		    var monthDays=[0,3,0,1,0,1,0,0,1,0,0,1];
		    regexp.test(date1);
		    var date1Year=RegExp.$1;
		    var date1Month=RegExp.$2;
		    var date1Day=RegExp.$3;

		    regexp.test(date2);
		    var date2Year=RegExp.$1;
		    var date2Month=RegExp.$2;
		    var date2Day=RegExp.$3;
	
			if(validatePeriod(date1Year,date1Month,date1Day,date2Year,date2Month,date2Day)){
				firstDate=new Date(date1Year,date1Month,date1Day);
			     secondDate=new Date(date2Year,date2Month,date2Day);
	
			     var result=Math.floor((secondDate.getTime()-firstDate.getTime())/(1000*3600*24));
			     for(j=date1Year;j<=date2Year;j++){
			         if(isLeapYear(j)){
			             monthDays[1]=2;
			         }else{
			             monthDays[1]=3;  
			         }
			         for(i=date1Month-1;i<date2Month;i++){
			             result=result-monthDays[i];
			         }
			     }
			     return result+1;
			}else{
			return 'the first field must before the second date.';
			}
		}


		
		function getRegisterTime(appkey,campaignGroupId,campaignId){
			var url = "../TrackingSurveyServlet";
			var ccc = Math.random();
			if(campaignId == null){
				if(campaignGroupId == "allCampaigns"){
					var para = {
						appkey:appkey,
						type : 0,
						ccc : ccc
					}
				}else{
					var para = {
						appkey:appkey,
						campaignGroupId : campaignGroupId,
						type : 0,
						ccc : ccc
					}
				}
			}else{
				var para = {
					appkey:appkey,
					campaignId : campaignId,
					type : 0,
					ccc : ccc
				}
			}
			$.get(url,para,callbackRegister);
		}
		function callbackRegister(data){
			var array = data.split("-");
			if((parseInt(array[1]) < 10) && array[1].length > 1){
				array[1] = array[1].substring(1);
			}
			registertime = array[0] + '-' + array[1] + '-' + array[2];
			//getMonths(registertime);
		}
		
		function searchTimeFlag(vtf){
			IsCompare = 0;
			if(vtf!=6){
				document.getElementById("middle").innerHTML="~";
			    document.getElementById("end").innerHTML=getToday();
				if(vtf==0){
					TimeTab = 0;
					document.getElementById("start").innerHTML=getToday();
					document.getElementById("middle").innerHTML="~";
					document.getElementById("end").innerHTML=getToday();
				}else if(vtf==1){
					TimeTab = 1;
					document.getElementById("start").innerHTML=getDateAgo(1);
					document.getElementById("middle").innerHTML="~";
					document.getElementById("end").innerHTML=getDateAgo(1);
				}else if(vtf==7){
					TimeTab = 2;
					document.getElementById("start").innerHTML=getDateAgo(7);
					document.getElementById("middle").innerHTML="~";
					document.getElementById("end").innerHTML=getDateAgo(1);
					//document.getElementById("start").innerHTML=getDateAgo(6);
				}else if(vtf==30){
					TimeTab = 2;
					document.getElementById("start").innerHTML=getDateAgo(31);
					document.getElementById("middle").innerHTML="~";
					document.getElementById("end").innerHTML=getDateAgo(1);
					//document.getElementById("start").innerHTML=getDateAgo(30);
				}
			}else{
				TimeTab = 2;
				var s=document.getElementById("startTime").value;
				var e=document.getElementById("endTime").value;
				if(s==null||s==""||e==null||e==""){
					document.getElementById("datamessage").style.display="block";
					return;
				}else{
					var sm = s.split("-")[1];
					if(sm.length<2){
						sm = "0"+ sm;
					}
					s = s.split("-")[0]+"-"+sm+"-"+s.split("-")[2];
					var em = e.split("-")[1];
					if(em.length<2){
						em = "0"+ em;
					}
					e = e.split("-")[0]+"-"+em+"-"+e.split("-")[2];
					document.getElementById("start").innerHTML=s;
					document.getElementById("end").innerHTML=e;
					document.getElementById("middle").innerHTML="~";
					document.getElementById("datamessage").style.display="none";
					
					var timeflag = compareDate(s,e);
					var diff=DateDiff(e,s);
					if(!(diff==false)){
						$("#time").slideUp("slow");
						$("#Custom").hide();
					}
				}
				
				
			}
			
			execute();
			$("#Custom").hide();
			$("#time").slideUp("slow");

		}
		function getMonths(data){
				document.getElementById("selectMonth").options.length = 0;
				//document.getElementById("newselectMonth").options.length = 0;
				var arr = data.split("-");
				var firstYear = arr[0];
				var firstMonth = arr[1];
				var today = new Date();
				var months = new Array();
				if(firstMonth.indexOf("0")==0){
					firstMonth = firstMonth.substring(1);
				}
				if(parseInt(firstYear) < today.getFullYear() && 
						today.getMonth()+1 >=parseInt(firstMonth)){
					for(var i=0;i<today.getMonth()+1;i++){
						var mm = today.getMonth()+1-i;
						months[i] = today.getFullYear() + "-" + mm;
					} 
					var length = 13-months.length;
					for(var i=0;i <length;i++){
						var m = 12 - i;
						months[months.length] = firstYear + "-" + m;
					}
					
				}else if(parseInt(firstYear)<today.getFullYear() && 
				   parseInt(firstMonth)>today.getMonth()+1){
					for(var i=0;i<today.getMonth()+1;i++){
						var mm = today.getMonth()+1-i;
						months[i] = today.getFullYear() + "-" + mm;
					} 
					var length = 13-parseInt(firstMonth);
					for(var i=0;i<length;i++){
						var m = 12 - i;
						months[months.length] = firstYear + "-" +  m;
					}	
						
				}else if(parseInt(firstYear) == today.getFullYear()){
					for(var i=0;i<today.getMonth()+2-firstMonth;i++){
						var mm = today.getMonth()+1-i;
						months[i] = today.getFullYear() + "-" + mm;
					}
				}
				
				callBackMonths(months);
			
		}
		
		function callBackMonths(data){
			if(data!=null&&data!=""){
				var array=data;
				for(var i=0;i<array.length;i++){
					$("#selectMonth").append("<option value='"+array[i]+"' id='"+array[i]+"'>"+array[i].split('-')[1]+"月</option>");
					//$("#newselectMonth").append("<option value='"+array[i]+"' id='"+array[i]+"'>"+array[i].split('-')[1]+"月</option>");
					$("#selectMonth option#"+array[0]).attr('selected',true);
					//$("#newselectMonth option#"+array[0]).attr('selected',true);
				}
					//$("#newselectMonth").sSelect();
					$("#selectMonth").sSelect();
					$("#campaignselect").sSelect();
			}
		}
		
		
		function selectMonthf(){
			document.getElementById("middle").innerHTML="~";
			var date = new Date();
			var tmpval=$("#selectMonth").val();
			var strYear = tmpval.split('-')[0];
			var month=tmpval.split('-')[1];
			var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);
			if(parseInt(strYear)%4 == 0 && parseInt(strYear)%100 != 0){   
		        daysInMonth[2] = 29;   
		    }   
		    if(parseInt(month)==-1){
				month=1;
		    }
		    var endday=daysInMonth[month];
		    var todayM = new Date().getMonth()+1;
		    if(strYear == new Date().getFullYear().toString() && month == todayM.toString()){
		    	endday = new Date().getDate().toString();
		    	if(endday.length == 1){
		    		endday = "0"+endday;
		    	}
		    }
		    var startvalue=strYear+"-"+month+"-01";
		    var endvalue=strYear+"-"+month+"-"+endday;
		    document.getElementById("start").innerHTML=startvalue;
			document.getElementById("end").innerHTML=endvalue;
			starttime=startvalue;
			endtime=endvalue;
			document.getElementById("datamessage").style.display="none";
			$("#time").slideUp("show");
			$("#Custom").hide();
			getAnalysis($("#campaignselect").val());
		}
		
		
		function closeWin(){
			document.getElementById("datamessage").style.display="none";
		}
		
	</script>
</head>
<body>
	<div class="relative" style="z-index:99; float:left;margin-left:10px;">
    	<a id="time_a" class="time" href="javascript:void(0);" onclick="timet('time','timecolse','Custom',this);closeWin();">
    	<span><font style="color:#CCCCCC" id="start"></font><font id="middle" style="margin:0 5px;color:#CCCCCC">请选择日期</font><font id="end" style="color:#CCCCCC"></font><em></em></span></a>
        <ul class="timetxt" id="time">
			<li id="li_today">
        		<a href="javascript:void(0)" class="select" id="aid1" onclick="searchTimeFlag(0);">今日</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="aid2" onclick="searchTimeFlag(1);">昨日</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="aid3" class="on_choose" onclick="searchTimeFlag(7);">近7日</a>
            </li>
            <li>
            	<a href="javascript:void(0)" class="select" id="aid4" onclick="searchTimeFlag(30);">近30日</a>
            </li>
            <li style="border-bottom:0;">
				<a href="javascript:void(0)" class="select" onclick="addCustom('Custom')">自定义</a>			
				<div id="Custom" class="hide Custom">
					<input type="text" id="startTime" name="startTime" class="Wdate" style="margin-left:0;" onfocus="WdatePicker({dateFmt:'yyyy-M-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'})"/>~<input type="text" id="endTime" name="endTime"class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-M-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})"/><small id="datamessage" style="display: none; margin-left:-7px; float:left"  >请填写日期</small><a href="javascript:void(0)" class="Buttoncr r timecolse" id="timecolse">取消</a> <a href="javascript:void(0)" class="Button r"  onclick="searchTimeFlag(6);">确定</a>
				</div>
			</li>
		</ul>
    </div>
</body>
</html>
