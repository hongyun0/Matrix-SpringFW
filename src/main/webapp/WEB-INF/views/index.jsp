<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headSetting.jsp" %>
<style type="text/css">
body {
	background-color: #005580;
	margin: auto;
	width: 360px;
	height: 640px;
}
</style>
</head>
</head>
<body>
<div style="padding-top:45%; color:white; text-align:center; font-weight: bold;"><img alt="logo" src="images/logo_white.png" width="350px"></div>
<script type="text/javascript">
$(document).ready(function(){
	$("body").fadeOut(2000, toLogin);
});

var toLogin = function(){
	location.href="login";
}
</script>
</body>
</html>