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

<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" href="css/lanrenzhijia.css" media="all">
<link rel="stylesheet" href="css/style.css" />
<style type="text/css">
    .blur {      
        -webkit-filter: blur(10px);  
           -moz-filter: blur(10px);  
            -ms-filter: blur(10px);      
                filter: blur(10px);      
    }
</style>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/Math.uuid.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		$("div[name='QRcode']").hide();	
		$('#QRshow').click(function(){
			
			var val =$('#QRshow').val();
			if(val==="二维码登录"){
				doQRcheck();
				$('#QRshow').val("账号密码登录");
			}else{
				$('#QRshow').val("二维码登录");
			}
		$("div[name='QRcode']").toggle(500);	
		$("div[name='form']").toggle(500);	
			})
		if($("#userInfo #spc").text()==="-"){
			$.ajax({
				async:false,
				data:{"flag":"flush"},
				url : 'login',  
				type : 'post',
				dataType:'json',
				success:function(data){
					if(data!==null &&data.message.match('.+?成功.+')){
						$("#userInfo #spc").empty;
					$("#userInfo #spc").text(data.cnum);
					}
				},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert(XMLHttpRequest.status);
				   alert(XMLHttpRequest.readyState);
				   alert(textStatus);
			   }
			
			});

		};
		<!-- 弹出登录框-->
		$('.theme-login').click(function(){
			//刷新验证码
			docheck();
			docheck();
			$("div[name='QRcode']").hide();	
			$('#QRshow').val("二维码登录");
			$('.theme-popover-mask').fadeIn(100);
			$('.theme-popover').slideDown(200);
			$("div[name='form']").show();	
		})
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	})
		
		function login(){
			var time = new Date()
			var ajax_option = {
				//target: '#output',          //把服务器返回的内容放入id为output的元素中        
				//beforeSubmit://提交前的回调函数    
				url : './login', //默认是form的action， 如果申明，则会覆盖    
				type : 'post', //默认是form的method（get or post），如果申明，则会覆盖    
				dataType : 'json', //html(默认), xml, script, json...接受服务端返回的类型    
				date:{"d":time},
				clearForm : true, //成功提交后，清除所有表单元素的值    
				resetForm : true, //成功提交后，重置所有表单元素的值    
				timeout : 5000, //限制请求的时间，当请求大于5秒后，跳出请求   
				success : function(data) {
					//	   alert(JSON.stringify(data));
					//	alert(data.sex+""+
					//		data.regdate.toString()+"\r\n"+
					//			data.avatar); 
					//						$("body").remove("#userInfo");
					if(data.message === "验证码错误"){
						alert("验证码错误");
						docheck();
					}else{
						
					$("#bt").hide();
					$("#regist").hide();			
					$("#quit").show();	
					$("#h1name").text("Hi!"+data.uname);
					$("#logina").text("Hi!"+data.uname);
					
						$('.theme-popover-mask').fadeOut(100);
						$('.theme-popover').slideUp(200);
					var path = "jsp/" + data.avatar;
					
					$("#logina").attr("href","javascript:void(0);");
					$("#touxiang").attr("src",path);
					}
				}, //提交后的回调函数
				error : function() {
					alert("登录异常，请重新登录！");
				}
			};
			$("#fm").ajaxSubmit(ajax_option);
			return false;
		};
	
		//$("#b").on("click",login);
		//$("#fm").bind("submit",login);
		$("#fm").submit(login);
		$("#quit").on("click", function() {
		//$("#name").val("@quit");
		//$("#pwd").val("@quit");
		
			var ajax_option2 = {
				url : './login', //默认是form的action， 如果申明，则会覆盖    
				type : 'post', //默认是form的method（get or post），如果申明，则会覆盖    
				dataType : 'text', //html(默认), xml, script, json...接受服务端返回的类型    
				data:{name:"@quit"},
				clearForm : true, //成功提交后，清除所有表单元素的值    
				resetForm : true, //成功提交后，重置所有表单元素的值    
				timeout : 3000, //限制请求的时间，当请求大于3秒后，跳出请求   
				success : function(data) {
					if(data.match('.+?成功.+')){
					$("#bt").show();
					$("#regist").show();
					$("#touxiang").attr("src","img/17.jpg");
					//$("#user").show();
					//$("#userInfo #spc").text("");
					//$("#userInfo #spc").empty();
					//$("#userInfo #sp1").text("-");
					//$("#userInfo img").attr("src", "#");
					//$("#userInfo #sp2").text("-");
					$("#quit").hide();
					$("#h1name").text("Hi!你好!");
					$("#logina").text("亲,请登录");
					
					$("#logina").attr("href","javascript:$('.theme-login').click();void(0);");
					alert("注销成功");
					}else{
						alert("注销失败，请重试！");
					}
				//temp.show();
				}, //提交后的回调函数
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("注销异常，请重试！");
	
					
					//前端异常调试 
					//XMLHttpRequest.status=200  (正常响应)
					//XMLHttpRequest.readyState=4 (正常接收)
					//parsererror 多是后台传递数据格式与dataType不符
					
					//	   alert(XMLHttpRequest.status);
					//   alert(XMLHttpRequest.readyState);
					  // alert(textStatus);
				   }
			};
			$(this).ajaxSubmit(ajax_option2);
		});
		
		
		$("#QRimg").parent().append("<img id='blur' src='img/0009021160259732_b.jpg' style='z-index:9999; position:relative; width:30%; height:30%;top:-180px'/>");
		$("#blur").hide();
	});
	var interval;
	function verify(){
		if($("div[name='QRcode']").is(":hidden")){
			clearInterval(interval);
			return;
		}
		var time = new Date();

		$.ajax({
			async:true,
			url : 'QrCode',  
			type : 'post',
			data:{"flag":"verify",
					"uuid":$("#UUID").val(),
					"d":time},
			dataType:'json',
			timeout:1800,
			success:function(data){
				//alert();
			//	if(data!==null&&data.message !==null &&data.message.match('.+?失败.+')){
				
				//}else 
				
				if(data.message === "已扫描"){
					if($("#blur").is(":hidden")){
					$("#QRimg").addClass("blur");
					$("#blur").show();
					
					}
				}else if(data.message === "验证失败！"){
					
				}else{
					
					$("div[name='QRcode']").hide();
					$("#bt").hide();
					$("#regist").hide();			
					$("#quit").show();	
					
					$("#h1name").text("Hi!"+data.uname);
					$("#logina").text("Hi!"+data.uname);
					$('.theme-popover-mask').fadeOut(100);
					$('.theme-popover').slideUp(200);
					var path = "jsp/" + data.avatar;
					$("#logina").attr("href","javascript:void(0);");
					$("#touxiang").attr("src",path);
				}
			},
			
		});
	}
	Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
	function docheck(){
		var time = new Date();
		$("#check").attr("src","<%=request.getContextPath()%>/ck?d="+time);
	};

	function doQRcheck(){
		
		$("#QRimg").removeClass("blur");
		$("#blur").hide();
		
		clearInterval(interval);
		var df = new Date().Format("yyyyMMddhhmmssS");
		var time = new Date();
		
		var uuid = df + Math.uuid().replace(/-/g, '');
	
		$("#QRimg").attr("src","<%=request.getContextPath()%>/QrCode?d="+time+"&&UUID="+uuid);
		//获得UUID，传递UUID
		$("#UUID").val(uuid);
		
		interval = setInterval("verify()",2000);
	};
	
