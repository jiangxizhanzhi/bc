package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Person 集合
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PersonCollection implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
  private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
  private String name;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
  private String createTime;

	/**
	 * 资源类型
	 */
	@ApiModelProperty("资源类型")
  private String businessType;

	/**
	 * 题型
	 */
	@ApiModelProperty("题型")
  private Integer practiceType;
}
