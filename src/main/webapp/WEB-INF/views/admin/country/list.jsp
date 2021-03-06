<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

  <link rel="stylesheet" href="<c:url value="/stylesheets/paging.css"/>" type="text/css" media="screen" />

  <form:form commandName="condition">

  <input type="button" value="新建" onclick="createRecord('<c:url value="/admin/country/add"/>')" />

  <table class="paginated">
    <thead>
      <th></th>
      <th>国家名</th>
      <th>操作</th>
    </thead>
    <tbody>
    <tr>
      <td> </td>
      <td><form:input path="name"/> </td>
      <td><input type="submit" value="查询" /></td>
    </tr>

    <c:forEach items="${list}" var="o" varStatus="loopStatus">
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
      <td></td>
      <td><c:out value="${o.name}"/></td>
      <td>
      <input type="button" value="查看" onclick="viewRecord('<c:url value="/admin/country/${o.id}"/>')" />
      <input type="button" value="编辑" onclick="editRecord('<c:url value="/admin/country/${o.id}?edit=true"/>')" />
      <input type="button" value="删除" onclick="deleteRecord(this, '<c:url value="/admin/country/${o.id}"/>', '<c:url value="/admin/country"/>', false)" />
      </td>
    </tr>
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

  <script type="text/javascript">
      $(document).ready(function() {
        $('table.paginated').staticPaginate(20);
      });
  </script>

  </form:form>

  <%-- TODO data link, order --%>