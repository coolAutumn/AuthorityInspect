/**
*日期格式判断:yyyy-mm-dd hh:mm
*/
function isMatchDate(phrase){
	var _phrase = phrase.trim();
	var reg=
	/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)/;
	if(reg.test(_phrase)){ 
	  //alert("true");
	  return true;
	}else{ 
	  alert("false:"+_phrase+"不是正确的日期");
	  return false;
	} 
}
/**
*判断输入是否为正整数，且值位于某个区间内
*/
function isMatchInteger(phrase,min,max){
	var _phrase = phrase.trim();
	var _min=0;
	var _max=0;
	if(min>max){
      _max=min;
      _min=max;		
	}else{
	  _min=min;
	  _max=max;
	}
	if(_phrase>=_min&&_phrase<=_max){
	  var reg = /^\d+$/; 
	  if(reg.test(_phrase)){
        return true;
	  }else{
        alert("false:不是正整数");
	    return false;
	  }
	}else{
	  alert("false:超出区间");
      return false;
	}
}
/**
*判断字符串字节数是否在某个区间内
*/
function isRightLength(phrase,min,max){
	var _phrase = phrase.trim();
	var arr = phrase.split("");
	var len = arr.length;
	var _min=0;
	var _max=0;
	if(min>max){
      _max=min;
      _min=max;		
	}else{
	  _min=min;
	  _max=max;
	}
	if(len>=_min&&len<=_max){
      alert("true:长度是"+len);
      return true;
	}else{
	  alert("false:长度是"+len+"超出区间");
	  return false;
	}
}
/**
*判断字符串是否包含英文和字符串
*/
function isIncludeLetterAndDigit(phrase){
	var _phrase = phrase.trim();
	var letterReg = /[a-zA-Z_]+/;
	var digitReg = /\d+/;
	if(letterReg.test(_phrase)&&digitReg.test(_phrase)){
      alert("true:"+_phrase+"包含数字和英文");
      return true;
	}else{
	  alert("false:"+_phrase+"缺少字母或数字");
	}
}
/**
*判断输入的字符串是否只包含汉字
*/
function isOnlyChinese(phrase){
	var _phrase = phrase.trim();
	var reg = /^[\u4e00-\u9fa5]+$/;
	if(reg.test(_phrase)){
	  alert("true:"+_phrase);
	  return true;
	}else{
	  alert("false:"+_phrase+",包含非汉字字符");
	  return false;
	  
	}
}

/**
 *判断ip是否合法
 */
function isLegalIp(phrase){
	var _phrase = phrase.trim();
	var reg = /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/;
	if(reg.test(_phrase)){
		//alert("true:"+_phrase);
		return true;
	}else{
		alert("false:"+_phrase+",不合规则的ip");
		return false;
	}
}

/**
 *判断是否是8位整数
 */
function isEighterInteger(phrase){
	var _phrase=phrase.trim();
	var reg = /^\d{8}$/;
	if(reg.test(_phrase)){
		alert("true:"+_phrase);
		return true;
	}else{
		alert("false:"+_phrase+",不合规则的data");
		return false;
	}
}

/**
 *判断是否是4位整数
 */
function isFourInteger(phrase){
	var _phrase=phrase.trim();
	var reg = /^\d{4}$/;
	if(reg.test(_phrase)){
		alert("true:"+_phrase);
		return true;
	}else{
		alert("false:"+_phrase+",不合规则的data");
		return false;
	}
}

/**
 * 判断日期类型是否为hh:mm格式的类型
 */
function IsMatchTime(phrase)
{
	var _phrase=phrase.trim();
	var reg=/(20|21|22|23|[0-1]\d):[0-5][0-9]/;
	if(reg.test(_phrase)){
		//alert("true:"+_phrase);
		return true;
	}else{
		alert("false:"+_phrase+",不合规则的time");
		return false;
	}
}

/**
 * 判断是否为正确的mac地址
 */
function IsMatchMacAddress(phrase)
{
	var _phrase=phrase.trim();
	var reg=/[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}/;
	if(reg.test(_phrase)){
		//alert("true:"+_phrase);
		return true;
	}else{
		alert("false:"+_phrase+",不合规则的mac地址");
		return false;
	}
}

/**
 * 判断是否为正确的mail地址
 */
function IsMatchMailAddress(phrase)
{
	var _phrase=phrase.trim();
	var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(reg.test(_phrase)){
		//alert("true:"+_phrase);
		return true;
	}else{
		alert("false:"+_phrase+",不合规则的邮箱地址");
		return false;
	}
}