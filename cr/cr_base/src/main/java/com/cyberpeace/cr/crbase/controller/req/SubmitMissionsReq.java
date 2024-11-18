package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提交任务请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class SubmitMissionsReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true, value = "用户id")
	@NotNull
	private String resourceId;

	/**
	 * 任务哈希
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true, value = "任务id")
	@NotNull
	private String missionHash;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true, value = "提交答案")
 	private String answer;

	/**
	 * 备注
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true, value = "备注")
	private String remark;

}
