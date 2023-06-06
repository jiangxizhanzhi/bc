

  // 任务分类
  var taskChart = echarts.init(document.getElementById('task'));
  taskOption = {
    color: [
      '#36D94C',
      '#FF6652',
      '#F2A92A',
      '#0ACFF2',
      '#448AFF'
    ],

    graphic: [
      {
        type: 'text',
        left: 'center',
        top: '42%',
        style: {
          text: ${kindMap?size},
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
          text: '任务分类',
          fontSize: 14,
          fill: 'rgba(255,255,255,0.7)',
          textAlign: 'center',
        },
      },
    ],
    series: [
      {
        type: 'pie',
        radius: ['43%', '56%'],
        center: ['50%', '50%'],
        avoidLabelOverlap: true,
        itemStyle: {
          borderColor: '#32405c',
          borderWidth: 3
        },
        label: {
          formatter: '{b|{b}}\n{per|{c}}',
          minMargin: 5,
          edgeDistance: 30,
          color: 'rgba(255,255,255,0.7)',
          fontSize: 14,
          lineHeight: 18,
          distanceToLabelLine: 0, // label距离引导线距离
          rich: {
            b: {
              fontSize: 14,
              padding: [0, 0, 0, -25],
              color: 'rgba(255,255,255,0.7)',
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
            color: 'rgba(255,255,255,0.2)',
          },
        },
        // labelLayout: (params) => {
        //   params.align = 'left';
        //   const isLeft = params.labelRect.x < myChart.getWidth() / 2;
        //   const points = params.labelLinePoints;
        //   points[2][0] = isLeft
        //     ? params.labelRect.x
        //     : params.labelRect.x + params.labelRect.width;
        //   return {
        //     labelLinePoints: points,
        //   };
        // },
        data: [
          ${kindMapString}
        ]
      }
    ]
  }

      taskChart.setOption(taskOption)
