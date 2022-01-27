function drawPie(elementId, labels, values, title, text) {
    /**
     * 任务分类和任务统计图
     * */
    var data = [];
    for (var i in labels) {
        data.push({name: labels[i] + "  " + values[i], value: values[i]});
    }
    var option = {
        color: ['#0bdaff', '#16d933', '#ff3045', '#f3900e', '#24a7ff'],
        animation: false,
        tooltip: {
            trigger: 'item'
        },
        title: {
            show: true,
            text: title,
            left: '24%',
            top: '80%',
            textAlign: 'center',
            textStyle: {
                color: '#C0CCE7',
                fontSize: 12,
                fontWeight: '700',
                fontFamily: 'PingFangSC-Regular,PingFang SC',
            },

        },
        legend: {
            left: '40%',
            top: 'center',

            orient: 'vertical',
            icon: 'circle',
            itemHeight: 12,
            textStyle: {
                color: '#C0CCE7',
                fontWeight: '700',
                fontFamily: 'PingFangSC-Regular,PingFang SC',
            },
        },
        graphic: {
            type: 'text',
            left: '42%',
            top: '20%',
            show: false,
            style: {
                text: text,
                textAlign: 'center',
                fill: '#C0CCE7',
                fontWeight: '700',
                fontSize: 14,
            }
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: ['40%', '55%'],
                center: ["25%", "50%"],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderColor: '#32405c',
                    borderWidth: 5
                },
                label: {
                    show: false,
                    position: 'center'
                },
                labelLine: {
                    show: false
                },
                data: data
            }
        ]
    };
    // echarts.init(document.getElementById(elementId)).setOption(option);
    echarts.init(document.getElementById(elementId), null, {renderer: 'svg'}).setOption(option);
}

function drawRing(elementId, value, total, text, color, locale_total_score, locale_score) {
    /**
     * 主观题和客观题得分比例图
     * elementId 容器id
     * value 得分
     * total 总分
     * text 标题
     * colors 得分部分颜色
     * */
    var label1 = locale_score + "  " + value;
    var label2 = locale_total_score + "  " + total;
    var data = [{name: label1, value: value}, {name: label2, value: total - value}];
    var optionObject = {
        color: [color, 'rgba(55, 99, 134, 1)'],
        animation: false,
        tooltip: {
            show: false,
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            x: 'center',
            y: '68%',
            icon: 'circle',
            itemWidth: 6, // 设置宽度
            itemHeight: 6, // 设置高度
            textStyle: {
                fontSize: 12,
                color: '#cad3e6',
                fontWeight: '700',
                fontFamily: 'PingFangSC-Regular,PingFang SC',
            },
            data: [label1, label2],
        },
        toolbox: {
            show: false,
        },
        graphic: {
            // 图形中间文字
            type: 'text',
            left: 'center',
            top: '60%',
            style: {
                text: text,
                textAlign: 'center',
                fill: '#cad3e6',
                fontSize: 12,
                fontWeight: '700',
                fontFamily: 'PingFangSC-Regular,PingFang SC',
            },
        },
        series: [
            {
                name: '',
                type: 'pie',
                radius: ['38%', '51%'],
                center: ['50%', '32%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center',
                    },
                },
                itemStyle: {
                    normal: {
                        borderWidth: 5,
                        borderColor: 'rgba(50, 64, 92, 1)',
                    },
                },
                labelLine: {
                    normal: {
                        show: false,
                    },
                },
                data: data
            }
        ],
    };
    echarts.init(document.getElementById(elementId), null, {renderer: 'svg'}).setOption(option);
    // echarts.init(document.getElementById(elementId)).setOption(optionObject);
}


