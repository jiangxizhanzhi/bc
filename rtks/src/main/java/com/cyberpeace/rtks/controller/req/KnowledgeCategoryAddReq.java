package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class KnowledgeCategoryAddReq implements Serializable {

	/**
	 * 知识点类别id
	 */
	@ApiModelProperty("知识点类别id")
	private String resourceId;

	/**
	 * 知识点类别名称
	 */
	@NotBlank
	@ApiModelProperty("知识点类别名称")
	private String content;
}
