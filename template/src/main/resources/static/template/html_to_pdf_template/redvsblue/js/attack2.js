
  // 攻击趋势
  var attack2Chart = echarts.init(document.getElementById('attack2'));
  attack2Option={
    color: ['#0acff2'],
    title: {
      left: 'center',
      top: '3%',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.9)',
        fontWeight: 600,
        fontSize: 14,
      },
    },
    // 网格
    grid: {
      show: false,
      top: 50,
      left: 50,
      right: 16,
      bottom: 16,
      containLabel: true,
    },
    tooltip: {
      //鼠标移动到坐标轴触发时提示框是否有虚线
      trigger: 'axis',
      //背景颜色
      backgroundColor: '#131926',
      borderWidth: 0,
      show: true,
      //tooltip的文本颜色
      textStyle: {
        color: 'rgba(255, 255, 255, 0.9)',
      },
      //toolTip显示时,线的显示样式
      axisPointer: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.9)',
          width: 1,
          type: 'solid',
        },
      },
    },
    xAxis: {
      boundaryGap: false,
      // name: 's', // x轴名称
      data: [${sceneTrendTimeString}],
      // x轴名称样式
      nameTextStyle: {
        fontSize: 14,
        color: 'rgba(255, 255, 255, 0.9)',
        verticalAlign: 'top',
        padding: [6, 0, 0, 0],
      },
      // 坐标轴轴线
      axisLine: {
        show: true,
        lineStyle: {
          color: '',
        },
      },
      // 刻度线
      axisTick: {
        show: false,
      },
      // 坐标轴刻度标签
      axisLabel: {
        show: true,
        // interval: 20,
        fontSize: 12,
        color: 'rgba(255, 255, 255, 0.9)',
      },
    },
    yAxis: {
      name: 'kb',
      nameTextStyle: {
        fontSize: 12,
        color: 'rgba(255, 255, 255, 0.9)',
        padding: [0, 55, 0, 20],
      },
      type: 'value',
      // max: 600,
      minInterval: 1,
      // splitNumber: 4,
      // 坐标轴轴线
      axisLine: {
        show: false,
      },
      // grid区域分隔线
      splitLine: {
        show: true,
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.9)',
        },
      },
      axisLabel: {
        fontSize: 12,
        color: 'rgba(255, 255, 255, 0.9)',
      },
    },
    series: [
      {
        type: 'line',
        data: [${sceneTrendSizeString}],
        showSymbol: false,
        lineStyle: {
          width: 2,
          color: '#0acff2',
        },
        symbol: 'none',
        smooth: true,
        areaStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: 'rgba(10, 207, 242, 0.9)',
              },
              {
                offset: 1,
                color: 'rgba(10, 207, 242, 0)',
              },
            ],
            global: false, // 缺省为 false
          },
        },
      },
    ],
  }
  attack2Chart.setOption(attack2Option)