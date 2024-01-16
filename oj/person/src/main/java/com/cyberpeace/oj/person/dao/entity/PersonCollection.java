package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonCollection implements Serializable {

  @ApiModelProperty("资源编号")
  private String resourceId;

  @ApiModelProperty("名称")
  private String name;

  @ApiModelProperty("创建时间")
  private String createTime;

  @ApiModelProperty("资源类型")
  private String businessType;

  @ApiModelProperty("题型")
  private Integer practiceType;

  @ApiModelProperty("比赛ID")
  private String raceId;

}
