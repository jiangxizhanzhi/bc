package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalSituationReq implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty(required = true, value = "事件名称")
	private String name;

	@ApiModelProperty(required = true, value = "攻击类型级别")
	private Integer attackType;

	@ApiModelProperty(required = true, value = "攻击源节点id")
	private String sourceId;

	@ApiModelProperty(required = true, value = "攻击目标节点id")
	private String targetId;

	@ApiModelProperty(required = true, value = "攻击结果")
	private Integer result;

}
