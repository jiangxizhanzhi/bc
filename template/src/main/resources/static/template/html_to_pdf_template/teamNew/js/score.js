// 得分趋势
var myChart = echarts.init(document.getElementById('score'));
option= {
  color: [
    '#25c1d9',
    '#448aff',
    '#36d94c',
    '#ff7d2c',
    '#f2a92a',
    '#ff6652',
    '#1f606d',
    '#2b4a7c',
    '#266a34',
    '#764528',
    '#715727',
    '#763c37',
  ],
  legend: {
    show: true,
    right: 10,
    top: 10,
    itemHeight: 0,
    orient: 'vertical',
    textStyle: {
      color: 'rgba(255, 255, 255, 0.7)',
      fontSize: 14,
    },
    //暂不支持设置data，显示legend时，series的name必须设置
    // data: [],
  },
  // 网格
  grid: {
    show: false,
    top: 40,
    left: 50,
    right: 150,
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
        color: 'rgba(255, 255, 255, 0.3)',
        width: 1,
        type: 'solid',
      },
    },
  },
  xAxis: {
    boundaryGap: false,
    // name: 's', // x轴名称
    data: [${timeListString}],
    // x轴名称样式
    nameTextStyle: {
      fontSize: 14,
      color: 'rgba(255, 255, 255, 0.4)',
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
      // interval: 0,
      fontSize: 12,
      color: 'rgba(255, 255, 255, 0.4)',
    },
  },
  yAxis: {
    name: '分',
    nameTextStyle: {
      fontSize: 12,
      color: 'rgba(255, 255, 255, 0.4)',
      padding: [0, 45, 10, 20],
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
        color: 'rgba(255, 255, 255, 0.05)',
      },
    },
    axisLabel: {
      fontSize: 12,
      color: 'rgba(255, 255, 255, 0.4)',
    },
  },
  series: [
    ${teamTrendsString}
  ],
}
myChart.setOption(option)