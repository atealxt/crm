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
</head>
<body>

  <form:form commandName="o">
  <form:hidden path="id"/>

  <table>
    <thead>
      <th colspan="2">修改详细信息</th>
    </thead>
    <tbody>
    <tr>
      <td>企业ID</td>
      <td><c:out value="${o.id}"/></td>
    </tr>
    <tr>
      <td>关键字</td>
      <td><form:input path="keyword"/></td>
    </tr>
    <tr>
      <td>所属国家</td>
      <td><form:input path="country.name"/></td>
    </tr>
    <tr>
      <td>公司名称</td>
      <td><form:input path="name"/></td>
    </tr>
    <tr>
      <td>联系人</td>
      <td><form:input path="contact"/></td>
    </tr>
    <tr>
      <td>邮箱</td>
      <td><form:input path="email"/></td>
    </tr>
    <tr>
      <td>电话</td>
      <td><form:input path="tel"/></td>
    </tr>
    <tr>
      <td>手机</td>
      <td><form:input path="mobileNo"/></td>
    </tr>
    <tr>
      <td>传真</td>
      <td><form:input path="faxNo"/></td>
    </tr>
    <tr>
      <td>来源网站</td>
      <td><form:input path="source"/></td>
    </tr>
    <tr>
      <td>备注</td>
      <td><form:input path="remark"/></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="提交" /> <input type="reset"/>
      </td>
    </tr>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>
  
  </form:form>

</body>
</html>