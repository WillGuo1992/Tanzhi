function datasort(id,def,row){
	var row=row  ||  "";
			if (row)row ="."+row;
		var datasortID=$(id+" thead th"+row);
	for(i=0; i<datasortID.length; i++){
		//jQuery(datasortID[i]).attr("onclick","datasortonclick('"+i+"','"+id+" thead th"+row+"')");
		(function(a){
			jQuery(datasortID[a]).click(function(){
				for(var b=$(id+" tr td span.l").length,c=0; b>0;b--,c++){
					$(id+" tr td span.l:eq("+c+") span.relative").css({"z-index":"9999"+b});
				}
				var flg=jQuery(datasortID[a]).hasClass("hover");
				$(id+" thead th"+row).removeClass("hover");
				$(id+" thead th"+row).removeClass("visited");
				if(flg==true)jQuery(datasortID[a]).addClass("visited");
				else jQuery(datasortID[a]).addClass("hover");
				//datasortonclick(a,id+" thead th"+row)
			});
			jQuery(datasortID[a]).mouseover(function(){
				//jQuery(datasortID[a]).addClass("hover");
			});
			jQuery(datasortID[a]).mouseout(function(){
				//jQuery(datasortID[a]).removeClass("hover");
			});
		})(i);
		
		
	}
	jQuery(datasortID[def]).addClass("hover");
	/*if(!jQuery(datasortID[def]).children("div").html()){
		var divhtml=jQuery(datasortID[def]).html();
		jQuery(datasortID[def]).html("<div style='position:relative'>"+divhtml+"</div>");
	}
	jQuery(datasortID[def]).children("div").append("<font style='position:absolute; top:-2px; font-size:15px;' id='Psort"+def+"'>&nbsp;鈫�/font>");	*/
}
	


function ptrbg(e) {
	//alert(e);
	showdel(window.event,this);
	$("#" + e.data.id).addClass("bgbulle");
	$("#" + e.data.id).mouseout(function() {
		//alert(e.data.id)
			$(this).removeClass("bgbulle");
			var trd = parseFloat($("#" + e.data.id).children().children().children("span").attr("id"));
				hidedel(window.event,this,trd);
		
	});
	
}

function ptrbg1(e) {
	
	$("#" + e.data.id).addClass("bgbulle");
	$("#" + e.data.id).mouseout(function() {
		$("#" + e.data.id).removeClass("bgbulle");
	
		
	});
	
}

function setthwidth(id, ftdt) {
	var tdt = $("#" + id + " th");
	var pwidth = $("#" + id).parent().width();
	//setdivwidth(ftdt)
	//alert(pwidth)
	for(var i = 0; i < tdt.length; ) {
		for(var j = 0; j < ftdt.length; j++) {
			jQuery(tdt[i]).width(pwidth * ftdt[j]);
			//alert(pwidth * ftdt[j])
	//var widths=parseInt($("#widthth").parent().css("width"))
			if(id=="example"){
				jQuery(tdt[i]).children('div').width(pwidth * ftdt[j]-47+"px");
				var tr=$(".nowrapwidth").parent().parent().parent(),
					tdfont=$(".nowrapwidth");
				for(var a=0; a<tr.length;a++){
					(function(b){
						jQuery(tr[a]).find("td").mouseover(function(){
						jQuery(tdfont[b]).width(pwidth * ftdt[0]-110+"px");					
						});
						jQuery(tr[a]).find("td").mouseleave(function(){
							if(jQuery(tdfont[b]).parent().children("span").css("display")=="block"){
							jQuery(tdfont[b]).width(pwidth * ftdt[0]-40+"px");					
							}
						});
					})(a);
				}
				$(".datenow").width(pwidth * ftdt[7]+"px");
			}
			//setdivwidth(ftdt[j])
			i++;
		}
	}
	
}

function ctrbg(e) {
	if(!$('#' + e.data.id).hasClass('cbgbulle')) {
		$('#' + e.data.id).parent().children().removeClass('cbgbulle');
		$('#' + e.data.id).removeClass('bgbulle').addClass('cbgbulle');
	} else {
		$('#' + e.data.id).removeClass('cbgbulle');
	}	
}

	 //onmouseover='showdel(event,this)' onmouseout='hidedel(event,this,\"plusta"+trd+"\")'
