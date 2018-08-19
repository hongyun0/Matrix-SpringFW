<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file = "loginCheckAdmin.jsp" %>
<%@include file = "tabMenuAdmin.jsp" %>
<style>
.dailyHeader {
	margin: auto;
	text-align: center;
	width: 50%;
	border-bottom: #d4e5f7 solid;
}
.dailyHeader h4{
	margin-bottom: 5px;
	color: rgb(0, 51, 102);
	font-weight: bolder;
	letter-spacing: 2px;
}
.accordion {
	background: linear-gradient(#d4e5f7, #d4e5f7, #d4e5f7, #d4e5f7, rgba(212, 229, 247, 0.4));
    color: #003366;
    cursor: pointer;
    padding: 8px 8px 8px 20px;
    width: 95%;
    text-align: left;
    font-weight: bold;
    outline: none;
    font-size: 15px;
    transition: 0.4s;
    margin: auto;
    border-radius: 5px 5px 5px 5px;
    border: 1px solid white;
    letter-spacing: 1px;
}
.active {
    background: linear-gradient(#206591, #206591, #206591, #206591, rgba(32, 101, 145, 0.2));
    color: #FFFFFF;
}

.accordion:after {
	font-family: FontAwesome;
    content: '\f0da';
    font-size: 20px;
    color: #003366;
    float: right;
    margin-top: -3px;
    margin-right: 10px;
    vertical-align: middle;
}

.active:after {
    font-family: FontAwesome;
    content: '\f0d7';
    color: #FFFFFF;
}

.panel{
    width: 95%;
    background: linear-gradient(#FFFFFF, #FFFFFF, #FFFFFF, #FFFFFF, rgba(192, 222, 241, 0.3));
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.2s ease-out;
    margin: auto;
    border-radius: 0 0 5px 5px;
}

.unfinished {
	color: #bababa;
}
ul {
	list-style: none;
}
li {
	list-style: none;
	font-size: 15px;
	margin-left: -20px;
	margin-right: 13px;
}
li.important::before {
	content: "\2605";
	color: #3284e2;
	font-size: 12px;
	margin-left: -15px;
}
.deleting li:after {
	content: "\26D4";
	color: rgb(255, 230, 230);
}
/* li.deleting:hover:after {
} */
.ui-datepicker-trigger {
	width: 35px;
	cursor: pointer;
	float: left;
	margin-left: 50px;
}
.ui-datepicker-trigger:hover {
	filter: grayscale(100%);
}
#date{
	margin-left: 4px;
	margin-top: 3px;
	margin-bottom: 5px;
	color: rgb(0, 51, 102);
	font-weight: bolder;
	letter-spacing: 1px;
	display:inline;
}
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}
#scrollPast {
	float: left;
	margin-left: 20px;
	margin-top: 10px;
	vertical-align: middle;
}
#scrollFuture {
	float: right;
	margin-right: 25px;
	margin-top: 10px;
	vertical-align: middle;
}
#scrollPast:hover, #scrollFuture:hover {
	filter: brightness(20%);
}
.disabled#scrollFuture, .disabled#scrollFuture:hover {
	filter: opacity(0%);
}
#assignTaskButton {
	float: right; 
	margin-right: 50px;
	margin-top: 1px;
	vertical-align: middle;
	cursor: pointer;
}
#assignTaskButton.disabled {
	filter: saturate(0%) brightness(520%);
	cursor: default;
}
#assignTaskButton:not(.disabled):hover {
	filter: grayscale(100%);
}
.container {
	max-width: 1000px;
    margin: auto;
    background: #3284E2;
	text-align: center;
}
#selectedTask::before {
	content: "\2605";
	color: lightgray;
	width: 1em;
}
.important#selectedTask::before {
	content: "\2605";
	color: orange;
	width: 1em;
}
.yesButton{
	background-color: white;
	border: rgb(50, 132, 226) 1px solid;
	border-radius: 10px;
	color: black;
	padding: 5px 10px 5px 10px;
}
.noButton{
	background-color: white;
	border: rgb(255, 128, 128) 1px solid;
	border-radius: 10px;
	color: black;
	padding: 5px 10px 5px 10px;
}
</style>
<script type="text/javascript">
$("#dailyTaskTab").addClass("tapped");
$(function(){
   $("#datepicker").datepicker({
      showOn : "button",
      buttonImage : "images/calendar_navy.png",
      buttonText: "날짜 선택",
      buttonImageOnly : true,
      showButtonPanel : true,
      currentText : '이번 달로',
      closeText : '닫기',
      nextText : "다음 달",
      prevText : "이전 달",
      dateFormat : "yy/mm/dd",
      altField: "#alterDate",
      altFormat: "m"+"월 "+"d"+"일",
      showMonthAfterYear : true,
      showOtherMonths : true,
      selectOtherMonths : true,
      changeMonth: false, //true 하면 스크롤로 월 선택 가능
        dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        yearSuffix: "년",
      maxDate: "+1d",
      onSelect : function(dateText, inst){
       contentLoad();
    }
   });
});
</script>
</head>
<body>
<div class="dailyHeader">
    <h4>일일업무 관리</h4>
