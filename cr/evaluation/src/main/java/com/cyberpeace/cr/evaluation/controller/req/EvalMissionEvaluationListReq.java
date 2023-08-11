package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class EvalMissionEvaluationListReq extends PageReqBase {

	@NotNull
	@ApiModelProperty(value = "测评模版ID")
	private String evalTemplateId;

	@ApiModelProperty(value = "学员ID")
	private String submitUser;

	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;
}
