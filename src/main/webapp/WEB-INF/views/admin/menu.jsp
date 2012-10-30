<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <div id="sidebar">
        <ul>
        	<c:forEach items="${mapLink}" var="entry">
        		<c:out value="${entry.key}" />
			    <c:forEach items="${entry.value}" var="e" varStatus="s">
			        <li><a href="<spring:url value="${e.link}"/>" target="main"><c:out value="${e.msgShow}"/></a></li>
			    </c:forEach>
        	</c:forEach>
        </ul>
    </div>