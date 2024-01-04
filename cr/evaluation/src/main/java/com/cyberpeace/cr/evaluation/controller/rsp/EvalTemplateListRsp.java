package com.cyberpeace.cr.evaluation.controller.rsp;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateListRsp {

	@ApiModelProperty(value = "测评靶场模板id")
	private String resourceId;

	@ApiModelProperty(value = "测评名称")
	@NotNull
	private String name;

	@ApiModelProperty("试验条件")
	private String condition;

	@ApiModelProperty("试验方法")
	private String method;

	@ApiModelProperty("任务方案附件")
	private String scheme;

	@ApiModelProperty(value = "测评描述")
	private String description;

	@ApiModelProperty(value = "Logo文件路径")
	private String logoPath;

	@ApiModelProperty(value = "Logo文件Id")
	private String logoId;

	@ApiModelProperty(value = "测评类型")
	private Integer categoryCode;

	public void setLogo(String logo) {
		this.logoId = logo;
	}

}
