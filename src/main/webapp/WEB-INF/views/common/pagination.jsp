<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <table class="paginated">
    <thead></thead>
    <tbody>
    第${pager.pageNo}页共${pager.pageCount}页
    <c:forEach var ="i" begin="1" end ="${pager.pageCount}">
      <A HREF="###" onclick="goPage(this, '<c:url value="/admin/enterprise?page=${i}"/>')" >${i}</A>&nbsp;
    </c:forEach>
    总计${pager.totalRows}条数据
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

<%-- TODO 将所选的数据进行
  <input type="submit" value="删除" class="btnDel" />
 --%>