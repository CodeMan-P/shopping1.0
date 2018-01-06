<%@ page errorPage="ShowError.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"
	import="com.mod.bean.ShoppingCar"
	import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <base href="<%=basePath%>">
    
    <title>结算</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
	<link href="./img/bootstrap.min.css" rel="stylesheet">
	<script src="./img/bootstrap.min.js.下载"></script>
	<link href="./img/style.css" rel="stylesheet">
<link rel="stylesheet" href="../css/lanrenzhijia.css" media="all">
<script type="text/javascript">
    $(document).ready(function(){
    	$("#bt1").click(function(){
    		alert("123");
    		
    	});
    	
    	$('.theme-login').click(function(){
    		$('.theme-popover-mask').fadeIn(100);
    		$('.theme-popover').slideDown(200);
    	})
    	$('.theme-poptit .close').click(function(){
    		$('.theme-popover-mask').fadeOut(100);
    		$('.theme-popover').slideUp(200);
    	})
    });
    
    function pay(){
    	$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
        $.ajax({
            type:'post',  
            url:'../Order',  
            data:{
            	"flag":$("#flag").val(),
            	"payPwd":$("#pwd").val(),
            	"oid":$("#oid").val(),
            	"addr":$("input[type=radio]:checked").val()
            	},
            dataType:'json',
            async: false,
            success:function(data){  
				if(data.message.match('.+?成功.+')){
                	alert("支付成功！"); 
                	window.location.href="../index.jsp";
				}else{
						alert("支付失败");
					}
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert(XMLHttpRequest.status);
				   alert(XMLHttpRequest.readyState);
				   alert(textStatus);
			   }
        });  
    return false;  
    };
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
		<input type="text" placeholder="请输入想要的东西吧" required value="" name="word">
		<button class="searchButton" type="submit">搜索</button>
		
	</div>
	</form>
	<div style="clear:both"></div>
</div>


  
  <% 
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String hmJson = (String)request.getSession().getAttribute("hm");
  	ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("unchecked")
	HashMap<String,Object> hm = (HashMap<String,Object>)mapper.readValue(hmJson, HashMap.class);
  	
  %>
 
  <input type="hidden" id="oid" value="<%=(String)hm.get("oid")%>">
  <%hm.remove("uid");%>
   <%hm.remove("state");%>

 <%String address = (String)hm.remove("address");
 if(address.equalsIgnoreCase("#")){
	 
	 //单个支付标志=============================
	 	String adresJson = (String)request.getSession().getAttribute("adresJson");
	 	@SuppressWarnings("unchecked")
	 	LinkedList<HashMap<String,Object>> adlist = mapper.readValue(adresJson, LinkedList.class);
	 	%>
<input type="hidden" id="flag" value="spay"/>
 <div 	class="cartDiv"	style="text-align: center;">
   <% 
	     int idindex = 1;
  for(HashMap<String,Object> item : adlist){
	  %>
	  <div  style="float:left;width:300px;height:170px; margin: 10px;bottom: 10px;border: 1px solid #CCCCCC">
	     <input type="radio" name="radio" id="radio<%=item.get("adressId")%>" value="<%=item.get("address")%>"><%=idindex++%><br/>
	     <% 
	     item.remove("uid");
	     for (String s:item.keySet()){
    	if(s.equals("default")){
    		if((Boolean)item.get(s)){
    			%>
    			<br/>
    			<span style="width:100px;height:auto;bottom: 1px;margin: 1px;border: solid 1px #0033CC;">
    			默认收货地址
    			</span>
    			<br/>
    			<br/>
    			<script type="text/javascript">
    			$("#radio<%=item.get("adressId")%>").prop("checked",true);
    			</script>
    			<%
    		}
    	}else{
    	
    	%>
    	
    	<%=s.equals("adressId")?"":item.get(s)%><br/>
    	
  		 <% }}%>
		</div>
	  <% }%>
	
 </div>
 <%}else{%>
 <!-- 通过购物车支付标志！！！！！！！！！！！！！！！！！！！！！ -->
 <%}%>
 
 <hr/>           				
<div class="cartDiv">
	<div class="cartTitle pull-right">
	<%if(!address.equalsIgnoreCase("#")){ %>
	
 <input type="hidden" id="flag" value="mpay"/>
送货地址: <%=address%>
	
	<% }%>
	</div>
	<div class="cartProductList">
		<table class="cartProductTable">
			<thead>
					<tr>
							<td colspan="5" align="center">
            					<h3>订单号:<%=(String)hm.get("oid")%></h3>
            				</td>
					</tr>
				<tr>
					<th class="selectAndImage">
										
					
					
					</th>
					<th>商品信息</th>
					<th>单价</th>
					<th>数量</th>
					<th width="120px">金额</th>
				
				</tr>
			</thead>
			<tbody  id="table">
				  <%int index = 1; 
mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
hm.remove("oid");
double sum = (double)hm.remove("sum");
for(String temp : hm.keySet()){
	
	@SuppressWarnings("unchecked")
	HashMap<String,Object> item =(HashMap<String,Object>)hm.get(temp);  
	  %>
					<tr class="cartProductItemTR">
						<td>
							<img class="cartProductImg" src="<%=(String)item.get("imgPath")%>">
						</td>
						<td>
							<div class="cartProductLinkOutDiv">
								<a href="#" class="cartProductLink"><%=(String)item.get("gname")%></a>
								
							</div>
							
						</td>
						<td>
							
							<span class="cartProductItemPromotionPrice">￥<%=item.get("price")%></span>
							
						</td>
						<td>
							<div class="cartProductChangeNumberDiv">
								<span ><%=item.get("gnum")%></span>
							</div>					
						 </td>
						<td>
							<span class="cartProductItemSmallSumPrice">
							￥<%=(double)item.get("price")*(int)item.get("gnum")%>
							</span>
						
						</td>
						
					</tr>
				
					  
	  <%
	index++;  
  } %>
								
			</tbody>
		
		</table>
	</div>
	
	<div class="cartFoot">
		
		
		<div class="pull-right">
			<span>合计 : </span> 
			<span class="cartSumPrice">￥<%=sum%></span>
			<a class="btn btn-primary btn-large theme-login" href="javascript:;">购买</a>
			
		</div>
		
	</div>
	
</div>
 

    

<div class="theme-buy" style="position: relative;top:-130px;">

</div>
<div class="theme-popover">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>支付</h3>
     </div>
     <div class="theme-popbod dform">
           <form class="theme-signin" name="loginform" action="" method="post">
                <ol>
                     <li><h4>交钱！</h4></li>
                     
                     <!--<li><strong>用户名：</strong><input class="ipt" type="text" name="log" value="lanrenzhijia" size="20" /></li>  -->
                     <li><strong>支付密码：</strong><input class="ipt" type="password" id = "pwd" name="payPwd" value="" size="20" /></li>
                     <li><input class="btn btn-primary" type="button" onclick="pay()" value="确认 " /></li>
                </ol>
           </form>
     </div>
</div>
    
    </center>
  </body>
</html>
