<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <table class="paginated">
    <thead></thead>
    <tbody>
        第${pageNo}页共${pageCount}页
    <c:forEach var ="i" begin="1" end ="${pageCount}">
      <A HREF="<c:url value="/admin/enterprise?page=${i}"/>">${i}</A>&nbsp; <%-- TODO change to submit --%>
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>