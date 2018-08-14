<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="staff" items="${staffs}">
	<a class="staffBox" onclick="setWorkStaff(this)">
	<c:if test="${!empty staff.PROFILE_PHOTO && staff.PROFILE_PHOTO != '' }">
		<img src="images/profile/${staff.PROFILE_PHOTO}" title="${staff.STAFF_ID}" style="width:30pt; border-radius: 50%;">
	</c:if>
	<c:if test="${staff.PROFILE_PHOTO == null}">
		<img src="images/profile/defaultProfile.png" title="${staff.STAFF_ID}" style="width:30pt; border-radius: 50%;">
	</c:if>
    <span class="staffName">${staff.NAME}</span>
	</a>
</c:forEach>
