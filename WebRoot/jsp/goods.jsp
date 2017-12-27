<%@ page errorPage="ShowError.jsp" %>
<%@ page language="java" import="java.util.*"  import="com.mod.bean.Goods" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" import="java.io.File"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>goods</title>
    
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
		<link href="./img/bootstrap.min.css" rel="stylesheet"/>
		<script src="./img/bootstrap.min.js.下载"></script>
		<link href="./img/style.css" rel="stylesheet"/>

		<style type="text/css">
		*{margin:0;padding:0}
		li{list-style:none;}
		</style>
		<script type="text/javascript">
		function showTime(){
			now=new Date;
			document.write(now.getYear()+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日"+now.getHours()+"点"+now.getMinutes()+"分");}
			function number(){
			var b=document.getElementById("gnum").value;
			
			if(b == null||b=== ""){
				document.getElementById("gnum").value="1";
				return;
			}
			var a=parseInt(b);
			a=a+1;
			b=a.toString();
			document.getElementById("gnum").value=b;
			}
			function number1(){
			var b=document.getElementById("gnum").value;
			if(b == null||b=== ""){
				document.getElementById("gnum").value="1";
				return;
			}
			var a=parseInt(b);
			if(a==0){
				return;
			}
			a=a-1;
			b=a.toString();
			document.getElementById("gnum").value=b;
			}
			$(document).ready(function(){
				
					//显示图片
				$(".f_l_ow").mouseenter(function() {
					$(".f_l_ow:not(this)").css({
						border: "1px solid #ccc"
					});
					$(this).css({
						border: "1px solid #000"
					});
					var $src = $(this).children().attr("src");
					
					// console.log($src)
					$(".f_l_img").attr("src", $src);
					$(".f_l_img").attr("src", $src);
				});
				//放大境——移入鼠标显示图片
				$(".f_middle").hover(function(e) {
					$(".f_r_imgNone").show();
					$(".f_m_divNone").show();
				},
				function(e) {

					$(".f_r_imgNone").hide();
					$(".f_m_divNone").hide();
				});
				$(".f_middle").mousemove(function(e) {
				// e.stopPropagation();
				var $x = e.pageX - $(".f_middle").offset().left - ($(".f_m_divNone").width() / 2);
				var $y = e.pageY - $(".f_middle").offset().top - ($(".f_m_divNone").height() / 2);
				var $maxWidth = $(".f_middle").width() - $(".f_m_divNone").width();
				var $maxHeight = $(".f_middle").height() - $(".f_m_divNone").height();
				var $nowX = Math.max(Math.min($x, $maxWidth), 0);
				var $nowY = Math.max(Math.min($y, $maxHeight), 0);
				//console.log($nowX * 2,$nowY * 2)
				$(".f_m_divNone").css({
					left: $nowX+$(".f_middle").offset().left,
					top: $nowY+$(".f_middle").offset().top,
				});
				$(".f_r_none_img").css({
				left: -$nowX * 2,
				top: -$nowY * 2
				});
				
				});

				});

		</script>
<script type="text/javascript">
    $(document).ready(function(){
		
    	
    	
    	
    	
    	<!--================================================== -->
     $("#num").on("keyup",function(){
    		if($(this).val() == null||$(this).val() <=0){
    			$("a[name='buy']").attr("href","#");
    		}else{
    		src="buy?"+$(this).val()+"&&gid="+$("#gid").val();
    		$("a[name='buy']").attr("href",src);
    		}
    		});	
    });  

  	
    function addGoods(){
    	if($("#empId").val()==1){
    		alert("未登录！！！");
    		return false;
    	}
    	if($("#gnum").val() == null||$("#gnum").val() <=0){
    		alert("输入购物数量？>0！");
    		return false;
    	}
    	var maxStock = $("#gStock").val();
    	if($("#gnum").val() >maxStock){
    		alert("超出库存范围！！！");
    		return false;
    	}
    	//参数应选择获取······
    	var desc = '[{"颜色" : "钻雕金","套餐类型" : "官方标配"}]';
    	var ajax_option={  
				   //target: '#output',          //把服务器返回的内容放入id为output的元素中        
				   //beforeSubmit://提交前的回调函数    
				   url: '../Spcar',                 //默认是form的action， 如果申明，则会覆盖    
				   type: 'post',               //默认是form的method（get or post），如果申明，则会覆盖    
				   dataType: 'json',           //html(默认), xml, script, json...接受服务端返回的类型    
				   data:{"flag":"add",
				   "desc":desc},
				  
				   clearForm: true,          //成功提交后，清除所有表单元素的值    
				   resetForm: true,          //成功提交后，重置所有表单元素的值    
				   timeout: 3000,               //限制请求的时间，当请求大于3秒后，跳出请求   
				   success: function(data){
					if(data.message.match('.+?成功.+')){
					alert("添加成功");
					$("#sp1").empty();
					$("#sp1").text(data.num);
//						location.href="../index.jsp";	
					}else{
						alert("添加失败");
					}
					
				   },      //提交后的回调函数
				   error:function(XMLHttpRequest, textStatus, errorThrown){
					   alert(XMLHttpRequest.status);
					   alert(XMLHttpRequest.readyState);
					   alert(textStatus);
				   },   
				   complete: function(XMLHttpRequest, textStatus) {
				   }
		};
		
		$("#fm").ajaxSubmit(ajax_option);  
    };

    function buyGoods(){
    	if($("#empId").val()==1){
    		alert("未登录！！！");
    		return false;
    	}
    	if($("#gnum").val() == null||$("#gnum").val() <=0){
    		alert("输入购物数量？>0！");
    		return false;
    	}
    	var maxStock = $("#gStock").val();
    	if($("#gnum").val() >maxStock){
    		alert("超出库存范围！！！");
    		return false;
    	}
    	//参数应选择获取······
    	var desc = '[{"颜色" : "钻雕金","套餐类型" : "官方标配"}]';
    	var ajax_option={  
				   url: '../Order',                 //默认是form的action， 如果申明，则会覆盖    
				   type: 'post',               //默认是form的method（get or post），如果申明，则会覆盖    
				   data:{"flag":"single",
				   "desc":desc},
				  	dataType:'text',
				   clearForm: true,          //成功提交后，清除所有表单元素的值    
				   resetForm: true,          //成功提交后，重置所有表单元素的值    
				   timeout: 3000,               //限制请求的时间，当请求大于3秒后，跳出请求   
				   success: function(data){
					   if(data.match('.+?失败.+')){
						   alert(data);
					   }else{
						   
					   window.location.href=data;
					   }
					
				   },      //提交后的回调函数
				   error:function(XMLHttpRequest, textStatus, errorThrown){
					   alert(XMLHttpRequest.status);
					   alert(XMLHttpRequest.readyState);
					   alert(textStatus);
				   },   
				   complete: function(XMLHttpRequest, textStatus) {
				   }
		};
		
		$("#fm").ajaxSubmit(ajax_option);  
    };
  </script>
  </head>
  
  <body>
     <script>
function formatMoney(num){
	num = num.toString().replace(/\$|\,/g,'');  
	if(isNaN(num))  
	    num = "0";  
	sign = (num == (num = Math.abs(num)));  
	num = Math.floor(num*100+0.50000000001);  
	cents = num%100;  
	num = Math.floor(num/100).toString();  
	if(cents<10)  
	cents = "0" + cents;  
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)  
	num = num.substring(0,num.length-(4*i+3))+','+  
	num.substring(num.length-(4*i+3));  
	return (((sign)?'':'-') + num + '.' + cents);  
}
function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}
</script>	
    <% Goods g = (Goods)request.getSession().getAttribute("goods");
    ObjectMapper mapper = new ObjectMapper();
    String temp = g.getDescption();
    temp = temp.replaceAll("\\]", "");
   	temp = temp.replaceAll("\\[", "");
   	@SuppressWarnings("unchecked")
    HashMap<String,String> h = mapper.readValue(temp, HashMap.class);
    %>
