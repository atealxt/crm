<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CRM Administrator</title>
</head>

    <frameset cols="197,*" frameborder="yes" border="1" framespacing="0">
        <frame name="menu" src="<spring:url value="/admin/menu"></spring:url>" scrolling="no">
        <frame name="main" src="">
    </frameset>

</html>