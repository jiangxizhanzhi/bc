package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class TaskAddReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@NotBlank
	@ApiModelProperty("名称")
	private String content;
}
