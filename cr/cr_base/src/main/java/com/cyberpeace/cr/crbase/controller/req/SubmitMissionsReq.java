package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提交任务请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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
}
