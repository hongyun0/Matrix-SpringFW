<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty workingStaffs}">
	<br>재직중인 직원이 없습니다.<br><br>
</c:if>
<table class="resultStaffTable">
<c:forEach var="workingStaff" items="${workingStaffs}">
	<tr id="${workingStaff.STAFF_ID}">
		<c:choose>
			<c:when test="${workingStaff.PROFILE_PHOTO != null}">
				<td onclick="getStaffDetail(this)"><img class="profilePhoto" src="images/profile/${workingStaff.PROFILE_PHOTO}"></td>
			</c:when>
			<c:otherwise>
				<td onclick="getStaffDetail(this)"><img class="profilePhoto" src="images/profile/defaultProfile.png"></td>
			</c:otherwise>
		</c:choose>
		<td class="workingStaffName" onclick="getStaffDetail(this)">${workingStaff.NAME}</td>
		<td>${workingStaff.GENDER}</td>
		<td>${workingStaff.BIRTH}</td>
		<c:choose>
			<c:when test="${workingStaff.WORK_PART != null}">
				<td><input type="button" onclick="getWorkPart(this)" value="${workingStaff.WORK_PART}" style="float: right" class="yesButton"></td>
			</c:when>
			<c:otherwise>
				<td><input type="button" onclick="getWorkPart(this)" value=" - " style="float: right" class="yesButton"></td>
			</c:otherwise>
		</c:choose>
		<td><input type="button" onclick="setLeaveDate(this)" value="퇴사" style="float: right" class="noButton"></td>
	</tr>
</c:forEach>
</table>
