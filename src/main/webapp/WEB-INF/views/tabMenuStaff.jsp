<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file = "loginCheckStaff.jsp" %>
<%@include file="headSetting.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
body {
	width: 360px;
	height: 640px;
}

.side-bar {
	height: 100%;
	width: 200px;
	background-color: #fff;
	position: fixed !important;
	z-index: 1;
	overflow: auto
}

.tabs {
	max-width: 360px;
	padding-top: 4px;
	position: relative;
}

.w3-col.s4 {
	padding: 7px;
	background-color: rgba(255, 255, 255, 0.7);
	border-radius: 15px 15px 0 0;
	width: 100%;
}

.w3-col.s4:hover, .w3-col.s4.tapped {
	background-color: rgba(255, 255, 255, 1.0);
}

.side-bar {
	background-color: #006088;
}

.w3-bar-block {
	font-size: 16px;
	margin: 4px;
}

.w3-bar-block a {
	background-color: rgb(255, 255, 255);
}

header {
	width: 360px;
	top: 0;
	text-align: center;
	background-image: url("images/header.png");
	background-size: 360px auto;
	background-repeat: no-repeat;
}
.mat-button {
	display: inline-block;
	margin-top: 2px;
	padding: 4px 10px;
	left: 0;
	vertical-align: middle;
	overflow: hidden;
	text-decoration: none;
	color: white;
	background-color: none;
	cursor: pointer;
	white-space: nowrap;
	float: left; 
	font-size: 35px;
}
.mat-button:hover {
	background-color: white;
}
</style>
</head>

<body>
<header>
<!-- Sidebar/menu -->
<div class="side-bar w3-animate-left" style="display:none;z-index:5; text-align: center;">
  <button class="w3-button" onclick="w3_close()" style="float:right; font-size:20px; font-weight: normal; color: white;">&times;</button>
  <div class="w3-container">
  	<table class="profileInfo" style="color: white">
  		<tr>
  			<td><img id="slideProfilePhoto" alt="프로필사진" src="images/profile/defaultProfile.png" style="width: 70px; height: 70px; border-radius: 50%; border: 5px solid white;"></td>
  			<td style="padding-left: 7px;"><span id="branchName"></span><br><span id="certifyType"></span><br><span id="name"></span>님</td>
		</tr>
  	</table>
  <br>
  <br>
  <div class="w3-bar-block">
    <a href="#updateUser" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-blue">회원 정보 수정</a> 
    <a href="#notifications" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-blue">알림 설정</a> 
    <a href="#help" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-blue">도움말</a> 
    <a href="#version" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-blue">버전 정보<span style="vertical-align: middle; font-size: 14px; color: gray; margin-right: -4px; float: right;">ver 1.0.0</span></a> 
    <a href="#developer" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-blue">개발자 정보</a> 
    <a href="#logout" onclick="logout()" class="w3-bar-item w3-button w3-hover-blue" style="background-color: rgb(255, 230, 230);">로그아웃</a> 
  </div>
  </div>
</div>

<div class="w3-container">
  <a href="#" class="mat-button" onclick="w3_open()">≡</a>
  <span style="padding: 80px 80px 20px 20px;" onclick="goHome()"></span>
</div>
<nav class="w3-overlay w3-animate-opacity" onclick="w3_close()" style="cursor:pointer"></nav>
	<!-- 위치조정 필요 -->
	<div class="tabs w3-bar">
	    <div class="w3-col s4" id="dailyTaskTab">
	   	  일일업무
	    </div>
	</div> 

</header>
<script>
function goHome(){
	location.href="controller?cmd=homeUI";
}
$("#dailyTaskTab").click(
		function(){
			 location.href="controller?cmd=dailyTaskStaffUI";
		});

//Script to open and close sidebar
function w3_open() {
    $(".side-bar").css({display:"block"});
    $(".w3-overlay").css({display:"block"});
}
 
function w3_close() {
	$(".side-bar").css({display:"none"});
    $(".w3-overlay").css({display:"none"});
}

$( document ).ready(function() {
	$.ajax({
	url: "controller?cmd=getSlideInfoAction",
	success: function(result) {
		result = JSON.parse(result);
		$("#branchName").html(result["branchName"]);
		$("#name").html(result["name"]);
		if(result["profilePhoto"] != null && result["profilePhoto"] != ""){
			$("#slideProfilePhoto").prop("src", "images/profile/"+result["profilePhoto"]);
		}
		if(result["type"] == "staff"){
			$("#certifyType").html("직원");
		} else if(result["type"] == "admin"){
			$("#certifyType").html("관리자");
		}
		
	}
	});
});

function logout() {
	location.href="controller?cmd=logoutAction";
}
</script>
</body>
</html>