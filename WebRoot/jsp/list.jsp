<%@ page errorPage="ShowError.jsp" %>
<%@ page language="java" import="java.util.*" import="com.mod.bean.Goods"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>产品搜索页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
<link href="./img/bootstrap.min.css" rel="stylesheet"/>
<link href="./img/style.css" rel="stylesheet"/>
<script src="./img/bootstrap.min.js.下载"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		
		$("#bt2").click(function(){
		$("#form2").submit();
	
	
		
		
	  });
	
	});
	
	
	
	</script>
  </head>
  
  <body>
  <nav class="top ">
	<div class="top_middle">
	
		<a href="../index.jsp">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
			
		</a>	
		
			<a href="../index.jsp"style="float:right">退出</a>	
		
		
			<a href="javascript:void(0);" style="float:right"><c:out value="${sessionScope.name}" ></c:out></a>
			
				<a href="../Spcar?flag=view" target="_blank" style="float:right;color:#F00">
			购物车(${sessionScope.carnum})
			</a>
	</div>
</nav>
<div class="simpleSearchOutDiv">
	<a href="../index.jsp">
		<img id="simpleLogo" class="simpleLogo" width="190"height="30" src="./img/simpleLogo.png">	
	</a>
	
	<form action="../search" method="post">	
	<div class="simpleSearchDiv pull-right">
		<input type="text" placeholder="请输入想要的东西吧" value="" name="word">
		<button class="searchButton" type="submit">搜索</button>
		
	</div>
	</form>
	<div style="clear:both"></div>
</div>
<div class="cartDiv">
	<div class="cartTitle pull-right">
		<form action="../search" id="form2" method="get">
 
<input type="submit" value="价格排序" id="bt2">
</form>
	</div>
	
	<div class="cartProductList">
		<table class="cartProductTable" cellspacing="30px">
<%----------------------------------列表循环 --%>
<c:set value="0"	var="index" ></c:set>
 <c:forEach var="list"		 items="${productList}">

<c:set value="${index+1}"	var="index" ></c:set>
<c:if test="${index == 1}">
<tr>
</c:if>
		
		
		<td style="width:30%;border: solid 1px #CCCCCC;margin:30px;	 text-align: center;">
		<a href="../ProInfo?gid=${list.gid}">
		<img alt="商品图片" src="${list.imgpath}" style="width: 188px;position:relative;	top:1px; height: 188px">
		</a><br/>
		<span	style="color:#f40">${list.gname }</span><br/>
		<span	style="color:#F40">	￥<strong style="color: #F40;font-weight: 700;font-family: arial;" >${list.price }</strong></span><br/>
		<span>库存：${list.stock }</span><br/>
		</td>
		
<c:if test="${index == 3}">
</tr>
<c:set value="0"	var="index" ></c:set>
</c:if>

 </c:forEach>
 
 <c:if test="${index != 0}">
<c:choose>
<c:when test="${index == 1}">
<td></td><td></td>
</c:when>
<c:otherwise>
<td></td>
</c:otherwise>
</c:choose>
</tr>
</c:if>
<%----------------------------------列表循环 --%>
		</table>
	</div>
</div>

 

  </body>
 
</html>
