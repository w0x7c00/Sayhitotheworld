// window.onload = function() {
// 	document.getElementById('sendMsg').addEventListener('click', function() {
// 		var msg = document.getElementById('msgTxt').value;
// 		var msgUser = document.getElementById('msgUser').value;
// 		if(''==msg)
// 		{
// 			alert('请输入内容后再提交');
// 			return;
// 		}
// 		var post = {
// 			msg: msg,
// 			user:msgUser
// 		}
// 		Send("index.php", post, callBack);
// 	});
// 	getMsg();
// }
//
// function callBack(data) {
//
// 	if(data.status) {
//
// 		alert('提交成功');
// 	}
//
// }
//
// function getMsg() {
//
// 	Send("index.php?getMsg", "", msgCallBack);
//
// }
//
// function msgCallBack(data) {
//
// 	for(var i = 0; i < data.length; i++) {
//
// 		creatMsgDiv(data[i].user, data[i].msg, data[i].msgTime);
// 	}
//
// }
//
// function creatMsgDiv(ip, msg, time) {
// 	var section = document.getElementById('sectionID');
// 	var div = document.createElement('div');
// 	var src = '<div class="container" style="border-bottom: 1px solid #0b93d5;margin-top: 10px;"><div style="width: 100%;"><div style="float: left;color: white;">' + ip + '</div><div style="float: left;margin-left: 20px;color: white;">' + time + '</div><div class="clearfix"></div><div style="color: white;float: left;">' + msg + '</div></div></div>';
// 	div.innerHTML = src;
// 	section.appendChild(div);
//
// }
//
// function Send(url, postdata, func) {
//
// 	jQuery.post(url, postdata, func, 'json');
//
// 	return false;
// }