package com.cyberpeace.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架新增输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class FrameAddInput extends FrameInput {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "框架名称")
	@NotBlank
	private String name;

}
