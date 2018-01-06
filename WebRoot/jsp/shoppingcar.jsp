<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"
	import="com.mod.bean.ShoppingCar"
	import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="ShowError.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>购物车</title>

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
<link rel="stylesheet" href="../css/lanrenzhijia.css">
<script src="../js/addressOp.js"></script>
<script src="../js/shoppingcar.js"></script>	
<script type="text/javascript" src="js/role.js"></script>
<script type="text/javascript">
$(document).ready(function(){
$("input").bind('keyup',function () {
	$(this).val($(this).val().replace(/\s/g,''));
});	
})
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
<form action="../Order" id="frm">
<input type="hidden" name="flag" value="multi"> 
<input type="hidden" name="buyCids"> 
<input type="hidden" name="adresId"> 
</form>
	<% 
 //@SuppressWarnings("unchecked")
 //LinkedList<ShoppingCar> list = (LinkedList<ShoppingCar>)request.getSession().getAttribute("carlist");
	@SuppressWarnings("unchecked")
	LinkedList<HashMap<String,Object>> view = (LinkedList<HashMap<String,Object>>)request.getSession().getAttribute("view");
	String adresJson = (String)request.getSession().getAttribute("adresJson");
	
	ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("unchecked")
	LinkedList<HashMap<String,Object>> adlist = mapper.readValue(adresJson, LinkedList.class);
	 %>
 <center>
 <div style="width:1010px;height:195px;border: 1px solid #CCCCCC;text-align:center;'" >
   <% 
	     int idindex = 1;
   if(adlist != null){
	   
   
  for(HashMap<String,Object> item : adlist){
	  %><div name= "<%=item.get("adressId")%>" style="float:left;height:170px;width:31%; margin: 10px;border: 1px solid #CCCCCC">
	     <input type="radio" name="radio" id="radio<%=item.get("adressId")%>" value="<%=item.get("address")%>"><%=idindex%><br/>
	     <input id="editb" type="button" name="<%=item.get("adressId")%>" value="修改"  />
	     <input id="deleb" type="button" value="删除" onclick="deleA(<%=item.get("adressId")%>)" /><br/>
	     <% 
	     item.remove("uid");
	     if(!item.isEmpty()){
	    	 
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
    	
    	<span name = <%=s %>>
    	<%=s.equals("adressId")?"":item.get(s)%>
    	</span><br/>
    	
  		 <% }}}%>
		</div>
		
	  <% 
	  idindex++;
	  if(idindex == 4){
		  break;
		}  
  }}%>
  <%-- 修改地址弹窗 ../Spcar?flag=view--%>
	<div class="theme-popover" name="edit" style="text-align: center; height:330px;">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>修改地址</h3>
     </div>
   
     <div class="theme-popbod dform" name="eform">
           <form class="theme-signin" name="loginform" id="efm" action="../ast" method="post">
                	
                	<input type="hidden" name="aid" value=""/>
                <ol>
				                     
                  	<li><strong>地区：</strong><select  id="pro" name="province"  >
						   <option >--选择省份--</option>
						   </select>
						   <select  id="city" name="city"    >
						   <option>--选择城市--</option>
						   </select>
						   	</li>
   					<li><strong>设为默认：</strong>是<input type="radio" name="default" value="true">
   					否<input type="radio" name="default" value="false"  checked="checked">
   					</li>
                  	
   					<li><strong>姓名：</strong><input class="ipt" required  type="text" name="aname"  size="20"></li>
                  	<li><strong>地址：</strong><input class="ipt" required   type="text" name="address"  size="20"></li>
                  	<li><strong>手机：</strong><input  class="ipt" required type="text" name="aphone" onkeyup="this.value=this.value.replace(/\D/g,'')"  size="20"></li>
                  	 <li ><input class="btn btn-primary" style="left:60px" type="submit" id="editbt" value="确认"/></li>
 
                </ol>
           </form>
     </div>                    
