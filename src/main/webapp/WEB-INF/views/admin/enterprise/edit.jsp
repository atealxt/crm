<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

  <form:form commandName="o">
  <form:hidden path="id"/>
  <form:hidden path="countMailSent"/>
  <input type="hidden" id="returnToList" name="returnToList"/>

  <table>
    <thead>
      <th colspan="2">编辑信息</th>
    </thead>
    <tbody>
    <tr>
      <td>企业ID</td>
      <td><c:out value="${o.id}"/></td>
    </tr>
    <jsp:include page="/WEB-INF/views/admin/enterprise/common/obj_fragment.jsp"></jsp:include>
    <tr>
      <td colspan="2">
        <input type="submit" value="保存" title="保存并返回企业详细信息页面" />
        <input type="button" id="submitReturnToList" value="保存并返回列表" title="保存并返回企业列表页面" />
        <input type="reset"/>
      </td>
    </tr>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

  </form:form>

  <script src="<c:url value="/javascripts/jquery.min.js"/>" type="text/javascript"></script>
  <script type="text/javascript">
  (function($) {
	  $('.enterpriseId').click(function() {
		  window.open('<c:url value="/admin/enterprise/"/>' + $(this).attr("title").replace(",", ""));
	  });
  })(jQuery);
  </script>