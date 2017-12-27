<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/admin/jsp/";
%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>table</title>
</head>
<body>
<c:import url="getTable.jsp" var="lis"></c:import>
<table border="1" width="100%">
<tr>
   <th>ID</th>
   <th>First Name</th>
   <th>Last Name</th>
   <th>phone</th>
   <th>email</th>
</tr>

<c:forEach var="row" items="${list.rows}">
<tr>
   <td><c:out value="${row.id}"/></td>
   <td><c:out value="${row.firstname}"/></td>
   <td><c:out value="${row.lastname}"/></td>
   <td><c:out value="${row.phone}"/></td>
   <td><c:out value="${row.email}"/></td>
</tr>
</c:forEach>
</table>
 
</body>
</html>
