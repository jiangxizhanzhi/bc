package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 提交任务请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SubmitMissionsReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true, value = "用户id")
	@NotNull
	private String resourceId;

	/**
	 * 任务哈希
	 */
	@ApiModelProperty(required = true, value = "任务id")
	@NotNull
	private String missionHash;

	/**
	 * 答案
	 */
	@ApiModelProperty(required = true, value = "提交答案")
 	private String answer;

	/**
	 * 备注
	 */
	@ApiModelProperty(required = true, value = "备注")
	private String remark;

}
