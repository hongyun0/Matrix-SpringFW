<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="headSetting.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">

<div style="margin: 40%; margin-top: 250px; text-align: center;">
	MATRIX GOT ERROR !
	<br><br>
	<input type="button" id="homeBtn" value="홈으로 가기" />
	<br><br>
	<input type="button" id="backBtn" value="이전 페이지" />
</div>

<script type="text/javascript">
	if (confirm("에러가 발생했습니다. 확인 버튼을 누르면 이전 페이지로 돌아갑니다.")) {
		location.href = "${backPage}";
	}
	
	document.querySelector("#homeBtn").onclick = function(){
		location.href = "";
	}
	document.querySelector("#backBtn").onclick = function(){
		location.href = "${backPage}";
	}
</script>