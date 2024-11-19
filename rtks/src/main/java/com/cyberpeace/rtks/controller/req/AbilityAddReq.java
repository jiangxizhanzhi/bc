package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 能力新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class AbilityAddReq implements Serializable {

	/**
	 * 能力id
	 *
	 * @see String
	 */
	@ApiModelProperty("能力id")
	private String resourceId;

	/**
	 * 能力名称
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty("能力名称")
	private String content;
}
