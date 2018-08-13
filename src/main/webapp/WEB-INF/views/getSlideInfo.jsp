<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:out escapeXml="false" value='{"branchName" : "${info.BRANCH_NAME}", "name" : "${info.NAME}", "profilePhoto" : "${info.PROFILE_PHOTO}", "type" : "${type}"}'></c:out>