package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 层级输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see LevelInput
 * @since 1.0.0
 */
@Data
public class LevelModifyInput extends LevelInput {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 层级名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级名称")
	@NotEmptyString
	private String name;

	/**
	 * 层级的父级层级/框架的id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotEmptyString
	private String parent;

}
