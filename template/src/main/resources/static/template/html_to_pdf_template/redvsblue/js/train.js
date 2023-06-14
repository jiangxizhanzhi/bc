 // 训练统计
 function train(elementId,successCount,failCount,allTaskCount,finishTaskCount) {
   var notFinishCount=allTaskCount-finishTaskCount;
   var element = document.getElementById(elementId);
   element.style.width = "100%";
   element.style.height = "100%";
   var myChart = echarts.init(element);

   option = {
     color: [
       '#36D94C',
       '#FF6652',
       '#F2A92A',
     ],

     graphic: [
       {
         type: 'text',
         left: 'center',
         top: '42%',
         style: {
           text: allTaskCount,
           fontSize: 24,
           fill: 'rgba(255,255,255,0.9)',
           textAlign: 'center',
         },
       },
       {
         type: 'text',
         left: 'center',
         top: '52%',
         style: {
           text: '任务总数',
           fontSize: 14,
           fill: 'rgba(255,255,255,0.9)',
           textAlign: 'center',
         },
       },
     ],
     series: [
       {
         name: 'Access From',
         type: 'pie',
         radius: ['43%', '56%'],
         center: ['50%', '50%'],
         avoidLabelOverlap: false,
         itemStyle: {
           borderColor: '#32405c',
           borderWidth: 3
         },
         label: {
           formatter: '{b|{b}：}{c}\n{per|{d}%}',
           minMargin: 5,
           edgeDistance: 30,
           color: 'rgba(255,255,255,0.9)',
           fontSize: 14,
           lineHeight: 18,
           rich: {
             b: {
               fontSize: 14,
               padding: [0, 0, 0, -25],
               color: 'rgba(255,255,255,0.9)',
             },
             per: {
               fontSize: 14,
               padding: [0, 20, 0, -20],
             },
           },
         },
         labelLine: {
           length: 30,
           length2: 40,
           maxSurfaceAngle: 80,
           lineStyle: {
             color: 'rgba(255,255,255,0.9)',
           },
         },
         //  labelLayout: (params) => {
         //    params.align = 'left';
         //    const isLeft = params.labelRect.x < myChart.getWidth() / 2;
         //    const points = params.labelLinePoints;
         //    points[2][0] = isLeft
         //      ? params.labelRect.x
         //      : params.labelRect.x + params.labelRect.width;
         //    return {
         //      labelLinePoints: points,
         //    };
         //  },
         data: [{'name': '成功', value: successCount}, {'name': '失败', 'value': failCount}, {'name':'未完成', 'value': notFinishCount}]
       }
     ]
   }
   myChart.setOption(option)
 }