function highlight(id,off,sub) {
	var nTrs = $('#' + id + ' tbody tr');
	for(var i = 0; i < nTrs.length; i++) {
		nTrs[i].id = id + "tr" + i;
		$('#' + id + 'tr' + i).unbind("click", ctrbg);	
		if(sub == "0"){
			if(!off){
				$('#' + id + 'tr' + i).unbind("mouseover", ptrbg1);
				$('#' + id + 'tr' + i).bind("mouseover", {
					id : id + "tr" + i
				}, ptrbg1);
			}else{
				$('#' + id + 'tr' + i).unbind("mouseover", ptrbg);
				$('#' + id + 'tr' + i).bind("mouseover", {
					id : id + "tr" + i
				}, ptrbg);
			}
		}
		$('#' + id + 'tr' + i).bind("click", {
			id : id + "tr" + i
		}, ctrbg);
	}
}

function initwidth(id) {
	var tdtxt = $(".tdtxtwidth");
	var initf = tdtxt;
	var pwidth = $(tdtxt[1]).parent().parent().parent().parent().parent().parent().width();
	for(var i = 0; i < tdtxt.length; i++) {
		var tdtitle = $(tdtxt[i]).html();
		$(tdtxt[i]).attr('title', tdtitle);
		$(tdtxt[i]).css({
			"min-width" : "24px"
		});
		if(id == 'example') {
			initf[i] = $(tdtxt[i]).width() / pwidth;
		} else {
			initf[i] = $(tdtxt[i]).parent().width() / pwidth;
		}
	}
	return initf;
}

function gettickInterval(info) {
	var tickInterval = 0;
	var c = 5;
	if(info != null) {
		var max = getMax(info);
		c = getC(max);
		tickInterval = Math.ceil((max / 3) / c) * c;
		if(max < 15) {
			tickInterval = 5;
		}
	}
	return tickInterval;
}

function getMax(info) {
	var max = 0;
	for(var i = 0; i < info.length; i++) {
		if(info[i] > max) {
			max = info[i];
		}
	}
	return max;
}

function getC(max) {
	if(max / 300000 > 1) {
		return c = 50000;
	}
	if(max / 30000 > 1) {
		return c = 5000;
	}
	if(max / 3000 > 1) {
		return c = 500;
	}
	if(max / 30 > 1) {
		return c = 50;
	}
	return 5;
}

function fnreader(info) {
	//alert(info);
	return "<div class='tdtxtwidth'>" + info + "</div>";
}

function Operation(e) {
	jQuery(e).children("div").show();
	jQuery(e).mouseleave(function() {
		jQuery(e).children("div").hide();
	});
}

function copyheight() {
	var clientHeights = document.body.clientHeight;
	var innerHeights = document.documentElement.clientHeight;
	//alert(clientHeights+">>"+innerHeights)
	if($.browser.safari) {
		innerHeights = window.innerHeight;
	}
	clientHeights = clientHeights + copyheight;
	if(clientHeights < innerHeights) {
		$(".loadmain").css({
			height : innerHeights - 169 + "px"
		});
	}
	}
function showManageDiv () {
	$(".manageDiv").slideToggle();
}
//窗口弹框
function showOut(txt, bg, close) {
	window.scrollTo(0,0);
	var txt = txt;
	var bg = bg;
	var dheight = document.documentElement.clientHeight;
	console.log(dheight);
	$("#" + txt).show();
	$("#" + bg).show();
	$("."+bg).css({
		"height" : dheight
	});
	/*$("."+txt).css({
		"height" : dheight*0.9
	});*/
	/*$("."+txt).css({
		"overflow-y" : "scroll"
	});
	$("."+txt).css({
		"overflow-x" : "hidden"
	});*/
	/*$("."+txt).css({
		"top" : dheight*0.05
	});*/
	document.body.style.overflow='hidden';
	
	$("." + close).click(function() {
		$("#" + txt).hide();
		$("#" + bg).hide();
		document.body.style.overflowY='scroll';
		document.body.style.overflowX='hidden';
	})
}
//寮瑰嚭绐楀彛鎻掍欢
function xytt(txt, bg, colse) {
	var txt = txt;
	var bg = bg;
	$("#" + txt + " input").val("");
	var sHeight = document.body.clientHeight;
	var dheight = document.documentElement.clientHeight;
	var srctop = document.documentElement.scrollTop;
	if($.browser.safari) {
		srctop = window.pageYOffset;
	}
	$(".xy").css({
		"height" : dheight
	});
	dheight = (dheight - $("#" + txt).height()) / 2;
	$("#" + txt).show();
	$("#" + bg).show();
	$("#" + txt).css({
		"top" : (srctop + dheight) + "px"
	});
	$("#" + bg).css({
		"top" : (srctop ) + "px"
	});
	window.onscroll = function scall() {
		var srctop = document.documentElement.scrollTop;
		if($.browser.safari) {
			srctop = window.pageYOffset;
		}
		$("#" + txt).css({
			"top" : (srctop + dheight) + "px"
		});
		$("#" + bg).css({
			"top" : (srctop) + "px"
		});
		window.onscroll = scall;
		window.onresize = scall;
		window.onload = scall;
	};
	$("." + colse).click(function() {
		$("#" + txt).hide();
		$("#" + bg).hide();
	});
}