</div>
<!-- 날짜선택바 -->
<div id="dateMenu" class="w3-center" style="vertical-align: middle; padding:10px;">
   <span>
      <input type="text" id="datepicker" hidden="hidden">
      <input type="text" id="alterDate" hidden="hidden">
  </span>
  <span>
      <img id="scrollPast" src="images/leftTriangle.png" width="15px"/>
   </span>
   <h4 id="date"></h4>
   <span>
       <img id="assignTaskButton" alt="assign" title="업무 배정" src="images/assign_navy.png" width="30px">
   </span>
   <span>
      <img id="scrollFuture" src="images/rightTriangle.png" width="15px"/>
   </span>
</div>

<!-- <h2 id="dailyTaskTitle" style="text-align:center;">dailyTaskTitle</h2> --> <!-- 오늘/내일/과거 업무 보기 -->
<div id="content" style="min-height: 70px;"></div>
<div style="text-align: center; margin-top: 15px;">
   <button id="updateTask" class="yesButton">수정</button>
   <button id="deleteTask" class="noButton">삭제</button>
</div>

<div id="updateTaskModal" style="z-index:3;display:none;position:fixed;left:0;top:0;width:100%;height:100%;overflow:auto;background-color:rgba(0,0,0,0.4);">
</div>
<div id="updateTaskPopup" style="z-index:4;position:absolute;outline:0;margin:50px;top:100px;overflow-y:auto;background-color: white; display:none;">
    <span id="updateTaskContent"></span>
</div>
<c:choose>
	<c:when test="${!empty param.date}">
		<script>
		$(document).ready(function() {
			$("#datepicker").datepicker("setDate", "${param.date}");
			contentLoad();
		});
		</script>
	</c:when>
	<c:otherwise>
		<script>
		$(document).ready(function() {
			$("#datepicker").datepicker("setDate", "0d");
			contentLoad();
		});
		</script>
	</c:otherwise>
