function drawXY(elementId, xs, ys) {
  /**
   * 折线图
   * */
  var myChartStaffScore = echarts.init(document.getElementById(elementId), null, {renderer: 'svg'});
  myChartStaffScore.setOption({
      title: {
        show: !xs.length && !ys.length, // 无数据时展示 title
        textStyle: {
          color: '#fff',
          fontSize: 14
        },
        text: '暂无数据',
        left: 'center',
        top: 'center'
      },
      color: '#448AFF',
      animation: false,
      grid: {
          left: 20,
          right: 23,
          bottom: 5,
          top: 30,
          containLabel: true,
      },
      xAxis: {
          type: 'category',
          axisLabel: {
              color: '#616468',
              showMaxLabel: true, //显示最后一个x轴标签
              fontSize: 12,
              rotate: 45,
          },
          // x轴是否显示，默认不显示
          axisLine: {
              show: false,
              lineStyle: {
                  //x轴线的颜色设置 ，暂不支持修改
                  color: '#9facc7',
                  //延长x轴 配合y轴的offset
                  shadowOffsetX: 22,
                  shadowColor: '#9facc7',
              },
          },
          // x轴上的刻度是否显示，默认不显示
          axisTick: {
              show: false,
          },
          data: xs,
      },
      yAxis: {
          name: xs.length && ys.length ? '次': '',
          nameTextStyle: {
            color: '#616468',
            padding: [30, 0, 0, -30],
          },
          type: 'value',
          axisLabel: {
              color: '#616468',
              showMaxLabel: true, //显示最后一个x轴标签
              fontSize: 12,
          },
          // y轴分界线
          splitLine: {
              show: true,
              lineStyle: {
                  color: '#53647A',
                  type: 'soild',
              },
          },
      },
      series: [
          {
              data: ys,
              type: 'line',
              smooth: false,
              showSymbol: true,
          }
      ]
  })
}
