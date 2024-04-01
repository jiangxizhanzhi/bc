package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ToolBo implements Serializable {

	@ApiModelProperty("工具名称")
	private String toolName;

	@ApiModelProperty("工具所在节点-节点下拉框信息中 带-的id值")
	private String node;

	@ApiModelProperty(value = "执行类型 自动执行-0 手动执行-1", required = true)
	@NotNull
	private Integer executeType;

	@ApiModelProperty("预定义动作")
	private String action;

	@ApiModelProperty("剩余时间")
	private int limitTime;

	@ApiModelProperty("是否手动执行")
	private Boolean isExecute = false;
}
