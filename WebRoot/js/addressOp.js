/**
 * 
 */
$(document).ready(function(){   	
$('a[name = address]').click(function(){
	//$('.theme-popover-mask').fadeIn(100);
	$('div[class=theme-popover][name=address]').slideDown(200);
	$("div[name='form']").show();
});

$('.theme-poptit .close').click(function(){
$('.theme-popover-mask').fadeOut(100);
$('.theme-popover').slideUp(200);
});
$("input[id=editb]").click(function(){
	$('div[class=theme-popover][name=edit]').slideDown(200);
	$("div[name='eform']").show();
	var aid = $(this).attr("name");
	$("#efm > input[type=hidden][name=aid]").val(aid);
	
	var name = $("div[name="+aid+"] span[name = aname]").text();
	var adr = $("div[name="+aid+"] span[name = address]").text();
	var aphone = $("div[name="+aid+"] span[name = aphone]").text();
	$("#efm > ol > li > input[name=aname]").val(name.replace(/\s/g,''));
	$("#efm > ol > li > input[name=address]").val(adr.replace(/\s/g,''));
	$("#efm > ol > li > input[name=aphone]").val(aphone.replace(/\s/g,''));
	
});
function addr(){
	var time = new Date();

	
	var ajax_option = {
		//target: '#output',          //把服务器返回的内容放入id为output的元素中        
		//beforeSubmit://提交前的回调函数 
		url : '../ast', //默认是form的action， 如果申明，则会覆盖    
		type : 'post', //默认是form的method（get or post），如果申明，则会覆盖    
		dataType : 'json', //html(默认), xml, script, json...接受服务端返回的类型    
		date:{"d":time},
		clearForm : true, //成功提交后，清除所有表单元素的值    
		resetForm : true, //成功提交后，重置所有表单元素的值    
		timeout : 3000, //限制请求的时间，当请求大于3秒后，跳出请求   
		success : function(data) {
			if(data.message.match('.+?失败.+')){
				
					alert("添加失败，请重试！");
				
				
			}else{
				alert("添加成功！");
				location.href="/shopping/Spcar?flag=view";
			//$('.theme-popover-mask').fadeOut(100);
			$('.theme-popover').slideUp(200);
			
			}
		}, //提交后的回调函数
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert("修改异常，请重试！");
		   },
	};
	$("#pfm").ajaxSubmit(ajax_option);
	return false;
};
$("#pfm").submit(addr);
function eddr(){
	var aid = $("#efm > input[type=hidden][name=aid]").val();
	
	var name = $("div[name="+aid+"] span[name = aname]").html();
	var adr = $("div[name="+aid+"] span[name = address]").html();
	var aphone = $("div[name="+aid+"] span[name = aphone]").html();
	if(name ==="" || adr==="" || aphone ===""){
		alert("请输入完整信息！");
	}
	
	var ajax_option2 = {
         type:'post',    
         url:'/shopping/ast',  
         data:{
         	"flag":"edit",
         	},
         dataType:'text',
         async: true,
         success:function(data){  
				if(data.match('.+?成功.+')){
             	alert("修改成功！"); 
                 //MARK**转付款界面
					//location.href="../index.jsp";	
             	location.href="/shopping/Spcar?flag=view";
				}else{
						alert("修改失败");
					}
         },
         error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert(XMLHttpRequest.status);
				   alert(XMLHttpRequest.readyState);
				   alert(textStatus);
			   }
     };  
	 $("#efm").ajaxSubmit(ajax_option2);
	return false;
};
$("#efm").submit(eddr);
});


function deleA(a){
	var aid = a;
	 $.ajax({
         type:'post',  
         traditional :true,//阻止深度序列化  
         url:'../ast',  
         data:{
         	"flag":"dele",
         	"aid":aid},
         dataType:'json',
         async: false,
         success:function(data){  
				if(data.message.match('.+?成功.+')){
             	alert("成功删除！"); 
                 //MARK**转付款界面
					//location.href="../index.jsp";	
             	location.href="/shopping/Spcar?flag=view";
				}else{
						alert("删除失败");
					}
         },
         error:function(XMLHttpRequest, textStatus, errorThrown){
				   alert(XMLHttpRequest.status);
				   alert(XMLHttpRequest.readyState);
				   alert(textStatus);
			   }
     });  
};


