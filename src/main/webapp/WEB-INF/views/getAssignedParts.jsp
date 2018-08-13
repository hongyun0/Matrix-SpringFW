<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="part" items="${parts}">
 	<div class="accordion assignedPart" id="${part}">${part} 파트</div>
 	<div class="panel"></div>
</c:forEach>
<c:if test="${personal}">
	<div class="accordion assignedPart" id="개인">개인 업무</div>
 	<div class="panel"></div>
</c:if>
<c:if test="${!personal && empty parts}">
	<p style="text-align: center;">배정된 업무가 없습니다.</p>
</c:if>
 	