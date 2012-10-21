<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CRM</title>
</head>

<body>

    <div id="sidebar">
        <ul>
		    <c:forEach items="${links}" var="e" varStatus="s">
		        <li><a href="<spring:url value="${e.link}"/>" target="main"><c:out value="${e.msgShow}"/></a></li>
		    </c:forEach>
        </ul>
    </div>

</body>
</html>