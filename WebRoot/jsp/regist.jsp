<%@ page errorPage="ShowError.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/jsp/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>用户注册页面</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
td {
	border: 1px solid #CCCCCC;
}
</style>
<link rel="stylesheet" href="css/style_login.css" />
<link rel="stylesheet" type="text/css" href="../css/easyui.css">
<link rel="stylesheet" type="text/css" href="../css/icon.css">
<link rel="stylesheet" type="text/css" href="../css/demo.css">


<script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		Date.prototype.format = function(pattern) {
			/*初始化返回值字符串*/
			var returnValue = pattern;
			/*正则式pattern类型对象定义*/
			var format = {
				"y+" : this.getFullYear(),
				"M+" : this.getMonth() + 1,
				"d+" : this.getDate(),
				"H+" : this.getHours(),
				"m+" : this.getMinutes(),
				"s+" : this.getSeconds(),
				"S" : this.getMilliseconds(),
				"h+" : (this.getHours() % 12),
				"a" : (this.getHours() / 12) <= 1 ? "AM" : "PM"
			};
			/*遍历正则式pattern类型对象构建returnValue对象*/
			for (var key in format) {
				var regExp = new RegExp("(" + key + ")");
				if (regExp.test(returnValue)) {
					var zero = "";
					for (var i = 0; i < RegExp.$1.length; i++) {
						zero += "0";
					}
					var replacement = RegExp.$1.length == 1 ? format[key] : (zero + format[key]).substring((("" + format[key]).length));
					returnValue = returnValue.replace(RegExp.$1, replacement);
				}
			}
			return returnValue;
		};
		$("input[name=regdate]").val(new Date().format("yyyy-MM-dd"));
		//$('input[name=regdate]').attr('disabled',true);
		$("#bt1").on("click", function() {
			$("#file").attr("name", "file");
			$.ajaxFileUpload({
				method : "POST",
				url : "../UMS", //需要链接到服务器地址  
				secureuri : false,
				fileElementId : 'file', //文件选择框的id属性
				dataType : 'text', //服务器返回的格式  
				async : true,
				success : function(data, status) {
					//上传成功之后的操作
					if (data.match('img.+')) {
						$("#img").attr("src", data);
						// $("input[name='img']").val(data);
						$("#ava").val(data);
						alert("上传成功");
						$("#file").removeAttr("name");
					} else {
						$("#file").removeAttr("name");
						alert("上传失败");
					}
				},
				error : function(data, status, e) {
					//上传失败之后的操作
					$("#file").removeAttr("name");
					alert("error");
				}
			});
		});



		$("#userForm").form({
			onSubmit : function() {
				var s = $("#sild").slider('getValue');
				if(s!= c){
					alert("请将滑块移到正确位置！");
					a = Math.round(Math.random() * 50);
					b = Math.round(Math.random() * 50);
					c = a + b;
					$("#vtd").html(a + "+" + b + "=");
					return false;
				}
			},
			url : '../UMS', //默认是form的action， 如果申明，则会覆盖    
			type : 'post', //默认是form的method（get or post），如果申明，则会覆盖    
			async : true,
			data : $("#userForm").serialize(),
			dataType : 'text', //html(默认), xml, script, json...接受服务端返回的类型    
			//clearForm: true,          //成功提交后，清除所有表单元素的值    
			// resetForm: true,          //成功提交后，重置所有表单元素的值    
			timeout : 3000, //限制请求的时间，当请求大于3秒后，跳出请求   
			success : function(data) {
				alert(data);
				if (data.match('注册成功.+')) {
					location.href = "../index.jsp";
				}

			}, //提交后的回调函数
			error : function() {}
		});
		var a = Math.round(Math.random() * 50);
		var b = Math.round(Math.random() * 50);
		var c = a + b;
		$("#vtd").html(a + "+" + b + "=");

		var te = $("tr");
		$.each(te, function(index, obj) {
			$(obj).children(":eq(1)").css("text-align", "center");
		})
		$.extend($.fn.validatebox.defaults.rules, {
			minLength : {
				validator : function(value, param) {
					//return value.length >= param[0];
					return (value.length == 11)
				},
				message : '输入正确手机号！.'
			},
			equals : {
				validator : function(value, param) {
					return value == $(param[0]).val();
				},
				message : ''
			}
		});



	});
