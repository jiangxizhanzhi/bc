// 客观题
function object(elementId,score,allscore) {
  var element = document.getElementById(elementId);
  element.style.width = "100%";
  element.style.height = "100%";
  var myChart = echarts.init(element);
  option = {

    "color": ["#0ACFF2", "#2D4E6A"],
    "tooltip": {
      "show": false
    },
    "legend": {
      "type": "scroll",
      "orient": "vertical",
      "x": "center",
      "y": "68%",
      "icon": "circle",
      "itemWidth": 6,
      "itemHeight": 6,
      "textStyle": {
        "fontSize": 12,
        "color": "rgba(255, 255, 255, 0.9)"
      },
      "data": ["得分 "+score, "总分 "+allscore]
    },
    "toolbox": {
      "show": false
    },
    "graphic": {
      "type": "text",
      "left": "center",
      "top": "62%",
      "style": {
        "text": "客观题",
        "textAlign": "center",
        "fill": "rgba(255, 255, 255, 0.9)",
        "fontSize": 12
      }
    },
    "series": [{
      "name": "客观题",
      "type": "pie",
      "radius": ["38%", "51%"],
      "center": ["50%", "32%"],
      "avoidLabelOverlap": false,
      "label": {
        "normal": {
          "show": false,
          "position": "center"
        }
      },
      "itemStyle": {},
      "labelLine": {
        "normal": {
          "show": false
        }
      },
      "data": [{
        "value": score,
        "name": "得分 "+score
      }, {
        "value": allscore-score,
        "name": "总分 "+allscore
      }]
    }, {
      "name": "",
      "type": "pie",
      "clockWise": false,
      "radius": ["32%", "32%"],
      "center": ["50%", "32%"],
      "hoverAnimation": false,
      "itemStyle": {
        "normal": {
          "labelLine": {
            "show": false
          }
        }
      },
      "data": [{
        "value": 60,
        "name": "",
        "itemStyle": {}
      }, {
        "value": 4,
        "name": ""
      }, {
        "value": 60,
        "name": "",
        "itemStyle": {}
      }, {
        "value": 4,
        "name": ""
      }, {
        "value": 60,
        "name": "",
        "itemStyle": {}
      }, {
        "value": 4,
        "name": ""
      }, {
        "value": 60,
        "name": "",
        "itemStyle": {}
      }, {
        "value": 4,
        "name": ""
      }]
    }]
  }

  myChart.setOption(option)
}
