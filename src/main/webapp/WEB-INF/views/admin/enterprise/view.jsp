<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/looso.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CRM</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
</head>
<body>

  <table>
    <thead>
      <th colspan="2">查看详细信息</th> <%-- TODO 添加修改按钮 --%>
    </thead>
    <tbody>
    <tr>
      <td>企业ID</td>
      <td><c:out value="${o.id}"/></td>
    </tr>
    <tr>
      <td>关键字</td>
      <td><c:out value="${o.keyword}"/></td>
    </tr>
    <tr>
      <td>所属国家</td>
      <td><c:out value="${o.country.name}"/></td>
    </tr>
    <tr>
      <td>公司名称</td>
      <td><c:out value="${o.name}"/></td>
    </tr>
    <tr>
      <td>联系人</td>
      <td><c:out value="${o.contact}"/></td>
    </tr>
    <tr>
      <td>邮箱</td>
      <td><c:out value="${o.email}"/></td>
    </tr>
    <tr>
      <td>电话</td>
      <td><c:out value="${o.tel}"/></td>
    </tr>
    <tr>
      <td>手机</td>
      <td><c:out value="${o.mobileNo}"/></td>
    </tr>
    <tr>
      <td>传真</td>
      <td><c:out value="${o.faxNo}"/></td>
    </tr>
    <tr>
      <td>来源网站</td>
      <td><c:out value="${o.source}"/></td>
    </tr>
    <tr>
      <td>备注</td>
      <td><c:out value="${o.remark}"/></td>
    </tr>
    <tr>
      <td>创建时间</td>
      <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
      <td>修改时间</td>
      <td><fmt:formatDate value="${o.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

</body>
</html>