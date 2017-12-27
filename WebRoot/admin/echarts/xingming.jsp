<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
  
  <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
 <div id="main_${param.flag}" style="width: 100%;height:100%;"></div>
 
 <script type="text/javascript"> 
 
 var myChart = echarts.init(document.getElementById("main_${param.flag}"));
$.getJSON('../erg?flag=${param.flag}&&sum=200', function (data) {
	 var maskImage = new Image();  
     //var world_option = world_cloud(eval('(' +  '${cloud}' + ')'),maskImage);    
     maskImage.src = './echarts/xm4.png';  
//maskImage.onload=function(){
myChart.setOption({
graphic: [
{ // 一个图形元素，类型是 image。
 type: 'image',
	left:'center',
	style:{
		image:'./echarts/xm4.png',
		opacity:0.1,
		width:$("#main_${param.flag}").width(),
		height:$("#main_${param.flag}").height(),
		shadowBlur:10,
		shadowColor:'blue'
	},
}],
		
title: {
text: '词云'
},
tooltip: {
show: true
},
series: [{
name:<%="${param.flag}".equals("mingInfo")?"'名'":"'姓'"%>,
type: 'wordCloud',
z:100,
// The shape of the "cloud" to draw. Can be any polar equation represented as a
// callback function, or a keyword present. Available presents are circle (default),
// cardioid (apple or heart shape curve, the most known polar equation), diamond (
// alias of square), triangle-forward, triangle, (alias of triangle-upright, pentagon, and star.

shape: 'star',

// A silhouette image which the white area will be excluded from drawing texts.
// The shape option will continue to apply as the shape of the cloud to grow.

//maskImage: maskImage,

// Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
// Default to be put in the center and has 75% x 80% size.

left: 'center',
top: 'center',
width: '100%',
height: '100%',
right: null,
bottom: null,
// Text size range which the value in data will be mapped to.
// Default to have minimum 12px and maximum 60px size.

sizeRange: <%="${param.flag}".equals("mingInfo")?"[2, 50]":"[12, 60]"%>,
// Text rotation range and step in degree. Text will be rotated randomly in range [-90, 90] by rotationStep 45

rotationRange: [-90, 90],
rotationStep: 45,

// size of the grid in pixels for marking the availability of the canvas
// the larger the grid size, the bigger the gap between words.

gridSize: 8,

// set to true to allow word being draw partly outside of the canvas.
// Allow word bigger than the size of the canvas to be drawn
drawOutOfBound: false,

// Global text style
textStyle: {
 normal: {
     fontFamily: 'sans-serif',
     fontWeight: 'bold',
     // Color can be a callback function or a color string
     color: function () {
         // Random color
         return 'rgb(' + [
             Math.round(Math.random() * 160),
             Math.round(Math.random() * 160),
             Math.round(Math.random() * 160)
         ].join(',') + ')';
     }
 },
 emphasis: {
     shadowBlur: 10,
     shadowColor: '#333'
 }
},

// Data is an array. Each array item must have name and value property.
data:data
	}]
});
//}
	//myChart.setOption(option);
});
 

	 
 </script> 
  
  </body>
</html>
