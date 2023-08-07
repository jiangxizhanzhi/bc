package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateBaseInfoReq {

	@ApiModelProperty(value = "测评靶场模板id", required = false)
	private String resourceId;

	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "测评靶场描述", required = false)
	private String description;

	@ApiModelProperty(value = "测评靶场Logo文件id", required = true)
	private String logoId;

	@ApiModelProperty(value = "测评靶场类型", required = true)
	@NotNull
	private Integer categoryCode;

	@ApiModelProperty(value = "是否录屏", required = true)
	@NotNull
	private Boolean enableRecording;

	@ApiModelProperty(value = "完成红蓝训练id", required = true)
	@NotNull
	private String redVsBlueId;

}