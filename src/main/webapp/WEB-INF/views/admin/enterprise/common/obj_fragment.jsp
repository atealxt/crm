<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <tr>
      <td>关键字</td>
      <td><form:input path="keyword"/></td>
    </tr>
    <tr>
      <td>所属国家</td>
      <td><form:select path="country.id"><form:option value="" label="--- Select ---"/><form:options items="${countries}" itemValue="id" itemLabel="name"/></form:select>
      <a href="###" id="newCountry">新添</a>
      <span id="spanNewCountry" style="display:none;"><form:input path="country.name"/></span>
      </td>
    </tr>
    <tr>
      <td>公司名称</td>
      <td><form:input path="name" cssErrorClass="form-error-field"/>
      <div class="form-error-message"><form:errors path="name"/></div></td>
    </tr>
    <tr>
      <td>联系人</td>
      <td><form:input path="contact"/></td>
    </tr>
    <tr>
      <td>邮箱</td>
      <td><form:input path="email"/></td>
    </tr>
    <tr>
      <td>电话</td>
      <td><form:input path="tel"/></td>
    </tr>
    <tr>
      <td>手机</td>
      <td><form:input path="mobileNo"/></td>
    </tr>
    <tr>
      <td>传真</td>
      <td><form:input path="faxNo"/></td>
    </tr>
    <tr>
      <td>来源网站</td>
      <td><form:input path="source"/></td>
    </tr>
    <tr>
      <td>备注</td>
      <td><form:input path="remark"/></td>
    </tr>

    <script src="<c:url value="/javascripts/admin/enterprise.js"/>" type="text/javascript"></script>