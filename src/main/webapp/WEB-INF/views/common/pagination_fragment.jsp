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
       <input type="hidden" id="pagingURL" value='<c:url value="${pagingURL}"/>'/>
            转至第 <input type="text" id="pageNo" name="page" size="3" style="width:20px;" maxlength="5" /> 页
       <input type="button" id="gotoPage" value="Go">&nbsp;
            <c:choose>
                <c:when test="${pager.pageNo != 1}">
                    <A HREF="###" onclick="goPage(this, '<c:url value="${pagingURL}"/>', ${1})" >首页</A>&nbsp;
                    <A HREF="###" onclick="goPage(this, '<c:url value="${pagingURL}"/>', ${pager.pageNo - 1})" >上一页</A>&nbsp;
                </c:when>
                <c:otherwise>
                    <A>首页</A>&nbsp;
                    <A>上一页</A>&nbsp;
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pager.pageNo != pager.pageCount}">
                    <A HREF="###" onclick="goPage(this, '<c:url value="${pagingURL}"/>', ${pager.pageNo + 1})" >下一页</A>&nbsp;
                    <A HREF="###" onclick="goPage(this, '<c:url value="${pagingURL}"/>', ${pager.pageCount})" >末页</A>&nbsp;
                </c:when>
                <c:otherwise>
                    <A>下一页</A>&nbsp;
                    <A>末页</A>&nbsp;
                </c:otherwise>
            </c:choose>
            <input type="hidden" id="pageUrl" name="pageUrl" value='<c:url value="${pagingURL}"/>' />
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