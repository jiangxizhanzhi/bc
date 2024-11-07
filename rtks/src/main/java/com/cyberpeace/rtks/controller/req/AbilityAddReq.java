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
 * @date 2024/11/07
 */
@Data
@ApiModel
public class AbilityAddReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("能力id")
	private String resourceId;

	/**
	 * 内容
	 */
	@NotBlank
	@ApiModelProperty("能力名称")
	private String content;
}
