package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalSystemTreeRsp {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "ID")
	private String resourceId;

	@ApiModelProperty(value = "评估指标ID")
	private String evalIndicatorId;

	@ApiModelProperty(value = "级别")
	private Integer level;

	@ApiModelProperty(value = "3级指标项评分类型")
	private Integer type;

	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemTreeRsp> children;

}