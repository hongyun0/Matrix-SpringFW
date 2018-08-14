<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="type" items="${types}">
	<div class="subAccordion ${mode}" id="${type}" onclick="activateSubAcc(this)">${type}</div>
	<div class="subPanel"></div>
</c:forEach>
