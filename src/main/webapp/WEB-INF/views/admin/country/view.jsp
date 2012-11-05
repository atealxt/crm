<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

  <table>
    <thead>
      <th colspan="2">详细信息（<a href="<c:url value="/admin/country/${o.id}?edit=true"/>">编辑</a>）</th>
    </thead>
    <tbody>
    <tr>
      <td>国家ID</td>
      <td><c:out value="${o.id}"/></td>
    </tr>
    <tr>
      <td>国家名称</td>
      <td><c:out value="${o.name}"/></td>
    </tr>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>
