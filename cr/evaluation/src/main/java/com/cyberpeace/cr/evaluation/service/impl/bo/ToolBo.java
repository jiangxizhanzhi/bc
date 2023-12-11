package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ToolBo implements Serializable {

	@ApiModelProperty("工具名称")
	private String toolName;

	@ApiModelProperty("工具所在节点")
	private String node;

	@ApiModelProperty(value = "执行类型", required = true)
	@NotNull
	private Integer executeType;

	@ApiModelProperty("预定义动作")
	private String actions;
}
