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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>全部订单页面</title>
<link rel="stylesheet" href="../css/lanrenzhijia.css" media="all">
<link rel="stylesheet" href="css/style_order.css" /> 
<script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	<!-- 弹出登录框-->
	$('a[name = pwd]').click(function(){
		//$('.theme-popover-mask').fadeIn(100);
		$('div[class=theme-popover][name=pwd]').slideDown(200);
		$("div[name='form']").show();
	});
	$('a[name = avatar]').click(function(){
		//$('.theme-popover-mask').fadeIn(100);
		$('div[class=theme-popover][name=avatar]').slideDown(200);
		$("div[name='aform']").show();
	});
$('.theme-poptit .close').click(function(){
	$('.theme-popover-mask').fadeOut(100);
	$('.theme-popover').slideUp(200);
})
	
	$('#cpwd').click(function(){
		var time = new Date();
	var	c = $("#ccpwd").val();
		if(c===""){
			alert("输入验证码！");
			return;
		}
		c = $("input[name=opwd]").val();
		if(c===""){
			alert("输入旧密码！");
			return;
		}
		c = $("input[name=npwd]").val();
		if(c===""){
			alert("输入新密码！");
			return;
		}
		var c2 = $("input[name=npwd2]").val();
		if(c2===""){
			alert("确认新密码！");
			return;
		}
		if(c !== c2){
			alert("新密码不一致！");
			return;
		}
		var ajax_option = {
			//target: '#output',          //把服务器返回的内容放入id为output的元素中        
			//beforeSubmit://提交前的回调函数    
			url : '../UMS', //默认是form的action， 如果申明，则会覆盖    
			type : 'post', //默认是form的method（get or post），如果申明，则会覆盖    
			dataType : 'json', //html(默认), xml, script, json...接受服务端返回的类型    
			date:{"d":time},
			clearForm : true, //成功提交后，清除所有表单元素的值    
			resetForm : true, //成功提交后，重置所有表单元素的值    
			timeout : 3000, //限制请求的时间，当请求大于3秒后，跳出请求   
			success : function(data) {
				if(data.message.match('.+?失败.+')){
					if(data.message.match('.+?验证码.+')){
						alert("验证码错误");
					}else if(data.message.match('.+?旧.+')){
						alert("旧密码错误");
					}else{
						alert("修改失败，请重试！");
					}
					
				}else{
					alert("修改成功！");
				//$('.theme-popover-mask').fadeOut(100);
				$('.theme-popover').slideUp(200);
				
				}
			}, //提交后的回调函数
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("修改异常，请重试！");
			   },
		};
		$("#pfm").ajaxSubmit(ajax_option);
	});
$("#check").click(function(){
	var time = new Date();
	$(this).attr("src","<%=request.getContextPath()%>/ck?d="+time);	
});

	$("input[type=button]").click(function(){
		var str = $(this).val();
		if(str !== "付款"){
			return;
		}
		var oid = $(this).attr("id");
		$("input[name=oid]").val(oid);
		$("#frm").submit();
	});
	$("input[type=button][value=删除]").hide();
});
function changeBox(){
	//$("input[type=checkbox][id!=check_all_box]").removeAttr("checked");
	$("input[type=checkbox][id!=check_all_box]").prop("checked",$('input[id=check_all_box]').is(':checked'));
};
</script>
  </head>
  <body>
  <form action="../Order" id="frm">
<input type="hidden" name="flag" value="payOrder"> 
<input type="hidden" name="oid"> 
</form>
<%
String json = (String)request.getSession().getAttribute("ogJson"); 
%>

