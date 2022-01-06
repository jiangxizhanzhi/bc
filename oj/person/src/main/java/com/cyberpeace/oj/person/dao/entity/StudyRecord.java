package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StudyRecord implements Serializable {

  @ApiModelProperty("完成课程数")
  private Integer courseCount;

  @ApiModelProperty("理论课时数")
  private Integer theoryCount;

  @ApiModelProperty("实验课时数")
  private Integer ExpCount;

  @ApiModelProperty("学习时长")
  private String duration;

  @ApiModelProperty("完成练习数")
  private Integer practiceFinishCount;

  @ApiModelProperty("完成报告数")
  private Integer reportCount;

  private Integer score;

  @ApiModelProperty("练习排名")
  private Integer practiceRanking;

  private String userId;

  private String record;
}