</div>	  
<%-- 修改地址弹窗../Spcar?flag=view--%>

    <% 
	  
	  if(idindex != 4){
		  %>
		  <%-- 添加新收货地址后刷新 ../Spcar?flag=view--%>
	  <div style="float:left;width:31%;height:170px; margin: 10px;border:  1px solid #CCCCCC;">
	      			<br/>
    			<span style="width:100px;height:auto;bottom: 10px;margin: 10px;border:  1px solid #CCCCCC;">
    			<a name="address" href="javascript:void(0);">新增收货地址</a>
    			</span>
    			<br/>
    			<br/>
	  </div>		  

 </div>
<%-- 地址弹窗 ../Spcar?flag=view--%>
	<div class="theme-popover" name="address" style="text-align: center; height:330px;">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>新增地址</h3>
     </div>
   
     <div class="theme-popbod dform" name="form">
           <form class="theme-signin" name="loginform" id="pfm" action="" method="post">
                	<input type="hidden" name="flag" value="changeAdd"/>
                <ol>
				                     
                  	<li><strong>地区：</strong><select  id="roles" name="province"  required>
						   <option >--选择省份--</option>
						   </select>
						   <select  id="user" name="city"    required>
						   <option>--选择城市--</option>
						   </select>
						   	</li>
   					<li><strong>设为默认：</strong>是<input type="radio" name="default" value="true">
   					否<input type="radio" name="default" value="false" checked="checked">
   					</li>
                  	
   					<li><strong>姓名：</strong><input required class="ipt" type="text" name="aname"  size="20"></li>
                  	<li><strong>地址：</strong><input required class="ipt" type="text" name="address"  size="20"></li>
                  	<li><strong>手机：</strong><input required class="ipt" type="text" name="aphone" onkeyup="this.value=this.value.replace(/\D/g,'')" required  size="20"></li>
                  	 <li ><input class="btn btn-primary" style="left:60px" type="submit" id="addb" value="确认"/></li>
                  	
                </ol>
           </form>
     </div>                    
</div>	  
<%-- 地址弹窗../Spcar?flag=view--%>
<%  
		}  
  %>
<!-- ------------------------------------------------------------ -->


<div class="cartDiv">
	<div class="cartTitle pull-right">
		
	</div>
	
	
	<div class="cartProductList">
		<table class="cartProductTable">
			<thead>
				<tr>
					<th class="selectAndImage">
										
					
					
					</th>
					<th>商品信息</th>
					<th>单价</th>
					<th>数量</th>
					<th width="120px">金额</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody  id="table">
				  <%int index = 1; 
mapper.setSerializationInclusion(Include.NON_NULL);  
mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
if(!view.isEmpty()){
	

for(HashMap<String,Object> item : view){
	  
	  %>

					<tr oiid="<%=item.get("cid") %>" class="cartProductItemTR">
						<td>
							<input type="checkbox" name="<%=item.get("cid")%>" value="<%=item.get("cid")%>" style="display: none;" >
							<img selectit="false" oiid="<%=item.get("cid") %>" class="cartProductItemIfSelected" src="./img/cartNotSelected.png">
							<a style="display:none" href="#"><img src="./img/cartSelected.png"></a>
							<img class="cartProductImg" src="<%=item.get("imgPath")%>" ale="商品图片">
						</td>
						<td>
						
							<span class="cartProductItemName"><a href="../ProInfo?gid=<%=item.get("gid") %>" class="cartProductLink"><%=item.get("gname") %></a></span>
						</td>
						<td>
							
							<span class="cartProductItemPromotionPrice"><%=item.get("price") %></span>
							
						</td>
						<td>
						
							<div class="cartProductChangeNumberDiv">
								<span class="hidden orderItemStock " pid="<%=item.get("gid")%>"><%=item.get("stock")%></span>
								<span class="hidden orderItemPromotePrice " pid="<%=item.get("gid")%>"><%=item.get("price")%></span>
								<a 	class="numberMinus"		 pid="<%=item.get("gid")%>" id="jian"  href="javascript:void(0);">-</a>
								<input pid="<%=item.get("gid")%>" oiid="<%=item.get("cid")%>" class="orderItemNumberSetting" autocomplete="off" value="<%=item.get("gnum")%>">
								<a class="numberPlus"		stock="<%=item.get("stock")%>" id="jia" pid="<%=item.get("gid")%>" href="javascript:void(0);">+</a>
							</div>					
						
						 </td>
						<td>
							<span class="cartProductItemSmallSumPrice" oiid="<%=item.get("cid")%>" pid="<%=item.get("gid")%>">
							￥<%=(double)item.get("price")*(int)item.get("gnum")%>
							</span>
						
						</td>
						<td>
							<a class="deleteOrderItem" oiid="<%=item.get("cid")%>" href="javascript:void(0);"onClick='javascript:if(deleGoods(<%=item.get("cid")%>)){deleterow2(this.parentElement.parentElement,<%=item.get("cid")%>)}'>删除</a>
						</td>
					</tr>
				
						  <%
	index++;  
  } }%>
								
			</tbody>
		
		</table>
	</div>
	
	<div class="cartFoot">
		<img selectit="false" class="selectAllItem" src="./img/cartNotSelected.png">
		<span>全选</span>
	
		<div class="pull-right">
			<span>已选商品 <span class="cartSumNumber">0</span> 件</span>
			
			<span>合计 : </span> 
			<span class="cartSumPrice">￥0.00</span>
			<button class="createOrderButton" disabled="disabled">结  算</button>
		</div>
		
	</div>
	
</div>

<!-- ------------------------------------------------------------ -->
 </center>
  </body>
</html>