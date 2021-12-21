package com.cyberpeace.cr.redvsblue.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class RbMissionEvaluationListReq extends PageReqBase {

	@NotNull
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	@ApiModelProperty(value = "学员ID")
	private String submitUser;

	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;
}
