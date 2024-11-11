package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 虚线
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class BrokenLine implements Serializable {

  /**
   * x 个名称
   */
  @ApiModelProperty("x轴，统计项")
  private List<String> xNames;

  /**
   * y 第一个值
   */
  @ApiModelProperty("y1轴，课程数值")
  private List<Integer> yFirstValues;

  /**
   * y 秒值
   */
  @ApiModelProperty("y2轴，实验报告数值")
  private List<Integer> ySecondValues;

  /**
   * y 第三个值
   */
  @ApiModelProperty("y3轴，实验报告平均分数值")
  private List<Double> yThirdValues;
}
