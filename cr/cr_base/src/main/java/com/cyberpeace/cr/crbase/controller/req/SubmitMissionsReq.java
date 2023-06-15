package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitMissionsReq {

	@ApiModelProperty(required = true, value = "用户id")
	@NotNull
	private String resourceId;

	@ApiModelProperty(required = true, value = "任务id")
	@NotNull
	private String missionHash;

	@ApiModelProperty(required = true, value = "提交答案")
 	private String answer;

	@ApiModelProperty(required = true, value = "备注")
	private String remark;

}
