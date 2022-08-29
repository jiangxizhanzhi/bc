package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class KnowledgeCategoryAddReq implements Serializable {

	@ApiModelProperty("知识点类别id")
	private String resourceId;

	@NotBlank
	@ApiModelProperty("知识点类别名称")
	private String content;
}
