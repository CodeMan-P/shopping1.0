<%@ page language="java" import="java.util.*" import="com.mod.bean.Users" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'jstlExm.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h1>测试JSTL核心库</h1>  
    <hr>  
    <li>采用c:out标签</li><br>  
    hello(使用标签):<c:out value="123"/><br>  
    hello(使用标签):<c:out value="hello"/><br>  
    hello(使用标签):<c:out value="${hello}"/><br>  
    hello(使用EL表达式):${hello }<br>  
    hello(default):${hello123 }<br>  
    hello(使用缺省值):<c:out value="${hello123}" default="没有值"/><br>  
    hello(使用缺省值):<c:out value="${hello123}">没有值</c:out><br>  
    welcome(使用EL表达式):${welcome }<br>  
    welcome(使用标签,escapeXml=true):<c:out value="${welcome}" escapeXml="true"/><br>  
    welcome(使用标签,escapeXml=false):<c:out value="${welcome}" escapeXml="false"/><br>  
    <p>  
      
    <li>测试c:set,c:remove</li><br>  
    <c:set value="root" var="userid"/>  
    userid:${userid }<br>  
    <c:remove var="userid"/>  
    userid:${userid }<br>  
    <p>  
      
    <li>条件控制标签：c:if</li><br>  
    <c:if test="${v1 lt v2}">  
        v1小于v2<br>  
    </c:if>  
    <p>  
    <li>条件控制标签：c:choose,c:when,c:otherwise</li><br>  
    <c:choose>  
        <c:when test="${v1 gt v2}">  
            v1大于v2<br>  
        </c:when>  
        <c:otherwise>  
            v1小于v2<br>  
        </c:otherwise>  
    </c:choose>  
    <c:choose>  
        <c:when test="${empty userList}">  
            没有符合条件的数据<br>  
        </c:when>  
        <c:otherwise>  
            存在用户数据<br>  
        </c:otherwise>  
    </c:choose>  
    <p>  
    <li>演示循环控制标签：forEach</li><br>  
    <h3>采用jsp脚本显示</h3>  
    <table border="1">  
        <tr>  
            <td>用户名称</td>  
            <td>年龄</td>  
            <td>所属组</td>  
        </tr>  
        <%  
            List userList = (List)request.getAttribute("users");  
            if (userList == null || userList.size() == 0) {  
        %>  
            <tr>  
                <td colspan="3">没有符合条件的数据</td>  
            </tr>  
        <%  
            }else {  
                for (Iterator iter=userList.iterator(); iter.hasNext();) {  
                	Users user = (Users)iter.next();  
        %>  
                    <tr>  
                        <td><%=user.getUname() %></td>  
                        <td><%=user.getAge() %></td>  
                    </tr>  
        <%  
                }  
            }  
        %>  
    </table>  
    <h3>采用forEach标签</h3>  
    <table border="1">  
        <tr>  
            <td>用户名称</td>  
            <td>年龄</td>  
            <td>所属组</td>  
        </tr>  
        <c:choose>  
            <c:when test="${empty users}">  
                <tr>  
                    <td colspan="3">没有符合条件的数据</td>  
                </tr>  
            </c:when>  
            <c:otherwise>  
                <c:forEach items="${users}" var="user">  
                    <tr>  
                        <td>${user.username }</td>  
                        <td>${user.age }</td>  
                        <td>${user.group.name }</td>  
                    </tr>  
                </c:forEach>  
            </c:otherwise>  
        </c:choose>  
    </table>    
    <h3>采用forEach标签,varstatus</h3><br>  
    <table border="1">  
        <tr>  
            <td>用户名称</td>  
            <td>年龄</td>  
            <td>所属组</td>  
        </tr>  
        <c:choose>  
            <c:when test="${empty users}">  
                <tr>  
                    <td colspan="3">没有符合条件的数据</td>  
                </tr>  
            </c:when>  
            <c:otherwise>  
                <c:forEach items="${users}" var="user" varStatus="vs">  
                    <c:choose>  
                        <c:when test="${vs.count mod 2 == 0}">  
                            <tr bgcolor="red">  
                        </c:when>  
                        <c:otherwise>  
                            <tr>  
                        </c:otherwise>  
                    </c:choose>  
                                <td>${user.username }</td>  
                                <td>${user.age }</td>  
                                <td>${user.group.name }</td>  
                            </tr>  
                </c:forEach>  
            </c:otherwise>  
        </c:choose>  
    </table>  
    <h3>采用forEach标签,begin,end</h3>  
    <table border="1">  
        <tr>  
            <td>用户名称</td>  
            <td>年龄</td>  
            <td>所属组</td>  
        </tr>  
        <c:choose>  
            <c:when test="${empty users}">  
                <tr>  
                    <td colspan="3">没有符合条件的数据</td>  
                </tr>  
            </c:when>  
            <c:otherwise>  
                <c:forEach items="${users}" var="user" begin="2" end="8" step="2">  
                    <tr>  
                        <td>${user.username }</td>  
                        <td>${user.age }</td>  
                        <td>${user.group.name }</td>  
                    </tr>  
                </c:forEach>  
            </c:otherwise>  
        </c:choose>  
    </table>    
    <li>演示循环控制标签：forEach，输出map</li><br>  
    <c:forEach items="${map}" var="entry">  
        ${entry.key },${entry.value }<br>  
    </c:forEach>    
    <p>  
    <li>演示循环控制标签：forTokens</li><br>  
    <c:forTokens items="${strTokens}" delims="#" var="v">  
        ${v }<br>  
    </c:forTokens>  
    <p>  
    <li>c:catch标签</li><br>  
    <%  
        try {  
            Integer.parseInt("asdfsdf");  
        }catch(NumberFormatException e) {  
            e.printStackTrace();  
            out.println(e.getMessage());  
        }  
    %>  
    <p>  
    <c:catch var="msg">  
        <%  
            Integer.parseInt("asdfsdf");  
        %>  
    </c:catch>  
    ${msg }<br>  
    <p>  
    <li>c:import标签</li><br>  
    <c:import url="http://localhost:8080/drp4.5/test_upload.html"/>  
    <p>  
    <li>c:url,c:param</li><br>  
    <c:url value="http://localhost:8080/drp4.5/sysmgr/validate.jsp" var="u">  
        <c:param name="userId" value="zhangsan"/>  
        <c:param name="age" value="20"/>  
    </c:url>  
    ${u }<br>  
    <li>c:redirect</li><br>  
    <c:redirect url="/login.jsp" context="/drp4.5"/>
  </body>
</html>
