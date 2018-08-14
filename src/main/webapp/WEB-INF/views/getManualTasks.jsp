<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
<c:forEach var="task" items="${tasks}">
	<li onclick="activateli(this)"">${task.MANUAL_TASK}
		<span class="interval" style="float: right;">
			<c:if test="${task.REPEAT_TYPE == '파트'}">매일 ${task.REPEAT_DETAIL}조</c:if>
			<c:if test="${task.REPEAT_TYPE == '기간'}"><span>${task.REPEAT_DETAIL}일</span> 마다</c:if>
		</span>
	</li>
</c:forEach>
</ul>