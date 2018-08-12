<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headSetting.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
body {
	background-image: url("images/login.png");
    background-position: center top;
    background-repeat: no-repeat;
    background-size: 360px 640px;
    margin: auto;
    width: 100%;
}
.container {	
    margin: auto;
    padding-top: 200px;
	text-align: center;
}

.roundBox {
	border-top: none;
	border-left: none;
	border-right: none;
	border-bottom: 2px solid #004566;
 	line-height : normal;
	width: 200px;
	padding: 10px;
	text-align: center;
	background-color: rgba(0,0,0,0);
	color: #004566;
}
#login {
	width: 200px;
	padding: 10px;
	border-radius: 20px;
	border-color: white;
	text-align: center;
	background-color: #005580;
	color: #FFFFFF;
}
#login:hover {
	background-color: #004065;
}
#logo {
	max-width: 300px;
	max-height: 300px;
}
/* The container */
.checkboxContainer {
    display: block;
    position: relative;
    padding-left: 25px;
    margin-bottom: 12px;
    cursor: pointer;
    font-size: 22px;
    user-select: none;
    display:inline;
    font-size:11pt;
    margin-left: auto;
    margin-right: auto;
}

/* Hide the browser's default checkbox */
.checkboxContainer input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
}

/* Create a custom checkbox */
.checkmark {
    position: absolute;
    top: 0;
    left: 0;
    height: 15px;
    width: 15px;
    margin-top: 3px;
    background-color: #bbb;
}

/* On mouse-over, add a grey background color */
.checkboxContainer:hover input ~ .checkmark {
    background-color: #aaa;
}

/* When the checkbox is checked, add a blue background */
.checkboxContainer input:checked ~ .checkmark {
    background-color: #004566;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
    content: "";
    position: absolute;
    display: none;
}

/* Show the checkmark when checked */
.checkboxContainer input:checked ~ .checkmark:after {
    display: block;
}

/* Style the checkmark/indicator */
.checkboxContainer .checkmark:after {
    left: 5px;
    top: 0;
    width: 6px;
    height: 12px;
    border: solid white;
    border-width: 0 3px 3px 0;
    transform: rotate(45deg);
}
#idCheck, #pwCheck {
	color: gray;
	font-size: 13px;
}

</style>
<script type="text/javascript"></script>
</head>

<body>
		<div class="container">
			<br>
			<br> <input type="text" id="userId" name="userId"
				placeholder="아이디" class="roundBox" autocapitalize="none">
			<br><span id="idCheck"></span><br>
			<input type="password" id="pw" name="pw" placeholder="비밀번호"
				class="roundBox">
			<br><span id="pwCheck"></span><br>
			<br>
			<button id="login">로그인</button>
			<br> <br>
			<!-- <input type="checkbox" class="check" name="autoLogin" id="autoLogin">자동 로그인
			<input type="checkbox" class="check" name="saveId" id="saveId">아이디 저장
			<br><br> -->
			<label class="checkboxContainer">자동 로그인 
				<input type="checkbox" class="check" name="autoLogin"
					id="autoLogin"> <span class="checkmark"></span>
			</label> 
			&nbsp;&nbsp;&nbsp;
			<label class="checkboxContainer">아이디 저장 
				<input type="checkbox" class="check" name="saveId" id="saveId">
				<span class="checkmark"></span>
			</label> <br> <br> 
			<a id="addUser" style="color: #004566">join</a>&nbsp; | &nbsp;<a id="findIdPassword" style="color: #004566">find id/password</a> 

		</div>
	
<script type="text/javascript">

$("button").button();
$("saveID").checkboxradio();
//아이디 : 6~16자 영소문자, 숫자/정규표현식: ^(?=.*[a-z]|(?=.*\d)).{6,16}$
//비밀번호 : 6~16자의 영문 대 소문자, 숫자, 특수문자/정규표현식: ^(?=.*[a-zA-Z]|(?=.*\d)|(?=.*\W)).{6,16}$

//아이디가 저장되어 있다면 세션에서 아이디값 받아오기.
	if (localStorage.getItem("loginId")) {
		$("#userId").val(localStorage.getItem("loginId"));
		$("#saveId").prop("checked", true);
	}
	
//test용 자동로그인(pw저장만)
	if (localStorage.getItem("loginPw")) {
		$("#pw").val(localStorage.getItem("loginPw"));
		$("#autoLogin").prop("checked", true);
	}
	

//아이디, 패스워드 길이 점검
	$("#userId").keyup(function() {
		if ($("#userId").val() != "") {
			$("#idCheck").html("");
		}
	});

	$("#pw").keyup(function() {
		if ($("#pw").val() != "") {
			$("#pwCheck").html("");
		}
	});
	
	$("#pw").keydown(function(event){
		if(event.which==13){
			$("#login").click();
		}
	})

	//로그인
	$("#login").on("click",	function() {
		var check = true;
		//1. null check
		if (check) {
			if ($("#userId").val() == "") {
				$("#idCheck").html("아이디를 입력해주세요");
				$("#userId").focus();
				check = false;
			} else if ($("#pw").val() == "") {
				$("#pwCheck").html("비밀번호를 입력해주세요");
				$("#pw").focus();
				check = false;
			}
		}
		//2. 형식 검사
		if (check) {
			var regExpId = new RegExp("^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,16}$");
			var regExpPw = new RegExp("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{6,16}$");
			if (!regExpId.test($("#userId").val()) || !regExpPw.test($("#pw").val())) {
				$("#pwCheck").html("아이디, 비밀번호를 다시 확인해주세요");
				check = false;
			}
		}
						
		//3. 아이디 저장 체크 여부
		if (check) {
			if ($("#saveId").prop("checked")) {
				localStorage.setItem("loginId", $("#userId").val());
			} else {
				if (localStorage.getItem("loginId")) {
					localStorage.removeItem("loginId");
				}
			}
		}
		
		//test용 pw저장
		if (check) {
			if ($("#autoLogin").prop("checked")) {
				localStorage.setItem("loginPw", $("#pw").val());
			} else {
				if (localStorage.getItem("loginPw")) {
					localStorage.removeItem("loginPw");
				}
			}
		}
		//4. ajax
		if(check) {
			$.ajax({
				type : "POST",
				url : "login",
				data : {
					userId : $("#userId").val(),
					pw : $("#pw").val()
				},
				success : function(result) {
					$("body").html(result);
				}
			});	
		} else {
			$( ".container" ).effect( "shake" );
		}
		
	});

	//비밀번호 찾기 페이지 이동
	$("#findIdPassword").on("click", function() {
		location.href = "controller?cmd=findIdPasswordUI";
	});

	//회원가입 페이지 이동
	$("#addUser").on("click", function() {
		location.href = "controller?cmd=addUserUI";
	});

	//자동로그인: #autologin check -> id, pw를 어디에 저장했다가 불러올건가?
</script>
</body>
</html>