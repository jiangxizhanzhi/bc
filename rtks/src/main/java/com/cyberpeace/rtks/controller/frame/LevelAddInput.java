package com.cyberpeace.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 水平新增输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class LevelAddInput extends LevelInput {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "层级名称")
	@NotBlank
	private String name;

	/**
	 * 父母
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotBlank
	private String parent;
}