</script>
</head>

<body>
	<div class="container">
		<div class="top">
			<div class="top_left">
				<ul>
					<li><a href="#">手机逛淘宝</a></li>
				</ul>
			</div>
			<div class="top_right">
				<ul>
					<li><a href="#
					index.sjp">淘宝网首页</a></li>
					
					
				</ul>
			</div>
		</div>
	</div>
	<div class="nav">
		<div class="bottom">
			<div class="bottom_left">
				<ul>
					<li><img class="logo" src="img/r56.png" /></li>
					<li><span>用户注册</span></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="mmenu" style="border-bottom: 2px solid #CCCCCC;">
		<div class="menu_middle">
			<ul>

				<li>填写账号信息</li>

			</ul>
		</div>
	</div>

	<center>
		<div style="width:900px;height:400px;">
			<iframe id="iframe_display" name="iframe_display"
				style="display: none;"></iframe>

			<form id="userForm" action="../UMS" method="post">
				<input type="hidden" name="flag" value="regist" /> <input
					name="regdate" type="hidden" /> <input type="hidden" name="avatar"
					id="ava" value="img/17.jpg" /> <br>
				<table id="t"
					style="width: 600px;height: 400px;border:  1px solid #CCCCCC;margin: 5px; ">

					<tr>
						<td>账户名：</td>
						<td><input type="text" class="easyui-validatebox"
							name="uname" data-options="required:true"></td>
						<td rowspan="8">
							<center>
								<img id="img" src="../img/17.jpg" alt="头像"
									style="width: 200px;height: 200px;border:  1px solid #CCCCCC;" />
							</center>

						</td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" name="upwd" id="upwd"
							class="easyui-validatebox" data-options="required:true">

						</td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input type="password" name="upwd2"
							class="easyui-validatebox" required="required"
							validType="equals['#upwd']" invalidMessage="密码不一致！！！"></td>
					</tr>
					<tr>
						<td>手机：</td>
						<td><input type="text" name="phone" class="easyui-numberbox"
							data-options="required:true,validType:'minLength[0]'"></td>
					</tr>
					<!-- onkeyup="this.value=this.value.replace(/\D/g,'')" -->
					<tr>
						<td>邮箱：</td>
						<td><input class="easyui-validatebox"
							data-options="required:true,validType:'email'" type="text"
							name="email"></td>
					</tr>
					<tr>
						<td>年龄：</td>
						<td><input type="text" name="age"
							onkeyup="this.value=this.value.replace(/\D/g,'')" required>

						</td>
					</tr>
					<tr>
						<td>所在地：</td>
						<td><input type="text" name="city" required></td>
					</tr>
					<tr>
						<td>性别：</td>
						<td><select name="sex">
								<option value="男" defaultSelected>男</option>
								<option value="女">女</option>
						</select></td>
					</tr>
					<tr>
						<td>姓名：</td>
						<td><input type="text" name="realname" /></td>
						<td>
							<center>
								<input id="file" type="file" />
							</center>
						</td>
					</tr>
					<tr>
						<td>身份证:</td>
						<td><input type="text" name="idcard" /></td>
						<td>
							<center>
								<input type="button" value="上传头像" id="bt1" />
							</center>

						</td>
					</tr>
					<tr>
						<td style="text-align: center;font-size: 22px;color: orange;width: 110px;	" id="vtd">1+2=</td>
						<td colspan="2" style="height: 100px;">
							<center>
								<input class="easyui-slider" id="sild" value="0" style="width:300px"
									data-options="showTip:true,rule:[0,'|',25,'|','50','|','75','|','100']" />
							</center>
						</td>
					</tr>

					<tr>
						<td colspan="3">
							<center>
								<input type="submit" id="bt2" value="提交" />
							</center>
						</td>
					</tr>
				</table>
			</form>


		</div>

	</center>
</body>
</html>
