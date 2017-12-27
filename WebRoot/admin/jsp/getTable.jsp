<%@page import="com.fasterxml.jackson.databind.ser.SerializerFactory" import="com.fasterxml.jackson.core.JsonGenerator.Feature"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/admin/jsp/";
%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"  import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
  <c:choose>
  <c:when test="${empty param.page}">
  <c:set var="pnum" value="1"></c:set>
  </c:when>
  <c:otherwise>
  <c:set var="pnum" value="${param.page}"></c:set>
  </c:otherwise>
  </c:choose>
  
  <c:choose>
  <c:when test="${empty param.rows}">
  <c:set var="rownum" value="10"></c:set>
  </c:when>
  <c:otherwise>
  <c:set var="rownum" value="${param.rows}"></c:set>
  </c:otherwise>
  </c:choose>
  <c:set var="offset" value="${(pnum-1)*rownum}"></c:set>
  <c:set var="col" value="oid"></c:set>
<c:out value="${fn:toUpperCase(fn:substring(col, 0,1))}${fn:substring(col, 1,fn:length(col))}"/>
<c:out value="==========================================================="/>
  <c:import url="conn.jsp"></c:import>
<sql:query dataSource="${sessionScope.shop}" var="result">
SELECT * from <c:out value="${param.tableName}" default="users"></c:out> limit <c:out value="${offset}"></c:out>,<c:out value="${rownum}"></c:out>;
</sql:query>

	<sql:query dataSource="${sessionScope.shop}" var="total">
	SELECT count(1) as cn from <c:out value="${param.tableName}" default="users"></c:out>;
	</sql:query>
<c:set var="list" scope="session" value="${result}" ></c:set>
<c:set var="total" scope="session" value="${total.rows[0].cn}" ></c:set>
	 <%
	 org.apache.taglibs.standard.tag.common.sql.ResultImpl rst= (org.apache.taglibs.standard.tag.common.sql.ResultImpl)request.getSession().getAttribute("list");
	 ObjectMapper mapper = new ObjectMapper();
	 String json = mapper.writeValueAsString(rst);
	 mapper.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true) ;
		
	 // json = json.replace("rowCount", "total");
	 @SuppressWarnings("unchecked")
	 LinkedHashMap<String,Object> list = mapper.readValue(json, LinkedHashMap.class);
	 LinkedHashMap<String,Object> list2 = new LinkedHashMap<String,Object>();
	 
	 list2.put("total", ""+request.getSession().getAttribute("total"));
	 list2.put("rows", list.get("rows"));
	 json = mapper.writeValueAsString(list2);
	 
	 %>
<%=json %>	 

