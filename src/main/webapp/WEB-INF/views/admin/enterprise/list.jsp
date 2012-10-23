<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CRM</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
</head>
<body>

  <form:form commandName="condition">

  <jsp:include page="/WEB-INF/views/common/pagination.jsp"></jsp:include>

  <input type="submit"/>

  <table class="paginated">
    <thead>
      <th>企业ID</th>
      <th>关键字</th>
      <th>所属国家</th>
      <th>公司名称</th>
      <th>联系人</th>
      <th>邮箱</th>
      <th>电话</th>
      <th>手机</th>
      <th>传真</th>
      <th>来源网站</th>
      <th>备注</th>
      <th>创建时间</th>
      <th>修改时间</th>
    </thead>
    <tbody>
    <tr>
      <td><form:input path="id"/> </td>
      <td><form:input path="keyword"/> </td>
      <td><form:input path="country.name"/> </td>
      <td><form:input path="name"/> </td>
      <td><form:input path="contact"/> </td>
      <td><form:input path="email"/> </td>
      <td><form:input path="tel"/> </td>
      <td><form:input path="mobileNo"/> </td>
      <td><form:input path="faxNo"/> </td>
      <td><form:input path="source"/> </td>
      <td><form:input path="remark"/> </td>
      <td></td>
      <td></td>
    </tr>

    <c:forEach items="${list}" var="e">
    <tr>
      <td><c:out value="${e.id}"/></td>
      <td><c:out value="${e.keyword}"/></td>
      <td><c:out value="${e.country.name}"/></td>
      <td><c:out value="${e.name}"/></td>
      <td><c:out value="${e.contact}"/></td>
      <td><c:out value="${e.email}"/></td>
      <td><c:out value="${e.tel}"/></td>
      <td><c:out value="${e.mobileNo}"/></td>
      <td><c:out value="${e.faxNo}"/></td>
      <td><c:out value="${e.source}"/></td>
      <td><c:out value="${e.remark}"/></td>
      <td><c:out value="${e.createTime}"/></td>
      <td><c:out value="${e.updateTime}"/></td>
    </tr>
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

  <jsp:include page="/WEB-INF/views/common/pagination.jsp"></jsp:include>

  </form:form>

</body>
</html>