package com.cyberpeace.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架新增输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see FrameInput
 * @since 1.0.0
 */
@Data
public class FrameAddInput extends FrameInput {

	/**
	 * 框架名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架名称")
	@NotBlank
	private String name;

}
