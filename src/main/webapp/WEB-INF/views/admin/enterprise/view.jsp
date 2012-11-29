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
<h4>企业备忘录（<a id="aAddMemo" href="###">添加</a>）</h4>

<script type="text/javascript" src="<c:url value="/javascripts/admin/enterprise.js"/>"></script>
<style>
#divNewMemo{ width: 60%; display:none; }
#divNewMemo input{ margin-top: 5px; margin-bottom: 30px; }
</style>
<div id="divNewMemo">
    <script type="text/javascript" src="<c:url value="/tinymce/tiny_mce.js"/>"></script>
    <form action="<c:url value="/admin/enterprise/addMemo"/>" method="post">
        <input type="hidden" name="eId" value="<c:out value="${o.id}"/>"/>
        <script>
            tinyMCE.init({
                mode : "exact",
                theme : "advanced",
                elements: "content",
                plugins : "iespell",
                theme_advanced_toolbar_location : "top",
                theme_advanced_toolbar_align : "left",
                theme_advanced_buttons1 : "separator, newdocument, bold, italic, underline, strikethrough, fontsizeselect, separator, justifyleft, justifycenter, justifyright, justifyfull, separator, bullist, numlist, separator, outdent, indent, separator, link, image, forecolor, backcolor, charmap, separator, iespell, code",
                theme_advanced_buttons2 : "",
                theme_advanced_buttons3 : "",
                content_css : "<c:url value='/css/editor.css'/>",
                language : 'en',
                force_p_newlines: false,
                force_br_newlines: true,
                auto_resize : false,
                verify_html : "false"
            });

        </script>
        <textarea id="content" rows="15" cols="20" style="width:20%;" name="content"></textarea>
        <input type="submit" value="添加">
    </form>
</div>

<c:if test="${fn:length(o.memos) > 0}">
    <form id="formMemo" action="<c:url value="/admin/enterprise/deleteMemo"/>" method="post">
    <input type="hidden" name="eId" value="<c:out value="${o.id}"/>"/>
    <input type="hidden" name="memoId" id="memoId" />
    <c:forEach items="${o.memos}" var="memo" varStatus="loopStatus">
        <table>
            <thead>
            </thead>
            <tr>
                <td name="t.createTime">创建时间：<fmt:formatDate value="${memo.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td align="right"><input type="button" value="删除" onclick="deleteMemo(<c:out value="${memo.id}"/>)"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:out value="${memo.content}" escapeXml="false"/>
                </td>
            </tr>
        </table>
        <br>
    </c:forEach>
    </form>
</c:if>
