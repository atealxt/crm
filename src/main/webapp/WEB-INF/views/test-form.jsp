<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Demo</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <style>
            .form-item { margin: 20px 0; }
            .form-label { font-weight: bold; }
            .form-error-field { background-color: red; }
            .form-error-message { font-weight: bold; color: #900; }
        </style>
</head>

<body>

 <form:form commandName="child">

  Input child info:<br>
  <table class="paginated">
   <thead>
   </thead>
   <tbody>
    <tr>
     <td>name</td>
     <td>
     <form:input path="name" cssErrorClass="form-error-field"/>(可以试着不输入值，submit就报错了)
     <div class="form-error-message"><form:errors path="name"/></div>
     </td>
    </tr>
    <tr>
     <td>money</td>
     <td>
     <form:input path="money" cssErrorClass="form-error-field"/>
     <div class="form-error-message"><form:errors path="money"/></div>
     </td>
    </tr>
    <tr>
     <td>father.name</td>
     <td>
     <form:input path="father.name" cssErrorClass="form-error-field"/>
     <div class="form-error-message"><form:errors path="father.name"/></div>
     </td>
    </tr>
   </tbody>
   <tfoot>
    <th></th>
   </tfoot>
  </table>

  <input type="submit" />

 </form:form>

</body>
</html>