function sever(txt, colse) {
	$("#" + txt).slideToggle("slow");
	$("#" + colse).click(function() {
		$("#" + txt).slideUp("slow");
	});
	
}

var srtop = document.documentElement.scrollTop;
if($.browser.safari) {
	srtop = window.pageYOffset;
}
var innerHeights = document.documentElement.clientHeight;
if($.browser.safari) {
	innerHeights = window.innerHeight;
}


function kfgd() {
	var srtop = document.documentElement.scrollTop;
	if($.browser.safari) {
		srtop = window.pageYOffset;
	}
	var innerHeights = document.documentElement.clientHeight;
	if($.browser.safari) {
		innerHeights = window.innerHeight;
	}
	$("#fkicon").css({
		top : srtop + (innerHeights / 2)
	});
	window.onscroll = kfgd;
	//window.onload = kfgd;
}

function xyttcolse(txt, bg, colse, txtp) {
	var txt = txt;
	var txtp = txtp;
	var bg = bg;
	var sHeight = document.body.clientHeight;
	var dheight = document.documentElement.clientHeight;
	$(".xy").css({
		"height" : dheight
	});
	dheight = (dheight - $("#" + txt).height()) / 2;
	$("#" + txt).show();
	$("#" + bg).show();
	$("#" + txt).css({
		"top" : (document.documentElement.scrollTop + dheight) + "px"
	});
	$("#" + bg).css({
		"top" : (document.documentElement.scrollTop ) + "px"
	});
	window.onscroll = function scall() {
		$("#" + txt).css({
			"top" : (document.documentElement.scrollTop + dheight) + "px"
		});
		$("#" + bg).css({
			"top" : (document.documentElement.scrollTop ) + "px"
		});
		window.onscroll = scall;
		window.onresize = scall;
		window.onload = scall;
	};
	$("#" + txtp).hide();
	$("#" + colse).click(function() {
		$("#" + txt).hide();
		$("#" + bg).hide();
	});
}

function timet(txt, colse,div,obj) {
	var txt = txt;
	if(obj){
		if($(obj).hasClass("hover"))$(obj).removeClass("hover");
		else $(obj).addClass("hover");
	}
	$("#" + txt).slideToggle("slow");
	$("#" + colse).click(function() {
		$("#" + txt).slideUp("slow");
		$("#"+div).hide();
		if(obj)$(obj).removeClass("hover");
	});
	/*$(obj).blur(function(){
		$("#" + txt).slideUp("show");
		$("#"+div).hide();
		if(obj)$(obj).removeClass("hover");
	});*/
	/*$("#"+txt).blur(function(){
		alert(1);
		$("#" + txt).slideUp("show");
		$("#"+div).hide();
		if(obj)$(obj).removeClass("hover");
	});*/
	/*$("#" + txt).parent().mouseleave(function() {
		$("#" + txt).slideUp("show");
		$("#"+div).hide();
	})*/
}

function addCustom (txt) {
	$("#" + txt).slideToggle("slow");
}

//姹備粖澶╃殑鏃ユ湡
function getToday() {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	if(day < 10) {
		day = "0" + day;
	}
	return year + "-" + month + "-" + day;
}

function getDateAgo(num) {
	var myDate = new Date();
	var t = myDate.getTime();
	var t2 = t - num * 1000 * 3600 * 24;
	var weekago = new Date(t2);
	var y = weekago.getFullYear();
	var m = weekago.getMonth() + 1 + "";
	if(m.length<2){
		m = "0"+m;
	}
	var d = weekago.getDate();
	if(parseInt(d) < 10) {
		d = "0" + d;
	}
	return y + "-" + m + "-" + d;
}

