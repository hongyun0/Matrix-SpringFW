<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<%@include file = "loginCheckAdmin.jsp" %>
<%@include file = "tabMenuAdmin.jsp" %>
<style>
#content {
	width: 100%;
}

.accordion {
	background: linear-gradient(#d4e5f7, #d4e5f7, #d4e5f7, #d4e5f7, rgba(212, 229, 247,
		0.2));
	color: #003366;
	cursor: pointer;
	padding: 7px 7px 7px 20px;
	width: 95%;
	text-align: left;
	font-weight: bold;
	outline: none;
	font-size: 15px;
	transition: 0.4s;
	margin: auto;
	border-radius: 5px 5px 5px 5px;
	letter-spacing: 5px;
}

.subAccordion {
	background: linear-gradient(#d4e5f7, #d4e5f7, #d4e5f7, #d4e5f7, #eaf3fa);
	color: #003366;
	cursor: pointer;
	padding: 5px 5px 5px 15px;
	position: inherit;
	width: 95%;
	text-align: left;
	font-weight: bold;
	outline: none;
	font-size: 13px;
	transition: 0.4s;
	margin: auto;
	border-radius: 5px 5px 5px 5px;
	letter-spacing: 5px;
}

.active, .subActive {
	background: linear-gradient(#206591, #206591, #206591, #206591, rgba(32, 101, 145,
		0.2));
	color: #FFFFFF;
}

.accordion:after, .subAccordion:after {
	font-family: FontAwesome;
	content: '\f0da';
	font-size: 17px;
	color: #003366;
	float: right;
	margin-top: -3px;
	margin-right: 10px;
	vertical-align: middle;
}

.active:after, .subActive:after {
	font-family: FontAwesome;
	content: '\f0d7';
	color: #FFFFFF;
}

.panel {
	width: 90%;
	background: linear-gradient(#FFFFFF, rgba(192, 222, 241, 0.3));
	max-height: 0;
	overflow: hidden;
	transition: max-height 0.2s ease-out;
	margin: auto;
	border-radius: 0 0 5px 5px;
}

.subPanel {
	width: 95%;
	background: linear-gradient(#FFFFFF, #FFFFFF, #FFFFFF, #FFFFFF, rgba(192, 222, 241,
		0.3));
	max-height: 0;
	overflow: hidden;
	transition: max-height 0.2s ease-out;
	margin: auto;
	border-radius: 0 0 5px 5px;
}

ul {
	margin-left: -20pt;
	list-style: none;
}

li {
	list-style: none;
	font-size: 15px;
	margin-left: -10px;
	margin-right: 5px;
	margin-bottom: 3px;
}

#manual {
	display: none;
}

.ui-autocomplete-category {
	font-weight: bold;
	padding: .2em .4em;
	margin: .8em 0 .2em;
	line-height: 1.5;
}

.assignTaskHeader, #modalHeader {
	margin: auto;
	text-align: center;
	width: 50%;
	border-bottom: #d4e5f7 solid;
	text-align: center;
}

.assignTaskHeader h4, #modalHeader h4 {
	margin-bottom: 5px;
	color: rgb(0, 51, 102);
	font-weight: bolder;
	letter-spacing: 2px;
}

#date {
	margin: auto;
	color: rgb(0, 51, 102);
	font-weight: bolder;
	letter-spacing: 1px;
	vertical-align: middle;
	padding-top: 10px;
	text-align: center;
}

.subMenu {
	padding: 10px;
	margin-left: 10px;
}

.subMenu span {
	font-weight: bold;
	font-size: 15px;
}

.subMenu::after {
	content: '\2795';
	color: #003366;
	font-size: 10px;
	padding-left: 6px;
	vertical-align: middle;
}

.subMenu.selected::after {
	content: '\2796';
}

#addTaskFromTyping {
	margin-left: auto;
	margin-right: auto;
	width: 80%;
	text-align: center;
}

.searchedTasks {
	font-size: 13px;
	width: 100%;
	display: block;
	padding: 1px; text-align : left;
	border: none;
	white-space: normal;
	float: none;
	outline: 0;
	text-align: left;
}

.addButton {
	background-color: #e6f2ff;
	border: none;
	border-radius: 10%;
	color: black;
	padding: 3px;
	width: 50px;
}