<nav class="top ">
	<div class="top_middle">
	
		<a href="../index.jsp">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
			
		</a>	
		<c:if test="${empty sessionScope.name}">
			<input type="hidden" id="empId" value="1">
			<a href="../index.jsp" style="float:right" >登录</a>
		</c:if>
		
		<c:if test="${not empty sessionScope.name}">
			<a href="../index.jsp"style="float:right">退出</a>	
		<a href="javascript:void(0);" style="float:right"><c:out value="${sessionScope.name}" ></c:out></a>
			
				<a href="../Spcar?flag=view" target="_blank" style="float:right;color:#F00">
			购物车(${sessionScope.carnum})
			</a>
		
		</c:if>
			
			
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
        <!--*********************************former**********************************************-->
     <div class="former">
			
			<div  class="f_left">
				<div class="f_l_show">
							 <div class="f_l_button1"></div>
                            <ul class="f_l_ow">
                                <li class="f_l_ow">
                                   <img src="<%=g.getImgpath()%>" class="f_l_img"/>
                                </li>
                                <li class="f_l_ow">
                                   <img src="<%=g.getImgpath()%>" class="f_l_img"/>
                                </li>
                                <li class="f_l_ow">
                                   <img src="<%=g.getImgpath()%>"class="f_l_img"/>
                                </li>
                                <li class="f_l_ow">
                                   <img src="<%=g.getImgpath()%>" class="f_l_img"/>
                                </li>
                            </ul>
                                <div class="f_l_button2"></div>
				</div>
				<div class="f_middle">
					<img class="f_m_img" src="<%=g.getImgpath()%>">
					<div class="f_m_divNone" style="display: none; left:215px; top: 187px;"></div>
				</div>
				
			</div>
			<div class="f_r_imgNone" style="display: none;">
                            <img class="f_r_none_img" src="<%=g.getImgpath()%>" style="left: -367px; top: -380px;">
                        </div>
			<div class="f_right">
						 <h1><%=g.getGname() %></h1>
                        <div class="ffr_price">
                            <span class="ffr_price_span2"><em>￥</em><span class="ffr_price_span3"><%=g.getPrice() %></span></span>
                            <span class="ffr_price_span1">想打折，没门</span>
                        </div>
                        <div class="ffr_freight">
                           	 运费：10米内包邮
                        </div>
                        <div class="ffr_freight">
                            <span >颜色:</span>
							
                            <input style="left:10px;width:60px;height:25px;" type="color"/><br/>
							<span style="font-size:16px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							没错，本店的产品只有你想不到，没有本店搞不定的--->就是这么神奇！</span>
							
                        </div>
                        <div class="ffr_freight">
                            <span >型号：</span>
							<select style="left:10px;width:100px;height:25px;" >
							<option value="1">请选择....</option>
							<option value="2">金手机</option>
							<option value="3">银手机</option>
							<option value="3">铁手机</option>
							<option value="4">纸手机</option>
							</select>
							
                        </div>
                        <div class="ffr_freight">
                            <span class="ffr_freight_span">数量：</span>
                            <form id="fm">
                            <input type="hidden" name="gid" value="<%=g.getGid()%>"/>
                            <input type="text" class="ffr_freight_input" value="1" name="gnum" id="gnum"/>
                            </form>
                            <div class="ffr_freight_div"> 
                                <a href="javascript:number();void(0);" class="ffr_freight_input1"></a>
                                <a href="javascript:number1();void(0);" class="ffr_freight_input2"></a>
                            </div>
                            <input type="hidden" id="gStock" value="<%=g.getStock()%>"/>
                            	（库存<%=g.getStock() %>）
                        </div>
                        <div class="ffr_freight_onclick">
                            <div class="ffr_freight_onclicks">
                                <a href="javascript:buyGoods();void(0);" class="ffr_freight_onclick_shop" style="color: black;">立即购买</a>
                                <a href="javascript:addGoods();void(0);" class="ffr_freight_onclick_shop" style="color: black;">加入购物车</a>
								
                            </div>
                           
                        </div>
                        
                    </div>
				</div>
     </div>
	<div class="downsold">
                
                    <div class="f_b_h2 f_b_h21">
                        <h2 id="a">商品详情</h2>
                    </div>
                     <%
     String fpath = "jsp/"+g.getFilepath();
     fpath = application.getRealPath(fpath);
     File file = new File(fpath);
     	if(file.listFiles()!=null){
     		
     	
		for(File f :file.listFiles()){
			String ipath = fpath+f.getName();
			int index =ipath.lastIndexOf("goods");
			ipath = ipath.substring(index);
			%>	
               <div class="f_b_xiangqing">
	<img alt="数据库炸了？？？" src="<%=ipath%>" class="f_b_x_img" />
                    </div>
     
			<%
			
		}}
     %>
     
                   
                   </div>
 
  </body>
</html>
