package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateListRsp implements Serializable {

	@ApiModelProperty(value = "测试靶场模板id")
	private String resourceId;

	@ApiModelProperty(value = "测试名称")
	@NotNull
	private String name;

	@ApiModelProperty(value = "测试描述")
	private String description;

	@ApiModelProperty(value = "Logo文件路径")
	private String logo;

	@ApiModelProperty(value = "测试类型")
	private String categoryCode;

}
