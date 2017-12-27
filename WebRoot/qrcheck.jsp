<%@ page errorPage="jsp/ShowError.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>首页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" href="css/lanrenzhijia.css" media="all">
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
			$('.theme-popover-mask').fadeIn(100);
			$('.theme-popover').slideDown(200);
	});
</script>
</head>

<body style="text-align: center;">

<div class="theme-popover" style="text-align: center;">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>登录 是一种态度</h3>
     </div>
   
     <div class="theme-popbod dform" name="form">
           <form class="theme-signin" name="loginform" id="fm" action="./QrCode" method="post">
                <ol>
                     <li><strong>用户名：</strong><input class="ipt" type="text" name="name" value="" size="20" /></li>
                     <li><strong>密码：</strong><input class="ipt" type="password" name="pwd" value="" size="20" /></li>
                     <li >
                     <input type="hidden" name = "flag" id="flag" value="check">
                    
                     <input class="btn btn-primary" style="left:60px" type="submit" id="b" value="登 录 "/></li>
                </ol>
           </form>
     </div>
</div>

	
</body>
</html>
