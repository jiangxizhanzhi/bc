package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Level Modify 输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class LevelModifyInput extends LevelInput {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "层级名称")
	@NotEmptyString
	private String name;

	/**
	 * 父母
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotEmptyString
	private String parent;

}
