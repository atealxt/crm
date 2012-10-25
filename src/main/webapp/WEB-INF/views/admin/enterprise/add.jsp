<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/looso.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CRM</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
    <style>
	    .form-item { margin: 20px 0; }
	    .form-label { font-weight: bold; }
	    .form-error-field { background-color: red; }
	    .form-error-message { font-weight: bold; color: #900; }
	</style>
</head>
<body>

  <form:form commandName="o">
  <form:hidden path="id"/>

  <table>
    <thead>
      <th colspan="2">添加信息</th>
    </thead>
    <tbody>
    <jsp:include page="/WEB-INF/views/admin/enterprise/common/obj_fragment.jsp"></jsp:include>
    <tr>
      <td colspan="2"><input type="submit" value="提交" /> <input type="reset"/></td>
    </tr>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

  </form:form>

</body>
</html>