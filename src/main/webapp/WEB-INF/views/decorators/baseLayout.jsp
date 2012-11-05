<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/looso.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CRM Administrator</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
    <link rel="stylesheet" href="<c:url value="/stylesheets/common.css"/>" type="text/css" media="screen" />
  	<script src="<c:url value="/javascripts/jquery.min.js"/>" type="text/javascript"></script>
</head>
<body>

  <jsp:include page="/WEB-INF/views/common/message_fragment.jsp"></jsp:include>

  <decorator:body />

  <script src="<c:url value="/javascripts/pagination.js"/>" type="text/javascript"></script>
  <script src="<c:url value="/javascripts/common.js"/>" type="text/javascript"></script>

</body>
</html>