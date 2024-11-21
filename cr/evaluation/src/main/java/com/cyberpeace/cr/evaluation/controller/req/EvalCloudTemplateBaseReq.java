package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalCloudTemplateBaseReq implements Serializable {

	@ApiModelProperty("试验名称")
	private String name;

	@ApiModelProperty("试验目标")
	private String target;

	@ApiModelProperty("封面url")
	private String logo;

	@ApiModelProperty("试验类型")
	private Integer type;

	@ApiModelProperty("计划时长")
	private Integer time;

	@ApiModelProperty("试验难度")
	private Integer difficulty;

	@ApiModelProperty("云类型")
	private Integer cloudType;

	@ApiModelProperty(value = "拓扑LOGO")
	private String topo;


}