</script>
</head>

<body>
<div class="container">
				<div class="top">
					<div class="top_l">
						<div class="top_left">
							<ul>
								<li id="xlcd_p"><a href="#">中国大陆<span><img src="img/1.jpg"/></span></a>
									<ul class="xlcd_c" id="xlcd_cc">
										<li>全球</li>
										<li>美国</li>
										<li>俄罗斯</li>
										<li>法国</li>
									</ul>
								</li>
								<c:if test="${sessionScope.name == null}">
					<li><a id="logina" class="top_a" href="javascript:$('.theme-login').click();void(0);">亲,请登录</a></li>
								</c:if>
								<c:if test="${sessionScope.name != null}">
								<li><a id="logina" class="top_a" href="javascript:void(0);">亲,请登录</a></li>
								<script type="text/javascript">
								$("#logina").text("Hi!<%=request.getSession().getAttribute("name")%>");
								$("#logina").attr("href","javascript:void(0);");
								</script>			
								</c:if>
								
								<li><a href="#">免费注册</a></li>
								<li><a href="#">手机逛淘宝</a></li>
							</ul>
						</div>
						<div class="top_right">
							<ul>
								<li id="xlcd_p2"><a href="Order?flag=allorder">我的淘宝<span><img src="img/1.jpg"/></span></a>
									<ul class="xlcd_c" id="xlcd_cc2">
										<li>已买到的宝贝</li>
										<li>我的足迹</li>
									</ul>
								</li>
								
								<li><a href="Spcar?flag=view"><span><img src="img/3.jpg"/></span>购物车</a></li>
								<li id="xlcd_p3"><a href="#"><span><img src="img/4.jpg"/></span>收藏夹</a>
									<ul class="xlcd_c" id="xlcd_cc3">
										<li>收藏的宝贝</li>
										<li>收藏的店铺</li>
									</ul>
								</li>
								<li><a href="#">商品分类</a><span>&nbsp;&nbsp;|</span></li>
								<li id="xlcd_p4"><a href="#">卖家中心<span><img src="img/1.jpg"/></span></a>
									<ul class="xlcd_c" id="xlcd_cc4">
										<li>免费开店</li>
										<li>已卖出的宝贝</li>
										<li>出售中的宝贝</li>
										<li>卖家服务市场</li>
										<li>卖家培训中心</li>
										<li>体检中心</li>
										<li>问商友</li>
									</ul>
								</li>
								<li id="xlcd_p5"><a href="#">联系客服<span><img src="img/1.jpg"/></span></a>
									<ul class="xlcd_c" id="xlcd_cc5">
										<li>消费者客服</li>
										<li>卖家客服</li>
									</ul>
								</li>
								<li id="xlcd_p6"><a href="#"><span><img src="img/5.jpg"/></span>网站导航</a></li>
							</ul>
						</div>
						<div class="xlcd_c1" id="xlcd_cc6">
										<div class="xlcd_q">
											<table>
												<tr>
													<td>
														<table class="top_xl">
															<tr><td class="ys1">主题市场</td></tr>
															<tr><td>女装</td><td>男装</td><td>内衣</td><td>鞋靴</td></tr>
															<tr><td>箱包</td><td>婴童</td><td>家电</td><td>数码</td></tr>
															<tr><td>手机</td><td>美妆</td><td>珠宝</td><td>眼睛</td></tr>
															<tr><td>手表</td><td>运动</td><td>户外</td><td>乐器</td></tr>
															<tr><td>游戏</td><td>动漫</td><td>影视</td><td>美食</td></tr>
															<tr><td>鲜花</td><td>宠物</td><td>农资</td><td>房产</td></tr>
															<tr><td>装修</td><td>建材</td><td>家具</td><td>百货</td></tr>
															<tr><td>汽车</td><td>二手车</td><td>办公</td><td>企业购</td></tr>
															<tr><td>定制</td><td>教育</td><td>卡卷</td><td>本地</td></tr>
														</table>
													</td>
													<td>
														<table class="top_xl">
															<tr><td class="ys2">主题市场</td></tr>
															<tr><td>女装</td><td>男装</td><td>内衣</td></tr>
															<tr><td>箱包</td><td>婴童</td><td>家电</td></tr>
															<tr><td>手机</td><td>美妆</td><td>内衣</td></tr>
															<tr><td>手表</td><td>运动</td><td>内衣</td></tr>
															<tr><td>游戏</td><td>动漫</td><td>内衣</td></tr>
															<tr><td>鲜花</td><td>宠物</td><td>内衣</td></tr>
															<tr><td>装修</td><td>建材</td><td>内衣</td></tr>
															<tr><td>汽车</td><td>二手车</td><td>内衣</td></tr>
															<tr><td>定制</td><td>教育</td><td>&nbsp;</td></tr>
														</table>
													</td>
													<td>
														<table class="top_xl">
															<tr><td class="ys3">主题市场</td></tr>
															<tr><td>女装</td><td>男装</td><td>内衣</td></tr>
															<tr><td>箱包</td><td>婴童</td><td>家电</td></tr>
															<tr><td>手机</td><td>美妆</td><td>内衣</td></tr>
															<tr><td>手表</td><td>运动</td><td>内衣</td></tr>
															<tr><td>游戏</td><td>动漫</td><td>内衣</td></tr>
															<tr><td>鲜花</td><td>宠物</td><td>内衣</td></tr>
															<tr><td>装修</td><td>建材</td><td>内衣</td></tr>
															<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
															<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
														</table>
													</td>
													<td>
														<table class="top_xl">
															<tr><td class="ys4">主题市场</td></tr>
															<tr><td>女装</td><td>男装</td></tr>
															<tr><td>箱包</td><td>婴童</td></tr>
															<tr><td>手机</td><td>美妆</td></tr>
															<tr><td>手表</td><td>运动</td></tr>
															<tr><td>游戏</td><td>动漫</td></tr>
															<tr><td>鲜花</td><td>&nbsp;</td></tr>
															<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
															<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
															<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
														</table>
													</td></tr>
											</table>
										</div>
									</div>
					</div>
				</div>
				
			<div class="logo_bar">
				<div class="logo">
					<div class="top_img"><img src="img/2.jpg"/></div>
					<div class="top_form">
							<form action="search" method="post">	
							<input class="bg_color1" required type="text" placeholder="请输入想要的东西吧" value="" name="word"/>
							<input class="bg_color2" type="submit"  value="搜索"/>
							</form>
						<p><span><a href="#">新款连衣裙</a></span><span><a href="#">四件套</a></span>
							<span><a href="#">潮流T恤</a></span><span><a href="#">时尚女鞋</a></span>
							<span><a href="#">短裤</a></span><span><a href="#">半身裙</a></span>
							<span><a href="#">男士外套</a></span><span><a href="#">墙纸</a></span>
							<span><a href="#">行车记录仪</a></span><span><a href="#">新款男鞋</a></span>
							<span><a href="#">耳机</a></span><span><a class="gd" href="#">更多></a></span>
						</p>
					</div>
					<div class="top_img2">
						<img src="img/8.jpg" />
					</div>
				</div>
			</div>
				
			<div class="nav_bar">	
				<div class="nav">
					<ul>
						<li class="nav_jc"><a href="#">主题市场</a></li>
						<li class="nav_d"><a href="#">天猫</a></li>
						<li class="nav_d"><a href="#">聚划算</a></li>
						<li class="nav_d"><a href="#">天猫超市</a></li>
						<li><span>|</span><a href="#">淘抢购</a></li>
						<li><a href="#">电器城</a></li>
						<li><a href="#">司法拍卖</a></li>
						<li><a href="#">中国制造</a></li>
						<li><a href="#">兴农扶贫</a></li>
						<li><span>|</span><a href="#">飞猪旅行</a></li>
						<li><a href="#">智能生活</a></li>
						<li><a href="#">苏宁易购</a></li>
					</ul>
				</div>
			</div>
			
		</div>
		
		<div class="content">
			<div class="content_left">
				<div class="content_left_top">
					<div class="c_content">
						<div class="c_nav">
		<ul id="nav_xlcd_p1">
								<li><a href="#">女装</a><span>/</span></li>
								<li><a href="#">男装</a><span>/</span></li>
								<li class="a"><a href="#">内衣</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">鞋靴</a><span>/</span></li>
								<li><a href="#">箱包</a><span>/</span></li>
								<li class="a"><a href="#">配件</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">童装玩具</a><span>/</span></li>
								<li><a href="#">孕产</a><span>/</span></li>
								<li><a href="#">用品</a><span  class="kongge1">></span></li>
							</ul>
							<ul>
								<li><a href="#">家电</a><span>/</span></li>
								<li><a href="#">数码</a><span>/</span></li>
								<li class="a"><a href="#">手机</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">美妆</a><span>/</span></li>
								<li><a href="#">洗护</a><span>/</span></li>
								<li><a href="#">保健品</a><span class="kongge2">></span></li>
							</ul>
							<ul>
								<li><a href="#">珠宝</a><span>/</span></li>
								<li><a href="#">眼睛</a><span>/</span></li>
								<li class="a"><a href="#">手表</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">运动</a><span>/</span></li>
								<li><a href="#">户外</a><span>/</span></li>
								<li class="a"><a href="#">乐器</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">游戏</a><span>/</span></li>
								<li><a href="#">动漫</a><span>/</span></li>
								<li class="a"><a href="#">影视</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">美食</a><span>/</span></li>
								<li><a href="#">生鲜</a><span>/</span></li>
								<li class="a"><a href="#">零食</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">鲜花</a><span>/</span></li>
								<li><a href="#">宠物</a><span>/</span></li>
								<li class="a"><a href="#">农资</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">房产</a><span>/</span></li>
								<li><a href="#">装修</a><span>/</span></li>
								<li class="a"><a href="#">建材</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">家具</a><span>/</span></li>
								<li><a href="#">家饰</a><span>/</span></li>
								<li class="a"><a href="#">家纺</a><span>></span></li>
							</ul>
							<ul>
								<li><a href="#">汽车</a><span>/</span></li>
								<li><a href="#">二手车</a><span>/</span></li>
								<li><a href="#">用品</a><span class="kongge2">></span></li>
							</ul>
							<ul>
								<li><a href="#">办公</a><span>/</span></li>
								<li><a href="#">DIY</a><span>/</span></li>
								<li><a href="#">五金电子</a><span class="kongge3">></span></li>
							</ul>
							<ul>
								<li><a href="#">百货</a><span>/</span></li>
								<li><a href="#">餐厨</a><span>/</span></li>
								<li><a href="#">家庭保健</a><span class="kongge1">></span></li>
							</ul>
							<ul>
								<li><a href="#">学习</a><span>/</span></li>
								<li><a href="#">卡卷</a><span>/</span></li>
								<li><a href="#">本地服务</a><span class="kongge1">></span></li>
							</ul>
							
							<div class="nav_xlcd1" id="nav_xlcd_c1">
										<div class="nav_xlcd1_top">
											<div class="div_top">
												<h1>女装<span class="right">更多</span></h1>
												<div class="neirong">
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span>
												</div>
											</div>
											<div class="div_middle">
												<h1>女装<span class="right">更多</span></h1>
												<div class="neirong">
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span>
												</div>
											</div>
											<div class="div_bottom">
												<h1>女装<span class="right">更多</span></h1>
												<div class="neirong">
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span><span>毛呢外套</span>
													<span>毛呢外套</span><span>毛呢外套</span>
												</div>
											</div>
										</div>
										<div class="nav_xlcd1_bottom">
											<h1>猜你喜欢</h1>
											<table>
												<tr><td><img src="img/42.png"/><p>2017秋冬新款正</p></td><td><img src="img/42.png"/><p>2017秋冬新款正</p></td></tr>
												<tr><td><img src="img/42.png"/><p>2017秋冬新款正</p></td><td><img src="img/42.png"/><p>2017秋冬新款正</p></td></tr>
												<tr><td><img src="img/42.png"/><p>2017秋冬新款正</p></td><td><img src="img/42.png"/><p>2017秋冬新款正</p></td></tr>
											</table>
										</div>
									</div>
						</div>
						
						<div class="c_banner">
							<div class="banner_top">
								<div class="banner_top_left">
								
								<a href="ProInfo?gid=1">
									<img style="width:522px;height:281px" src="img/华为.png" />
								</a>
								</div>
								<div class="banner_top_right">
									<img src="img/10.jpg" />
								</div>
							</div>
							<div class="banner_bottom">
								<div class="banner_bottom_left">
									<h1>理想生活上天猫</h1>
									<a href="ProInfo?gid=2">
									<img style="width:522px;height:201px" src="img/小米6.PNG" />
									</a>
								</div>
								<div class="banner_bottom_right">
									<h1>今日热卖</h1>
									<img src="img/13.jpg" />
								</div>
							</div>
						</div>
					</div>
					
					
					<!--<div class="c_ads">
						SEA
					</div>-->
				</div>
				<div class="content_left_bottom">
					<div class="content_left_bottom_left">
						<img src="img/14.jpg">
						<p>让你的生活更有趣</p>
					</div>
					<div class="content_left_bottom_right">
						<div class="ads_left">
							<img src="img/15.jpg">
						</div>
						<div class="ads_right">
							<h1>狗狗每天早出晚归，主人跟在后面感动了<span><a href="#">更多</a></span></h1>
							<p>国外有个男子因年老去世了，留下一只狗和少许财产。
								主人的小儿子将狗狗带回家饲养，狗狗不吵也不闹，
								饭量少，每天吃完，就安静的离开家门，等晚上才的时候才回来</p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="content_right">
				<div class="login">
					<div class="login_top">
					<c:if test="${sessionScope.avatar == null}">
					
						<img id="touxiang" src="img/17.jpg"/>
					</c:if>
					<c:if test="${sessionScope.avatar != null}">
					
						<img id="touxiang" src="jsp/${sessionScope.avatar}" style="border-radius: 50%;height: 60px;"/>
					</c:if>
					
						<h1 id="h1name">Hi!你好!</h1>
							<span class="c_1">领取金币抵钱</span><span class="c_2">会员俱乐部</span>
						<div class="zhuce">
							<button class="btn btn-primary btn-large theme-login" id="bt" type="button">登录</button>
							
							<button onclick="javascript:location.href='jsp/regist.jsp'" id="regist" type="button">注册</button>
							<button type="button" id="quit">注销</button>
							<c:if test="${sessionScope.avatar == null}">
					
						<script type="text/javascript">
					
								
					$("#quit").hide();	
						</script>
					</c:if>
					<c:if test="${sessionScope.avatar != null}">
					<script type="text/javascript">
					$("#h1name").text("Hi!<%=request.getSession().getAttribute("name")%>");
							$("#bt").hide();
					$("#regist").hide();			
						
						</script>
					
					</c:if>
						</div>			
					</div>
				<div class="jubao">
					<h1>网上有害信息举报专区</h1>
				</div>
				<div class="gonggao">
					<div class="nav_top">
						<ul>
							<li><a href="#">公告</a></li>
							<li><a href="#">规则</a></li>
							<li><a href="#">论坛</a></li>
							<li><a href="#">安全</a></li>
							<li><a href="#">公益</a></li>
						</ul>
					</div>
					<div class="xiala_1" id="hd_1">
						<ul>
							<li class="jiase"><a href="#">阿里连续登顶财富杂志“最受赞赏中国公司”</a></li>
							<li><a href="#">双11成交额1682亿元&nbsp;买家电双11当天最划算</a></li>
						</ul>
					</div>
					
				</div>
				<div class="tubiao">
					<table>
						<tr><td><img src="img/20.jpg"/><p>充话费</p></td><td><img src="img/21.jpg"/><p>旅行</p></td><td><img src="img/22.png"/><p>车型</p></td><td><img src="img/23.png"/><p>游戏</p></td></tr>
						<tr><td><img src="img/24.png"/><p>彩票</p></td><td><img src="img/25.png"/><p>电影</p></td><td><img src="img/26.png"/><p>酒店</p></td><td><img src="img/27.png"/><p>理财</p></td></tr>
						<tr><td><img src="img/28.png"/><p>找服务</p></td><td><img src="img/29.png"/><p>演出</p></td><td><img src="img/30.png"/><p>水电煤</p></td><td><img src="img/31.png"/><p>火车票</p></td></tr>
					</table>
				</div>
				<div class="app">
					<h1>阿里APP<span>更多</span></h1>
					<table>
						<tr><td><img src="img/32.png"/></td><td><img src="img/33.png"/></td><td><img src="img/34.png"/></td><td><img src="img/35.png"/></td><td><img src="img/36.png"/></td></tr>
						<tr><td><img src="img/37.png"/></td><td><img src="img/38.png"/></td><td><img src="img/39.png"/></td><td><img src="img/40.png"/></td><td><img src="img/41.png"/></td></tr>
					</table>
				</div>
			</div>
			</div>
		</div>


