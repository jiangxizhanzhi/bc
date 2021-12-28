package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainInfo implements Serializable {

  @ApiModelProperty("")
  private String appId;

  @ApiModelProperty("")
  private String appName;

  @ApiModelProperty("训练数")
  private Integer count;
}