function drawPersonalTaskStatusRing(elementId, labels, values, locale_tasks) {
    /**
     * 个人训练 已完成和进行中饼图
     * */
    var data = [];
    var total = 0;
    for (var index in labels){
        data.push({name: labels[index] + " " + values[index], value: values[index]});
        total = total + values[index];
    }
    var title = total + " " + locale_tasks;
    var option = {
        color: [
            '#36D94C',
            '#FDA939',
            'rgba(11, 218, 255, 1)',
        ],
        animation: false,
        tooltip: {
            trigger: 'item'
        },
        legend: {
            left: '55%',
            top: 'center',
            borderColor: 'rgba(50, 64, 92, 1)',

            orient: 'vertical',
            icon: 'circle',
            itemHeight: 12,
            textStyle: {
                color: '#C0CCE7',
                fontWeight: '700',
                fontFamily: 'PingFangSC-Regular,PingFang SC',
            }
        },
        graphic: {
            type: 'text',
            left: '56%',
            top: '25%',
            style: {
                text: title,
                textAlign: 'center',
                fill: '#C0CCE7',
                fontSize: 14,
                fontWeight: '700',
                fontFamily: 'PingFangSC-Regular,PingFang SC',
            },
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: ['40%', '55%'],
                center: ["40%", "50%"],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderColor: '#32405c',
                    borderWidth: 5
                },
                label: {
                    show: false,
                    position: 'center'
                },
                labelLine: {
                    show: false
                },
                data: data
            }
        ]
    };
    echarts.init(document.getElementById(elementId), null, {renderer: 'svg'}).setOption(option);
    // echarts.init(document.getElementById(elementId)).setOption(option);
}

function drawRadar(elementId, values, labels) {
    var indicator = [];
    for (var index in labels){
      if(index == 0){
        indicator.push({name: labels[index], max: 100,              
              axisLabel: {
                align: 'center',
                verticalAlign: 'top',
                show: true,
                fontSize: 14,
                color: '#B8C4DF',
                showMaxLabel: true, //不显示最大值，即外圈不显示数字30
                showMinLabel: true, //显示最小数字，即中心点显示0
              },});
      }else{
         indicator.push({name: labels[index], max: 100});
      }
       
    }
    var option = {
        legend: {
            show: false
        },
        animation: false,
        radar: {
            // 标签的颜色
            name: {
                textStyle: {
                    color: '#C0CCE7',
                    fontFamily: 'PingFangSC-Regular,PingFang SC',
                },
            },
            // shape: 'circle',
            indicator: indicator,
            // 雷达图大小
            radius: '65%',
            // 分割线
            splitLine: {
                lineStyle: {
                    width: 1,
                    color: [
                        'rgba(36, 167, 255,0.5)',
                        'rgba(36, 167, 255,0.5)',
                        'rgba(36, 167, 255,0.5)',
                        'rgba(36, 167, 255,0.5)',
                        'rgba(36, 167, 255,0.5)',
                        'rgba(36, 167, 255,0.5)',
                        'rgba(36, 167, 255,1)',
                    ],
                },
            },
            //轴线设置
            axisLine: {
                lineStyle: {
                    color: 'rgba(36, 167, 255,0.5)',
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
                    color: 'rgba(1,255,255,0.7)',
                },
                //lineStyle 颜色要和区域颜色一样
                lineStyle: {
                    color: 'rgba(1,255,255,0.7)',
                    width: 1,
                },
                data: [
                    {
                        value: values,
                    }
                ]
            }
        ]
    }
    // echarts.init(document.getElementById(elementId)).setOption(option);
    echarts.init(document.getElementById(elementId), null, {renderer: 'svg'}).setOption(option);
}

function drawXY(elementId, xs, ys) {
    /**
     * 人员得分折线图
     * */

    var myChartStaffScore = echarts.init(document.getElementById(elementId), null, {renderer: 'svg'});
    myChartStaffScore.setOption({
        color: '#0BDAFF',
        animation: false,
        grid: {
            left: 20,
            right: 23,
            bottom: 5,
            top: 20,
            containLabel: true,
        },
        xAxis: {
            type: 'category',
            axisLabel: {
                color: '#9facc7',
                showMaxLabel: true, //显示最后一个x轴标签
                fontSize: 14,
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
            type: 'value',
            max: 100,
            axisLabel: {
                color: '#9facc7',
                showMaxLabel: true, //显示最后一个x轴标签
                fontSize: 14,
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
                smooth: true,
                showSymbol: false,
            }
        ]
    })
}
