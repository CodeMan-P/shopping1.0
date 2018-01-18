<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>后台管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css"  href="css/icon.css">
<link rel="stylesheet" type="text/css"  href="css/demo.css">
<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.datagrid.js"></script>
<script type="text/javascript" src="js/jquery.edatagrid.js"></script>
	
	<script type="text/javascript" src="js/lib/jasmine-1.3.1/jasmine.js"></script>
<script type="text/javascript" src="css/build/spec/MessengerSpec.js"></script>
<script type="text/javascript" src="css/build/spec/lib/underscore-1.4.4.js"></script>
<script type="text/javascript" src="css/build/spec/lib/backbone-0.9.10.js"></script>
	hUmGNj7oj4VfNOqLliGkz72CtVeREFzZ
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hUmGNj7oj4VfNOqLliGkz72CtVeREFzZ"></script>
	 	
	-->


<link rel="stylesheet" type="text/css"
	href="css/build/css/messenger.css">
<link rel="stylesheet" type="text/css"
	href="css/build/css/messenger-theme-future.css">
<link rel="stylesheet" type="text/css" href="theme/easyui_full.css">
<link rel="stylesheet" type="text/css"
	href="theme/theme.codebox/master.css">
<link rel="stylesheet" type="text/css" href="theme/icon.css">
<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.datagrid.js"></script>
<script type="text/javascript" src="js/jquery.edatagrid.js"></script>
<script type="text/javascript"
	src="theme/expand/jquery-easyui-datagridview/datagrid-detailview.js"></script>
<script type="text/javascript" src="theme/insdep-extend.min.js"></script>

<script type="text/javascript" src="css/build/js/messenger.js"></script>
<script type="text/javascript"
	src="css/build/js/messenger-theme-future.js"></script>

<script type="text/javascript" src="js/echarts.js"></script>

<script type="text/javascript" src="js/api.js"></script>

<script type="text/javascript" src="js/bmap.min.js"></script>
<script type="text/javascript" src="js/purl.js"></script>
<script type="text/javascript" src="js/echarts-wordcloud.js"></script>
<style>
#maintitle {
	letter-spacing: 8px;
	font-family: "华文行楷";
}
;
</style>
<script>
	$(function() {
		$('#dialog').dialog({
			title : 'My Dialog',
			width : 600,
			height : 500,
			closed : true,
			cache : false,
			modal : true,
			buttons : [ {
				text : 'Close',
				handler : function() {
					$('#dialog').dialog("close");
				}
			} ]
		});
	})
