/**
 * 
 */
	$(document).ready(function(){
	
	$.ajax({
	url:"../RoleServlet",
	type:"POST",
	success:function(data){
	for(var i=0;i<data.length;i++){
	
	$("<option value='"+data[i].roleid+"'>"+data[i].rolename+"</option>").appendTo("#roles");
	}
	},
	
    dataType:"json"
	});
    //alert("到此");
	//$("#role").change(function(){
	//var roleid=$(this).val();
	//alert(roleid);
	  $("#roles").change(function(){
   var roleid=$(this).val();
	$.ajax({
	url:"../RoleServlet",
	type:"POST",
	data:{"rid":roleid,
		"flag":"u"
	},
	success:function(data){
	for(var i=$("#user option").length-1;i>=0;i--){
	$("#user option").eq(i).remove();
	}
	for(var i=0;i<data.length;i++){
	$("<option value='"+data[i].userid+"'>"+data[i].username+"</option>").appendTo("#user");
	}
	
	},
	dataType:"json"
	
	});

	});
	  
	  
	  
		$.ajax({
			url:"../RoleServlet",
			type:"POST",
			success:function(data){
			for(var i=0;i<data.length;i++){
			
			$("<option value='"+data[i].roleid+"'>"+data[i].rolename+"</option>").appendTo("#pro");
			}
			},
			
		    dataType:"json"
			});
		    //alert("到此");
			//$("#role").change(function(){
			//var roleid=$(this).val();
			//alert(roleid);
			  $("#pro").change(function(){
		   var roleid=$(this).val();
			$.ajax({
			url:"../RoleServlet",
			type:"POST",
			data:{"rid":roleid,
				"flag":"u"
			},
			success:function(data){
			for(var i=$("#city option").length-1;i>=0;i--){
			$("#city option").eq(i).remove();
			}
			for(var i=0;i<data.length;i++){
			$("<option value='"+data[i].userid+"'>"+data[i].username+"</option>").appendTo("#city");
			}
			
			},
			dataType:"json"
			
			});

			});
	});