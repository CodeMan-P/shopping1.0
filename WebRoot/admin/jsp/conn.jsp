<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/admin/jsp/";
%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:choose>
<c:when test="${empty sessionScope.shop}">
  <sql:setDataSource var="shop" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://localhost:3306/shop?characterEncoding=UTF-8"
     user="root"  password="mysql"
     scope="session"/>
</c:when>
</c:choose>

