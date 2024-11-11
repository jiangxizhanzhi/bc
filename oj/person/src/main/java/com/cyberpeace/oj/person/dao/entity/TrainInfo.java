package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 训练信息
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class TrainInfo implements Serializable {

  /**
   * 应用程序id
   */
  @ApiModelProperty("")
  private String appId;

  /**
   * 应用程序名称
   */
  @ApiModelProperty("")
  private String appName;

  /**
   * 计数
   */
  @ApiModelProperty("训练数")
  private Integer count;
}
