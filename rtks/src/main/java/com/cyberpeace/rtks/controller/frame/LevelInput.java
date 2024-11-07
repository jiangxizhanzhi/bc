package com.cyberpeace.rtks.controller.frame;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电平输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class LevelInput implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "层级名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "层级描述")
	private String description;

	/**
	 * 父母
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

}
