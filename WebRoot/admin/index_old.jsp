<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	
	<title>jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css"  href="css/icon.css">
	<link rel="stylesheet" type="text/css"  href="css/demo.css">
	
	
	<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery.datagrid.js"></script>
	<script type="text/javascript" src="js/jquery.edatagrid.js"></script>
	<style type="text/css">
	table{
	text-align:center
	}
	</style>
	<!-- 
	<script type="text/javascript" src="http://www.jeasyui.net/Public/js/easyui/jquery.edatagrid.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${not empty param.table}">
		$('#dg').edatagrid({
			url: 'datagrid?flag=<c:out value="${param.table}"/>',
			saveUrl: 'php/save_user.php',
			updateUrl: 'php/update_user.php',
			destroyUrl: 'php/destroy_user.php'
		});
		</c:if>
	});
	</script>
	-->
</head>
<body>
<!-- 
	<h2>CRUD DataGrid</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip">&nbsp;</div>
		<div>Double click the row to begin editing.</div>
	</div>
	<c:import url="jsp/dataTable.jsp"></c:import>
 -->
</body>
</html>
