package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionEvaluationQuery extends PageReqBase {

	@ApiModelProperty(value = "房间ID")
	private String roomId;

	@ApiModelProperty(value = "学员ID")
	private String studentHash;

	@ApiModelProperty(value = "评估方式")
	private String evaluateType;

	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

}
