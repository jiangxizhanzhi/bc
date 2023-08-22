package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateListRsp implements Serializable {

	@ApiModelProperty(value = "测评靶场模板id")
	private String resourceId;

	@ApiModelProperty(value = "测评名称")
	@NotNull
	private String name;

	@ApiModelProperty(value = "测评描述")
	private String description;

	@ApiModelProperty(value = "Logo文件路径")
	private String logoPath;

	@ApiModelProperty(value = "Logo文件Id")
	private String logoId;

	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	@ApiModelProperty(value = "测评类型")
	private Integer categoryCode;

	@ApiModelProperty(value = "红蓝训练id")
	private String redVsBlueId;

	public void setLogo(String logo) {
		this.logoId = logo;
	}

}
