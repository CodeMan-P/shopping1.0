<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/admin/echarts/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
  
  <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
 <div id="main_regDate${param.flag}" style="width: 100%;height:100%;"></div>
 
 <script type="text/javascript"> 
 var myChart = echarts.init(document.getElementById('main_regDate'+${param.flag}));
var year = ${param.flag};
//console.log(year);
$.getJSON('../erg?flag=dateInfo&&year='+year, function (data) {
	var dataInfo = data;
	//console.log(dataInfo);
	
	 function getArrayData() {
    var data = [];
    for (var i=0;i<dataInfo.length;i++) {
        data.push([
            dataInfo[i].regDate,
			//echarts.format.formatTime('yyyy-MM-dd', time),
            dataInfo[i].value,
        ]);
    }
    return data;
}
	var datas = getArrayData();
	var size = 3.5;//标记大小系数
	//console.log(datas);
option = {
    backgroundColor: '#404a59',

    title: {
        top: 30,
        text: year+'年注册日期分布',
        subtext: '数据纯属虚构',
        left: 'center',
        textStyle: {
            color: '#fff'
        }
    },
    tooltip : {
        trigger: 'item'
    },
    legend: {
        top: '30',
        left: '100',
        data:['人数', 'Top 12'],
        textStyle: {
            color: '#fff'
        }
    },
    calendar:[{
		top: 100,
        left: 'center',
        range: [year+'-01-01', year+'-06-30'],
        splitLine: {
            show: true,
            lineStyle: {
                color: '#000',
                width: 4,
                type: 'solid'
            }
        },
        yearLabel: {
            formatter: '{start}  1st',
            textStyle: {
                color: '#fff'
            }
        },
        itemStyle: {
            normal: {
                color: '#323c48',
                borderWidth: 1,
                borderColor: '#111'
            }
        }
	},{	top: 340,
        left: 'center',
        range: [year+'-07-01', year+'-12-31'],
        splitLine: {
            show: true,
            lineStyle: {
                color: '#000',
                width: 4,
                type: 'solid'
            }
        },
        yearLabel: {
            formatter: '{start}  2nd',
            textStyle: {
                color: '#fff'
            }
        },
        itemStyle: {
            normal: {
                color: '#323c48',
                borderWidth: 1,
                borderColor: '#111'
            }
        }
	}],
    series : [
        {
            name: '人数',
            type: 'scatter',
            coordinateSystem: 'calendar',
            dimensions: ['日期',  '注册用户数'],
            data: datas,
            symbolSize: function (val) {
                return val[1] *size;
            },
            itemStyle: {
                normal: {
                    color: '#ddb926'
                }
            }
        },
        {
            name: '人数',
            type: 'scatter',
            dimensions: ['日期',  '注册用户数'],
            coordinateSystem: 'calendar',
            calendarIndex: 1,
            data: datas,
            symbolSize: function (val) {
                return val[1] *size;
            },
            itemStyle: {
                normal: {
                    color: '#ddb926'
                }
            }
        },
        {
            name: 'Top 12',
            type: 'effectScatter',
            coordinateSystem: 'calendar',
            dimensions: ['日期',  '注册用户数'],
            calendarIndex: 1,
            data: datas.sort(function (a, b) {
                return b[1] - a[1];
            }).slice(0, 12),
            symbolSize: function (val) {
                return val[1] *size;
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            itemStyle: {
                normal: {
                    color: '#f4e925',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            zlevel: 1
        },
        {
            name: 'Top 12',
            type: 'effectScatter',
            coordinateSystem: 'calendar',
            dimensions: ['日期',  '注册用户数'],
            data: datas.sort(function (a, b) {
                return b[1] - a[1];
            }).slice(0, 12),
            symbolSize: function (val) {
                return val[1] *size;
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            itemStyle: {
                normal: {
                    color: '#f4e925',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            zlevel: 1
        }
    ]
};

 myChart.setOption(option);
});
	 
 </script> 
  
  </body>
</html>
