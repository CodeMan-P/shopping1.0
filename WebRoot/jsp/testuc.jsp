<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/jsp/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testuc.jsp' starting page</title>
    
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
  <script type="text/javascript">
  $(document).ready(function(){
      var saveDataAry=[];    
      var data1={"userName":"ququ","address":"gr"};    
      //var data2={"userName":"ququ","address":"gr"};    
      
      saveDataAry.push(data1);    
/*     var data = $("#fn").serializeArray(); //自动将form表单封装成json  
      alert(JSON.stringify(data));  
     // saveDataAry.push(data2);           
 //     var temp = JSON.stringify(saveDataAry);
 
      $.ajax({   
          type:"POST",   
          url:"../ucc.do/uc6",   
          dataType:"json",        
          contentType:"application/json",                 
          data:JSON.stringify(data),//JSON.stringify(saveDataAry),   
          success:function(data){   
                            alert(data+"");          
          }   
       });  */
       $.prototype.serializeObject = function() {  
    	    var a, o, h, i, e;  
    	    a = this.serializeArray();  
    	    o = {};  
    	    h = o.hasOwnProperty;  
    	    for (i = 0; i < a.length; i++) {  
    	        e = a[i];  
    	        if (!h.call(o, e.name)) {  
    	            o[e.name] = e.value;  
    	        }  
    	    }  
    	    return o;  
    	};  
	  function addr(){
    	      var saveDataAry=[];    
    	      var data1={"userName":"ququ","address":"gr"};    
    	      saveDataAry.push(data1);  
		    var data = $("#fn").serializeObject(); //自动将form表单封装成json  
		   alert(JSON.stringify(data));
/* 		    var temp = JSON.stringify(data)
		    var data = "{\"asd\":{\"ename\":123}}";
 */
 //   alert(data);  
		     // saveDataAry.push(data2);           
		 //     var temp = JSON.stringify(saveDataAry);
/* 		 var patrn = /{.*}/;
		 var match = temp.match(patrn); */
		 
		 //alert(match.toString());
	/* alert(JSON.stringify({assd:"asdsd",asd:12312})); */
		      $.ajax({   
		          type:"POST",   
		          url:"../ucc.do/uc6",   
		          dataType:"json",        
		          contentType:"application/json",                 
		          data:JSON.stringify($("#fn").serializeObject()),//JSON.stringify($("#fn")),////JSON.stringify({assd:"asd",asd:12312}),//match.toString(),//JSON.stringify(data),//JSON.stringify(saveDataAry),   
		          success:function(data){   
		                            alert(JSON.stringify(data));          
		          }   
		       });
		      
			/* var time = new Date();
			//JSON.stringify(param)
			var mess={};
			mess.d=time;
			mess.user="root";
			//alert(JSON.stringify(mess));
			$.ajaxSetup({contentType:"application/json;charset=UTF-8"});
			var ajax_option = {
				//target: '#output',          //把服务器返回的内容放入id为output的元素中        
				//beforeSubmit://提交前的回调函数 
				url:'../ucc.do/uc6', //默认是form的action， 如果申明，则会覆盖    
				type:'POST', //默认是form的method（get or post），如果申明，则会覆盖    
				contentType:"application/json", 
				dataType:'json', //html(默认), xml, script, json...接受服务端返回的类型    
				
				clearForm : true, //成功提交后，清除所有表单元素的值    
				resetForm : true, //成功提交后，重置所有表单元素的值    
				timeout : 3000, //限制请求的时间，当请求大于3秒后，跳出请求   
				success : function(data) {
					alert(data);
				}, //提交后的回调函数
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(XMLHttpRequest);
					alert(textStatus);
					alert(errorThrown);
				   },
			};
			$("#fn").ajaxSubmit(ajax_option); */
			return false;
		};
	  $("#sub").click(function(){

		  addr();
	  });
  })
  </script>
  </head>
  
  <body>
    This is my JSP page. <br>
    <form id="fn" action="ucc.do/uc" enctype="application/json" method="post" >
   <input name="asd">
   <input name="asd2">
    </form>
    <input type="button" value="提交" id="sub">
  </body>
</html>
