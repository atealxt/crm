<#escape x as x?html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>test</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
</head>
<body>

<#--TODO 模板tag化、支持排序等-->

  <table class="paginated">
    <thead>
      <th>ID</th>
      <th>NAME</th>
      <th>AGE</th>
    </thead>
    <tbody>
    <#list list as Father>
    <tr>
      <td>${Father.id}</td>
      <td>${Father.name}</td>
      <td><#if Father.age??>${Father.age}</#if></td>
    </tr>
    </#list>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

<#--TODO 分页也用pagination.js-->
  <table class="paginated">
    <thead></thead>
    <tbody>
        第${pageNo}页共${pageCount}页
    <#list 1..pageCount as i>
      <A HREF="${requestContext.contextPath}/pagination_back?page=${i}">${i}</A>&nbsp;
    </#list>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>


</body>
</html>
</#escape>