<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>test</title>
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=UTF-8">
</head>

<body>

  <table class="paginated">
    <thead>
      <th>ID</th>
      <th>NAME</th>
      <th>AGE</th>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="Father">
    <tr>
      <td>${Father.id}</td>
      <td>${Father.name}</td>
      <td><c:if test="${Father.age!=null}">${Father.age}</c:if></td>
    </tr>
    </c:forEach>
    </tbody>
    <tfoot><th></th></tfoot>
  </table>

</body>

  <link rel="stylesheet" href="stylesheets/paging.css" type="text/css" media="screen" />
  <script src="javascripts/jquery.min.js" type="text/javascript"></script>
  <script src="javascripts/pagination.js" type="text/javascript"></script>
  <script type="text/javascript">
      $(document).ready(function() {
        $('table.paginated').staticPaginate(2);
      });
  </script>

</html>
