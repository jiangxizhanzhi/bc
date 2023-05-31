package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EvalIndexRefReq {

	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotEmpty
	private String resourceId;

	@ApiModelProperty("等级")
	private Integer level;

	@ApiModelProperty("指标项专家评分配置")
	private EvalRecordSpecialistContext specialist;

	@ApiModelProperty("评分方式 1系统 2 专家")
	private Integer type;

	@ApiModelProperty("系统评分时 需要节点id")
	private String deviceId;

	@ApiModelProperty("系统评分时 需要探针id")
	private String probeId;



}