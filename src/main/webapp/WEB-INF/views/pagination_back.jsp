<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>test</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
</head>
<body>

  <table class="paginated">
    <thead>
      <th>ID</th>
      <th>NAME</th>
      <th>AGE</th>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="Father">
    <tr>
      <td>${Father.id}</td>
      <td>${Father.name}</td>
      <td><c:if test="${Father.age!=null}">${Father.age}</c:if></td>
    </tr>
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

  <table class="paginated">
    <thead></thead>
    <tbody>
        第${pageNo}页共${pageCount}页
    <c:forEach var ="i" begin="1" end ="${pageCount}">
      <A HREF="${pageContext.request.contextPath}/pagination_back?page=${i}">${i}</A>&nbsp;
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>


</body>
</html>