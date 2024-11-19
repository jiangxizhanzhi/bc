package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架修改输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see FrameInput
 * @since 1.0.0
 */
@Data
public class FrameModifyInput extends FrameInput {

	/**
	 * 框架id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架id")
	private String resourceId;

	/**
	 * 框架名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架名称")
	@NotEmptyString
	private String name;

}
