<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>SSH Demo</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/json.min.js" type="text/javascript"></script>
</head>

<body>

<a href="test.servlet">test by servlet</a>
<hr>
<a href="test.action">test by spring-mvc</a>
<hr>
<a href="testFlush.action">test flush</a>
<hr>
<a href="testAutoRollBack.action">test AutoRollBack</a>
<hr>
<a href="testMultiDataSource.action">test MultiDataSource</a>
<hr>
<a href="redirecttest.action">redirect response test(spring standard controller)</a>
<br>
<a href="response.action?p1=a&p2=b">use gzip</a>
<br>
<a href="response.action?p1=a&p2=b&donotuseGzip=1">do not use gzip</a>
<hr>
<a href="test-rest-json.jsp">rest json test</a>
<br>
<hr>
pagination test<br>
<a href="pagination_back">back</a><br>
<a href="pagination_front">front</a><br>
<a href="pagination_static">static</a>
<br>
<hr>
<a href="test-upload.jsp">test upload file</a><br>
<hr>
<a href="test-form.action">test form submit</a>
<hr>

</body>
</html>
