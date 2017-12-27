/**
 * 
 */

$(document).ready(function(){
	
    	$("button.createOrderButton").click(function(){ 
            var $check_boxes = $("input[type=checkbox][id!=check_all_box]:checked");  
            if($check_boxes.length<=0){ alert('未勾选购买物品，请勾选！');return;}  
            if(confirm('确定要剁手（购买）吗？')){
                var buyCids = new Array();  
                $check_boxes.each(function(){  
                	buyCids.push($(this).val());  
                });  
                var adresId = $("input[type=radio]:checked").val();
             
                $("input[name='buyCids']").val(buyCids.toString());
                $("input[name='adresId']").val(adresId+"");
                $("#frm").submit();
             
            }
            
        });  
});
function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}
  
	
    function changeBox(){
    	//$("input[type=checkbox][id!=check_all_box]").removeAttr("checked");
		$("input[type=checkbox][id!=check_all_box]").prop("checked",$('input[id=check_all_box]').is(':checked'));
    };
    function deleGoods(cid){
    	flag = false;
        $.ajax({
            type:'post',  
            traditional :true,//阻止深度序列化  
            url:'../Spcar',  
            data:{
            	"flag":"dele",
            	"cid":cid},
            dataType:'json',
            async: false,
            success:function(data){  
				if(data.message.match('.+?成功.+')){
                	alert("成功删除！"); 
                    //MARK**转付款界面
					//location.href="../index.jsp";	
                	flag =true; 
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
    return flag;  
    };
    
    
    
    
    var deleteOrderItem = false;
    var deleteOrderItemid = 0;
    $(function(){

    	$("a.deleteOrderItem").click(function(){
    		deleteOrderItem = false;
    		var oiid = $(this).attr("oiid")
    		deleteOrderItemid = oiid;
    		$("#deleteConfirmModal").modal('show');	   
    	});
    	$("button.deleteConfirmButton").click(function(){
    		deleteOrderItem = true;
    		$("#deleteConfirmModal").modal('hide');
    	});
    	
    	$('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
    		if(deleteOrderItem){
    			var page="foredeleteOrderItem";
    			$.post(
    				    page,
    				    {"oiid":deleteOrderItemid},
    				    function(result){
    						if("success"==result){
    							$("tr.cartProductItemTR[oiid="+deleteOrderItemid+"]").hide();
    						}
    						else{
    							location.href="login.jsp";
    						}
    				    }
    				);
    			
    		}
    	})	
    	
    	$("img.cartProductItemIfSelected").click(function(){
    		var selectit = $(this).attr("selectit");
    		var oiid = $(this).attr("oiid")
    		
    		if("selectit"==selectit){
    			$("input[type=checkbox][name="+oiid+"]").prop("checked",false);
    			$(this).attr("src","img/cartNotSelected.png");
    			$(this).attr("selectit","false")
    			$(this).parents("tr.cartProductItemTR").css("background-color","#fff");
    		}
    		else{
    			$("input[type=checkbox][name="+oiid+"]").prop("checked",true);
    			$(this).attr("src","img/cartSelected.png");
    			$(this).attr("selectit","selectit")
    			$(this).parents("tr.cartProductItemTR").css("background-color","#FFF8E1");
    		}
    		
    		syncSelect();
    		syncCreateOrderButton();
    		calcCartSumPriceAndNumber();
    	});
    	$("img.selectAllItem").click(function(){
    		var selectit = $(this).attr("selectit")
    		if("selectit"==selectit){
    			$("img.selectAllItem").attr("src","img/cartNotSelected.png");
    			$("img.selectAllItem").attr("selectit","false");
    			$("input[type=checkbox][id!=check_all_box]").prop("checked",false);
    			$(".cartProductItemIfSelected").each(function(){
    				$(this).attr("src","img/cartNotSelected.png");
    				$(this).attr("selectit","false");
    				$(this).parent("td").children("input[type=checkbox]").prop("checked",false);
    				$(this).parents("tr.cartProductItemTR").css("background-color","#fff");
    			});			
    		}
    		else{
    			$("input[type=checkbox][id!=check_all_box]").prop("checked",true);
    			$("img.selectAllItem").attr("src","img/cartSelected.png");
    			$("img.selectAllItem").attr("selectit","selectit")
    			$(".cartProductItemIfSelected").each(function(){
    				$(this).attr("src","img/cartSelected.png");
    				$(this).attr("selectit","selectit");
    				$(this).parent("td").children("input[type=checkbox]").prop("checked",true);
    				$(this).parents("tr.cartProductItemTR").css("background-color","#FFF8E1");
    			});				
    		}
    		syncCreateOrderButton();
    		calcCartSumPriceAndNumber();
    		

    	});

    	$(".numberPlus").click(function(){
    		
    		var pid=$(this).attr("pid");
    		var stock= $("span.orderItemStock[pid="+pid+"]").text();
    		var price= $("span.orderItemPromotePrice[pid="+pid+"]").text();
    		var num= $(".orderItemNumberSetting[pid="+pid+"]").val();

    		num++;
    		if(num>stock){
    			num = stock;
    		}
    		
    		syncPrice(pid,num,price);
    	});

    	$(".numberMinus").click(function(){
    		var pid=$(this).attr("pid");
    		var stock= $("span.orderItemStock[pid="+pid+"]").text();
    		var price= $("span.orderItemPromotePrice[pid="+pid+"]").text();
    		
    		var num= $(".orderItemNumberSetting[pid="+pid+"]").val();
    		--num;
    		if(num<=0){
    			num=1;
    		}
    		syncPrice(pid,num,price);
    	});
    	function syncPrice(pid,num,price){
    		
    		var oiid =$(".orderItemNumberSetting[pid="+pid+"]").attr("oiid"); 
    		var cartProductItemSmallSumPrice = formatMoney(num*price);
    		$.ajax({
                type:'post',  
                traditional :true,//阻止深度序列化  
                url:'../Spcar',  
                data:{
                	"flag":"change",
                	"cid":oiid,
                	"gnum":num},
            	dataType:'json',
                async: false,
                success:function(data){  
    				if(data.message.match('.+?成功.+')){
    					$(".orderItemNumberSetting[pid="+pid+"]").val(num);
    					 
    					$(".cartProductItemSmallSumPrice[pid="+pid+"]").html("￥"+cartProductItemSmallSumPrice);
    					calcCartSumPriceAndNumber();
    				}else{
    						alert("修改失败");
    					}
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
    				   alert(XMLHttpRequest.status);
    				   alert(XMLHttpRequest.readyState);
    				   alert(textStatus);
    			   }
            });  
    		/*
    		 * var page = "forechangeOrderItem";
    			$.post(
    				    page,
    				    {"pid":pid,"number":num},
    				    function(result){
    						if("success"!=result){
    							location.href="login.jsp";
    						}
    				    }
    				);
    		 */	
    	}
    	function calcCartSumPriceAndNumber(){
    		var sum = 0;
    		var totalNumber = 0;
    		$("img.cartProductItemIfSelected[selectit='selectit']").each(function(){
    			var oiid = $(this).attr("oiid");
    			var price =$(".cartProductItemSmallSumPrice[oiid="+oiid+"]").text();
    			price = price.replace(/,/g, "");
    			price = price.replace(/￥/g, "");
    			sum += new Number(price);	
    			
    			var num =$(".orderItemNumberSetting[oiid="+oiid+"]").val();
    			totalNumber += new Number(num);	
    			
    		});
    		
    		if(totalNumber==0){
    			$("span.cartSumNumber").html(0);
    			$("span.cartSumPrice").html("￥0.00");
    			return;
    		}
    		$("span.cartSumPrice").html("￥"+formatMoney(sum));
    		$("span.cartSumNumber").html(totalNumber);
    	}
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
    	$(".orderItemNumberSetting").keyup(function(){
    		var pid=$(this).attr("pid");
    		var stock= $("span.orderItemStock[pid="+pid+"]").text();
    		var price= $("span.orderItemPromotePrice[pid="+pid+"]").text();
    		
    		var num= $(".orderItemNumberSetting[pid="+pid+"]").val();
    		num = parseInt(num);
    		if(isNaN(num))
    			num= 1;
    		if(num<=0)
    			num = 1;
    		if(num>stock)
    			num = stock;
    		
    		syncPrice(pid,num,price);
    	});

    	
    	
    	$("#bt1").click(function(){
    		var params = "";
    		$(".cartProductItemIfSelected").each(function(){
    			if("selectit"==$(this).attr("selectit")){
    				var oiid = $(this).attr("oiid");
    				params += "&oiid="+oiid;
    			}
    		});
    		params = params.substring(1);
    		location.href="forebuy?"+params;
    	});
    	
    	function syncCreateOrderButton(){
    		var selectAny = false;
    		$(".cartProductItemIfSelected").each(function(){
    			if("selectit"==$(this).attr("selectit")){
    				selectAny = true;
    			}
    		});
    		
    		if(selectAny){
    			$("button.createOrderButton").css("background-color","#C40000");
    			$("button.createOrderButton").removeAttr("disabled");
    		}
    		else{
    			$("button.createOrderButton").css("background-color","#AAAAAA");
    			$("button.createOrderButton").attr("disabled","disabled");		
    		}
    			
    	}
    	function syncSelect(){
    		var selectAll = true;
    		$(".cartProductItemIfSelected").each(function(){
    			if("false"==$(this).attr("selectit")){
    				selectAll = false;
    			}
    		});
    		if(selectAll)
    			$("img.selectAllItem").attr("src","img/cartSelected.png");
    		else
    			$("img.selectAllItem").attr("src","img/cartNotSelected.png");
    	}

    });
    function syncSelect(){
    	var selectAll = true;
    	var sum = 0;
    	$(".cartProductItemIfSelected").each(function(){
    		if("false"==$(this).attr("selectit")){
    			selectAll = false;
    		}
    		sum+=1;
    	});
    	if(selectAll&&sum!=0)
    		$("img.selectAllItem").attr("src","img/cartSelected.png");
    	else
    		$("img.selectAllItem").attr("src","img/cartNotSelected.png");
    }
    function deleterow2(src,oiid) {

    	$("img.cartProductItemIfSelected[oiid="+oiid+"]").attr("selectit","false");
    	$("input[type=checkbox][name="+oiid+"]").prop("checked",false);
    	$(src).empty();
    	
    	src.parentElement.deleteRow(src.rowIndex-1);
    	var a=document.getElementById("table").rows.length;
    	calcCart();
    	syncSelect();
    	if(a==0){
    		$("#table").append("<tr><td>您的购物车没有商品，快去商城看看吧</td></tr>");
//    		document.getElementById("div").style.display=null;		
    	}	
    }

    function calcCart(){
    	var sum = 0;
    	var totalNumber = 0;
    	$("img.cartProductItemIfSelected[selectit='selectit']").each(function(){
    		var oiid = $(this).attr("oiid");
    		var price =$(".cartProductItemSmallSumPrice[oiid="+oiid+"]").text();
    		price = price.replace(/,/g, "");
    		price = price.replace(/￥/g, "");
    		sum += new Number(price);	
    		
    		var num =$(".orderItemNumberSetting[oiid="+oiid+"]").val();
    		totalNumber += new Number(num);	
    		
    	});

    	if(totalNumber==0){
    		$("span.cartSumNumber").html(0);
    		$("span.cartSumPrice").html("￥0.00");
    		return;
    	}
    	$("span.cartSumPrice").html("￥"+formatMoney(sum));
    	$("span.cartSumNumber").html(totalNumber);
    }
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