<%
ObjectMapper mapper = new ObjectMapper();
@SuppressWarnings("unchecked")
LinkedList<LinkedHashMap<String, Object>> list = mapper.readValue(json, LinkedList.class);
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
	<div class="container">
			<div class="top">
				<div class="top_left">
					<ul>
						<li><a  class="img01" href="javascript:void(0);"><c:out value="${sessionScope.name}"></c:out></a><span><img src="img/44.png"/></span></li>
						<li><a href="javascript:void(0);">消息</a><span><img src="img/44.png"/></span></li>
						<li><a hrref="javascript:void(0);">手机逛淘宝</a></li>
					</ul>
				</div>
				<div class="top_right">
					<ul>
						<li><a href="javascript:void(0);">淘宝网首页</a></li>
						<li><a href="javascript:void(0);">我的淘宝</a><span><img src="img/44.png"/></span></li>
						<li><a class="img02"  href="../Spcar?flag=view">购物车</a><span>(${sessionScope.carnum})</span><span><img src="img/44.png"/></span></li>
						<li><a class="img03" href="javascript:void(0);">地址管理</a><span>|</span></li>
						<li><a name="pwd" href="javascript:void(0);">修改密码</a><span>|</span></li>
						<li><a name="avatar" href="javascript:void(0);">问题反馈</a><span></span>|</li>
						<li><a href="javascript:void(0);">联系客服</a><span><img src="img/44.png"/></span></li>
						<li><a class="img04" href="javascript:void(0);">网站导航</a><span><img src="img/44.png"/></span></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="nav">
			<div class="bottom">
				<div class="">
					<div class="bottom_left">
						<ul>
							<li><a href="../index.jsp"><img class="logo" src="img/48.png" /></a></li>
							<li><a href="javascript:void(0);">首页</a></li>
							<li><a href="javascript:void(0);">账户设置</a><span><img src="img/44.png"/></span></li>
							<li><a href="javascript:void(0);">消息</a></li>
						</ul>
					</div>
					<div class="bottom_right">
					<form action="../search" method="post">	
						<input class="search_left" type="text" required placeholder="请输入想要的东西吧" value="" name="word"/>
						<input class="search_right" type="submit" value="搜索" />
					</form>
					</div>
				</div>
			</div>
		</div>
		
		
			<div class="content">
			<div class="content_left">
				<ul>
					<span>全部功能</span>
					<li><a href="../Spcar?flag=view">我的购物车</a></li>
					<li><a class="nav01" href="javascript:void(0);">已买到的宝贝</a></li>
					<li><a href="javascript:void(0);">购买过的店铺</a></li>
					<li><a class="nav01" href="javascript:void(0);">我的发票</a></li>
					<li><a href="javascript:void(0);">我的收藏</a></li>
					<li><a href="javascript:void(0);">我的积分</a></li>
					<li><a href="javascript:void(0);">我的优惠信息</a></li>
					<li><a href="javascript:void(0);">我的评价管理</a></li>
					<li><a class="nav01" href="javascript:void(0);">退款维权</a></li>
					<li><a href="javascript:void(0);">我的足迹</a></li>
					<li><a href="javascript:void(0);">流量钱包</a></li>
					<span>最经访问</span>
					<li><a href="javascript:void(0);">淘女郎</a></li>
					<li><a href="javascript:void(0);">聚划算</a></li>
					<li><a href="javascript:void(0);">淘宝抢拍</a></li>
				</ul>
			</div>
			
			<div class="content_right">
				<div class="content_nav">
					<ul>
						<li><a href="javascript:void(0);">所有订单</a></li>
						<li><a href="javascript:void(0);">待付款</a></li>
						<li><a href="javascript:void(0);">待发货</a></li>
						<li><a href="javascript:void(0);">待收货</a></li>
						<li><a href="javascript:void(0);">待评价</a></li>
						<li class="xiaozi"><a href="javascript:void(0);">分阶段</a></li>
						<span><a href="javascript:void(0);">订单回收站</a></span>
					</ul>
				</div>
				<div class="search_order">
					<input type="text" class="searcher01" /><input class="searcher02" type="button" value="订单搜索">
					<span>更多筛选条件</span><span class="searcher03"><img src="img/44.png"/></span>
				</div>
			</div>
			
			  <div class="content_right1">
            <div class="field">
            	
            		<ul>
            			<li><a class="field01" href="javascript:void(0);">宝贝</a></li>
            			<li><a class="field02" href="javascript:void(0);">单价</a></li>
            			<li><a class="field02" href="javascript:void(0);">数量</a></li>
            			<li><a class="field03" href="javascript:void(0);">商品操作</a></li>
            			<li><a class="field04" href="javascript:void(0);">实付款</a></li>
            			<li><a class="field05" href="javascript:void(0);">交易状态</a></li>
            			<li><a class="field03" href="javascript:void(0);">交易操作</a></li>
            		</ul>
            	
            </div>
            
            <div class="field_bottom">
        		<div class="field_bottom_left">
        			<input type="checkbox" id="check_all_box" onclick="$(changeBox)"/><span>全选</span>
        			<span class="sp"><a href="javascript:void(0);">批量删除</a></span>
        		</div>
        		<div class="field_bottom_right">
        			<span>上一页</span><span>下一页</span>
            	</div>
            </div>
               <!------- 最外层for ---------------->
