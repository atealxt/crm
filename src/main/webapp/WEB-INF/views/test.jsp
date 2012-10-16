<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>test</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
</head>
<body>

    <c:forEach items="${dataList}" var="data" varStatus="s">
        <c:out value="${data}"/>
        <c:if test="${s.index!=3}">|</c:if>
    </c:forEach>
    | <spring:message code="currentlocale" text="default message"/>
    | <spring:message code="hello" text="default message" arguments="Luis"/>
    | <fmt:message key="currentlocale"/>
    <hr/>

    ${Father} | id:${Father.id} | name:${Father.name} | age:${Father.age} <br/>
    <c:forEach items="${Father.children}" var="child">
    ${child} | id:${child.id} | name:${child.name} | father:${child.father}
    </c:forEach>
    <hr/>

    <fmt:formatDate value="${dt}" pattern="yyyy-MM-dd HH:mm:ss"/> |
    <fmt:formatNumber value="${num}" type="number" var="money"/>
    <c:out value="${money}"/>
    <hr/>

    <c:forEach items="${map}" var="entry">
        <c:out value="${entry.key}" /> = <c:out value="${entry.value}" />;
    </c:forEach>
    | ${map["key2"]}
    <hr/>

    <c:if test="${empty aaa}">property aaa not exist!</c:if>
    <hr/>

    <c:forEach items="${Teachers}" var="teacher">
    ${teacher} | id:${teacher.id} | name:${teacher.name} | Students: ${teacher.students}<br>
    </c:forEach>

</body>
</html>