<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <tr>
      <td>国家名称</td>
      <td><form:input path="name" cssErrorClass="form-error-field"/>
      <div class="form-error-message"><form:errors path="name"/></div></td>
    </tr>
