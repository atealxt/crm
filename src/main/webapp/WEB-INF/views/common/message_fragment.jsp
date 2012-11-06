<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="dynamicErrorMessage"></div>
<div id="dynamicWarnMessage"></div>
<div id="dynamicInfoMessage"></div>
<c:forEach items="${requestScope.ERRORS}" var="data">
    <div><spring:message code="${data.message}" text="${data.message}" arguments="${data.reasons}"/></div>
</c:forEach>
<c:forEach items="${requestScope.INFOS}" var="data">
    <div><spring:message code="${data.key}" text="${data.defaultMessage}" arguments="${data.args}"/></div>
</c:forEach>