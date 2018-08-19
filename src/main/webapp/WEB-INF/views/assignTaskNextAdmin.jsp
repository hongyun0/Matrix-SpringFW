<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file = "loginCheckAdmin.jsp" %>
<%@include file = "tabMenuAdmin.jsp" %>
<style>
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
	letter-spacing: 1px;
}


.active {
	background: linear-gradient(#206591, #206591, #206591, #206591, rgba(32, 101, 145,
		0.2));
	color: #FFFFFF;
}

.accordion:after {
	font-family: FontAwesome;
	content: '\f0da';
	font-size: 17px;
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

.panel {
	width: 90%;
	background: linear-gradient(#FFFFFF, #FFFFFF, #FFFFFF, #FFFFFF, rgba(192, 222, 241, 0.3));
	max-height: 0;
	overflow: hidden;
	transition: max-height 0.2s ease-out;
	margin: auto;
	border-radius: 0 0 5px 5px;
}
.assignTaskHeader{
	margin: auto;
	text-align: center;
	width: 50%;
	border-bottom: #d4e5f7 solid;
	text-align: center;
}

.assignTaskHeader h4{
	margin-bottom: 5px;
	color: rgb(0, 51, 102);
	font-weight: bolder;
	letter-spacing: 2px;
}
ul {
	margin-left: -20pt;
	list-style: none;
}
li {
	list-style: none;
	font-size: 14px;
	margin-left: -10px;
	margin-right: 5px;
	margin-bottom: 3px;
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
	font-size: 15px;
	letter-spacing: 1px;
}
.subMenu::after {
	content: '\2795';
	color: #003366;
	font-size: 12px;
	padding-left: 5px;
}
.subMenu.selected::after {
	content: '\2796';
}
.staffs{
	border: 2px solid black;
    padding: 0 18px;
    float: center;
}
.selectedAssignDetail{
	font-size: 16px;
	font-weight: bold;
	color: #699fbb;
}
.panel.assignableStaffs {
	overflow: auto;
    white-space: nowrap;
}
.staffBox {
	display: inline-block;
	margin: 7px;
}
.staffName {
	margin-top: 5px;
	display: block;
	font-size: 15px;
}
.workparts {
	margin-bottom : 10px;
    background-color: #cccccc;
    padding: 10px;
    font-size: 16px;
    font-weight: bold;
    border: none;
    display: inline-block;
}
.selected {
	font-size: 16px;
	font-weight: bold;
	color: #699fbb;
}
#selectedTaskBox {
	box-shadow: 5px 5px 5px lightgray;
	border-radius: 10px;
	width: 90%;
	margin-left: 5%;
}
#selectedTask {
	margin-left: 55px;
	font-size: 17px;
	font-weight: bold;
	vertical-align: middle;
	margin-bottom: 3px;
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
.workParts {
	padding: 8px;
	margin: 10px 10px 17px 10px;
	background: #ededed;
	border: none;
	border-radius: 10px;
	box-shadow: 2px 2px 5px lightgray;
}
#assignButton {
	color: white;
	background-color: #003366;
	border-color: #003366;
	border-style: solid;
	border-width: 1px;
	padding: 5px 10px 5px 10px;
}
#assignCancelButton {
	background-color: white;
	border-color: rgb(255, 230, 230);
	border-style: solid;
	border-width: 1px;
	padding: 5px 10px 5px 10px;
}
</style>
</head>
<body>
<div class="assignTaskHeader">
	<h4>업무 배정</h4>
</div>
<h4 id="date">${param.dateKor}</h4>
<div id="selectedTaskBox">
	<h4 id="selectedTask">${param.selectedTask}</h4>
</div>
<div id="assignTask">
	<p style="font-size: 15px; margin-left: 20px; margin-bottom: 3px; font-weight: bold;">담당자 지정하기</p>
	<div class="accordion" id="assignableParts">파트별</div>
	<div class="panel assignableParts">
	</div>
	
	<div class="accordion" id="assignableStaffs">개인별</div>
	<div class="panel assignableStaffs">
	</div>
