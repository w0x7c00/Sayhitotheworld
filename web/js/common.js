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
    if(!(/^\d{1，2}$/.test(input))){
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
