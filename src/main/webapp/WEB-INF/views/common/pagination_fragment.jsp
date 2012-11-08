<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
  <table class="paginated">
    <thead></thead>
    <tbody>
    <c:set var="noRecord" value="${pager.totalRows == 0}" />
    <c:set var="pagingURL" value="${param.pagingURL}" />
    <c:if test="${fn:indexOf(pagingURL, '?') == -1}">
    	<c:set var="pagingURL" value="${param.pagingURL}?" />
    </c:if>
    <c:choose>
	    <c:when test="${!noRecord}">
		    第${pager.pageNo}页共${pager.pageCount}页
		    <c:forEach var ="i" begin="1" end ="${pager.pageCount}">
		      <%-- 改进分页体验 --%><A HREF="###" onclick="goPage(this, '<c:url value="${pagingURL}"/>' + '&page=${i}')" >${i}</A>&nbsp;
		    </c:forEach>
		    第${pager.startRow + 1}条至第${pager.endRow}条记录，共${pager.totalRows}条记录
	    </c:when>
	    <c:otherwise>
	    	没有记录
	    </c:otherwise>
    </c:choose>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

<%-- TODO 将所选的数据进行
  <input type="submit" value="删除" class="btnDel" />
 --%>