</div>
<br>
<h6 style="text-align: center;">해당 업무를 <span class="selectedAssignDetail"></span><span id="selectedAssignType"></span> 배정합니다.</h6>
<br>
<div style="text-align: center;">
<!-- <input type="button" class="assignButton" value="배정하기">
<input type="button" class="assignCancelButton" value="취소"> -->
<button id="assignButton">배정하기</button>
<button id="assignCancelButton">취소</button>
</div>
<br>
<br>
<script>
$(document).ready(function() {
	if("${param.importance}"==1){
		$("#selectedTask").addClass("important");
	}
});

$("#assignableParts").on("click", function() {
	this.classList.toggle("active");
	var panel = this.nextElementSibling;
	if (panel.style.maxHeight) {
		panel.style.maxHeight = null;
	} else {
		$.ajax({
			url : "admin/employee/workparts",
			success : function(result) {
				panel.innerHTML = result;
				panel.style.maxHeight = panel.scrollHeight + "px";
			}
		});
	}
});
var setWorkPart = function(input){
	$(".selected").removeClass("selected");
	$(input).addClass("selected");
	$(".selectedAssignDetail").html($(input).html());
	$(".selectedAssignDetail").attr("id", $(input).html());
	$("#selectedAssignType").html(" 파트에");
}

var setWorkStaff = function(input){
	$(".selected").removeClass("selected");
	$(".selectedAssignDetail").html($(input).clone());
	$(".selectedAssignDetail").attr("id", $(input).children("img").prop("title"));
	$(input).children(".staffName").addClass("selected");
	$("#selectedAssignType").html(" 님에게");
}

$("#assignableStaffs").on("click", function() {
	this.classList.toggle("active");
	var panel = this.nextElementSibling;
	if (panel.style.maxHeight) {
		panel.style.maxHeight = null;
	} else {
		$.ajax({
			url : "admin/employee/assignable",
			success : function(result) {
				panel.innerHTML = result;
				panel.style.maxHeight = panel.scrollHeight + 25 +"px";
			}
		});
	}
});

var subAcc = document.querySelectorAll(".subAccordion");

for (i = 0; i < subAcc.length; i++) {
	subAcc[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var panel = this.nextElementSibling;
    if (panel.style.maxHeight){
      panel.style.maxHeight = null;
    } else {
      panel.style.maxHeight = panel.scrollHeight + "px";
    } 
    
    var motherPanel = this.parentNode;
    motherPanel.style.maxHeight = motherPanel.scrollHeight + panel.scrollHeight + "px";

  });
}
var li = document.querySelectorAll("li");
var i;

for (i = 0; i < li.length; i++) {
	li[i].addEventListener("click", function() {
		 this.classList.toggle("selected");
		 if(this.classList.contains("selected")){
			var addLi = document.createElement("li");
	   		var nodes = this.childNodes;
	   		for(var j = 0; j < nodes.length; j++){
	   			addLi.appendChild(nodes[j].cloneNode(true));
	   		}
			document.querySelector("#selectedTasksList").appendChild(addLi);
		 } else {
			 var list = document.querySelector("#selectedTasksList");
			 var li = list.firstChild;
			 for(var j = 0; j < list.childNodes.length-1; j++){
				 li = li.nextElementSibling;
				 if(li.innerHTML == this.innerHTML) {
					 list.removeChild(li);
					 break;
				 }
			 }
		 }
	})
};

$('#assignButton').on('click',function (){
	var selectedAssignType = "개인";
	if($("#selectedAssignType").html().trim()=="파트에"){
		selectedAssignType = "파트";
	}
	 $.ajax({
	        url : "admin/task/daily/assign", 
	        type : "POST",
	        data : {  
	        	dailyTask : "${param.selectedTask}", 
	        	assignDate : "${param.date}",
	        	importance : "${param.importance}", 
	        	assignType : selectedAssignType, 
	        	assignDetail : $(".selectedAssignDetail").prop("id")
	        },
	        success : function(result) {
				if(result.result == "succeed") {
	        		alert("${param.selectedTask} 업무를 " + $(".selectedAssignDetail").text().trim() + $(".selectedAssignType").text() + "에게 배정했습니다.");
					location.href = "admin/task/daily?date=${param.date}";
				}
	        }
	 });
});

$('#assignCancelButton').on('click',function (){
	        	alert("이전 화면으로 돌아갑니다.");
				location.href = "admin/task/daily?date=${param.date}";
});
</script>
</body>
</html>
