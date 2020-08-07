<?php
require_once ('./unhacker.php');
require_once ('./DataBase.php');
date_default_timezone_set("Asia/Chongqing");

if (isset($_POST['msg'])) {
	$MSG = $_POST['msg'];
	$user = $_POST['user'];
	$ip = $_SERVER["REMOTE_ADDR"];
	if ('' == $ip) {
		$ip = '火星用户';
	}
	//$time  =  date('Y-m-d H:i:s');
	$db = new mydb('127.0.0.1', '3306', 'root', 'angel', 'xj', 'utf8');

	$sql = "INSERT INTO  `msg` (`msg`, `msgTime`, `ip`,`user`)  VALUES  ('" . $MSG . "', NOW(), '" . $ip . "','" . $user . "')";

	$db -> query($sql);

	$arr = array();

	$arr['status'] = 'true';
	echo json_encode($arr);

}

if (isset($_GET['getMsg'])) {
	$sql = "SELECT * FROM msg";
	$db = new mydb('127.0.0.1', '3306', 'root', 'angel', 'xj', 'utf8');

	$result = $db -> query($sql);
	$arr = array();
	while ($row = mysqli_fetch_array($result)) {

		$row['msg'] = iconv('gbk', 'UTF-8', $row['msg']);

		$arr[] = $row;

	}

	echo json_encode($arr);

}
?>