//灏唖tr杞垚鏃ユ湡date
function strToDate(str) {
	var arys = new Array();
	arys = str.split('-');
	var newDate;
	if(parseInt(arys[1]) < 10 && arys[1].length == 1) {
		arys[1] = '0' + arys[1];
	}
	newDate = new Date(arys[0], parseInt(arys[1]) - 1, arys[2]);
	return newDate;
}

function DateDiff(sDate1, sDate2, daynm) {
	var oDate1, oDate2, iDays;
	oDate1 = strToDate(sDate1);
	oDate2 = strToDate(sDate2);
	iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24);
	if(iDays > daynm) {
		return false;
	} else {
		return true;
	}
}
//姹備袱涓瓧绗︿覆鏃ユ湡鐩搁殧澶╂暟
function DateDifflong(sDate1,sDate2){
	var oDate1, oDate2, iDays;
	oDate1 = strToDate(sDate1);
	oDate2 = strToDate(sDate2);
	iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24);
	return iDays;
}

//姹備竴涓棩鏈熷悗澶氬皯澶╃殑鏃ユ湡
function getContrastEndTime(sDate1,datediff){
	var oDate1 = strToDate(sDate1);
	var t=oDate1.getTime()+datediff*24*60*60*1000;
	var contrasttime = new Date(t);
	var y = contrasttime.getFullYear();
	var m = contrasttime.getMonth() + 1;
	var d = contrasttime.getDate();
	if(parseInt(d) < 10) {
		d = "0" + d;
	}
	if(parseInt(m) < 10) {
		m = "0" + m;
	}
	return y + "-" + m + "-" + d;
}


function changeStrToJson(newinfo){
	if(!newinfo){
		return null;
	}else{
		for(var i=0;i<newinfo.length;i++){
			if(isInteger(newinfo[i])){
				newinfo[i]=parseInt(newinfo[i]);
			}else if(isFloat(newinfo[i])){
				newinfo[i]=parseFloat(newinfo[i]);
			}else{
				newinfo[i]=0;
			}
		}
		return newinfo;
	}
}

function getTickInterval(info){
	var cdy=info.length;
	var tickInterval=Math.ceil(cdy/31);
	return tickInterval;         
}

function strTOStr(strDate) {
	var arys = new Array();
	arys = strDate.split('-');
	if(parseInt(arys[1]) < 10) {
		arys[1] = parseInt(arys[1],10);
	}
	return arys[0] + "-" + arys[1] + "-" + arys[2];
}

var oaw, naw;
var f = null;
function setdivwidth(fc) {
	if(fc == null) {
		return;
	}
	var tdtxt = $(".tdtxtwidth");
	var parwidth = $(tdtxt[0]).parent().parent().parent().parent().parent().parent().width();
	//alert($(tdtxt[0]).parent().parent().parent().parent().parent().parent().attr('id'));
	if(parwidth != null && parwidth != 0) {
		for(var i = 0; i < fc.length; i++) {
			$(tdtxt[i]).width(0);
			var newwidth = parwidth * fc[i];
			if(!$(".tdtxtwidth").hasClass("thhover"))$(tdtxt[i]).width(newwidth);
			else $(tdtxt[i]).width(newwidth);
		}
	}
}