<% 
String oid="";
String state="";
for(LinkedHashMap<String, Object> temp : list){
	oid = (String)temp.get("oid");
	state =(String)temp.get("state");
%>
            <!-- 订单循环 -->
              <div class="order" oid="<%=oid%>">
            	<table cellspacing="0" class="table1" oid="<%=oid%>">
            		<tr class="title">
            			<td colspan="4">
            				<ul class="menu">
            					<li><input type="checkbox" value="<%=oid%>" /></li>
            					<li><%=sdf2.format(sdf.parse(oid))%></li>
            					<li><span>订单号:</span><span><%=oid%></span></li>
            					<li><span class="tubiao1">旗舰店</span></li>
            					<li><img src="img/53.png"/></li>
            				</ul>
            			</td>
            		</tr>
            		<tr>
            			<td>
            					<%
		@SuppressWarnings("unchecked")	
		ArrayList <HashMap<String,Object>> goods =(ArrayList <HashMap<String,Object>>)temp.get("goods");
		String descTemp="";
       for(HashMap<String,Object> m:goods){
    	   descTemp = (String)m.get("descption");
    	   
			%>
            			<%------------------------------table 商品循环----------------------------------- --%>
            				<table class="order_xi" gid="<%=m.get("gid")%>" style="border: solid 1px #CCCCCC;">
            					<tr>
            						<td><img src="<%=m.get("imgPath")%>" style="width: 80px;height: 80px  "/></td>
            						<td  class="order_menu">
            							<p><%=m.get("gname")%></p>
            							<p><%=descTemp.replaceAll("\"|\\{|\\}|\\[|\\]", "")%></p>
            							<p><img src="img/55.png"/></p>
            						</td>
            						<td><p>￥<%=m.get("price")%></p></td><%//subtotal%>
            						<td><%=m.get("gnum")%></td>
            						<td>
            							<ul>
            								<li>退款/退货</li>
            								<li>投诉商家</li>
            								<li>虚抬专柜价</li>
            								
            							</ul>
            						</td>
            					</tr>
            				</table>
            						<%
									}
									%>
            			<%------------------------------table 商品循环----------------------------------- --%>
            			</td>
            			
            			<td rowspan="2">
            				<ul>
            					<li style="color:red;">￥<%=temp.get("sum") %></li>
            					<li>(含运费:￥0.00)</li>
            					<li><span>手机订单</span></li>
            				</ul>
            			</td>
            			<td  rowspan="2">
            				<ul>
            					<li style="color:<%=state.equals("已付款")?"blue":"red"%>;"><%=state %></li>
            				</ul>
            			</td>
            			<td  rowspan="2">
            				<ul>
            					
            					<li><input type="button" id="<%=oid%>" onclick="" value="<%=state.equals("已付款")?"删除":"付款"%>"></li>
            				</ul>
            			</td>
            		</tr>
            		<tr>
            			<td>
            				<span>保险服务</span><span>￥0.00</span><span>1</span>
            			</td>
            		</tr>
            	</table>
            </div>
              <!------------------------------ 订单循环 ------------------------------------------>
            	   <!-- 最外层for -->
<%}%>
            
                        <div class="page">
            	<div class="xuanze">
            		<input type="checkbox" /><span>全选</span><button>批量操作</button>
            	</div>
            	<div class="paging">
            		<button>&lt;</button><button>1</button><button>2</button><button>3</button><span>...</span><button>12</button><button>&gt;</button>
            		<span>跳至</span><input type="text" /><span>页</span><button>跳转</button>
            	</div>
            </div>
            </div>
          </div>



<%---------------修改密码 --------------------------%>
<div class="theme-popover" name="pwd" style="text-align: center; height:400px;">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>修改密码</h3>
     </div>
    <h4>点击图片生成新的验证码！</h4>
     <div class="theme-popbod dform" name="form">
           <form class="theme-signin" name="loginform" id="pfm" action="" method="post">
                	<input type="hidden" name="flag" value="changePwd"/>
                <ol>
					<li><strong>旧密码：</strong><input class="ipt" type="password" name="opwd" value="" size="20"/></li>                     
                     <li><strong>新密码：</strong><input class="ipt" type="password" name="npwd" value="" size="20" /></li>
                     <li><strong>确认密码：</strong><input class="ipt" type="password" name="npwd2" value="" size="20" /></li>
                     <li><strong>验证码：</strong><input class="ipt" type="text" name="checkcode" id="ccpwd" value="" size="20" /></li>
                     <li><img id="check" alt="验证码" src="<%=request.getContextPath()%>/ck" onclick="docheck()" style="width: 200px;height: 60px;border-color: blue"/>
                     <li ><input class="btn btn-primary" style="left:60px" type="button" id="cpwd" value="确认修改"/></li>
                </ol>
           </form>
     </div>
      
      
                           
</div>
<%---------------修改密码end --------------------------%>


  </body>
</html>
