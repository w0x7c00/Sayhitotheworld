$(document).ready(init);
function init() {
    var window_width = $(window).width();
    if(window_width<768) {       //小屏幕
        $("#left-nav").css("left", "-285px");
        $("#left-nav").attr("show", "false");
    }
    setContextWidth();
    $("#close-open-control").click(animator_toggle_nav);
    $(window).resize(setContextWidth);
    $(".outer-ul>li").on("click",click_li_active);
    $(".outer-ul>li.inner-li").on("click",click_li_toggle);
    $("#context-head>p").click(function () {
        window.location="/mainPage.html";
    })
    initPage();
    //为每个page绑定事件
    pageBindClick();
    $("#page2-form>button").off("click").on("click",page_2_form_submit);
}
function initPage() {
    //首先检查权限
    $.post("/initAdminPage",function (data,status) {
        if(data===undefined||data==="0"){
            window.location = "/adminLogin.html";
        }
        else{
            var admin_packet = JSON.parse(data);   //作为全局变量 控制一些按钮是否可用
            $("#admin_name").text(admin_packet["admin_name"]);
            $("#page-1-head-welcome>p").text("欢迎管理员 ："+admin_packet["name"]);
            $("#name").text(admin_packet["name"]);
            $("#level").text(function () {
                var type = admin_packet["type"];
                if(type===1){
                    return "超级管理员";
                }
                else{
                    return "普通管理员";
                }
            });
            $("#registe_time").text(getData(admin_packet["register_time"]));

            //进行一些界面的控制 某些情况不能够被展开
            if(admin_packet["type"]!==1){
                $("#super_user_li").css("display","none");
            }
        }
    });
}
function getData(str) {
     if(str === undefined){
         return "1970-9-9";
     }
     var date = new Date(parseInt(str));
     return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDay();
}
function setContextWidth() {
    // var nav_height = window.innerHeight
    //     || document.documentElement.clientHeight
    //     || document.body.clientHeight;
    var nav_height = $(window).height();
    $("#left-nav").css("height",nav_height+"px");

    var jq_left_nav = $("#left-nav");
    var jq_div_main = $("#div-main");
    var width_par;
    if(jq_left_nav.attr("show")==="true") {
        width_par= jq_div_main.width() - jq_left_nav.width();
    }
    else{
        width_par= jq_div_main.width();
    }
    $("#right-context").css("width",width_par+"px");
}
function animator_toggle_nav() {
    if($("#left-nav").attr("show")==="true") {
        $("#left-nav").animate({left: "-285px"}, {duration: 500, queue: false});
        $("#right-context").animate({width: $("#right-context").width() + 285 + "px"}, {duration: 500, queue: false});
        $("#left-nav").attr("show", "false");
    }
    else{
        $("#left-nav").animate({left:"0"},{duration:500,queue:false});
        $("#right-context").animate({width:$("#right-context").width()-285+"px"},{duration:500,queue:false});
        $("#left-nav").attr("show","true");
    }
}
function click_li_active() {
    $(this).siblings().removeClass("active");
    $(this).addClass("active");
}
function click_li_toggle() {
    var target = $(this).attr("target-ul");
    $(target).slideToggle("slow");
}
function pageBindClick() {
    $(".page-link").on("click",function () {
        var target_page = $($(this).attr("target-page"));
        if(target_page.hasClass("page-active")){
            //do nothing
        }
        else{
            $(".page").removeClass("page-active");
            target_page.addClass("page-active");
            setContextWidth();
        }
    });
}
function page_2_form_submit() {
    var title = $("#page2-form-title");
    var text = $("#page2-form-text");
    var result = true;
    if(title.val().length>20||title.val().length===0){
        $("#page2-form-title-warn").show();
        result=false;
    }
    if (text.val().length>200||text.val().length===0){
        $("#page2-form-text-warn").show();
        result=false;
    }
    if(result){
        $.post("/adminNoticeSubmit",{
            "title":title.val(),
            "text":text.val(),
        },function (data,status) {
            if(data=="1"){
                alert("提交成功！");
            }
            else if(data=="0"){
                alert("没有合法的提交权限");
                window.location = "/adminLogin";
            }
            else if(data=="2"){
                alert("不要使用脚本提交或者修改js文件，好吗？");
            }
            else{
                alert("服务器故障");
            }
        });
    }
}
$("#yes").on("click",function () {
    alert("已提交审核！");
    $("#b").hide();
})