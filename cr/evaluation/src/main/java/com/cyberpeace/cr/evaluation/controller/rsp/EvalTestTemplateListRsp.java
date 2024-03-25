package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTestTemplateListRsp implements Serializable {

	@ApiModelProperty("模板id")
	private String templateId;

	@ApiModelProperty("模板名称")
	private String templateName;

	@ApiModelProperty("时长")
	private Integer time;

	@ApiModelProperty("节点数量")
	private Integer nodeCount;

	@ApiModelProperty("类型")
	private Integer type;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("验证目标")
	private String target;
}
