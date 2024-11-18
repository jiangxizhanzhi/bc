package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评估列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
@Data
public class MissionEvaluationListReq extends PageReqBase {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@NotNull
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 学员散 列
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "学员ID")
	private String studentHash;

	/**
	 * 评估类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "评估方式")
	private Integer evaluateType;

	/**
	 * 显示已评估
	 *
	 * @see Boolean
	 */
	@NotNull
	@ApiModelProperty(value = "只展示未评估")
	private Boolean isShowEvaluated;

}
