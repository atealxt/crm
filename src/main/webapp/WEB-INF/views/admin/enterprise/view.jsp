<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link rel="stylesheet" href="<c:url value="/stylesheets/paging.css"/>" type="text/css" media="screen" />

<h4>详细信息（<a href="<c:url value="/admin/enterprise/${o.id}?edit=true"/>">编辑</a>）</h4>
<table>
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
    <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
  </tr>
  <tr>
    <td>修改时间</td>
    <td><fmt:formatDate value="${o.updateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
  </tr>
  <tr>
    <td>邮件发送次数</td>
    <td><c:out value="${o.countMailSent}"/></td>
  </tr>
  <tr>
    <td>最后发送时间</td>
    <td><fmt:formatDate value="${o.latestMailSent}" pattern="yyyy-MM-dd HH:mm"/></td>
  </tr>
  </tbody>
  <tfoot><th></th></tfoot>
</table>

<hr>
<h4>企业备忘录（<a href="###">添加</a>）</h4><%-- TODO 添加 删除 样式 等 --%>
<c:if test="${fn:length(o.memos) > 0}">
    <c:forEach items="${o.memos}" var="memo" varStatus="loopStatus">
        <table>
            <thead>
            </thead>
            <tr>
                <td name="t.createTime">创建时间：<fmt:formatDate value="${memo.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td align="right"><input type="button" value="删除"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:out value="${memo.content}" escapeXml="false"/>
                </td>
            </tr>
        </table>
        <br>
    </c:forEach>
</c:if>
