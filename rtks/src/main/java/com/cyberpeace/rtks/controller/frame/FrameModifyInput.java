package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架修改输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class FrameModifyInput extends FrameInput {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "框架id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "框架名称")
	@NotEmptyString
	private String name;

}