#selectedTaskModal {
	z-index: 3;
	display: none;
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}

#selectedTaskPopup {
	z-index: 5;
	position: absolute;
	outline: 0;
	background-color: white;
	height: 40%;
	width: 300px;
	display: none;
	border-radius: 10px; margin-left : 30px;
	margin-top: 70px;
	margin-left: 30px;
}

#selectedTask {
	margin-left: 35px;
	font-size: 17px;
}

#selectedTask::before {
	content: "\2605";
	color: lightgray;
	font-size: 30px;
	padding-right: 15px;
}

.important#selectedTask::before {
	content: "\2605";
	color: #3284e2;
}

#goNext {
	position: absolute;
	font-weight: bolder;
	font-size: 15px;
	color: #003366;
	right: 30px;
	bottom: 15px;
	padding: 3px 6px;
}
</style>
</head>
<body>
<div id="content" style="z-index: 0; position: absolute;">
<div class="assignTaskHeader">
	<h4>업무 배정</h4>
</div>
<h4 id="date">${param.dateKor}</h4>
<div id="taskFromRecommend">
	<div id="recommendToggle" class="subMenu selected"><span>오늘의 추천업무</span></div>
	<div id="recommendList">
	</div>
</div>

<div id="taskFromManual">
	<div id="manualList" class="subMenu"><span>매뉴얼에서 선택하기</span></div>
	<div id="manual">
	</div>	
</div>
<div id="taskFromTyping">
	<div id="inputToggle" class="subMenu selected"><span>직접 입력하기/매뉴얼에서 검색하기</span></div>
	<div>
		<input id="addTaskFromTypingInput" type="text" placeholder="업무명을 입력해주세요" style="margin-left: 10px; width: 78%;">
		<button id="addTask" class="addButton" style="display: inline;">추가</button>
		<div id="addTaskFromTyping" class="w3-dropdown-hover" style="display: inline;">
		<div id="searchFromManual" class="w3-dropdown-content w3-bar-block w3-border" style="margin-left: 10px;">
  		</div>
	</div>
	</div>
</div>
</div>
<!-- 선택된 업무 출력 -->
<div id="selectedTaskModal">
</div>
<div id="selectedTaskPopup">
    <span id="closeModal" class="w3-button" style="pad; float: right; margin-top:5px; margin-right: 15px; padding:3px 3px; font-size: 30px;">&times;</span>
    <div id="modalHeader"><h4>선택된 업무</h4></div>
    <br>
    <div style="color: gray; font-size: 10px; margin-left: 20px; display: block;">중요여부 click!</div>
    <div id="selectedTask"></div>
    <span id="goNext" class="w3-button" style="pad;">
	다음 단계 
    <img alt="next" src="images/forward.png" width="17px" style="padding: 5 px; margin-left: 10px; margin-bottom: 2px;">
    </span>
</div>
<br>
<br>
<script>
$("#recommendToggle").click(function(){
	$("#recommendToggle").toggleClass("selected");
	if($(this).hasClass("selected")){
		$("#recommendList").show();
	} else {
		$("#recommendList").hide();
	}
});
//매뉴얼목록 토글
$("#manualList").click(function() {
	$("#manualList").toggleClass("selected");
	if($(this).hasClass("selected")){
		manualSpaceMode();
		$("#manual").show();
	} else {
		$("#manual").hide();
	}
});
$("#inputToggle").click(function(){
	$("#inputToggle").toggleClass("selected");
	if($(this).hasClass("selected")){
		$(this).next().show();
	} else {
		$(this).next().hide();
	}
});
var activateli = function(input) {
	//업무 리스트 중 1개를 클릭 -> 업무 배정에 추가
	$("#selectedTask").html(input.childNodes[0].nodeValue);
	$("#selectedTaskModal").css({
		display : "block"
	});
	$("#selectedTaskPopup").css({
		display : "block"
	});
}
//매뉴얼 db연결
var manualSpaceMode = function() {
	$.ajax({
		url : "task/manual/spacetypes",
		success : function(result) {
			$("#manual").html(result);
		}
	});
}

