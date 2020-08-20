//Sayhitotheworld
//2020-8-18

//the common tool function for all pages


/* Part1:Form Input Format Check  */

/*Reachable Max Value*/
var MAX_NAME_LENGTH = 254;
var MAX_USER_NAME_LENGTH = 20;
var MAX_PASSWORD_LENGTH = 20;
var EMAIL_CODE_LENGTH = 6;
var MAX_SELF_INTRODUCTION_LENGTH = 1000;
function checkUserName(input) {
    if(input===""){
        return false;
    }
    try{
        if(input.length<=MAX_USER_NAME_LENGTH){
            return true;
        }
        else{
            return false;
        }
    }
    catch(e){
        return false;
    }
}

function checkPassword(input) {
    if (input===""){
        return false;
    }
    try{
        if(input.length<=MAX_PASSWORD_LENGTH){
            return true;
        }
    }
    catch (e) {
        return false;
    }
}


function checkName(input) {
    if(input===""){
        return false;
    }
    else{
        if(input.length<=MAX_NAME_LENGTH){
            return true;
        }
        else{
            return true;
        }
    }
}

function checkLanguage(input) {
    if(input==="-1"){
        return false;
    }
    else {
        return true;
    }
}

function checkCountry(input) {
    if(input==="-1"){
        return false;
    }
    else {
        return true;
    }
}

function checkPrice(input) {
    try{
        parseInt(input);
        if(input===""){
            return false;
        }
        else {
            return true;
        }
    }
    catch (e) {
        return false;
    }
}

function checkGender(input) {
    try{
        if(input==="1"||input==="0"){
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

function checkEmailCode(input) {
    try{
        if(input.length==EMAIL_CODE_LENGTH){
            parseInt(input);
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

function checkEmail(input) {
    if(input===""){
        return false;
    }
    else{
        return true;
    }
}

function checkFile(input) {
    if(input===""){
        return false;
    }
    else{
        return true;
    }
}

function checkAge(input) {
    try{
        if (input===""){
            return false;
        }
        input = parseInt(input);
        if(input>0&&input<=120){
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
    if(input.length<=MAX_SELF_INTRODUCTION_LENGTH){
        return true;
    }
    else{
        return false;
    }
}
/* Part1:Form Input Format Check  */