</c:choose>
<script>

	// 아코디언
	$("#content").on("click", ".accordion", function() {
		activateAcc(this);
	});
	
	var activateAcc = function(input){
	    input.classList.toggle("active");
	    var panel = input.nextElementSibling;
	    if (panel.style.maxHeight){
	      panel.style.maxHeight = null;
	    } else {
			$.ajax({
		        url : "admin/task/daily/" + input.id, 
		        data: {
		        	assignDate : $("#datepicker").val(),
		        	assignType : input.id
				},
		        success : function(result){
		        	panel.innerHTML = result;
		        	panel.style.maxHeight = panel.scrollHeight + "px";
		        }
		    });
	    } 
	}
	
	/**날짜 포맷 변환*/
	Date.prototype.format = function(){
		var date = this;
		var year = date.getFullYear();
		var month = (1 + date.getMonth());
		month = month >= 10 ? month : '0' + month;
		var day = date.getDate();
		day = day >= 10 ? day : '0' + day;
		return year + '/' + month + '/' + day;
	}

	/**일일업무 컨텐츠 로드 및 UI 설정*/
	var contentLoad = function() {
		$("#date").html($("#alterDate").val());
		var today = new Date();
		var tomorrow = new Date();
		tomorrow.setDate(today.getDate() + 1);
		today = today.format("yyyy/MM/dd");
		tomorrow = tomorrow.format("yyyy/MM/dd");
		if ($("#datepicker").val() == today) {
			$("#updateTask").show();
			$("#deleteTask").show();
			$("#scrollFuture").removeClass("disabled");
			$("#assignTaskButton").removeClass("disabled");
		} else if ($("#datepicker").val() == tomorrow) {
			$("#updateTask").show();
			$("#deleteTask").show();
			$("#scrollFuture").addClass("disabled");
			$("#assignTaskButton").removeClass("disabled");
		} else {
			$("#updateTask").hide();
			$("#deleteTask").hide();
			$("#scrollFuture").removeClass("disabled");
			$("#assignTaskButton").addClass("disabled");
		}
		$.ajax({
			url : "admin/task/daily/parts",
			data : {
				date : $("#datepicker").val()
			},
			success : function(result) {
				$("#content").html(result);
			}
		});
	}
	

	/**왼쪽/오른쪽 버튼으로 날짜 선택 시 세부 페이지 이동 기능*/
	$("#scrollPast").on("click", function() {
		var dateVal = $("#datepicker").datepicker("getDate");
		dateVal.setDate(dateVal.getDate() - 1);
		$("#datepicker").datepicker("setDate", dateVal);
		contentLoad();
	});

	$("#scrollFuture").on("click", function() {
		var dateVal = $("#datepicker").datepicker("getDate");
		dateVal.setDate(dateVal.getDate() + 1);
		$("#datepicker").datepicker("setDate", dateVal);
		contentLoad();
	});

	/**업무 수정, 삭제  기능*/
	$("#updateTask").click(function() {
		$(this).toggleClass("clicked");
		$("#deleteTask").hide();
		if ($(this).hasClass("clicked")) {
			$(".assignDetail").not($(".unfinished")).parent("li").css({
				"color" : "#bababa"
			});
			$(".unfinished").show();
			$(".unfinished").prev().show();
			$(".unfinished").next().show();
			$(this).html("수정 완료");
		} else {
			$(".assignDetail").not($(".unfinished")).parent("li").css({
				"color" : "black"
			});
			$(".unfinished").not($(".personal").children()).hide();
			$(".unfinished").prev().hide();
			$(".unfinished").next().hide();
			$("#deleteTask").show();
			$(this).html("수정");
		}
	});

	$("#closeModal").on("click", function() {
		$("#updateTaskContent").html("");
		$("#updateTaskModal").css({
			display : "none"
		});
		$("#updateTaskPopup").css({
			display : "none"
		});
	});

	var activateTask = function(input) {
		var imp = 0;
		if ($(input).parent().hasClass("important")) {
			imp = 1;
		}
		location.href = "controller?cmd=setDailyTaskUI&oldDailyTask="+input.parentNode.childNodes[0].nodeValue
					+"&assignDetail="+$(input).next().prop("id")
					+"&assignName="+$(input).next().text()
					+"&assignDate="+$("#datepicker").val()
					+"&importance="+imp;
	}
	
	var activateAssign = function(input) {
		var assignType = '파트';
		if ($(input).parent().hasClass("personal")) {
			assignType = '개인';
		}
		var imp = false;
		if ($(input).parent().hasClass("important")) {
			imp = true;
		}
		$.ajax({
			url : "controller?cmd=setDailyAssignUI",
			data : {
				oldAssignType : assignType,
				oldAssignDetail : $(input).next().id,
				oldAssignName : $(input).next().text(),
				dailyTask : $(input).parent().text(),
				assignDate : $("#datepicker").val(),
				importance : imp
			},
			success : function(result) {
				$("#updateTaskContent").html(result);
			}
		});
		$("#updateTaskModal").css({
			display : "block"
		});
		$("#updateTaskPopup").css({
			display : "block"
		});
	}

	$("#deleteTask").click(function() {
		$("#updateTask").hide();
		$("#content").toggleClass("deleting");
		if ($("#content").hasClass("deleting")) {
			$(this).html("삭제 완료");
			$("#content").click(function(event) {
				if (!$(event.target).is("li")) {
					return;
				}
				var task = event.target.childNodes[0].nodeValue.trim();
				if(confirm(task + ' 업무를 삭제하시겠습니까?')){
					var type = '파트';
					if($(event.target).hasClass("personal")){
						type = '개인';
					}
					$.ajax({
						url : "admin/task/daily/remove",
						type : "POST",
						data : {
							dailyTask :  task,
							assignType : type,
							assignDetail : $(event.target).children(".assignDetail").attr("id"),
							assignDate : $("#datepicker").val()
						},
						success : function(result) {
							if(result.result == "succeed") {
								alert(task +' 업무가 삭제되었습니다.')
							}
							location.href="admin/task/daily?date=" + $("#datepicker").val();
						}
					});
				}
			});
		} else {
			$(this).html("삭제");
			$("#updateTask").show();
			$("li").click("");
		}
		
	});

	/**assignTaskAdmin 페이지로 이동*/
	$("#assignTaskButton").click(function() {
		if(!$(this).hasClass("disabled")){
			location.href = "admin/task/daily/assign?date="+$("#datepicker").val()+"&dateKor="+$("#alterDate").val();
		}
		/* $.ajax({
			url : "controller?cmd=assignTaskAdminUI&date"+$("#datepicker").val()+"&dateKor="+$("#alterDate").val(),
			data : {
				date : $("#datepicker").val(),
				dateKor : $("#alterDate").val()
			},
			success : function(result) {
				$("#result").html(result);
			}
		}); */
	});
</script>

</body>
</html>
