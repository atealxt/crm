<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

  <link rel="stylesheet" href="<c:url value="/stylesheets/paging.css"/>" type="text/css" media="screen" />

  <form:form commandName="condition">
  <c:set var="recycle" value="${param.status == '-1'}" />
  <c:choose>
	  <c:when test="${!recycle}">
	  	<c:set var="pagingURL" value="/admin/enterprise" />
	  </c:when>
	  <c:otherwise>
	  	<c:set var="pagingURL" value="/admin/enterprise?status=-1" />
	  </c:otherwise>
  </c:choose>
  <jsp:include page="/WEB-INF/views/common/pagination_fragment.jsp">
  	<jsp:param name="pagingURL" value="${pagingURL}"/>
  </jsp:include>

  <c:if test="${!recycle}">
  	<A HREF="<c:url value="/admin/enterprise/add"/>" >新建</A>
  </c:if>

  <table class="paginated">
    <thead>
      <th></th>
      <th>企业ID</th>
      <th>关键字</th>
      <th>所属国家</th>
      <th>公司名称</th>
      <th>联系人</th>
      <th>邮箱</th>
      <th>电话</th>
      <th>手机</th>
      <th>传真</th>
      <th>来源网站</th>
      <th>备注</th>
      <th>创建时间</th>
      <th>修改时间</th>
      <th>操作</th>
    </thead>
    <tbody>
    <tr>
      <td> </td>
      <td></td>
      <td><form:input path="keyword"/> </td>
      <td><form:input path="country.name"/> </td>
      <td><form:input path="name"/> </td>
      <td><form:input path="contact"/> </td>
      <td><form:input path="email"/> </td>
      <td><form:input path="tel"/> </td>
      <td><form:input path="mobileNo"/> </td>
      <td><form:input path="faxNo"/> </td>
      <td><form:input path="source"/> </td>
      <td><form:input path="remark"/> </td>
      <td></td>
      <td></td>
      <td><input type="submit" value="查询" /></td>
    </tr>

    <c:forEach items="${list}" var="o">
    <tr>
      <td><%--<input type="checkbox" name="selectedObj" value="${o.id}" />  --%></td>
      <td><c:out value="${o.id}"/></td>
      <td><c:out value="${o.keyword}"/></td>
      <td><c:out value="${o.country.name}"/></td>
      <td><c:out value="${o.name}"/></td>
      <td><c:out value="${o.contact}"/></td>
      <td><c:out value="${o.email}"/></td>
      <td><c:out value="${o.tel}"/></td>
      <td><c:out value="${o.mobileNo}"/></td>
      <td><c:out value="${o.faxNo}"/></td>
      <td><c:out value="${o.source}"/></td>
      <td><c:out value="${o.remark}"/></td>
      <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd"/></td>
      <td><fmt:formatDate value="${o.updateTime}" pattern="yyyy-MM-dd"/></td>
      <td>
      <c:if test="${!recycle}">
      <input type="button" value="查看" onclick="viewRecord('<c:url value="/admin/enterprise/${o.id}"/>')" />
      <input type="button" value="编辑" onclick="editRecord('<c:url value="/admin/enterprise/${o.id}?edit=true"/>')" />
      </c:if>
      <c:if test="${recycle}">
      <input type="button" value="恢复" onclick="restoreRecord(this, '<c:url value="/admin/enterprise/${o.id}/restore"/>')" />
      </c:if>
      <input type="button" value="删除" onclick="deleteRecord(this, '<c:url value="/admin/enterprise/${o.id}"/>', '<c:url value="/admin/enterprise"/><c:if test="${recycle}">?status=-1</c:if>', <c:out value="${!recycle}"/>)" />
      </td>
    </tr>
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

  <jsp:include page="/WEB-INF/views/common/pagination_fragment.jsp">
  	<jsp:param name="pagingURL" value="${pagingURL}"/>
  </jsp:include>

  </form:form>

  <%-- TODO data link, order --%>