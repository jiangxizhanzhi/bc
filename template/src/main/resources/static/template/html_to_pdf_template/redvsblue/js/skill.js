function skillScore(elementId,skillNames,skillScores) {
  // 技能评分图
  var element = document.getElementById(elementId);
  element.style.width = "100%";
  element.style.height = "100%";
  var myChart1 = echarts.init(element);
  option1 = {
    legend: {
      show: false
    },
    radar: {
      // 标签的颜色
      name: {
        textStyle: {
          color: '#C0CCE7',
          fontFamily: 'PingFangSC-Regular,PingFang SC',
        },
      },
      // shape: 'circle',
      indicator: skillNames,
      // 雷达图大小
      radius: '65%',
      // 分割线
      splitLine: {
        lineStyle: {
          width: 1,
          color: [
            'rgba(255, 255, 255,0.2)',
            'rgba(255, 255, 255,0.2)',
            'rgba(255, 255, 255,0.2)',
            'rgba(255, 255, 255,0.2)',
            'rgba(255, 255, 255,0.2)',
            'rgba(255, 255, 255,0.2)',
            'rgba(255, 255, 255,1)',
          ],
        },
      },
      //轴线设置
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255,0.2)',
        },
      },
      //分隔区域设置
      splitArea: {
        areaStyle: {
          color: 'transparent',
        },
      },
    },
    series: [
      {
        name: 'Budget vs spending',
        type: 'radar',
        //标记图形的形状
        symbol: 'none',
        //区域颜色,透明度
        areaStyle: {
          color: '#196E89',
        },
        //lineStyle 颜色要和区域颜色一样
        lineStyle: {
          color: '#0ACFF2',
          width: 1,
        },
        data: [
          {
            value: skillScores,
          }
        ]
      }
    ]
  }

  myChart1.setOption(option1)

}
