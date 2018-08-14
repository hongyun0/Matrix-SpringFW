<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${param.staffId!=null}">
	<span onclick="closeModal()" class="w3-button" style="pad; float: right; margin-top:5px; margin-right: 15px; padding:3px 3px; font-size: 30px;">&times;</span>
	<div class="setWorkPartHeader"><h4>소속 파트 수정</h4></div>
	<br><p class="staffInfoToSetWorkPart" id="${param.staffId}" style="margin-bottom: 5px;"><span>${param.staffName}</span>님의 소속파트를 수정합니다.</p><br>
</c:if>
<c:forEach var="part" items="${workParts}">
	<c:choose>
		<c:when test="${part==param.oldWorkPart}">
			<c:if test="${!empty param.oldWorkPart && param.oldWorkPart != ''}">
				<button class="selected workParts" >${part}</button>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${!empty part && part != ''}">
				<button class="workParts" onclick="setWorkPart(this)">${part}</button>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:forEach>
