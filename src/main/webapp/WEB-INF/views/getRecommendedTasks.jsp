<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>

</style>
<c:forEach var="space" items="${spaces}">
	<div class="accordion recommended" id="${space}" onclick="activateAccBasic(this)">${space}</div>
	<div class="panel recommended"><ul>
	<c:forEach var="task" items="${tasks}">
	<c:if test="${task.SPACE_TYPE == space}">
	<li onclick="activateli(this)">${task.MANUAL_TASK} <span class="interval" style="float: right;">${task.REPEAT_DETAIL}일 마다</span></li>
	</c:if>
	</c:forEach>
	</ul></div>
</c:forEach>