</script>
<script type="text/javascript">
	$(document).ready(function() {


		//$('#dd').dialog('content', 'asdfdasf'); 

		//		var d = $("div.easyui-layout");
		//		alert( d.css("width"));
		//		alert( d.css("height"));
		var s = "欢迎使用超级无敌酷炫拽商城购物后台管理系统";
		var temp = $("#north");
		for (var i = 0; i < s.length; i++) {
			temp.append("<span id='maintitle' style='position:relative'>" + s[i] + "</span>");
		}

		function getRandomColor() {
			var c = '#';
			var cArray = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' ];
			for (var i = 0; i < 6; i++) {
				var cIndex = Math.round(Math.random() * 15);
				c += cArray[cIndex];
			}
			return c;
		}
		var TOP = -4;
		function changeColor() {
			var spans = $("span[id=maintitle]")
			$.each(spans, function(i, obj) {
				var a = Math.round(Math.random() * 250);
				var b = Math.round(Math.random() * 250);
				var c = Math.round(Math.random() * 250);
				var rgb = "rgb(" + a + "," + b + "," + c + ")";
				$(obj).css("color", rgb);
				$(obj).css("top", "0px");
				$(obj).css("top", TOP + "px");
				$(obj).css("left", "0px");
				$(obj).css("left", TOP + "px");

				if (TOP == 4) {
					TOP = -4;
				} else {
					TOP += 2;
				}
			});
		}
		;

		var titcol = setInterval(changeColor, 200);
		var speed = 300
		$('#alock').click(function() {
			if (titcol === "") {
				titcol = setInterval(changeColor, speed);
			} else {
				window.clearInterval(titcol);
				titcol = "";
			}
		});
		$('#aadd').click(function() {
			window.clearInterval(titcol);
			speed = speed + 100;
			titcol = setInterval(changeColor, speed);
		});
		$('#adele').click(function() {
			window.clearInterval(titcol);
			speed = speed - 100;
			if (speed < 100) {
				speed = 100;
			}
			titcol = setInterval(changeColor, speed);
		});
		$('#alock').parent().hover(
			function() {
				$('#alock').show();
				$('#aadd').show();
				$('#adele').show();
			},
			function() {
				$('#alock').hide();
				$('#aadd').hide();
				$('#adele').hide();
			}
		);
		$('#alock').hide();
		$('#aadd').hide();
		$('#adele').hide();
		$.parser.onComplete = function() {}
		$('#DataTree').tree({
			onClick : function(node) {
				var s = node.id + "";
				var par = $('#DataTree').tree("getParent", node.target);
				if (par.text === "DataGrid") {
					var tn = node.text;
					var tid = node.text + "_dgdiv";

					if (!$("#dgtab").tabs("exists", tn + "")) {
						$('#dgtab').tabs('add', {
							title : tn + "",
							id : tid + "",
							closable : true,
						});
					};
					//限定高度，固定底部分页栏
					var height = $("#dgtab").height() - (458 - 427);
					$("#" + tid + "").load("../dataTable?table=" + tn + "&&id=" + tid + "_dg&&h=" + height, function() {
						$("#" + tid + "_dg").edatagrid({
							url : '../datagrid?flag=' + tn,
							saveUrl : '../dataAdd?flag=' + tn,
							updateUrl : '../dataEdit?flag=' + tn,
							destroyUrl : '../dataDele?flag=' + tn,
							//定义是否从服务器排序数据。		
							remoteSort : false,
							onSave : function(index, row) {
								//alert(index);
								//alert(JSON.stringify(row));
								//$(this).edatagrid('reload');
							},
							onEdit : function(index, row) {
								//var row = $(this).datagrid("getRows");
								//for (var r = 0; r < row.length; r++) {
							//		$(this).datagrid("expandRow", r);
							//	}
								//上传保存编辑行数据到服务器，对比更新使用
								$.ajax({
									async : true,
									data : {
										"flag" : tn,
										"target" : "editing",
										"oldRow" : JSON.stringify(row)
									},
									url : 'dataEdit',
									type : 'post',
									dataType : 'text',
									success : function(data) {
										//accepted!
									}
								});

							},
							onError : function(index, row) {
								//alert(JSON.stringify(row));

								if (typeof row.trace == "null") {
									Messenger().post({
										message : row.msg,
										type : 'error', //'info',//'error',
										showCloseButton : true
									})
								} else {
									Messenger().post({
										message : row.msg,
										type : 'error', //'info',//'error',
										showCloseButton : true,
										actions : {
											other : {
												label : '错误详情',
												action : function() {
													var str = row.trace.replace(/at/g, "\r\n\tat\t");
													$('div[id=dialog]').html(str);
													$('#dialog').dialog('setTitle', '错误提示框');
													$('#dialog').dialog('open');

												}
											}
										}
									})

								}
							}
						});
						if (tn === "goods") {
							$("#" + tid + "_dg").datagrid({
								view : detailview,
								detailFormatter : function(rowIndex, rowData) {
									//alert(rowIndex);
									return '<table style="background:#0C80D7;" cellspacing="10"><tr>' +
										'<td rowspan=2 style="border:0"><img src="jsp/' + rowData.imgPath + '" style="border:1px #2a2a2a solid;height:100px;"></td>' +
										'<td style="border:0">' +
										'<p style="color:orangered">Name: ' + rowData.gname + '</p>' +
										'<p	style="color:#eec710">Price: ' + rowData.price + '￥</p>' +
										'</td>' +
										'</tr></table>';
								}
							});
						}
						//重新渲染,单个组件要渲染其父窗体；
						$.parser.parse($('#dgtab').parent());
						$("#center").tabs("select", "DataGrid");
						$("#dgtab").tabs("select", tn);
					//$("#"+tid+"_dg").datagrid('expandRow',0)
					});

				}
				;
			//	var temp = $("#"+node.text+"_tab").attr("id");
			//	alert(temp);
			}
		});
		$('#formTree').tree({
			onClick:function(node){
				//非叶节点双击触发展开/折叠操作，返回
				if(!$('#formTree').tree('isLeaf', node.target)){
					$('#formTree').tree('toggle', node.target)
					return;
				};
				var s = node.id + "";
				var nt = node.text;
				var par = $('#formTree').tree("getParent", node.target);
				var href;
				if(node.id == 111){
					href="./echarts/userRegion.html";
				}else if(node.id == 112){
					href="./echarts/userAgeStep.html";
				}else if(par.id == 113){
					nt+="注册分布表";
					href="./echarts/userRegDateInfo.jsp?flag="+node.text;
				}else if(par.id == 115){
					nt+="词云分析";
					if(node.id == 1151){
						href="./echarts/xingming.jsp?flag=xingInfo";	
					}else if(node.id == 1152){
						href="./echarts/xingming.jsp?flag=mingInfo";	
					}
				}
				
				
				if (!$("#center").tabs("exists", nt + "")) {
					$('#center').tabs('add', {
						title : nt,
						//content : 'Tab Body',
						href:href,
						closable : true
					});
				};
			
				//$.parser.parse($('#center'));
				$("#center").tabs("select", nt);

			}
		});
		//$.parser.parse($('#center').parent());
	/*	$('#center').tabs('add', {
			title : 'New Tab',
			content : 'Tab Body',
			closable : true,
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					alert('refresh');
				}
			} ]
		});*/
		$("#center").tabs("select", "DataGrid");
		$("#center").tabs("select", "Main");
		//$('.easyui-layout').layout('collapse','west'); 

		//$('#main').load('subTabs.html');
		Messenger.options = {
			extraClasses : 'messenger-fixed messenger-theme-future messenger-on-bottom messenger-on-right'
		}

		var i = 0;
		//消息框测试函数
		function show() {
			var i = 0;
			Messenger().post({
				message : 'There was an explosion while processing your request.',
				type : 'success', //'info',//'error',
				showCloseButton : true,
				actions : {
					cancel : {
						label : 'cancel launch',
						action : function() {
							var i = 0;

							Messenger().run({
								errorMessage : 'Error destroying alien planet',
								successMessage : 'Alien planet destroyed!',
								action : function(opts) {
									if (++i < 3) {
										return opts.error({
											status : 3000,
											readyState : 0,
											responseText : 0
										});
									} else {
										return opts.error();
									}
								}
							});
						}
					},
					other : {
						label : 'other',
						action : function() {}
					}
				},
			});
			Messenger().run({
				errorMessage : 'Error destroying alien planet',
				successMessage : 'Alien planet destroyed!',
				action : function(opts) {
					if (++i < 3) {
						return opts.error({
							status : 500,
							readyState : 0,
							responseText : 0
						});
					} else {
						return opts.error();
					}
				}
			});
		}
	//	show();
	//超级管理员登录成功添加
	//通行口令
	//alert($('#DataTree'));
		var node = $('#DataTree').tree('find',111);
	
	//alert($('#DataTree').tree('find',111).text);
	if (node){
			$('#DataTree').tree('insert', {
				before: node.target,
				data: {
					id: 110,
					text: 'role'
				}
			});
		}


	});