<!-- ---------------------------------------------------------------------------------------------- -->
	



<div class="theme-popover" style="text-align: center;">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>登录 是一种态度</h3>
     </div>
    <h4>点击图片生成新的验证码（或二维码）！</h4>
     <div class="theme-popbod dform" name="form">
           <form class="theme-signin" name="loginform" id="fm" action="" method="post">
                <ol>
                     <li><strong>用户名：</strong><input class="ipt" type="text" name="name" value="" size="20" required="required"/></li>
                     <li><strong>密码：</strong><input class="ipt" type="password" name="pwd" value="" size="20" required="required"/></li>
                     <li><strong>验证码：</strong><input class="ipt" type="text" name="checkcode" value="" size="20" required="required"/></li>
                     <li><img id="check" alt="验证码" src="<%=request.getContextPath()%>/ck" onclick="docheck()" style="width: 200px;height: 60px;border-color: blue"/>
                     <li ><input class="btn btn-primary" style="left:60px" type="submit" id="b" value="登 录 "/></li>
                </ol>
           </form>
     </div>
       <div id="qrdiv" name="QRcode" style="text-align: center;position:relative;width: 276px;height: 276px;left:180px;float: left;">
	<img src="#" onclick="doQRcheck()" alt="二维码" id="QRimg" style="position:static;width: 276px;height: 276px;"/>
      <input type="hidden" id="UUID">
      </div>
    <input style="position:relative; float:right " class="btn btn-primary" type="button" id="QRshow" value="二维码登录" />                       
</div>

	
</body>
</html>
