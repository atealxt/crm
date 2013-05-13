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
      <input type="button" value="新建" onclick="createRecord('<c:url value="/admin/enterprise/add"/>')" />
      <input type="button" value="给匹配的企业发送邮件" onclick="sendEmail(this, '<c:url value="/admin/enterprise/sendEmail"/>')" />
  </c:if>

  <c:set var="order" value="<%= session.getAttribute(com.zhyfoundry.crm.web.controller.EnterpriseController.EMAIL_CONTIDION_ORDER) %>" />
  <input type="hidden" id="order" name="order" value="<c:out value="${order}"  />" />
  <input type="hidden" name="<%= com.zhyfoundry.crm.web.controller.EnterpriseController.PARAM_USE_PREVIOUS_LIST %>" value="false" />
  <table class="paginated">
    <thead>
      <th></th><%-- TODO checkbox --%>
      <th name="t.keyword">关键字</th>
      <th name="t.country.name">所属国家</th>
      <th name="t.name">公司名称</th>
      <th name="t.contact">联系人</th>
      <th name="t.email">邮箱</th>
      <th name="t.tel">电话</th>
      <th name="t.mobileNo">手机</th>
      <th name="t.faxNo">传真</th>
      <th name="t.source">来源网站</th>
      <th name="t.remark">备注</th>
      <th name="t.createTime">创建时间</th><%-- TODO 时间控件 --%>
      <th name="t.updateTime">修改时间</th>
      <th name="t.countMailSent">邮件发送次数</th>
      <th name="t.latestMailSent">最后发送时间</th>
      <th>操作</th>
    </thead>
    <tbody>
    <tr>
      <td> </td>
      <td><form:input path="keyword"/> </td>
      <td><form:select path="country.id"><form:option value="" label="--- Select ---"/><form:options items="${countries}" itemValue="id" itemLabel="name"/></form:select></td>
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
      <td></td>
      <td></td>
      <td><input type="submit" value="查询" /></td>
    </tr>

    <c:forEach items="${list}" var="o" varStatus="loopStatus">
    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
      <td><%--<input type="checkbox" name="selectedObj" value="${o.id}" />  --%></td>
      <td><c:out value="${o.keyword}"/></td>
      <td><c:out value="${o.country.name}"/></td>
      <td><c:out value="${o.name}"/></td>
      <td><c:out value="${o.contact}"/></td>
      <td><c:out value="${o.emailBrief}"/></td>
      <td><c:out value="${o.tel}"/></td>
      <td><c:out value="${o.mobileNo}"/></td>
      <td><c:out value="${o.faxNo}"/></td>
      <td><c:out value="${o.source}"/></td>
      <td><c:out value="${o.remark}"/></td>
      <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd"/></td>
      <td><fmt:formatDate value="${o.updateTime}" pattern="yyyy-MM-dd"/></td>
      <td><c:out value="${o.countMailSent}"/></td>
      <td><fmt:formatDate value="${o.latestMailSent}" pattern="yyyy-MM-dd"/></td>
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

  <script src="<c:url value="/javascripts/admin/enterprise.js"/>" type="text/javascript"></script>

  <%-- TODO data link --%>