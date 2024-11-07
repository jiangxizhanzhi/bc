package com.cyberpeace.cr.redvsblue.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB任务评估列表请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@ApiModel
@Data
public class RbMissionEvaluationListReq extends PageReqBase {

	/**
	 * 房间id
	 */
	@NotNull
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 提交用户
	 */
	@ApiModelProperty(value = "学员ID")
	private String submitUser;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;
}