$(document).ready(function() {
	oaw = window.innerWidth;
	//涓嬫媺鍒楄〃
	$(function() {
		//$("#productTxt").sSelect();
		//$("#date_1a").sSelect();
		//$("#productList").sSelect();
		/*$("#date_2").sSelect();
		$("#date_1").sSelect();*/
		//$("#date_2a").sSelect();
		/*$("#date_3").sSelect();
		$("#date_4").sSelect();
		$("#date_5").sSelect();
		$("#date_6").sSelect();*/
		

	});
	//鑷�搴旀祻瑙堝櫒瀹介珮
	var winWidths = 0;
	function findDimensions(){
		//鑾峰彇绐楀彛瀹藉害
		if(window.innerWidth) {
			winWidths = window.innerWidth;
			naw = winWidths;
		} else if((document.body) && (document.body.clientWidth)) {
			winWidths = document.body.clientWidth;
			winWidths = winWidths + 15;
		}
		//閫氳繃娣卞叆Document鍐呴儴瀵筨ody杩涜妫�祴锛岃幏鍙栫獥鍙ｅぇ灏�		//if (document.documentElement  &&   document.documentElement.clientWidth){
		//  winWidths = document.documentElement.clientWidth;
		//  }
		if(winWidths < 768) {
			winWidths = 768;
		}
		winWidths = winWidths - 316;
		/* if(winWidths>1140)
		winWidths=1140;
		if($.browser.safari){winWidths = winWidths+110}*/
		//缁撴灉杈撳嚭鑷充袱涓枃鏈
		/*$("#right").css({
			width : winWidths
		});
		$(".web").css({
			"min-width" : "768px"
		});
		$(".top").css({
			"min-width" : "748px"
		});*/
		//鏍规嵁绐楀彛澶у皬鏄剧ず澶氬皯鏂囧瓧
		//setdivwidth(f);
		//setthwidth("example", [0.20, 0.08, 0.09, 0.08, 0.1, 0.1,0.13,0.12,0.1])
		//setthwidth("analysisexamples", [0.15, 0.15, 0.1, 0.1, 0.1, 0.1,0.2])
								//var widths=document.getElementById("widthth").parentNode;
								//alert(widths)
									//$(".nowrapwidth").width(widths.offsetWidth-95+"px");
		//alert(1)
		//	鏍规嵁绐楀彛澶у皬鏄剧ず澶氬皯鏂囧瓧缁撴潫
		
	}

	//findDimensions();
	window.onresize = findDimensions;
	window.onscroll = kfgd;
	//window.onload = kfgd;

});

//tmptime:12041600鏍煎紡
function changeStringDateToDate(tmptime){
	var year=tmptime.substring(0,2);
	var month=tmptime.substring(2,4);
	var day=tmptime.substring(4,6);
	var hour=tmptime.substring(6,8);
	if(month.substring(0,1)=="0"){
		month=month.substring(1,2);
	}
	var tmpdate=month+"/"+day;
	return tmpdate;
}
//tmptime:2012-04-16鏍煎紡
function changeStringDateToDate2(tmptime){
	var start=tmptime.indexOf("-");
	return tmptime.substring(start+1);
}


function changeStrToJson(newinfo){
	if(!newinfo){
		return null;
	}else{
		for(var i=0;i<newinfo.length;i++){
			if(isInteger(newinfo[i])){
				newinfo[i]=parseInt(newinfo[i]);
			}else if(isFloat(newinfo[i])){
				newinfo[i]=parseFloat(newinfo[i]);
			}else{
				newinfo[i]=0;
			}
		}
		return newinfo;
	}
}


function getMapJSON(country,data){
	if(country==null||country==""||data==null||data==""){
		return null;
	}else{
		var gdpData="";
		var country=country.toString();
		var data=data.toString();
		var tmpcountry=country.split(",");
		var tmpdata=data.split(",");
		for(var i=0;i<tmpcountry.length ;i++){
			var countryname=tmpcountry[i].toLowerCase();
			if(!isNumber(tmpdata[i])){
				tmpdata[i]=0;
			}
			gdpData+="\""+countryname+"\":"+tmpdata[i]+",";
		}
		if(tmpcountry.length>0){
			gdpData=gdpData.substring(0,gdpData.length-1);
			gdpData="{"+gdpData+"}";
		}
		var object;
		if (typeof (JSON) == 'undefined'){
			object=eval('('+gdpData+')'); 
		}else{
			object= JSON.parse(gdpData);
		}
		return object;
	}
}

function isNumber(str,obj)
{
	if(str==null||str=="")
	{
		return true;
	}
	var intnum=/^[0-9]{0,}$/;
	if(intnum.test(str))
	{
		return true;
	}else
	{
		var num = /^[0-9]+(.[0-9]{1,10})$/;
		if(num.test(str))
		{
			return true;
		}else
		{
			try 
	    { 
	        obj.focus();
	    } 
	    catch(ex)
	    { 
	        
	    } 
			alert("杈撳叆鐨勬牸寮忛敊璇紝鍙兘杈撳叆鏁板瓧鎴栧皬鏁帮紝濡�.333");
			return false;
		}
	}
	
}

function dateframe(date){
	var strYear=date.getFullYear();   
    var strDay=date.getDate();
    if(strDay<10){
    	strDay="0"+strDay;
    }
    var strMonth=date.getMonth()+1;
    var strtmpday=strYear+"-"+strMonth+"-"+strDay;
    return strtmpday;
}