</script>

<!-- 验证表单项 -->
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		md : {
			validator : function(value, param) {

				var d1 = $.fn.datebox.defaults.parser(param[0]);
				var d2 = $.fn.datebox.defaults.parser(value);
				return d2 <= d1;
			},
			message : 'The date must be less than or equals to {0}.'
		},
		date : {
			validator : function(value) {

				//格式yyyy-MM-dd或yyyy-M-d
				return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
			},
			message : '请输入合适的日期格式'
		},
		phone : {
			validator : function(value) {
				var pv = $.fn.validatebox.defaults.rules.phone;
				var c = $(this).val();

				if (c.length > 11) {
					pv.message = "你的手机号这么长？";
					return false;
				}
				if (c.length < 11) {
					pv.message = "你的手机号这么短？";
					return false;
				}
				return true;
			},
			message : ''
		},
		intonly : {
			validator : function(value) {
				var c = $(this).val();
				if ('/\D/g'.test(c)) {

					$(this).val(c.replace(/\D/g, ''));

					return false;
				}

				return true;
			},
			message : '只能输入0-9'
		},
		bool : {
			validator : function(value) {
				var c = $(this).val();

				if (c === "true" || c === "false") {
					return true;
				}
				return false;
			},
			message : '只能输入true或false'
		}
	})
