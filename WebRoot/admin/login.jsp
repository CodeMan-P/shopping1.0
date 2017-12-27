<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>webcam</title>
<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
<script src="js/cascade.js"></script>
<script src="js/ccv.js"></script>
<script src="js/jquery.facedetection.js"></script>
	
     <style>
        .video-container {
            position: relative;
            width: 260px;
            height: auto;
            margin: 20px auto;
            border: 10px solid #fff;
            box-shadow: 0 5px 5px #000;
        }
            .video {
                display: block;
                width: 100%;
                height: auto;
            }

        .face {
            position: absolute;
            border: 2px solid #FFF;
        }
         .picture-container {
            position: relative;
            width: 600px;
            height: auto;
            margin: 20px auto;
            border: 10px solid #fff;
            box-shadow: 0 5px 5px #000;
        }
            .picture {
                display: block;
                width: 100%;
                height: auto;
            }
    </style>
    
    <script type="text/javascript">
     window.onload = function (){
         try{
             //动态创建一个canvas元 ，并获取他2Dcontext。如果出现异常则表示不支持
             document.createElement("canvas").getContext("2d");
             document.getElementById("support").innerHTML = "浏览器支持HTML5 CANVAS";
         }catch(e){
             document.getElementById("support").innerHTML = "浏览器不支持HTML5 CANVAS";
         }
     };

     //这段代 主要是获取摄像头的视频流并显示在Video 签中
     window.addEventListener("DOMContentLoaded", function () {
         var video = document.getElementById("video");
         var videoObj = { "video": true };
         var errBack = function (error){
                 console.log("Video capture error: " + error.message, error.code);
             };
         //  支持浏览器  谷歌,火狐,360,欧朋
         //navigator.getUserMedia这个写法在Opera中好像是navigator.getUserMedianow
         if (navigator.getUserMedia) {
             navigator.getUserMedia(videoObj, function (stream) {
                 video.src = stream;
                 video.play();
             }, errBack);
         } else if (navigator.webkitGetUserMedia) {
             navigator.webkitGetUserMedia(videoObj, function (stream) {
                 video.src = window.URL.createObjectURL(stream);
                 video.play();
             }, errBack);
         } else if (navigator.mozGetUserMedia){
        	 //navigator.mediaDevices.getUserMedia
             navigator.mozGetUserMedia(videoObj, function (stream) {
                      video.src = window.URL.createObjectURL(stream);
                     video.play();
             }, errBack);
         }
         
         
         //这个是拍照按钮的事件，
         document.getElementById("snap").addEventListener("click",function(){
                 CatchCode();
                 //alert("123");
                 
                
                 
                 //setInterval(detect,100);
         });
     }, false);
     //定时器
     //var interval = setInterval(CatchCode, "300");
     //这个是 刷新上 图像的
     function CatchCode() {
         //实际运用可不写，测试代 ， 为单击拍照按钮就获取了当前图像，有其他用途
         var canvans = document.getElementById("canvas");
         var video = document.getElementById("video");
         var context = canvas.getContext("2d");

         canvas.width = video.videoWidth;
         canvas.height = video.videoHeight;
         context.drawImage(video,0,0);
         
         //获取浏览器页面的画布对象
         //以下开始编 数据
         var imgData = canvans.toDataURL("image/jpg");
         //将图像转换为base64数据
         var base64Data = imgData.split(",")[1];
         var imagedata = context.getImageData(0, 0, 200,200);
         //var xhr = new XMLHttpRequest();
         //xhr.open("post", "${ctx}/webcam.do", true);
         //告诉服务器是以个Ajax 请求
       //  xhr.setRequestHeader("X-Requested-Width", "XMLHttpRequest");
      //   var fd = new FormData();
      //   fd.append("doc",base64Data);
      //   xhr.send(fd);
      
        // $("#picture").css("src",imagedata);
        // var imgSrc = document.getElementById("canvasId").toDataURL("image/png");  
         document.getElementById("picture").src = imgData;  
        // alert(base64Data);
        
		
         //在前端截取22位之后的字符串作为图像数据
         //开始异步上
     }
     var detect = function () {
         $('.face').remove();

      

         $('#video').faceDetection({
             interval: 1,
             complete: function (faces) {
                 for (var i = 0; i < faces.length; i++) {
                     $('<div>', {
                         'class':'face',
                         'css': {
                             'position': 'absolute',
                             'left':     faces[i].x + 'px',
                             'top':      faces[i].y + 'px',
                             'width':    faces[i].width  + 'px',
                             'height':   faces[i].height + 'px'
                         }
                     })
                     .insertAfter(this);
                 }
        
             },
             error:function (code, message) {
                 alert('Error: ' + message);
             }
         });
     }
     setInterval(detect,1000);
    </script>
</head>
    <body>
        <input type="file" capture="camera" accept="image/*" id="cameraInput" name="cameraInput"  class="sign_file"/>
        
        <div id="support"></div>
         <video id="video" width="800" src="camera" capture="camera" height="600" style="border:1px solid red" autoplay></video>
         <hr/>
        <div id="contentHolder">       
                  
            <button id="snap"> 拍照</button>        
            <canvas style="border:1px solid red" id="canvas"></canvas> 
             
        </div>
         <div class="picture-container">
        <img id="picture" class="picture" onload="" alt="#" src="">
    </div>
        <div style="width:800px;height:300xp" id="base64">111111</div>
        
    </body>
</html>