<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <div id="sidebar">
        <ul>
        	<c:forEach items="${mapLink}" var="entry">
        		<spring:message code="${entry.key}" text="${entry.key}"/>
			    <c:forEach items="${entry.value}" var="e" varStatus="s">
			        <li><a href="<spring:url value="${e.link}"/>" target="main"><spring:message code="${e.msgShow}" text="${e.msgShow}"/></a></li>
			    </c:forEach>
			    <br/>
        	</c:forEach>
        </ul>
    </div>