</script>
</head>

<body>
	<div id="dialog" title="Dialog Title">No Message!</div>
	<div class="easyui-layout"
		style="width:100%;height:inherit;position: absolute;top: 0px;margin:2px 0;bottom: 0px;overflow:hidden;">
		<div data-options="region:'north'" id="north"
			style="height:50px;text-align: center;padding:5px;font-size: 26px;background-color: rgb(42,42,42)">
			<a id="alock" href="javascript:void(0);" class="easyui-linkbutton"
				style="border:none; width:22px; height:22px;	position:absolute; top: 10px;right: 2px;background-color: transparent;"
				data-options="iconCls:'icon-lock'"></a> <a id="aadd"
				href="javascript:void(0);" class="easyui-linkbutton"
				style="border:none;height:22px;width:22px;	position:absolute; top: 10px;right: 25px;background-color: transparent;"
				data-options="iconCls:'icon-mini-append'"></a> <a id="adele"
				href="javascript:void(0);" class="easyui-linkbutton"
				style="border:none;height:22px;width:22px;	position:absolute; top: 10px;right: 49px;background-color: transparent;"
				data-options="iconCls:'icon-mini-delete'"></a>
		</div>
		
		
		<div data-options="region:'west',split:true" id="west" title="West"
			style="width:200px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="DataGrid" data-options="iconCls:'icon-database_red'"
					style="selected:true,padding:10px;">
					<ul class="easyui-tree" id="DataTree"
						data-options="url:'DataGrid_data.json',method:'get',animate:true,dnd:true,lines:true"></ul>
				</div>
				<div  title="报表分析"
					data-options="iconCls:'icon-chart'" style="padding:10px;">
					<ul class="easyui-tree" id="formTree"
						data-options="url:'reportForms.json',method:'get',animate:true,dnd:true,lines:true"></ul>
				</div>
				<div title="Title3" style="padding:10px">
					<ul class="easyui-tree"
						data-options="url:'tree_data1.json',method:'get',animate:true,dnd:true"></ul>
				</div>
			</div>
		</div>

		<div class="easyui-tabs" id="center"
			data-options="region:'center',fit:false,border:true,plain:false"
			>

			<div title="Main" id="main" class="easyui-tabs"
				data-options="border:false,plain:true" style="padding:1px">
				<div title="sub1" data-options="href:'_content.html',closable:true"
					style="padding:10px"></div>

			</div>
			<div title="DataGrid" id="dgtab" class="easyui-tabs"
				data-options="fit:true,border:false,plain:true"
				style="height: inherit;padding:1px"></div>
			<div title="About" data-options="href:'_content.html',closable:true"
				style="padding:10px"></div>

		</div>
		
		<!-- 
		<div data-options="region:'south',split:true" style="height:20px;"></div>
		<div data-options="region:'east',split:true"
			title="East" style="width:85px;">
			<a id="btn" href="javascript:alert('search');void(0);"
				class="easyui-linkbutton" style=""
				data-options="iconCls:'icon-search'">-</a> <a id="btn1"
				href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit'">-</a> <a id="btn2" href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-save'">-</a> <a
				id="btn3" href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove'">-</a> <a id="btn4" href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">-</a>
			<a id="btn5" href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-lock'">-</a> <a id="btn7" href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-add'">-</a> <a
				id="btn8" href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-clear'">-</a> <a href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-ok'">-</a><br>
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-mini-dele'">-</a>

		</div>
		<div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'" id="maintab">
			</div>

-->

	</div>
</body>
</html>
