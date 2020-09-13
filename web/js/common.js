//Sayhitotheworld
//2020-8-18

//the common tool function for all pages


/* Part1:Form Input Format Check  */

/*Reachable Max Value*/
var MAX_AGE = 120;
var MIN_AGE = 0;
var MAX_PASSWORD_LENGTH = 25;
var MAX_PRICE = 20;
function checkUserName(input) {
    return /^[a-zA-Z0-9_]{6,25}$/.test(input);
}

function checkPassword(input) {
    return (/^.*(?=.{6,})(?=.*\d)(?=.*[A-Za-z])(?=.*[!@#$%^&*?]*).*$/.test(input))&&(input.length<=MAX_PASSWORD_LENGTH);
}

function checkName(input) {
    return /^[^\d]{2,254}$/.test(input);
}

function checkLanguage(input) {
    return input !== "-1";
}

function checkCountry(input) {
    return input !== "-1";
}

function checkPrice(input) {
    if(!(/^\d{1,2}$/.test(input))){
        return false;
    }
    try{
        var input_int = parseInt(input);
        return input_int <= MAX_PRICE;
    }
    catch (e) {
        return false;
    }
}

function checkGender(input) {
    return input === "1" || input === "0";
}

function checkEmailCode(input) {
    return /^\d{6}$/.test(input);
}

function checkEmail(input) {
    return /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(input);
}

function checkFile(input) {
    return /^.+$/.test(input);
}

function checkAge(input) {
    if(!/^\d{1,3}$/.test(input)){
        return false;
    }
    try{
        if (input===""){
            return false;
        }
        input = parseInt(input);
        if(input>MIN_AGE&&input<=MAX_AGE){
            return true;
        }
        else{
            return false;
        }
    }
    catch (e) {
        return false;
    }
}

function checkSelfIntroduction(input) {
    return /^.{1,1000}$/.test(input);
}

function checkEducation(input) {
    return /^.{1,100}$/.test(input);
}

function checkNotNull(input) {
    return input!=="";
}

function inputCheck(documentElement,checkFunc) {
    var $d = $(documentElement);
    var value = $d.val();
    var $parent = $d.parent();
    var $span = $parent.children("span.input-check-span");
    if(checkFunc(value)){
        //检测通过
        //span需要特殊的标志 .class=input-check-span 否则会与动态生成的select中span相互冲突
        if($span.length==1){
            //已存在此元素
            $d.attr("check-flag",true);
            $span.children("i:first").removeClass("glyphicon-remove").addClass('glyphicon-ok').css("color","RGB(23,164,59)");
            $span.children("i:last").text($d.attr("valid-msg")).css("color","RGB(23,164,59)");
        }
        else{
            //不存在此元素
            $d.attr("check-flag",true);
            $parent.append("<span class='input-check-span'><i style='color: RGB(23,164,59);font-size: 13px;font-weight: bolder' class='glyphicon glyphicon-ok'></i><i style='color: RGB(23,164,59);font-size: 13px;font-weight: bolder'>"+$d.attr("valid-msg")+"</i></span>");
        }
    }
    else{
        //检测不通过
        if($span.length==1){
            //已存在此元素
            $d.attr("check-flag",false);
            $span.children("i:first").removeClass("glyphicon-ok").addClass('glyphicon-remove').css("color","RGB(255,150,0)");
            $span.children("i:last").text($d.attr("invalid-msg")).css("color","RGB(255,150,0)");
        }
        else{
            //不存在此元素
            $d.attr("check-flag",false);
            $parent.append("<span class='input-check-span'><i style='color: RGB(255,150,0);font-size: 13px;font-weight: bolder' class='glyphicon glyphicon-remove'></i><i style='color: RGB(255,150,0);font-size: 13px;font-weight: bolder'>"+$d.attr("invalid-msg")+"</i></span>");
        }
    }
}



/* Part1:Form Input Format Check  */

/* Part2:Common Tool Function */
function getData(str) {
    if(str === undefined){
        return "1970-9-9";
    }
    var date = new Date(parseInt(str));
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDay();
}
/* Part2:Common Tool Function */

