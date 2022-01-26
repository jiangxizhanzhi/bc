package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BrokenLine implements Serializable {

  @ApiModelProperty("x轴，统计项")
  private List<String> xNames;

  @ApiModelProperty("y1轴，课程数值")
  private List<Integer> yFirstValues;

  @ApiModelProperty("y2轴，实验报告数值")
  private List<Integer> ySecondValues;

  @ApiModelProperty("y3轴，实验报告平均分数值")
  private List<Double> yThirdValues;
}