//추천업무 가져오기 
var getRecommendedTasks = function(){
	$.ajax({
		url : "task/manual/recommend/tasks",
		data : {
			date : "${param.date}"
		},
		success : function(result) {
			$("#recommendList").html(result);
		}
	});
}
$(document).ready(function(){
	getRecommendedTasks();
})

var activateAcc = function(input) {
	input.classList.toggle("active");
	var panel = input.nextElementSibling;
	if (panel.style.maxHeight) {
		panel.style.maxHeight = null;
	} else {
		$.ajax({
			url : "task/manual/" + input.id + "/tasktypes",
			data : {
				spaceType : input.id,
			},
			success : function(result) {
				panel.innerHTML = result;
				panel.style.maxHeight = panel.scrollHeight + "px";
			}
		});
	}
};

var activateSubAcc = function(input) {
	input.classList.toggle("active");
	var panel = input.nextElementSibling;
	if (panel.style.maxHeight) {
		panel.style.maxHeight = null;
	} else {
		$.ajax({
			url : "task/manual/type/tasks",
			data : {
				spaceType : input.parentNode.previousElementSibling.id,
				taskType : input.id
			},
			success : function(result) {
				panel.innerHTML = result;
				panel.style.maxHeight = panel.scrollHeight + "px";
				var motherPanel = input.parentNode;
				motherPanel.style.maxHeight = motherPanel.scrollHeight
						+ panel.scrollHeight + "px";
				//#### 페이지가 이벤트 처리이후 데이터를 실시간으로 가져올 때는 이후 관련 이벤트도 같이 연결 = getTasks.jsp로 코드 이동
			}
		});
	}
}

var activateAccBasic = function(input) {
	input.classList.toggle("active");
	var panel = input.nextElementSibling;
	if (panel.style.maxHeight) {
		panel.style.maxHeight = null;
	} else {
		panel.style.maxHeight = panel.scrollHeight + "px";
	}
};

//직접 입력하여 업무 추가
$("#addTask").click(function() {
	if ($("#addTaskFromTypingInput").val() != "") {
		$("#selectedTask").html($("#addTaskFromTypingInput").val());
		$("#selectedTaskModal").css({
			display : "block"
		});
		$("#selectedTaskPopup").css({
			display : "block"
		});
	}
});

$(".w3-bar-item.w3-button").click(function() {
	$("#selectedTask").html($(this).html());
	$("#selectedTaskModal").css({
		display : "block"
	});
	$("#selectedTaskPopup").css({
		display : "block"
	});
});
//선택or입력된 업무를 리스트에서 지우기

$("#closeModal").on("click", function() {
	$("#selectedTaskModal").css({
		display : "none"
	});
	$("#selectedTaskPopup").css({
		display : "none"
	});
	$("#selectedTask").removeClass("important");
});

$("#selectedTask").on("click", function() {
	this.classList.toggle("important");
});

$("#goNext").on("click", function() {
	$.ajax({
        url : "admin/task/daily/exist", 
        data : {  
        	dailyTask : $("#selectedTask").html().trim(), 
        	assignDate : "${param.date}",
        },
        success : function(result) {
        	result = JSON.parse(result);
			if(result["result"] == "true") {
				alert('해당 날짜에 이미 배정된 업무입니다.')
				$("#closeModal").click();
			} else {
				var importance = 0;
				if($("#selectedTask").hasClass("important")){
					importance = 1;
				}
				location.href = "admin/task/daily/assign/next?selectedTask="
						+$("#selectedTask").html().trim()
						+"&date=${param.date}&dateKor=${param.dateKor}&importance="
						+importance;
			}
        }
    });
});

//자동완성
$("#searchFromManual").width($("#addTaskFromTypingInput").width());
$("#addTaskFromTypingInput").on("keyup", function() {
	if ($(this).val() == "") {
		$("#searchFromManual").html("");
		return;
	}
	$("#searchFromManual").css({
		display : "block"
	});
	$.ajax({
		url : "task/manual/search/tasks",
		data : {
			inputText : $("#addTaskFromTypingInput").val()
		},
		success : function(result) {
			$("#searchFromManual").html(result);
		}
	})
});
var goInputText = function(input) {
	$("#addTaskFromTypingInput").val($(input).html());
	$("#searchFromManual").html("");
}
</script>

</body>
</html>